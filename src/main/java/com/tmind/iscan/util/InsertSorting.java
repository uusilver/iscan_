package com.tmind.iscan.util;

import com.tmind.iscan.entity.System_meta_Info;

/**
 * Created by lijunying on 15/12/8.
 */
public class InsertSorting {

    public static void main(String []args){
        int a[] = {8,2,6,3,4};
        for(int i=1;i<a.length;i++){
            int posval = a[i];
            System.out.println(posval);
            int j=i-1;
            while(j>=0&&a[j]>posval){
                    //全体移动一位

                    a[j+1] = a[j];
                    System.out.println(a[j+1]+"--->"+a[j]);
                    //这里j--，所以下面得＋1 回到最开始
                    j--;

            }
            a[j+1] = posval;
            System.out.println(a[j+1]);
            for(Integer t:a){
                System.out.print(t+",");
            }
            System.out.println("");
            System.out.println("-----------------------");
        }
        for(Integer i:a){
            System.out.print(i+",");
        }
    }
}
