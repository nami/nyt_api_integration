package nyt.model.db;

import java.util.ArrayList;
import nyt.model.db.Article;

public class DBSearch {

    // set the keyword so we can see it in the response
    String keyword;

    // the number of articles (rows from the db) that contain the keyword
    int articlesContainingKeyword;

    // BONUS: the total number of times that keyword is found in the DB
    int numTimesKeywordFound;

    // an array of article objects that contain the keyword
    ArrayList<Article> articles;

    // getters and setters
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getArticlesContainingKeyword() {
        return articlesContainingKeyword;
    }

    public void setArticlesContainingKeyword(int articlesContainingKeyword) {
        this.articlesContainingKeyword = articlesContainingKeyword;
    }

    public int getNumTimesKeywordFound() {
        return numTimesKeywordFound;
    }

    public void setNumTimesKeywordFound(int numTimesKeywordFound) {
        this.numTimesKeywordFound = numTimesKeywordFound;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }


}
