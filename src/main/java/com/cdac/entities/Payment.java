package com.cdac.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true)
@Table(name="payment")
public class Payment extends BaseEntity{
	@Enumerated(EnumType.STRING) 
	@Column(length = 30, name = "payment_method")
	private PaymentMethod paymentMethod;
	@Column(nullable=false)
	private boolean status=false;

}
