package com.app.valueeats.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.valueeats.model.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository < ItemEntity, Long > 
{
	@Query("SELECT m FROM ItemEntity m WHERE m.userId =:userId order by m.recordedDate desc")
	public List<ItemEntity> findAllItem(@Param("userId") int userId);

	@Query("SELECT sum(m.itemQuantity) as quantity FROM ItemEntity m WHERE m.machineId =:machineId")
	public Optional<Long> findByMachineId(@Param("machineId") int machineId);

	@Query("SELECT m FROM ItemEntity m WHERE m.itemId =:itemId order by m.recordedDate desc")
	public List<ItemEntity> findAllItemId(@Param("itemId") int itemId);

	@Query("SELECT m FROM ItemEntity m WHERE m.machineId =:machineId")
	public List<ItemEntity> findAllItemByMachineId(@Param("machineId") int machineId);
	
	@Query("SELECT m FROM ItemEntity m WHERE m.itemId =:itemId order by m.recordedDate desc")
	public ItemEntity findByItemId(@Param("itemId") Long itemId);
}

