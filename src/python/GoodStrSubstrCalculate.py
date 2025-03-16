import sys,sys
import bisect
# 小红认为一个字符串是好字符串当且仅当这个字符串去重后按照相对顺序排列在字典序上是一个单调递增字符串。例如:s=aabca ，去重后为abc，满足字典序
# 单调递增。现在小红有一个长度为n的字符串，请你帮助她计算有多少个非空子串是好字符串。去重:每种字符只保留第一个出现的位置。子串:子串是指一个字符
# 串中的连续部分。输入描述第一行一个整数n(1<=n<=10^5)，表示字符串长度。第二行一个长度为n的字符串t，保证输入只含小写字母。输出描述一个整数，
# 表示t中有多少个子串是好字符串。样例1输入5
# aabac
# 输出13
import sys
import bisect

def main():
    # Read first line for n
    n = int(input().strip())
    # Read second line for string s
    s = input().strip()
    # sys.stdin.read().split()一次性读取所有输入。在PyCharm的控制台中，这个方法会一直等待直到有EOF (End-of-File)信号，
    # 而在交互式环境中你不会自然地发送EOF信号，所以程序会一直等待更多输入而不会继续执行。
    # input()函数，这个函数在读取一行后就会返回结果，不需要EOF信号。

    # 预处理 run_end 数组
    run_end = [0]*n
    run_end[n-1] = n-1
    for i in range(n-2, -1, -1):
        if s[i] == s[i+1]:
            run_end[i] = run_end[i+1]
        else:
            run_end[i] = i

    # 记录每个字母出现的位置
    pos_list = { chr(c): [] for c in range(ord('a'), ord('z')+1) }
    for i, ch in enumerate(s):
        pos_list[ch].append(i)

    INF = n + 10  # 一个大数

    # 辅助函数：对于已知列表 lst（已排序），返回第一个 >= x 的值，若不存在则返回 INF
    def lower_bound(lst, x):
        idx = bisect.bisect_left(lst, x)
        if idx < len(lst):
            return lst[idx]
        return INF

    # 利用二分查找，从当前 r+1 开始，求所有未在 bitmask 中字母的第一个出现位置的最小值
    # bitmask: 26位整数，bit i 表示字母 chr(ord('a')+i) 是否出现
    def next_new_index(r, bitmask):
        cand = INF
        for c in range(26):
            if not (bitmask >> c) & 1:
                ch = chr(ord('a')+c)
                idx = lower_bound(pos_list[ch], r+1)
                if idx < cand:
                    cand = idx
        return cand

    # 模拟计算 f(l) —— 以 l 为起点的最长好子串的终止位置
    # 要求 l 为某个连续块（run）的起点
    def compute_f_from(l):
        # 初始化
        bitmask = 1 << (ord(s[l]) - ord('a'))
        current_max = s[l]
        r = run_end[l]
        while r < n - 1:
            # 求 [r+1, n-1] 中第一个出现的"新字母"（即不在 bitmask 中）的下标
            candidate_new = next_new_index(r, bitmask)
            if candidate_new == INF:
                r = n - 1
                break
            # 先将 [r+1, candidate_new - 1] 都"吃掉"（它们的字符都在 bitmask 中）
            r = candidate_new - 1
            # candidate_new 处出现一个新字母
            if s[candidate_new] > current_max:
                bitmask |= 1 << (ord(s[candidate_new]) - ord('a'))
                current_max = s[candidate_new]
                # 跳过该连续块
                r = run_end[candidate_new]
            else:
                break
        return r

    # 找出每个连续块的起点（即 run 的开始位置）
    runs = []
    i = 0
    while i < n:
        runs.append(i)
        i = run_end[i] + 1

    total = 0
    # 对于同一 run，所有起点 l 都有相同 f(l)
    for a in runs:
        b = run_end[a]  # 该 run 的终点
        f_val = compute_f_from(a)
        # 对于 l 从 a 到 b，贡献 = sum_{l=a}^{b} (f_val - l + 1)
        # 利用等差求和公式
        count_this_run = (f_val + 1) * (b - a + 1) - ((a + b) * (b - a + 1) // 2)
        total += count_this_run

    print(total)

if __name__ == '__main__':
    main()