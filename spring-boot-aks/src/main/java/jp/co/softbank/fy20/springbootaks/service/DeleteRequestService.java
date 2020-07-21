package jp.co.softbank.fy20.springbootaks.service;

import java.util.List;

import jp.co.softbank.fy20.springbootaks.entity.DeleteRequest;

public interface DeleteRequestService {

    void insert(DeleteRequest deleteRequest);

    //時間順に並んでいるとよい
    List<DeleteRequest> findAll();

    //単語の名前による検索：部分一致
    List<DeleteRequest> findByName(String name);
    //wordIDのものをずべて削除
    boolean deleteWord(Integer wordID);

    //wordIDとuserIDの一致する１つを削除
    boolean delete(Integer wordID, String userID);
    
}