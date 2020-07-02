package jp.co.softbank.fy20.springbootaks.service;

import org.springframework.stereotype.Service;
import jp.co.softbank.fy20.springbootaks.mapper.*;
import jp.co.softbank.fy20.springbootaks.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WordsServiceImpl implements WordsService {
    
    private final WordsMapper wordsMapper;

    public WordsServiceImpl(WordsMapper wordsMapper) {
        this.wordsMapper = wordsMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Words> findAll() {
        List<Words> wordsList = wordsMapper.findAll();
        return wordsList;
    }

    @Override
    @Transactional(readOnly = false)
    public void insert(Words words){
        wordsMapper.insert(words);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Integer id){
        boolean checkDelete = wordsMapper.delete(id);
        return checkDelete;
    }

    @Override
    @Transactional(readOnly = false)
    public int update(String content, Integer id){
        int checkUpdate = wordsMapper.update(content, id);
        return checkUpdate;
    }

    @Override
    @Transactional(readOnly = true)
    public Words find(Integer id){
        Words rs = wordsMapper.find(id);
        return rs;
    }


}