//
// Created by zhangweijian on 2025/3/6.
//
//https://leetcode.cn/contest/weekly-contest-438/problems/check-if-digits-are-equal-in-string-after-operations-ii/
#include <iostream>
#include <vector>
#include <climits>
using namespace std;

class Solution {
public:
    bool hasSameDigits(const string &s) {
        int n = s.size();
        if(n == 2) return s[0] == s[1];

        // 预先将字符转换为数字
        vector<int> digits(n);
        for (int i = 0; i < n; i++)
            digits[i] = s[i] - '0';

        // 用于累加两个最终数字的贡献
        int sumX = 0, sumY = 0;
        // 初始项：C(0) = 1
        int cur = 1;
        // 记录因子2和因子5的剩余个数
        int extra2 = 0, extra5 = 0;

        // 第一项对应 i = 0
        sumX = (sumX + cur * digits[0]) % 10;
        // 对应 Y 的第一项使用 digits[1]
        sumY = (sumY + cur * digits[1]) % 10;

        // 迭代计算 i = 1 到 n-2，对应 C(i) = binom(n-2, i) mod 10
        for (int i = 0; i < n - 2; i++) {
            int num = (n - 2 - i); // 分子
            // 提取 num 中的 2 和 5 因子
            int temp = num;
            while(temp % 2 == 0) { extra2++; temp /= 2; }
            while(temp % 5 == 0) { extra5++; temp /= 5; }
            // 更新 cur：乘上去掉2和5因子后的部分
            cur = (cur * (temp % 10)) % 10;

            int den = i + 1; // 分母
            temp = den;
            while(temp % 2 == 0) { extra2--; temp /= 2; }
            while(temp % 5 == 0) { extra5--; temp /= 5; }
            // 此时 temp 与 10 互素，可以求模逆元
            int inv = modInverse(temp, 10);
            cur = (cur * inv) % 10;

            // 将剩余的 2 和 5 因子合并
            int factor = 1;
            if (extra2 > 0 && extra5 > 0) {
                // 一旦同时有至少一个2和5，乘积至少有一个10 => 模10为0
                factor = 0;
            } else if (extra2 > 0) {
                factor = modPow(2, extra2, 10);
            } else if (extra5 > 0) {
                // 5^k mod 10为5（当 k>=1 时）
                factor = 5;
            }
            int binomVal = (cur * factor) % 10;

            // 累加对最终X、Y的贡献
            sumX = (sumX + binomVal * digits[i+1]) % 10;     // X对应 s[i]，注意下标偏移
            if(i+2 < n)
                sumY = (sumY + binomVal * digits[i+2]) % 10;   // Y对应 s[i+1]
        }

        // 归一化模10
        if(sumX < 0) sumX += 10;
        if(sumY < 0) sumY += 10;
        return sumX == sumY;
    }

    // 求 a 在模 m 下的逆元，m=10且 a 与10互素时可暴力枚举
    int modInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) return x;
        }
        return 1; // 理论上不会走到这里
    }

    // 快速幂
    int modPow(int base, int exp, int mod) {
        int result = 1;
        while(exp > 0) {
            if(exp & 1)
                result = (result * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }

};
int main() {
    Solution s=Solution();
    string str="012";
    std::cout<<s.hasSameDigits(str);
}