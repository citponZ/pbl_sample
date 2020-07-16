package jp.co.softbank.fy20.springbootaks.mapper;

import jp.co.softbank.fy20.springbootaks.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HistoryMapper {


    @Select("SELECT * FROM History WHERE historyType like #{historyType}")
    List<History> findType(String historyType);

    @Insert("INSERT INTO History(historyType, userID, wordID, historyDate) VALUES(#{historyType}, #{userID}, #{wordID}, DATEADD(hour,9,GETDATE()))")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(History history);

    //新着の言葉
    @Select("SELECT TOP 10 Words.name as name FROM Words, History " +
    "WHERE Words.id = wordID and historyType like 'insert' ORDER BY historyDate DESC")
    List<String> findNewWordsTen();

    /*  検索数も表示するパターン
    @Select("SELECT TOP 10 name, count(*) as count FROM  Words, History "+
    "WHERE Words.id = History.wordID and historyType like 'select' "+
    "GROUP BY name ORDER BY count(*) DESC")*/

    //累計ランキング
    @Select("SELECT TOP 10 name FROM  Words, History "+
    "WHERE Words.id = History.wordID and historyType like 'select' "+
    "GROUP BY name ORDER BY count(*) DESC")
    List<String> findRankingTen();

    //月間
    @Select("SELECT TOP 10 name FROM  Words, History "+
    "WHERE Words.id = History.wordID and historyType like 'select' and "+
    "historyDate > DATEADD(month,-1,DATEADD(hour,9,GETDATE())) GROUP BY name ORDER BY count(*) DESC")
    List<String> findMonthRankingTen();

    //週間
    @Select("SELECT TOP 10 name FROM  Words, History "+
    "WHERE Words.id = History.wordID and historyType like 'select' and "+
    "historyDate > DATEADD(week,-1,DATEADD(hour,9,GETDATE())) GROUP BY name ORDER BY count(*) DESC")
    List<String> findWeekRankingTen();

    //日間
    @Select("SELECT TOP 10 name FROM  Words, History "+
    "WHERE Words.id = History.wordID and historyType like 'select' and "+
    "historyDate > DATEADD(day,-1,DATEADD(hour,9,GETDATE())) GROUP BY name ORDER BY count(*) DESC")
    List<String> findDayRankingTen();

    
}