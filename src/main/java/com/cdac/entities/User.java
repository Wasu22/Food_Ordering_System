package com.cdac.entities;
import java.time.LocalDate;

//import org.springframework.security.core.userdetails.UserDetails;

//import annotations from JPA
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users") 
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity{
	@Column(length = 20, name = "first_name") 
	private String firstName;
	@Column(length = 30, name = "last_name") 
	private String lastName;
	@Column(length = 30, unique = true) 
	private String email;
	@Column(length = 300, nullable = false)
	private String password;
	private LocalDate dob;
	@Enumerated(EnumType.STRING) 
	@Column(length = 30, name = "user_role")
	private UserRole userRole;
	//User 1 ----> 1 Address
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="address_id")
	private Address myAddress;
	
	public User(String firstName, String lastName, String email, String password, LocalDate dob, UserRole userRole) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.userRole = userRole;
	}

	public User(String firstName, String lastName, LocalDate dob) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
	}

}


