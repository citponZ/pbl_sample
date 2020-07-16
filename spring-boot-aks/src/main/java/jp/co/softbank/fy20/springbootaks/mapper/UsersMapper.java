package jp.co.softbank.fy20.springbootaks.mapper;

import jp.co.softbank.fy20.springbootaks.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface UsersMapper {
    
    @Select("SELECT * FROM Users")
    List<Users> findAll();

    @Insert("INSERT INTO Users(id,name,password,userGroup) " +
    "VALUES(#{id},#{name},#{password},#{userGroup})")
    void insert(Users users);

    @Delete("DELETE FROM Users WHERE id=#{id}")
    boolean delete(String id);

    //名前更新
    @Update("UPDATE Users SET name=#{name} WHERE id=#{id}")
    int updateName(String name, String id);

    //password更新
    @Update("UPDATE Users SET password=#{password} WHERE id=#{id}")
    int updatePassword(String password, String id);

    @Select("SELECT * FROM Users WHERE id=#{id}")
    Users find(String id);

    //Group検索
    @Select("SELECT * FROM Users WHERE userGroup=#{userGroup}")
    List<Users> findByGroup(String userGroup);

}