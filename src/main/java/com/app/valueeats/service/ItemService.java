package com.app.valueeats.service;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.app.valueeats.dto.ItemDto;
import com.app.valueeats.dto.MachineDto;

public interface ItemService 
{
	public String add(ItemDto itemDto, BindingResult result, Model model, HttpSession session);

	public String view(Model model, HttpSession session);

	public String delete(long id, Model model, HttpSession session);

	public String available(MachineDto machineDto,  Model model, HttpSession session);

	public String advancedAdd(long id, Model model);
}
