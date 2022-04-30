package com.app.valueeats.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.app.valueeats.dto.BookingDetailsDto;
import com.app.valueeats.dto.BookingDto;
import com.app.valueeats.model.BookingEntity;
import com.app.valueeats.model.ItemEntity;
import com.app.valueeats.model.UserEntity;
import com.app.valueeats.repository.BookingRepository;
import com.app.valueeats.repository.CategoryRepository;
import com.app.valueeats.repository.ItemRepository;
import com.app.valueeats.repository.MachineRepository;
import com.app.valueeats.repository.UserRepository;
import com.app.valueeats.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService
{
	@Autowired
	public BookingService bookingService;
	
	@Autowired
	public ItemRepository itemRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public BookingRepository bookingRepository;
	
	@Autowired
	public MachineRepository machineRepository;
	
	@Autowired
	public CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public String add(BookingDto bookingDto, BindingResult result, Model model, HttpSession session)
	{
		model.addAttribute("items", itemRepository.findAll());
		
		BookingEntity bookingEntity = modelMapper.map(bookingDto, BookingEntity.class);
		
		UserEntity userEntity = userRepository.findByName(session.getAttribute("x").toString());
		
		if(null != userEntity)
		{
			bookingEntity.setUserId(userEntity.getUserId().intValue());
		}
		
		bookingEntity.setRecordedDate(java.sql.Date.valueOf(LocalDate.now()));
		
		ItemEntity itemEntity = itemRepository.findOne((long)bookingDto.getItemId());
		
		Long quantity = 0L;
		
		Optional<Long> t = bookingRepository.findByItemId(itemEntity.getItemId().intValue());
		
		if(t.isPresent())
		{
			quantity += t.get();
		}
		
		if(null != itemEntity && (quantity + bookingEntity.getBookingQuantity()) <= itemEntity.getItemQuantity())
		{
			BookingEntity book = bookingRepository.save(bookingEntity);
	        
	        if(null != book)
			{
	        	ObjectError error = new ObjectError("globalError", "Items booked successfully!");
	            result.addError(error);
			}
	        else
	        {
	        	ObjectError error = new ObjectError("globalError", "Can't able to book that particular item!");
	            result.addError(error);
	        }
		}
		else
		{
			ObjectError error = new ObjectError("globalError", "Booking quantity exceeds the item quantity!");
            result.addError(error);
		}
				 
		return "bookItem";
	}

	@Override
	public String view(Model model, HttpSession session) 
	{
		UserEntity userEntity = userRepository.findByName(session.getAttribute("x").toString());
		
		if(null != userEntity)
		{
			List<BookingDetailsDto> objBookingDetailsDto = new ArrayList<BookingDetailsDto>();
			
			List<Object[]> bookingEntity = bookingRepository.findByUserId(userEntity.getUserId().intValue());
			
			for(Object[] bookingDto: bookingEntity)
			{
				BookingDetailsDto bookingDetailsDto = new BookingDetailsDto();
				bookingDetailsDto.setBooking_Id(Long.parseLong(bookingDto[0].toString()));
				bookingDetailsDto.setBooking_Quantity(Integer.parseInt(bookingDto[1].toString()));
				bookingDetailsDto.setItem_Name(bookingDto[2].toString());
				bookingDetailsDto.setItem_Quantity(Integer.parseInt(bookingDto[3].toString()));
				bookingDetailsDto.setMachine_Name(bookingDto[4].toString());				
				bookingDetailsDto.setMachine_Location(Integer.parseInt(bookingDto[5].toString()));
				bookingDetailsDto.setMachine_Address(bookingDto[6].toString());
				bookingDetailsDto.setCategory_Name(bookingDto[7].toString());
				
				LocalDate date = LocalDate.parse(bookingDto[8].toString());
				
				bookingDetailsDto.setRecorded_Date(java.sql.Date.valueOf(date));
				
				objBookingDetailsDto.add(bookingDetailsDto);
			}
			
			model.addAttribute("bookings", objBookingDetailsDto);
		}
		
		return "viewBooking";
	}

	@Override
	public String advancedAdd(long itemId, BookingDto bookingDto, Model model) 
	{
		model.addAttribute("items", itemRepository.findByItemId(itemId));
		
		return "bookItem";
	}
}