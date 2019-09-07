package pq.jdev.b001.bookstore.users.constraint;

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
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(new LengthRule(0, 70)));
		PasswordValidator nonValidator = new PasswordValidator(Arrays.asList(new SpecialCharRuleEx(1)));

		RuleResult result = validator.validate(new PasswordData(password));
		RuleResult nonResult = nonValidator.validate(new PasswordData(password));
		if (result.isValid() && !nonResult.isValid()) {
			return true;
		}
		List<String> messages = validator.getMessages(result);
		String messageTemplate = messages.stream().collect(Collectors.joining(","));
		context.buildConstraintViolationWithTemplate(messageTemplate)
        .addConstraintViolation()
        .disableDefaultConstraintViolation();
		return false;
	}
}
