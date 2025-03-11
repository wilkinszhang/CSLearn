//
// Created by zhangweijian on 2025/3/11.
//

#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
// 给定三个长度为的数组a,b,c，最多可以进行一次操作，交换数组中的两个数字的位置。定义s=(b1-a1)*c1+(b2-a2)*c2+...+(bn-an)*cn，求最多一次操
//作后的最大值是多少?输入描述第一行输入一个整数n(1<=n<=10^5）。接下来3行，每行n个整数，第一行为数组a，第二行为数组b，第三行为数组c.1<=ai,bi,
//ci<=10^3输出描述输出一个整数，表示s的最大值。示例1输入3
// 2 2 1
// 2 3 3
// 1 2 1
// 输出6
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int n;
    cin >> n;
    vector<long long> a(n), b(n), c(n);
    for (int i=0; i<n; i++){
        cin >> a[i];
    }
    for (int i=0; i<n; i++){
        cin >> b[i];
    }
    for (int i=0; i<n; i++){
        cin >> c[i];
    }
    
    long long s = 0;
    long long bestGain = 0;
    for (int i=0; i<n; i++){
        long long f = (b[i] - a[i]) * c[i];
        s += f;
        
        // 方案1：交换 a 与 b
        long long delta_ab = -2 * f;
        
        // 方案2：交换 a 与 c
        long long f_ac = (b[i] - c[i]) * a[i];
        long long delta_ac = f_ac - f;
        
        // 方案3：交换 b 与 c
        long long f_bc = (c[i] - a[i]) * b[i];
        long long delta_bc = f_bc - f;
        
        long long curMax = max({delta_ab, delta_ac, delta_bc});
        if(curMax > bestGain)
            bestGain = curMax;
    }
    
    cout << s + bestGain << "\n";
    return 0;
}