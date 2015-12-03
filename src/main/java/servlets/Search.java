/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.SearchProvider;
import stores.SearchResult;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;

/**
 *
 * @author Alexander Grey
 */
@WebServlet(name = "Search", urlPatterns = {"/search", "/search/*", "/search/*/page/*"})
public class Search extends HttpServlet 
{
    Cluster cluster = null;
    
    @Override
    public void init(ServletConfig config) throws ServletException 
    {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String[] parts = Convertors.SplitRequestPath(request);
        
        switch(parts.length)
        {
            case 2:
            {
                response.sendRedirect("/search/" + parts[1] + "/page/1");
                break;
            }
            case 4:
            {
                long start = System.nanoTime();
                
                String terms = parts[1];
                int pageNumber = 1;
                
                try
                {
                    pageNumber = Integer.parseInt(parts[3]);
                }
                catch(Exception e)
                {
                    response.sendRedirect("/");
                    break;
                }
                
                if(pageNumber < 1)
                {
                    response.sendRedirect("/");
                }
                else
                {
                    pageNumber = pageNumber - 1;
                    
                    SearchProvider search = new SearchProvider(cluster);
                    LinkedList<SearchResult> results = search.performSearch(terms, pageNumber);
                    
                    long end = System.nanoTime();
                    double duration = (double)((end - start) / 1000000000.0);
                    
                    request.setAttribute("pageNumber", pageNumber);
                    request.setAttribute("time", duration);
                    request.setAttribute("results", results);
                    request.setAttribute("showResult", true);
                    request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
                }
                
                break;
            }
            default:
            {
                response.sendRedirect("/");
                break;
            }
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        long start = System.nanoTime();
        
        String terms = (String)request.getParameter("terms");
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        
        SearchProvider search = new SearchProvider(cluster);
        LinkedList<SearchResult> results = search.performSearch(terms, pageNumber);
        
        long end = System.nanoTime();
        double duration = (double)((end - start) / 1000000000.0);
        
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("time", duration);
        request.setAttribute("results", results);
        request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() 
    {
        return "Short description";
    }
    // </editor-fold>
}