package com.blog.marublo;


public class ExecPointController  {
    public static void main(String[] args) throws InterruptedException {

    	System.out.println("===ポイント確認APIデータ作成処理===");

    	//ユーザーデータの取得
    	System.out.println("ユーザーID取得初期化処理");
    	SettingInitializer.init();
    	System.out.println("初期化完了");

    	//データ保存
    	System.out.println("各サイトポイント情報取得処理開始");
    	AbstractPointController selenium = new PointSaveBatch();
    	selenium.execute();

    	//System.out.println("D系対象ユーザースクリーニング処理開始");
    	//selenium = new DkeiUserScreeningBatch();
    	//selenium.execute();

    	//終了処理
        System.out.println("処理が終了しました");

    }
}