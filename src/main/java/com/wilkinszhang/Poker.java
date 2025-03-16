package com.wilkinszhang;

import java.util.HashSet;

public class Poker {
    public static boolean isStraight(String[] hand) {
        HashSet<Integer> uniqueCards = new HashSet<>();
        int max = 0;
        int min = 14; // 牌的范围是1到13，14是一个大于所有牌的数

        for (String card : hand) {
            int value = getValue(card);
            if (value == 0) continue; // 跳过大小王
            if (uniqueCards.contains(value)) return false; // 重复牌
            uniqueCards.add(value);
            max = Math.max(max, value);
            min = Math.min(min, value);
        }

        return (max - min < 5); // 顺子的范围是5
    }

    private static int getValue(String card) {
        switch (card) {
            case "A": return 1;
            case "J": return 11;
            case "Q": return 12;
            case "K": return 13;
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            case "10": return 10;
            default: return 0; // 处理大小王或无效输入
        }
    }

    public static void main(String[] args) {
        String[] hand = {"A", "2", "3", "4", "5"}; // 示例手牌
        System.out.println(isStraight(hand) ? "是 顺子" : "不是顺子");
    }
}
