package com.wilkinszhang;

public class DrinkExchange {
    public static void main(String[] args) {
        int n = 18; // 假设你有 10 块钱
        int totalDrinks = calculateTotalDrinks(n);
        System.out.println("最多可以喝到 " + totalDrinks + " 瓶饮料");
    }

    public static int calculateTotalDrinks(int money) {
        int totalDrinks = money;
        int emptyBottles = money;

        while (emptyBottles >= 3) {
            int newDrinks = emptyBottles / 3;
            totalDrinks += newDrinks;
            emptyBottles = emptyBottles % 3 + newDrinks;
        }

        return totalDrinks;
    }
}