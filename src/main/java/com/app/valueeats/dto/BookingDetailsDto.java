package com.app.valueeats.dto;

import java.sql.Date;

public class BookingDetailsDto 
{
	private Long Booking_Id;
	
	private int Booking_Quantity;
	
	private String Item_Name;

	private int Item_Quantity;
	
	private String Machine_Name;
	
	private String Machine_Address;
	
	private int Machine_Location;
	
	private String Category_Name;
	
	private Date Recorded_Date;

	public Long getBooking_Id() {
		return Booking_Id;
	}

	public void setBooking_Id(Long booking_Id) {
		Booking_Id = booking_Id;
	}

	public int getBooking_Quantity() {
		return Booking_Quantity;
	}

	public void setBooking_Quantity(int booking_Quantity) {
		Booking_Quantity = booking_Quantity;
	}

	public String getItem_Name() {
		return Item_Name;
	}

	public void setItem_Name(String item_Name) {
		Item_Name = item_Name;
	}

	public int getItem_Quantity() {
		return Item_Quantity;
	}

	public void setItem_Quantity(int item_Quantity) {
		Item_Quantity = item_Quantity;
	}

	public String getMachine_Name() {
		return Machine_Name;
	}

	public void setMachine_Name(String machine_Name) {
		Machine_Name = machine_Name;
	}

	public int getMachine_Location() {
		return Machine_Location;
	}

	public void setMachine_Location(int machine_Location) {
		Machine_Location = machine_Location;
	}

	public String getCategory_Name() {
		return Category_Name;
	}

	public void setCategory_Name(String category_Name) {
		Category_Name = category_Name;
	}

	public Date getRecorded_Date() {
		return Recorded_Date;
	}

	public void setRecorded_Date(Date recorded_Date) {
		Recorded_Date = recorded_Date;
	}

	public String getMachine_Address() {
		return Machine_Address;
	}

	public void setMachine_Address(String machine_Address) {
		Machine_Address = machine_Address;
	}
}