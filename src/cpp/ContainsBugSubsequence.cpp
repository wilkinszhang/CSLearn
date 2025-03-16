//
// Created by zhangweijian on 2025/3/9.
//
// https://oj.niumacode.com/problem/P1422
// https://leetcode.cn/problems/minimum-edge-weight-equilibrium-queries-in-a-tree/description/?envType=daily-question&envId=2024-01-26

//       1(A)
//      /   \
//     2(U)  3(G)
//    / \
//   4(B) 5(C)
//  /     \
// 7(B)    6(G)
// 查询是从节点7到节点6的路径：
// 7 → 4 → 2 → 5 → 6
// 对应的字母序列是: B → B → U → C → G
// 在这个序列"BBUCG"中，我们可以找到子序列"BUG"：
//
// 第一个B（来自节点7）
// U（来自节点2）
// G（来自节点6）
//
// 因为从节点7到节点6的路径上的字母序列中包含"BUG"作为子序列，所以(7, 6)不是好对，输出"NO"。

#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

const int MAXN = 100005;
const int LOGN = 20;

int n, q;
int parentArr[MAXN];  // 父亲数组，1号结点的父亲为 0
char letter[MAXN];    // 每个结点携带的字母（1-indexed）
int depth[MAXN];      // 每个结点到根的深度
int up[MAXN][LOGN];   // 二叉上跳预处理表，up[v][j] 表示 v 的 2^j 祖先

// LCA 函数（利用二叉上跳）
int getLCA(int u, int v) {
    if(depth[u] < depth[v]) swap(u, v);
    int d = depth[u] - depth[v];
    for (int j = 0; j < LOGN; j++){
        if(d & (1 << j))
            u = up[u][j];
    }
    if(u == v) return u;
    for (int j = LOGN - 1; j >= 0; j--){
        if(up[u][j] != up[v][j]){
            u = up[u][j];
            v = up[v][j];
        }
    }
    return parentArr[u];
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> n >> q;
    // 输入父亲数组：题目保证 1 号为根，父亲 1 为 0
    for (int i = 1; i <= n; i++){
        cin >> parentArr[i];
    }
    // 输入每个结点携带的字母
    for (int i = 1; i <= n; i++){
        cin >> letter[i];
    }

    // 计算每个结点的深度，1 号深度为 0，注意题目保证 father[i] < i
    depth[1] = 0;
    for (int i = 2; i <= n; i++){
        depth[i] = depth[ parentArr[i] ] + 1;
    }

    // 构造二叉上跳表：up[v][0] = parentArr[v]
    for (int i = 1; i <= n; i++){
        up[i][0] = parentArr[i];
    }
    for (int j = 1; j < LOGN; j++){
        for (int i = 1; i <= n; i++){
            int par = up[i][j - 1];
            up[i][j] = (par == 0 ? 0 : up[par][j - 1]);
        }
    }

    // 模式 "BUG"
    string pattern = "BUG";
    // 处理每个查询
    while(q--){
        int u, v;
        cin >> u >> v;
        int L = getLCA(u, v);
        int state = 0;  // 状态 0～3，初始状态 0

        // 上半段：从 u 向上走到 L（不包括 L），顺序即路径上字母出现的顺序
        int temp = u;
        while(temp != L){
            if(state < 3 && letter[temp] == pattern[state])
                state++;
            temp = parentArr[temp];
        }
        // 处理 L 结点
        if(state < 3 && letter[L] == pattern[state])
            state++;
        // 下半段：从 v 向上走到 L（不包括 L），由于实际路径是 L->...->v，所以需要先保存再倒序模拟
        vector<int> down;
        temp = v;
        while(temp != L){
            down.push_back(temp);
            temp = parentArr[temp];
        }
        reverse(down.begin(), down.end());
        for (int x : down){
            if(state < 3 && letter[x] == pattern[state])
                state++;
        }
        // 如果最终状态为 3，则 "BUG" 作为子序列存在，输出 "NO"；否则 "YES"
        cout << (state == 3 ? "NO" : "YES") << "\n";
    }
    return 0;
}

