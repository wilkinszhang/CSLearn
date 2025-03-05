# 菜鸟笔试，难度适中（1115秋招笔试真题解析）
# 读入输入
n, k, w = map(int, input().split())

# 计算模数
mod = 10 ** w

# 快速幂求 n^k mod mod
def mod_exp(n, k, mod):
    result = 1
    n = n % mod
    while k > 0:
        if k & 1:
            result = (result * n) % mod
        n = (n * n) % mod
        k //= 2
    return result

# 计算答案
ans = mod_exp(n, k, mod)

# 将答案转换为字符串并补齐前导0
ans_str = str(ans).zfill(w)
print(ans_str)
