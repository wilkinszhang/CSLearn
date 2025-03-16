package com.wilkinszhang;

import java.util.Scanner;

public class SubnetCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a C class IP address: ");
        String cClassIP = scanner.nextLine();

        System.out.print("Enter the number of additional subnet bits (between 1 and 8): ");
        int subnetBits = scanner.nextInt();

        if (subnetBits < 1 || subnetBits > 8) {
            System.out.println("Invalid number of bits. Must be between 1 and 8.");
            System.exit(2);
        }

        // 计算子网数量
        int subnets = (int) Math.pow(2, subnetBits);
        // 计算每个子网的主机数量
        int hostsPerSubnet = (int) Math.pow(2, 8 - subnetBits) - 2; // 减去2个地址（网络地址和广播地址）

        // 输出子网数量和每个子网的主机数量
        System.out.printf("For the C class IP address %s with %d additional subnet bits:\n", cClassIP, subnetBits);
        System.out.println("Number of subnets: " + subnets);
        System.out.println("Hosts per subnet: " + hostsPerSubnet);
    }
}