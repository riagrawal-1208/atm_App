# atm_App

API EndPoints -

1-Create Account
	Endpoint -  /atm/createAccount/
	Sample Request-{
				"userfname":"Richa",
				"userlname":"Agrawal",
				"aadharcardno":***********,
				"phonenumber":************,
				"acctype":"savings"
				}
	Sample Response - {
						"acc_no":100001,
						"card_no":123456,
						"pin":1234
						}

Note:- Taking account pin as base64Encoded with adding extra random 5 Characters before the pin entered by username ex-1234 it will be converted to *****1234 and then base64 encoded

2-Cash Deposit
	Endpoint -  /depositBalance/{acc_num}/{pin}/{balance}/ -  /depositBalance/100001/cmljaGExMjM0/1000
	
	Sample Response - 
		1-If Account number and pin does not match - "Account Number and Pin does not match"
		2-1-If Account number and pin match - "Account Balance Updated Successfully"

3-Cash Withdrawal
	
	Endpoint -  /withdrawBalance/{acc_num}/{pin}/{balance}/ -  /withdrawBalance/100001/cmljaGExMjM0/1000
	
	Sample Response -
		1-If Account number and pin does not match - "Account Number and Pin does not match"
		2-If transaction is successfull	- "Transaction Successfull"
		3-If withdrawal balance is less then account balance - "You does not have sufficient account balance"

4-Balance Enquiry
	Endpoint -  /getbalance/{acc_num}/{pin}/ -  /getbalance/100001/cmljaGExMjM0/
	
	Sample Response -
		1-If Account number and pin does not match - "Account Number and Pin does not match"
		2-If transaction is successfull	- {"bal":1000}
		
Steps to deploy - 
1-Download the project
2-Import it any of the IDE 
3-Run it on a server then 		
		
		
