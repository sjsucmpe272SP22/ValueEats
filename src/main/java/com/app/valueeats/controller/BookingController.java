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
import com.app.valueeats.dto.BookingDto;
import com.app.valueeats.service.BookingService;

@Controller
@RequestMapping(path = "/booking")
public class BookingController
{
	@Autowired
	public BookingService bookingService;
	
	@PostMapping(path = "/add")
    public String add(@Valid BookingDto bookingDto, BindingResult result, Model model, HttpSession session) 
	{
		return bookingService.add(bookingDto, result, model, session);
    }
	
	@GetMapping("/view")
    public String view(Model model, HttpSession session)
	{
        return bookingService.view(model, session);
    }
	
	@GetMapping("/advAdd/{id}")
	public String advancedAdd(@PathVariable("id") long id, @Valid BookingDto bookingDto, Model model) 
	{
		return bookingService.advancedAdd(id, bookingDto, model);
	}
}
