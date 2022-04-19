package com.app.valueeats.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.app.valueeats.dto.UserDto;
import com.app.valueeats.service.UserService;

@Controller
@RequestMapping(path = "/user")
public class UserController 
{
	@Autowired
	public UserService userService;
	
	@PostMapping(path = "/add")
    public String addNewUser(@Valid UserDto userDto, BindingResult result, Model model) 
	{
		return userService.addNewUser(userDto, result, model);
    }
	
	@PostMapping(path = "/radd")
    public String addNewRestaurant(@Valid UserDto userDto, BindingResult result, Model model) 
	{
		return userService.addNewRestaurant(userDto, result, model);
    }
	
	@PostMapping(path = "/login")
    public String loginUser(@Valid UserDto userDto, BindingResult result, Model model, HttpSession session) 
	{
		return userService.loginUser(userDto, result, model, session);
    }
	
	@PostMapping(path = "/rlogin")
    public String loginRestaurant(@Valid UserDto userDto, BindingResult result, Model model, HttpSession session) 
	{
		return userService.loginRestaurant(userDto, result, model, session);
    }
	
	@GetMapping("/available")
    public String available(@Valid UserDto userDto, Model model, HttpSession session)
	{
        return userService.available(userDto, model, session);
    }
}
