#include<iostream>
using namespace std;
#include<string>
#include<vector>
//https://leetcode.cn/contest/weekly-contest-439/problems/longest-palindromic-subsequence-after-at-most-k-operations/?slug=sum-of-k-subarrays-with-length-at-least-m&region=local_v2


class Solution {
public:
    int longestPalindromicSubsequence(string s, int k) {
        int n = s.length();

        // dp[i][j][ops] = length of longest palindromic subsequence
        // in substring s[i...j] using at most 'ops' operations
        vector<vector<vector<int>>> memo(n, vector<vector<int>>(n, vector<int>(k+1, -1)));

        return solve(s, 0, n-1, k, memo);
    }

private:
    // Calculate minimum operations to change from character a to b
    int minOperations(char a, char b) {
        int diff = abs(a - b);
        return min(diff, 26 - diff);
    }

    int solve(const string& s, int i, int j, int k, vector<vector<vector<int>>>& memo) {
        // Base cases
        if (i > j) return 0;
        if (i == j) return 1;

        // If already computed
        if (memo[i][j][k] != -1) return memo[i][j][k];

        // Case 1: Skip either character
        int result = max(solve(s, i+1, j, k, memo), solve(s, i, j-1, k, memo));

        // Case 2: If characters match, include both
        if (s[i] == s[j]) {
            result = max(result, solve(s, i+1, j-1, k, memo) + 2);
        }
        // Case 3: Try to make characters match by operations
        else if (k > 0) {
            // Minimum operations needed to make s[i] match s[j] or vice versa
            int ops = minOperations(s[i], s[j]);

            if (ops <= k) {
                result = max(result, solve(s, i+1, j-1, k - ops, memo) + 2);
            }
        }

        memo[i][j][k] = result;
        return result;
    }
};