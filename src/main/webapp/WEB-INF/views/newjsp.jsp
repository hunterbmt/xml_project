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
        <link href="resources/css/bootstrap.css" rel="stylesheet">
        <link href="resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="resources/css/bootstrap-responsive.css" rel="stylesheet">
        <script type="text/javascript"
        src="http://platform.twitter.com/widgets.js"></script>
        <script src="resources/js/lib/prettify.js"></script>

        <script src="resources/js/lib/jquery.js"></script>
        <script src="resources/js/lib/jquery.url.js"></script>
        <script src="resources/js/lib/bootstrap-transition.js"></script>
        <script src="resources/js/lib/bootstrap-alert.js"></script>
        <script src="resources/js/lib/bootstrap-modal.js"></script>
        <script src="resources/js/lib/bootstrap-dropdown.js"></script>
        <script src="resources/js/lib/bootstrap-scrollspy.js"></script>
        <script src="resources/js/lib/bootstrap-tab.js"></script>
        <script src="resources/js/lib/bootstrap-tooltip.js"></script>
        <script src="resources/js/lib/bootstrap-popover.js"></script>
        <script src="resources/js/lib/bootstrap-button.js"></script>
        <script src="resources/js/lib/bootstrap-collapse.js"></script>
        <script src="resources/js/lib/bootstrap-carousel.js"></script>
        <script src="resources/js/lib/bootstrap-typeahead.js"></script>
        <script src="resources/js/lib/bootstrap-affix.js"></script>
        <script src="resources/js/lib/application.js"></script>
        <script src="resources/js/lib/jquery-ui.js"></script>
    </head>
    <body>
        <div id="navbar-example" class="navbar navbar-static">
            <div class="navbar-inner">
                <div class="container" style="width: auto;">
                    <a class="brand" href="#">My Site</a>

                    <ul class="nav pull-right">
                        <li class="dropdown">
                            <a href="#" id="drop2" role="button" class="dropdown-toggle" data-toggle="dropdown">Sign in <b class="caret"></b></a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="drop2">
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="#">
                                        <form  method="post" accept-charset="UTF-8">
                                            <label > Username:</label>
                                            <input id="user_username"  type="text" name="username" />
                                            <label > Password:</label>
                                            <input id="user_password"  type="password" name="password" />
                                            <label > Re-Password:</label>
                                            <input id="user_repassword"  type="password" name="password" />
                                            <input class="btn btn-primary"  type="submit" value="Sin In" />
                                        </form>
                                    </a></li>

                            </ul>
                        </li>
                        <li id="fat-menu" class="dropdown">
                            <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">Login <b class="caret"></b></a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="#">
                                        <form  method="post" accept-charset="UTF-8">
                                            <label > Username:</label>
                                            <input id="user_username"  type="text" name="username" />
                                            <label > Password:</label>
                                            <input id="user_password"  type="password" name="password" />
                                            <input class="btn btn-primary"  type="submit" value="Log In" />
                                        </form>
                                    </a></li>

                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>
