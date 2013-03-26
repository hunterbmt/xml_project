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
        <link href="/resources/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
        <link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="/resources/css/font-awesome.css" rel="stylesheet">
        <link href="/resources/css/product.css" rel="stylesheet">
        <link href="/resources/css/jquery-ui-helper.css" rel="stylesheet">
        <script src="/resources/js/lib/jquery.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="/resources/js/lib/bootstrap.min.js"></script>
        <script src="/resources/js/lib/holder.js"></script>
        <script src="/resources/js/vteam.js"></script>
        <script src="/resources/js/bid.js"></script>
        <script src="/resources/js/product.js"></script>
        <script src="/resources/js/category.js"></script>
        <script src="/resources/js/login.js"></script>   
        <script src="/resources/js/order.js"></script> 
        <script src="/resources/js/jquery-ui-timepicker-addon.js"></script>
        <script src="/resources/js/dateUtils.js"></script>
        <script>
            $(document).ready(function() {
                loadAndDisplayProduct(1);
                loadAndDisplayCategory();
                loadUserInfo();
                $('#user_birthday').datepicker({
                    showMonthAfterYear: false,
                    minDate: '-90Y', maxDate: '-9Y',
                    yearRange: '1930:2000',
                    showOn: 'focus', changeYear: true});
            });
        </script>
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
                                    <input class="span3" id="appendedInputButtons" type="text" onkeydown="searchOnKeyDown(event)">
                                    <button onclick="searchProduct()" class="btn btn-primary" type="button" ><i class="icon-search" ></i></button>
                                </div>           
                            </form>
                            <ul class="nav">

                                <li class="active"><a href="#" onclick="changeContext()">Home</a></li>
                                <li><a href="#">Hot Bids</a></li>
                                <li><a href="#">FAQ</a></li>
                            </ul>
                            <ul class="nav pull-right">
                                <div id="loginResult" style="display: none;margin-top: 10px">

                                </div>


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
                                                    <div id="error" style="color: red;display: none"></div>
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


                                        </li>

                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>

        <div class="container" style="margin-top: 80px">
            <div class="row-fluid" id="product">
                <div id="side-section" class="span3 category">   
                    <ul id ="category_div" class="nav nav-list">

                    </ul>
                </div>
                <div class="span9" id="product_list">
                    <ul class="thumbnails">


                    </ul>
                </div>
                <div class="span9 hide" id="search_product_list">
                    <ul class="thumbnails">


                    </ul>
                </div>

                <div class="span9 hide" id="product_detail">
                    <ul class="p_detail">


                    </ul>
                </div>

                <div class="span9" id="search_product_category_list" style="display: none">
                    <ul class="p_search_by_category">



                    </ul>
                </div>
            </div>
        </div>
        <div class="container" id="user_detail" style="position: absolute;width:70%;display: none">
            <div class="alert" id="updateResult1" style="display: none;z-index: 9002">

            </div>
            <div class="tabbable tabs-left">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#lA" data-toggle="tab">Profile</a></li>
                    <li class=""><a href="#lB" data-toggle="tab">Change Password</a></li>
                    <li class=""><a href="#lC" data-toggle="tab" onclick="loadAndDisplayOrder()">Orders History</a></li>
                    <li class=""><a href="#lD" data-toggle="tab">Payment</a></li>
                </ul>
                <div class="tab-content">

                    <div class="tab-pane active" id="lA">
                        <form id="tab">
                            <label>Email</label>
                            <input type="text" id="user_email" class="input-xlarge">
                            <input type="hidden" id="user_id" class="input-xlarge">
                            <label>First Name</label>
                            <input type="text" id="user_fullname" class="input-xlarge">
                            <label>Phone</label>
                            <input type="text" id="user_phone"  class="input-xlarge">

                            <label>Address</label>
                            <input type="text" id="user_address"  class="input-xlarge">
                            <label>Birthday</label>
                            <input type="text" id="user_birthday" class="input-xlarge">
                            <label>Balance</label>
                            <input type="text" id="user_balance" class="input-xlarge" readonly="true">
                            <div class="row" style="display: inline;margin-left: 0px" >
                                <button type='button' class="btn btn-primary" onclick="updateInfo()">Update</button>
                            </div>
                            </span>
                        </form>
                    </div>
                    <div class="tab-pane" id="lB">
                        <form id="tab2">
                            <label>Password</label>
                            <input type="password" id="newpassword" class="input-xlarge">       
                            <label>Old Password</label>
                            <input type="password" id="curr_password" class="input-xlarge">
                            <div>   
                                <button type='button' class="btn btn-primary" onclick="updatePassword()">Update</button>

                            </div>

                        </form>
                    </div>
                    <div class="tab-pane" id="lC" style="width: 100%" >
                        <table class="table table-hover">
                            <caption><h3>User's Order History</h3> </caption>
                            <thead>
                                <tr>
                                    <td><h4>Product's Name</h4></td>
                                    <td><h4>Address</h4></td>
                                    <td><h4>Date</h4></td>
                                    <td><h4>Amount</h4></td> 
                                </tr>
                            </thead>
                            <tbody id="orderResult">

                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane" id="lD">
                        <form id="tab4">
                            <label>Enter your code here:</label>
                            <input type="password" id="newpassword" class="input-xlarge">       
                            <div>   
                                <button type='button' class="btn btn-primary" onclick="updatePassword()">Update</button>

                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
