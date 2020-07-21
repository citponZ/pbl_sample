package jp.co.softbank.fy20.springbootaks.service;

import java.util.List;

import jp.co.softbank.fy20.springbootaks.entity.DeleteRequest;

public interface DeleteRequestService {

    void insert(DeleteRequest deleteRequest);

    //時間順に並んでいるとよい
    List<DeleteRequest> findAll();

    //単語の名前による検索：部分一致
    List<DeleteRequest> findByName(String name);

    DeleteRequest find(String wordName, String userID);

    //IDの一致する１つを検索
    DeleteRequest findId(Integer id);

    //wordIDのものをずべて削除
    boolean deleteWord(String wordName);

    //wordIDとuserIDの一致する１つを削除
    boolean delete(String wordName, String userID);

    //IDの一致する１つを削除
    boolean deleteId(Integer id);
    
}