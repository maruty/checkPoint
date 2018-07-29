
package com.blog.marublo;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PointSaveBatch extends AbstractPointController{
	public PointSaveBatch(){
		super();
	}

	public void execute() throws InterruptedException, IOException{
		String pointTemp ="";
		//MoppyController moppyController = new MoppyController();

		//モッピーログイン
		//moppyController.login(driver);

		//ポイント表示部をパース
		/*
		pointTemp = driver.findElement(By.cssSelector("#point_blinking")).getText();
		List<String> pointList = MatchUtil.getPointList(pointTemp,"[09]");
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
		System.out.println("Moppy");
		System.out.println("ログイン処理");
		//ログインフォームからスタート
		System.out.println("モッピー：ログインフォーム");
		//#content > section > div > div.box-login > form > div > div.login-btn > button
		driver.get("https://ssl.pc.moppy.jp/login/");
		driver.manage().timeouts().implicitlyWait(3 ,TimeUnit.SECONDS);
		driver.findElement(By.name("mail")).sendKeys(SettingInitializer.MOPPY_USERID);
		driver.findElement(By.name("pass")).sendKeys( SettingInitializer.MOPPY_PASSWORD);
		driver.manage().timeouts().implicitlyWait(3 ,TimeUnit.SECONDS);

		//
		driver.findElement(By.cssSelector(".a-btn__login")).click();
		driver.manage().timeouts().implicitlyWait(10 ,TimeUnit.SECONDS);
		Thread.sleep(4000);

		System.out.println("モッピー：ログイン成功");
		//pointパース
		//driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(3 ,TimeUnit.SECONDS);


		List<WebElement>moppyPointList = driver.findElements(By.cssSelector(".odometer"));
		String moppyPoint = "";
		for(WebElement element : moppyPointList) {
			moppyPoint = moppyPoint + element.getText();
		}



		System.out.println("moppyPoint:"+ moppyPoint );
		PointSaveBatch.getCapture(driver,"moppy");

		moppyPoint = moppyPoint.replaceAll("[\\r\\n]+", "");

		//前日と今日の比較をするためコンペアを行うためデータ抽出
		List<Point> moppyTheDayBeforeList = DbUtil.selectPointData("moppy");
		//インサート用データ
		Point moppy = new Point("moppy", moppyPoint, CalendarUtil.todayUnderNormal());

		if(moppyTheDayBeforeList != null && moppyTheDayBeforeList.size() > 0){
			//コンペアしたデータをyesterdayにいれる(DBインサートには影響なし）
			moppy.setYesterday(compareTheDayBefore(moppyTheDayBeforeList.get(0),moppy));
		}

		//ポイントをDBに登録
		System.out.println("DBインサート");
		DbUtil.insertPointData(moppy);

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

		if(driver.findElements(By.cssSelector("#color_box_close")).size() > 0) {
			driver.findElement(By.cssSelector("#color_box_close")).click();
			System.out.println("モバトク：広告消した");
		}

		//ポイント表示部をパース
		//#global_header > div > p > em
		//.header__userinfo > em:nth-child(2)
		pointTemp = driver.findElement(By.cssSelector(".header__userinfo > em:nth-child(2)")).getText();
		System.out.println("モバトク：" + pointTemp);
		//replaceAll("[^0-9]","")

		pointTemp = pointTemp.trim();
		pointTemp = pointTemp.replaceAll(",","");
		pointTemp = pointTemp.replaceAll("pt","");

		//List<String> pointList2 = MatchUtil.getPointList(pointTemp,"/[^0-9]/");

		//System.out.println("モバトク、パース：" + pointList2.get(0));


		//前日と今日の比較をするためコンペアを行うためデータ抽出
		List<Point> mobatokuTheDayBeforeList = DbUtil.selectPointData("mobatoku");
		//インサート用データpointTemp
		//Point mobatokuPoint = new Point("mobatoku", MatchUtil.createPoint(pointList2), CalendarUtil.todayUnderNormal());
		Point mobatokuPoint = new Point("mobatoku", pointTemp, CalendarUtil.todayUnderNormal());


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

		driver.findElement(By.cssSelector(".btn")).click();

		driver.manage().timeouts().implicitlyWait(50 ,TimeUnit.SECONDS);
		Thread.sleep(3000);

		String fcPoint = driver.findElement(By.cssSelector(".panel-red > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1)")).getText();
		Point feelPoint = new Point("FC",fcPoint,CalendarUtil.todayUnderNormal());

		//前日と今日の比較をするためコンペアを行うためデータ抽出
		List<Point> fcTheDayBeforeList = DbUtil.selectPointData("FC");

		if(fcTheDayBeforeList != null && fcTheDayBeforeList.size() > 0){
			//コンペアしたデータをyesterdayにいれる(DBインサートには影響なし）
			feelPoint.setYesterday(compareTheDayBefore(fcTheDayBeforeList.get(0),feelPoint));
		}

		//ポイントをDBに登録
		System.out.println("DBインサート");
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

		String hapipoint = driver.findElement(By.cssSelector("#globalnavigation > div > ul.usernavi > li.usernavili.usernavistatus > a.usernavipoint")).getText();

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


		//2018/1/5 bmonsterのレッスン情報を新たにインサートする
		System.out.println("bmonsterレッスン数取得");
		driver.get("https://www.b-monster.jp/");
		//#gconsole > li:nthchild(1) > button
		//button.btn
		driver.findElement(By.cssSelector(".button.btn")).click();
		driver.findElement(By.name("loginusername")).sendKeys(SettingInitializer.getGmailTrade());
		driver.findElement(By.name("loginpassword")).sendKeys(SettingInitializer.MOPPY_PASSWORD);
		driver.findElement(By.cssSelector("#loginbtn")).click();
		System.out.println("bmonsterログイン成功");
		//マイページ表示
		//最新の1件をダウソ、インサート
		//#lessonstatus > div:nthchild(2)
		//日付
		//#lessonstatus > div:nthchild(2) > table > tbody > tr.formgroup.latestreserve > td > strong
		//会場
		//#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(2) > td > strong
		//時間
		//#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(3) > td > strong
		//レッスン名
		//#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(4) > td > strong
		//パフォーマー
		//#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(5) > td > strong
		//サンドバック
		//#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(6) > td > strong

		//#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(2) > td > strong
		Thread.sleep(2000);
		System.out.println("bmonster：マイページ");

		if(0 < driver.findElements(By.cssSelector("#lessonstatus > div:nthchild(2)")).size()) {
			System.out.println("bmonster：マイページロジック入った");
			Lesson lesson = new Lesson();
			lesson.setLessonDate(driver.findElement(By.cssSelector("#lessonstatus > div:nthchild(2) > table > tbody > tr.formgroup.latestreserve > td > strong")).getText());
			lesson.setLessonTenpo(driver.findElement(By.cssSelector("#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(2) > td > strong")).getText());
			lesson.setLessonTimeFrom(CalendarUtil.divideFrom(driver.findElement(By.cssSelector("#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(3) > td > strong")).getText()));
			lesson.setLessonTimeTo(CalendarUtil.divideTo(driver.findElement(By.cssSelector("#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(3) > td > strong")).getText()));
			lesson.setLessonName(driver.findElement(By.cssSelector("#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(4) > td > strong")).getText());
			lesson.setLessonInstructor(driver.findElement(By.cssSelector("#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(5) > td > strong")).getText());
			lesson.setLessonMashine(driver.findElement(By.cssSelector("#lessonstatus > div:nthchild(2) > table > tbody > tr:nthchild(6) > td > strong")).getText());

			System.out.println("レッスン詳細:" + lesson.getLessonDate() + ":"  + lesson.getLessonInstructor());



			Lesson beforeLesson = new Lesson();
			beforeLesson = DbUtil.getBmonLessonDate();

			//初回のみ通るロジック
			if(beforeLesson == null && lesson != null) {
				System.out.println("レッスンインサートロジック");

				DbUtil.insertBmonData(lesson);
				System.out.println("レッスンインサート(初回):" + lesson.getLessonDate() + ":"  + lesson.getLessonInstructor() + lesson.getLessonTimeFrom());
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

		//前日と今日の比較をするためコンペアを行うためデータ抽出
		List<Point> bmonTheDayBeforeList = DbUtil.selectPointData("bmonster");

		//bmonのレッスン数カウント
		int bmonCount = DbUtil.countBmonLesson();
		//Point bmonPoint = new Point();
		//bmonPoint.setName("bmonster");
		//bmonPoint.setPoint(String.valueOf(bmonCount));
		System.out.println("bmonCount:" + String.valueOf(bmonCount));

		//インサート用データ
		Point bmonPoint = new Point("bmonster", String.valueOf(bmonCount), CalendarUtil.todayUnderNormal());

		if(bmonTheDayBeforeList != null && bmonTheDayBeforeList.size() > 0){
			//コンペアしたデータをyesterdayにいれる(DBインサートには影響なし）
			bmonPoint.setYesterday(compareTheDayBefore(bmonTheDayBeforeList.get(0),bmonPoint));
		}
		//ポイントをDBに登録
		System.out.println("DBインサート");
		DbUtil.insertPointData(bmonPoint);


		//持株会確認処理
		System.out.println("持株会処理");
		driver.get("https://mochikabukai.mizuho-sc.com/kai/KiLoginPre.do");
		PointSaveBatch.getCapture(driver,"kabu");
		Thread.sleep(3000);
		WebElement fr = driver.findElement(By.name("LEFT"));
		driver.switchTo().frame(fr);

		driver.findElement(By.cssSelector("#contents-inner > form > dl:nth-child(2) > dd:nth-child(2) > input")).sendKeys(SettingInitializer.MOTIKABU_CODE);
		driver.findElement(By.cssSelector("#contents-inner > form > dl:nth-child(3) > dd:nth-child(2) > input")).sendKeys(SettingInitializer.MOTIKABU_ID);
		driver.findElement(By.cssSelector("#tblloginbtn > input")).sendKeys(SettingInitializer.MOTIKABU_PASS);
		driver.findElement(By.cssSelector("#bt > input[type=\"image\"]")).click();

		//パスワード変更画面を後で変更
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		WebElement fr2 = driver.findElement(By.name("LEFT"));
		driver.switchTo().frame(fr2);
		PointSaveBatch.getCapture(driver,"kabu");
		driver.findElement(By.cssSelector("#line > a > img")).click();
		//メイン
		driver.switchTo().defaultContent();
		WebElement fr3 = driver.findElement(By.name("LEFT"));
		driver.switchTo().frame(fr3);

		PointSaveBatch.getCapture(driver,"kabu");

		driver.get("https://mochikabukai.mizuho-sc.com/kai/KiSyokMenu.do");

		//#content > ol > li:nth-child(2) > a
		driver.findElement(By.cssSelector("#content > ol > li:nth-child(2) > a")).click();

		PointSaveBatch.getCapture(driver,"kabu");

		Thread.sleep(4000);


		PointSaveBatch.getCapture(driver,"kabu");

		//株除去(小数点.）

		//#content > table.tblbasic > tbody > tr:nth-child(4) > td
		System.out.println(driver.findElement(By.xpath("//*[@id=\"content\"]/table[2]/tbody/tr[4]/td")).getText());
		String kabusu[] = driver.findElement(By.xpath("//*[@id=\"content\"]/table[2]/tbody/tr[4]/td")).getText().split("\\.");
		//,までをいったん取得
		System.out.println( driver.findElement(By.xpath("//*[@id=\"content\"]/table[2]/tbody/tr[7]/td")).getText());
		String kakaku[] = driver.findElement(By.xpath("//*[@id=\"content\"]/table[2]/tbody/tr[7]/td")).getText().split(",");
		String kakakuMaster = kakaku[0] + kakaku[1].substring(0, 3);

		int goukeiKingaku = Integer.parseInt(kabusu[0]) * Integer.parseInt(kakakuMaster);
		System.out.println("株数:" + kabusu[0] + " 取得平均単価：" + kakakuMaster + " 合計金額：" + goukeiKingaku );
		System.out.println("株数:" + kabusu[0] + " 取得平均単価：" + kakakuMaster + " 合計金額：" + goukeiKingaku );
		//インサート用データ
		Point kabu = new Point("R-kabu", String.valueOf(goukeiKingaku) , CalendarUtil.todayUnderNormal());
		//ポイントをDBに登録
		System.out.println("DBインサート");
		DbUtil.insertPointData(kabu);

		System.out.println("json作成");

		//jsonファイルを作成する
		List<Point> jsonList = new ArrayList<>();
		jsonList.add(moppy);
		jsonList.add(mobatokuPoint);
		//jsonList.add(maildepointPoint);
		//jsonList.add(hapitasuPoint);
		jsonList.add(feelPoint);
		jsonList.add(bmonPoint);
		jsonList.add(kabu);

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
		//

		try {
			PointSaveBatch.getCapture(driver,"moppy");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		driver.findElement(By.cssSelector("#content > section > div > div.box-login > form > div > div.m-btn-form__item.ga-login-btn > button")).
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

	public static void getCapture(WebDriver driver,String site) throws IOException{
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String fileName = site + "_"  + ".png";
			String rootPath = "/var/www/html/log_images/";
			String URL = "http://133.242.235.62";
			FileUtils.copyFile(srcFile, new File(rootPath + fileName));
			System.out.println("file:" + "  " + URL + "/log_images/" + fileName);
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
