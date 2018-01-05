package com.blog.marublo;

import java.util.Calendar;

public class CalendarUtil {

	public static String todayUnderScore(){
		Calendar cal = Calendar.getInstance();

		return cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1) +"_" + cal.get(Calendar.DATE);
	}

	public static String todayUnderNormal(){
		//今日の日付をyyyy/mm/ddで返す
		Calendar cal = Calendar.getInstance();

		return cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) +"/" + cal.get(Calendar.DATE);
	}
	
	public static String divideFrom(String str) {
		//前後の空白削除
		String[] array = str.split("-");
		return array[0].trim();
	}
	
	public static String divideTo(String str) {
		//前後の空白削除
		String[] array = str.split("-");
		return array[1].trim();
	}

}
