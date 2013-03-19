/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function login() {
    var email = document.getElementById("user_username").value;
    var password = document.getElementById("user_password").value;


    vteam_http.init();
    vteam_http.makeHttpRequest("/user/login",
            {email: email,
                password: password},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    $("#titleLogin").hide();
                    $('#fat-menu').hide();
                    $('#quickLogin').hide();
                    $('#loginResult').html('<a class="btn btn-primary" id="email" style="margin-left:10px;margin-top:2px;" href="#" onclick="showUserInfo()"><i class="icon-user icon-white" ></i>' + result.email + ' </a>'
                                            +'<a class="btn btn-primary" id="logout" style="margin-left:10px;margin-top:2px;" href="#" onclick="logout()"><i class="icon-off icon-white" ></i></a>').show();

                }
                else {
                    alert("Error");
                }
            });

}
function logout(){
    var email = document.getElementById("user_username").value;
    vteam_http.init();
    vteam_http.makeHttpRequest("/user/logout",
            {email: email},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    displayunLogin()
                    $("#user_detail").hide();
                    $("#product").show();
                }
                else {
                    alert("Error");
                }
            });
}
function changeSigin() {
    $("#login").hide();
    $("#sigin").show();
}
function changeLogin() {
    $("#login").show();
    $("#sigin").hide();
}
function displayunLogin(){
    $('#loginResult').hide();
    $('#fat-menu').show();
}
function updateInfo() {
    var address = document.getElementById("user_address").value;
    var phone = document.getElementById("user_phone").value;
    var birthday = document.getElementById("user_birthday").value;
    var format_date = "MM/dd/yyyy";
    vteam_http.init();
    vteam_http.makeHttpRequest("/user/update",
            {   address: address,
                phone: phone,
                birthday: birthday,
                formatDate: format_date},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    alert(result.status);
                } else {
                    alert("error");
                }
            });

}
function updatePassword() {
    var ollPass = document.getElementById("curr_password").value;
    var newPass = document.getElementById("newpassword").value;
    vteam_http.init();
    vteam_http.makeHttpRequest("/user/changePassword",
            {   currentPass: ollPass,
                newPass: newPass},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    alert(result.status);
                } else {
                    alert("error");
                }
            });

}
function showUserInfo() {
     vteam_http.init();
     vteam_http.makeHttpRequest("/user/get_user_by_email",{},
      "POST",
            function(result) {
                if (result.status == "success")
                {
                    $("#product").hide();
                    $("#user_detail").show();
                    document.getElementById('user_email').value=result.user_email;
                    document.getElementById('user_fullname').value=result.user_fullname;
                } else {
                    alert("error");
                }
            });
}
function loadUserInfo() {
    vteam_http.init();
    vteam_http.makeHttpRequest("/user/get_user_by_email",{},
                   "POST",
            function(result) {
                if (result.status == "success")
                {
                    $('#fat-menu').hide();
                    $('#quickLogin').hide();
                    $('#loginResult').html('<a class="btn btn-primary" id="email" style="margin-left:10px;margin-top:2px;" href="#" onclick="showUserInfo()"><i class="icon-user icon-white" ></i>' + result.user_email + ' </a>'
                                            +'<a class="btn btn-primary" id="logout" style="margin-left:10px;margin-top:2px;" href="#" onclick="logout()"><i class="icon-off icon-white" ></i></a>').show();
                }else if(result.status=="unlogin") {
                    displayunLogin()
                }else {
                    alert("error");
                }
            });
}