Tyler Nguyen
Coding Challenge
Overview:
	This is a RESTFUL Web Services built using Eclipse, Maven, Jersey, Postman,, Tomcat, and PostgreSQL.
	This is the Claim Webservice that can:

1.	GET a claim:
	Return a claim in XML format base on claim number ID.
1.1) Format URL:
	http://localhost:8080/MitchellClaims/webapi/claims/id/{claimnumber}
1.2) Resource information:
	XML
1.3) Example request:
	http://localhost:8080/MitchellClaims/webapi/claims/id/22c9c23bac142856018ce14a26b6c299
1.4) Result:
	information of a claim by claim number ID in XML

2.	POST a claim:
	Create and store it in the database a  new claim by giving a XML file of a claim.
2.1) Format URL:
	http://localhost:8080/MitchellClaims/webapi/claims
2.2) Resource information:
	XML
2.3) Example request:
	http://localhost:8080/MitchellClaims/webapi/claims
2.4) Result:
	Output a string Success or Failed

3.	GET claims within date range:
	Return claims in XML format that are in a range of dates.
3.1) Format URL:
	http://localhost:8080/MitchellClaims/webapi/claims/startdate/2014-01-01/enddate/2015-12-01
3.2) Resource information
	XML
3.3) Example request:
	http://localhost:8080/MitchellClaims/webapi/claims/startdate/2014-01-01/enddate/2015-12-01
3.4) Result:
	XML file contains all the claim in the range of the giving dates.

4.	UPDATE claim:
	Update and store a claim into the database by giving a XML file of a claim.
4.1) Format URL:
	http://localhost:8080/MitchellClaims/webapi/claims/id/{claimnumber}
4.2) Resource information:
	XML
4.3) Example request:
	http://localhost:8080/MitchellClaims/webapi/claims/id/22c9c23bac142856018ce14a26b6c299
4.4) Result:
	Output a string Success or Failed.
5.	DELETE claim:
	Delete a claim from the database by giving a claim number id.
5.1) Format URL:
	http://localhost:8080/MitchellClaims/webapi/claims/id/{claimnumber}
5.2) Resource information:
	XML
5.3) Example request:
	http://localhost:8080/MitchellClaims/webapi/claims/id/22c9c23bac142856018ce14a26b6c299
5.4) Result:
	Output a string Success or Failed
