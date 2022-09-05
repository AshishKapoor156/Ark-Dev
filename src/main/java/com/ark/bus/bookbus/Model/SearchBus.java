package com.ark.bus.bookbus.Model;

import java.util.Date;

public class SearchBus {
 private String Arrival;
 private String Departure ;
 
 public String getArrival() {
	return Arrival;
}

public void setArrival(String arrival) {
	Arrival = arrival;
}

public String getDeparture() {
	return Departure;
}

public void setDeparture(String departure) {
	Departure = departure;
}

public Date getTraveldate() {
	return traveldate;
}

public void setTraveldate(Date traveldate) {
	this.traveldate = traveldate;
}

private Date traveldate;
}
