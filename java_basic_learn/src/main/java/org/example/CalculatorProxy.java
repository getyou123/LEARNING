package org.example;

public class CalculatorProxy implements Calculator{
    CalculatorImpl target;
    public CalculatorProxy(CalculatorImpl target) {
        this.target = target;
    }
    @Override
    public int add(int a, int b) {
        System.out.println("Before add method");
        int result = target.add(a, b);
        System.out.println("After add method");
        return result;
    }

    public static void main(String[] args) {
        CalculatorImpl target = new CalculatorImpl();
        Calculator proxy = new CalculatorProxy(target);
        int result = proxy.add(1, 2);
        System.out.println("result:" + result);
    }
}
