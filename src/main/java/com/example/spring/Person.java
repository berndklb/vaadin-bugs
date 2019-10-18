package com.example.spring;

public class Person {

	private String name;
	private boolean overaged;
	private int age;
	private String city;
	
	
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public boolean isOveraged() {
		return overaged;
	}
	public void setOveraged(boolean overaged) {
		this.overaged = overaged;
	}
	
	
}
