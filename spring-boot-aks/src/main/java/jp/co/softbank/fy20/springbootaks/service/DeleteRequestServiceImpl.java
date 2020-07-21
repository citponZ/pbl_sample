package jp.co.softbank.fy20.springbootaks.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.softbank.fy20.springbootaks.entity.*;
import jp.co.softbank.fy20.springbootaks.mapper.DeleteRequestMapper;

@Service
public class DeleteRequestServiceImpl implements DeleteRequestService{

    private final DeleteRequestMapper deleteRequestMapper;

    public DeleteRequestServiceImpl(DeleteRequestMapper deleteRequestMapper) {
        this.deleteRequestMapper = deleteRequestMapper;
    }

    //追加
    @Override
    @Transactional(readOnly = false)
    public void insert(DeleteRequest deleteRequest){
        deleteRequestMapper.insert(deleteRequest);
    }

    //時間順に並んでいるとよい
    @Override
    @Transactional(readOnly = true)
    public List<DeleteRequest> findAll(){
        return deleteRequestMapper.findAll();
    }

    //単語の名前による検索：部分一致
    @Override
    @Transactional(readOnly = true)
    public List<DeleteRequest> findByName(String name){
        return deleteRequestMapper.findByName("%"+name+"%");
    }

    //wordIDのものをずべて削除
    @Override
    @Transactional(readOnly = false)
    public boolean deleteWord(Integer wordID){
        return deleteRequestMapper.deleteWord(wordID);
    }

    //wordIDとuserIDの一致する１つを削除
    @Override
    @Transactional(readOnly = false)
    public boolean delete(Integer wordID, String userID){
        return deleteRequestMapper.delete(wordID, userID);
    }
    
}