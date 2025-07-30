package com.cdac.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dao.AddressDao;
import com.cdac.dao.UserDao;
import com.cdac.dto.AddressDTO;
import com.cdac.dto.ApiResponse;
import com.cdac.entities.Address;
import com.cdac.entities.User;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

	private final AddressDao addressDao;
	private final UserDao userDao;
	private ModelMapper mapper;

	@Override
	public ApiResponse assignAddress(Long userId, AddressDTO dto) {
		
		User userEntity = userDao.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid UserEntityid - can;t link UserEntityaddress!!!!!"));
		// userEntity : persistent
		
		Address addressEntity = userEntity.getMyAddress();
		if (addressEntity != null) {
			mapper.map(dto, addressEntity);
			return new ApiResponse("address updated ....");
		}
		//map adr dto -> adr entity
		Address adrEntity = mapper.map(dto, Address.class);
		userEntity.setMyAddress(adrEntity);
		return new ApiResponse("address linked to UserEntity....");
	}
	
	@Override
	public AddressDTO getUserAddress(Long userId) {
		Address address = addressDao.fetchUserAddress(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid UserEntityid or address not yet assigned !!!!"));
		return mapper.map(address, AddressDTO.class);
	}

}
