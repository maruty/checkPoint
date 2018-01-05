package com.blog.marublo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtil {


	public static boolean hasMatch(String str, String preg){

		boolean matchFlag = str.matches(preg);

		return matchFlag;
	}

	public static List<String> getUrlList(String str, String preg){
		Pattern urlPattern = Pattern.compile(preg , Pattern.CASE_INSENSITIVE);
		Matcher matcher = urlPattern.matcher(str);
		List<String> urlList = new ArrayList<>();
		while(matcher.find()){
			urlList.add(matcher.group());
		}

		return urlList;
	}

	public static List<String> getPointList(String str, String preg){
		Pattern urlPattern = Pattern.compile(preg , Pattern.CASE_INSENSITIVE);
		Matcher matcher = urlPattern.matcher(str);
		List<String> urlList = new ArrayList<>();
		while(matcher.find()){
			urlList.add(matcher.group());
		}

		return urlList;
	}

	public static String createPoint(List<String> list){
		String point="";

		for(String str : list){
			point = point + str;
		}

		return point;
	}

	public static String changeBlank(String number) {




		return number.trim();
	}

	public static boolean isUpdateLesson(Lesson beforeLesson, Lesson lesson) {
		if(beforeLesson.getLessonDate().equals(lesson.getLessonDate()) && 
		   beforeLesson.getLessonTenpo().equals(lesson.getLessonTenpo()) && 
	       beforeLesson.getLessonName().equals(lesson.getLessonName()) && 
	    	   beforeLesson.getLessonInstructor().equals(lesson.getLessonInstructor()) &&
	    	   beforeLesson.getLessonTimeFrom().equals(lesson.getLessonTimeFrom())) {
			
			
			return false;
		}
		
		return true;
	}


}
