package jp.co.softbank.fy20.springbootaks.service;

import jp.co.softbank.fy20.springbootaks.SampleApplication;
import jp.co.softbank.fy20.springbootaks.entity.Words;
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
    }

    
}