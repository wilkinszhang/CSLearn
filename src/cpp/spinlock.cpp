#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sched.h>
#include <pthread.h>
#include <stdint.h>

// 1. 定义 pause_cpu() —— 发出 CPU PAUSE 指令
static inline void pause_cpu(void) {
    __asm__ __volatile__("pause" ::: "memory");
}

// 2. 定义 CAS，使用 GCC 内置原子操作
//    返回 1 表示交换成功，将 *ptr 从 oldval 改为 newval
static inline int cas_uintptr(volatile uintptr_t *ptr,
                              uintptr_t oldval,
                              uintptr_t newval)
{
    return __sync_bool_compare_and_swap(ptr, oldval, newval);
}

// 3. 自旋锁类型
typedef struct {
    volatile uintptr_t owner;
} spinlock_t;

// 4. 初始化
static inline void spinlock_init(spinlock_t *lock) {
    lock->owner = 0;
}

// 5. 获取锁
void spinlock_lock(spinlock_t *lock, uintptr_t pid) {
    int n, i;

    while (1) {
        // 快速路径：先读后 CAS
        if (lock->owner == 0 &&
            cas_uintptr(&lock->owner, 0, pid))
        {
            // 成功获得锁
            return;
        }
        // 如果是多核，再做忙等待
        if (sysconf(_SC_NPROCESSORS_ONLN) > 1) {
            for (n = 1; n < 2048; n <<= 1) {
                for (i = 0; i < n; i++) {
                    pause_cpu();       // CPU暂停
                }
                // 再次尝试 CAS
                if (lock->owner == 0 &&
                    cas_uintptr(&lock->owner, 0, pid))
                {
                    return;
                }
            }
        }
        // 单核或长时间未获锁，主动让出 CPU
        sched_yield();
    }
}

// 6. 释放锁
static inline void spinlock_unlock(spinlock_t *lock, uintptr_t pid) {
    // 仅持有者才能解锁
    if (lock->owner != pid) {
        fprintf(stderr, "spinlock_unlock: not owner (pid=%lu)\n",
                (unsigned long)pid);
        abort();
    }
    // 内存屏障，确保之前的写操作都完成
    __sync_synchronize();
    lock->owner = 0;
}

// 7. 全局计数器，供所有线程累加
static volatile int counter = 0;

#define THREADS 4
#define ITER   1000000

spinlock_t lock;

void *worker(void *arg) {
    uintptr_t pid = (uintptr_t)arg + 1;
    for (int i = 0; i < ITER; i++) {
        spinlock_lock(&lock, pid);
        counter++;
        spinlock_unlock(&lock, pid);
    }
    return NULL;
}

int main(void) {
    pthread_t th[THREADS];

    spinlock_init(&lock);

    for (int i = 0; i < THREADS; i++) {
        if (pthread_create(&th[i], NULL, worker, (void*)(uintptr_t)i) != 0) {
            perror("pthread_create");
            exit(EXIT_FAILURE);
        }
    }
    for (int i = 0; i < THREADS; i++) {
        pthread_join(th[i], NULL);
    }

    // 正确打印全局 counter
    printf("counter = %d (expected %d)\n",
           counter, THREADS * ITER);
    return 0;
}
