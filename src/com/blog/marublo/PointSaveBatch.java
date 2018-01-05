
package com.blog.marublo;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.openqa.selenium.WebDriver;

import com.blog.marublo.AbstractPointController;
import com.blog.marublo.CalendarUtil;
import com.blog.marublo.DbUtil;
import com.blog.marublo.MatchUtil;
import com.blog.marublo.Point;
import com.blog.marublo.SettingInitializer;
import com.sun.jna.platform.win32.DBT;

public class PointSaveBatch extends AbstractPointController{
	public PointSaveBatch(){
		super();
	}

	public void execute() throws InterruptedException{

		String pointTemp ="";


		//MoppyController moppyController = new MoppyController();

			//モッピーログイン
		//moppyController.login(driver);

		//ポイント表示部をパース
		/*
		pointTemp = driver.findElement(By.cssSelector("#point_blinking")).getText();
		List<String> pointList = MatchUtil.getPointList(pointTemp,"[0-9]");
		Point moppyPoint = new Point("moppy", MatchUtil.createPoint(pointList), (CalendarUtil.todayUnderNormal()));
		*/
		//前日と今日の比較をするためコンペアを行うためデータ抽出
		/*
		List<Point> moppyList = DbUtil.selectPointData("moppy");
		if(moppyList != null && moppyList.size() > 0){
			moppyPoint.setYesterday(compareTheDayBefore(moppyList.get(0),moppyPoint));
		}
		*/
		//ポイントをDBに登録
		/*
		System.out.println("DBインサート");
		DbUtil.insertPointData(moppyPoint);
		*/

		//###############################################################################################
		//メールdeポイントログイン
		//maildeLogin(driver);
		//ポイント取得
		//String maildepoint = driver.findElement(By.cssSelector("#status > ul > li.point > p > strong")).getText();
		//maildepoint = MatchUtil.changeBlank(maildepoint);

		//前日と今日の比較をするためコンペアを行うためデータ抽出
		//List<Point> maildepointList = DbUtil.selectPointData("maildepoint");
		//インサート用データ
		/*
		Point maildepointPoint = new Point("maildepoint",maildepoint , CalendarUtil.todayUnderNormal());
		if(maildepointList != null && maildepointList.size() > 0){
			//コンペアしたデータをyesterdayにいれる(DBインサートには影響なし）
			maildepointPoint.setYesterday(compareTheDayBefore(maildepointList.get(0),maildepointPoint));
		}
		*/
		//###############################################################################################

		System.out.println("モバトクTOP");
		System.out.println("ログイン処理");
		driver.get("http://pc.mtoku.jp/");
		driver.findElement(By.name("mail")).sendKeys(SettingInitializer.getGmailId());
		driver.findElement(By.name("password")).sendKeys(SettingInitializer.MOPPY_PASSWORD);

		//ログインボタン
		driver.findElement(By.cssSelector("#global_header > div > div > form > fieldset > button")).
		click();
		System.out.println("モバトク：ログイン成功");

		//ポイント表示部をパース
		pointTemp = driver.findElement(By.cssSelector("#global_header > div > p > em")).getText();
		List<String> pointList2 = MatchUtil.getPointList(pointTemp,"[0-9]");


		//前日と今日の比較をするためコンペアを行うためデータ抽出
		List<Point> mobatokuTheDayBeforeList = DbUtil.selectPointData("mobatoku");
		//インサート用データ
		Point mobatokuPoint = new Point("mobatoku", MatchUtil.createPoint(pointList2), CalendarUtil.todayUnderNormal());

		if(mobatokuTheDayBeforeList != null && mobatokuTheDayBeforeList.size() > 0){
			//コンペアしたデータをyesterdayにいれる(DBインサートには影響なし）
			mobatokuPoint.setYesterday(compareTheDayBefore(mobatokuTheDayBeforeList.get(0),mobatokuPoint));
		}

		//ポイントをDBに登録
		System.out.println("DBインサート");
		DbUtil.insertPointData(mobatokuPoint);
		//###############################################################################################

		System.out.println("FCレッスン数確認");
		driver.get("http://133.242.235.62/workspace/");
		driver.findElement(By.name("loginId")).sendKeys(SettingInitializer.getGmailTrade());
		driver.findElement(By.name("loginPass")).sendKeys(SettingInitializer.MOPPY_PASSWORD);
		driver.findElement(By.cssSelector("body > div > div > div > div > div.panel-body > form > fieldset > input")).click();
		String fcPoint = driver.findElement(By.cssSelector("#page-wrapper > div:nth-child(2) > div:nth-child(1) > div > div > div > div.col-xs-9.text-right > div.huge")).getText();
		Point feelPoint = new Point("FC",fcPoint,CalendarUtil.todayUnderNormal());

		//前日と今日の比較をするためコンペアを行うためデータ抽出
		List<Point> fcTheDayBeforeList = DbUtil.selectPointData("FC");

		if(fcTheDayBeforeList != null && fcTheDayBeforeList.size() > 0){
			//コンペアしたデータをyesterdayにいれる(DBインサートには影響なし）
			feelPoint.setYesterday(compareTheDayBefore(fcTheDayBeforeList.get(0),feelPoint));
		}

		DbUtil.insertPointData(feelPoint);
		//###############################################################################################
		/*
		System.out.println("ハピトクポイント確認");

		driver.get("http://hapitas.jp/");
		driver.get("http://hapitas.jp/auth/signin/");

		System.out.println("ハピタス：ログインフォーム");
		driver.findElement(By.cssSelector("#email_main")).sendKeys(SettingInitializer.getGmailId());
		driver.findElement(By.cssSelector("#password_main")).sendKeys(SettingInitializer.MOPPY_PASSWORD);
		driver.findElement(By.cssSelector("#login_keep_main")).click();
		driver.findElement(By.cssSelector("#post_review > div > p.mb10.pt5 > input")).click();

		String hapipoint = driver.findElement(By.cssSelector("#global-navigation > div > ul.usernavi > li.usernavi-li.usernavi-status > a.usernavi-point")).getText();

		Point hapitasuPoint = new Point("hapitasu",hapipoint,CalendarUtil.todayUnderNormal());
		*/
		//前日と今日の比較をするためコンペアを行うためデータ抽出
		/*
		List<Point> hapitasBeforeList = DbUtil.selectPointData("hapitasu");

		if(hapitasBeforeList != null && hapitasBeforeList.size() > 0){
			//コンペアしたデータをyesterdayにいれる(DBインサートには影響なし）
			//hapitasuPoint.setYesterday(compareTheDayBefore(hapitasBeforeList.get(0),hapitasuPoint));
		}
		DbUtil.insertPointData(hapitasuPoint);
		*/
		//###############################################################################################
		
		
		//2018/1/5 b-monsterのレッスン情報を新たにインサートする
		System.out.println("b-monsterレッスン数取得");
		driver.get("https://www.b-monster.jp/");
		//#g-console > li:nth-child(1) > button
		driver.findElement(By.cssSelector("#g-console > li:nth-child(1) > button")).click();
		driver.findElement(By.name("login-username")).sendKeys(SettingInitializer.getGmailTrade());
		driver.findElement(By.name("login-password")).sendKeys(SettingInitializer.MOPPY_PASSWORD);
		driver.findElement(By.cssSelector("#login-btn")).click();
		System.out.println("b-monsterログイン成功");
		//マイページ表示
		//最新の1件をダウソ、インサート
		//#lesson-status > div:nth-child(2)
		//日付
		//#lesson-status > div:nth-child(2) > table > tbody > tr.form-group.latest-reserve > td > strong
		//会場
		//#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(2) > td > strong
		//時間
		//#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(3) > td > strong
		//レッスン名
		//#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(4) > td > strong
		//パフォーマー
		//#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(5) > td > strong
		//サンドバック
		//#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(6) > td > strong
		
		if(0 < driver.findElements(By.cssSelector("#lesson-status > div:nth-child(2)")).size()) {
			Lesson lesson = new Lesson();
			lesson.setLessonDate(driver.findElement(By.cssSelector("#lesson-status > div:nth-child(2) > table > tbody > tr.form-group.latest-reserve > td > strong")).getText());
			lesson.setLessonTenpo(driver.findElement(By.cssSelector("#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(3) > td > strong")).getText());
			lesson.setLessonTimeFrom(CalendarUtil.divideFrom(driver.findElement(By.cssSelector("#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(3) > td > strong")).getText()));
			lesson.setLessonTimeTo(CalendarUtil.divideTo(driver.findElement(By.cssSelector("#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(3) > td > strong")).getText()));
			lesson.setLessonName(driver.findElement(By.cssSelector("#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(4) > td > strong")).getText());
			lesson.setLessonInstructor(driver.findElement(By.cssSelector("#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(5) > td > strong")).getText());
			lesson.setLessonMashine(driver.findElement(By.cssSelector("#lesson-status > div:nth-child(2) > table > tbody > tr:nth-child(6) > td > strong")).getText());
			
			System.out.println("レッスン詳細:" + lesson.getLessonDate() + ":"  + lesson.getLessonInstructor());
			
			
			
			Lesson beforeLesson = new Lesson();
			beforeLesson = DbUtil.getBmonLessonDate();
			
			//初回のみ通るロジック
			if(beforeLesson == null || lesson != null) {
				System.out.println("レッスンインサートロジック");

				DbUtil.insertBmonData(lesson);
				System.out.println("レッスンインサート(初回):" + lesson.getLessonDate() + ":"  + lesson.getLessonInstructor());
			}
			
			
			if(beforeLesson != null || lesson != null) {
				System.out.println("レッスン2回目ロジック");

				//直近と最新のレッスンが同じかの判定
				if(MatchUtil.isUpdateLesson(beforeLesson,lesson)) {
					//異なっていたらインサート
					DbUtil.insertBmonData(lesson);
					System.out.println("レッスンインサート:" + lesson.getLessonDate() + ":"  + lesson.getLessonInstructor());
					
				}
			}
		}
		
		//b-monのレッスン数カウント
		int bmonCount = DbUtil.countBmonLesson();
		Point bmonPoint = new Point();
		bmonPoint.setName("b-monster");
		bmonPoint.setPoint(String.valueOf(bmonCount));
		System.out.println("bmonCount:" + String.valueOf(bmonCount));

		//jsonファイルを作成する
		List<Point> jsonList = new ArrayList<>();
		//jsonList.add(moppyPoint);
		jsonList.add(mobatokuPoint);
		//jsonList.add(maildepointPoint);
		//jsonList.add(hapitasuPoint);
		jsonList.add(feelPoint);
		jsonList.add(bmonPoint);

		createJson(jsonList);
		



		driver.quit();
	}

	public void moppyLogin(WebDriver driver){
		System.out.println("モッピー：TOP");
		driver.get("http://pc.moppy.jp/");

		System.out.println("モッピー：ログインフォーム");
		driver.get("https://ssl.pc.moppy.jp/login/");
		driver.findElement(By.name("mail")).sendKeys(SettingInitializer.MOPPY_USERID);
		driver.findElement(By.name("pass")).sendKeys(SettingInitializer.MOPPY_PASSWORD);
		//driver.findElement(By.name("autologin")).click();
		driver.findElement(By.cssSelector("#content  section  div  form  div  div  button  span")).
		click();
		System.out.println("モッピー：ログイン成功");
	}

	public void maildeLogin(WebDriver driver){
		System.out.println("メールdeポイント");
		System.out.println("ログイン処理");
		driver.get("https://member.pointmail.rakuten.co.jp/box/");
		driver.findElement(By.name("u")).sendKeys(SettingInitializer.getGmailId());
		driver.findElement(By.name("p")).sendKeys(SettingInitializer.MOPPY_PASSWORD);
		driver.findElement(By.cssSelector("#loginInner > p:nth-child(3) > input")).click();
		System.out.println("ログイン完了");
	}

	public static void createJson(List<Point> pointList){
		String tempStr = "";
		int count=0;
		tempStr = tempStr + "[";
		for(Point point : pointList){
			tempStr = tempStr +		""
							  +			""
							  +				"{"
							  +					"\"name\":\"" + point.getName() + "\","
							  +					"\"point\":\""+ point.getPoint()+ "\","
							  +					"\"date\":\"" + point.getDate() + "\","
							  +					"\"yesterday\":\"" + point.getYesterday() + "\""
							  +				 "}"
							  +			""
							  +		"";
			if(count != (pointList.size()-1)){
				tempStr = tempStr +	",";
			}

			count++;
		}
		tempStr = tempStr + "]";
		//json書き込み
		writeJsonFile(tempStr);

		//メール送信
		sendmail(pointList);



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
