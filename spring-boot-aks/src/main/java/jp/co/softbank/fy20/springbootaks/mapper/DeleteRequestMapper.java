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
public interface DeleteRequestMapper {

    @Insert("INSERT INTO DeleteRequest(userID,wordName,reason,requestDate) " +
    "VALUES(#{userID},#{wordName},#{reason},DATEADD(hour,9,GETDATE()))")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(DeleteRequest deleteRequest);

    //時間順に並んでいるとよい
    @Select("SELECT * FROM DeleteRequest ORDER BY requestDate DESC")
    List<DeleteRequest> findAll();

    //名前による検索：部分一致
    @Select("SELECT * "+
    "FROM DeleteRequest WHERE wordName like #{name} ORDER BY requestDate DESC")
    List<DeleteRequest> findByName(String name);

    //wordIDとuserIDの一致する１つを検索
    @Select("SELECT * FROM DeleteRequest WHERE wordName=#{wordName} and userID=#{userID}")
    DeleteRequest find(String wordName, String userID);

    //IDの一致する１つを検索
    @Select("SELECT * FROM DeleteRequest WHERE id=#{id}")
    DeleteRequest findId(Integer id);

    //wordIDのものをずべて削除
    @Delete("DELETE FROM DeleteRequest WHERE wordName=#{wordName}")
    boolean deleteWord(String wordName);

    //wordIDとuserIDの一致する１つを削除
    @Delete("DELETE FROM DeleteRequest WHERE wordName=#{wordName} and userID=#{userID}")
    boolean delete(String wordName, String userID);

    //IDの一致する１つを削除
    @Delete("DELETE FROM DeleteRequest WHERE id=#{id}")
    boolean deleteId(Integer id);



    
}