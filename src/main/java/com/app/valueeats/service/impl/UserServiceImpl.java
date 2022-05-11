package com.app.valueeats.service.impl;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.app.valueeats.dto.UserDto;
import com.app.valueeats.model.UserEntity;
import com.app.valueeats.repository.UserRepository;
import com.app.valueeats.service.UserService;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
		
	@Override
	public String addNewUser(UserDto userDto, BindingResult result, Model model) 
	{
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userEntity.setUserType("User");
		userEntity.setRecordedDate(java.sql.Date.valueOf(LocalDate.now()));
		
		UserEntity user = userRepository.save(userEntity);
        
        if(null != user)
		{
			ObjectError error = new ObjectError("globalError", "Registered successfully!");
	        result.addError(error);
			 
			return "index";
		}
        
        return "signin";
	}

	@Override
	public String loginUser(UserDto userDto, BindingResult result, Model model, HttpSession session) 
	{
		if(null != userDto 
				&& (null != userDto.getUserName() && !userDto.getUserName().equalsIgnoreCase(""))
				&& (null !=userDto.getUserPassword() && !userDto.getUserPassword().equalsIgnoreCase(""))) 
		{
			UserEntity userEntity = userRepository.loginUser(userDto.getUserName(), userDto.getUserPassword());
			
			if(null == userEntity)
			{
				ObjectError error = new ObjectError("globalError", "Incorrect username or password!");
		        result.addError(error);
				 
				return "index";
			}
			else
			{
				session.setAttribute("x", null != userEntity && null != userEntity.getUserName() ? userEntity.getUserName() : "-");
				session.setAttribute("y", null != userEntity && null != userEntity.getUserId() ? userEntity.getUserId() : "-");
				
				if(null != userEntity && userEntity.getUserType().equalsIgnoreCase("User"))
				{
					return "redirect:/item/available";
				}
				else
				{
					 ObjectError error = new ObjectError("globalError", "Kindly login in restaurant login page!");
				     result.addError(error);
					 
					 return "index";
				}
			}			
		}
		else
		{
			 ObjectError error = new ObjectError("globalError", "Kindly fill both username and password!");
		     result.addError(error);
			 
			 return "index";
		}
	}

	@Override
	public String loginRestaurant(UserDto userDto, BindingResult result, Model model, HttpSession session) 
	{
		if(null != userDto 
				&& (null != userDto.getUserName() && !userDto.getUserName().equalsIgnoreCase(""))
				&& (null !=userDto.getUserPassword() && !userDto.getUserPassword().equalsIgnoreCase(""))) 
		{
			UserEntity userEntity = userRepository.loginUser(userDto.getUserName(), userDto.getUserPassword());
			
			if(null == userEntity)
			{
				ObjectError error = new ObjectError("globalError", "Incorrect username or password!");
		        result.addError(error);
				 
				return "rindex";
			}
			else
			{
				session.setAttribute("x", null != userEntity && null != userEntity.getUserName() ? userEntity.getUserName() : "-");
				session.setAttribute("y", null != userEntity && null != userEntity.getUserId() ? userEntity.getUserId() : "-");
				
				if(null != userEntity && userEntity.getUserType().equalsIgnoreCase("User"))
				{
					 ObjectError error = new ObjectError("globalError", "Kindly login in user login page!");
				     result.addError(error);
					 
					 return "rindex";
				}
				else
				{
					return "redirect:/user/available";
				}
			}			
		}
		else
		{
			 ObjectError error = new ObjectError("globalError", "Kindly fill both username and password!");
		     result.addError(error);
			 
			 return "rindex";
		}
	}

	@Override
	public String addNewRestaurant(UserDto userDto, BindingResult result, Model model)
	{
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userEntity.setUserType("Vendor");
		userEntity.setRecordedDate(java.sql.Date.valueOf(LocalDate.now()));
		
		UserEntity user = userRepository.save(userEntity);
        
        if(null != user)
		{
			ObjectError error = new ObjectError("globalError", "Registered successfully!");
	        result.addError(error);
			 
			return "rindex";
		}
        
        return "rsignin";
	}

	@Override
	public String available(UserDto userDto, Model model, HttpSession session)
	{
		UserEntity userEntity = userRepository.findByName(session.getAttribute("x").toString());
		
		if(null != userEntity)
		{
			UserDto userDtos = modelMapper.map(userEntity, UserDto.class);
			
			model.addAttribute("users", userDtos);
		}
		
		return "viewRestaurant";
	}
}
