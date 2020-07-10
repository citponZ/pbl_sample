package jp.co.softbank.fy20.springbootaks.service;

import org.springframework.stereotype.Service;
import jp.co.softbank.fy20.springbootaks.mapper.*;
import jp.co.softbank.fy20.springbootaks.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class WordsServiceImpl implements WordsService {
    
    private final WordsMapper wordsMapper;
    private final HistoryMapper historyMapper;

    public WordsServiceImpl(WordsMapper wordsMapper, HistoryMapper historyMapper) {
        this.wordsMapper = wordsMapper;
        this.historyMapper = historyMapper;
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
        historyMapper.insert(new History("insert",words.getUserID(),words.getId()));
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Integer id, Integer userID){
        boolean checkDelete = wordsMapper.delete(id);
        historyMapper.insert(new History("delete", userID, id));
        return checkDelete;
    }

    @Override
    @Transactional(readOnly = false)
    public int update(String content, Integer id, Integer userID){
        int checkUpdate = wordsMapper.update(content, id);
        historyMapper.insert(new History("update", userID, id));
        return checkUpdate;
    }

    @Override
    @Transactional(readOnly = true)
    public Words find(Integer id){
        Words rs = wordsMapper.find(id);
        return rs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WordsByAbb> findByName(String name){
        List<WordsByAbb> wordsList = wordsMapper.findByName(name);
        return wordsList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WordsByAbb> findByNameAsInclude(String name){
        List<WordsByAbb> wordsList = wordsMapper.findByNameAsInclude("%"+name+"%");
        return wordsList;
    }

    @Override
    @Transactional(readOnly = true)
    public Words checkByName(String name){
        return wordsMapper.checkByName(name);
    }

    @Override
    public List<WordsListAbb> converToWordsListAbb(List<WordsByAbb> wordsAbbList){
        Map<String, WordsListAbb> wordsmap= new HashMap<>();

        for (WordsByAbb words : wordsAbbList){
            if (wordsmap.containsKey(words.getName())){
                wordsmap.get(words.getName()).addAbbName(words.getAbbName());
            }
            else{
                wordsmap.put(words.getName(),new WordsListAbb(words));
            }
        }
        //map -> list
        List<WordsListAbb> wordsList = new ArrayList<WordsListAbb>();
        for(Map.Entry<String, WordsListAbb> entry : wordsmap.entrySet()){
            wordsList.add(entry.getValue());
        }
        //wordsList.add(new WordsListAbb());
        return wordsList;
    }


}