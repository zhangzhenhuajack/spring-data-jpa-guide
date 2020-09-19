package com.example.redis;

public class CacheableTest {
    static class A {}

    static class B extends A {}

    public static void main(String[] args) {
        B b = new B();

        A a = new A();

        A ba = new B();
        A bb = b;

        System.out.println(b instanceof A);
    }

}
