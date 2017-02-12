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
	public Throttle() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// cast to an HttpServletRequest in order to get the session
		
		HttpServletRequest session = (HttpServletRequest) request;
		
		long last_time = 	session.getSession().getLastAccessedTime();
		long current_time = System.currentTimeMillis();
		
		if(current_time - last_time < 5000){
			System.out.println(current_time - last_time);
			request.getRequestDispatcher("/Throttle.jspx").forward(request, response);
		}else{
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
