package pq.jdev.b001.bookstore.users.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.model.Role;
import pq.jdev.b001.bookstore.users.service.UserService;
import pq.jdev.b001.bookstore.users.web.dto.AdminUpdateInfoUserDto;
import pq.jdev.b001.bookstore.users.web.dto.UserUpdateInfoDto;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/listUser")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// tao list
	@ModelAttribute("list")
	public List<Person> getList(Principal principal) {
		String username = principal.getName();
		Person per = userService.findByUsername(username);
		List<Person> oldList = userService.findAll();
		List<Person> newList = new ArrayList<Person>();
		int key = per.getPower();
		for (Person p : oldList)
			if (p.getPower() <= key) {
				newList.add(p);
			}
		return newList;
	}

	@ModelAttribute("singleSelectAllValues")
	public String[] getSingleSelectAllValues() {
		return new String[] { "Male", "Female" };
	}

	@ModelAttribute("allRoles")
	public List<String> allRoles(Principal principal) {
		ArrayList<String> kq = new ArrayList<>();
		String username = principal.getName();
		Person per = userService.findByUsername(username);
		Set<Role> roles = per.getRoles();
		String key = null;
		for (Role role : roles) {
			key = role.getName();
		}
		List<Role> list = userService.findAllRole();
		for (Role r : list) {
			String ss = r.getName();
			if (!ss.equals(key)) {
				kq.add(ss.substring(5));
			} else {
				kq.add(ss.substring(5));
				break;
			}
		}
		return kq;
	}

	@GetMapping
	public String showUpdateInfoForm(Model model, ModelMap map, Principal principal) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		model.addAttribute("list", getList(principal));
		return "/listUser";
	}

	// update user
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/edit-user-{id}")
	public String showUpdateInfoForm(Model model, ModelMap map, Principal principal, @PathVariable long id) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		List<Person> list = getList(principal);
		for (Person p : list)
			if (p.getId() == id) {
				AdminUpdateInfoUserDto uuid = userService.updateUserInfo(p);
				model.addAttribute("person", uuid);
			}
		return "adminUpdateUser";
	}

	@PostMapping(value = "/edit-user-{id}")
	public String updateUserAccount(@PathVariable long id, Model model, Principal principal,
			@ModelAttribute("person") @Valid AdminUpdateInfoUserDto userDto, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			System.out.println(result.toString());
			return "adminUpdateUser";
		}
		userService.save(userDto);

		return "redirect:/listUser";
	}

	// change password

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/edit-user-{id}/changePassword", method = RequestMethod.GET)
	public String showChangePassForm(@PathVariable long id, Model model, ModelMap map, Principal principal) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		List<Person> list = getList(principal);
		for (Person p : list)
			if (p.getId() == id) {
				UserUpdateInfoDto uuid = userService.updateInfo(p);
				model.addAttribute("person", uuid);
			}
		return "/adminChangePassword";
	}

	@RequestMapping(value = "/edit-user-{id}/changePassword", method = RequestMethod.POST)
	public String UpdatePassUserAccount(@PathVariable long id,
			@ModelAttribute("person") @Valid UserUpdateInfoDto userDto, BindingResult result) {
		String url = "";
		if (result.hasErrors()) {
			url = "/listUser/edit-user-" + String.valueOf(id) + "/changePassword";
			return url;
		}

		String updatedPassword = passwordEncoder.encode(userDto.getPassword());
		userService.updatePassword(updatedPassword, userDto.getId());
		userService.loadUserByUsername(userDto.getUserName());
		url = "";
		url = "redirect:/listUser/edit-user-" + String.valueOf(id) + "/changePassword?success";
		return url;
	}

	// delete user

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = { "/delete-user-{id}" })
	public String deleteUser(@PathVariable long id, Principal principal, HttpServletRequest request,
			HttpServletResponse response) {
		int key_user_delete = userService.findById(id).getPower();
		Person p = userService.findByUsername(principal.getName());
		int key_admin = p.getPower();

		if (id == p.getId()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
		}

		if (key_admin >= key_user_delete) {
			userService.deleteTokenByIdPerson(id);
			userService.delete(id);
		}

		return "redirect:/listUser";
	}
}
