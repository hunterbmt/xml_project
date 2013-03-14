<%-- 
    Document   : userInfo
    Created on : Mar 13, 2013, 9:08:56 AM
    Author     : TH11032013
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            function updateInfo(){
                var email=document.getElementById("email").value;
                var address=document.getElementById("address").value;
                var phone= document.getElementById("phone").value;
                var birthday=document.getElementById("birthday").value;
                var format_date = "MM/dd/yyyy";
                vteam_http.init();
                vteam_http.makeHttpRequest("/user/update",
                                    {email:email,
                                    address:address,
                                    phone:phone,
                                    birthday:birthday,
                                    formatDate:format_date},
                                    "POST",
                      function (result){
                           if(result.status =="success")
                            {
                            alert("Update successfully.");
                            }else{
                                alert("error");
                            }
                });           
               
            }
        function updatePassword(){
            var email="<c:out value = "${user_email}"/>";;
            var currentPass=document.getElementById("curr_password").value;
            var newPass=document.getElementById("newpassword").value;
             vteam_http.init();
             vteam_http.makeHttpRequest("/user/changePassword",
                                    {email:email,
                                    
                                    currentPass:currentPass,
                                    newPass:newPass},
                                    "POST",
                      function (result){
                           if(result.status =="success")
                            {
                            alert("Update successfully.");
                            }else{
                                alert("error");
                            }
                });           
           
        }
        </script>
    </head>
    <body>
        <div class="well">
            <ul class="nav nav-tabs">
                <li class="active"><a href="#home" data-toggle="tab">Profile</a></li>
                <li><a href="#profile" data-toggle="tab">Change Password</a></li>
                <li><a href="#profile" data-toggle="tab">Order History</a></li>
            </ul>
            <div id="myTabContent" class="tab-content">
                <div class="tab-pane active in" id="home">
                    <form id="tab">
                        <label>Email</label>
                        <input type="text" id="email" class="input-xlarge" value="${user_email}">
                        
                        <label>First Name</label>
                        <input type="text"  class="input-xlarge" value="${user_fullname}">
                        <label>Phone</label>
                        <input type="text" id="phone"  class="input-xlarge">
                        
                        <label>Address</label>
                        <input type="text" id="address"  class="input-xlarge">
                        <label>Birthday</label>
                        <input type="text" id="birthday" class="input-xlarge">
                        <div>
                            <button class="btn btn-primary" onclick="updateInfo()">Update</button>
                        </div>
                    </form>
                </div>
              <div class="tab-pane fade" id="profile">
                    <form id="tab2">
                        <label>Password</label>
                        <input type="password" id="newpassword" class="input-xlarge">       
                        <label>Old Password</label>
                        <input type="Password" id="curr_password" class="input-xlarge">
                        <div>
                            <button class="btn btn-primary" onclick="updatePassword()">Update</button>
                        </div>
                        
                    </form>
                </div>
                <div class="tab-pane fade" id="profile">
                    <form id="tab3">
                        <label>Order History</label>
                        
                    </form>
                </div>
                
            </div>
        </div>
    </body>
</html>