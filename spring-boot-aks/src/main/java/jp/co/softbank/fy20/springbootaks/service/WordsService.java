package jp.co.softbank.fy20.springbootaks.service;

import jp.co.softbank.fy20.springbootaks.entity.*;
import java.util.List;

public interface WordsService {

    List<Words> findAll();

    //List<Words> findByNameLike(String keyword);

    void insert(Words words);

    boolean delete(Integer id);

    int update(String content, Integer id);

    Words find(Integer id);

    List<WordsByAbb> findByName(String name);

    List<WordsByAbb> findByNameAsInclude(String name);

    Words checkByName(String name);
}