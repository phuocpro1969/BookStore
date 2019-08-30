package pq.jdev.b001.bookstore.users.constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneConstraintValidator implements ConstraintValidator<ValidPhone, String> {

	@Override
	public void initialize(ValidPhone constraintAnnotation) {

	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext context) {
		List<String> messages = new ArrayList<String>();
		if (contactField != null && !(contactField.matches("\\d{10}")
				|| contactField.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")
				|| contactField.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")
				|| contactField.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")))
			messages.add("Phone Number must be wrong");
		if (contactField != null && (!(contactField.length() > 8) || !(contactField.length() < 14)))
			messages.add("Phone Number must length [9-13]");
		String messageTemplate = messages.stream().collect(Collectors.joining(","));
		context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation()
				.disableDefaultConstraintViolation();
		return contactField != null && (contactField.matches("\\d{10}")
				|| contactField.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")
				|| contactField.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")
				|| contactField.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
				&& (contactField.length() > 8) && (contactField.length() < 14);
	}
}
