#!/usr/bin/env python3
import sys
# 现有一个会场拥有n *  m个座位，对应编号和分布如图所示：
# 1       2    。。。   m-1    m
# m+1     m+2  。。。   2m-1   2m
# 2m+1    2m+2 。。。   3m-1   3m
# 。       。            。    。
# 。       。            。    。
# 。       。            。    。
# m(n-1)+1 m(n-1)+2。。。nm-1  nm
# 第k排的座位编号范围为[m * (k-1)+1，m * k]。假设你已经拥有了来宾的身高hi和到场次序，作为主办方你需要保证来宾们的体验，对于任意来宾i,j的
# 身高hi < hj时需要保证座位编号si < sj。来宾入场时需要统一从每一排左侧走到自己的座位，但走过有人已经坐好的位置时会比较拥挤，来宾i感受到的拥挤
# 指数ci为走到自己座位前路过的其他来宾个数。请问在保证来宾体验的情况下，所有来宾感受到的拥挤指数之和最小为多少?输入描述第一行输入为n和m表示
# 有n排m列座位(1≤n,m< 300)第二行输入为n*m个整数表示按顺序到场的来宾的身高hi(1<=hi<=10^9)输出描述输出一个整数表示在保证来宾体验的情况下，所有
# 来宾拥挤指数之和的最小值示例1输入3 1
# 9 8 10
# 输出0
# 座位分布为3排一列，座位安排如下所示。每排只有一个位置，宾客感受到的拥挤指数总和为0
# 8
# 9
# 10
# 示例2输入1 3
# 10 33 1
# 输出1
# 座位分布为1排3列，座位安排如下所示。为了满足来宾的体验，必须座位安排必须按照下图所示。身高为10的来宾先到，之后身高为33的来宾会经过身高为10的
# 来宾从而感受到拥挤，最后身高为1的来宾坐在排头不会经过任何人没有感受到拥挤。所以最小的拥挤指数总和为1。
# 1    10     33
# 示例3输入3 3
# 3 4 4 1 1 1 1 1 2
# 输出4
# 座位分布为3排3列，座位安排如下所示。第一排:按身高为1的来宾的入场顺序从排尾依次入座，不会感受到拥挤。第二排:两个身高为1的来宾按入场先后入座第
# 二列和第一列，感受到的拥挤指数为0。身高为2的来宾入座最晚，会经过前面两个先入场的身高为1的来宾，拥挤指数为2。第三排:考虑来宾体验，身高为3的来
# 宾只能坐在第一排第一列，并且比两个身高为4的来宾先到。两个身高为4的来宾按入场先后入座第三列和第二列，他们感受到的拥挤值都是1。所以全场来宾的拥
# 挤指数最小总和为4。1  1  1
# 1  1  2
# 3  4  4
def main():
    import sys
    # 读取 n, m
    n, m = map(int, input().split())
    total = n * m

    tokens = []
    while len(tokens) < total:
        tokens += input().split()
    tokens = tokens[:total]

    # 记录每个来宾的 (height, arrival_index)
    guests = []
    for i in range(total):
        h = int(tokens[i])
        guests.append((h, i))  # i 为来宾到场的顺序(0-indexed)

    guests.sort(key=lambda x: (x[0], x[1]))

    # 将排号确定：分成 n 组，每组 m个人
    # rows[i] 表示第 i 排（0-index）的来宾信息列表
    rows = []
    for i in range(n):
        group = guests[i*m : (i+1)*m]
        # 在 group 中，同身高段的来宾 【原本顺序】 为到场顺序非降
        # 为了降低拥挤值，对于同一段身高相等的来宾，倒转这段顺序
        j = 0
        new_group = []
        while j < m:
            # 找到连续的相等身高段 [j, k)
            k = j + 1
            while k < m and group[k][0] == group[j][0]:
                k += 1
            # 翻转这段，注意：这样等身高来宾中，到场顺序较小的会被放到靠右（后面）的位置
            segment = group[j:k]
            segment.reverse()
            new_group.extend(segment)
            j = k
        rows.append(new_group)

    # 计算每一排的拥挤值
    # 方法：对于一排的座位（固定顺序为左到右 0 ~ m-1），统计：
    # 对于位置 j，其拥挤值 = 统计位置0 ~ j-1中有多少来宾到场顺序 < 当前来宾到场顺序
    total_cost = 0
    # 由于 m < 300，可以用简单两重循环来计算
    for row in rows:
        # 为每排，首先提取到场顺序数组
        arrival_list = [x[1] for x in row]
        # 对于每个位置，从左到右统计比当前 arrival_time 小的个数在前面的位置上
        for j in range(m):
            cnt = 0
            for k in range(j):
                if arrival_list[k] < arrival_list[j]:
                    cnt += 1
            total_cost += cnt
    print(total_cost)

if __name__ == '__main__':
    main()