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

    //@Select("SELECT name FROM Words ORDER BY LEN(name)")
    @Select("SELECT name FROM Words ORDER BY LEN(name) DESC")
    List<String> findAllName();

    @Insert("INSERT INTO Words(name,userID,content,createdDate,updatedDate) " +
    "VALUES(#{name},#{userID},#{content},DATEADD(hour,9,GETDATE()),DATEADD(hour,9,GETDATE()))")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(Words word);

    @Delete("DELETE FROM Words WHERE id=#{id}")
    boolean delete(Integer id);

    //更新
    @Update("UPDATE Words SET content=#{content}, updatedDate=DATEADD(hour,9,GETDATE()) WHERE id=#{id}")
    int update(String content, Integer id);

    @Select("SELECT * FROM Words WHERE id=#{id}")
    Words find(Integer id);

    //名前によるWordsの検索：完全一致
    @Select("SELECT Words.id as id, Words.name as name,Words.content as content,Words.updatedDate as updatedDate,Abbreviations.name as abbName "+
    "FROM Words LEFT OUTER JOIN Abbreviations ON Words.id = Abbreviations.wordID WHERE Words.name like #{name}")
    List<WordsByAbb> findByName(String name);

    //名前によるWordsの検索：部分一致
    @Select("select Words.id as id, Words.name as name, Words.content as content, Words.updatedDate as updatedDate, Abbreviations.name as abbName "+
    "from (select id from Words where name like #{name} UNION "+
    "select wordID as id from Abbreviations where name like #{name}) rs, "+
    "Words LEFT OUTER JOIN Abbreviations ON Words.id = Abbreviations.wordID where Words.id = rs.id")
    List<WordsByAbb> findByNameAsInclude(String name);

    @Select("SELECT TOP (1) * FROM Words WHERE name like #{name}")
    Words checkByName(String name);

    //略語の追加
    @Insert("INSERT INTO Abbreviations(name,wordID) VALUES(#{abbName},(SELECT id FROM Words WHERE name like #{wordName}))")
    void insertAbb(String wordName, String abbName);

    //略語の削除
    @Delete("DELETE FROM Abbreviations WHERE name like #{abbName} and wordID like (SELECT id FROM Words WHERE name like #{wordName})")
    boolean deleteAbb(String wordName, String abbName);

    //略語の確認
    @Select("SELECT TOP (1) name FROM Abbreviations WHERE name like #{abbName} and wordID like (SELECT id FROM Words WHERE name like #{wordName})")
    String checkByNameAbb(String wordName, String abbName);


    
}