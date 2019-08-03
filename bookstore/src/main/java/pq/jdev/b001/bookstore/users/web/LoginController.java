package pq.jdev.b001.bookstore.users.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping({ "/" })
	public String root(Authentication authentication, ModelMap map) {
		if (authentication != null) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			List<String> roles = new ArrayList<String>();
			for (GrantedAuthority a : authorities) {
				roles.add(a.getAuthority());
			}
			
			if (isUser(roles)) {
				map.addAttribute("header", "header_user");
				map.addAttribute("footer", "footer_user");
				return "indexcontainer";
			} else 
			if (isAdmin(roles)) {
				map.addAttribute("header", "header_admin");
				map.addAttribute("footer", "footer_admin");
				return "indexcontainer";
			}
		}
		map.addAttribute("header", "header_login");
		map.addAttribute("footer", "footer_login");
		return "indexcontainer";
	}

	@PreAuthorize("!(hasRole('EMPLOYEE') OR hasRole('ADMIN'))")
	@GetMapping("/login")
	public String login(ModelMap map) {
		map.addAttribute("header", "header_login");
		map.addAttribute("footer", "footer_login");
		return "login";
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/user")
	public String userPage(ModelMap map) {
		map.addAttribute("header", "header_user");
		map.addAttribute("footer", "footer_user");
		return "user";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String getAdmin(ModelMap map) {
		map.addAttribute("user", getPrincipal());
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		return "admin";
	}

	@GetMapping(value = "/403")
	public String accessDeniedPage(Authentication authentication, ModelMap map) {
		if (authentication != null) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			List<String> roles = new ArrayList<String>();
			for (GrantedAuthority a : authorities) {
				roles.add(a.getAuthority());
			}
			
			if (isUser(roles)) {
				map.addAttribute("header", "header_user");
				map.addAttribute("footer", "footer_user");
			} else 
			if (isAdmin(roles)) {
				map.addAttribute("header", "header_admin");
				map.addAttribute("footer", "footer_admin");
			} 
		}else
		{
			map.addAttribute("header", "header_login");
			map.addAttribute("footer", "footer_login");
		}
		return "403";
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_EMPLOYEE")) {
			return true;
		}
		return false;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}

}
