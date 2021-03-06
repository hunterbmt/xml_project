
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
        <link href="/resources/css/jquery-ui-helper.css" rel="stylesheet">
        <link href="/resources/css/product.css" rel="stylesheet">
        <script src="/resources/js/lib/jquery.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="/resources/js/lib/bootstrap.min.js"></script>
        <script src="/resources/js/lib/holder.js"></script>
        <script src="/resources/js/vteam.js"></script>
        <script src="/resources/js/bid.js"></script>
        <script src="/resources/js/product.js"></script>
        <script src="/resources/js/search.js"></script>
        <script src="/resources/js/category.js"></script>
        <script src="/resources/js/login.js"></script>   
        <script src="/resources/js/order.js"></script> 
        <script src="/resources/js/jquery-ui-timepicker-addon.js"></script>
        <script src="/resources/js/dateUtils.js"></script>
        <script src="/resources/js/xml_transform_helper.js"></script>
        <script>
            window.onload = function() {
                loadAndDisplayProduct(1);
                initUserInfo();
                $('#user_birthday').datepicker({
                    showMonthAfterYear: false,
                    minDate: '-90Y', maxDate: '-9Y',
                    yearRange: '1930:2000',
                    showOn: 'focus', changeYear: true});
//                $('#appendedInputButtons').typeahead({
//                    source: function(query,process) {
//                        if (query === '') {
//                            return false;
//                        }
//                        else {
//                            return vteam_http.makeHttpRequest('/search/searchTypeAHeader', {query: query}, 'POST',
//                                    function(data) {
//                                        if (data.status === 'success') {
//                                            return process(data.keywordList);
//                                        }
//                                    })
//                        }
//                    },
//                    items: 5,
//                    minLength: 3
//                });
            }</script>
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
                            <a href="/">
                                Bid 20!
                                <i class="icon-legal"></i>
                            </a>
                        </h1>
                        <div class="nav-collapse collapse">
                            <form class="navbar-form pull-left">
                                <div class="input-append">
                                    <input class="span3" id="appendedInputButtons" autocomplete="off" type="text" onkeydown="searchOnKeyDown(event)">
                                    <button onclick="searchProductAtLocal('',1)" class="btn btn-success" type="button" ><i class="icon-search" ></i></button>
                                </div>           
                            </form>
                            <ul id ="menu"class="nav">
                                <c:import url="/resources/xml/main_page_menu.xml" var="menuXML" charEncoding="UTF-8"/>
                                <c:import url="/resources/xsl/main_page_menu.xsl" var="menuXSL"/>
                                <x:transform xml="${menuXML}" xslt="${menuXSL}"/>
                                <li class="test" id="login_menu"><a href="#" onclick="showLogin()">Đăng Nhập</a></li>
                                <li><div class="test hide" id="loginResult" style="margin-top: 5px"></div></li>

                            </ul>
                        </div>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>
        <div id="CategoriesBar" class="-exp navbar-fixed-top">
            <ul id ="CategoriesBar_ul"class="LiquidContainer HeaderContainer" style="width: 100%">
                <c:import url="/resources/xml/category.xml" var="categoryXML" charEncoding="UTF-8"/>
                <c:import url="/resources/xsl/category.xsl" var="categoryXSL"/>
                <x:transform xml="${categoryXML}" xslt="${categoryXSL}"/>
            </ul>
        </div>
        <div class="container" id="product" style="position: relative">

            <div id="wrapper">
                <div id="columnContainer" class="row-fluid">
                    <a class="carousel-control left" id="back" onclick="backOnClick()" href="javascript:void(0)">‹</a>
                    <a class="carousel-control right" id="next" onclick="nextOnClick()" href="javascript:void(0)">›</a>
                    <div  class="span10 offset1" id="product_list">
                    </div>
                    <div class="span10 offset1 hide" id="search_product_list">
                    </div>
                    <div class="span10 offset1 hide" id="product_detail">

                    </div>
                    <div class="span10 offset1 hide" id="user_login" style="margin-top: 40px;">

                        <div class="Login"  id="login">
                            <div id="error" style="color: red;margin-left: 30%;margin-bottom: 5px" class="hide"></div>
                            <form class="login-form">
                                <fieldset style="padding-left: 300px;">
                                    <div class="control-group">
                                        <label class="control-label">Tên đăng nhập</label>
                                        <div class="controls">
                                            <div class="input-prepend large"><span class="add-on" style="height: 30px;"><i class="icon-user"></i></span><input class="input-large" name="miniusername"  id="user_username" type="text" style="height: 30px;" onblur="validUsername()"></div>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="label-main">Mật khẩu</label>
                                        <div class="controls">
                                            <div class="input-prepend"><span class="add-on" style="height: 30px;"><i class="icon-lock"></i></span></span><input class="input-large" name="miniusername"  id="user_password" type="password" style="height: 30px;" onblur="validPassword()"></div>
                                        </div>
                                    </div>
                                    <p/>
                                    <button name="send" type="button" class="btn btn-success" onclick="login()">Đăng nhập</button>
                                    <button name="send" type="button"  class="btn " onclick="changeSigin()" style="margin-left: 12%">Đăng ký</button>

                                </fieldset>
                            </form>
                        </div>
                        <div class="Sigin hide" id="signin">
                            <form  method="post" accept-charset="UTF-8">
                                <fieldset style="padding-left: 300px;">
                                    <div class="control-group">
                                        <label > Tên đăng nhập:<font style="color: red">*</font></label>
                                        <input id="new_username" name="email" type="text" name="username" onblur="valid_register_Username()"/>
                                        <div class="hide" id="email_validation" sytle=""></div>
                                    </div>
                                    <div class="control-group">
                                        <label > Mật khẩu:<font style="color: red">*</font></label>
                                        <input id="new_password"  type="password" name="password" onblur="valid_register_Password()" />
                                    </div>
                                    <div class="control-group">
                                        <label > Nhập lại mật khẩu:<font style="color: red">*</font></label>
                                        <input id="new_repassword"  type="password" name="password" onblur="valid_register_rePassword()" />
                                        <div class="hide" id="pass_validation"></div>
                                    </div>
                                    <div class="control-group">
                                        <label > Tên thật:<font style="color: red">*</font></label>
                                        <input id="new_fullname"  type="text" name="fullname" onblur="valid_register_Fullname()" /><p/>
                                    </div>
                                    <div class="hide" id="fullname_validation"><font style="color: red">Hãy nhập tên đầy đủ</font></div>
                                    <div class="control-group">
                                        <label > Số điện thoại:<font style="color: red">*</font></label>
                                        <input id="new_phone" type="text" name="fname" onblur="valid_register_Phone()"><br>
                                        <div class="hide" id="phone_validation"></div>
                                    </div>
                                    
                                    <div>
                                        <input type="text" id="txtCaptcha" 
                                               style="background-color: #FFA0A0; text-align:center; border:none;
                                               font-weight:bold; font-family:Modern" readonly="true" onpaste="return false;"/>
                                        <i style="margin-left: 5px;" class="icon-refresh" id="btnrefresh"onclick="DrawCaptcha();"></i>
                                         
                                    </div>
                                    <div class="control-group">
                                        <label > Nhập mã xác nhận tại đây:</label>
                                        <input type="text" id="txtInput"/> 
                                    </div>
                                    <div class="hide" id="capcha_validation"><font style="color: red">Hãy nhập đúng mã xác nhận</font></div>
                                    <button name="send" type="button"  class="btn btn-success" onclick="create()">Đăng ký</button>
                                    <button name="send" type="button"  class="btn" onclick="changeLogin()"style="margin-left: 16%">Hủy bỏ</button>
                                </fieldset>
                            </form>
                            <div id="result" style="display: none"></div>
                        </div>
                    </div>

                    <div class="span10 offset1 hide" id="user_detail" style="margin-top: 40px;">
                        <div class="alert hide" id="updateResult1"></div>
                        <div class="tabbable tabs-left">
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#lA" data-toggle="tab" onclick="loadUserInfo()">Thông tin chi tiết</a></li>
                                <li class=""><a href="#lB" data-toggle="tab">Thay đổi mật khẩu</a></li>
                                <li class=""><a href="#lC" data-toggle="tab" onclick="getOrderList()">Lịch sử mua hàng</a></li>
                                <li class=""><a href="#lD" data-toggle="tab" onclick="hidePaymentList()">Nạp thẻ</a></li>
                            </ul>
                            <div class="tab-content">

                                <div class="tab-pane active" id="lA">
                                    <form id="tab">
                                        <label>Email</label>
                                        <input type="text" id="user_email" readonly="true">
                                        <input type="hidden" id="user_id" >
                                        <label>Tên thật</label>
                                        <input type="text" id="user_fullname">
                                        <label>Số điện thoại</label>
                                        <input type="text" id="user_phone">
                                        <label>Địa chỉ</label>
                                        <input type="text" id="user_address">
                                        <label>Ngày sinh</label>
                                        <input type="text" id="user_birthday">
                                        <label>Tài khoản</label>
                                        <input type="text" id="user_balance" readonly="true">
                                        <div class="row" style="display: inline;margin-left: 0px" >
                                            <button type='button' class="btn btn-success" onclick="updateInfo()">Update</button>
                                        </div>
                                        </span>
                                    </form>
                                </div>
                                <div class="tab-pane" id="lB">
                                    <form id="tab2">
                                        <label>Mật khẩu mới</label>
                                        <input type="password" id="newpassword" >       
                                        <label>Mật khẩu cũ</label>
                                        <input type="password" id="curr_password">
                                        <div>   
                                            <button type='button' class="btn btn-success" onclick="updatePassword()">Update</button>
                                        </div>
                                    </form>
                                </div>
                                <div class="tab-pane" id="lC" style="width: 100%" onclick="loadAndDisplayOrder()">
                                    <table class="table table-hover">
                                        <caption><h3>Lịch sử mua hàng</h3> </caption>
                                        <thead>
                                            <tr>
                                                <td><h4>Tên sản phẩm</h4></td>
                                                <td><h4>Địa chỉ</h4></td>
                                                <td><h4>Ngày mua hàng</h4></td>
                                                <td><h4>Giá tiền</h4></td> 
                                            </tr>
                                        </thead>
                                        <tbody id="orderResult">

                                        </tbody>
                                    </table>
                                    <div>   
                                        <button type='button' class="btn btn-success" onclick="exportPDF()">Xuất PDF</button>
                                    </div>
                                </div>
                                <div class="tab-pane" id="lD" >
                                    <form id="tab4">
                                        <label>Nhập mã thẻ:</label>
                                        <input type="text" id="payment_code">       
                                        <div>   
                                            <button type='button' class="btn btn-success" onclick="inputCode()">Cập Nhật</button>
                                            <button type='button' class="btn btn-success" onclick="loadAndDisplayPayment()">Lịch sử thanh toán</button>
                                        </div>
                                    </form>
                                    <table class="table table-hover hide" id="tablePayment">
                                        <caption><h3>Lịch sử thanh toán</h3> </caption>
                                        <thead>
                                            <tr>
                                                <td><h4>Mã thẻ</h4></td>
                                                <td><h4>Ngày nạp</h4></td>
                                                <td><h4>Số tiền</h4></td> 
                                            </tr>
                                        </thead>

                                        <tbody  id="paymentResult">

                                        </tbody>
                                    </table>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>  
</body>
</html>