package jp.co.softbank.fy20.springbootaks.service;

import org.springframework.stereotype.Service;
import jp.co.softbank.fy20.springbootaks.mapper.*;
import jp.co.softbank.fy20.springbootaks.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    @Transactional(readOnly = true)
    public List<String> findAllName(){
        List<String> nameList = wordsMapper.findAllName();
        return nameList;
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

    @Override
    public String makeLink(String content){
        List<String> dictList = findAllName();
        //URLをaタグに変換
        content = content.replaceAll("(http://|https://){1}[\\w\\.\\-/:]+", "<a target=\"_blank\" href='$0'>$0</a>");
        Pattern p = Pattern.compile("<a.*?<\\/a>");

        for (String dict : dictList){
            //aタグを使っていないところで比較
            //split分割
            List<String> splitList = Arrays.asList(content.split("<a.*?<\\/a>",-1));
            Matcher m = p.matcher(content);
            List<String> tagList= new ArrayList<>();
            while (m.find()) {
                tagList.add(m.group());
            }
            //replase
            for (int i=0; i<splitList.size() ; i++){
                splitList.set(i, splitList.get(i).replace(dict, 
                            "<a href=\"/spring-boot-aks/words/id/"+dict+"\">"+dict+"</a>"));
            }
            String tmp = "";
            //　くっつける //list[0] -> list2[0] -> list[1] -> list2[1] -> list[2]
            for (int i=0; i<tagList.size() ; i++){
                tmp += splitList.get(i) + tagList.get(i);
            }
            tmp += splitList.get(splitList.size()-1);
            content = tmp;
        }
        return content;
    }

    //略語の追加
    @Override
    @Transactional(readOnly = false)
    public void insertAbb(String wordName, String abbName){
        wordsMapper.insertAbb(wordName, abbName);
    }

    //略語の削除
    @Override
    @Transactional(readOnly = false)
    public boolean deleteAbb(String wordName, String abbName){
        return wordsMapper.deleteAbb(wordName, abbName);
    }

    //略語の確認
    //存在確認
    @Override
    @Transactional(readOnly = true)
    public String checkByNameAbb(String wordName, String abbName){
        return wordsMapper.checkByNameAbb(wordName, abbName);
    }
}