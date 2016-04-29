package com.blog.marublo;

import java.util.Date;

public class Mail {

	public String subject = "";
	public String body = "";
	public Date date;

	public Mail(String subject, String body, Date date){
		this.subject = subject;
		this.body = body;
		this.date = date;
	}

}
