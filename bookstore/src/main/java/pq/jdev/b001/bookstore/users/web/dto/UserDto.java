package pq.jdev.b001.bookstore.users.web.dto;

import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import pq.jdev.b001.bookstore.users.constraint.FieldMatch;
import pq.jdev.b001.bookstore.users.constraint.ValidName;
import pq.jdev.b001.bookstore.users.constraint.ValidPhone;
import pq.jdev.b001.bookstore.users.constraint.ValidPassword;

@FieldMatch.List({
		@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"), })
public class UserDto {

	@NotEmpty
	@ValidName
	private String firstName;

	@NotEmpty
	@ValidName
	private String lastName;

	@NotEmpty
	@ValidName
	private String userName;

	@Email
	@NotEmpty
	private String email;

	@NotEmpty
	@ValidPassword
	private String password;

	@NotEmpty
	@ValidPassword
	private String confirmPassword;

	@ValidPhone
	private String phone;

	private String address;

	private Date birthday;

	private String sex;

	private final int power = 1;

	private Timestamp create_date;

	private Timestamp update_date;
	
	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date() {
		java.util.Date date= new java.util.Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		this.update_date = ts;
	}

	@Nullable
	private MultipartFile picture;

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}

	@AssertTrue
	private Boolean terms;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Boolean getTerms() {
		return terms;
	}

	public void setTerms(Boolean terms) {
		this.terms = terms;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getPower() {
		return power;
	}
}
