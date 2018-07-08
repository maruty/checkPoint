
package com.blog.marublo;



import java.io.BufferedWriter;
import java.io.File;
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
		String kabusu[] = driver.findElement(By.xpath("//*[@id=\"content\"]/table[2]/tbody/tr[4]/td")).getText().split(".");
		//,までをいったん取得
		String kakaku[] = driver.findElement(By.cssSelector("#content > table.tblbasic > tbody > tr:nth-child(7) > td")).getText().split(",");
		String kakakuMaster = kakaku[0] + kakaku[1].substring(1, 3);

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
		//jsonList.add(moppy);
		//jsonList.add(mobatokuPoint);
		//jsonList.add(maildepointPoint);
		//jsonList.add(hapitasuPoint);
		//jsonList.add(feelPoint);
		//jsonList.add(bmonPoint);
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
