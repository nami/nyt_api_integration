package nyt.services;

import nyt.mappers.NYTMapper;
import nyt.model.db.Article;
import nyt.model.db.DBSearch;
import nyt.model.db.Headers;
import nyt.model.db.wordCountObject;
import nyt.model.nyt.Docs;
import nyt.model.nyt.Headline;
import nyt.model.nyt.NYTRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import sun.jvm.hotspot.memory.HeapBlock;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

@org.springframework.stereotype.Service
public class NYTService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    NYTMapper mapper;

    // put API key for easy access
    private final String API_KEY = "d81e88a6506a4398a84f22f0710fe225";

    //search NYT and map it to persist results
    public NYTRoot searchNYT(String keyword, String persist) {

        // create URL of API request, and put keyword
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q="
                + keyword + "&api-key=" + API_KEY;

        // restTemplate connects to APIs for us, gets response and maps it to NTYResponse
        NYTRoot response = restTemplate.getForObject(url, NYTRoot.class);

        // if user passes in persist = true (Ignorecase), then we call the method persistResults
        if (persist.equalsIgnoreCase("true")) {
            // pass the response that they got
            persistResults(response);
        }
        return response;
    }

    private void persistResults(NYTRoot data) {

        // for each Doc elements in the docs[] response
        for (Docs doc : data.getResponse().getDocs()) {

            // create object to insert into DB
            Article a = new Article();

            // set Article values
            a.setHeadline(doc.getHeadline().getMain());
            a.setPub_date(doc.getPub_date());
            a.setSnippet(doc.getSnippet());
            a.setUrl(doc.getWeb_url());

            // insert article into DB
            mapper.insertRecord(a);
        }
    }

    // search NYT and get map it to persist results headlines
    public NYTRoot searchHeadlines(String keyword, String persists) {

        // create URL of API request and put keyword
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q="
                + keyword + "&api-key=" + API_KEY;

        // restTemplate connects to APIs for us, gets response and maps it to NTYResponse
        NYTRoot response = restTemplate.getForObject(url, NYTRoot.class);

        // if user passes in persist = trueHeadlines (Ignorecase), then we call the method persistHeadlines
        if(persists.equalsIgnoreCase("headlines")){
            // pass the response that they got
            persistHeadlines(response);
        }
        return response;
    }

    private void persistHeadlines(NYTRoot data){

        // for each Doc
        for(Docs doc : data.getResponse().getDocs()){

            // create object to insert into DB
            Headers h = new Headers();

            // set Headers values
            h.setHeadline(doc.getHeadline().getMain());
            h.setPrint_headline(doc.getHeadline().getPrint_headline());

            // insert headline into DB
            mapper.insertHeadline(h);
        }
    }

    public DBSearch findArticles(String keyword){

        // new instance of DBSearch to set variables
        DBSearch dbs = new DBSearch();

        // set the keyword that was searched on dbs
        dbs.setKeyword(keyword);

        // to provide SQL query with wildcard parameters for keyword
        String keywordLike = "%" + keyword + "%";
        // ArrayList getting all the articles from SQL query
        ArrayList<Article> articles = mapper.getArticlesWithKeyword(keywordLike);
        // new ArrayList storing all headlines & snippets that contain keyword
        ArrayList<Article> articlewithKeywords = new ArrayList();

        // if there are no articles in the database
        if(articles.size() < 1){
            // call the search NYT and pass in the keyword, true
            searchNYT(keyword, "true");

            // get the articles with keyword
            articlewithKeywords = mapper.getArticlesWithKeyword(keywordLike);
            // set the articles with keyword
            dbs.setArticles(articlewithKeywords);
        }

        // for loop to look for keyword...
        for (Article art : articles){
            // in headline
            String headline = art.getHeadline();
            // in snippet
            String snippet = art.getSnippet();

            // count in headline number of times keyword appears
            headline = StringUtils.lowerCase(headline);
            int countKeyword = StringUtils.countMatches(headline, keyword);

            // count in snippet number of times keyword appears
            snippet = StringUtils.lowerCase(snippet);
            int countKey = StringUtils.countMatches(snippet, keyword);

            // plus the both to get the total number of times keyword appears
            int totalCount = countKey + countKeyword;
            // set it to the variable in DBSearch & keep updating when you find it
            dbs.setNumTimesKeywordFound(dbs.getNumTimesKeywordFound() + totalCount);

            // check if keyword exists in snippet or headline
            if(headline.contains(keyword) || snippet.contains(keyword)){
                // if it does, map it to new array list to return to user
                articlewithKeywords.add(art);
            }
        }

        // set the articles that contain keyword to return to user
        dbs.setArticles(articlewithKeywords);

        // count how many times keyword exists in articles
        int a = articlewithKeywords.size();
        dbs.setArticlesContainingKeyword(a);

        return dbs;
    }

    // word countObject (String keyword, String[] searchTerms){
    public wordCountObject countObject(String keyword, String[] searchTerms) {

        // call the searchNYT passing the keyword to search for (do not want to save the results)
        NYTRoot root = searchNYT(keyword, "false");

        // create object of wordCountObject class to set variables
        wordCountObject retVal = new wordCountObject();

        // set the keyword that was searched for on retVal
        retVal.setKeyword(keyword);

        // set the searchTerms that we will be counting on retVal
        retVal.setSearchTerms(searchTerms);

        // iterate through the response objects (Docs[] aka article)
        // for each loop Doc object - search for each search term provided by the user
        for (Docs doc : root.getResponse().getDocs()) {
            // get headline
            String headline = doc.getHeadline().getMain();
            // get snippet
            String snippet = doc.getSnippet();

            // count in headline number of times keyword appears
            headline = StringUtils.lowerCase(headline);
            int countKeyword = StringUtils.countMatches(headline, keyword);

            // count in snippet number of times keyword appears
            snippet = StringUtils.lowerCase(snippet);
            int countKey = StringUtils.countMatches(snippet, keyword);

            // plus the both to get the total number of times keyword appears
            int totalCount = countKey + countKeyword;

            // use merge method to update HashMap with keyword and count
             retVal.getWordCountMap().merge(keyword, totalCount, Integer::sum);

            // add each article to the arraylist of articles in retVal
            retVal.getArticles().add(new Article(doc.getHeadline().getMain(), doc.getWeb_url(), doc.getSnippet()));

        }
        return retVal;
    }

}
