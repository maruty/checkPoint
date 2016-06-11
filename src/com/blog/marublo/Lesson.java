package com.blog.marublo;

public class Lesson {
	private String lesson;
	private String ir;

	public Lesson(String lesson, String ir){
		this.lesson = lesson;
		this.ir = ir;

	}


	public String getLesson() {
		return lesson;
	}
	public void setLesson(String lesson) {
		this.lesson = lesson;
	}
	public String getIr() {
		return ir;
	}
	public void setIr(String ir) {
		this.ir = ir;
	}

}
