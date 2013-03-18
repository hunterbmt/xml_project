
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
        <link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="//netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
        <link href="/resources/css/admin.css" rel="stylesheet">
        <!--<link rel="stylesheet" media="all" type="text/css" href="http://code.jquery.com/ui/1.10.0/themes/smoothness/jquery-ui.css" />-->
        <link href="/resources/css/jquery-ui-helper.css" rel="stylesheet">
        <link href="/resources/css/bid.css" rel="stylesheet">
        <script src="/resources/js/lib/jquery.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
        <script src="/resources/js/vteam.js"></script>
        <script src="/resources/js/dateUtils.js"></script>
        <script src="/resources/js/bid.js"></script>
        <script src="/resources/js/jquery-ui-timepicker-addon.js"></script>
        
        <script>
            $(document).ready(function() {
                vteam_http.init();                
                _displayOngoingBid();
                $('#bid_start_date').datetimepicker();
                $('#bid_end_date').datetimepicker();
            });
            

        </script>
    </head>
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <ul>
                        <li>
                            <a href="./">
                                <i class="icon-dashboard"></i>
                                <span>Dash Board</span>
                            </a>	    				
                        </li>
                        <li>
                            <a href="./admin/product">
                                <i class="icon-book"></i>
                                <span>Product</span>
                            </a>	    				
                        </li>
                        <li>
                            <a href="./users">
                                <i class="icon-group"></i>
                                <span>Users</span>
                            </a>	    				
                        </li>
                        <li class="active">
                            <a href="./bid">
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
                    <div class="span4">
                        <div class="widget">
                            <div class="widget-header">
                                <i class="icon-tasks"></i>
                                <h3>Bid detail</h3>
                            </div>

                            <div class="widget-content">
                                <form action="#" method="get" class="form-horizontal">
                                    <div class="control-group">
                                        <label class="control-label">ID</label>
                                        <div class="controls">
                                            <span id="bid_id" class="input-small uneditable-input">
                                                
                                            </span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Last user</label>
                                        <div class="controls">
                                            <span id="bid_last_userid" class="uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Product Name</label>
                                        <div class="controls">
                                            <input id="bid_product_name" type="text">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Current price</label>
                                        <div class="controls">
                                            <span id="bid_current_price" class="uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Last edit</label>
                                        <div class="controls">
                                            <span id="bid_last_edit" class="uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Start date</label>
                                        <div class="controls">
                                            <input id="bid_start_date" type="text">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">End date</label>
                                        <div class="controls">
                                            <input id="bid_end_date" type="text">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Status</label>
                                        <div class="controls">
                                            <input id ="bid_status" type="text">
                                        </div>
                                    </div>
                                    <div class="controls-button">
                                        <button type="submit" class="btn btn-warning" onclick="insertOrUpdateBid()">Save</button>
                                        <button type="submit" class="btn" onclick="clearBidDetail()">New</button>
                                    </div>
                                </form>
                            </div>

                        </div>

                    </div>
                    <div class="span8">

                        <!-- Ongoing bids -->
                        <div class="widget widget-table action-table">

                            <div class="widget-header">
                                <i class="icon-list-alt"></i>
                                <h3>Ongoing Bids</h3>
                            </div>

                            <div class="widget-content">
                                <table class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Product</th>
                                            <th>Start date</th>
                                            <th>Current user</th>
                                            <th>Current price</th>
                                            <th>Last bid</th>
                                            <th class="td-actions"></th>
                                        </tr>
                                    </thead>
                                    <tbody id="onGoingBid">
                                    </tbody>
                                </table>
                                <div class="pagination-bar">
                                    <a tabindex="0" class="ui-button last">Last</a>
                                    <a tabindex="0" class="ui-button next">Next</a>

                                    <span>
                                        <a tabindex="0" class="ui-button active">1</a>
                                        <a tabindex="0" class="ui-button ">2</a>
                                        <a tabindex="0" class="ui-button ">3</a>                                        
                                    </span>
                                    <a tabindex="0" class="ui-button ui-button-diable">Previous</a>
                                    <a tabindex="0" class="ui-button ui-button-diable first">First</a>

                                </div>
                            </div>
                        </div>
                        <!-- Upcoming bids -->
                        <div class="widget widget-table action-table">

                            <div class="widget-header">
                                <i class="icon-list-alt"></i>
                                <h3>Upcoming Bids</h3>
                            </div>

                            <div class="widget-content">
                                <table class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Product</th>
                                            <th>Start date</th>
                                            
                                            <th class="td-actions"></th>
                                        </tr>
                                    </thead>
                                    <tbody id="upComingBid">
                                    </tbody>
                                </table>
                                <div class="pagination-bar">
                                    <a tabindex="0" class="ui-button last">Last</a>
                                    <a tabindex="0" class="ui-button next">Next</a>

                                    <span>
                                        <a tabindex="0" class="ui-button active">1</a>
                                        <a tabindex="0" class="ui-button ">2</a>
                                        <a tabindex="0" class="ui-button ">3</a>                                        
                                    </span>
                                    <a tabindex="0" class="ui-button ui-button-diable">Previous</a>
                                    <a tabindex="0" class="ui-button ui-button-diable first">First</a>

                                </div>
                            </div>
                        </div>
                        
                        <!-- completed bids -->
                        <div class="widget widget-table action-table">

                            <div class="widget-header">
                                <i class="icon-list-alt"></i>
                                <h3>Completed Bids</h3>
                            </div>

                            <div class="widget-content">
                                <table class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Product</th>
                                            <th>Start date</th>
                                            <th>Bidder</th>
                                            <th>Price</th>
                                            <th>Complete on</th>
                                        </tr>
                                    </thead>
                                    <tbody id="completedBids">                                 

                                    </tbody>
                                </table>
                                <div class="pagination-bar">
                                    <a tabindex="0" class="ui-button last">Last</a>
                                    <a tabindex="0" class="ui-button next">Next</a>

                                    <span>
                                        <a tabindex="0" class="ui-button active">1</a>
                                        <a tabindex="0" class="ui-button ">2</a>
                                        <a tabindex="0" class="ui-button ">3</a>                                        
                                    </span>
                                    <a tabindex="0" class="ui-button ui-button-diable">Previous</a>
                                    <a tabindex="0" class="ui-button ui-button-diable first">First</a>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>    
            </div>
        </div>
    </body>
</html>
