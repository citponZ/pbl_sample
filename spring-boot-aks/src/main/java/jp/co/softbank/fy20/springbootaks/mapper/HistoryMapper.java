package jp.co.softbank.fy20.springbootaks.mapper;

import jp.co.softbank.fy20.springbootaks.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface HistoryMapper {


    @Select("SELECT * FROM History WHERE historyType like #{historyType}")
    List<History> findType(String historyType);

    @Insert("INSERT INTO History(historyType, userID, wordID, historyDate) VALUES(#{historyType}, #{userID}, #{wordID}, GETDATE())")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(History history);
    
}