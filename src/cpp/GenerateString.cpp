//https://leetcode.cn/contest/weekly-contest-439/problems/lexicographically-smallest-generated-string/description/?slug=sum-of-k-subarrays-with-length-at-least-m&region=local_v2
//TODO 有样例没过
//
// Created by zhangweijian on 2025/3/5.
//
#include <iostream>
#include <vector>
#include <climits>
using namespace std;

class Solution {
public:
    string generateString(string str1, string str2) {
        int n = str1.size();
        int m = str2.size();
        int totalLen = n + m - 1;

        vector<char> res(totalLen, 0);  // 0表示未设置字符

        // 处理所有T条件
        for (int i = 0; i < n; ++i) {
            if (str1[i] != 'T') continue;

            if (i + m > totalLen) {
                return "";
            }

            for (int k = 0; k < m; ++k) {
                int pos = i + k;
                if (res[pos] != 0 && res[pos] != str2[k]) {
                    return "";
                }
                res[pos] = str2[k];
            }
        }

        // 处理所有F条件
        for (int i = 0; i < n; ++i) {
            if (str1[i] != 'F') continue;

            if (i + m > totalLen) {
                return "";
            }

            bool canEqual = true;
            bool hasUnset = false;
            for (int k = 0; k < m; ++k) {
                int pos = i + k;
                if (res[pos] == 0) {
                    hasUnset = true;
                } else if (res[pos] != str2[k]) {
                    canEqual = false;
                    break;
                }
            }

            if (!canEqual) {
                continue;
            }

            if (!hasUnset) {
                return "";
            }

            // 找到第一个未设置的字符，设置为与str2不同的最小字符
            for (int k = 0; k < m; ++k) {
                int pos = i + k;
                if (res[pos] == 0) {
                    res[pos] = (str2[k] != 'a') ? 'a' : 'b';
                    break;
                }
            }

            // 检查是否仍然等于str2
            bool isEqual = true;
            for (int k = 0; k < m; ++k) {
                int pos = i + k;
                if (res[pos] != str2[k]) {
                    isEqual = false;
                    break;
                }
            }
            if (isEqual) {
                return "";
            }
        }

        // 填充剩余未设置的字符为'a'
        for (int i = 0; i < totalLen; ++i) {
            if (res[i] == 0) {
                res[i] = 'a';
            }
        }

        return string(res.begin(), res.end());
    }

};

int main(){
    Solution s= Solution();
    cout<<s.generateString("F","acfcfc");
}