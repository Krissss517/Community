package com.example.community;

import java.util.Arrays;

public class TestSort {

    public static void main(String[] args) {
        int[] arr={8,3,1,4,9,10,5,6,40,23,45,12};
        testQuick(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    static void testQuick(int[] temp,int begin,int end){
        //递归退出的条件
        if(begin>=end)
            return;
        //备份左索引和右索引
        int i=begin;
        int j=end;
        //基准数取第一位
        int index=temp[i];

        //当左索引和右索引碰撞就说明遍历完成
        while (i<j){
            //先从右边开始遍历，遍历到比基准数小为止
            while (i<j&&temp[j]>=index){
                j--;
            }
            //将右边比基准数小的 值赋给左索引，并且左索引++
            if(i<j){
                temp[i++]=temp[j];
            }
            //遍历左边，直到左索引上的值大于基准数
            while (i<j&&temp[i]<index){
                i++;
            }
            if(i<j){
                temp[j--]=temp[i];
            }
        }
        //最终把左索引上的值赋为基准数，实现基准数的左边都小于基准数，右边都大于等于该基准数
        temp[i]=index;
        //递归基准数左边的无序数组
        testQuick(temp,begin,i-1);
        //递归基准数右边的无序数组
        testQuick(temp,i+1,end);
    }
}
