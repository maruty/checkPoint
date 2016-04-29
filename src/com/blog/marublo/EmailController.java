package com.blog.marublo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;


public class EmailController extends AbstractPointController{

	public EmailController(){
		super();
	}

	public void execute(){
		//mail接続
		MailUtil mailUtil;
		try {
			mailUtil = new MailUtil("imap.gmail.com",
										SettingInitializer.getGmailId(),
										SettingInitializer.getGmailPass(),
										"993");
			//メール一覧を取得する
			List<Mail> mailList;

			System.out.println("メール一覧取得処理");
			mailList = mailUtil.getMailList();

			System.out.println("メール数：" + mailList.size());
			//正規表現でマッチするための配列を作成
			String matchArray[] = {".*Tポイント.*",".*メールdeポイント.*",".*モッピー.*"};

			//Listでまわしながら正規表現でマッチするかの確認を行う　マッチした場合は後でseleniiumでアクセスするためのURLListを作成する
			List<String> urlList = new ArrayList<>();
			for(Mail mail : mailList){
				System.out.println("全メール表示：" + mail.subject);
				//正規表現
				for(int i=0; i < matchArray.length; i++){
					//Listを連結するためにtempのListを作成する
					List<String> urlTempList = new ArrayList<>();
					//URL抽出
					if(MatchUtil.hasMatch(mail.subject, matchArray[i])){
						System.out.println("処理対象メール：" + mail.subject);
						urlTempList = MatchUtil.getUrlList(mail.body, "(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+");
					}
					//URL連結
					urlList.addAll(urlTempList);
				}
			}
			System.out.println("URL数：" + urlList.size());
			System.out.println("メールリンククリック中");
			//URL Listを基にseleniumでアクセスをする
			if(urlList != null || urlList.size() != 0){
				for(String url : urlList){
					driver.get(url);
				}
				driver.quit();
				//アクセス完了後メールを削除する
				mailUtil.allDeleteMail();
			}
			//mailのクローズ
			mailUtil.closeMail();

		} catch (MessagingException | IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
