#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/wait.h>

#define THREAD_COUNT 20

void *thread_func(void *arg) {
    long idx = (long)arg;
    // 打印线程启动信息
    printf("[INFO] 线程 %ld 开始运行, 父进程 PID: %d, TID: %lu\n", idx, getpid(), pthread_self());

    // 每个线程都调用 fork()，创建子进程
    pid_t pid = fork();
    if (pid < 0) {
        perror("fork() 失败");
        exit(EXIT_FAILURE);
    } else if (pid == 0) {
        // 子进程分支：仅包含调用 fork 的该线程
        printf("[CHILD] 线程 %ld, 子进程 PID: %d, 父进程 PID: %d, TID: %lu\n", idx, getpid(), getppid(), pthread_self());
        // 模拟子进程的工作
        sleep(1);
        exit(0);
    } else {
        // 父进程分支（仍然处于多线程环境中）
        printf("[PARENT] 线程 %ld, 父进程 PID: %d, fork() 创建子进程 PID: %d, TID: %lu\n", idx, getpid(), pid, pthread_self());
        // 父进程等待该子进程结束，防止僵尸进程
        int status;
        waitpid(pid, &status, 0);
    }

    // 模拟线程后续工作
    sleep(1);
    printf("[INFO] 线程 %ld 正常结束\n", idx);
    return NULL;
}

int main(void) {
    pthread_t threads[THREAD_COUNT];

    // 创建 20 个线程，每个线程都会调用 fork()
    for (long i = 0; i < THREAD_COUNT; i++){
        if (pthread_create(&threads[i], NULL, thread_func, (void *)i) != 0) {
            perror("pthread_create 失败");
            exit(EXIT_FAILURE);
        }
    }

    // 等待所有线程结束
    for (int i = 0; i < THREAD_COUNT; i++){
        pthread_join(threads[i], NULL);
    }

    printf("[INFO] 父进程 (PID: %d) 所有线程结束\n", getpid());
    return 0;
    // XXX:/mnt/e/idea_project/CSLearn/src/cpp# ./MultiThreadFork
    // [INFO] 线程 0 开始运行, 父进程 PID: 53, TID: 140590604929848
    // [INFO] 线程 1 开始运行, 父进程 PID: 53, TID: 140590604786488
    // [INFO] 线程 2 开始运行, 父进程 PID: 53, TID: 140590604643128
    // [INFO] 线程 3 开始运行, 父进程 PID: 53, TID: 140590604499768
    // [INFO] 线程 4 开始运行, 父进程 PID: 53, TID: 140590604356408
    // [INFO] 线程 5 开始运行, 父进程 PID: 53, TID: 140590604213048
    // [INFO] 线程 6 开始运行, 父进程 PID: 53, TID: 140590604069688
    // [INFO] 线程 7 开始运行, 父进程 PID: 53, TID: 140590603926328
    // [INFO] 线程 8 开始运行, 父进程 PID: 53, TID: 140590603782968
    // [INFO] 线程 9 开始运行, 父进程 PID: 53, TID: 140590603639608
    // [INFO] 线程 10 开始运行, 父进程 PID: 53, TID: 140590603496248
    // [INFO] 线程 11 开始运行, 父进程 PID: 53, TID: 140590603352888
    // [INFO] 线程 12 开始运行, 父进程 PID: 53, TID: 140590603209528
    // [INFO] 线程 13 开始运行, 父进程 PID: 53, TID: 140590603066168
    // [INFO] 线程 14 开始运行, 父进程 PID: 53, TID: 140590602922808
    // [INFO] 线程 15 开始运行, 父进程 PID: 53, TID: 140590602779448
    // [INFO] 线程 16 开始运行, 父进程 PID: 53, TID: 140590602636088
    // [INFO] 线程 17 开始运行, 父进程 PID: 53, TID: 140590602492728
    // [INFO] 线程 18 开始运行, 父进程 PID: 53, TID: 140590602349368
    // [INFO] 线程 19 开始运行, 父进程 PID: 53, TID: 140590602206008
    // [CHILD] 线程 0, 子进程 PID: 74, 父进程 PID: 53, TID: 140590604929848
    // [PARENT] 线程 0, 父进程 PID: 53, fork() 创建子进程 PID: 74, TID: 140590604929848
    // [CHILD] 线程 1, 子进程 PID: 75, 父进程 PID: 53, TID: 140590604786488
    // [CHILD] 线程 2, 子进程 PID: 76, 父进程 PID: 53, TID: 140590604643128
    // [PARENT] 线程 2, 父进程 PID: 53, fork() 创建子进程 PID: 76, TID: 140590604643128
    // [PARENT] 线程 1, 父进程 PID: 53, fork() 创建子进程 PID: 75, TID: 140590604786488
    // [CHILD] 线程 6, 子进程 PID: 77, 父进程 PID: 53, TID: 140590604069688
    // [PARENT] 线程 6, 父进程 PID: 53, fork() 创建子进程 PID: 77, TID: 140590604069688
    // [CHILD] 线程 17, 子进程 PID: 78, 父进程 PID: 53, TID: 140590602492728
    // [PARENT] 线程 17, 父进程 PID: 53, fork() 创建子进程 PID: 78, TID: 140590602492728
    // [CHILD] 线程 19, 子进程 PID: 79, 父进程 PID: 53, TID: 140590602206008
    // [PARENT] 线程 19, 父进程 PID: 53, fork() 创建子进程 PID: 79, TID: 140590602206008
    // [CHILD] 线程 15, 子进程 PID: 80, 父进程 PID: 53, TID: 140590602779448
    // [PARENT] 线程 15, 父进程 PID: 53, fork() 创建子进程 PID: 80, TID: 140590602779448
    // [CHILD] 线程 4, 子进程 PID: 81, 父进程 PID: 53, TID: 140590604356408
    // [PARENT] 线程 4, 父进程 PID: 53, fork() 创建子进程 PID: 81, TID: 140590604356408
    // [CHILD] 线程 13, 子进程 PID: 82, 父进程 PID: 53, TID: 140590603066168
    // [PARENT] 线程 13, 父进程 PID: 53, fork() 创建子进程 PID: 82, TID: 140590603066168
    // [CHILD] 线程 10, 子进程 PID: 83, 父进程 PID: 53, TID: 140590603496248
    // [PARENT] 线程 10, 父进程 PID: 53, fork() 创建子进程 PID: 83, TID: 140590603496248
    // [CHILD] 线程 18, 子进程 PID: 84, 父进程 PID: 53, TID: 140590602349368
    // [PARENT] 线程 18, 父进程 PID: 53, fork() 创建子进程 PID: 84, TID: 140590602349368
    // [CHILD] 线程 12, 子进程 PID: 85, 父进程 PID: 53, TID: 140590603209528
    // [PARENT] 线程 12, 父进程 PID: 53, fork() 创建子进程 PID: 85, TID: 140590603209528
    // [CHILD] 线程 11, 子进程 PID: 86, 父进程 PID: 53, TID: 140590603352888
    // [PARENT] 线程 11, 父进程 PID: 53, fork() 创建子进程 PID: 86, TID: 140590603352888
    // [PARENT] 线程 7, 父进程 PID: 53, fork() 创建子进程 PID: 87, TID: 140590603926328
    // [CHILD] 线程 5, 子进程 PID: 88, 父进程 PID: 53, TID: 140590604213048
    // [PARENT] 线程 5, 父进程 PID: 53, fork() 创建子进程 PID: 88, TID: 140590604213048
    // [CHILD] 线程 8, 子进程 PID: 89, 父进程 PID: 53, TID: 140590603782968
    // [PARENT] 线程 8, 父进程 PID: 53, fork() 创建子进程 PID: 89, TID: 140590603782968
    // [PARENT] 线程 14, 父进程 PID: 53, fork() 创建子进程 PID: 90, TID: 140590602922808
    // [CHILD] 线程 14, 子进程 PID: 90, 父进程 PID: 53, TID: 140590602922808
    // [CHILD] 线程 7, 子进程 PID: 87, 父进程 PID: 53, TID: 140590603926328
    // [PARENT] 线程 16, 父进程 PID: 53, fork() 创建子进程 PID: 91, TID: 140590602636088
    // [CHILD] 线程 16, 子进程 PID: 91, 父进程 PID: 53, TID: 140590602636088
    // [PARENT] 线程 3, 父进程 PID: 53, fork() 创建子进程 PID: 92, TID: 140590604499768
    // [CHILD] 线程 3, 子进程 PID: 92, 父进程 PID: 53, TID: 140590604499768
    // [PARENT] 线程 9, 父进程 PID: 53, fork() 创建子进程 PID: 93, TID: 140590603639608
    // [CHILD] 线程 9, 子进程 PID: 93, 父进程 PID: 53, TID: 140590603639608
    // [INFO] 线程 1 正常结束
    // [INFO] 线程 0 正常结束
    // [INFO] 线程 2 正常结束
    // [INFO] 线程 6 正常结束
    // [INFO] 线程 17 正常结束
    // [INFO] 线程 19 正常结束
    // [INFO] 线程 15 正常结束
    // [INFO] 线程 4 正常结束
    // [INFO] 线程 13 正常结束
    // [INFO] 线程 10 正常结束
    // [INFO] 线程 18 正常结束
    // [INFO] 线程 12 正常结束
    // [INFO] 线程 11 正常结束
    // [INFO] 线程 5 正常结束
    // [INFO] 线程 8 正常结束
    // [INFO] 线程 7 正常结束
    // [INFO] 线程 14 正常结束
    // [INFO] 线程 16 正常结束
    // [INFO] 线程 3 正常结束
    // [INFO] 线程 9 正常结束
    // [INFO] 父进程 (PID: 53) 所有线程结束
}
