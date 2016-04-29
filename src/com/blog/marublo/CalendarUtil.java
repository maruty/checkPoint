package com.blog.marublo;

import java.util.Calendar;

public class CalendarUtil {

	public static String todayUnderScore(){
		Calendar cal = Calendar.getInstance();

		return cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1) +"_" + cal.get(Calendar.DATE);
	}

	public static String todayUnderNormal(){
		Calendar cal = Calendar.getInstance();

		return cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) +"/" + cal.get(Calendar.DATE);
	}

}
