
package com.cdac.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.AddressDTO;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.UserRequestDTO;
import com.cdac.service.AddressService;
import com.cdac.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final AddressService addressService;
	
	@PostMapping("/{userId}/address")
	@Operation(description = "Assign new address or update existing address")
	public ResponseEntity<?> assignOrUpdateUserAddress(
			@PathVariable Long userId,@RequestBody AddressDTO dto) {
		System.out.println("assign adr ");
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(addressService.assignAddress(userId,dto));
	}
	
	
	@GetMapping("/{userId}/address")
	public ResponseEntity<?> getUserAddress(@PathVariable Long userId)
	{
		System.out.println("in get adr "+userId);
		return ResponseEntity
				.ok(addressService.getUserAddress(userId));
	}
	
	@PostMapping("/addUser")
	@Operation(description = "Add new User")
	public ResponseEntity<?> addNewUser(@RequestBody UserRequestDTO dto) {
		System.out.println("Add new user ");
		try
		{
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(userService.addNewUser(dto));
		}
		catch(RuntimeException e)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ApiResponse(e.getMessage()));
		}
	}
	
//	@DeleteMapping("/{email}")
//	public ResponseEntity<?> deleteUserDetails(@PathVariable String email) {
//	    System.out.println("in delete " + email);
//	    try {
//	        return ResponseEntity.ok(userService.deleteUserDetails(email));
//	    } catch (RuntimeException e) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//	                .body(new ApiResponse(e.getMessage()));
//	    }
//	}
	
	@DeleteMapping("/{userId}")
	 public ResponseEntity<?> deleteUserById(@PathVariable Long userId)
	 {
		 try
		 {
			 return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(userId));
		 }
		 catch(RuntimeException e)
		 {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
		 }
	 }
	 @PutMapping("/id/{userId}")
	 public ResponseEntity<?> updateUserById(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO)
	 {
		 try
		 {
			 return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserById(userId,userRequestDTO));
		 }
		 catch(RuntimeException e)
		 {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
		 }
	 }
}
