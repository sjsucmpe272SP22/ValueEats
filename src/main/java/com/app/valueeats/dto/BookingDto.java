package com.app.valueeats.dto;

import java.sql.Date;

public class BookingDto
{
	private Long bookingId;
	
	private int userId;
	
	private int itemId;
	
	private int bookingQuantity;
	
	private Date recordedDate;

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getBookingQuantity() {
		return bookingQuantity;
	}

	public void setBookingQuantity(int bookingQuantity) {
		this.bookingQuantity = bookingQuantity;
	}

	public Date getRecordedDate() {
		return recordedDate;
	}

	public void setRecordedDate(Date recordedDate) {
		this.recordedDate = recordedDate;
	}
}