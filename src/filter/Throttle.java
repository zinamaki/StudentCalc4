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

	// private client;
	// private session;
	// private timeOfLastRequest;
	private long lastAccessedTime;

	/**
	 * Default constructor.
	 */
	public Throttle() {
		// TODO Auto-generated constructor stub
		lastAccessedTime = 0;
	}

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
		// TODO Auto-generated method stub
		// place your code here

		// implement the logic that check the visit interval.. //note that you
		// can check the time of the last visit..
		
		HttpServletRequest session = (HttpServletRequest) request;
		
		long time_since = 	session.getSession().getLastAccessedTime();
		
		//System.out.println(time_since);
		
		if(time_since - lastAccessedTime < 5000){
			request.getRequestDispatcher("/Throttle.jspx").forward(request, response);
		}else{
			chain.doFilter(request, response);
		}
		lastAccessedTime = time_since;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
