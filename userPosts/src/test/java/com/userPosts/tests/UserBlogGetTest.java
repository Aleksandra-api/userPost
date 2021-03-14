package com.userPosts.tests;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Enumeration;
import java.util.regex.*;
//import java.util.*;    
import org.testng.Assert;
import org.testng.annotations.Test;

import com.userblog.base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserBlogGetTest extends TestBase {
		
	
	@Test
		// This test is search for all the usernames that exist and displayed in array and after that search and check does the username “Delphine” exist in the users JSON
		public void GetUsername() {
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.request(Method.GET, "/users");
			// First get the JsonPath object instance from the Response interface
			 JsonPath jsonPathEvaluator = response.jsonPath();			 
			// Retrieve the body of the Response
			ArrayList <String> usernames = new ArrayList<String> ();
		    usernames = jsonPathEvaluator.get("username");
			 System.out.println("The usernames returns in the response: " + usernames);
			 Assert.assertEquals(usernames.contains("Delphine") /*Expected value*/, true /*Actual Value*/, "Response body contains Delphine");
			 Response response1 = httpRequest.request(Method.GET, "/users/9");
			 String responseBody1 = response1.getBody().asString();
			 System.out.println("Information for Delphine users are: " + responseBody1);
			 
		}
		
		@Test
		//This test return all available posts, and check the response by status code, status line and content type
		public void GetAllPosts() {
			//RestAssured return the Request from the the baseUri. Each request is represent by an interface called RequestSpecification
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.request(Method.GET, "/posts");	
			String responseBody = response.getBody().asString();
			
			System.out.println("Response Body is: " + responseBody);
			
			//Response object contains Status, Headers and Body
			int statusCode = response.getStatusCode();
			//Assert that the correct status is return
			Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
			System.out.println("Status code => " + statusCode);
			
			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine /*actual value*/,"HTTP/1.1 200 OK" /*expected value*/, "Correct status line returned");
			System.out.println("Status line => " + statusLine);
			
			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType /* actual value */, "application/json; charset=utf-8" /* expected value */);
			System.out.println("Content-Type value: " + contentType);
			
		}	
		
		//Negative testing for posts - try to return post which is not exist and verify that the return status code is 404
		@Test
		public void GetPostwhichDoesNotExist() {
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.get ("/posts/123");	
			String responseBody = response.getBody().asString();
			System.out.println("Response Body is: " + responseBody);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode /*actual value*/, 404 /*expected value*/, "Correct status code returned");
			System.out.println("Status code => " + statusCode);
		}
		
		@Test
		//Search for posts written by specific user
		public void GetAllPostsFromSpecificUser() {
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.get("/users/9/posts");
			String responseBody = response.getBody().asString();
			System.out.println("The posts added from userId:9 " + responseBody);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
			System.out.println("Status code => " + statusCode);
			
		}
		@Test
		//Get all the comments for all post
		public void GetAllCommentsForAllPosts() {
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.get("/comments");
			String responseBody = response.getBody().asString();
			System.out.println("All comments for all posts " + responseBody);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
		    System.out.println("Status code => " + statusCode);
		
		}
		@Test
		//Validate Response Body contains some String from the exact node in the posts (ex. "title": "non est facere")
		public void PostMessageBody() {
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.get("/posts");
			JsonPath jsonPathEvaluator = response.jsonPath();
			ArrayList <String> titles = new ArrayList<String> ();
			titles= jsonPathEvaluator.get("title");
			Assert.assertEquals(titles.contains("non est facere") /*Expected value*/, true /*Actual Value*/, "Response body contains the searched title");
		}
		
		@Test
		//Return the comments for specific postId
		public void GetTheCommentsForOnePost() {
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.get("/posts/1/comments");
			String responseBody = response.getBody().asString();
			System.out.println("The comments for postId:1: " + responseBody);
		}
			
			
			
			@Test
			//Return all the e-mails from the comments and verify which of them are valid which of them are invalid
			public void ValidateEmailFormat() {
				RequestSpecification httpRequest = RestAssured.given();
				Response response = httpRequest.get("/comments");	
				JsonPath jsonPathEvaluator = response.jsonPath();
				
				ArrayList <String> emails = new ArrayList<String> ();
			    emails = jsonPathEvaluator.get("email");
				System.out.println("The emails returns in the response: " + emails);
				 //Regular Expression 
				String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@"+"(?:[a-zA-Z0-9-]+\\.)+[a-z"+"A-Z]{2,7}$";		        
		        //Compile regular expression to get the pattern  
		        Pattern pattern = Pattern.compile(regex);
		        for (String email: emails) {
		        	//Create instance of matcher   
		            Matcher matcher = pattern.matcher(email);  
		            System.out.println(email +" : "+ matcher.matches()+"\n"); 
	     	
		        }		      
						
}
	
}
		
		
		
	

	
			
			
		


