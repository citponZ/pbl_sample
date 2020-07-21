package jp.co.softbank.fy20.springbootaks.service;

import jp.co.softbank.fy20.springbootaks.SampleApplication;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationContext;
import jp.co.softbank.fy20.springbootaks.entity.DeleteRequest;

public class DeleteRequestServiceTest {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SampleApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE); // Webアプリケーション環境を無効化
        ApplicationContext context = application.run(args);

        // EmployeeMapperのBeanを取得
        DeleteRequestService deleteRequestService = context.getBean(DeleteRequestService.class);

        System.out.println("==== 新規追加 ====");
        deleteRequestService.insert(new DeleteRequest("admin",1,"あってもしょうがないから"));
        deleteRequestService.insert(new DeleteRequest("user",1,"あってもしょうがないから"));

        System.out.println("==== 全件検索 ====");
        List<DeleteRequest> list = deleteRequestService.findAll();
        for (DeleteRequest data : list){
            System.out.println(data);
        }

        System.out.println("==== 新規追加 ====");
        deleteRequestService.insert(new DeleteRequest("admin",2,"あってもしょうがないから"));

        System.out.println("==== 部分一致検索 ====");
        list = deleteRequestService.findByName("AP");
        for (DeleteRequest data : list){
            System.out.println(data);
        }

        System.out.println("==== 全件検索 ====");
        list = deleteRequestService.findAll();
        for (DeleteRequest data : list){
            System.out.println(data);
        }

        System.out.println("==== 1個削除 ====");
        deleteRequestService.delete(2, "admin");

        System.out.println("==== 全件検索 ====");
        list = deleteRequestService.findAll();
        for (DeleteRequest data : list){
            System.out.println(data);
        }

        //System.out.println("==== 新規追加 ====");
        //deleteRequestService.insert(new DeleteRequest("user",2,"あaaaaa"));

        System.out.println("==== 全件検索 ====");
        list = deleteRequestService.findAll();
        for (DeleteRequest data : list){
            System.out.println(data);
        }

        System.out.println("==== wordID削除 ====");
        deleteRequestService.deleteWord(1);

        System.out.println("==== 全件検索 ====");
        list = deleteRequestService.findAll();
        for (DeleteRequest data : list){
            System.out.println(data);
        }

    }
    
}