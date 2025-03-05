# 菜鸟笔试，难度适中（1115秋招笔试真题解析）
def can_reach_positions(n, k, s):
    """
    Determine if each position on the number line can be reached.

    Args:
        n: Number of points on the number line (1 to n)
        k: Current position (1-indexed)
        s: Instruction string with 'L', 'R', and '?'

    Returns:
        List of booleans indicating if each position can be reached
    """
    # Convert to 0-indexed for easier calculations
    k = k - 1

    # Calculate leftmost and rightmost positions when replacing ? with L or R
    min_left = k
    max_right = k

    # First pass: replace ? with L to find leftmost position
    position = k
    for char in s:
        if char == 'R':
            position += 1
        else:  # char is 'L' or '?' (both treated as 'L' for min calculation)
            position -= 1
        min_left = min(min_left, position)

    # Second pass: replace ? with R to find rightmost position
    position = k
    for char in s:
        if char == 'L':
            position -= 1
        else:  # char is 'R' or '?' (both treated as 'R' for max calculation)
            position += 1
        max_right = max(max_right, position)

    # For each position, check if it's within the reachable range
    result = []
    for i in range(n):
        can_reach = min_left <= i <= max_right
        if can_reach==True:
            result.append('1')
        else:
            result.append('0')

    return result

# 读取输入
n, k = map(int, input().split())
s = input().strip()

# 输出结果
print(can_reach_positions(n, k, s))