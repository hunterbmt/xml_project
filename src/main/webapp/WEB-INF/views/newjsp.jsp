<%-- 
    Document   : newjsp
    Created on : Mar 9, 2013, 7:55:37 PM
    Author     : TH31012013
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/resources/css/bootstrap.css" rel="stylesheet">
        <link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
        <script type="text/javascript"
        src="http://platform.twitter.com/widgets.js"></script>
        <script src="/resources/js/lib/prettify.js"></script>

        <script src="/resources/js/lib/jquery.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/bootstrap-transition.js"></script>
        <script src="/resources/js/lib/bootstrap-alert.js"></script>
        <script src="/resources/js/lib/bootstrap-modal.js"></script>
        <script src="/resources/js/lib/bootstrap-dropdown.js"></script>
        <script src="/resources/js/lib/bootstrap-scrollspy.js"></script>
        <script src="/resources/js/lib/bootstrap-tab.js"></script>
        <script src="/resources/js/lib/bootstrap-tooltip.js"></script>
        <script src="/resources/js/lib/bootstrap-popover.js"></script>
        <script src="/resources/js/lib/bootstrap-button.js"></script>
        <script src="/resources/js/lib/bootstrap-collapse.js"></script>
        <script src="/resources/js/lib/bootstrap-carousel.js"></script>
        <script src="/resources/js/lib/bootstrap-typeahead.js"></script>
        <script src="/resources/js/lib/bootstrap-affix.js"></script>
        <script src="/resources/js/lib/application.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="/resources/js/vteam.js"></script>
        <script type="text/javascript">
            function login(){
            var email=document.getElementById("user_username").value;
            var password=document.getElementById("user_password").value;
            vteam_http.init();
            vteam_http.makeHttpRequest("/user/login",
                                    {email:email,
                                    password:password},
                                    "POST",
                      function (result){
                        if(result.status =="success")
                            {
                           $('#fat-menu').hide(); 
                            $('#loginResult').html("Welcome"+ '<a class="btn btn-primary" href="/user/detail"><i class="icon-user icon-white"></i>'  + result.email+' </a>').show();
                            }
                         else{
                             alert("Error");
                         }   
                    });
           
            }
        
        </script>
    </head>
    <body>
        <div id="navbar-example" class="navbar navbar-static">
            <div class="navbar-inner">
                <div class="container" style="width: auto;">
                    <a class="brand" href="#">My Site</a>

                    <ul class="nav pull-right">
                         <div id="loginResult" style="display: none"></div>
                        <li id="fat-menu" class="dropdown">
                            <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">Login <b class="caret"></b></a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="#">
                                       
                                        <div id="login">  
                                            
                                        <form id="loginForm" accept-charset="UTF-8">
                                            <label > Username:</label>
                                            <input id="user_username"  type="text" name="username" />
                                            <label > Password:</label>
                                            <input id="user_password"  type="password" name="password" />
                                            <input class="btn btn-primary"  value="Log In" onclick="login()" />
                                            <input class="btn btn-inverse" type="button" value="Register here"/>
                                        </form>
                                        
                                        <div id="sigin" style="display: none">
                                            <form  method="post" accept-charset="UTF-8">
                                            <label > Username:</label>
                                            <input id="user_username"  type="text" name="username" />
                                            <label > Password:</label>
                                            <input id="user_password"  type="password" name="password" />
                                            <label > Re-Password:</label>
                                            <input id="user_repassword"  type="password" name="password" />
                                            <input class="btn btn-primary"  type="submit" value="Sin In" />
                                          
                                        </div>
                                        </div>
                                    </a>
                                        </li>
                             
                            </ul>
                        </li>
                    </ul>
                    
                </div>
            </div>
        </div>
    </body>
</html>

