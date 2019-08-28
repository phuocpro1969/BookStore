package pq.jdev.b001.bookstore.users.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public CustomSuccessHandler() {
		super();
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		String url = request.getSession().getAttribute("url_pre_login").toString();
		System.out.println(url);
		if ((url.contains("/registration")) || (url.contains("/forgot-password")) || (url.contains("/reset-password")))
			url="/";
		if (response.isCommitted()) {
			return;
		}

		redirectStrategy.sendRedirect(request, response, url);
	}

}
