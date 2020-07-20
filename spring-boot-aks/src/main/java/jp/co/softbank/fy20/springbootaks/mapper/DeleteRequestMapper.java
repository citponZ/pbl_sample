package jp.co.softbank.fy20.springbootaks.mapper;

import jp.co.softbank.fy20.springbootaks.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface DeleteRequestMapper {

    @Insert("INSERT INTO Words(name,userID,content,createdDate,updatedDate) " +
    "VALUES(#{name},#{userID},#{content},DATEADD(hour,9,GETDATE()),DATEADD(hour,9,GETDATE()))")
    void insert(DeleteRequest deleteRequest);

    //時間順に並んでいるとよい
    @Select("SELECT * FROM DeleteRequest ORDER BY requestDate DESC")
    List<DeleteRequest> findAll();

    //名前による検索：部分一致
    @Select("SELECT Words.id as id, Words.name as name,Words.content as content,Words.updatedDate as updatedDate,Abbreviations.name as abbName "+
    "FROM Words LEFT OUTER JOIN Abbreviations ON Words.id = Abbreviations.wordID WHERE Words.name like #{name}")
    List<DeleteRequest> findByName(String name);

    //wordIDのものをずべて削除
    @Delete("DELETE FROM DeleteRequest WHERE wordID=#{wordID}")
    boolean deleteWord(Integer wordID);

    //wordIDとuserIDの一致する１つを削除
    @Delete("DELETE FROM DeleteRequest WHERE wordID=#{wordID} and userID=#{userID}")
    boolean delete(Integer wordID, String userID);



    
}