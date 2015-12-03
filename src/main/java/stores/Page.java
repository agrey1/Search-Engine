/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stores;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Grey
 */
public class Page 
{
    private String url;
    private String title;
    private String words;
    List<String> urls = new ArrayList<>();
    
    public Page(String url)
    {
        this.url = url;
        this.words = "";
        this.title = null;
    }
    
    public void addWord(String word)
    {
        this.words += " " + word;
    }
    
    public String getTitle()
    {
        return this.title;
    }
    
    public String getWords()
    {
        return this.words.trim();
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
}
