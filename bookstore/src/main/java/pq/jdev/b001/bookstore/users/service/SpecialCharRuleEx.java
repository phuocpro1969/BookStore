package pq.jdev.b001.bookstore.users.service;

import org.passay.PasswordUtils;
import org.passay.SpecialCharacterRule;

public class SpecialCharRuleEx extends SpecialCharacterRule {
	public static final String CHARNEWS = "\"#$%&'*+/:;<=>?@[\\]^`{|}~";

	@Override
	public String getValidCharacters() {
		return CHARNEWS;
	}
	
	@Override
	  protected String getCharacterTypes(final String password)
	  {
	    return PasswordUtils.getMatchingCharacters(CHARNEWS, password);
	  }
	
	public SpecialCharRuleEx() {
	}

	public SpecialCharRuleEx(final int num) {
		setNumberOfCharacters(num);
	}
}
