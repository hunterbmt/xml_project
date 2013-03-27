/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function login() {
    var email = document.getElementById("user_username").value;
    var password = document.getElementById("user_password").value;
    var html = '';
    html += '<ul class="dropdown-menu" role="menu" aria-labelledby="drop3" style="width: 250px">'
    html += '<li ><div role="menuitem" tabindex="-1" style="padding: 3px 20px;display: block">'

    html += '<div id="payment">'
    html += '<form >'
    html += '<fieldset>'
    html += '<label class="label-main">Insert your code here:</label>'
    html += '<input name="miniusername"  id="user_paymentCode" type="text">'
    html += '<button name="send" type="button" class="btn btn-primary btn-small" onclick="inputCode()">Insert</button>'
    html += '</fieldset>'
    html += '</form>'
    html += '<div id="result" style="color: red;display: none"></div>'
    html += '</div>'
    html += '</div>'
    html += '</li>'
    html += '</ul>'


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
                        $("#titleLogin").hide();
                        $('#fat-menu').hide();
                        $('#quickLogin').hide();
                        $('#loginResult').html('<a class="btn btn-primary" id="email" style="margin-left:10px;margin-top:2px;" href="#" onclick="showUserInfo()"><i class="icon-user icon-white" ></i>' + result.email + ' </a>'
                                + '<li id="fat-menu1" class="btn btn-primary dropdown" style="width:60px;height:20px;margin-left:10px;" ><i class="icon-money" style="margin-left:-15px;"></i><a href="#" id="drop4" role="button" class="dropdown-toggle" data-toggle="dropdown" style="color:white;">' + result.balance + '</a>' + html + '</li>'
                                + '<a class="btn btn-primary" id="logout" style="margin-left:10px;margin-top:2px;" href="#" onclick="logout()"><i class="icon-off icon-white" ></i></a>').show();

//                        $("#drop4").html(result.balance);
//                        $("#balanceResult").show();
                    }
                } else {
                    $("#error").html('Wrong username or password');
                    $("#error").show('fast');
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
function displayunLogin() {
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
            {address: address,
                phone: phone,
                birthday: birthday,
                formatDate: format_date},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    $("#updateResult1").html("<font style='color: green;font-size: large;'><strong>Well done! !</strong> You successfully updated.</font>");
                } else if (result.status == "unlogin") {
                    $("#updateResult1").html("<font style='color: black;font-size: large;'><strong>Sesstion Times out! !</strong> Please log in again.</font>");
                } else if (result.status == "error") {
                    $("#updateResult1").html("<font style='color: red;font-size: large;'><strong>Oh snap!!</strong> Change a few things up and try submitting again.</font>");
                }
                $("#updateResult1").show('slow');
                $("#updateResult1").hide(1500);
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
                    $("#updateResult1").html("<font style='color: green;font-size: large;'><strong>Well done! !</strong> You successfully updated.</font>");


                } else if (result.status == "unlogin") {
                    $("#updateResult1").html("<font style='color: black;font-size: large;'><strong>Sesstion Times out! !</strong> Please log in again.</font>");


                } else if (result.status == "error") {
                    $("#updateResult1").html("<font style='color: red;font-size: large;'><strong>Oh snap!!</strong> Change a few things up and try submitting again.</font>");


                }
                $("#updateResult1").show('slow');
                $("#updateResult1").hide(1500);
                //showNotifications();
            });

}
function showUserInfo() {
    vteam_http.makeHttpRequest("/user/get_user_by_email", {},
            "POST",
            function(result) {
                if (result.status == "success")
                {
                    $("#product").hide();
                    $("#user_detail").show();
                    document.getElementById('user_email').value = result.email;
                    document.getElementById('user_id').value = result.id;
                    document.getElementById('user_fullname').value = result.fullname;
                } else {
                    alert("error");
                }
            });
}
function loadUserInfo() {
    var html = '';
    html += '<ul class="dropdown-menu" role="menu" aria-labelledby="drop3" style="width: 250px">'
    html += '<li ><div role="menuitem" tabindex="-1" style="padding: 3px 20px;display: block">'

    html += '<div id="payment">'
    html += '<form >'
    html += '<fieldset>'
    html += '<label class="label-main">Insert your code here:</label>'
    html += '<input name="miniusername"  id="user_paymentCode" type="text">'
    html += '<button name="send" type="button"  class="btn btn-primary btn-small" onclick="inputCode()">Insert</button>'
    html += '</fieldset>'
    html += '</form>'
    html += '</div>'
    html += '</div>'
    html += '</li>'
    html += '</ul>'
    vteam_http.makeHttpRequest("/user/get_user_by_email", {},
            "POST",
            function(result) {
                if (result.status == "success")
                {
                    $('#fat-menu').hide();
                    $('#quickLogin').hide();
                    $('#loginResult').html('<a class="btn btn-primary" id="email" style="margin-left:10px;margin-top:2px;" href="#" onclick="showUserInfo()"><i class="icon-user icon-white" ></i>' + result.email + ' </a>'
                            + '<li id="fat-menu1" class="btn btn-primary dropdown" style="width:60px;height:20px;margin-left:10px;" ><i class="icon-money" style="margin-left:-15px;"></i><a href="#" id="drop4" role="button" class="dropdown-toggle" data-toggle="dropdown" style="color:white;">' + result.balance + '</a>' + html + '</li>'
                            + '<a class="btn btn-primary" id="logout" style="margin-left:10px;margin-top:2px;" href="#" onclick="logout()"><i class="icon-off icon-white" ></i></a>').show();
                } else if (result.status == "unlogin") {
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
                    $("#result").html("Insert succesfully!!");
                    $("#result").show();
                    location.reload();
                } else if (result.status == "unlogin") {
                    $("#result").html("You need login again to insert");
                    $("#result").show();
                } else {
                    $("#result").html("Error when insert");
                    $("#result").show();
                }
            });
}
function create(){
    var email = document.getElementById("new_username").value;
    var password = document.getElementById("new_password").value;
    var fullname = document.getElementById("user_fullname").value;
    vteam_http.makeHttpRequest("/user/create", {
        email: email,
        password:password,
        fullname:fullname
    },
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    $("#result").html("<h4 style='color:green;'>Insert succesfully!!</h4>");
                    $("#result").show();
                } else {
                    $("#result").html("<h4 style='color:red;'>Error when insert</h4>");
                    $("#result").show();
                }
            });
}