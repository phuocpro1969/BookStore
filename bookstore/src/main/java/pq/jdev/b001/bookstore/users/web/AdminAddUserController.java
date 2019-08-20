package pq.jdev.b001.bookstore.users.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.model.Role;
import pq.jdev.b001.bookstore.users.service.UserService;
import pq.jdev.b001.bookstore.users.web.dto.AdminDto;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/listUser/adminAddUser")
public class AdminAddUserController {

	@Autowired
	private UserService userService;

	@ModelAttribute("person")
	public AdminDto adminDto() {
		return new AdminDto();
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
	@PreAuthorize("hasRole('ADMIN')")
	public String showRegistrationForm(ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		return "adminAddUser";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("person") @Valid AdminDto userDto, BindingResult result,
			ModelMap map, Authentication authentication) {
		if (isAdmin(roleAuthentication(authentication))) {
			Person existingUserName = userService.findByUsername(userDto.getUserName());
			Person existingEmail = userService.findByEmail(userDto.getEmail());
			if (existingEmail != null || existingUserName != null) {
				result.rejectValue("email", null, "There is email or username already an account registered");
			}

			if (result.hasErrors()) {
				map.addAttribute("header", "header_admin");
				map.addAttribute("footer", "footer_admin");
				return "adminAddUser";
			}

			userService.save(userDto);
		}
		return "redirect:/listUser/adminAddUser?success";
	}

	public List<String> roleAuthentication(Authentication authentication) {
		List<String> roles = new ArrayList<String>();
		if (authentication != null) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority a : authorities) {
				roles.add(a.getAuthority());
			}
		}
		return roles;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}
}
