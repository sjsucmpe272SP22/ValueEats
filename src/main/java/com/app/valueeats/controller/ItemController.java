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
import com.app.valueeats.dto.ItemDto;
import com.app.valueeats.dto.MachineDto;
import com.app.valueeats.service.ItemService;

@Controller
@RequestMapping(path = "/item")
public class ItemController 
{
	@Autowired
	public ItemService itemService;
	
	@PostMapping(path = "/add")
    public String add(@Valid ItemDto itemDto, BindingResult result, Model model, HttpSession session) 
	{
		return itemService.add(itemDto, result, model, session);
    }
	
	@GetMapping("/view")
    public String view(Model model, HttpSession session)
	{
        return itemService.view(model, session);
    }
	
	@GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model, HttpSession session) 
	{
		return itemService.delete(id, model, session);
	}	
	
	@GetMapping("/available")
    public String available(@Valid MachineDto machineDto, Model model, HttpSession session)
	{
        return itemService.available(machineDto, model, session);
    }
	
	@GetMapping("/advAdd/{id}")
	public String advancedAdd(@PathVariable("id") long id, @Valid ItemDto itemDto, Model model) 
	{
		return itemService.advancedAdd(id, model);
	}
}

