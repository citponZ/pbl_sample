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
}