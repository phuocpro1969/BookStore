package pq.jdev.b001.bookstore.users.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

import pq.jdev.b001.bookstore.users.service.SpecialCharRuleEx;


public class NameConstraintValidator implements ConstraintValidator<ValidName, String> {

	@Override
	public void initialize(ValidName constraintAnnotation) {

	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(new LengthRule(0, 70)));
		PasswordValidator nonValidator = new PasswordValidator(Arrays.asList(new SpecialCharRuleEx(1)));

		RuleResult result = validator.validate(new PasswordData(contactField));
		RuleResult nonResult = nonValidator.validate(new PasswordData(contactField));
		if (result.isValid() && !nonResult.isValid()) {
			return true;
		}
		
		List<String> messages = new ArrayList<String>();
		if (!result.isValid())
			messages.add("Name must be length [1-70]");
		if (nonResult.isValid())
			messages.add("Name must not have special character");
		String messageTemplate = messages.stream().collect(Collectors.joining(","));
		context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation()
				.disableDefaultConstraintViolation();
		return false;
	}
}
