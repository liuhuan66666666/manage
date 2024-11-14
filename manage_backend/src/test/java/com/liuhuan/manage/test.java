package com.liuhuan.manage;

import java.util.Scanner;

/**
 * @version 1.0
 * @Author liuhuan
 * @Date 2024/10/14 14:37
 * @注释
 */



public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int sum = 0;
        int count=0;
        for (int i = a; i <= b; i++) {
            if (i == 1) {
                continue; // 1不是素数，跳过
            }
            // 特殊判断2
            if(i==2){
                count++;
                sum+=2;
                continue;
            }
            int isPrime = 1;
            for (int j = 3; j <i; j++) {
                if (i % j == 0) {
                    isPrime = 0; // 如果可以被j整除，则i不是素数
                    break;
                }
            }
            if (isPrime==1) {
                count++;
                if(count%2==0){
                    sum-= i;
                }
                else{
                    sum+=i;
                }
            }
        }
        System.out.println(sum);
        scanner.close(); // 关闭scanner对象
    }
}