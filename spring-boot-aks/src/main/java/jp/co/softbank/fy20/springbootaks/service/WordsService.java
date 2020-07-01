package jp.co.softbank.fy20.springbootaks.service;

import jp.co.softbank.fy20.springbootaks.entity.*;
import java.util.List;

public interface WordsService {

    List<Words> findAll();

    //List<Words> findByNameLike(String keyword);

    //void insert(Words employee);
}