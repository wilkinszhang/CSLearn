//
// Created by zhangweijian on 2025/5/13.
//
#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>
#include<errno.h>

int main(void) {
    pid_t pid=fork();
    if(pid<0) {
        perror("fork failed");
        return 1;
    }else if(pid==0) {
        //子进程分支
        printf("Child: fork() returned %d\n",pid);
    }else {
        //父进程分支
        printf("Parent: fork() returned child pid %d\n",pid);
    }
    return 0;
}