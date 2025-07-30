
package com.cdac.service;

import java.util.List;

import com.cdac.dto.ApiResponse;
import com.cdac.dto.SignupReqDTO;
import com.cdac.dto.UserRequestDTO;
import com.cdac.dto.UserRespDTO;

public interface UserService {

	UserRespDTO signUp(SignupReqDTO dto);
	
	List<UserRespDTO> getAllUsersByCity(String city);

	UserRespDTO addNewUser(UserRequestDTO dto);
	
	UserRespDTO updateUserById(Long userId, UserRequestDTO userRequestDTO);
	
	ApiResponse deleteUserById(Long userId);

	//ApiResponse deleteUserDetails(String userName);
}
