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

	private double max_principal;

	/**
	 * Default constructor.
	 */
	public MaxPrincipal() {
		// TODO Auto-generated constructor stub
		this.max_principal = 0;
	}

	/**
	 * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
	 */
	public void attributeAdded(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub

		//System.out.println("Attribute + " + arg0.getName() +"changed " + arg0.getValue());
		
		if(arg0.getName().equals("principal")){
			try{
				double principal = Double.parseDouble(arg0.getValue().toString());
				
				if(principal > max_principal){
					this.max_principal = principal;
					System.out.println("New max value");
				}
				
			}catch(Exception e){
				System.out.println("Could not convert to double");
			}
		}
		
		
	}

	/**
	 * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
	 */
	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
	 */
	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub

		System.out.println("Attribute replaced " + arg0.getValue());

		if(arg0.getName().equals("principal")){
			try{
				double principal = Double.parseDouble(arg0.getValue().toString());
				
				if(principal > max_principal){
					this.max_principal = principal;
					System.out.println("New max value");
				}
				
			}catch(Exception e){
				System.out.println("Could not convert to double");
			}
		}
	}

	public double getMaxPrincipal() {
		return this.max_principal;
	}

}
