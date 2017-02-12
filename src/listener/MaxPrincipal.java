package listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class MaxPrincipal
 *
 */
@WebListener
public class MaxPrincipal implements ServletContextAttributeListener {

	private static double maxPrincipal;

	/**
	 * Default constructor.
	 */
	public MaxPrincipal() {
		this.maxPrincipal = 0;
	}

	/**
	 * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
	 * 
	 *      This method listens to when an attribute is added, and when it gets
	 *      added it will check if the new value of principal is higher than the
	 *      max, if so then update the max value
	 * 
	 */
	public void attributeAdded(ServletContextAttributeEvent attribute) {

		if (attribute.getName().equals("principal")) {
			try {
				double principal = Double.parseDouble(attribute.getValue().toString());

				if (principal > maxPrincipal) {
					this.maxPrincipal = principal;
					System.out.println("New max value");
				}

			} catch (Exception e) {
				System.out.println("Could not convert to double");
			}
		}

	}

	/**
	 * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
	 */
	public void attributeRemoved(ServletContextAttributeEvent attribute) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
	 * 
	 *      This method listens to when an attribute is changed, and when it
	 *      gets changed it will check if the new value of principal is higher
	 *      than the max, if so then update the max value
	 * 
	 */
	public void attributeReplaced(ServletContextAttributeEvent attribute) {

		if (attribute.getName().equals("principal")) {
			try {
				double principal = Double.parseDouble(attribute.getValue().toString());

				if (principal > maxPrincipal) {
					this.maxPrincipal = principal;
					System.out.println("New max value");
				}

			} catch (Exception e) {
				System.out.println("Could not convert to double");
			}
		}
	}

	/**
	 * Accessor for the max_principal variable
	 */
	public static double getMaxPrincipal() {
		return maxPrincipal;
	}

}
