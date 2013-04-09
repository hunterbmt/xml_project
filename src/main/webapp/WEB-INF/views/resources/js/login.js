/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var current_user_info;
function login() {
    var email = document.getElementById("user_username").value;
    var password = document.getElementById("user_password").value;
    if (valid_Login(email, password)) {
        vteam_http.makeHttpRequest("/user/login",
                {email: email,
                    password: password},
        "POST",
                function(result) {
                    if (result.status == "success")
                    {
                        if (result.id == 1) {
                            window.location.href = "/admin";
                        } else {
                            current_user_info = result;
                            showLogedInMenu();
                            changeContext();
                        }
                    } else {
                        vteam_http.setHTML("error", "Sai tên đăng nhập hoặc mật khẩu");
                        vteam_http.show("error");
                    }
                });
    }

}
function showLogedInMenu() {
    vteam_http.hide("login_menu");
    vteam_http.setHTML("loginResult", '<a class="btn btn-success" id="email" style="margin-left:10px;margin-top:2px;" href="javascript:void(0)" onclick="loadUserInfo()"><i class="icon-user icon-white" ></i>' + current_user_info.fullname + ' </a>'
            + '<li id="fat-menu1" class="btn btn-success" style="width:70px;height:20px;margin-left:10px;" ><a href="javascript:void(0)" id="drop4" role="button" class="dropdown-toggle" data-toggle="dropdown" style="color:white;">' + current_user_info.balance + 'Nil' + '</a></li>'
            + '<a class="btn btn-success" id="logout" style="margin-left:10px;margin-top:2px;" href="javascript:void(0)" onclick="logout()"><i class="icon-off icon-white" ></i></a>');
    vteam_http.show("loginResult");
}
function logout() {
    var email = document.getElementById("user_username").value;
    vteam_http.makeHttpRequest("/user/logout",
            {email: email},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    displayunLogin();
                    vteam_http.hide("loginResult");
                    changeContext();
                }
                else {
                    alert("Error");
                }
            });
}
function changeSigin() {
    clearText();
    DrawCaptcha();
   document.getElementById('new_phone').value = '+84';

    currentPosition = -1;
    vteam_http.hide("login");
    vteam_http.show("signin");
}
function changeLogin() {
    clearText();
    currentPosition = -1;
    vteam_http.hide("error");
    vteam_http.hide("signin");
    vteam_http.show("login");
}
function displayunLogin() {
    vteam_http.hide("loginResult");
    vteam_http.show("login_menu");
}
function updateInfo() {
    var address = document.getElementById("user_address").value;
    var phone = document.getElementById("user_phone").value;
    var birthday = document.getElementById("user_birthday").value;
    var format_date = "MM/dd/yyyy";
    vteam_http.makeHttpRequest("/user/update",
            {address: address,
                phone: phone,
                birthday: birthday,
                formatDate: format_date},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    vteam_http.setHTML("updateResult1", "<font style='color: green;font-size: large;'><strong>Cập nhật thành công! !</strong></font>");
                    vteam_http.show("updateResult1");
                    $("#updateResult1").hide(5000);
                    loadUserInfo();
                    
                } else {
                    if (result.status == "unlogin") {
                        vteam_http.setHTML("updateResult1", "<font style='color: black;font-size: large;'><strong>Sesstion Times out! !</strong> Please log in again.</font>");
                    } else if (result.status == "error") {
                        vteam_http.setHTML("updateResult1", "<font style='color: red;font-size: large;'><strong>Oh snap!!</strong> Change a few things up and try submitting again.</font>");
                    }
                    vteam_http.show("updateResult1");
                    $("#updateResult1").hide(5000);
                }
            });
}
function showNotifications() {
    $("#updateResult1:visible").hide('fast', function() {
        $("#updateResult1").first().show('fast');
    });
}
function updatePassword() {
    var ollPass = document.getElementById("curr_password").value;
    var newPass = document.getElementById("newpassword").value;

    vteam_http.makeHttpRequest("/user/changePassword",
            {currentPass: ollPass,
                newPass: newPass},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    vteam_http.setHTML("updateResult1", "<font style='color: green;font-size: large;'><strong>Cập nhật thành công! !</strong></font>");


                } else if (result.status == "unlogin") {
                    vteam_http.setHTML("updateResult1", "<font style='color: black;font-size: large;'><strong>Sesstion Times out! !</strong> Please log in again.</font>");


                } else if (result.status == "error") {
                    vteam_http.setHTML("updateResult1", "<font style='color: red;font-size: large;'><strong>Oh snap!!</strong> Change a few things up and try submitting again.</font>");
                }
                vteam_http.show("updateResult1");
                $("#updateResult1").hide(5000);
            });

}
function loadUserInfo() {
    vteam_http.makeHttpRequest("/user/get_user_by_email", {},
            "POST",
            function(result) {
                if (result.status == "success")
                {
                    current_user_info = result;
                    displayUserDetail();
                } else {
                    alert("error");
                }
            });
}
function displayUserDetail() {
    currentPosition += 1;
    divArray[currentPosition] = "user_detail";
    document.getElementById('user_email').value = current_user_info.email;
    document.getElementById('user_id').value = current_user_info.id;
    document.getElementById('user_fullname').value = current_user_info.fullname;
    document.getElementById('user_phone').value = current_user_info.phone;
    if (current_user_info.birthday) {
        document.getElementById('user_birthday').value = toDateAndTimeBirthday(current_user_info.birthday);
    }
    else {
        document.getElementById('user_birthday').value = '';
    }
    document.getElementById('user_address').value = current_user_info.address;
    document.getElementById('user_balance').value = current_user_info.balance;
    hideAllDiv();
    vteam_http.show("user_detail");
}
function initUserInfo() {
    vteam_http.makeHttpRequest("/user/get_user_by_email", {},
            "POST",
            function(result) {
                if (result.status == "success")
                {
                    current_user_info = result;
                    showLogedInMenu();
                    //displayUserDetail();
                } else if (result.status == "unlogin") {
                    displayunLogin()
                } else {
                    alert("error");
                }
            });
}
function inputCode() {
    var input_code = document.getElementById("payment_code").value;
    vteam_http.makeHttpRequest("/user/input_payment_code", {
        code: input_code
    },
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    vteam_http.setHTML("updateResult1", "<font style='color: green;font-size: large;'><strong>Cập nhật thành công! !</strong></font>");
                    vteam_http.show("updateResult1");                  
                    $("#updateResult1").hide(5000);
                    clearText();
                    initUserInfo();
                } else if (result.status == "unlogin") {
                    vteam_http.setHTML("result", "You need login again to insert");
                    vteam_http.show("result");
                } else {
                    vteam_http.setHTML("result", "Error when insert");
                    vteam_http.show("result");
                }
            });
}
function hidePaymentList(){
    vteam_http.hide("tablePayment");
    vteam_http.hide("paymentResult");
}
function create() {
    var email = document.getElementById("new_username").value;
    var password = document.getElementById("new_password").value;
    var repassword = document.getElementById("new_repassword").value;
    var fullname = document.getElementById("new_fullname").value;
    var phone = document.getElementById("new_phone").value;
    if (valid_register(email, password, repassword, fullname,phone)) {
        vteam_http.makeHttpRequest("/user/create", {
            email: email,
            password: password,
            fullname: fullname,
            phone:phone
        },
        "POST",
                function(result) {
                    if (result.status == "success")
                    {
                        initUserInfo();
                        changeContext();
                    } else {
                        vteam_http.setHTML("result", "<h4 style='color:red;'>Error when insert</h4>");
                        vteam_http.show("result");
                    }
                });
    }
}
function showLogin() {
    currentPosition = -1;
    hideAllDiv();
    changeLogin();
    vteam_http.show("user_login");
}
function loadAndDisplayPayment() {
    var id = document.getElementById("user_id").value;
    vteam_http.makeHttpRequest("/user/getPaymentList",
            {id: id},
    'POST',
            function(result) {
                if (result.status == 'success') {
                    displayPayment(result.paymentList);
                }
            });
}

function displayPayment(paymentList) {
    var html = '';
    for (var i = 0; i < paymentList.length; i++) {
        html += '<tr>'
        html += '<td>'
        html += paymentList[i].card_code
        html += '</td>'
        html += '<td>'
        html += toDateAndTime2(paymentList[i].payment_date)
        html += '</td>'
        html += '<td>'
        html += paymentList[i].ammount
        html += '</td>'
        html += '</tr>'
    }
    vteam_http.setHTML("paymentResult", html);
    vteam_http.show("tablePayment");
    vteam_http.show("paymentResult");
}
function exportPDF() {
    window.location.href = "/order/export_product_list_to_pdf";
}
function validUsername() {
    var email = document.getElementById("user_username").value;

    if (!email) {
        var div = $("#user_username").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        return false;
    } else {
        var div = $("#user_username").parents("div.control-group");
        div.removeClass("error");
        //div.addClass("success");


    }
}
function validPassword() {
    var password = document.getElementById("user_password").value;
    if (!password) {
        var div = $("#user_password").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        return false;
    } else {
        var div = $("#user_password").parents("div.control-group");
        div.removeClass("error");
        //div.addClass("success");

    }
}
function valid_Login(email, password) {
    if (email == "" && password == "") {
        vteam_http.setHTML("error", "Chưa điền tên đăng nhập và mật khẩu.");
        vteam_http.show("error");
        return false;
    }
    if (email == "") {
        vteam_http.setHTML("error", "Chưa điền tên đăng nhập.");
        vteam_http.show("error");
        return false;
    }
    if (password == "") {
        vteam_http.setHTML("error", "Chưa điền mật khẩu.");
        vteam_http.show("error");
        return false;
    }
    return true;
}
function valid_register_Username() {
    var username = document.getElementById("new_username").value;
    if (!username) {
        var div = $("#new_username").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        var html = '<font style="color: red">Địa chỉ email của bạn theo dạng aa@aa.aaa</font>';
        vteam_http.setHTML("email_validation", html);
        vteam_http.show("email_validation");
        return false;
    } else {
        var div = $("#new_username").parents("div.control-group");
        div.removeClass("error");
        vteam_http.hide("email_validation");
        vteam_http.makeHttpRequest("/user/check_email", {email: username},
        "POST",
                function(result) {
                    if (result == false)
                    {

                        var html = "<font style='color:red'>Địa chỉ email của bạn đã tồn tại.</font>";
                        vteam_http.setHTML("email_validation", html);
                        vteam_http.show("email_validation");
                        document.getElementById("new_username").focus();
                        return false;
                    } else if (result == true) {
                        vteam_http.hide("email_validation");
                        return true;
                    }
                });
        //div.addClass("success");

    }
}
function valid_register_Password() {
    var password = document.getElementById("new_password").value;
    if (!password) {
        var div = $("#new_password").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        var html = ' <font style="color: red">Vui lòng điền mật khẩu</font>';
        vteam_http.setHTML("pass_validation", html);
        vteam_http.show("pass_validation");
        return false;
    } else {
        var div = $("#new_password").parents("div.control-group");
        div.removeClass("error");
        vteam_http.hide("pass_validation");
        //div.addClass("success");
        return true;
    }
}
function valid_register_rePassword() {
    var repassword = document.getElementById("new_repassword").value;
    if (!repassword) {
        var div = $("#new_repassword").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
         var html = ' <font style="color: red">Hãy nhập đúng mật khẩu đăng kí</font>';
        vteam_http.setHTML("pass_validation", html);
        vteam_http.show("pass_validation");
        return false;
    } else {
        var div = $("#new_repassword").parents("div.control-group");
        div.removeClass("error");
        vteam_http.hide("pass_validation");
        //div.addClass("success");
        return true;
    }
}
function valid_register_Fullname() {
    var fullname = document.getElementById("new_fullname").value;
    if (!fullname) {
        var div = $("#new_fullname").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        vteam_http.show("fullname_validation");
        return false;
    } else {
        var div = $("#new_fullname").parents("div.control-group");
        div.removeClass("error");
        vteam_http.hide("fullname_validation");
        //div.addClass("success");
        return true;
    }
}
function valid_register_Phone() {
    var phone = document.getElementById("new_phone").value;
    if (!phone) {
        var div = $("#new_phone").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        var html = "<font style='color: red'>Định dạng số điện thoạilà +84xxxxxxxxx.</font>";
                        vteam_http.setHTML("phone_validation", html);
                        vteam_http.show("phone_validation");
        return false;
    } else {
        var div = $("#new_phone").parents("div.control-group");
        div.removeClass("error");
        vteam_http.hide("phone_validation");
        vteam_http.makeHttpRequest("/user/check_phone", {phone: phone},
        "POST",
                function(result) {
                    if (result == false)
                    {

                        var html = "<font style='color: red'>Định dạng số điện thoạilà +84xxxxxxxxx.</font>";
                        vteam_http.setHTML("phone_validation", html);
                        vteam_http.show("phone_validation");
                        return false;
                    } else if (result == true) {
                        vteam_http.hide("phone_validation");
                        return true;
                    }
                });
        //div.addClass("success");

    }
}
function valid_register(email, pass, re_pass, fullname,phone) {
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!filter.test(email)) {
        var html = '<font style="color: red">Địa chỉ email của bạn theo dạng aa@aa.aaa</font>';
        vteam_http.setHTML("email_validation", html);
        vteam_http.show("email_validation");
        return false;
    } else {
        vteam_http.hide("email_validation");
    }
    if(pass=="" && re_pass==""){
       var html = '<font style="color: red">Vui lòng điền mật khẩu</font>';
        vteam_http.setHTML("pass_validation", html);
        vteam_http.show("pass_validation");
        return false;
    }else{
       if (pass != re_pass) {
        var html = '<font style="color: red">Hãy nhập đúng mật khẩu đăng kí</font>';
        vteam_http.setHTML("pass_validation", html);
        vteam_http.show("pass_validation");
        return false;
    } else {
        vteam_http.hide("pass_validation");
    } 
    }
    if (fullname == "" || fullname == null) {
        vteam_http.show("fullname_validation");
        return false;
    } else {
        vteam_http.hide("fullname_validation")
    }
    if (phone == "" || phone == null) {
        var html = "<font style='color: red'>Định dạng số điện thoạilà +84xxxxxxxxx.</font>";
                        vteam_http.setHTML("phone_validation", html);
                        vteam_http.show("phone_validation");
        return false;
    } else {
        vteam_http.hide("phone_validation");
        vteam_http.makeHttpRequest("/user/check_phone", {phone: phone},
        "POST",
                function(result) {
                    if (result == false)
                    {

                        var html = "<font style='color: red'>Định dạng số điện thoạilà +84xxxxxxxxx.</font>";
                        vteam_http.setHTML("phone_validation", html);
                        vteam_http.show("phone_validation");
                        return false;
                    } else if (result == true) {
                        vteam_http.hide("phone_validation");
                        return true;
                    }
                });
    }
    if (!ValidCaptcha()) {
        vteam_http.show("capcha_validation");
        return false;
    } else {
        vteam_http.hide("capcha_validation")
    }
    
    return true;
}
function DrawCaptcha()
{
    var a = Math.ceil(Math.random() * 10) + '';
    var b = Math.ceil(Math.random() * 10) + '';
    var c = Math.ceil(Math.random() * 10) + '';
    var d = Math.ceil(Math.random() * 10) + '';
    var e = Math.ceil(Math.random() * 10) + '';
    var f = Math.ceil(Math.random() * 10) + '';
    var g = Math.ceil(Math.random() * 10) + '';
    var code = a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' ' + f + ' ' + g;
    document.getElementById('txtCaptcha').value = code;
}
function ValidCaptcha() {
    var str1 = removeSpaces(document.getElementById('txtCaptcha').value);
    var str2 = removeSpaces(document.getElementById('txtInput').value);
    if (str1 == str2)
        return true;
    return false;

}

// Remove the spaces from the entered and generated code
function removeSpaces(string)
{
    return string.split(' ').join('');
}
function clearText(){
     var elements = document.getElementsByTagName("input");
    for (var ii = 0; ii < elements.length; ii++) {
        if (elements[ii].type == "text") {
            elements[ii].value = "";
        }else if(elements[ii].type == "password"){
            elements[ii].value = "";
        }
    }
}
function getOrderList(){
 vteam_http.makeHttpRequest("/user/getOrderList", {},
        "POST",
                function(result) {
                    if (result.status == "success")
                    {
                            vteam_http.setHTML("orderResult",result.result);
                            vteam_http.show("orderResult");
                    }
                }); 
}