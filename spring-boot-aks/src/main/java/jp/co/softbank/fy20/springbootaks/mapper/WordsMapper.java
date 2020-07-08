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

    //名前によるWordsの検索：完全一致
    //@Select("SELECT Words.id as id, Words.name as name,Words.content as content, "+
    //"Words.updatedDate as updatedDate, Abbreviations.name as abbName FROM Words, Abbreviations "+
    //"WHERE Words.name like #{name} and Words.id = Abbreviations.wordID")
    //@Select("IF EXISTS(SELECT * FROM Words,Abbreviations WHERE  Words.name like #{name} and Words.id = Abbreviations.wordID) "+
    //"(SELECT Words.id as id, Words.name as name, Words.content as content,Words.updatedDate as updatedDate, "+
    //"Abbreviations.name as abbName FROM Words, Abbreviations WHERE Words.name like #{name} and Words.id = Abbreviations.wordID) "+
    //"ELSE (SELECT Words.id as id, Words.name as name, Words.content as content, Words.updatedDate as updatedDate, null as abbName "+
    //"FROM Words WHERE Words.name like #{name})")

    @Select("SELECT Words.id as id, Words.name as name,Words.content as content,Words.updatedDate as updatedDate,Abbreviations.name as abbName "+
    "FROM Words LEFT OUTER JOIN Abbreviations ON Words.id = Abbreviations.wordID WHERE Words.name like #{name}")
    List<WordsByAbb> findByName(String name);

    //名前によるWordsの検索：部分一致
    //@Select("select Words.id as id, Words.name as name, Words.content as content, "+
    //"Abbreviations.name as abbName from Words, Abbreviations,"+
    //" (select id,name from Words where name like #{name} UNION select wordID as id,name "+
    //"from Abbreviations where name like #{name} ) rs "+
    //"where Words.id = rs.id and Words.id = Abbreviations.wordID")

    @Select("select Words.id as id, Words.name as name, Words.content as content, Abbreviations.name as abbName "+
    "from (select id,name from Words where name like #{name} UNION "+
    "select wordID as id,name from Abbreviations where name like #{name}) rs, "+
    "Words LEFT OUTER JOIN Abbreviations ON Words.id = Abbreviations.wordID where Words.id = rs.id")
    List<WordsByAbb> findByNameAsInclude(String name);

    @Select("SELECT TOP (1) * FROM Words WHERE name like #{name}")
    Words checkByName(String name);

    
}