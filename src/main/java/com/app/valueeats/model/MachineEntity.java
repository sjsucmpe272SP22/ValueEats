package com.app.valueeats.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "machine_details")
public class MachineEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Machine_Id")
	private Long machineId;
	
	@Column(name = "Machine_Name")
	private String machineName;
	
	@Column(name = "Machine_Location")
	private String machineLocation;
	
	@Column(name = "Machine_Address")
	private String 	machineAddress;
	
	@Column(name = "Machine_Capacity")
	private int machineCapacity;
	
	@Column(name = "Admin_Id")
	private int adminId;
	
	@Column(name = "Recorded_Date")
	private Date recordedDate;

	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineLocation() {
		return machineLocation;
	}

	public void setMachineLocation(String machineLocation) {
		this.machineLocation = machineLocation;
	}

	public int getMachineCapacity() {
		return machineCapacity;
	}

	public void setMachineCapacity(int machineCapacity) {
		this.machineCapacity = machineCapacity;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public Date getRecordedDate() {
		return recordedDate;
	}

	public void setRecordedDate(Date recordedDate) {
		this.recordedDate = recordedDate;
	}

	public String getMachineAddress() {
		return machineAddress;
	}

	public void setMachineAddress(String machineAddress) {
		this.machineAddress = machineAddress;
	}
}