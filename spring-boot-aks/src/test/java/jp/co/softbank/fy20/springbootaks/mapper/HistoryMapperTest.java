package jp.co.softbank.fy20.springbootaks.mapper;

import jp.co.softbank.fy20.springbootaks.SampleApplication;
import jp.co.softbank.fy20.springbootaks.entity.*;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationContext;

public class HistoryMapperTest {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SampleApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE); // Webアプリケーション環境を無効化
        ApplicationContext context = application.run(args);

        // EmployeeMapperのBeanを取得
        HistoryMapper historyMapper = context.getBean(HistoryMapper.class);

        //履歴追加のテスト
        System.out.println("==== 履歴追加 ====");
        //historyMapper.insert(new History("select",1,1));


        System.out.println("==== 検索 ====");
        List<History> historyList = historyMapper.findType("select");
        for (History history : historyList) {
            System.out.println(history);
        }

        System.out.println("==== 新着のお言葉 ====");
        List<String> newList = historyMapper.findNewWordsTen();
        for (String num : newList) {
            System.out.println(num);
        }

        System.out.println("==== 検索ランキング ====");
        List<String> rankList = historyMapper.findRankingTen();
        for (String num : rankList) {
            System.out.println(num);
        }
        

    }
    
}