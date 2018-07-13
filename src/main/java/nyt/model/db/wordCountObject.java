package nyt.model.db;

import java.util.ArrayList;
import java.util.HashMap;

public class wordCountObject {

    // instance variables to show keyword, search terms
    String keyword;
    String[] searchTerms;
    // Hashmap to store wordcount
    HashMap<String, Integer> wordCountMap = new HashMap();
    // ArrayList to insert articles with keyword
    ArrayList<Article> articles = new ArrayList();

    // getters & setters
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String[] getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String[] searchTerms) {
        this.searchTerms = searchTerms;
    }

    public HashMap<String, Integer> getWordCountMap() {
        return wordCountMap;
    }

    public void setWordCountMap(HashMap<String, Integer> wordCountMap) {
        this.wordCountMap = wordCountMap;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
