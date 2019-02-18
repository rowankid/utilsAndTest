package com.example.demo;

import org.junit.Test;

/**
 * @Author: rowan
 * @Date: 2019/2/18 15:33
 * @Description:
 */
public class Feb1801Test {

    @Test
    public void test01(){
        /**
         * break result:
         * 0 and 0
         * 1 and 0
         *
         * continue result:
         * 0 and 0
         * 1 and 0
         * 0 and 1
         * 1 and 1
         * 0 and 2
         * 1 and 2
         */
        Outer: for(int i=0;i<3;i++){
            Inner: for (int j=0;j<3;j++){
                if(j>1) continue Outer;
                System.out.println(j+" and "+i);
            }
        }
    }

    @Test
    public void test02(){
        int m =2;
        switch (m){
            case 0:
                System.out.println("case 0");
            case 1:
                System.out.println("case 1");
                break;
            case 2:
            default:
                System.out.println("default");
        }
    }
}