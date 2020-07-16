package jp.co.softbank.fy20.springbootaks.service;

import java.util.List;

import jp.co.softbank.fy20.springbootaks.entity.Users;

public interface UsersService {
    List<Users> findAll();

    void insert(Users users);

    boolean delete(String id);

    //名前更新
    int updateName(String name, String id);

    //password更新
    int updatePassword(String password, String id);

    Users find(String id);

    //Group検索
    List<Users> findByGroup(String userGroup);
}