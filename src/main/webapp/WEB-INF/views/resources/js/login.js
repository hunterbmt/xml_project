/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
                           $('#fat-menu').hide(); 
                            $('#loginResult').html("Welcome"+ '<a class="btn btn-primary" style="margin-left:10px;margin-top:2px;" href="/user/detail"><i class="icon-user icon-white" ></i>'  + result.email+' </a>').show();
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
