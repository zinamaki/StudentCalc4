package model;

import javax.servlet.http.HttpServletRequest;

public class Loan {

	public static double computePayment(Double principal, Double period, Double interest, Double graceInterest,
			Double gracePeriod, Double fixedInterest, boolean grace) throws Exception {

		// throw an exception if invalid parameters
		
		// if (period > 2) {
		// throw (new Exception());
		// }

		interest /= 1200;
		Double monthlyPayment = (interest * principal) / (1 - Math.pow(1 + interest, -period));

		if (grace) {
			// if grace is enabled
			//graceInterest /= 100;
			monthlyPayment += (graceInterest / gracePeriod);
		}

		return monthlyPayment;

	}

	public static double computeGraceInterest(Double principal, Double gracePeriod, Double interest,
			Double fixedInterest, boolean grace) throws Exception {

		Double graceInterest;

		interest /= 100;
		fixedInterest /= 100;

		if (grace) {
			graceInterest = principal * gracePeriod * ((interest + fixedInterest) / 12);
		} else {
			graceInterest = 0.0;
		}

		// throw an exception if invalid parameters

		 if ( principal < 0 || gracePeriod < 0 || interest < 0 || fixedInterest < 0) {
			 throw (new Exception());
		 }

		return graceInterest;
	}
}
