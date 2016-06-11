package com.blog.marublo;


public class ExecPointController  {
    public static void main(String[] args) throws InterruptedException {

    	System.out.println("===自動ポイント取得処理開始===");

    	AbstractPointController driver = new PointSaveBatch();
    	driver.execute();

    	//終了処理
        System.out.println("処理が終了しました");

    }
}