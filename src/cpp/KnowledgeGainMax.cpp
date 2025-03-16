//
// Created by zhangweijian on 2025/3/11.
//
// 多多很喜欢读书，现在多多有一本书，书有n页，每读完一页，多多都可以获得ai的知识量。正常情况下，多多每分钟可以读完一页，但是多多还有一
// 个能力，可以在一分钟内读完连续两页的内容，只不过能获取的知识量只有正常读完两页知识量之和的二分之一。现在多多只有m分钟的时间用来读完这本书
// ，请你告诉多多他最多可以获得多少的知识。输入描述输入两行第一行包含两个整数n和m(1<=n,m<=1000),表示书的页数和用来读书的时间。第二行包含n
// 个数字，每个数字ai(0<=ai<=10000)表示第i页的知识量。输出描述输出一行，包含一个数字ans，表示最大可获取的知识量，输出的结果保留一位小数，如
// 果在m分钟内不能读完一本书，输出"-1"补充说明50%的数据,n<=100100%的数据,n<=1000,0<=ai<=10000,m<=n示例1输入4 1
// 1 2 3 4
// 输出-1
// 示例2输入5 3
// 1 2 3 2 1
// 输出6.0
// 使用能力读完1、2两页，获取知识量1.5
// 第3页正常读，获取知识量3
// 使用能力读完4、5两页，获取知识量1.5
// 花费三分钟总计获得6的知识量
#include <iostream>
#include <vector>
#include <algorithm>
#include <iomanip>
using namespace std;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;
    vector<int> a(n);
    long long S = 0;
    for (int i = 0; i < n; i++){
        cin >> a[i];
        S += a[i];
    }

    // 最快阅读完书的条件：最多一分钟读两页，所以必须满足 2*m >= n，否则不可能读完
    if(2 * m < n){
        cout << -1;
        return 0;
    }

    // 必须使用的特殊能力次数
    int x = n - m;  // x 次特殊能力，每次阅读两页节省1分钟

    // 定义dp[i][j] 表示考虑前 i 页，选取 j 个不重叠连续页对所得到的两页知识和总和的最小值
    const long long INF = 1e15;
    vector<vector<long long>> dp(n+1, vector<long long>(x+1, INF));
    dp[0][0] = 0;

    for (int i = 1; i <= n; i++){
        for (int j = 0; j <= x; j++){
            // 不使用第 i 页与前一页配对，直接跳过
            dp[i][j] = min(dp[i][j], dp[i-1][j]);
            // 如果可以配对第 i-1 和第 i 页
            if(i >= 2 && j >= 1){
                dp[i][j] = min(dp[i][j], dp[i-2][j-1] + a[i-2] + a[i-1]);
            }
        }
    }

    if(dp[n][x] == INF){
        cout << -1;
        return 0;
    }

    // 每次使用特殊能力，损失为该对页知识量的一半，所以总损失为 0.5 * (dp[n][x])
    double ans = S - dp[n][x] / 2.0;
    cout << fixed << setprecision(1) << ans;
    return 0;
}
