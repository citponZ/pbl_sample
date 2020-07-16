package jp.co.softbank.fy20.springbootaks.service;

import jp.co.softbank.fy20.springbootaks.SampleApplication;
import jp.co.softbank.fy20.springbootaks.entity.*;
import jp.co.softbank.fy20.springbootaks.mapper.WordsMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

public class WordsServiceTest {
    public static void main(String[] args) {
        // DIコンテナの作成
        SpringApplication application = new SpringApplication(SampleApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE); // Webアプリケーション環境を無効化
        ApplicationContext context = application.run(args);

        // EmployeeMapperのBeanを取得
        WordsService wordsService = context.getBean(WordsService.class);


        // 全件検索のテスト
        System.out.println("==== 全件検索 ====");
        List<Words> WordsList1 = wordsService.findAll();
        for (Words Words : WordsList1) {
            System.out.println(Words);
        }

        //新規追加のテスト
        System.out.println("==== 新規追加 ====");
        //wordsService.insert(new Words("Azure", 1, "Azuerはクラウドです。"));

        // 全件検索のテスト
        System.out.println("==== 全件検索 ====");
        List<Words> wordsList1 = wordsService.findAll();
        for (Words words : wordsList1) {
            System.out.println(words);
        }

        /*
        // 更新のテスト
        System.out.println("==== 更新 ====");
        int checkUpdate = wordsService.update("アップデートしました。", 2,1);
        System.out.println(checkUpdate);
        */

        // 1検索
        System.out.println("==== 1検索 ====");
        Words rs = wordsService.find(2);
        System.out.println(rs);

        /*
        // 削除のテスト
        System.out.println("==== 削除 ====");
        boolean checkDelete = wordsService.delete(2,1);
        if (checkDelete){
            System.out.println("削除されました。");
        }
        else{
            System.out.println("削除できませんでした。");
        }*/
        
        // 全件検索のテスト
        System.out.println("==== 全件検索 ====");
        List<Words> wordsList2 = wordsService.findAll();
        for (Words words : wordsList2) {
            System.out.println(words);
        }

        //名前検索
        System.out.println("==== name検索 ====");
        List<WordsByAbb> List = wordsService.findByName("word1");
        for (WordsByAbb words : List) {
            System.out.println(words);
        }

        //mapper関数の引数の前と後ろに”％”を入れる必要がある
        System.out.println("==== name検索 部分一致 ====");
        List<WordsByAbb> List2 = wordsService.findByNameAsInclude("word");
        for (WordsByAbb words : List2) {
            System.out.println(words);
        }

        //nameのものがあるかないか
        System.out.println("==== あるかないか ====");
        Words words = wordsService.checkByName("word1");
        if (words != null){
            System.out.println("aru");
        }
        else{
            System.out.println("nai");
        }

        words = wordsService.checkByName("word8888");
        if (words != null){
            System.out.println("aru");
        }
        else{
            System.out.println("nai");
        }

        System.out.println("==== convert ====");
        List2 = wordsService.findByNameAsInclude("AP");
        List<WordsListAbb> testlist = wordsService.converToWordsListAbb(List2);
        for (WordsListAbb test : testlist) {
            System.out.println(test);
        }

        System.out.println("==== link ====");
        List2 = wordsService.findByName("APサーバー");
        String test = wordsService.makeLink(List2.get(0).getContent());


        //略語追加
        System.out.println("==== 履歴新規追加 ====");
        wordsService.insertAbb("リーダブルコード", "RC");

        //略語確認
        System.out.println("==== 履歴確認 ====");
        String str = wordsService.checkByNameAbb("リーダブルコード", "RC");
        if (str == null){
            System.out.println("ありません");
        }
        else{
            System.out.println("履歴："+str);
        }
        //略語削除
        System.out.println("==== 履歴削除 ====");
        boolean checkDelete = wordsService.deleteAbb("リーダブルコード", "RC");
        //boolean checkDelete = false;
        if (checkDelete){
            System.out.println("削除されました。");
        }
        else{
            System.out.println("削除できませんでした。");
        }
        //略語確認
        System.out.println("==== 履歴確認 ====");
        str = wordsService.checkByNameAbb("リーダブルコード", "RC");
        if (str == null){
            System.out.println("ありません");
        }
        else{
            System.out.println("履歴："+str);
        }


    }

    
}