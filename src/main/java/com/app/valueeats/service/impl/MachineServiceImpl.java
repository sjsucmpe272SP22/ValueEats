package com.app.valueeats.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.app.valueeats.dto.MachineDto;
import com.app.valueeats.model.ItemEntity;
import com.app.valueeats.model.MachineEntity;
import com.app.valueeats.repository.BookingRepository;
import com.app.valueeats.repository.ItemRepository;
import com.app.valueeats.repository.MachineRepository;
import com.app.valueeats.service.MachineService;

@Service
public class MachineServiceImpl implements MachineService
{
	@Autowired
	public MachineRepository machineRepository;
	
	@Autowired
	public BookingRepository bookingRepository;
	
	@Autowired
	public ItemRepository itemRepository;
	
	@Autowired
	ModelMapper modelMapper;
			
	@Override
	public String addMachine(MachineDto machineDto, BindingResult result, Model model, HttpSession session) 
	{
		MachineEntity machineEntity = modelMapper.map(machineDto, MachineEntity.class);
		machineEntity.setAdminId(Integer.parseInt(session.getAttribute("y").toString()));
		machineEntity.setRecordedDate(java.sql.Date.valueOf(LocalDate.now()));
		
		MachineEntity machineEntitys = machineRepository.findByMachineName(machineDto.getMachineName());
		
		if(null == machineEntitys)
		{
			MachineEntity machine = machineRepository.save(machineEntity);
	        
	        if(null != machine)
			{
				ObjectError error = new ObjectError("globalError", "Machine added successfully!");
		        result.addError(error);
				 
				return "addMachine";
			}
		}
		else
		{
			ObjectError error = new ObjectError("globalError", "Machine already present in our system!");
	        result.addError(error);
			 
			return "addMachine";
		}
		
        return "addMachine";
	}

	@Override
	public String viewMachine(Model model)
	{
		model.addAttribute("machines", machineRepository.findAllMachines());
		
		return "viewMachine";
	}

	@Override
	public String deleteMachine(long id, Model model)
	{
		MachineEntity machineEntity = machineRepository.findOne(id);
		
		if(null != machineEntity)
		{
			machineRepository.delete(id);
		}
		
		model.addAttribute("machines", machineRepository.findAllMachines());
		
		return "redirect:/machine/view";
	}

	@Override
	public String searchMachine(MachineDto machineDto, BindingResult result, Model model) 
	{
		if(null != machineDto && null != machineDto.getMachineLocation() && !machineDto.getMachineLocation().equalsIgnoreCase(""))
		{
			List<MachineDto> machineDtos = new ArrayList<MachineDto>();
			
			List<MachineEntity> objMachineEntity = machineRepository.findAllMachinezipcode(machineDto.getMachineLocation());
			
			List<MachineDto> machineDtoList = objMachineEntity
					  .stream()
					  .map(user -> modelMapper.map(user, MachineDto.class))
					  .collect(Collectors.toList());
			
			for(MachineDto machDto: machineDtoList)
			{
				Long book_quantity = 0L;
				Long item_quantity = 0L;
				
				Optional<Long> itemQuantity = itemRepository.findByMachineId(machDto.getMachineId().intValue());
				
				if(itemQuantity.isPresent())
				{
					item_quantity = itemQuantity.get();
				}
				
				List<ItemEntity> objItemEntity = itemRepository.findAllItemByMachineId(machDto.getMachineId().intValue());
				
				for(ItemEntity itemEntity: objItemEntity)
				{
					Optional<Long> booking_quantity = bookingRepository.findByItemId(itemEntity.getItemId().intValue());
					
					if(booking_quantity.isPresent())
					{
						book_quantity += booking_quantity.get();
					}
				}
				
				//100 - (30 - 0)
				Long balance = machDto.getMachineCapacity() - (item_quantity - book_quantity);
				
				machDto.setBalance(Math.abs(balance.intValue()));
				
				machineDtos.add(machDto);
			}
			
			model.addAttribute("machines", machineDtos);
		}
		else
		{
			List<MachineDto> machineDtos = new ArrayList<MachineDto>();
			
			List<MachineEntity> objMachineEntity = machineRepository.findAllMachines();
			
			List<MachineDto> machineDtoList = objMachineEntity
					  .stream()
					  .map(user -> modelMapper.map(user, MachineDto.class))
					  .collect(Collectors.toList());
			
			for(MachineDto machDto: machineDtoList)
			{
				Long book_quantity = 0L;
				Long item_quantity = 0L;
				
				Optional<Long> itemQuantity = itemRepository.findByMachineId(machDto.getMachineId().intValue());
				
				if(itemQuantity.isPresent())
				{
					item_quantity = itemQuantity.get();
				}
				
				List<ItemEntity> objItemEntity = itemRepository.findAllItemByMachineId(machDto.getMachineId().intValue());
				
				for(ItemEntity itemEntity: objItemEntity)
				{
					Optional<Long> booking_quantity = bookingRepository.findByItemId(itemEntity.getItemId().intValue());
					
					if(booking_quantity.isPresent())
					{
						book_quantity += booking_quantity.get();
					}
				}
				
				//100 - (30 - 0)
				Long balance = machDto.getMachineCapacity() - (item_quantity - book_quantity);
				
				machDto.setBalance(Math.abs(balance.intValue()));
				
				machineDtos.add(machDto);
			}
			
			model.addAttribute("machines", machineDtos);
		}
		
		return "searchMachine";
	}

	@Override
	public String update(long machineId, MachineDto machineDto, BindingResult result, Model model)
	{
		MachineEntity machine = machineRepository.findByMachineId(machineId);
		
		if(null != machine)
		{
			MachineEntity machineEntity = modelMapper.map(machineDto, MachineEntity.class);
			machineEntity.setMachineId(machine.getMachineId());
			machineEntity.setAdminId(machine.getAdminId());
			machineEntity.setRecordedDate(java.sql.Date.valueOf(LocalDate.now()));
			
			machineRepository.save(machineEntity);
		}
		
		model.addAttribute("machines", machineRepository.findAllMachines());
		
		return "redirect:/machine/view";
	}

	@Override
	public String edit(long id, Model model)
	{
		model.addAttribute("machineDto", machineRepository.findByMachineId(id));
		
		return "updateMachine";
	}
}