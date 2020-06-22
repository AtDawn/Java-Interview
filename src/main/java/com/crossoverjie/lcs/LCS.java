package com.crossoverjie.lcs;

/**
 * @Author: wangjikui
 * @Description:
 * @Date: 15:08 01/17
 */
public class LCS {
    public static void main(String[] args) {
        System.out.println(lcsSubStr("abcvvvvv","vvvvv"));

    }

    /***
     * 这一部分我们使用辅助表，从左上角开始计算每一个位置上LCS的长度
     * 判断算法：
     */
    public static int[][] lcsLength(Object[] x, Object[] y) {
        int m = x.length;
        int n = y.length;
        int[][] c = new int[m + 1][n + 1];
        int i, j;
        for (i = 1; i <= m; i++) {
            c[i][0] = 0;
        }
        for (j = 0; j <= n; j++) {
            c[0][j] = 0;
        }
        for (i = 1; i <= m; i++) {
            for (j = 1; j <= n; j++) {
                if (x[i - 1].equals(y[j - 1])) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else if (c[i - 1][j] >= c[i][j - 1]) {
                    c[i][j] = c[i - 1][j];
                } else {
                    c[i][j] = c[i][j - 1];

                }
            }
        }
        return c;
    }

//print the lcs
//采用递归的方式将结果打印出来
    public static void printLcs(int[][] c, Object[] x, Object[] y, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (x[i - 1].equals(y[j - 1])) {
            printLcs(c, x, y, i - 1, j - 1);
            System.out.print(x[i - 1] + " ");
        } else if (c[i - 1][j] >= c[i][j - 1]) {
            printLcs(c, x, y, i - 1, j);
        } else {
            printLcs(c, x, y, i, j - 1);
        }
    }


    //求解str1 和 str2 的最长公共子序列
    public static int lcsSubStr(String str1, String str2) {
        int[][] c = new int[str1.length() + 1][str2.length() + 1];
        for (int row = 0; row <= str1.length(); row++) {
            c[row][0] = 0;
        }
        for (int column = 0; column <= str2.length(); column++) {
            c[0][column] = 0;
        }
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else if (c[i][j - 1] > c[i - 1][j]) {
                    c[i][j] = c[i][j - 1];
                } else {
                    c[i][j] = c[i - 1][j];
                }
            }
        }
        System.out.println(c[str1.length()][str2.length()]);
        return c[str1.length()][str2.length()];
    }


}
