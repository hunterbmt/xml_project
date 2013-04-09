
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
        <link href="/resources/css/simplePagination.css" rel="stylesheet">
        <link href="/resources/css/admin.css" rel="stylesheet">
        <script src="/resources/js/lib/jquery.js"></script>
        <script type="text/javascript" src="/resources/js/lib/jquery.simplePagination.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="/resources/js/lib/bootstrap.min.js"></script>
        <script src="/resources/js/vteam.js"></script>
        <script src="/resources/js/admin_order.js"></script>
        <script>
            window.onload = function() {
                loadOrderList(1);
            }
        </script>
    </head>
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <ul>
                        <li>
                            <a href="/admin">
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
                            <a href="/admin/bid">
                                <i class="icon-legal"></i>
                                <span>Bid</span>
                            </a>	    				
                        </li>
                        <li class="active">
                            <a href="/admin/order">
                                <i class="icon-money"></i>
                                <span>Order</span>
                            </a>	    				
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="main" >
            <div class="container">
                <div class="row">
                    <div class="widget widget-table action-table">
                        <div class="widget-header">
                            <i class="icon-list-alt"></i>
                            <h3>Orders</h3>
                        </div>
                        <div class="widget-content">
                            <table class="table table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th >ID</th>
                                        <th >User</th>
                                        <th >Product</th>
                                        <th >Address</th>
                                        <th>Amount</th>
                                        <th>Order day</th>
                                        <th>Status</th>
                                        <th style ="width: 140px"class="td-actions"></th>
                                    </tr>
                                </thead>
                                <tbody id="order_list_tbody">

                                </tbody>
                            </table>
                            <div class="pagination-bar">
                                <div id="pagination_bar" style="float: right">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>    
        <div id="order_detail_modal" style="width: 30%" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="myModalLabel">Order detail</h3>
            </div>
            <div class="modal-body">
                <form>
                    <fieldset>
                        <div id ="order_detail_result" class="hide">All form fields are required.</div>
                        <label>Address</label>
                        <input type="text" id="order_detal_address"/>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
                <button class="btn btn-warning" onclick="updateOrder()">Update</button>
            </div>
        </div>
        <div id="order_action_confirm" title="Confirm your action" class="hide">
           <p><i class="icon-warning-sign"></i> <span id="order_action_confirm_msg"></span></p>
        </div>
    </body>
</html>
