package com.blog.marublo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

public class MailUtil {
	public String HOST = "";
	public String USER = "";
	public String PASS = "";
	public String PORT = "";

	public Properties props = new Properties();
	public Session session;
	public Store store;
	public Folder folder;
	//メール
	Message[]messages;

	//コンストラクタで一通りの初期化を行う
	public MailUtil(String host,String user, String pass,String port) throws MessagingException{
		this.HOST = host;
		this.USER = user;
		this.PASS = pass;
		this.PORT = port;

		//メールコネクション関係
		props = new Properties();
		props.put("mail.imap.starttls.enable","true");
		props.put("mail.imap.auth", "true");
		props.put("mail.imap.socketFactory.port", PORT);
		props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.imap.socketFactory.fallback", "false");

		session = Session.getDefaultInstance(props,null);
		store = null;
		initMail();
	}

	//メール初期化
	public void initMail() throws MessagingException{
		store = session.getStore("imap");
		store.connect(HOST,USER,PASS);
		folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
	}

	public List<Mail> getMailList() throws MessagingException, IOException{
		messages = folder.getMessages();
		List<Mail> mailList = new ArrayList<>();
		for(Message message : messages){

			Part p = message;
			String mailContent ="";
			//メールにはhtmlメールとテキストメールがある　htmlメールはMimeタイプを判別して取得方法を変えないといけない
			//http://antlers.cis.ibaraki.ac.jp/PROGRAM/JAVA/MAIL/m133b.htm
			String mineType = p.getContentType();

			if(p.isMimeType("text/plain") || mineType.matches(".*EXT/HTML.*")){
				mailContent = (String) message.getContent();
				//System.out.println(mailContent);
			} else { // マルチパートの場合
				MimeMultipart mmp = (MimeMultipart) message.getContent();
				BodyPart body = mmp.getBodyPart(0);
				mailContent =  body.getContent().toString();
				//System.out.println(mailContent);
			}
			//mailオブジェクトにメールをセット
			Mail mail = new Mail(message.getSubject(), mailContent, message.getReceivedDate());
			mailList.add(mail);
		}
		return mailList;
	}
	//取得したメールを全て削除する
	public void allDeleteMail() throws MessagingException{
		messages = folder.getMessages();
		for(Message message : messages){
			message.setFlag(Flags.Flag.DELETED, true);
		}
	}

	//クローズ処理
	public void closeMail() throws MessagingException{
		folder.close(false);
		store.close();
	}



}
