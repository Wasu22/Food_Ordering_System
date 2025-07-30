package com.cdac.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdac.entities.Address;

public interface AddressDao extends JpaRepository<Address, Long>{
	@Query("select u.myAddress from User u where u.id=:userId")
	Optional<Address> fetchUserAddress(Long userId);
}
