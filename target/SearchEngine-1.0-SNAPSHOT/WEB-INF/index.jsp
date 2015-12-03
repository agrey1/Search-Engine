<%-- 
    Document   : index
    Created on : 22-Nov-2015, 14:48:21
    Author     : Alexander Grey
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Search Engine</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/css/home.css">
        <script src="/js/jquery-2.1.4.js" type="text/javascript"></script>
        <script src="/js/search.js" type="text/javascript"></script>
    </head>
    <body>
        <div class="center">
            <h1>Search Engine</h1>
            <form id="searchForm">
                <input type="text" id="searchText"/>
                <button type="button" id="searchSubmit">Perform Search</button>
            </form>
            
            <div id="results">
                <%
                Object showResult = request.getAttribute("showResult");
                if(showResult != null && (boolean)showResult == true)
                {
                    %>
                    <%@include file="/WEB-INF/search.jsp" %>
                    <%
                }
                %>
            </div>
        </div>
    </body>
</html>
