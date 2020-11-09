package com.atm.test;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.atm.app.application;

public class Application_Test extends JerseyTest  {
	@Override
    protected Application configure() {
        return new ResourceConfig(application.class);
    } 
	@Test
	public void givenWithdrawtBalance() {
	    Response response = target("/withdrawBalance/100001/cmljaGExMjM0/1000/").request()
	        .get();
	 
	    assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	 
	    String content = response.readEntity(String.class);
	    assertEquals("Content of ressponse is: ", "hi", content);
	}
	@Test
	public void givenGetBalance() {
	    final String json = target("/getbalance/100001/cmljaGExMjM0/").request()
	        .get(String.class);
	    assertThat(json, containsString("{\"bal\":1000}"));
	}
	@Test
	public void givendepositBalance() {
	    Response response = target("/depositBalance/100001/cmljaGExMjM0/1000/").request()
	        .get();
	 
	    assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}
	@Test
	public void givenCreateAccount() {
	    Response response = target("/atm/createAccount/").request()
	        .post(Entity.json("{\"userfname\":\"richa\",\"userlname\":\"Agrawal\",\"aadharcardno\":000000000000 ,\"phonenumber\":1234567890, \"acctype\":\"savings\"}"));
	   
	 	    assertEquals("Http Response should be 201 ", Status.CREATED.getStatusCode(), response.getStatus());
	    assertThat(response.readEntity(String.class), containsString("{\"acc_no\":100001,\"card_no\":123456,\"pin\":1234 }"));
	}
}
