package jp.co.softbank.fy20.springbootaks.service;

import jp.co.softbank.fy20.springbootaks.entity.History;

public interface HistoryService {
    
    void findInsert(Integer userID, Integer wordID);

}