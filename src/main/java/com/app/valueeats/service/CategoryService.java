package com.app.valueeats.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.app.valueeats.dto.CategoryDto;

public interface CategoryService 
{
	public String add(CategoryDto categoryDto, BindingResult result, Model model, HttpSession session);

	public String view(Model model);

	public String delete(long id, Model model);

	public String edit(long id, Model model);

	public String update(long id, CategoryDto categoryDto, BindingResult result, Model model);
}
