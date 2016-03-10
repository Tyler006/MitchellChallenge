package com.tyler.mitchell.MitchellClaims.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tyler.mitchell.MitchellClaims.model.LossInfoType;
import com.tyler.mitchell.MitchellClaims.model.MitchellClaimType;
import com.tyler.mitchell.MitchellClaims.model.VehicleInfoType;

public class ClaimService {
	private Connection connection = null;

	public void establishConnection(){
		System.out.println("——– PostgreSQL " + "JDBC Connection Testing ————");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Failed!!!");
			e.printStackTrace();
			return;
		}
		System.out.println("PostgreSQL JDBC Driver Registered!");

		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MitchellDB", "postgres","2711991");
		} catch (SQLException e) {
			System.out.println("Connection Failed!!!");
			e.printStackTrace();
			return;
		}
		System.out.println("Database Connected!");
	}

	public boolean addClaim(MitchellClaimType claim){
		establishConnection();
		Statement stmt;
		try {
			StringWriter sw = new StringWriter();
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(MitchellClaimType.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.marshal(claim, sw);
				String xmlString = sw.toString();
				stmt = connection.createStatement();
				String query = "INSERT INTO mitchellclaims(claimnumber, claiminfo) VALUES( '"+claim.getClaimNumber()+"', '"+xmlString+"' )";
				stmt.executeUpdate(query);
				System.out.println("executed insert query!");
				System.out.println("Connection closed");
				connection.close();
			} catch (JAXBException e) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public List<MitchellClaimType> getClaims(){
		List<MitchellClaimType> claims = new ArrayList<MitchellClaimType>();
		establishConnection();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "SELECT claiminfo FROM mitchellclaims";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("executed get query!");
			JAXBContext jaxbContext;
			while(rs.next()){
				String m = rs.getString("claiminfo");
				try {
					jaxbContext = JAXBContext.newInstance(MitchellClaimType.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					StringReader reader = new StringReader(m);
					MitchellClaimType claim = (MitchellClaimType) jaxbUnmarshaller.unmarshal(reader);
					claims.add(claim);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Connection closed");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return claims;
	}

	public MitchellClaimType getClaim(String key){
		establishConnection();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "SELECT claiminfo FROM mitchellclaims WHERE claimnumber ='"+key+"'";
			System.out.println("executed get query!");
			ResultSet rs = stmt.executeQuery(query);
			String m = "";
			while(rs.next()){
				m = rs.getString("claiminfo");
			}
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(MitchellClaimType.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(m);
				MitchellClaimType claim = (MitchellClaimType) jaxbUnmarshaller.unmarshal(reader);
				System.out.println("Connection closed");
				connection.close();
				return claim;
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new MitchellClaimType();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new MitchellClaimType();
		}
	}

	public List<MitchellClaimType> searchByDate(Date from, Date to) {
		List<MitchellClaimType> claimArray = getClaims();
		List<MitchellClaimType> result = new ArrayList<>();
		for(int i =0; i< claimArray.size();i++){
			Date day = toDate(claimArray.get(i).getLossDate());
			if(isWithinDateRange(day, from, to)){
				result.add(claimArray.get(i));
			}
		}
		return result;
	}

	public Date toDate(XMLGregorianCalendar day){
		return day.toGregorianCalendar().getTime();
	}
	
	boolean isWithinDateRange(Date sample, Date startDate, Date endDate){
		return sample.after(startDate) && sample.before(endDate);
	}

	public boolean updateClaim(MitchellClaimType claim, String key) throws JAXBException{
		establishConnection();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "SELECT claiminfo FROM mitchellclaims WHERE claimnumber='"+key+"'";
			ResultSet rs = stmt.executeQuery(query);
			String m = "";
			while(rs.next()){
				m = rs.getString("claiminfo");
			}
			JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(MitchellClaimType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(m);
			MitchellClaimType currentClaim = (MitchellClaimType) jaxbUnmarshaller.unmarshal(reader);
			if(claim.getClaimantFirstName() != null) currentClaim.setClaimantFirstName(claim.getClaimantFirstName());
			if(claim.getClaimantLastName() != null) currentClaim.setClaimantLastName(claim.getClaimantLastName());
			if(claim.getStatus() != null) currentClaim.setStatus(claim.getStatus());
			if(claim.getLossDate() != null) currentClaim.setLossDate(claim.getLossDate());
			if(claim.getLossInfo() != null){
				LossInfoType lossInfo = claim.getLossInfo();
				if(lossInfo.getCauseOfLoss() != null) currentClaim.getLossInfo().setCauseOfLoss(lossInfo.getCauseOfLoss());
				if(lossInfo.getLossDescription() != null) currentClaim.getLossInfo().setLossDescription(lossInfo.getLossDescription());
				if(lossInfo.getReportedDate() != null) currentClaim.getLossInfo().setReportedDate(lossInfo.getReportedDate());
				currentClaim.setLossInfo(claim.getLossInfo());
				}
			if(claim.getAssignedAdjusterID() != null) currentClaim.setAssignedAdjusterID(claim.getAssignedAdjusterID());
			if(claim.getVehicles() != null) {
				ArrayList<VehicleInfoType> vehicles = (ArrayList<VehicleInfoType>) claim.getVehicles().getVehicleDetails();//claim.getVehicles().getVehicleDetails();
				for(int i = 0; i < vehicles.size(); i++){
					VehicleInfoType vehicleInfo = currentClaim.getVehicles().getVehicle(vehicles.get(i).getVin());
					if(vehicles.get(i).getModelYear() != 0) { vehicleInfo.setModelYear(vehicles.get(i).getModelYear());}
					if(vehicles.get(i).getMakeDescription() != null) { vehicleInfo.setMakeDescription(vehicles.get(i).getMakeDescription());}
					if(vehicles.get(i).getModelDescription() != null) { vehicleInfo.setModelDescription(vehicles.get(i).getModelDescription());}
					if(vehicles.get(i).getEngineDescription() != null) { vehicleInfo.setEngineDescription(vehicles.get(i).getEngineDescription());}
					if(vehicles.get(i).getExteriorColor() != null) { vehicleInfo.setExteriorColor(vehicles.get(i).getExteriorColor());}
					if(vehicles.get(i).getLicPlate() != null) { vehicleInfo.setLicPlate(vehicles.get(i).getLicPlate());}
					if(vehicles.get(i).getLicPlateState()!= null) { vehicleInfo.setLicPlateState(vehicles.get(i).getLicPlateState());}
					if(vehicles.get(i).getLicPlateExpDate() != null) { vehicleInfo.setLicPlateExpDate(vehicles.get(i).getLicPlateExpDate());}
					if(vehicles.get(i).getDamageDescription() != null) { vehicleInfo.setDamageDescription(vehicles.get(i).getDamageDescription());}
					if(vehicles.get(i).getMileage() != null) { vehicleInfo.setMileage(vehicles.get(i).getMileage());}
				}
			}

			StringWriter sw = new StringWriter();
			try {
				jaxbContext = JAXBContext.newInstance(MitchellClaimType.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.marshal(currentClaim, sw);
				String xmlString = sw.toString();
				String sql = "UPDATE mitchellclaims SET claiminfo = '"+xmlString+"' WHERE claimnumber = '"+claim.getClaimNumber()+"'";
				stmt.executeUpdate(sql);
				
				System.out.println("executed update query!");
				System.out.println("Connection closed");
				connection.close();
			} catch (JAXBException e) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public boolean deleteclaim(String key) {
		establishConnection();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "DELETE FROM mitchellclaims WHERE claimnumber ='"+key+"'";
			System.out.println("executed delete query!");
			stmt.executeUpdate(query);
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
}

