package pq.jdev.b001.bookstore.users.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import pq.jdev.b001.bookstore.books.service.UploadPathService;
import pq.jdev.b001.bookstore.users.model.PasswordResetToken;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.model.Role;
import pq.jdev.b001.bookstore.users.repository.PasswordResetTokenRepository;
import pq.jdev.b001.bookstore.users.repository.RoleRepository;
import pq.jdev.b001.bookstore.users.repository.UserRepository;
import pq.jdev.b001.bookstore.users.web.dto.AdminDto;
import pq.jdev.b001.bookstore.users.web.dto.AdminUpdateInfoUserDto;
import pq.jdev.b001.bookstore.users.web.dto.UserChangePassDto;
import pq.jdev.b001.bookstore.users.web.dto.UserDto;
import pq.jdev.b001.bookstore.users.web.dto.UserUpdateInfoDto;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordResetTokenRepository tokenRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UploadPathService uploadPathService;

	@Autowired
	private ModuleRunFirst moduleRunFirst;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Person person = userRepository.findByUsername(username);
		if (person == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		Set<Role> roles = person.getRoles();
		for (Role role : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return new org.springframework.security.core.userdetails.User(person.getUsername(), person.getPassword(),
				grantedAuthorities);
	}

	@Override
	public Person findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}

	@Override
	public Person findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void updatePassword(String updatedPassword, Long id) {
		userRepository.updatePassword(updatedPassword, id);
	}

	@Override
	public AdminUpdateInfoUserDto updateUserInfo(Person p) {
		AdminUpdateInfoUserDto auiu = new AdminUpdateInfoUserDto();
		auiu.setId(p.getId());
		auiu.setFirstName(p.getFirstname());
		auiu.setLastName(p.getLastname());
		auiu.setAddress(p.getAddress());
		auiu.setBirthday(p.getBirthday());
		auiu.setEmail(p.getEmail());
		auiu.setPhone(p.getPhone());
		auiu.setUserName(p.getUsername());
		auiu.setPassword(p.getPassword());
		auiu.setConfirmPassword(p.getPassword());
		auiu.setSex(p.getSex());
		auiu.setPower(p.getPower());
		auiu.setCreate_date(p.getCreate_date());
		auiu.setPicture(p.getPicture());

		switch (p.getPower()) {
		case 1:
			auiu.setDropdownSelectedValue("EMPLOYEE");
			break;
		case 2:
			auiu.setDropdownSelectedValue("ADMIN");
			;
			break;
		}
		return auiu;
	}

	@Override
	public UserUpdateInfoDto updateInfo(Person p) {
		UserUpdateInfoDto us = new UserUpdateInfoDto();
		us.setId(p.getId());
		us.setFirstName(p.getFirstname());
		us.setLastName(p.getLastname());
		us.setAddress(p.getAddress());
		us.setBirthday(p.getBirthday());
		us.setEmail(p.getEmail());
		us.setPhone(p.getPhone());
		us.setUserName(p.getUsername());
		us.setPassword(p.getPassword());
		us.setConfirmPassword(p.getPassword());
		us.setCreate_date(p.getCreate_date());
		us.setSex(p.getSex());
		us.setPower(p.getPower());
		us.setRoles(p.getRoles());
		us.setPicture(p.getPicture());
		return us;
	}

	@Override
	public UserChangePassDto updateInfoP(Person p) {
		UserChangePassDto us = new UserChangePassDto();
		us.setId(p.getId());
		us.setFirstName(p.getFirstname());
		us.setLastName(p.getLastname());
		us.setAddress(p.getAddress());
		us.setBirthday(p.getBirthday());
		us.setEmail(p.getEmail());
		us.setPhone(p.getPhone());
		us.setUserName(p.getUsername());
		us.setPassword(p.getPassword());
		us.setConfirmPassword(p.getPassword());
		us.setSex(p.getSex());
		us.setPower(p.getPower());
		us.setRoles(p.getRoles());
		us.setCreate_date(p.getCreate_date());
		us.setPicture(p.getPicture());
		return us;
	}

	@Override
	public Person save(UserUpdateInfoDto userDto) {
		Person person = findById(userDto.getId());
		person.setFirstname(userDto.getFirstName());
		person.setLastname(userDto.getLastName());
		person.setPhone(userDto.getPhone());
		person.setAddress(userDto.getAddress());
		person.setSex(userDto.getSex());
		person.setBirthday(userDto.getBirthday());
		person.setEmail(userDto.getEmail());
		person.setUsername(userDto.getUserName());
		person.setPassword(userDto.getPassword());
		person.setPower(userDto.getPower());
		person.setCreate_date(userDto.getCreate_date());
		person.setRoles(userDto.getRoles());
		person.setUpdate_date(moduleRunFirst.getTimeNow());

		if (person != null && !userDto.getNewPicture().isEmpty()) {
			try {
				MultipartFile pictureFile = userDto.getNewPicture();
				if (pictureFile != null & StringUtils.hasText(pictureFile.getOriginalFilename())) {
					String originalFileName = pictureFile.getOriginalFilename();
					String modifiedFileName = person.getId() + "_" + FilenameUtils.getBaseName(originalFileName) + "."
							+ FilenameUtils.getExtension(originalFileName);
					File storePictureFile = uploadPathService.getFilePath(modifiedFileName, "personImage");
					if (storePictureFile != null) {
						try {
							FileUtils.writeByteArrayToFile(storePictureFile, pictureFile.getBytes());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					person.setPicture(modifiedFileName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return userRepository.save(person);
	}

	@Override
	public Person save(UserDto userDto) {
		Person person = new Person();
		person.setFirstname(userDto.getFirstName());
		person.setLastname(userDto.getLastName());
		person.setPhone(userDto.getPhone());
		person.setAddress(userDto.getAddress());
		person.setSex(userDto.getSex());
		person.setBirthday(userDto.getBirthday());
		person.setEmail(userDto.getEmail());
		person.setUsername(userDto.getUserName());
		person.setPassword(passwordEncoder.encode(userDto.getPassword()));
		person.setPower(userDto.getPower());
		Timestamp ts = moduleRunFirst.getTimeNow();
		if (userDto.getCreate_date() == null)
			person.setCreate_date(ts);
		else
			person.setCreate_date(userDto.getCreate_date());
		person.setUpdate_date(ts);
		HashSet<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByName("ROLE_EMPLOYEE"));
		person.setRoles(roles);
		Person personSaveImage = userRepository.save(person);
		if (personSaveImage != null && !userDto.getPicture().isEmpty()) {
			try {
				MultipartFile pictureFile = userDto.getPicture();
				if (pictureFile != null & StringUtils.hasText(pictureFile.getOriginalFilename())) {
					String originalFileName = pictureFile.getOriginalFilename();
					String modifiedFileName = person.getId() + "_" + FilenameUtils.getBaseName(originalFileName) + "."
							+ FilenameUtils.getExtension(originalFileName);
					File storePictureFile = uploadPathService.getFilePath(modifiedFileName, "personImage");
					if (storePictureFile != null) {
						try {
							FileUtils.writeByteArrayToFile(storePictureFile, pictureFile.getBytes());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					personSaveImage.setPicture(modifiedFileName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String originalFileName = "noImage.jpg";
			String modifiedFileName = personSaveImage.getId() + "_" + originalFileName;
			File storePictureFile = uploadPathService.getFilePath(modifiedFileName, "personImage");
			if (storePictureFile != null) {
				try {
					byte[] bFile = Files.readAllBytes(Paths.get("src/main/resources/static/images/noImage.jpg"));
					FileUtils.writeByteArrayToFile(storePictureFile, bFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			personSaveImage.setPicture(modifiedFileName);
		}
		return userRepository.save(personSaveImage);
	}

	@Override
	public Person save(AdminDto userDto) {
		Person person = new Person();
		person.setFirstname(userDto.getFirstName());
		person.setLastname(userDto.getLastName());
		person.setPhone(userDto.getPhone());
		person.setAddress(userDto.getAddress());
		person.setSex(userDto.getSex());
		person.setBirthday(userDto.getBirthday());
		person.setEmail(userDto.getEmail());
		person.setUsername(userDto.getUserName());
		person.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Timestamp ts = moduleRunFirst.getTimeNow();
		if (userDto.getCreate_date() == null)
			person.setCreate_date(ts);
		else
			person.setCreate_date(userDto.getCreate_date());
		person.setUpdate_date(ts);
		String key = "ROLE_" + userDto.getDropdownSelectedValue();
		switch (key) {
		case "ROLE_EMPLOYEE":
			person.setPower(1);
			break;
		case "ROLE_ADMIN":
			person.setPower(2);
			break;
		}
		HashSet<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByName(key));
		person.setRoles(roles);
		person.setUpdate_date(moduleRunFirst.getTimeNow());
		Person personSaveImage = userRepository.save(person);
		if (personSaveImage != null && !userDto.getNewPicture().isEmpty()) {
			try {
				MultipartFile pictureFile = userDto.getNewPicture();
				if (pictureFile != null & StringUtils.hasText(pictureFile.getOriginalFilename())) {
					String originalFileName = pictureFile.getOriginalFilename();
					String modifiedFileName = person.getId() + "_" + FilenameUtils.getBaseName(originalFileName) + "."
							+ FilenameUtils.getExtension(originalFileName);
					File storePictureFile = uploadPathService.getFilePath(modifiedFileName, "personImage");
					if (storePictureFile != null) {
						try {
							FileUtils.writeByteArrayToFile(storePictureFile, pictureFile.getBytes());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					personSaveImage.setPicture(modifiedFileName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String originalFileName = "noImage.jpg";
			String modifiedFileName = personSaveImage.getId() + "_" + originalFileName;
			File storePictureFile = uploadPathService.getFilePath(modifiedFileName, "personImage");
			if (storePictureFile != null) {
				try {
					byte[] bFile = Files.readAllBytes(Paths.get("src/main/resources/static/images/noImage.jpg"));
					FileUtils.writeByteArrayToFile(storePictureFile, bFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			personSaveImage.setPicture(modifiedFileName);
		}
		return userRepository.save(personSaveImage);
	}

	@Override
	public Person save(AdminUpdateInfoUserDto userDto) {
		Person person = findById(userDto.getId());
		person.setFirstname(userDto.getFirstName());
		person.setLastname(userDto.getLastName());
		person.setPhone(userDto.getPhone());
		person.setAddress(userDto.getAddress());
		person.setSex(userDto.getSex());
		person.setBirthday(userDto.getBirthday());
		person.setEmail(userDto.getEmail());
		person.setUsername(userDto.getUserName());
		person.setPassword(userDto.getPassword());
		person.setCreate_date(userDto.getCreate_date());
		String key = "ROLE_" + userDto.getDropdownSelectedValue();
		switch (key) {
		case "ROLE_EMPLOYEE":
			person.setPower(1);
			break;
		case "ROLE_ADMIN":
			person.setPower(2);
			break;
		}
		Timestamp ts = moduleRunFirst.getTimeNow();
		person.setUpdate_date(ts);
		HashSet<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByName(key));
		person.setRoles(roles);

		if (person != null && !userDto.getNewPicture().isEmpty()) {
			try {
				MultipartFile pictureFile = userDto.getNewPicture();
				if (pictureFile != null & StringUtils.hasText(pictureFile.getOriginalFilename())) {
					String originalFileName = pictureFile.getOriginalFilename();
					String modifiedFileName = person.getId() + "_" + FilenameUtils.getBaseName(originalFileName) + "."
							+ FilenameUtils.getExtension(originalFileName);
					File storePictureFile = uploadPathService.getFilePath(modifiedFileName, "personImage");
					if (storePictureFile != null) {
						try {
							FileUtils.writeByteArrayToFile(storePictureFile, pictureFile.getBytes());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					person.setPicture(modifiedFileName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return userRepository.save(person);
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteByIdP(id);
	}

	@Override
	public Person findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public List<Person> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<Role> findAllRole() {
		return roleRepository.findAll();
	}

	@Override
	public PasswordResetToken findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	public void deleteByToken(PasswordResetToken token) {
		tokenRepository.delete(token);
	}

	@Override
	public void saveToken(PasswordResetToken token) {
		tokenRepository.save(token);
	}

	@Override
	public void deleteTokenByIdPerson(long id) {
		userRepository.deleteByIdPRT(id);

	}

	@Override
	public Role findByIdRole(long id) {
		return roleRepository.findById(id);
	}

	@Override
	public void autoLogin(String username) {
		UserDetails userDetails = loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, userDetails.getPassword(), userDetails.getAuthorities());

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}

	@Override
	public void changeAuthorize(Long idTo, long idFrom) {
		userRepository.changeAuthorize(idTo, idFrom);
	}

}
