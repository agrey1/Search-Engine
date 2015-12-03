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
public class VisitedPage 
{
    long time;
    String url;
    
    public VisitedPage(String url)
    {
        this.url = url;
        time = System.currentTimeMillis() / 1000l;
    }
    
    public String getURL()
    {
        return url;
    }
    
    public long getTime()
    {
        return time;
    }
}
