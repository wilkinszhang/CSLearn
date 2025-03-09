//
// Created by zhangweijian on 2025/3/9.
//
// https://oj.niumacode.com/problem/P1422

//TODO 建立的线段树应该没有问题，可视化这棵树:
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


// 代码不好写
// 7 1
// 0 1 1 2 2 5 4
// AUGBCGB
// 7 6
// [0, 0, 0, 0]
// [1, 3, 3, 3]
// [1, 2, 2, 3]
// [0, 1, 3, 3]
// [0, 2, 2, 3]
// [1, 1, 2, 3]
// [0, 1, 3, 3]
// [0, 1, 3, 3]
// [0, 1, 2, 3]
// [0, 2, 2, 3]
// [1, 1, 2, 3]
// [1, 1, 2, 3]
// [0, 1, 2, 3]
// [0, 1, 3, 3]
//
//  ----------------------
// [0, 0, 0, 0]
// [2, 2, 3, 3]
// [2, 2, 2, 3]
// [0, 1, 3, 3]
// [0, 2, 2, 3]
// [1, 1, 2, 3]
// [0, 1, 3, 3]
// [0, 1, 3, 3]
// [0, 1, 2, 3]
// [0, 2, 2, 3]
// [1, 1, 2, 3]
// [1, 1, 2, 3]
// [0, 1, 2, 3]
// [0, 1, 3, 3]

#include <iostream>
#include <vector>
#include <cstring>
using namespace std;

const int NMAX = 100005;

// 状态空间只有4个状态：0,1,2,3
struct Trans {
    int tr[4];
};

// 单个字母转移函数（正向定义）
inline Trans makeTrans(char c) {
    Trans res;
    for (int i = 0; i < 4; i++) {
        if(i == 3) {
            res.tr[i] = 3;
        } else {
            // 如果c和模式"BUG"在位置i相等则推进状态，否则不变
            res.tr[i] = (c == "BUG"[i]) ? i + 1 : i;
        }
    }
    return res;
}

inline Trans identityTrans() {
    Trans res;
    for (int i = 0; i < 4; i++) res.tr[i] = i;
    return res;
}

// 正向合成函数：mergeF(a,b)= b ° a，即 result[i] = b.tr[ a.tr[i] ]
inline Trans mergeF(const Trans &a, const Trans &b) {
    Trans res;
    for (int i = 0; i < 4; i++){
         res.tr[i] = b.tr[ a.tr[i] ];
    }
    return res;
}

// 反向合成函数：mergeB(a,b)= a ° b，即 result[i] = a.tr[ b.tr[i] ]
inline Trans mergeB(const Trans &a, const Trans &b) {
    Trans res;
    for (int i = 0; i < 4; i++){
         res.tr[i] = a.tr[ b.tr[i] ];
    }
    return res;
}

// 重链剖分相关全局变量
int n, q;
vector<int> adj[NMAX];
int parent[NMAX], depth[NMAX], heavy[NMAX], head[NMAX], pos[NMAX], sz[NMAX];
int curPos = 0;
// 为方便查询，记录基数组：base[i] 表示编号为i的在重链剖分中的结点编号
int base[NMAX];
// 每个结点对应的字母（1-indexed）
char letter[NMAX];

// DFS求子树大小、确定重儿子
int dfs(int u) {
    sz[u] = 1;
    heavy[u] = -1;
    int maxSize = 0;
    for (int v : adj[u]) {
        depth[v] = depth[u] + 1;
        parent[v] = u;
        int subSize = dfs(v);
        if (subSize > maxSize) {
            maxSize = subSize;
            heavy[u] = v;
        }
        sz[u] += subSize;
    }
    return sz[u];
}

// 重链剖分，给每个结点分配在基数组中的位置
void decompose(int u, int h) {
    head[u] = h;
    pos[u] = curPos;
    base[curPos++] = u;
    if (heavy[u] != -1)
        decompose(heavy[u], h);
    for (int v : adj[u]) {
        if (v == heavy[u]) continue;
        decompose(v, v);
    }
}

// LCA 求法（利用重链剖分信息）
int lca(int u, int v) {
    while(head[u] != head[v]) {
        if(depth[head[u]] > depth[head[v]])
            u = parent[head[u]];
        else
            v = parent[head[v]];
    }
    return depth[u] < depth[v] ? u : v;
}

// 线段树——正向版：segF
vector<Trans> segF; // 线段树数组，下标从1开始

void buildSegF(int idx, int l, int r) {
    if(l == r) {
        int node = base[l];
        segF[idx] = makeTrans(letter[node]);
        return;
    }
    int mid = (l + r) / 2;
    buildSegF(idx*2, l, mid);
    buildSegF(idx*2+1, mid+1, r);
    segF[idx] = mergeF(segF[idx*2], segF[idx*2+1]);
}

Trans querySegF(int idx, int l, int r, int ql, int qr) {
    if(ql > r || qr < l) return identityTrans();
    if(ql <= l && r <= qr) return segF[idx];
    int mid = (l + r) / 2;
    Trans leftRes = querySegF(idx*2, l, mid, ql, qr);
    Trans rightRes = querySegF(idx*2+1, mid+1, r, ql, qr);
    return mergeF(leftRes, rightRes);
}

// 线段树——反向版：segB
vector<Trans> segB;

void buildSegB(int idx, int l, int r) {
    if(l == r) {
        int node = base[l];
        segB[idx] = makeTrans(letter[node]);
        return;
    }
    int mid = (l + r) / 2;
    buildSegB(idx*2, l, mid);
    buildSegB(idx*2+1, mid+1, r);
    segB[idx] = mergeB(segB[idx*2], segB[idx*2+1]);
}

Trans querySegB(int idx, int l, int r, int ql, int qr) {
    if(ql > r || qr < l) return identityTrans();
    if(ql <= l && r <= qr) return segB[idx];
    int mid = (l + r) / 2;
    Trans leftRes = querySegB(idx*2, l, mid, ql, qr);
    Trans rightRes = querySegB(idx*2+1, mid+1, r, ql, qr);
    return mergeB(leftRes, rightRes);
}

// 查询两点路径(u,v)的整体转移函数（组合上半部和下半部）
Trans queryPath(int u, int v) {
    int L = lca(u, v);
    Trans transUp = identityTrans();
    // 处理上半部：从 u 到 L（不包括 L），注意方向为向上，用 segB 查询
    while(head[u] != head[L]) {
        int start = pos[ head[u] ];
        int end = pos[u];
        Trans cur = querySegB(1, 0, n-1, start, end);
        // 合成：这里希望 transUp = cur ° transUp
        transUp = mergeF(cur, transUp);
        u = parent[ head[u] ];
    }
    if(u != L) {
        Trans cur = querySegB(1, 0, n-1, pos[L] + 1, pos[u]);
        transUp = mergeF(cur, transUp);
    }
    // 下半部：从 L 到 v（包括 L），正向查询 segF
    Trans transDown = makeTrans(letter[L]);
    while(head[v] != head[L]) {
        int start = pos[ head[v] ];
        int end = pos[v];
        Trans cur = querySegF(1, 0, n-1, start, end);
        transDown = mergeF(cur, transDown);
        v = parent[ head[v] ];
    }
    if(v != L) {
        Trans cur = querySegF(1, 0, n-1, pos[L] + 1, pos[v]);
        transDown = mergeF(cur, transDown);
    }
    // 最终整体转换函数 = transDown ° transUp
    Trans total = mergeF(transUp, transDown);
    return total;
}

ostream& operator<<(ostream &os, const Trans &t) {
    os << "[";
    for (int i = 0; i < 4; i++) {
        os << t.tr[i];
        if (i != 3) {
            os << ", ";
        }
    }
    os << "]";
    return os;
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> n >> q;
    // 注意：题目保证结点编号1为根，father1=0
    // 读入父亲数组（1-indexed）
    for (int i = 1; i <= n; i++) {
        int fa;
        cin >> fa;
        if(i == 1) continue; // 根结点father1=0
        // i 的父亲为 fa，将 i 加入 fa 的孩子列表
        adj[fa].push_back(i);
    }
    // 读入每个结点携带的字母（以空格分隔或连续均可）
    for (int i = 1; i <= n; i++){
        cin >> letter[i];
    }

    // 初始化
    depth[1] = 0;
    parent[1] = 0;
    dfs(1);
    curPos = 0;
    // base数组大小为 n
    //（注意：pos[] 的取值范围为[0, n-1]）
    decompose(1, 1);

    // 建立两棵线段树，基于基数组 [0, n-1]
    segF.resize(4 * n);
    segB.resize(4 * n);
    buildSegF(1, 0, n-1);
    buildSegB(1, 0, n-1);


    // for (const auto &t : segF) {
    //     cout << t << endl;
    // }
    // std::cout  << " ----------------------"<<endl;
    // for (const auto &t : segB) {
    //     cout << t << endl;
    // }

    // 处理每个查询
    // 对于查询(u,v)：若路径上状态机从初始状态 0 经过转移后得到状态 3，则说明“BUG”作为子序列存在，答案输出 "NO"，否则 "YES"
    while(q--){
        int u, v;
        cin >> u >> v;
        Trans total = queryPath(u, v);
        cout << (total.tr[0] == 3 ? "NO" : "YES") << "\n";
    }

    return 0;
}
