package com.app.valueeats.service;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.app.valueeats.dto.BookingDto;

public interface BookingService
{
	public String add(BookingDto bookingDto, BindingResult result, Model model, HttpSession session);

	public String view(Model model, HttpSession session);

	public String advancedAdd(long id, BookingDto bookingDto, Model model);
}
