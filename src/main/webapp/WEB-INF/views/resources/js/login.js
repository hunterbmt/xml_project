/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function quickLogin(){
            var email=document.getElementById("miniusername").value;
            var password=document.getElementById("minipassword").value;
            
            
            vteam_http.init();
            vteam_http.makeHttpRequest("/user/login",
                                    {email:email,
                                     password:password},
                                    "POST",
                      function (result){
                        if(result.status =="success")
                            {
                           $("#titleLogin").hide();
                           $('#quickLogin').hide();
                           $('#fat-menu').hide();
                            $('#loginResult').html('<a class="btn btn-primary" style="margin-left:10px;margin-top:2px;" href="/user/detail" onclick="showUserInfo()"><i class="icon-user icon-white" ></i>'  + result.email+' </a>').show();
                            
                            }
                         else{
                             alert("Error");
                         }   
                    });
           
            }
            function login(){
            var email=document.getElementById("user_username").value;
            var password=document.getElementById("user_password").value;
            
            
            vteam_http.init();
            vteam_http.makeHttpRequest("/user/login",
                                    {email:email,
                                     password:password},
                                    "POST",
                      function (result){
                        if(result.status =="success")
                            {
                                $("#titleLogin").hide();
                           $('#fat-menu').hide(); 
                           $('#quickLogin').hide();
                            $('#loginResult').html('<a class="btn btn-primary" style="margin-left:10px;margin-top:2px;" href="/user/detail" onclick="showUserInfo()"><i class="icon-user icon-white" ></i>'  + result.email+' </a>').show();
                            
                            }
                         else{
                             alert("Error");
                         }   
                    });
           
            }
 function changeSigin(){
     $("#login").hide();
     $("#sigin").show();
 }
  function changeLogin(){
     $("#login").show();
     $("#sigin").hide();
 }
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
                            $("#product_detail").hide();
                            $("#user_detail").show();
                            }else{
                                alert("error");
                            }
                });           
               
            }
   function showUserInfo(){
       $("#product_detail").hide();
       $("#user_detail").show();
   }