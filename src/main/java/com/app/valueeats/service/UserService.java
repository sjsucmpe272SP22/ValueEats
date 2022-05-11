package com.app.valueeats.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.app.valueeats.dto.UserDto;

public interface UserService 
{
	public String addNewUser(UserDto userDto, BindingResult result, Model model);

	public String loginUser(UserDto userDto, BindingResult result, Model model, HttpSession session);

	public String loginRestaurant(UserDto userDto, BindingResult result, Model model, HttpSession session);

	public String addNewRestaurant(UserDto userDto, BindingResult result, Model model);

	public String available(UserDto userDto, Model model, HttpSession session);
}
