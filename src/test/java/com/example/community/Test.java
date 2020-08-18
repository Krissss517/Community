package com.example.community;

import java.util.Scanner;

class TreeNode{
    private int val;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int val){
        this.left=null;
        this.right=null;
        this.val=val;
    }
}
public class Test {


    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);

        String n=sc.next();
        String m=sc.next();
        n.compareTo(m);



    }

    private static int calHigh(int n) {
        if(n==1||n==2){
            return 3;
        }
        int[] dp=new int[n+1];
        dp[1]=3;
        dp[2]=3;
        for (int i = 3; i <= n; i++) {
            dp[i]=dp[i-1]+dp[i-2];
        }
        int high=dp[n]*2;

        for(int i=n-1;i>=1;i--){
            high=(high+dp[i])*2;
        }

        return high;
    }
}
