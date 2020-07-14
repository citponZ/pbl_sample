package jp.co.softbank.fy20.springbootaks.service;

import java.util.List;

import javax.servlet.http.HttpSession;

public interface HistoryService {
    
    void findInsert(Integer userID, Integer wordID);

    List<String> findNewWordsTen();

    void sessionSet(HttpSession session);

}