package com.app.valueeats.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.valueeats.model.MachineEntity;

@Repository
public interface MachineRepository extends JpaRepository < MachineEntity, Long >
{
	@Query("SELECT m FROM MachineEntity m WHERE m.machineName =:machineName")
	public MachineEntity findByMachineName(@Param("machineName") String machineName);

	@Query("SELECT m FROM MachineEntity m order by m.recordedDate desc")
	public List<MachineEntity> findAllMachines();
	
	@Query("SELECT m FROM MachineEntity m WHERE m.machineLocation =:machineLocation order by m.recordedDate desc")
	public List<MachineEntity> findAllMachinezipcode(@Param("machineLocation") String machineLocation);

	@Query("SELECT m FROM MachineEntity m WHERE m.machineId =:machineId")
	public MachineEntity findByMachineId(@Param("machineId") Long machineId);
	
	@Query("SELECT m FROM MachineEntity m WHERE m.machineLocation =:machineLocation order by m.recordedDate desc")
	public MachineEntity findByMachinezipcode(@Param("machineLocation") String machineLocation);
	
	@Query("SELECT m FROM MachineEntity m WHERE m.machineLocation =:machineLocation order by m.recordedDate desc")
	public List<MachineEntity> findByMachinelocation(@Param("machineLocation") String machineLocation);
}
