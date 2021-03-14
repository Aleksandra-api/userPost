package com.userPosts.tests;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.userblog.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserBlogPutTest extends TestBase {
	
	
	@Test
	//Update the post with specific postId
	
	public void UpdateUserPost() {
		int postId=9;
		RequestSpecification request = RestAssured.given();
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("userId", 1);
		map.put("title", "Update the post title");
		map.put("body", "This is the new body for this post");
		JSONObject requestParams = new JSONObject();
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");	
		request.body(requestParams.toJSONString());
				
		// The actual request being passed to https://jsonplaceholder.typicode.com/posts/9
		// Here, we capture the response for PUT request by passing the associated postId in the baseURI
		Response response = request.put("/posts/"+ postId);
		//Validate the PUT request response received
		int statusCode = response.getStatusCode();
		System.out.println(response.asString());
		//When the request is send, the status code is assert to be 200
		Assert.assertEquals(statusCode, 200);
		
	}
	
	@Test
	//Update post for userId for which that postId doesn't exist
	public void UpdateNonExistingPost() {
		int postId=8;
		RequestSpecification request = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("userId", 3);
		map.put("title", "Non-existing post");
		map.put("body", "For this userId the postId=8 is non existing");
		request.header("Content-Type", "application/json");	
		request.body(requestParams.toJSONString());
		Response response = request.put("/posts/"+ postId);
		int statusCode = response.getStatusCode();
		System.out.println(response.asString());
		//When the request is send, the status code is assert to be 400 because for userId:3 the post with postId:3 is not exist
		Assert.assertEquals(statusCode, 400);
	}

}
