package jp.co.softbank.fy20.springbootaks.mapper;

import jp.co.softbank.fy20.springbootaks.SampleApplication;
import jp.co.softbank.fy20.springbootaks.entity.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class UsersMapperTest {
    public static void main(String[] args) {
        // DIコンテナの作成
        SpringApplication application = new SpringApplication(SampleApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE); // Webアプリケーション環境を無効化
        ApplicationContext context = application.run(args);

        // EmployeeMapperのBeanを取得
        UsersMapper usersMapper = context.getBean(UsersMapper.class);

        //新規追加のテスト
        System.out.println("==== 新規追加 ====");
        usersMapper.insert(new Users("admin","管理者","admin","admin"));
        usersMapper.insert(new Users("user","ユーザー","user","user"));

        // 全件検索のテスト
        System.out.println("==== 全件検索 ====");
        List<Users> usersList1 = usersMapper.findAll();
        for (Users users : usersList1) {
            System.out.println(users);
        }

        // 更新のテスト
        System.out.println("==== 更新 ====");
        int checkUpdate = usersMapper.updateName("運用β", "user");
        if (checkUpdate>=1){
            System.out.println("更新されました。");
        }
        else{
            System.out.println("更新できませんでした。");
        }
        
        
        System.out.println("==== password更新 ====");
        checkUpdate = usersMapper.updatePassword("unyo", "user");
        if (checkUpdate>=1){
            System.out.println("更新されました。");
        }
        else{
            System.out.println("更新できませんでした。");
        }

        System.out.println("==== 全件検索 ====");
        usersList1 = usersMapper.findAll();
        for (Users users : usersList1) {
            System.out.println(users);
        }

        // 1検索
        System.out.println("==== 1検索 ====");
        Users rs = usersMapper.find("user");
        System.out.println(rs);

        // 削除のテスト
        System.out.println("==== 削除 ====");
        boolean checkDelete = usersMapper.delete("user");
        if (checkDelete){
            System.out.println("削除されました。");
        }
        else{
            System.out.println("削除できませんでした。");
        }

        // 全件検索のテスト
        System.out.println("==== 全件検索 ====");
        usersList1 = usersMapper.findAll();
        for (Users users : usersList1) {
            System.out.println(users);
        }

        // グループ検索のテスト
        System.out.println("==== グループ検索 ====");
        usersList1 = usersMapper.findByGroup("admin");
        for (Users users : usersList1) {
            System.out.println(users);
        }

    }    
}