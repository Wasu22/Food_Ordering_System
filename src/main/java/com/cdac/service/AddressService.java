package com.cdac.service;

import com.cdac.dto.AddressDTO;
import com.cdac.dto.ApiResponse;

public interface AddressService {
	ApiResponse assignAddress(Long userId, AddressDTO dto);

	AddressDTO getUserAddress(Long userId);
}
