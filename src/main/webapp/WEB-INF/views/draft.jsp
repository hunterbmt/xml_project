<%-- 
    Document   : draft
    Created on : Mar 19, 2013, 1:39:00 PM
    Author     : Crick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <li class= "span4">
        <div class= "p_detail" style="border:none; margin-top:0px">
            
            <img src="' + product.image + '"/>
            <div id= "right">

                <div class="title">
                    product.name
                </div>
                <div class="price"><div class="price_view">
                        <div class="v6Price mTop10" align="center">
                            <span>$60,000</span></div>'
                        <div class="v7inlinetype" align="center">20 nils per bid</div>
                        <div class="v6BuyNow">'
                            <a class=" fixPng" href="javascript:void(0)" onclick="bidNow()"></a></div>
                    </div></div>

                <div class="v6BorderBot pTop5"><div class="v6Timer">
                        <div class="v6Gray fl">Chỉ còn</div>
                        <div class="v6DisplayTime" id=""><span>3 ngày 20:56’:45”</span></div>
                    </div></div>

            </div>
            
            <div class="c"></div>
            <div class="v6BorderBot" style="border-bottom: none">
                product.description
            </div>
        </div>
    </li>
</body>
</html>
