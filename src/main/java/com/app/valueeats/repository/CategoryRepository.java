package com.app.valueeats.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.valueeats.model.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository < CategoryEntity, Long >
{
	@Query("SELECT a FROM CategoryEntity a WHERE a.categoryCode =:categoryCode AND a.categoryName =:categoryName")
	public CategoryEntity findbycodeandName(@Param("categoryCode") String categoryCode, @Param("categoryName") String categoryName);

	@Query("SELECT m FROM CategoryEntity m order by m.recordedDate desc")
	public List<CategoryEntity> findAllCategory();

	@Query("SELECT a FROM CategoryEntity a WHERE a.categoryId =:categoryId")
	public CategoryEntity findByCategoryId(@Param("categoryId") Long categoryId);
}
