/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stores;

/**
 *
 * @author Alexander Grey
 */
public class SearchResult 
{
    String url;
    String title;
    String words;
    
    public SearchResult(String url, String title, String words)
    {
        this.url = url;
        this.title = title;
        this.words = words;
    }
    
    public String getURL()
    {
        return this.url;
    }
    
    public String getTitle()
    {
        return this.title;
    }
    
    public String getWords()
    {
        return this.words;
    }
}
