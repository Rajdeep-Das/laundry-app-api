package com.azure.laundry.laundry.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 10)
	@Column(name = "phone", nullable = true)
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotBlank
	@Size(max = 120)
	private String password;

	@Column(name = "is_enabled", nullable = true)
	private boolean isEnabled;
	@Column(name = "is_email_verified", nullable = true)
	private boolean isEmailVerified;
	@Column(name = "is_phone_verified", nullable = true)
	private boolean isPhoneVerified;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public boolean isPhoneVerified() {
		return isPhoneVerified;
	}

	public void setPhoneVerified(boolean isPhoneVerified) {
		this.isPhoneVerified = isPhoneVerified;
	}

	/*public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.isEmailVerified = false;
		this.isPhoneVerified = false;
		this.isEnabled = true;
	}*/

	public User(String userName,String email, String password) {
		this.username = userName;
		this.email = email;
		this.password = password;
		this.isEmailVerified = false;
		this.isPhoneVerified = false;
		this.isEnabled = true;
	}

	public User(String username, String email,String phone, String password) {
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.isEmailVerified = false;
		this.isPhoneVerified = false;
		this.isEnabled = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
