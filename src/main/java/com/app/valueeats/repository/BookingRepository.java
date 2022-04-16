package com.app.valueeats.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.valueeats.model.BookingEntity;

@Repository
@Transactional
public interface BookingRepository extends JpaRepository < BookingEntity, Long >
{
	@Query("SELECT SUM(a.bookingQuantity) FROM BookingEntity a WHERE a.itemId =:itemId")
	public Optional<Long> findByItemId(@Param("itemId") int itemId);

	@Query(value = "SELECT b.Booking_Id, b.Booking_Quantity, i.Item_Name, i.Item_Quantity, m.Machine_Name, m.Machine_Location, m.Machine_Address, c.Category_Name, b.Recorded_Date "
			+ "FROM booking_details AS b "
			+ "LEFT JOIN item_details AS i ON i.Item_Id = b.Item_Id "
			+ "LEFT JOIN machine_details AS m ON m.Machine_Id = i.Machine_Id "
			+ "LEFT JOIN category_details AS c ON c.Category_Id = i.Category_Id "
			+ "WHERE b.User_Id =:userId", nativeQuery = true)
	public List<Object[]> findByUserId(@Param("userId") int userId);	
	
	@Modifying
	@Query("DELETE FROM BookingEntity a WHERE a.itemId =:itemId")
	public void deleteByItem(@Param("itemId") int id);

		
}
