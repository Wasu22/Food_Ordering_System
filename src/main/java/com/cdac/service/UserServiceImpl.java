package com.cdac.service;

import java.util.List;

import org.modelmapper.ModelMapper;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exception.ApiException;
import com.cdac.dao.UserDao;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.SignupReqDTO;
import com.cdac.dto.UserRequestDTO;
import com.cdac.dto.UserRespDTO;
import com.cdac.entities.User;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserDao userDao;
	private final ModelMapper modelMapper;
	
	@Override
	public UserRespDTO signUp(SignupReqDTO dto) {
		if (userDao.existsByEmail(dto.getEmail()))
			throw new ApiException("Dup Email detected - User exists already!!!!");
		User entity = modelMapper.map(dto, User.class);
		return modelMapper.map(userDao.save(entity), UserRespDTO.class);
	}

	
	@Override
	public List<UserRespDTO> getAllUsersByCity(String city) {
		return userDao.findByMyAddressCity(city)
		.stream() 
		.map(user -> modelMapper.map(user, UserRespDTO.class))
		.toList();
		
	}

	@Override
	public UserRespDTO addNewUser(UserRequestDTO dto) {
			if(userDao.findByEmail(dto.getEmail()).isEmpty())
			{
				User entity=modelMapper.map(dto, User.class);
				return modelMapper.map(userDao.save(entity),UserRespDTO.class);
			}
			throw new ApiException("duplicate Grocery name!!");
		}

	
	@Override
	public UserRespDTO updateUserById(Long userId, UserRequestDTO userRequestDTO) {
				User user = userDao.findById(userId).orElseThrow(()->new ApiException("user id not found"));	
				 modelMapper.map(userRequestDTO, user);
				    User updatedUser = userDao.save(user);
				    UserRespDTO userRespDTO = modelMapper.map(updatedUser, UserRespDTO.class);
				return userRespDTO;
			}


	@Override
	public ApiResponse deleteUserById(Long userId) {
		User user = userDao.findById(userId).orElseThrow(()->new ApiException("user id not found"));		
		userDao.deleteById(userId);
		return new ApiResponse(user.getEmail()+ "User deleted successfully..");
	}
	}
		

	


	
	


	


	


	
	


