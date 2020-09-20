package eason.mq.active.test3;

import java.io.Serializable;

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
}
