package com.app.valueeats.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.app.valueeats.dto.ItemDto;
import com.app.valueeats.dto.MachineDto;
import com.app.valueeats.model.CategoryEntity;
import com.app.valueeats.model.ItemEntity;
import com.app.valueeats.model.MachineEntity;
import com.app.valueeats.model.UserEntity;
import com.app.valueeats.repository.BookingRepository;
import com.app.valueeats.repository.CategoryRepository;
import com.app.valueeats.repository.ItemRepository;
import com.app.valueeats.repository.MachineRepository;
import com.app.valueeats.repository.UserRepository;
import com.app.valueeats.service.ItemService;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@SuppressWarnings("unused")
@Service
public class ItemServiceImpl implements ItemService
{
	@Autowired
	public ItemRepository itemRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public MachineRepository machineRepository;
	
	@Autowired
	public CategoryRepository categoryRepository;
	
	@Autowired
	public BookingRepository bookingRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Value("${sendgrid.api.key}")
	private String apiKey;
	
	@Value("${sendgrid.from.email}")
	private String fromEmail;
	
	@Value("${sendgrid.cc.email}")
	private String ccEmail;
		
	@Override
	public String add(ItemDto itemDtos, BindingResult result, Model model, HttpSession session)
	{
		ObjectError error = null;
		
		ItemEntity itemEntity = modelMapper.map(itemDtos, ItemEntity.class);
		
		UserEntity userEntity = userRepository.findByName(session.getAttribute("x").toString());
		
		if(null != userEntity)
		{
			itemEntity.setUserId(userEntity.getUserId().intValue());
		}
		
		itemEntity.setRecordedDate(java.sql.Date.valueOf(LocalDate.now()));
		
		MachineEntity machineEntity = machineRepository.findByMachineId((long)itemEntity.getMachineId());
		
		int machine_Capacity = 0;
		String machine_zipcode = null;
		
		if(null != machineEntity)
		{
			machine_Capacity = machineEntity.getMachineCapacity();
			machine_zipcode = machineEntity.getMachineLocation();
		}
		
		Long quantity = 0L;
		
		Optional<Long> machine_Quantity = itemRepository.findByMachineId(itemEntity.getMachineId());
		
		if(machine_Quantity.isPresent())
		{
			quantity += machine_Quantity.get();
		}
		
		List<ItemEntity> itemEntityList = itemRepository.findAllItemByMachineId(itemEntity.getMachineId());
		
		Long booking_quantity = 0L;
		
		for(ItemEntity item: itemEntityList)
		{
			Optional<Long> t = bookingRepository.findByItemId(item.getItemId().intValue());
			
			if(t.isPresent())
			{
				booking_quantity += t.get();
			}
		}
		
		//100 - ((10 + 100) - 10) = 100 - 10 = 90
		Long balance = machine_Capacity - ((quantity + itemEntity.getItemQuantity()) - booking_quantity);
		
		System.out.println(machine_Capacity);
		System.out.println(quantity);
		System.out.println(itemEntity.getItemQuantity());
		System.out.println(booking_quantity);
		System.out.println(balance);
				
		if(balance < 0)
		{
			itemDtos.setBalance(0L);
		}
		else
		{
			itemDtos.setBalance(balance);
		}
		
		// 100 + 1 - 0 <= 100
		if(((quantity + itemEntity.getItemQuantity()) - booking_quantity) <= machine_Capacity) //100 < 120
		{
			ItemEntity item = itemRepository.save(itemEntity);
	        
	        if(null != item)
			{
	        	List<UserEntity> userEntityList = userRepository.findByZipcode(Long.parseLong(machine_zipcode));
	        	
	        	if(!userEntityList.isEmpty())
	        	{
	        		Personalization personalization = new Personalization();
	        		
	        		Set<String> data = new LinkedHashSet<String>(); 
	        		
		        	for(UserEntity userEntitys: userEntityList)
		        	{
		        		 data.add(userEntitys.getUserEmailId()); 
		        	}

		        	Object[] array_data = data.toArray(); 
		        	
		            for (int i = 0; i < array_data.length; i++) 
		            {
		                personalization.addTo(new Email(array_data[i].toString()));
		            }
		            
		        	
		        	String subject = "Value Eats Email Notification";
					String messages = "<html>"
			                  
					                  + "<head>"
			
					                  + "<title><h1>"+ subject +"</h1></title>"
					                  
					                  + "<style>"
					                  
									  +	"table, th, td"
									  + "{"
										  +"border: 1px solid black;"
										+"}"
										  
									  +	"</style>"
			
					                  + "</head>"
			
					                  + "<body>"
					                  
									  + "<div style='color:red;'> <em> The below vending machine has been filled with food items.</em></div>"
					                 
					                  + "<table>"
					                  
										+ "<thead>"
										
											+ "<tr>"
											
												+ "<td>Food Item Name</td>"
												+ "<td>Vending Machine Name</td>"
												+ "<td>Vending Machine Address</td>"
											+ "</tr>"
												
										+ "</thead>"
											
										+ "<tbody>"
										
											+ "<tr>"
											+ "<td>" + itemEntity.getItemName() + "</td>"
											+ "<td>" + machineEntity.getMachineName() + "</td>"
											+ "<td>" + machineEntity.getMachineAddress() + "</td>"
											+ "</tr>"
											
										+ "</tbody>"
										
										+ "</table>"
			
					                  + "</body>"
					                  
					                  + "</html>";		                  		                 
					
					try 
					{				
				        sendEmail(fromEmail, personalization, subject, messages);
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
	        	}
	        	
				error = new ObjectError("globalError", "Items added successfully!");
			}
	        else
	        {
	        	error = new ObjectError("globalError", "Items not added successfully!");
	        }
		}		
		else
		{
			error = new ObjectError("globalError", "The wending machine had reached its full capacity!");
		}
		
		model.addAttribute("machines", machineRepository.findAll());
	    model.addAttribute("categorys", categoryRepository.findAll());
	    model.addAttribute("itemDto", itemDtos);
	    
		result.addError(error);
		
		return "addItem";
	}

	private String sendEmail(String _from, Personalization personalization, String subject, String messages)
	{
        String results = "";
        
		try 
		{
			CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() 
			{
			    @Override
			    public void run()
			    {
			    	Email from = new Email(_from);
			    	
			    	personalization.addCc(new Email(ccEmail));
			    	
			    	Content content = new Content();				    
				    content.setType("text/html");
				    content.setValue(messages);
			    	
			    	Mail mail = new Mail();
			    	mail.setFrom(from);
			    	mail.setSubject(subject);
			    	mail.addPersonalization(personalization);
			    	mail.addContent(content);
				    				
				    SendGrid sg = new SendGrid(apiKey);
				    
				    Request request = new Request();
				    
				    try
				    {
				      request.setMethod(Method.POST);
				      request.setEndpoint("mail/send");
				      request.setBody(mail.build());
				      
				      Response response = sg.api(request);
				      
				      System.out.println(response.getBody());
				    } 
				    catch (IOException ex)
				    {
				      ex.printStackTrace();
				    }	   
			    }
			});
			
			future.get();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		catch (ExecutionException e) 
		{
			e.printStackTrace();
		}
        
		return results; 
	}

	@Override
	public String view(Model model, HttpSession session)
	{	
		UserEntity userEntity = userRepository.findByName(session.getAttribute("x").toString());
		
		if(null != userEntity)
		{
			List<ItemEntity> objItemEntity = itemRepository.findAllItem(userEntity.getUserId().intValue());
			
			List<ItemDto> itemDtoList = objItemEntity
					  .stream()
					  .map(user -> modelMapper.map(user, ItemDto.class))
					  .collect(Collectors.toList());
			
			for(ItemDto itemDto: itemDtoList)
			{
				MachineEntity machineEntity = machineRepository.findByMachineId((long) itemDto.getMachineId());
				
				if(null != machineEntity)
				{
					itemDto.setMachineName(machineEntity.getMachineName());
				}

				CategoryEntity categoryEntity = categoryRepository.findByCategoryId((long) itemDto.getCategoryId());
				
				if(null != categoryEntity)
				{
					itemDto.setCategoryName(categoryEntity.getCategoryName());
				}
			}
			
			model.addAttribute("items", itemDtoList);
		}
		
		return "viewItem";
	}

	@Override
	public String delete(long id, Model model, HttpSession session) 
	{
		ItemEntity itemEntity = itemRepository.findOne(id);
		
		if(null != itemEntity)
		{
			itemRepository.delete(id);
			
			bookingRepository.deleteByItem((int)id);
		}
		
		UserEntity userEntity = userRepository.findByName(session.getAttribute("x").toString());
		
		if(null != userEntity)
		{
			List<ItemEntity> objItemEntity = itemRepository.findAllItem(userEntity.getUserId().intValue());
			
			List<ItemDto> itemDtoList = objItemEntity
					  .stream()
					  .map(user -> modelMapper.map(user, ItemDto.class))
					  .collect(Collectors.toList());
			
			for(ItemDto itemDto: itemDtoList)
			{
				MachineEntity machineEntity = machineRepository.findByMachineId((long) itemDto.getMachineId());
				
				if(null != machineEntity)
				{
					itemDto.setMachineName(machineEntity.getMachineName());
				}

				CategoryEntity categoryEntity = categoryRepository.findByCategoryId((long) itemDto.getCategoryId());
				
				if(null != categoryEntity)
				{
					itemDto.setCategoryName(categoryEntity.getCategoryName());
				}
			}
			
			model.addAttribute("items", itemDtoList);
		}
		
		return "redirect:/item/view";
	}

	@Override
	public String available(MachineDto machineDto, Model model, HttpSession session) 
	{
		List<List<ItemEntity>> objItemEntityList = new ArrayList<List<ItemEntity>>();
		
		UserEntity userEntity = userRepository.findByName(session.getAttribute("x").toString());
		
		if(null != userEntity)
		{
			List<ItemDto> itemDtos = new ArrayList<ItemDto>();
			
			if(null != machineDto && null != machineDto.getMachineLocation() && !machineDto.getMachineLocation().equalsIgnoreCase(""))
			{
				List<MachineEntity> machineEntity = machineRepository.findByMachinelocation(machineDto.getMachineLocation());
				
				for(MachineEntity machineEntitys : machineEntity)
				{
					List<ItemEntity> objItemEntity = itemRepository.findAllItemByMachineId(machineEntitys.getMachineId().intValue());
					
					objItemEntityList.add(objItemEntity);
				}
			}
			else
			{
				List<MachineEntity> machineEntity = machineRepository.findByMachinelocation(userEntity.getUserLocation().toString());
				
				for(MachineEntity machineEntitys : machineEntity)
				{
					List<ItemEntity> objItemEntity = itemRepository.findAllItemByMachineId(machineEntitys.getMachineId().intValue());
					
					objItemEntityList.add(objItemEntity);
				}
			}
			
			for(List<ItemEntity> objItemEntity : objItemEntityList)
			{
				List<ItemDto> itemDtoList = objItemEntity
						  .stream()
						  .map(user -> modelMapper.map(user, ItemDto.class))
						  .collect(Collectors.toList());
				
				for(ItemDto itemDto: itemDtoList)
				{
					Optional<Long> booking_quantity = bookingRepository.findByItemId(itemDto.getItemId().intValue());
					
					Long quantity = 0L;
					
					if(booking_quantity.isPresent())
					{
						quantity = booking_quantity.get();
					}
					
					//System.out.println(quantity + "<" + itemDto.getItemQuantity());
					
					//90 < 90
					if(quantity < itemDto.getItemQuantity())
					{
						Long balance = itemDto.getItemQuantity() - quantity;
								
						ItemDto item = new ItemDto();
						item.setItemId(itemDto.getItemId());
						item.setItemName(itemDto.getItemName());
						item.setItemQuantity(itemDto.getItemQuantity());
						
						MachineEntity machineEntity = machineRepository.findByMachineId((long) itemDto.getMachineId());
						
						if(null != machineEntity)
						{
							item.setMachineName(machineEntity.getMachineName());
							item.setMachineLocation(machineEntity.getMachineLocation());
							item.setMachineAddress(machineEntity.getMachineAddress());
						}

						CategoryEntity categoryEntity = categoryRepository.findByCategoryId((long) itemDto.getCategoryId());
						
						if(null != categoryEntity)
						{
							item.setCategoryName(categoryEntity.getCategoryName());
						}
						
						item.setUserId(itemDto.getUserId());
						item.setRecordedDate(itemDto.getRecordedDate());
						item.setBalance(balance);
						item.setExpiryDate(itemDto.getExpiryDate());
						
						itemDtos.add(item);
					}
				}
			}
			
			model.addAttribute("items", itemDtos);
		}
		
		return "viewAvailable";
	}

	@Override
	public String advancedAdd(long machineId, Model model) 
	{
		model.addAttribute("machines", machineRepository.findByMachineId(machineId));
	    model.addAttribute("categorys", categoryRepository.findAll());
	    
	    ItemDto itemDto = new ItemDto();
	    
	    MachineEntity machDto = machineRepository.findByMachineId(machineId);
		
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
		
		itemDto.setBalance(Math.abs(balance));
		
		model.addAttribute("itemDto", itemDto);
		
		return "addItem";
	}
}