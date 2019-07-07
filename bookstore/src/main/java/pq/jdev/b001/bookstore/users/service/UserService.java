package pq.jdev.b001.bookstore.users.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.web.dto.AdminDto;
import pq.jdev.b001.bookstore.users.web.dto.UserDto;
import pq.jdev.b001.bookstore.users.web.dto.UserUpdateInfoDto;

public interface UserService extends UserDetailsService {

	Person findByUsername(String userName);

	Person findByEmail(String email);

	Person save(AdminDto adminDto);

	Person save(UserDto userDto);
	
	Person save(UserUpdateInfoDto userDto);
	
    void updatePassword(String password, Long personId);

	UserUpdateInfoDto updateInfo(Person p);

	void delete(Long id);

	Person findById(Long id);

	List<Person> findAll();	
}