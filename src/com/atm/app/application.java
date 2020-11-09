package com.atm.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.atm.connect.*;

@Path("/atm")
@Encoded
public class application {
	ResultSet rs = null;
	connection con = new connection();

	@GET
	@Path("getbalance/{acc_num}/{pin}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response balanceEnquiry(@PathParam("acc_num") int acc_num, @PathParam("pin") String pin) {
		int int1 = getPin(pin);
		String query = "SELECT balance FROM BANK_ACCOUNT where acc_no=" + acc_num + "and pin =" + int1;
		int bal = 0;
		ResultSet rs = con.executeQuery(query);
		try {
			if (!rs.isBeforeFirst()) {
				return Response.status(200).entity("Account Number and Pin does not match").build();
			}
			while (rs.next()) {
				bal = rs.getInt("balance");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.closeConnection(rs);
		}
		return Response.status(200).entity(bal).build();
	}

	@GET
	@Path("withdrawBalance/{acc_num}/{pin}/{balance}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response balanceWithdrawal(@PathParam("acc_num") int acc_num, @PathParam("pin") String pin,
			@PathParam("balance") int amt) {
		int int1 = getPin(pin);
		String query = "SELECT balance FROM BANK_ACCOUNT where acc_no=" + acc_num + "and pin =" + int1;
		int bal = 0;
		ResultSet rs = con.executeQuery(query);
		try {
			if (!rs.isBeforeFirst()) {
				return Response.status(200).entity("Account Number and Pin does not match").build();
			}
			while (rs.next()) {
				bal = rs.getInt("balance");
			}
			if (bal > amt) {
				int remainingBalance = bal - amt;
				String updateQuery = "update BANK_ACCOUNT set balance = " + remainingBalance + "where acc_no="
						+ acc_num;
				con.executeUpdate(updateQuery);
				String updateTransactionDetails = "Insert into WITHDRAW (acc_no,amount,date,time) values(" + acc_num
						+ "," + amt + "," + java.time.LocalDate.now() + "," + new Timestamp(System.currentTimeMillis())
						+ ")";
				con.executeUpdate(updateTransactionDetails);
			} else {
				return Response.status(200).entity("You does not have sufficient account balance").build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.closeConnection(rs);
		}
		return Response.status(200).entity("Transaction Successfull").build();
	}

	@GET
	@Path("depositBalance/{acc_num}/{pin}/{balance}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response depositBalance(@PathParam("acc_num") int acc_num, @PathParam("pin") String pin,
			@PathParam("balance") int amt) {
		int int1 = getPin(pin);

		String query = "SELECT balance FROM BANK_ACCOUNT where acc_no=" + acc_num + "and pin =" + int1;
		int bal = 0;
		ResultSet rs = con.executeQuery(query);
		try {
			if (!rs.isBeforeFirst()) {
				return Response.status(200).entity("Account Number and Pin does not match").build();
			}
			while (rs.next()) {
				bal = rs.getInt("balance");
			}
			int totalBalance = bal + amt;
			String updateQuery = "update BANK_ACCOUNT set balance = " + totalBalance + "where acc_no=" + acc_num;
			con.executeUpdate(updateQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.closeConnection(rs);
		}
		return Response.status(200).entity("Account Balance Update Successfully").build();
	}

	@SuppressWarnings("static-access")
	public Integer getPin(String pin) {
		// will get pin as base64 encoded adding 5 random characters before the
		// actual pin will decode it here json
		byte[] decodedBytes = Base64.getDecoder().decode(pin);
		String decodePin = new String(decodedBytes);
		decodePin = decodePin.substring(5, decodePin.length());
		Integer int1 = new Integer(5);
		int1.decode(decodePin);
		return int1;

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("createAccount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(String reqbody) {
		JSONParser parser = new JSONParser();
		JSONObject dtls = new JSONObject();
		JSONObject object = new JSONObject();

		try {
			dtls = (JSONObject) parser.parse(reqbody);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String fname = this.getInputValue(dtls, "userfname");
		String lname = this.getInputValue(dtls, "userlname");
		int aadharcard = (int)dtls.get("aadharcardno");
		int phonenumber = (int) dtls.get("phonenumber");
		String acctype=  this.getInputValue(dtls, "acctype");
		String createacc ="Insert into (acc_no,card_no,pin,FNAME,LNAME,ACC_TYPE,AADHAR_NO,PHONE_NO,date,time) values ( ACC_SEQUENCE.NEXTVAL , CARD_SEQUENCE.NEXTVAL ,PIN_SEQUENCE.NEXTVAL" +fname+","+lname+","+acctype+","+aadharcard+","+phonenumber
				+","+java.time.LocalDate.now()+","+ new Timestamp(System.currentTimeMillis())+")";
		ResultSet rs = con.createAccount(createacc);
		if(rs==null){
			return Response.status(200).entity("Account Creation Failed").build();

		}else{
		try {
			if(rs.next()){
				object.put("acc_no",rs.getInt(1));
				object.put("card_no",rs.getInt(9));
				object.put("pin",rs.getInt(10));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).entity(object.toString()).build();
		}
		
	}

	/**
	 * getting values of key whose values are of string type in a JSONObject
	 */
	public String getInputValue(JSONObject dtls, String key) {
		if ((dtls.containsKey(key)) && (dtls.get(key) != null)) {
			if ("[]".equals(dtls.get(key).toString())) {
				return null;
			} else {
				return dtls.get(key).toString();
			}
		} else {
			return null;
		}
	}
}
