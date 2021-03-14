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

public class UserBlogPostTest extends TestBase {
	
	@Test
	//Create new post
	public void createNewPost() {
		
		RequestSpecification request = RestAssured.given();
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("userId", 2);
		map.put("title", "New Test Post");
		map.put("body", "Create new post for testing");
		
		JSONObject requestParams  = new JSONObject();
		System.out.println(requestParams.toJSONString());
		//Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		//Add the JSON to the body of the request
		request.body(requestParams.toJSONString());
		Response response = request.post("/posts");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, "201");
		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals( "Correct Success code was returned", successCode, "OPERATION_SUCCESS");
			
		}
	@Test
	//Create post with user which is not register, it doesn't exist in the users list (the userId is not exist or is invalid)
	public void createNewPostWithInvalidUserId() {
		RequestSpecification request = RestAssured.given();
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("userId", 15);
		map.put("title", "New Test Post with user which is nor existing");
		map.put("body", "Create new post for testing with userId which is not existing");
	    JSONObject requestParams  = new JSONObject();
		System.out.println(requestParams.toJSONString());
		request.header("Content-Type", "application/json");
		//Add the JSON to the body of the request
		request.body(requestParams.toJSONString());
		Response response = request.post("/posts");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, "404");
		String FaultId = response.jsonPath().get("FaultId");
		Assert.assertEquals( "Fault code was returned", FaultId, "The userId is not exist");
	}
	
	@Test
	//Create new comment
	public void createNewComment() {
		
		RequestSpecification request = RestAssured.given();
		Map <String, Object> map = new HashMap<String, Object>();
	    map.put("postId", 1);
	    map.put("name", "New comment");
        map.put("email", "test123@test.com");
	    map.put("body", "This comment is reffered for comment with postId:1");
	    JSONObject requestParams  = new JSONObject();
		System.out.println(requestParams.toJSONString());
		request.header("Content-Type", "application/json");
		//Add the JSON to the body of the request
		request.body(requestParams.toJSONString());
		Response response = request.post("/comments");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, "201");
		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals( "Correct Success code was returned", successCode, "OPERATION_SUCCESS");
	    
		
	}
	
	@Test
	//Create new comment with invalid e-mail address
	public void createNewCommentWithInvalidEmailAddress() {
		
		RequestSpecification request = RestAssured.given();
		Map <String, Object> map = new HashMap<String, Object>();
	    map.put("postId", 2);
	    map.put("name", "New comment with invalid e-email address");
        map.put("email", "test12test.com");
	    map.put("body", "This comment is doesn't have valid e-mail address");
	    JSONObject requestParams  = new JSONObject();
		System.out.println(requestParams.toJSONString());
		request.header("Content-Type", "application/json");
		//Add the JSON to the body of the request
		request.body(requestParams.toJSONString());
		Response response = request.post("/comments");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, "201");
		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals( "Correct Success code was returned", successCode, "OPERATION_SUCCESS");
	
	}
	
	@Test
	//Create comment for postId which is invalid
	public void createNewCommentWithInvalidPostId() {
		RequestSpecification request = RestAssured.given();
		Map <String, Object> map = new HashMap<String, Object>();
	    map.put("postId", 123);
	    map.put("name", "New comment with invalid postId");
        map.put("email", "test12@test.com");
	    map.put("body", "This comment should not be added because this postId is not exist");
	    JSONObject requestParams  = new JSONObject();
		System.out.println(requestParams.toJSONString());
		request.header("Content-Type", "application/json");
		//Add the JSON to the body of the request
		request.body(requestParams.toJSONString());
		Response response = request.post("/comments");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, "404");
		String FaultId = response.jsonPath().get("FaultId");
		Assert.assertEquals( "Fault code was returned", FaultId, "The invalid postId");
	}
		
		
	}


