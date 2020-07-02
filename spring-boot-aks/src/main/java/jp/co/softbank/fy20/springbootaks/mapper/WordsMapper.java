package jp.co.softbank.fy20.springbootaks.mapper;

import jp.co.softbank.fy20.springbootaks.entity.Words;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface WordsMapper {
    @Select("SELECT * FROM Words")
    List<Words> findAll();

    @Insert("INSERT INTO Words(name,userID,content,createdDate,updatedDate) " +
    "VALUES(#{name},#{userID},#{content},GETDATE(),GETDATE())")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(Words word);

    @Delete("DELETE FROM Words WHERE id=#{id}")
    boolean delete(Integer id);

    @Update("UPDATE Words SET content=#{content}, updatedDate=GETDATE() WHERE id=#{id}")
    int update(String content, Integer id);

    @Select("SELECT * FROM Words WHERE id=#{id}")
    Words find(Integer id);
    
}