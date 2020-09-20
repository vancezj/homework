package com.eason.eurekeconsumer.beans;

/**
 * @Auther: Eason
 * @Date: 2020/9/15 - 09 - 15 - 21:43
 * @Description: com.eason.eureka.beans
 * @version: 1.0
 */
public class Person {
    private String id;
    private String name;

    public Person() {
    }

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
