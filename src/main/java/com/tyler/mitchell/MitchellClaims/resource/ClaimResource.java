package com.tyler.mitchell.MitchellClaims.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import com.tyler.mitchell.MitchellClaims.model.MitchellClaimType;
import com.tyler.mitchell.MitchellClaims.service.ClaimService;


@Path("/claims")
public class ClaimResource {
	ClaimService service = new ClaimService();
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<MitchellClaimType> getClaims(){
		return service.getClaims();
	}

	@GET
	@Path("/id/{claimId}")
	@Produces(MediaType.APPLICATION_XML)
	public MitchellClaimType getClaim(@PathParam("claimId") String key){
		return service.getClaim(key);
	}
	
	@GET
	@Path("/startdate/{from}/enddate/{to}")
	@Produces(MediaType.APPLICATION_XML)
	public List<MitchellClaimType> getClaimsByDate(@PathParam("from") String from, @PathParam("to") String to) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = dateFormat.parse(from);
		Date d2 = dateFormat.parse(to);
		return service.searchByDate(d1, d2);
	}
	
	@POST
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String createClaim(MitchellClaimType m){
		if(service.addClaim(m))
			return "Success";
		return "Failed";
	}
	
	@PUT
	@Path("/id/{claimId}")
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateClaim(MitchellClaimType m, @PathParam("claimId") String key) throws JAXBException{
		if(service.updateClaim(m, key))
			return "Success";
		return "Failed!!!";
	}
	
	@DELETE
	@Path("/id/{claimId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteClaim(@PathParam("claimId") String key){
		if(service.deleteclaim(key))
			return "Success";
		return "Failed!!!";
	}
	
}

