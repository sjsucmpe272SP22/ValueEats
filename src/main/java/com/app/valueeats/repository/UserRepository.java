package com.app.valueeats.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.valueeats.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository < UserEntity, Long >
{
	@Query("SELECT u FROM UserEntity u WHERE u.userPhoneno =:mobilenumber")
	public UserEntity findByMobileNo(@Param("mobilenumber") Long mobilenumber);

	@Query("SELECT u FROM UserEntity u WHERE u.userName =:userName AND u.userPassword =:userPassword")
	public UserEntity loginUser(@Param("userName") String userName, @Param("userPassword") String userPassword);

	@Query("SELECT u FROM UserEntity u WHERE u.userName =:userName")
	public UserEntity findByName(@Param("userName") String userName);

	@Query("SELECT u FROM UserEntity u WHERE u.userLocation =:userLocation AND u.userType = 'User'")
	public List<UserEntity> findByZipcode(@Param("userLocation") Long userLocation);
}
