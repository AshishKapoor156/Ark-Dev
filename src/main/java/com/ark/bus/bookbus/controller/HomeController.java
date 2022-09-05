package com.ark.bus.bookbus.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ark.bus.bookbus.Model.ListCity;
import com.ark.bus.bookbus.Model.SearchBus;
import com.ark.bus.bookbus.storedprocedure.DatabaseProcCallApi;
import com.ark.bus.bookbus.storedprocedure.ServiceCallApi;


@RestController

@RequestMapping("/home")
public class HomeController {

	@Autowired
	DatabaseProcCallApi Procapi;
	
	@Autowired
	ServiceCallApi Servicecall;
	
	@CrossOrigin(origins = "http://localhost:4200")
 @RequestMapping(value = "/list", method = RequestMethod.GET)
 public Object hello(HttpServletRequest request) {

	 String AlisaPrefix = request.getParameter("Alias");
	 System.out.println("Alisa name :" +AlisaPrefix);
	List<ListCity> listcity =  (List<ListCity>) Servicecall.ListCityDetails(AlisaPrefix);
	
//System.out.println(listcity.get(0));
 System.out.println(listcity.toString());	 
  return listcity;

 }
	@CrossOrigin(origins = "http://localhost:4200")
	 @RequestMapping(value = "/Search", method = RequestMethod.POST)
	 @PostMapping()
	 public Object Search(@PathVariable SearchBus search ) {

		//System.out.println("search controller "+request.getAttribute("Search"));
		/// System.out.println("Alisa name :" +AlisaPrefix);
		//List<ListCity> listcity =  (List<ListCity>) Servicecall.ListCityDetails(AlisaPrefix);
		System.out.println("got the value");
	//System.out.println(listcity.get(0));
	// System.out.println(listcity.toString());	 
	  return search;

	 }
	//    http://localhost:8080/home/cards?mid=1236&channel=2&cardBin=123456&purchaseAmount=6000
	 //    /fssEMIService/check-out-emi-plans/cards/5005/2/789654/6000 
	  @GetMapping({"/cards/{mid}/{channel}/{cardBin}/{purchaseAmount}"})
	  public String getCheckOutEmiPlanCards(@PathVariable Long mid, @PathVariable int channel, @PathVariable int cardBin, @PathVariable int purchaseAmount) throws URISyntaxException {
	    String result  = "I got the request";
	    
	    ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
	   // builder.scheme("https");
	  
	  //  builder.replaceQueryParam("someBoolean", false);
	    URI newUri = builder.build().toUri();
	    
	
        String Scheme = newUri.getScheme();
        String host = newUri.getHost();               
        int port =  newUri.getPort();
        ///fssEMIService/check-out-emi-plans/cards/5005/2/789654/6000
        StringBuffer url = new StringBuffer();
       
        url.append(Scheme+"://");
        url.append(host+":"+port);
        url.append("/fssEMIService/check-out-emi-plans/");
	    System.out.println(newUri + "    0"+ host + "    1" + port);
	    System.out.println(url.toString());
	    String mids = "5005";
	    String channels = "2";
	    String cardBins = "789654";
	    String purchaseAmounts = "6000";
	   StringBuilder  results = new StringBuilder();
	   results.append("http://localhost:8080/home/cards");
	   results.append("/"+mid);
	   results.append("/"+channel);
	   results.append("/"+cardBin);
	   results.append("/"+purchaseAmount);
	    return results.toString();
	  }
	

}