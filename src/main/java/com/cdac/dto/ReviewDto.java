package com.cdac.dto;

import com.cdac.entities.Rating;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private String comment;
    private Rating rating; 
}
