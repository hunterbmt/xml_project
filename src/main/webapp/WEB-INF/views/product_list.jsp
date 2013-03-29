
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <header>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="/resources/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
        <link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="/resources/css/font-awesome.css" rel="stylesheet">
        <link href="/resources/css/product.css" rel="stylesheet">
        <link href="/resources/css/animated-slider.css" rel="stylesheet">
        <link href="/resources/css/entypo.css" rel="stylesheet">
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
        <div id="loading">
            <img id="loading-image" src="/resources/img/loading.gif" alt="Đang tải dữ liệu" />
        </div>
        <div id="header">
            <div class="navbar navbar-fixed-top">
                <div class="navbar-inner">
                    <div class="container">
                        <h1 class="brand">
                            <a href="index.html">
                                Bid 20!
                                <i class="icon-legal"></i>
                            </a>
                        </h1>
                        <div class="nav-collapse collapse">
                            <form class="navbar-form pull-left">
                                <div class="input-append">
                                    <input class="span3" id="appendedInputButtons" type="text" onkeydown="searchOnKeyDown(event)">
                                    <button onclick="searchProduct(1)" class="btn btn-success" type="button" ><i class="icon-search" ></i></button>
                                </div>           
                            </form>
                            <ul id ="menu"class="nav">
                                <c:import url="/resources/xml/main_page_menu.xml" var="menuXML"/>
                                <c:import url="/resources/xsl/main_page_menu.xsl" var="menuXSL"/>
                                <x:transform xml="${menuXML}" xslt="${menuXSL}"/>
                                <li class="test" id="login_menu"><a href="#" onclick="showLogin()">Log in</a></li>
                                <li><div class="hide" id="loginResult" style="margin-top: 5px"></div></li>
                                
                            </ul>
                        </div>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>
        <div id="CategoriesBar" class="-exp navbar-fixed-top">
            <ul id ="CategoriesBar_ul"class="LiquidContainer HeaderContainer" style="width: 100%">
            </ul>
        </div>
        <div class="container" id="product" style="position: relative">

            <div id="wrapper">
                <div id="columnContainer" class="row-fluid">
                    <div class="span10" id="sequence-theme" style="width: 90%;position: fixed;margin-top: 100px;z-index: 100;">
                        <li class="prev">
                            <i class="e-icon-left-open-big-open-big">
                                <i class="icon-white icon-reply" id="back" onclick="backOnClick()"></i>
                            </i>
                        </li>
                        <li class="next">
                            <i class="e-icon-right-open-big-open-big">  
                                <i class="icon-white icon-share-alt" id="next" onclick="nextOnClick()"></i>
                            </i>
                        </li>
                    </div>
                    <div  class="span10 offset1" id="product_list">
                    </div>
                    <div class="span10 offset1 hide" id="search_product_list">
                    </div>
                    <div class="span10 offset1 hide" id="product_detail">

                    </div>
                    <div class="span10 offset1 hide" id="user_login" style="margin-top: 50px;">

                        <div class="Login"  id="login">
                                                    <form >
                                                        <fieldset style="padding-left: 300px;">
                                                            <label class="label-main">Username</label>
                                                            <div class="input-prepend large"><span class="add-on" style="height: 30px;"><i class="icon-user"></i></span><input class="input-large" name="miniusername"  id="user_username" type="text" style="height: 30px;"></div>
                                                            <label class="label-main">Password</label>
                                                            <div class="input-prepend"><span class="add-on" style="height: 30px;"><i class="icon-lock"></i></span></span><input class="input-large" name="miniusername"  id="user_password" type="password" style="height: 30px;"></div>

                                                            <p/>
                                                            <button name="send" type="button"  class="btn btn-success btn-small" onclick="login()">Login</button>
                                                            <button name="send" type="button"  class="btn  btn-small" onclick="changeSigin()">Sig up</button>

                                                        </fieldset>
                                                    </form>
                                                    <div id="error" style="color: red;display: none"></div>
                                                </div>
                        <div class="Sigin" id="sigin">
                                                    <form  method="post" accept-charset="UTF-8">
                                                        <fieldset style="padding-left: 300px;">
                                                        <label > Username:</label>
                                                        <input id="new_username"  type="text" name="username" />
                                                        <label > Password:</label>
                                                        <input id="new_password"  type="password" name="password" />
                                                        <label > Re-Password:</label>
                                                        <input id="new_repassword"  type="password" name="password" />
                                                        <label > Fullname:</label>
                                                        <input id="user_fullname"  type="text" name="fullname" /><p/>
                                                        <button name="send" type="button"  class="btn btn-success btn-small" onclick="create()">Create User</button>
                                                            <button name="send" type="button"  class="btn  btn-small" onclick="changeLogin()">Back</button>
                                                        </fieldset>
                                                    </form>
                                                    <div id="result" style="display: none"></div>
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
        <div id="CategoriesBar" class="-exp navbar-fixed-top">
            <ul id ="CategoriesBar_ul"class="LiquidContainer HeaderContainer" style="width: 100%">
            </ul>
        </div>
        <div class="container" id="product" style="position: relative">

            <div id="wrapper">
                <div id="columnContainer" class="row-fluid">
                 <div class="span10" id="sequence-theme" style="width: 100%;height: auto;overflow: hidden;position: absolute;margin-top: 200px;">
                    <li id="back" class="prev" >
                        <i class="e-icon-left-open-big-open-big">
                            <i class="icon-reply" onclick="backOnClick()"></i>
                        </i>
                    </li>
                    <li id="next" class="next">
                        <i class="e-icon-right-open-big-open-big">  
                            <i class="icon-white icon-share-alt"  onclick="nextOnClick()"></i>
                            </i>
                    </li>
                </div>
                    <div  class="span10 offset1" id="product_list">
                    </div>
                    <div class="span10 offset1 hide" id="search_product_list">
                    </div>

                    <div class="span10 offset1 hide" id="product_detail">
                        
                    </div>

                </div>

                <div class="span10 offset1 hide" id="user_detail" style="margin-top: 50px;">
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
                                        <button type='button' class="btn btn-success" onclick="updateInfo()">Update</button>
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
                                        <button type='button' class="btn btn-success" onclick="updatePassword()">Update</button>

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
                                        <button type='button' class="btn btn-success" onclick="updatePassword()">Update</button>

                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>


            </div>


        </div>


    </div>
</body>
</html>