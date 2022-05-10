package com.app.valueeats.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item_details") 
public class ItemEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Item_Id")
	private Long itemId;
	
	@Column(name = "Item_Name")
	private String itemName;
	
	@Column(name = "Item_Quantity")
	private int itemQuantity;
	
	@Column(name = "Machine_Id")
	private int machineId;
	
	@Column(name = "Category_Id")
	private int categoryId;
	
	@Column(name = "User_Id")
	private int userId;
	
	@Column(name = "Expiry_Date")
	private Date expiryDate;
	
	@Column(name = "Recorded_Date")
	private Date recordedDate;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getRecordedDate() {
		return recordedDate;
	}

	public void setRecordedDate(Date recordedDate) {
		this.recordedDate = recordedDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}