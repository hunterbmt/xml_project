<%-- 
    Document   : product_list
    Created on : Mar 12, 2013, 4:26:06 PM
    Author     : phitt60230
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
    <header>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
        <link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="//netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
        <link href="/resources/css/admin.css" rel="stylesheet">
        <script src="/resources/js/lib/jquery.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
        <script src="/resources/js/vteam.js"></script>
    </header>
    <h1>Exiting Products</h1>
    <table class="table">
        <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Image</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <script>
        $(document).ready(function() {
            vteam_http.init();
            vteam_http.makeHttpRequest("/product/getProductList",
                    {page: 1, pageSize: 5},
            'POST',
                    function(result) {
                        if (result.status == 'success') {
                            displayProduct(result.result)
                        }
                    });
        });
        function displayProduct(productList) {
            var html = '';
            for (; i < productList.length; i++) {
                html += '<tr>'
                html += '<td>'+productList[i].name+'</td>'
                html += '<td>'+productList[i].description+'</td>'
                html += '<td>'+productList[i].image+'</td>'
                html +='</tr>'
            }
            $("table >tbody").html(html)
        }
    </script>
</html>
