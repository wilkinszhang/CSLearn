//
// Created by zhangweijian on 2025/5/13.
//
#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>
#include<errno.h>

// fork方法的返回值
int main(void) {
    pid_t a = fork();
    pid_t b = fork();
    printf("%d %d\n", a, b);
    /*./a.out
    19 20
    19 0
    0 21
    0 0*/
    return 0;
}