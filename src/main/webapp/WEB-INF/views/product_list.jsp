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
        <link href="/resources/css/product.css" rel="stylesheet">
        <script src="/resources/js/lib/jquery.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
        <script src="/resources/js/lib/holder.js"></script>
        <script src="/resources/js/vteam.js"></script>
        <script src="/resources/js/productfunction.js"></script>

    </header>
    <body>
        <div id="header">
            <div class="navbar navbar-fixed-top">
                <div class="navbar-inner">
                    <div class="container">
                        <a href="index.htm"> <h1>VTeam</h1></a>
                        <div class="nav-collapse collapse">
                            <form class="navbar-form pull-left">
                                <div class="input-append">
                                    <input class="span3" id="appendedInputButtons" type="text">
                                    <button class="btn btn-primary" type="button"><i class="icon-search"></i></button>
                                </div>           
                            </form>
                            <ul class="nav">

                                <li class="active" ><a href="#">Home</a></li>
                                <li><a href="#">Hot Bids</a></li>
                                <li><a href="#">FAQ</a></li>
                            </ul>
                            <p class="navbar-text pull-right">
                                <a href="login.htm" class="navbar-link">login</a> or <a href="register.htm" class="navbar-link">register</a>
                            </p> 
                        </div><!--/.nav-collapse -->
                    </div>
                </div>
            </div>
        </div>
        <div class="container" style="margin-top: 80px">
            <div class="row-fluid">
                <div id="side-section" class="span3">
                    <div>
                        <ul class="nav nav-list">
                            <li class="nav-header"><i class="icon-lock"></i>Category</li> 
                        </ul>
                        <ul class ="categorylist">
                            <li>Dien Thoai</li>
                            <li>Laptop</li>
                        </ul>
                    </div>
                    <div class="module-top"><i class="icon-lock"></i> Quick Login</div>
                    <div class="module">
                        <form method="post" action="?" data-jkit="[form:validateonly=true]">
                            <fieldset>
                                <label class="label-main">Username</label>
                                <input name="miniusername" class="span10" id="miniusername" type="text" data-jkit="[validate:required=true;min=3;max=10;error=Please enter your username (3-10 characters)]">
                                <label class="label-main">Password</label>
                                <input name="miniusername" class="span10" id="miniusername" type="text" data-jkit="[validate:required=true;min=3;max=10;error=Please enter your username (3-10 characters)]">
                                <p><label class="checkbox"><input type="checkbox">remember me</label></p>
                                <button name="send" type="submit" value="Submit" class="btn btn-small">Login</button>
                            </fieldset>
                            <input type="hidden" name="jkit-requireds" id="jkit-requireds">
                        </form>
                    </div>
                </div>
                <div class="span9">
                    <ul class="thumbnails">


                    </ul>
                </div>
                <div class="pagination pagination-small pull-right">
                    <ul>
                        <li><a href="#">«</a></li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">»</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </body>
    <!--    <script>
            $(document).ready(function() {
                vteam_http.init();
                vteam_http.makeHttpRequest("/product/getProductList",
                        {page: 1, pageSize: 5},
                'POST',
                        function(result) {
                            if (result.status == 'success') {
                                displayProduct(result.result.productList)
                            }
                        });
            });
            function displayProduct(productList) {
                var html = '';
                for (var i =0 ; i < productList.length; i++) {
                    html += '<li>'
                    html += '<td>'+productList[i].name+'</td>'
                    html += '<td>'+productList[i].description+'</td>'
                    html += '<td>'+productList[i].image+'</td>'
                    html +='</li>'
                }
                $("table >tbody").html(html)
            }
        </script>-->
</html>
