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
        <script src="/resources/js/product.js"></script>
        <script src="/resources/js/category.js"></script>
        <script src="/resources/js/login.js"></script>
        <script type="text/javascript">
            $(document).ready(function (){
                loadAndDisplayCategory();
                var pid = ${pid};
//                alert(pid);
                view_product_detail(pid);
            })
        </script>
        <title>Product Detail</title>
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
                                    <button onclick="searchProduct()" class="btn btn-primary" type="button" ><i class="icon-search" ></i></button>
                                </div>           
                            </form>
                            <ul class="nav">

                                <li class="active" onclick="changeContext()" ><a href="#">Home</a></li>
                                <li><a href="#">Hot Bids</a></li>
                                <li><a href="#">FAQ</a></li>
                            </ul>
                            <!--                            <p class="navbar-text pull-right">
                                                            <a href="login.htm" class="navbar-link">login</a> or <a href="register.htm" class="navbar-link">register</a>
                                                        </p> -->
                            <ul class="nav pull-right">
                                <div id="loginResult" style="display: none;margin-top: 10px"><c:out value = "${sessionScope.email}"/></div>
                                <li id="fat-menu" class="dropdown" >
                                    <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">Login <b class="caret"></b></a>
                                    <ul class="dropdown-menu" role="menu" aria-labelledby="drop3" style="width: 300px">
                                        <li ><div role="menuitem" tabindex="-1" style="padding: 3px 20px;display: block">

                                                <div id="login">
                                                    <form >
                                                        <fieldset>
                                                            <label class="label-main">Username</label>
                                                            <input name="miniusername"  id="user_username" type="text">
                                                            <label class="label-main">Password</label>
                                                            <input name="miniusername"  id="user_password" type="password">
                                                            <p/>
                                                            <button name="send" type="button"  class="btn btn-primary btn-small" onclick="login()">Login</button>
                                                            <button name="send" type="button"  class="btn  btn-small" onclick="changeSigin()">Sig up</button>
                                                        </fieldset>
                                                    </form>

                                                </div>
                                                <div id="sigin" style="display: none">Sig up
                                                    <form  method="post" accept-charset="UTF-8">
                                                        <label > Username:</label>
                                                        <input id="user_username"  type="text" name="username" />
                                                        <label > Password:</label>
                                                        <input id="user_password"  type="password" name="password" />
                                                        <label > Re-Password:</label>
                                                        <input id="user_repassword"  type="password" name="password" /><p/>
                                                        <input class="btn btn-primary btn-small"  type="button" value="Sin In" />
                                                        <input type="button" class="btn-group btn-small" value="Back" onclick="changeLogin()"/>

                                                </div>
                                            </div>
                                            </div>
                                        </li>

                                    </ul>
                                </li>
                            </ul>
                        </div><!--/.nav-collapse -->
                    </div>
                </div>
            </div>

            <div class="container" style="margin-top: 80px">
                <div class="row-fluid" id="product">
                    <div id="side-section" class="span3">   
                        <ul id ="category_div" class="nav nav-list">

                        </ul>

                        <div class="module-top" id="titleLogin"><i class="icon-lock"></i> Quick Login</div>
                        <div class="module" id="quickLogin">
                            <form >
                                <fieldset>
                                    <label class="label-main">Username</label>
                                    <input name="miniusername" class="span10" id="miniusername" type="text">
                                    <label class="label-main">Password</label>
                                    <input name="minipassword" class="span10" id="minipassword" type="password">
                                    <p><label class="checkbox"><input type="checkbox">remember me</label></p>
                                    <button name="send" type="button" value="Submit" class="btn btn-primary btn-small" onclick="quickLogin()">Login</button>
                                </fieldset>
                                <input type="hidden" name="jkit-requireds" id="jkit-requireds">
                            </form>
                        </div>
                    </div>                    

                    <div class="span9" id="product_detail" style="display: none">
                        <ul class="p_detail">


                        </ul>
                    </div>
                    <div class="span9" id="search_product_list" style="display: none">
                        <ul class="thumbnails">


                        </ul>
                    </div>
                    
                </div>
                
            </div>
    </body>
</html>
