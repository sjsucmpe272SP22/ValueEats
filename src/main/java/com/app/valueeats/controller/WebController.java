package com.app.valueeats.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.valueeats.dto.AdminDto;
import com.app.valueeats.dto.BookingDto;
import com.app.valueeats.dto.CategoryDto;
import com.app.valueeats.dto.ItemDto;
import com.app.valueeats.dto.MachineDto;
import com.app.valueeats.dto.UserDto;
import com.app.valueeats.repository.BookingRepository;
import com.app.valueeats.repository.CategoryRepository;
import com.app.valueeats.repository.ItemRepository;
import com.app.valueeats.repository.MachineRepository;

@Controller
public class WebController 
{
	@Autowired
	public MachineRepository machineRepository;
	
	@Autowired
	public CategoryRepository categoryRepository;
	
	@Autowired
	public ItemRepository itemRepository;
	
	@Autowired
	public BookingRepository bookingRepository;
	
   @RequestMapping(value = "/index")
   public String index(@Valid UserDto userDto, BindingResult result, Model model) 
   {
	   ObjectError error = new ObjectError("globalError", "");
       result.addError(error);
   
      return "index";
   }
   
   @RequestMapping(value = "/rindex")
   public String rindex(@Valid UserDto userDto, BindingResult result, Model model) 
   {
	   ObjectError error = new ObjectError("globalError", "");
       result.addError(error);
   
      return "rindex";
   }
   
   @RequestMapping(value = "/signin")
   public String signin(@Valid UserDto userDto, BindingResult result, Model model) 
   {
	   ObjectError error = new ObjectError("globalError", "");
       result.addError(error);
   
      return "signin";
   }
   
   @RequestMapping(value = "/rsignin")
   public String rsignin(@Valid UserDto userDto, BindingResult result, Model model) 
   {
	   ObjectError error = new ObjectError("globalError", "");
       result.addError(error);
   
      return "rsignin";
   }
   
   @RequestMapping(value = "/admin")
   public String admin(@Valid AdminDto adminDto, BindingResult result, Model model) 
   {
	   ObjectError error = new ObjectError("globalError", "");
       result.addError(error);
   
      return "admin";
   }
   
   @RequestMapping(value = "/addMachine")
   public String addMachine(@Valid MachineDto machineDto, BindingResult result, Model model) 
   {
	   ObjectError error = new ObjectError("globalError", "");
       result.addError(error);
   
      return "addMachine";
   }
   
   @RequestMapping(value = "/addCategory")
   public String addCategory(@Valid CategoryDto categoryDto, BindingResult result, Model model) 
   {
	   ObjectError error = new ObjectError("globalError", "");
       result.addError(error);
   
      return "addCategory";
   }
   
   @RequestMapping(value = "/signout")
   public String signout() 
   {
      return "signout";
   }
   
   @RequestMapping(value = "/logout")
   public String logout(@Valid UserDto userDto, BindingResult result, Model model, HttpSession session) 
   {
	  ObjectError error = new ObjectError("globalError", "Logged out succesfully!");
      result.addError(error);
      
      //session  close
      session.getAttribute("x");
         
      //invalidate session or destroy session
      session.invalidate();
       
      return "index";
   }
   
   @RequestMapping(value = "/addItem")
   public String addItem(@Valid ItemDto itemDto, BindingResult result, Model model, HttpSession session) 
   {
	   ObjectError error = new ObjectError("globalError", "");
       result.addError(error);
       
       model.addAttribute("machines", machineRepository.findByMachineId((long)itemDto.getMachineId()));
       model.addAttribute("categorys", categoryRepository.findAll());
       
      return "addItem";
   }
   
   @RequestMapping(value = "/bookItem")
   public String bookItem(@Valid BookingDto bookingDto, BindingResult result, Model model, HttpSession session) 
   {
	   ObjectError error = new ObjectError("globalError", "");
       result.addError(error);
       
      model.addAttribute("items", itemRepository.findAll());
   
      return "bookItem";
   }
   
   @RequestMapping(value = "/updateMachine")
   public String updateMachine(@PathVariable("id") long machineId, BindingResult result, Model model) 
   {
	   ObjectError error = new ObjectError("globalError", "");
       result.addError(error);
       
       model.addAttribute("machineDto", machineRepository.findByMachineId(machineId));
   
      return "updateMachine";
   }
   
   @RequestMapping(value = "/updateCategory")
   public String updateCategory(@PathVariable("id") long categoryId, BindingResult result, Model model) 
   {
	  ObjectError error = new ObjectError("globalError", "");
      result.addError(error);
       
      model.addAttribute("categoryDto", categoryRepository.findOne(categoryId));
   
      return "updateCategory";
   }
}