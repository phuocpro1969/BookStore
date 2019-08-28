package pq.jdev.b001.bookstore.users.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pq.jdev.b001.bookstore.users.model.Mail;
import pq.jdev.b001.bookstore.users.model.PasswordResetToken;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.service.EmailService;
import pq.jdev.b001.bookstore.users.service.ModuleRunFirst;
import pq.jdev.b001.bookstore.users.service.UserService;
import pq.jdev.b001.bookstore.users.web.dto.PasswordForgotDto;

@PreAuthorize("!(hasRole('EMPLOYEE') OR hasRole('ADMIN'))")
@Controller
@RequestMapping("/forgot-password")
public class PasswordFogotController {
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ModuleRunFirst moduleRunFirst;

	@ModelAttribute("forgotPasswordForm")
	public PasswordForgotDto forgotPasswordDto() {
		return new PasswordForgotDto();
	}

	@GetMapping
	public String displayForgotPasswordPage(ModelMap map, Model model, Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model);
		moduleRunFirst.headerFooter(authentication, map, roles);
		return "user_admin/no_login/forgot-password";
	}

	@PostMapping
	public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form,
			BindingResult result, HttpServletRequest request, ModelMap map, Model model,
			Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model);
		moduleRunFirst.headerFooter(authentication, map, roles);

		if (result.hasErrors()) {
			moduleRunFirst.leftBar_cate_pub(model);
			moduleRunFirst.headerFooter(authentication, map, roles);
			return "user_admin/no_login/forgot-password";
		}

		Person person = userService.findByEmail(form.getEmail());
		if (person == null) {
			result.rejectValue("email", null, "We could not find an account for that e-mail address.");
			return "user_admin/no_login/forgot-password";
		}

		PasswordResetToken token = new PasswordResetToken();
		token.setToken(UUID.randomUUID().toString());
		token.setPerson(person);

		token.setExpiryDate();
		userService.saveToken(token);

		Mail mail = new Mail();
		mail.setFrom("user1@testmail.com");
		mail.setTo(person.getEmail());
		mail.setSubject("Password reset request");

		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("token", token);
		modelMap.put("person", person);
		modelMap.put("signature", "http://localhost:8090");
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		modelMap.put("resetUrl", url + "/reset-password?token=" + token.getToken());
		mail.setModel(modelMap);
		emailService.sendEmail(mail);

		return "redirect:/forgot-password?success";

	}
}
