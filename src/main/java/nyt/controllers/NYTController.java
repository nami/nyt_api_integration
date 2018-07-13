package nyt.controllers;

import nyt.model.db.DBSearch;
import nyt.model.db.wordCountObject;
import nyt.model.nyt.NYTRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import nyt.services.NYTService;

// request mapping will happen
@RestController
// will set up servlets
@RequestMapping("/nyt")
public class NYTController {

    // autowire service class
    @Autowired
    NYTService service;

    // returns NYTRoot
    @RequestMapping("/search")
    // takes request param, query command (?)
    public NYTRoot searchNYT(
            @RequestParam("keyword") String keyword,
            @RequestParam("persist") String persist) {

        // returns whatever NYT response returns
        NYTRoot response = service.searchNYT(keyword, persist);

        return response;
    }

    // returns NYTRoot
    @RequestMapping("/searchheadlines")
    // takes request param, query command (?)
    public NYTRoot searchHeadlines(
            @RequestParam("keyword") String keyword,
            @RequestParam("persist") String persist) {

        // returns whatever NYT response returns
        NYTRoot response = service.searchHeadlines(keyword, persist);

        return response;
    }

    // in the controller
    @RequestMapping("/searchDB")
    public DBSearch searchDB(
            @RequestParam("keyword") String keyword) {

        DBSearch response = service.findArticles(keyword);

        return response;
    }


    // comma seperated search terms will be mapped onto an array
    @RequestMapping("/wordcount")
    public wordCountObject wordCount(
            @RequestParam("keyword") String keyword,
            @RequestParam("searchTerms") String[] searchTerms) {

        return service.countObject(keyword, searchTerms);
    }

}
