package com.wilkinszhang;

public class SubnetCalculator2 {

    public static String calculateSubnetAddress(String ip, String subnetMask) {
        try {
            // 将IP地址和子网掩码转换为长整型
            long ipLong = ipToLong(ip);
            long maskLong = ipToLong(subnetMask);

            // 通过位与运算计算子网地址
            long networkLong = ipLong & maskLong;

            // 将结果转换回点分十进制格式
            return longToIp(networkLong);
        } catch (Exception e) {
            return "Invalid input";
        }
    }

    /**
     * 将IP地址转换为长整型
     */
    private static long ipToLong(String ipAddress) {
        String[] ipArray = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result <<= 8;
            result |= Integer.parseInt(ipArray[i]);
        }
        return result;
    }

    /**
     * 将长整型转换为IP地址
     */
    private static String longToIp(long ip) {
        StringBuilder result = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            result.append((ip >> (i * 8)) & 0xFF);
            if (i > 0) {
                result.append(".");
            }
        }
        return result.toString();
    }

    /**
     * 主方法，包含使用示例
     */
    public static void main(String[] args) {
        String ip = "240.210.43.56";
        String subnetMask = "255.255.248.0";

        String subnetAddress = calculateSubnetAddress(ip, subnetMask);
        System.out.println("IP地址: " + ip);
        System.out.println("子网掩码: " + subnetMask);
        System.out.println("子网地址: " + subnetAddress);
    }
}
