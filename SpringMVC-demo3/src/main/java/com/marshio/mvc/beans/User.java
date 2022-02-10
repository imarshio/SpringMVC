package com.marshio.mvc.beans;

import java.util.Arrays;

/**
 * @author masuo
 * @data 10/2/2022 下午2:45
 * @Description bean
 */

public class User {
    String name;
    String pwd;
    String sex;
    int age;
    String []hobby;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", hobby=" + Arrays.toString(hobby) +
                '}';
    }

    public User() {
    }

    public User(String name, String pwd, String sex, int age, String[] hobby) {
        this.name = name;
        this.pwd = pwd;
        this.sex = sex;
        this.age = age;
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getHobby() {
        return hobby;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }
}
