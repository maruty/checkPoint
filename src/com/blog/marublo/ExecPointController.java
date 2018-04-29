package com.blog.marublo;


public class ExecPointController  {
    public static void main(String[] args) throws InterruptedException {

    	System.out.println("===自動ポイント取得処理開始===");
    	String geckoDriverPath = "";
    	if(SettingInitializer.isDebug()) {
    		//デバッグ
    		geckoDriverPath = "/Applications/geckodriver";
    	} else {
    		//本番
    		geckoDriverPath = "/opt/geckodriver/geckodriver";
    	}
    	System.setProperty("webdriver.gecko.driver", geckoDriverPath);

    	AbstractPointController driver = new PointSaveBatch();
    	driver.execute();
    driver.quitDriver();	

    	//終了処理
    System.out.println("処理が終了しました");

    }
}