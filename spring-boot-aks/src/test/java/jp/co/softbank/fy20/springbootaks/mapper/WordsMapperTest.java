package jp.co.softbank.fy20.springbootaks.mapper;

import jp.co.softbank.fy20.springbootaks.SampleApplication;
import jp.co.softbank.fy20.springbootaks.entity.Words;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

public class WordsMapperTest {
    public static void main(String[] args) {
        // DIコンテナの作成
        SpringApplication application = new SpringApplication(SampleApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE); // Webアプリケーション環境を無効化
        ApplicationContext context = application.run(args);

        // EmployeeMapperのBeanを取得
        WordsMapper wordsMapper = context.getBean(WordsMapper.class);

        //新規追加のテスト
        System.out.println("==== 新規追加 ====");
        wordsMapper.insert(new Words("Azuer", 1, "Azuerはクラウドです。"));

        // 全件検索のテスト
        System.out.println("==== 全件検索 ====");
        List<Words> wordsList1 = wordsMapper.findAll();
        for (Words words : wordsList1) {
            System.out.println(words);
        }

        // 更新のテスト
        System.out.println("==== 更新 ====");
        int checkUpdate = wordsMapper.update("アップデートしました。", 3);
        /*if (checkUpdate){
            System.out.println("更新されました。");
        }
        else{
            System.out.println("更新できませんでした。");
        }*/
        System.out.println(checkUpdate);

        // 1検索
        System.out.println("==== 1検索 ====");
        Words rs = wordsMapper.find(3);
        System.out.println(rs);

        // 削除のテスト
        System.out.println("==== 削除 ====");
        boolean checkDelete = wordsMapper.delete(3);
        if (checkDelete){
            System.out.println("削除されました。");
        }
        else{
            System.out.println("削除できませんでした。");
        }
        
        // 全件検索のテスト
        System.out.println("==== 全件検索 ====");
        List<Words> wordsList2 = wordsMapper.findAll();
        for (Words words : wordsList2) {
            System.out.println(words);
        }

        //いつか作りたいね
        // 名前キーワード検索のテスト
        //System.out.println("==== 名前キーワード検索 ====");
        //List<Employee> employeeList2 = employeeMapper.findByNameLike("%田%");
        //for (Employee employee : employeeList2) {
            //System.out.println(employee);
        //}
        
    }
    
}