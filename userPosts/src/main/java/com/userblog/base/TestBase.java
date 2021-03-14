package com.userblog.base;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public class TestBase {
	
@BeforeClass
	
	public static void init() {
		RestAssured.baseURI="https://jsonplaceholder.typicode.com/";
	}



}
