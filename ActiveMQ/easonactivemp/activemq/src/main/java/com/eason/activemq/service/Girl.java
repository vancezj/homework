package com.eason.activemq.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Eason
 * @Date: 2020/8/22 - 08 - 22 - 0:22
 * @Description: eason.mq.active.test3
 * @version: 1.0
 */
public class Girl implements Serializable {
    private String name;
    private int age;
    private double price;

    public Girl(String name, int age, double price) {
        this.name = name;
        this.age = age;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", price=" + price +
                '}';
    }

    public static void main(String[] args) {
        Map<String, String> hashMap = new HashMap<>();
        System.out.println(hashMap.put("1", "oo"));
        System.out.println(hashMap.put("1", "xxoo"));
        System.out.println(hashMap.put("1", "xxoo2"));
        System.out.println("-----------------------------------");
        System.out.println(hashMap.putIfAbsent("2", "ssxx"));
        System.out.println(hashMap.putIfAbsent("2", "ssxx1"));
        System.out.println(hashMap.putIfAbsent("2", "ssxx2"));

    }
}
