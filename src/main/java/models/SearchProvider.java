/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.util.LinkedList;
import stores.SearchResult;

/**
 * @author Alexander Grey
 */
public final class SearchProvider 
{
    Cluster cluster = null;
    
    public SearchProvider(Cluster cluster)
    {
        this.cluster = cluster;
    }
    
    public LinkedList<SearchResult> performSearch(String terms, int page)
    {
        terms = terms.toLowerCase();
        String[] termWords = terms.split(" ");
        LinkedList<SearchResult> results = new LinkedList<>();
        
        //Construct the JSON Solr query
        String solrQuery = "{\"q\":\"";
        for(int i = 0; i < termWords.length; i++)
        {
            if(i != 0)
            {
                solrQuery = solrQuery.concat(" ");
            }
            
            solrQuery = solrQuery.concat("words:" + termWords[i]);
            if(i != termWords.length - 1)
            {
                solrQuery = solrQuery.concat(" AND");
            }
        }
        
        int pageNumber = 10 * page;
        solrQuery = solrQuery.concat("\", \"start\":" + pageNumber + "}");
        
        Session session = cluster.connect("search");
        PreparedStatement ps = session.prepare("SELECT url, title, words FROM pages WHERE solr_query='" + solrQuery + "' LIMIT 11;");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet result = session.execute(boundStatement.bind());
        
        if(result.isExhausted())
        {
            return null;
        }
        else 
        {
            for(Row row : result) 
            {
                String snippet = "";
                String[] words = row.getString("words").split(" ");
                
                //Search for a snippet of text which is relevant
                int index = -1;
                
                for(int i = 0; i < words.length; i++)
                {
                    for(String term : termWords)
                    {
                        if(words[i].equals(term))
                        {
                            index = i;
                            break;
                        }
                    }
                    
                    if(index != -1) break;
                }
                
                if(index == -1)
                {
                    index = 0;
                }
                else if(index - 5 >= 0)
                {
                    index = index - 5;
                }
                
                for(int i = index; i < words.length && i < index + 30; i++)
                {
                    for(String term : termWords)
                    {
                        if(words[i].equals(term))
                        {
                            words[i] = "<span class=\"highlight\">" + words[i] + "</span>";
                        }
                    }
                    
                    snippet = snippet.concat(words[i] + " ");
                }
                
                SearchResult searchResult = new SearchResult(row.getString("url"), row.getString("title"), snippet);
                results.add(searchResult);
            }
        }
        
        return results;
    }
}