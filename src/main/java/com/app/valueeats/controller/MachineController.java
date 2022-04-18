package com.app.valueeats.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.valueeats.dto.MachineDto;
import com.app.valueeats.service.MachineService;

@Controller
@RequestMapping(path = "/machine")
public class MachineController 
{
	@Autowired
	public MachineService machineService;
	
	@PostMapping(path = "/add")
    public String addMachine(@Valid MachineDto machineDto, BindingResult result, Model model, HttpSession session) 
	{
		return machineService.addMachine(machineDto, result, model, session);
    }
	
	@GetMapping("/view")
    public String viewMachine(Model model)
	{
        return machineService.viewMachine(model);
    }
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) 
	{
		return machineService.edit(id, model);
	}
	
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id") long id, @Valid MachineDto machineDto,  BindingResult result, Model model) 
	{
		return machineService.update(id, machineDto, result, model);
	}
	
	@GetMapping("/delete/{id}")
    public String deleteMachine(@PathVariable("id") long id, Model model) 
	{
		return machineService.deleteMachine(id, model);
	}
		
	@GetMapping("/search")
    public String searchMachine(@Valid MachineDto machineDto, BindingResult result, Model model)
	{
        return machineService.searchMachine(machineDto, result, model);
    }
	
	@PostMapping("/advsearch")
    public String advsearch(@Valid MachineDto machineDto, BindingResult result, Model model)
	{
        return machineService.searchMachine(machineDto, result, model);
    }
}
