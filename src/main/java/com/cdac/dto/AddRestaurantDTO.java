package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRestaurantDTO {

    private String name;

    private String description;

    private AddressDTO address;

    // Optional: base64-encoded image or MultipartFile (if uploading)
    //private String imageBase64;
}
