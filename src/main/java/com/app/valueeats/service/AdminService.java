package com.app.valueeats.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.app.valueeats.dto.AdminDto;

public interface AdminService 
{
	public String loginUser(AdminDto adminDto, BindingResult result, Model model, HttpSession session);
}
