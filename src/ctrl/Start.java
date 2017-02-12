package ctrl;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Loan;

/**
 * Servlet implementation class Start
 */
@WebServlet({ "/Start", "/Startup", "/Startup/*" })
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * These three parameters store how the jsp is retrieving these values
	 * 
	 */

	private static final String GINTEREST = "graceInterest";
	private static final String MPAYMENT = "monthlyPayment";
	private static final String EMESSAGE = "errorMessage";

	private static final String PRINCIPAL = "principal";
	private static final String INTEREST = "interest";
	private static final String PERIOD = "period";

	/*
	 * The next two parameters hold what will be displayed as the value of the
	 * calculation
	 */

	private double graceInterest = 0;
	private double monthlyPayment = 0;

	/*
	 * This is the model that computes the graceInterest and monthlyPayments
	 */

	private Loan loan;

	/*
	 * These two parameters are used in displaying a meaningful error message
	 * and to flag that an error has occurred
	 */

	private String errorMessage = "";
	private boolean error = false; // is there an error?

	/*
	 * These three parameters store what the user has input, in case they input
	 * invalid parameters and the form must be refreshed
	 */

	private String principal;
	private String interest;
	private String period;

	private boolean firstTime = true;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
	}

	public void init() throws ServletException {
		this.loan = new Loan();
	}

	/*
	 * This method returns whether the grace checkbox has been enabled or not
	 * return true if grace box has been checked return false if grace box has
	 * not been checked
	 */
	private boolean graceEnabled(HttpServletRequest request) {

		String input_grace = request.getParameter("inputGrace");

		boolean grace = false;

		if (input_grace == null) {
			grace = false;
		} else if (input_grace.equals("on")) {
			grace = true;
		}

		return grace;

	}

	/*
	 * This method gets the three parameters from the form If the three input
	 * parameters are all doubles, then it runs the model's calculation If the
	 * model's calculation throws an exception, this means that the inputs are
	 * invalid In that case, you must display a meaningful error message
	 */
	private void calculatePayment(HttpServletRequest request) {

		String input_principal = request.getParameter("inputPrincipal");
		String input_interest = request.getParameter("inputInterest");
		String input_period = request.getParameter("inputPeriod");

		this.principal = input_principal;
		this.period = input_period;
		this.interest = input_interest;

		this.error = validateInputisDouble(input_principal, input_interest, input_period);

		if (!this.error) {
			Double r = Double.parseDouble(input_interest);
			Double A = Double.parseDouble(input_principal);
			Double n = Double.parseDouble(input_period);

			Double fixedInterest = Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest"));
			Double gracePeriod = Double.parseDouble(this.getServletContext().getInitParameter("gracePeriod"));

			try {
				this.graceInterest = loan.computeGraceInterest(A, gracePeriod, r, fixedInterest, graceEnabled(request));
				this.monthlyPayment = loan.computePayment(A, n, r, this.graceInterest, gracePeriod, fixedInterest,
						graceEnabled(request));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				this.errorMessage = "Invalid paramaters";
				this.error = true;
				errorJSP();
			}

		} else {
			if (!firstTime) {
				this.errorMessage = "found an error";
				errorJSP();
			}

		}

	}

	/*
	 * This method check whether the three inputs from the form are doubles, if
	 * they are not then flag an error
	 */
	private boolean validateInputisDouble(String input_principal, String input_interest, String input_period) {
		// TODO Auto-generated method stub

		if (input_principal == null || input_interest == null || input_period == null) {
			return true;
		} else {
			try {
				Double.parseDouble(input_principal);
				Double.parseDouble(input_period);
				Double.parseDouble(input_interest);
			} catch (Exception e) {
				return true;
			}
		}

		return false;
	}

	/*
	 * This method serves the jsp If the user has submitted a result with no
	 * error, take them to the Result page Otherwise, keep them on the main form
	 * page
	 */
	private void serveJSP(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String target = "/UI.jspx";
		String submitParameter = request.getParameter("submit");

		String restartParameter = request.getParameter("restart");

		if (restartParameter != null && restartParameter.equals("Restart")) {
			firstTime = true;
			System.out.println("Just hit restart");
			this.errorMessage = "";
			errorJSP();
		} else if (submitParameter != null && submitParameter.equals("Submit")) {

			if (!error) {

				System.out.println("submitted");
				target = "/Result.jspx";
			}

		} else {

			System.out.println("first time");
			// firstTime = true;
		}
		request.getRequestDispatcher(target).forward(request, response);
	}

	/*
	 * This method updates the error message in the jsp with the appropriate
	 * message
	 */
	private void errorJSP() {
		this.getServletContext().setAttribute(EMESSAGE, this.errorMessage);
	}

	/*
	 * This method updates the attributes in the jsp files with the result of
	 * the calculations
	 */
	private void updateJSP(HttpServletRequest request) {

		this.getServletContext().setAttribute(MPAYMENT, this.monthlyPayment);
		this.getServletContext().setAttribute(GINTEREST, this.graceInterest);

		this.getServletContext().setAttribute(PRINCIPAL, this.principal);
		this.getServletContext().setAttribute(INTEREST, this.interest);
		this.getServletContext().setAttribute(PERIOD, this.period);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		calculatePayment(request);
		updateJSP(request);
		serveJSP(request, response);
		firstTime = false;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
