package jp.co.softbank.fy20.springbootaks.service;

import java.util.List;

import javax.servlet.http.HttpSession;

public interface HistoryService {
    
    void findInsert(String userID, Integer wordID);

    List<String> findNewWordsTen();

    List<String> findRankingTen();

    List<String> findMonthRankingTen();

    List<String> findWeekRankingTen();

    List<String> findDayRankingTen();

    void sessionSet(HttpSession session);

}