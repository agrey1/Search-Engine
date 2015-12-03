/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var page = 0;
var timeout = null;

function search(term, pageNumber)
{
    $.ajax(
    {
        url: "/search",
        data: 'terms=' + term + '&pageNumber=' + pageNumber,
        method: "POST",
        success:function(html)
        {
            $('#results').html(html);
        }
    });
}

function nextPage()
{
    page += 1;
    var term = $("#searchText").val();
    
    search(term, page);
}

function previousPage()
{
    if(page > 0) page -= 1;
    var term = $("#searchText").val();
    
    search(term, page);
}

$(document).ready(function()
{
    $("#searchText").keyup(function()
    {
        page = 0;
        var term = $(this).val();
        
        if(term != "")
        {
            search(term, page);
            
            clearTimeout(timeout);
            timeout = setTimeout(function()
            {
                window.history.pushState('Search Results: ' + term, term + ' - Results', '/search/' + term + '/page/' + (page + 1));
            }, 1500);
        }
    });
    
    $("#searchSubmit").click(function()
    {
        $("#searchText").keyup();
    });
    
    $("#searchForm").submit(function(e) 
    {
        e.preventDefault();
    });
});