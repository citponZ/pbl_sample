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

        // 新規追加のテスト
        //System.out.println("==== 新規追加 ====");
        //WordsMapper.insert(new Employee("和田三郎", LocalDate.of(2019, 1, 1), 10));

        // 全件検索のテスト
        System.out.println("==== 全件検索 ====");
        List<Words> wordsList1 = wordsMapper.findAll();
        for (Words words : wordsList1) {
            System.out.println(words);
        }

        // 名前キーワード検索のテスト
        //System.out.println("==== 名前キーワード検索 ====");
        //List<Employee> employeeList2 = employeeMapper.findByNameLike("%田%");
        //for (Employee employee : employeeList2) {
            //System.out.println(employee);
        //}
        
    }
    
}