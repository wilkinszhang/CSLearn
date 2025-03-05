# 菜鸟笔试，难度适中（1115秋招笔试真题解析）
def can_dye_red(n, s):
    # 初始状态是没有遇到任何需要的字符
    found_r = False
    found_e = False
    found_d = False

    for char in s:
        if char == 'r':
            # 找到 'r' 后，我们可以继续寻找 'e' 和 'd'
            found_r = True
        elif char == 'e' and found_r:
            # 如果已经找到 'r'，再找到 'e'
            found_e = True
        elif char == 'd' and found_e:
            # 如果已经找到 'e'，再找到 'd'
            found_d = True

    # 如果找到了 'r', 'e', 'd' 且满足顺序，返回 "YES"
    if found_r and found_e and found_d:
        return "YES"
    else:
        return "NO"

# 读取输入
T = int(input())  # 测试用例数
for _ in range(T):
    n = int(input())  # 字符串的长度
    s = input().strip()  # 字符串本身
    print(can_dye_red(n, s))
