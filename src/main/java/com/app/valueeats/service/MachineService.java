package com.app.valueeats.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.app.valueeats.dto.MachineDto;

public interface MachineService 
{
	public String addMachine(MachineDto machineDto, BindingResult result, Model model, HttpSession session);

	public String viewMachine(Model model);

	public String deleteMachine(long id, Model model);

	public String searchMachine(MachineDto machineDto, BindingResult result, Model model);

	public String update(long id, MachineDto machineDto, BindingResult result, Model model);

	public String edit(long id, Model model);
}
