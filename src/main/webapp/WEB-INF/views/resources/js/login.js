/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var current_user_info;
function login() {
    var email = document.getElementById("user_username").value;
    var password = document.getElementById("user_password").value;

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
                    vteam_http.setHTML("error", "Wrong username or password");
                    vteam_http.show("error");
                }
            });

}
function showLogedInMenu() {
    vteam_http.hide("login_menu");
    vteam_http.setHTML("loginResult", '<a class="btn btn-success" id="email" style="margin-left:10px;margin-top:2px;" href="javascript:void(0)" onclick="displayUserDetail()"><i class="icon-user icon-white" ></i>' + current_user_info.email + ' </a>'
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
    vteam_http.hide("login");
    vteam_http.show("signin");
}
function changeLogin() {
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
                    loadUserInfo();

                } else {
                    if (result.status == "unlogin") {
                        vteam_http.setHTML("updateResult1", "<font style='color: black;font-size: large;'><strong>Sesstion Times out! !</strong> Please log in again.</font>");
                    } else if (result.status == "error") {
                        vteam_http.setHTML("updateResult1", "<font style='color: red;font-size: large;'><strong>Oh snap!!</strong> Change a few things up and try submitting again.</font>");
                    }
                    vteam_http.show("updateResult1");
                    $("#updateResult1").hide(20000);
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
                    vteam_http.setHTML("updateResult1", "<font style='color: green;font-size: large;'><strong>Well done! !</strong> You successfully updated.</font>");


                } else if (result.status == "unlogin") {
                    vteam_http.setHTML("updateResult1", "<font style='color: black;font-size: large;'><strong>Sesstion Times out! !</strong> Please log in again.</font>");


                } else if (result.status == "error") {
                    vteam_http.setHTML("updateResult1", "<font style='color: red;font-size: large;'><strong>Oh snap!!</strong> Change a few things up and try submitting again.</font>");
                }
                vteam_http.show("updateResult1");
                $("#updateResult1").hide(20000);
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
        document.getElementById('user_birthday').value = toDateAndTime2(current_user_info.birthday);
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
function create() {
    var email = document.getElementById("new_username").value;
    var password = document.getElementById("new_password").value;
    var fullname = document.getElementById("new_fullname").value;
    vteam_http.makeHttpRequest("/user/create", {
        email: email,
        password: password,
        fullname: fullname
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
function showLogin() {
    hideAllDiv();
    vteam_http.show("user_login");
}
function loadAndDisplayPayment() {
    var id=document.getElementById("user_id").value;
    vteam_http.makeHttpRequest("/user/getPaymentList",
    {id:id},
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
    vteam_http.setHTML("paymentResult",html);
    vteam_http.show("tablePayment");
    vteam_http.show("paymentResult");
}
function exportPDF(){
    window.location.href="/order/export_product_list_to_pdf";
}
function validLogIn(){
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
function validPassword(){
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