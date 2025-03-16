# 几近缺失整数 https://leetcode.cn/contest/weekly-contest-439/problems/find-the-largest-almost-missing-integer/?slug=sum-of-k-subarrays-with-length-at-least-m&region=local_v2
from collections import defaultdict
from typing import List

class Solution:
    def largestInteger(self, nums: List[int], k: int) -> int:
        # 记录每个数在所有子数组中出现的次数
        count = defaultdict(int)

        # 滑动窗口，遍历所有大小为 k 的子数组
        for i in range(len(nums) - k + 1):
            for j in range(i, i + k):
                count[nums[j]] += 1

        # 寻找只出现在一个子数组中的元素，并获取最大值
        result = -1
        for num in count:
            if count[num] == 1:
                result = max(result, num)
        myMax=nums[0]
        for i in range(len(nums)):
            if nums[i]>myMax:
                myMax=nums[i]
        if k==len(nums):
            return myMax

        return result

nums = [0,0]
k = 2
solution = Solution()
print(solution.largestInteger(nums, k))