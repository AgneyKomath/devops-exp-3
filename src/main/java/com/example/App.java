package com.example;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello from myapp!");
    }

    public boolean isPrime(int n){
        if (n <= 1) return false;
        for(int i = 2; i * i <= n; i++){
            if(n % i == 0) return false;
        }
        return true;
    }
}