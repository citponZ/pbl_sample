package jp.co.softbank.fy20.springbootaks.service;

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
    public void findInsert(Integer userID, Integer wordID){
        historyMapper.insert(new History("select", userID, wordID));
    }
    
}