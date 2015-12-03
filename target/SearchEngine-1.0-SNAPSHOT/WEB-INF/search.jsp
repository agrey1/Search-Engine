<%@page import="java.util.LinkedList"%>
<%@page import="java.util.LinkedList"%>
<%@page import="stores.SearchResult"%>
<%
    LinkedList<SearchResult> results = (LinkedList<SearchResult>)request.getAttribute("results");
    double duration = (double)request.getAttribute("time");
    int pageNumber = (int)request.getAttribute("pageNumber");
    
    if(results != null)
    {
        boolean nextPage = false;
        if(results.size() > 10) //Should be == 11 if there is another page available
        {
            results.removeLast();
            nextPage = true;
        }
        
        for(SearchResult result : results)
        {
            %>
            <h3><a href="<%=result.getURL()%>" target="_blank"><%=result.getTitle()%></a></h3>
            <span class="green"><%=result.getURL()%></span><br>
            <span><%=result.getWords()%></span>
            <%
        }
        
        %>
        <p>Found <%=results.size()%> result(s) in <%=duration%> seconds.</p>
        <p>Page <%=(pageNumber + 1)%>.</p>
        <%
        
        if(pageNumber > 0)
        {
            %>
            <button type="button" onclick="previousPage()">Previous Page</button>
            <%
        }
        else
        {
            %>
            <button type="button" disabled>Previous Page</button>
            <%
        }
        
        if(nextPage == true)
        {
            %>
            <button type="button" onclick="nextPage()">Next Page</button>
            <%
        }
        else
        {
            %>
            <button type="button" disabled>Next Page</button>
            <%
        }
    }
%>