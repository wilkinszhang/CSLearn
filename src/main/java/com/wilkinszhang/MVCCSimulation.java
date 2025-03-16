package com.wilkinszhang;

import java.util.HashSet;
import java.util.Set;

public class MVCCSimulation {

    private static class ReadView {
        Set<Long> m_ids; // 未提交事务ID列表
        long maxLimitId; // 分配给下一个事务的ID
        long minLimitId; // 当前未提交事务最小ID

        public ReadView(Set<Long> m_ids, long maxLimitId, long minLimitId) {
            this.m_ids = new HashSet<>(m_ids);
            this.maxLimitId = maxLimitId;
            this.minLimitId = minLimitId;
        }

        public boolean isVersionVisible(long versionId, long creatorTrxId) {
            if (versionId < minLimitId) {
                return true; // 版本事务已提交
            }
            if (versionId >= maxLimitId) {
                return false; // 版本在read view创建后生成
            }
            if (m_ids.contains(versionId)) {
                return versionId == creatorTrxId; // 检查是否是当前事务创建
            }
            return true; // 事务在read view创建之前提交
        }
    }

    public static void main(String[] args) {
        // 示例数据
        Set<Long> m_ids = new HashSet<>();
        m_ids.add(102L);
        m_ids.add(103L);

        // 创建一个ReadView
        ReadView readView = new ReadView(m_ids, 105L, 101L);

        // 测试版本的可见性
        System.out.println("Version 100, Creator ID 100: " + readView.isVersionVisible(100L, 100L)); // true
        System.out.println("Version 102, Creator ID 101: " + readView.isVersionVisible(102L, 101L)); // false
        System.out.println("Version 104, Creator ID 104: " + readView.isVersionVisible(104L, 104L)); // false
        System.out.println("Version 101, Creator ID 101: " + readView.isVersionVisible(101L, 101L)); // true
    }
}