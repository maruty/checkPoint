package com.blog.marublo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class DkeiUserScreeningBatch extends AbstractPointController{
	public DkeiUserScreeningBatch(){
		super();
	}

	public void execute() throws InterruptedException{



		//PCMAXDMMのTOPにアクセスする
		driver.get("http://max.dmm.co.jp/mobile/");
		//ログインボタンをクリック
		System.out.println("ログインボタンをクリック");
		driver.findElement(By.cssSelector("#start_box > ul > li.login > a")).click();

		Thread.sleep(3000);
		System.out.println("ログインフォーム入力");
		driver.findElement(By.name("login_id")).sendKeys(SettingInitializer.getGendamaId());
		driver.findElement(By.name("password")).sendKeys(SettingInitializer.getGendamaPass());
		driver.findElement(By.cssSelector("#loginbutton_script_on > span > input[type='submit']")).click();

		Thread.sleep(3000);
		System.out.println("ログイン完了");
		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#pcmax_header > div:nth-child(6) > a")).click();

		//プロフィール検索ページ
		JavascriptExecutor jexec = (JavascriptExecutor) driver;
		//System.out.println("js実行");
		//#to_age > option:nth-child(15)
		//#to_age > option:nth-child(15)
		jexec.executeScript("document.#to_age.options[14].selected= true;");
		//System.out.println("js実行されたはず");
		Thread.sleep(3000);
		jexec.executeScript("document.#sort.options[1].selected= true;");

		System.exit(0);




	}
	private static void sendmail(List<Point> pointList) {
		 final String to = "yanagisawa.trade@gmail.com";
		    final String from = SettingInitializer.getGmailId();

		    // Google account mail address
		    final String username =  SettingInitializer.getGmailId();
		    // Google App password
		    final String password = SettingInitializer.getGendamaPass();

		    //final String charset = "ISO-2022-JP";
		    final String charset = "UTF-8";

		    final String encoding = "base64";

		    // for gmail
		    String host = "smtp.gmail.com";
		    String port = "587";
		    String starttls = "true";

		    Properties props = new Properties();
		    props.put("mail.smtp.host", host);
		    props.put("mail.smtp.port", port);
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", starttls);

		    props.put("mail.smtp.connectiontimeout", "10000");
		    props.put("mail.smtp.timeout", "10000");

		    props.put("mail.debug", "true");

		    String content = "";
		    String subject = CalendarUtil.todayUnderNormal() + ":ポイント取得処理結果";

		    content = content + "ポイント取得処理実行 \n";

		    for(Point point : pointList){
		    	content = content + point.getName() + ":" + point.getPoint() + "pt" + "　前日比" + point.getYesterday() + "\n";
		    }


		    Session session = Session.getInstance(props,
		    new javax.mail.Authenticator() {
		       protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(username, password);
		       }
		    });

		    try {
		      MimeMessage message = new MimeMessage(session);

		      // Set From:
		      message.setFrom(new InternetAddress(from, "point"));
		      // Set ReplyTo:
		      message.setReplyTo(new Address[]{new InternetAddress(from)});
		      // Set To:
		      message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

		      message.setSubject(subject, charset);
		      message.setText(content, charset);

		      message.setHeader("Content-Transfer-Encoding", encoding);

		      Transport.send(message);

		    } catch (MessagingException e) {
		      throw new RuntimeException(e);
		    } catch (UnsupportedEncodingException e) {
		      throw new RuntimeException(e);
		    }

		  }



	//IO書き込み
	public static void writeJsonFile(String str){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("/var/www/html/json/point.json"));
			bw.write(str);
			bw.flush();
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//前日との比較
	public String compareTheDayBefore(Point day1 , Point day2){
		String str = "";
		//前日がnullの場合は初めて来たので "±0"
		if(day1 == null){
			str = "±0";
		}else{
			int num1 =Integer.parseInt(day1.getPoint());
			int num2 =Integer.parseInt(day2.getPoint());
			int result = 0;


			if(num1 > num2){

				result = num1 - num2;
				str = "-" + result;
			}else{
				result = num2 - num1;
				str = "+" + result;
			}
		}

		return str;
	}

}
