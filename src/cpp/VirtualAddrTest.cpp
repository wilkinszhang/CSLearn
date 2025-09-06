#include <stdio.h>
#include <unistd.h>
using namespace std;

int testvar = 0;

// 虚拟内存和缺页中断
int main(int argc, char const *argv[])
{
    testvar += 1;
    sleep(10);
    printf("address: %p, value: %d\n", (void*)&testvar, testvar);
//    address: 0x55db3d7e0070, value: 1
//    address: 0x55ee54f75070, value: 1
    return 0;
}
