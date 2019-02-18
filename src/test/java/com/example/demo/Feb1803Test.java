package com.example.demo;

import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

/**
 * @Author: rowan
 * @Date: 2019/2/18 15:53
 * @Description:
 */
public class Feb1803Test {

    class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("before run");
            super.run();
            System.out.println("after run");
        }

        void disp() {
            System.out.println("disp");
        }

        @Override
        public synchronized void start() {
            System.out.println("before start");
            super.start();
            System.out.println("end start");

        }
    }

    @Test
    public void test01() {
        MyThread mt = new MyThread();
        mt.disp();
        mt.start();
    }

    @Test
    public void test02() {
        HttpSession session = new HttpSession() {
            @Override
            public long getCreationTime() {
                return 0;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public long getLastAccessedTime() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public void setMaxInactiveInterval(int i) {

            }

            @Override
            public int getMaxInactiveInterval() {
                return 0;
            }

            @Override
            public HttpSessionContext getSessionContext() {
                return null;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Object getValue(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String[] getValueNames() {
                return new String[0];
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void putValue(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public void removeValue(String s) {

            }

            @Override
            public void invalidate() {

            }

            @Override
            public boolean isNew() {
                return false;
            }
        };
        session.setAttribute("1", "2");
        session.setAttribute("1", "3");
        String attribute = (String) session.getAttribute("1");
        System.out.println(attribute);
    }


}