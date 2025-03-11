# 多多在玩一个传送门游戏。游戏开始时多多在一维数轴的x=0处。他有n个传送门，每个传送门都有一个传送值ai，他能使用该传送门从x=t位置传送到x=t+ai，传送
# 门是消耗品，只能使用一次。同时他还有一个"反转"技能，使用该技能可以立即从位置 x=t"反转"到x=-t。多多必须从1-n依次使用这些传送门，可以在任何时候
# 使用"反转"技能(最多用一次，也可以不用)，问在传送过程中，多多到初始位置x=0最远的距离为多少?输入描述第一行为一个正整数n(1 ≤ n ≤ 10^5)第二行为
# n个整数a1,a2,...,an(-10^9 < ai ≤ 10^9)输出描述输出在传送过程中，少少到初始位置距离的最大值。补充说明对于 60% 的数据，1 <= n <= 10:对于
# 100%的数据，1 <= n <= 10^5, -10^9 <= ai <= 10^9示例1输入4
# 1 1 -1 1
# 输出3
# 最初少少在位置x=0处;他先依次使用前2个传送门，到达位置x=0+a1+a2=0+1+1=2，与初始位置距离为2。然后他使用技能“反转”，到达位置=-2与初始位置距离
# 为2，再使用第3个传送门，到达位置x=-2+a3=-2-1=-3，与初始距离为3。最后使用第4个传送门，到达位x=-3+a4=-3+1=-2与初始位置距离为2，在传送的过程
# 中，与初始位置距离最大为 3
# 示例2输入5
# 1 -4 10 -30 2
# 输出37
# 说明少少在使用过前3个传送门后到达x=7;此时使用一次“反转”，到达 x=-7;再使用第 4 个传送门到达x=-37，此时与初始位置距离最远为37
def main():
    import sys
    # input_data = sys.stdin.read().strip().split()
    # if not input_data:
    #     return
    # n = int(input_data[0])
    # a = list(map(int, input_data[1:]))
    n= int(input().strip())
    a=list(map(int, input().strip().split()))

    # 计算前缀和 P[0..n]，其中 P[0]=0
    P = [0]*(n+1)
    A = [0]*(n+1)  # A[k] = max(|P[0]|, ..., |P[k]|)
    P[0] = 0
    A[0] = abs(0)
    for i in range(1, n+1):
        P[i] = P[i-1] + a[i-1]
        A[i] = max(A[i-1], abs(P[i]))

    # 不使用反转时的答案
    ans = A[n]

    # 倒序计算后缀最小值和后缀最大值
    suffix_min = [0]*(n+1)
    suffix_max = [0]*(n+1)
    suffix_min[n] = P[n]
    suffix_max[n] = P[n]
    for i in range(n-1, -1, -1):
        suffix_min[i] = min(P[i], suffix_min[i+1])
        suffix_max[i] = max(P[i], suffix_max[i+1])

    # 枚举在第 k 个传送门后使用反转技能
    for k in range(0, n+1):
        # 使用反转后，后续每一步的位置为: P[j] - 2*P[k] (j>= k)
        # 区间[j=k, n]上最大的绝对值为：
        candidate2 = max( 2*P[k] - suffix_min[k], suffix_max[k] - 2*P[k] )
        candidate = max(A[k], candidate2)
        ans = max(ans, candidate)

    print(ans)

if __name__ == '__main__':
    main()
