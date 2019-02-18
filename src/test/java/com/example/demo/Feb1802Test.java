package com.example.demo;

import org.junit.Test;

/**
 * @Author: rowan
 * @Date: 2019/2/18 15:33
 * @Description:
 */
public class Feb1802Test {

    class b {
        int n=1;
        void disp(){
            System.out.println(n);
        }
    }
    class c extends b{
        int n = 2;

        @Override
        void disp() {
            super.disp();
            System.out.println(super.n);
            System.out.println(n);
        }
    }

    @Test
    public void test01(){
        (new c()).disp();
    }
}