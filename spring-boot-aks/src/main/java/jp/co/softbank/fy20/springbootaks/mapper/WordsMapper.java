package jp.co.softbank.fy20.springbootaks.mapper;

import jp.co.softbank.fy20.springbootaks.entity.Words;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface WordsMapper {
    @Select("SELECT * FROM Words")
    List<Words> findAll();

    @Insert("INSERT INTO Words(name,userID,content,createdDate,updatedDate) " +
    "VALUES(#{name},#{userID},#{content},GETDATE(),GETDATE())")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(Words word);
    
    
    
}