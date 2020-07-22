package jp.co.softbank.fy20.springbootaks.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.softbank.fy20.springbootaks.entity.History;
import jp.co.softbank.fy20.springbootaks.mapper.HistoryMapper;

@Service
public class HistoryServiceImpl implements HistoryService {
    
    private final HistoryMapper historyMapper;

    public HistoryServiceImpl(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    @Override
    @Transactional(readOnly = false)
    public void findInsert(String userID, Integer wordID){
        historyMapper.insert(new History("select", userID, wordID));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findNewWordsTen(){
        return historyMapper.findNewWordsTen();   
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findRankingTen(){
        return historyMapper.findRankingTen();   
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findMonthRankingTen(){
        return historyMapper.findMonthRankingTen();  
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findWeekRankingTen(){
        return historyMapper.findWeekRankingTen();  
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findDayRankingTen(){
        return historyMapper.findDayRankingTen();  
    }

    @Override
    public void sessionSet(HttpSession session){
        session.setAttribute("newWordsList", findNewWordsTen());
        session.setAttribute("rankingList", findRankingTen());

    }

    @Override
    public void sessionSet(HttpSession session,String referer){
        session.setAttribute("newWordsList", findNewWordsTen());
        session.setAttribute("rankingList", findRankingTen());
        session.setAttribute("referer", referer);
    }

    @Override
    public void sessionSet(HttpSession session,String referer,String name){
        session.setAttribute("newWordsList", findNewWordsTen());
        session.setAttribute("rankingList", findRankingTen());
        session.setAttribute("referer", referer);
        session.setAttribute("wordsName", name);
    }
    
}