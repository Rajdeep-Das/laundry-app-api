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
	@Column(name = "id")
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

	@NotBlank
	@Size(max = 120)
	private String password;

	@Column(name = "verification_code", length = 64)
    private String verificationCode;

	@Column(name = "is_enabled", nullable = true)
	private boolean isEnabled;
	@Column(name = "is_email_verified", nullable = true)
	private boolean isEmailVerified;
	@Column(name = "is_phone_verified", nullable = true)
	private boolean isPhoneVerified;

	@Column(name = "first_name", nullable = true)
	private String firstName;
	@Column(name = "last_name", nullable = true)
    private String lastName;
	@Column(name = "nick_name", nullable = true)
    private String nickName;
	// For Demo Purpose setting as string,may need to chnage in real application
	@Column(name = "dob", nullable = true)
    private String dob;
	@Column(name = "gender", nullable = true)
    private String gender;


	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();


	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}
