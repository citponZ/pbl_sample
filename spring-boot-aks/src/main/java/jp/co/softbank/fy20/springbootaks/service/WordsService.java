package jp.co.softbank.fy20.springbootaks.service;

import jp.co.softbank.fy20.springbootaks.entity.*;
import java.util.List;

public interface WordsService {

    List<Words> findAll();

    List<String> findAllName();

    //List<Words> findByNameLike(String keyword);

    void insert(Words words);

    boolean delete(Integer id, Integer userID);

    int update(String content, Integer id, Integer userID);

    Words find(Integer id);

    List<WordsByAbb> findByName(String name);

    List<WordsByAbb> findByNameAsInclude(String name);

    Words checkByName(String name);

    List<WordsListAbb> converToWordsListAbb(List<WordsByAbb> wordsAbbList);

    String makeLink(String content);

    //略語の追加
    void insertAbb(String wordName, String abbName);

    //略語の削除
    boolean deleteAbb(String wordName, String abbName);

    //略語の確認
    //存在確認
    String checkByNameAbb(String wordName, String abbName);

    //略語全取得
    List<String> findAllByNameAbb(String wordName);
}