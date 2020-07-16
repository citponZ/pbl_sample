package jp.co.softbank.fy20.springbootaks.service;

import java.util.List;

import org.springframework.stereotype.Service;
import jp.co.softbank.fy20.springbootaks.mapper.*;
import jp.co.softbank.fy20.springbootaks.entity.*;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersMapper usersMapper;

    public UsersServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Users> findAll(){
        return usersMapper.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void insert(Users users){
        usersMapper.insert(users);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(String id){
        return usersMapper.delete(id);
    }

    //名前更新
    @Override
    @Transactional(readOnly = false)
    public int updateName(String name, String id){
        return usersMapper.updateName(name, id);
    }

    //password更新
    @Override
    @Transactional(readOnly = false)
    public int updatePassword(String password, String id){
        return usersMapper.updatePassword(password, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Users find(String id){
        return usersMapper.find(id);
    }

    //Group検索
    @Override
    @Transactional(readOnly = true)
    public List<Users> findByGroup(String userGroup){
        return usersMapper.findByGroup(userGroup);
    }
}