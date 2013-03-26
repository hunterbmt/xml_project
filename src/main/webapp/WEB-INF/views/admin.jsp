
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="/resources/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
        <link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="/resources/css/font-awesome.css" rel="stylesheet">
        <link href="/resources/css/jquery-ui-helper.css" rel="stylesheet">
        <link href="/resources/css/admin.css" rel="stylesheet">
        <script src="/resources/js/lib/jquery.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="/resources/js/lib/bootstrap.min.js"></script>
        <script src="/resources/js/admin.js"></script>
    </head>
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <ul>
                        <li class="active">
                            <a href="./">
                                <i class="icon-dashboard"></i>
                                <span>Dash Board</span>
                            </a>	    				
                        </li>
                        <li>
                            <a href="/admin/product">
                                <i class="icon-book"></i>
                                <span>Product</span>
                            </a>	    				
                        </li>
                        <li>
                            <a href="/users">
                                <i class="icon-group"></i>
                                <span>Users</span>
                            </a>	    				
                        </li>
                        <li>
                            <a href="/admin/bid">
                                <i class="icon-legal"></i>
                                <span>Bid</span>
                            </a>	    				
                        </li>
                        <li>
                            <a href="./payment">
                                <i class="icon-money"></i>
                                <span>Payment</span>
                            </a>	    				
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="main" >
            <div class="container">
                <div class="row">
                    <div class="span6">
                        <div class="widget">
                            <div class="widget-header">
                                <i class="icon-bar-chart"></i>
                                <h3>Static</h3>
                            </div>
                            <div class="widget-content">
                                <div class="stats">
                                    <div class="stat">
                                        <span class="stat-value">12,386</span>									
                                        Products
                                    </div> <!-- /stat -->

                                    <div class="stat">
                                        <span class="stat-value">9,249</span>									
                                        Users
                                    </div> <!-- /stat -->

                                    <div class="stat">
                                        <span class="stat-value">70%</span>									
                                        Bids
                                    </div> 
                                </div>
                            </div>
                        </div>

                        <div class="widget">

                            <div class="widget-header">
                                <i class="icon-cogs"></i>
                                <h3>Function Shortcuts</h3>
                            </div> <!-- /widget-header -->

                            <div class="widget-content">

                                <div class="shortcuts">

                                    <a href="javascript:;" class="shortcut">
                                        <i class="shortcut-icon icon-cloud-download"></i>
                                        <span class="shortcut-label">Download report as PDF</span>	
                                    </a>

                                    <a href="javascript:;" class="shortcut" onclick="generateNinOnClick()">
                                        <i class="shortcut-icon  icon-magic"></i>
                                        <span class="shortcut-label">Generate Nin code</span>	
                                    </a>

                                    <a href="javascript:;" class="shortcut">
                                        <i class="shortcut-icon icon-envelope"></i>
                                        <span class="shortcut-label">Send email to users</span>
                                    </a>				
                                </div> <!-- /shortcuts -->	

                            </div> <!-- /widget-content -->
                            <div id="nin_code_modal" class="hide" title="Generate Nin code">
                                <p id ="nin_result"class="validateTips hide">All form fields are required.</p>
                                <form>
                                    <fieldset>
                                        <label for="Amount">Amount</label>
                                        <input type="number" name="Amount" id="nin_amount" class="number ui-widget-content ui-corner-all"/>
                                        <label for="Quantity">Quantity</label>
                                        <input type="number" name="Quantity" id="nin_quantity" class="number ui-widget-content ui-corner-all"/>
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="span6">
                        <div class="widget widget-nopad">

                            <div class="widget-header">
                                <i class="icon-list-alt"></i>
                                <h3>Recent Payment</h3>
                            </div> <!-- /widget-header -->

                            <div class="widget-content">
                                <ul class="news-items">
                                    <li>
                                        <div class="news-item-detail">										
                                            <a href="javascript:;" class="news-item-title">500 Nin be added</a>
                                            <p class="news-item-preview">500 Nin be added by HunterBMT via Nin code : 56fgds54gd43</p>
                                        </div>

                                        <div class="news-item-date">
                                            <span class="news-item-day">10</span>
                                            <span class="news-item-month">Mar</span>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="news-item-detail">										
                                            <a href="javascript:;" class="news-item-title">500 Nin be added</a>
                                            <p class="news-item-preview">500 Nin be added by HunterBMT via Nin code : 56f54444444</p>
                                        </div>

                                        <div class="news-item-date">
                                            <span class="news-item-day">08</span>
                                            <span class="news-item-month">Mar</span>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="news-item-detail">										
                                            <a href="javascript:;" class="news-item-title">200 Nin be added</a>
                                            <p class="news-item-preview">500 Nin be added by HunterBMT via Nin code : 56svcfd43554</p>
                                        </div>

                                        <div class="news-item-date">
                                            <span class="news-item-day">08</span>
                                            <span class="news-item-month">Mar</span>
                                        </div>
                                    </li>
                                </ul>
                            </div> <!-- /widget-content -->
                        </div>
                    </div>
                </div>    
            </div>
        </div>

    </body>
</html>
