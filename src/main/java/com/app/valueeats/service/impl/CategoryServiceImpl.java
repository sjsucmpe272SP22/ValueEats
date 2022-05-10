package com.app.valueeats.service.impl;

import java.time.LocalDate;
import javax.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.app.valueeats.dto.CategoryDto;
import com.app.valueeats.model.CategoryEntity;
import com.app.valueeats.repository.CategoryRepository;
import com.app.valueeats.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService 
{
	@Autowired
	public CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public String add(CategoryDto categoryDto, BindingResult result, Model model, HttpSession session) 
	{
		CategoryEntity categoryEntity = modelMapper.map(categoryDto, CategoryEntity.class);
		categoryEntity.setAdminId(Integer.parseInt(session.getAttribute("y").toString()));
		categoryEntity.setRecordedDate(java.sql.Date.valueOf(LocalDate.now()));
		
		if(null != categoryDto && (null != categoryDto.getCategoryCode() && !categoryDto.getCategoryCode().equalsIgnoreCase("")) 
				&& (null != categoryDto.getCategoryName() && !categoryDto.getCategoryName().equalsIgnoreCase("")))
		{
			CategoryEntity categoryEntitys = categoryRepository.findbycodeandName(categoryDto.getCategoryCode(), categoryDto.getCategoryName());
			
			if(null == categoryEntitys)
			{
				CategoryEntity category = categoryRepository.save(categoryEntity);
		        
		        if(null != category)
				{
					ObjectError error = new ObjectError("globalError", "Category added successfully!");
			        result.addError(error);
					 
					return "addCategory";
				}
			}
			else
			{
				ObjectError error = new ObjectError("globalError", "Category already present in our system!");
		        result.addError(error);
				 
				return "addCategory";
			}
		}
		else
		{
			ObjectError error = new ObjectError("globalError", "Kindly fill all the mandatory fields.");
	        result.addError(error);
			 
			return "addCategory";
		}
		
        return "addCategory";
	}

	@Override
	public String view(Model model) 
	{
		model.addAttribute("categorys", categoryRepository.findAllCategory());
		
		return "viewCategory";
	}

	@Override
	public String delete(long id, Model model) 
	{
		CategoryEntity categoryEntity = categoryRepository.findOne(id);
		
		if(null != categoryEntity)
		{
			categoryRepository.delete(id);
		}
		
		model.addAttribute("categorys", categoryRepository.findAllCategory());
		
		return "redirect:/category/view";
	}

	@Override
	public String edit(long id, Model model)
	{
		model.addAttribute("categoryDto", categoryRepository.findOne(id));
		
		return "updateCategory";
	}

	@Override
	public String update(long categoryId, CategoryDto categoryDto, BindingResult result, Model model) 
	{
		CategoryEntity category = categoryRepository.findOne(categoryId);
		
		if(null != category)
		{
			if(null != categoryDto && (null != categoryDto.getCategoryCode() && !categoryDto.getCategoryCode().equalsIgnoreCase("")) 
					&& (null != categoryDto.getCategoryName() && !categoryDto.getCategoryName().equalsIgnoreCase("")))
			{
				CategoryEntity categoryEntity = modelMapper.map(categoryDto, CategoryEntity.class);
				
				categoryEntity.setCategoryId(category.getCategoryId());
				categoryEntity.setAdminId(category.getAdminId());
				categoryEntity.setRecordedDate(java.sql.Date.valueOf(LocalDate.now()));
				
				categoryRepository.save(categoryEntity);
			}
		}
		else
		{
			ObjectError error = new ObjectError("globalError", "Kindly fill all the mandatory fields.");
	        result.addError(error);
		}
		
		model.addAttribute("categoryDto", categoryRepository.findAllCategory());
		
		return "redirect:/category/view";
	}
}