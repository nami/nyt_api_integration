package nyt.mappers;

import nyt.model.db.Article;
import nyt.model.db.Headers;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface NYTMapper {

    // SQL query to insert article
    String INSERT_ARTICLE = "INSERT INTO `nyt`.`articles` (`headline`, `snippet`, `url`, `pub_date`) " +
            "VALUES (#{headline}, #{snippet}, #{url}, #{pub_date})";

    // SQL query to insert headlines
    String INSERT_HEADLINE = "INSERT INTO `nyt`.`headlines` (`headline`, `print_headline`) " +
            "VALUES (#{headline}, #{print_headline})";

    // SQL query to get articles with keyword
    String ARTICLES_WITH_KEYWORD = "SELECT * FROM nyt.articles where snippet like #{keyword} or headline like #{keyword}";

    // my-batis will then populate the methods for you using the SQLQuery
    // int is the number of rows affected
    @Insert(INSERT_ARTICLE)
    public int insertRecord(Article article);

    @Insert(INSERT_HEADLINE)
    public int insertHeadline(Headers headers);

    @Select(ARTICLES_WITH_KEYWORD)
    public ArrayList<Article> getArticlesWithKeyword(String keyword);

}

