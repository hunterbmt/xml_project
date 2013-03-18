<%-- 
    Document   : login
    Created on : Mar 17, 2013, 8:47:17 PM
    Author     : Crick
--%>
<%@page session="false"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <header>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>        
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
                            <!--                            <p class="navbar-text pull-right">
                                                            <a href="login.htm" class="navbar-link">login</a> or <a href="register.htm" class="navbar-link">register</a>
                                                        </p> -->
                            <ul class="nav pull-right">
                                <div id="loginResult" style="display: none;margin-top: 10px"></div>
                                <li id="fat-menu" class="dropdown" >
                                    <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">Login <b class="caret"></b></a>
                                    <ul class="dropdown-menu" role="menu" aria-labelledby="drop3" style="width: 300px">
                                        <li ><div role="menuitem" tabindex="-1" style="padding: 3px 20px;display: block">

                                                <div id="login">Login
                                                    <form >
                                                        <fieldset>
                                                            <label class="label-main">Username</label>
                                                            <input name="miniusername"  id="user_username" type="text">
                                                            <label class="label-main">Password</label>
                                                            <input name="miniusername"  id="user_password" type="password">
                                                            <p/>
                                                            <button name="send" type="button"  class="btn btn-primary btn-small" onclick="login()">Login</button>
                                                            <button name="send" type="button"  class="btn btn-inverse btn-small" onclick="changeSigin()">Sig in</button>
                                                        </fieldset>
                                                    </form>

                                                </div>
                                                <div id="sigin" style="display: none">Sig in
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
            <div class="container" style="position: absolute;margin-top:60px;width:60%;margin-left:200px;">
            <ul class="nav nav-tabs">
                <li class="active"><a href="#home" data-toggle="tab">Profile</a></li>
                <li><a href="#profile" data-toggle="tab">Change Password</a></li>
                <li><a href="#profile" data-toggle="tab">Order History</a></li>
            </ul>
            <div id="myTabContent" class="tab-content">
                <div class="tab-pane active in" id="home">
                    <form id="tab">
                        <label>Email</label>
                        <input type="text" id="email" class="input-xlarge" value="${user_email}">
                        
                        <label>First Name</label>
                        <input type="text"  class="input-xlarge" value="${user_fullname}">
                        <label>Phone</label>
                        <input type="text" id="phone"  class="input-xlarge">
                        
                        <label>Address</label>
                        <input type="text" id="address"  class="input-xlarge">
                        <label>Birthday</label>
                        <input type="text" id="birthday" class="input-xlarge">
                        <div>
                            <button class="btn btn-primary" onclick="updateInfo()">Update</button>
                        </div>
                    </form>
                </div>
              <div class="tab-pane fade" id="profile">
                    <form id="tab2">
                        <label>Password</label>
                        <input type="password" id="newpassword" class="input-xlarge">       
                        <label>Old Password</label>
                        <input type="Password" id="curr_password" class="input-xlarge">
                        <div>
                            <button class="btn btn-primary" onclick="updatePassword()">Update</button>
                        </div>
                        
                    </form>
                </div>
                <div class="tab-pane fade" id="profile">
                    <form id="tab3">
                        <label>Order History</label>
                        
                    </form>
                </div>
                
            </div>
           </div>
            </div>
    </body>
</html>