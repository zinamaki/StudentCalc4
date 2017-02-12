package filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class Throttle
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/Start", "/Startup" }, servletNames = {
		"Start" })
public class Throttle implements Filter {

	/**
	 * Default constructor.
	 */
	public Throttle() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 * 
	 *      This method checks if the user has made a request in the last 5
	 *      seconds, if they have, then send them to the throttling page, if
	 *      they have not then take them to the requested page
	 * 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// cast to an HttpServletRequest in order to get the session

		HttpServletRequest session = (HttpServletRequest) request;

		long last_time = session.getSession().getLastAccessedTime();
		long current_time = System.currentTimeMillis();

		// check if they have made a request within 5000ms (or 5 seconds)

		boolean enable = false;
		
		if(!enable){
			chain.doFilter(request, response);
			return;
		}
		
		if (current_time - last_time < 5000) {

			// if they have made a request within 5 seconds, then send them to
			// the throttle page

			request.getRequestDispatcher("/Throttle.jspx").forward(request, response);
		} else {
			// if they have not made a request within 5 seconds, then process
			// their request

			chain.doFilter(request, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
