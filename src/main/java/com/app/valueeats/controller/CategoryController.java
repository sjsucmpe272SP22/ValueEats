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
import com.app.valueeats.dto.CategoryDto;
import com.app.valueeats.service.CategoryService;

@Controller
@RequestMapping(path = "/category")
public class CategoryController 
{
	@Autowired
	public CategoryService categoryService;
	
	@PostMapping(path = "/add")
    public String add(@Valid CategoryDto categoryDto, BindingResult result, Model model, HttpSession session) 
	{
		return categoryService.add(categoryDto, result, model, session);
    }
	
	@GetMapping("/view")
    public String view(Model model)
	{
        return categoryService.view(model);
    }
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) 
	{
		return categoryService.edit(id, model);
	}
	
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id") long id, @Valid CategoryDto categoryDto,  BindingResult result, Model model) 
	{
		return categoryService.update(id, categoryDto, result, model);
	}
	
	@GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) 
	{
		return categoryService.delete(id, model);
	}	
}
