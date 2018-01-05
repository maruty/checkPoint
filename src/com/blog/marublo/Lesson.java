package com.blog.marublo;

public class Lesson {
	private String lessonUserId; //レッスンユーザーID
	private String lessonName; //レッスン名
	private String lessonInstructor; //レッスンインストラクター名
	private String lessonDate; //レッスン日付
	private String lessonTimeFrom; //レッスン時間開始時間
	private String lessonTimeTo; //レッスン終了時間
	private String lessonTenpo;
	private String lessonMashine; //座席
	
	public String getLessonUserId() {
		return lessonUserId;
	}
	public void setLessonUserId(String lessonUserId) {
		this.lessonUserId = lessonUserId;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public String getLessonInstructor() {
		return lessonInstructor;
	}
	public void setLessonInstructor(String lessonInstructor) {
		this.lessonInstructor = lessonInstructor;
	}
	public String getLessonDate() {
		return lessonDate;
	}
	public void setLessonDate(String lessonDate) {
		this.lessonDate = lessonDate;
	}
	public String getLessonTimeFrom() {
		return lessonTimeFrom;
	}
	public void setLessonTimeFrom(String lessonTimeFrom) {
		this.lessonTimeFrom = lessonTimeFrom;
	}
	public String getLessonTimeTo() {
		return lessonTimeTo;
	}
	public void setLessonTimeTo(String lessonTimeTo) {
		this.lessonTimeTo = lessonTimeTo;
	}
	public String getLessonTenpo() {
		return lessonTenpo;
	}
	public void setLessonTenpo(String lessonTenpo) {
		this.lessonTenpo = lessonTenpo;
	}

	public String getLessonMashine() {
		return lessonMashine;
	}
	public void setLessonMashine(String lessonMashine) {
		this.lessonMashine = lessonMashine;
	}

}
