/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
                    if (result.admin == "admin") {
                        window.location.href = "/admin";
                    } else {
                        vteam_http.hide("login_menu");
                        vteam_http.hide("user_login");
                        vteam_http.setHTML("loginResult",'<a class="btn btn-success" id="email" style="margin-left:10px;margin-top:2px;" href="javascript:void(0)" onclick="showUserInfo()"><i class="icon-user icon-white" ></i>' + result.email + ' </a>'
                                + '<li id="fat-menu1" class="btn btn-success" style="width:70px;height:20px;margin-left:10px;" ><a href="javascript:void(0)" id="drop4" role="button" class="dropdown-toggle" data-toggle="dropdown" style="color:white;">' + result.balance+'Nil' + '</a></li>'
                                + '<a class="btn btn-success" id="logout" style="margin-left:10px;margin-top:2px;" href="javascript:void(0)" onclick="logout()"><i class="icon-off icon-white" ></i></a>');
                        vteam_http.show("loginResult");
                        vteam_http.show("product_list");
                    }
                } else {
                    vteam_http.setHTML("error","Wrong username or password");
                    vteam_http.show("error");
                }
            });

}
function logout() {
    var email = document.getElementById("user_username").value;
    vteam_http.makeHttpRequest("/user/logout",
            {email: email},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    displayunLogin()
//                    vteam_http.hide("user_detail");
//                    vteam_http.show("columnContainer");
                    vteam_http.hide("loginResult");
                    vteam_http.hide("user_detail");
                    vteam_http.show("product_list");
                }
                else {
                    alert("Error");
                }
            });
}
function changeSigin() {
    vteam_http.hide("login");
    vteam_http.show("sigin");
}
function changeLogin() {
    vteam_http.hide("sigin");
    vteam_http.show("login");
}
function displayunLogin() {
    vteam_http.hide("loginResult");
    vteam_http.hide("user_detail");
    //vteam_http.show("login");
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
                    vteam_http.setHTML("updateResult1","<font style='color: green;font-size: large;'><strong>Well done! !</strong> You successfully updated.</font>");
                    loadUserInfo();
                    
                } else if (result.status == "unlogin") {
                    vteam_http.setHTML("updateResult1","<font style='color: black;font-size: large;'><strong>Sesstion Times out! !</strong> Please log in again.</font>");
                } else if(result.status == "error") {
                    vteam_http.setHTML("updateResult1","<font style='color: red;font-size: large;'><strong>Oh snap!!</strong> Change a few things up and try submitting again.</font>");
                }

                vteam_http.show("updateResult1");
                setTimeout(vteam_http.hide("updateResult1"),20000);
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
                    vteam_http.setHTML("updateResult1","<font style='color: green;font-size: large;'><strong>Well done! !</strong> You successfully updated.</font>");


                } else if (result.status == "unlogin") {
                    vteam_http.setHTML("updateResult1","<font style='color: black;font-size: large;'><strong>Sesstion Times out! !</strong> Please log in again.</font>");


                } else if (result.status == "error") {
                    vteam_http.setHTML("updateResult1","<font style='color: red;font-size: large;'><strong>Oh snap!!</strong> Change a few things up and try submitting again.</font>");
                }
                vteam_http.show("updateResult1");
                $("#updateResult1").hide('fast');
            });

}
function showUserInfo() {
    vteam_http.makeHttpRequest("/user/get_user_by_email", {},
            "POST",
            function(result) {
                if (result.status == "success")
                {
                    currentPosition += 1;
                    divArray[currentPosition] = "user_detail"
                    vteam_http.hide("product_list");
//                    vteam_http.hide("product_list");
                    vteam_http.show("user_detail");
//                    $("#columnContainer").hide();
//                    $("#user_detail").show();
                    document.getElementById('user_email').value = result.email;
                    document.getElementById('user_id').value = result.id;
                    document.getElementById('user_fullname').value = result.fullname;
                    document.getElementById('user_phone').value = result.phone;
                    document.getElementById('user_birthday').value = toDateAndTime2(result.birthday);
                    document.getElementById('user_address').value = result.address;
                    document.getElementById('user_balance').value = result.balance;
                } else {
                    alert("error");
                }
            });
}
function loadUserInfo() {
    vteam_http.makeHttpRequest("/user/get_user_by_email", {},
            "POST",
            function(result) {
                if (result.status == "success")
                {
                    //vteam_http.hide("product_list");
                    vteam_http.hide("login_menu");
                    //vteam_http.show("user_detail");
                    
                    vteam_http.setHTML('loginResult','<a class="btn btn-success" id="email" style="margin-left:10px;margin-top:2px;" href="javascript:void(0)" onclick="showUserInfo()"><i class="icon-user icon-white" ></i>' + result.email + ' </a>'
                            + '<li id="fat-menu1" class="btn btn-success" style="width:70px;height:20px;margin-left:10px;" ><a href="javascript:void(0)" id="drop4" role="button" class="dropdown-toggle" data-toggle="dropdown" style="color:white;">' + result.balance+'Nil' + '</a></li>'
                            + '<a class="btn btn-success" id="logout" style="margin-left:10px;margin-top:2px;" href="javascript:void(0)" onclick="logout()"><i class="icon-off icon-white" ></i></a>');
                    vteam_http.show("loginResult");
                    document.getElementById('user_email').value = result.email;
                    document.getElementById('user_id').value = result.id;
                    document.getElementById('user_fullname').value = result.fullname;
                    document.getElementById('user_phone').value = result.phone;
                    document.getElementById('user_address').value = result.address;
                    document.getElementById('user_balance').value = result.balance;
                } else if(result.status == "unlogin") {
                    displayunLogin()
                } else {
                    alert("error");
                }
            });
}
function inputCode() {
    var input_code = document.getElementById("user_paymentCode").value;
    vteam_http.makeHttpRequest("/user/input_payment_code", {
        code: input_code
    },
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    vteam_http.setHTML("result","Insert succesfully!!");
                    vteam_http.show("result");
                    location.reload();
                } else if (result.status == "unlogin") {
                    vteam_http.setHTML("result","You need login again to insert");
                    vteam_http.show("result");
                } else {
                    vteam_http.setHTML("result","Error when insert");
                    vteam_http.show("result");
                }
            });
}
function create(){
    var email = document.getElementById("new_username").value;
    var password = document.getElementById("new_password").value;
    var fullname = document.getElementById("new_fullname").value;
    vteam_http.makeHttpRequest("/user/create", {
        email: email,
        password:password,
        fullname:fullname
    },
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    vteam_http.setHTML("result","<h4 style='color:green;'>Insert succesfully!!</h4>");
                    vteam_http.show("result");
                } else {
                    vteam_http.setHTML("result","<h4 style='color:red;'>Error when insert</h4>");
                    vteam_http.show("result");
                }
            });
}
function showLogin(){
 vteam_http.show("user_login");
 vteam_http.hide("product_list");
}