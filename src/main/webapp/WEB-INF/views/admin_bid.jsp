
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="/resources/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
        <link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="/resources/css/font-awesome.css" rel="stylesheet">
        <link href="/resources/css/admin.css" rel="stylesheet">
        <link href="/resources/css/simplePagination.css" rel="stylesheet">
        <link href="/resources/css/jquery-ui-helper.css" rel="stylesheet">
        <link href="/resources/css/bid.css" rel="stylesheet">
        <script src="/resources/js/lib/jquery.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="/resources/js/lib/bootstrap.min.js"></script>
        <script src="/resources/js/vteam.js"></script>
        <script src="/resources/js/dateUtils.js"></script>
        <script src="/resources/js/bid.js"></script>
        <script src="/resources/js/jquery-ui-timepicker-addon.js"></script>
        <script type="text/javascript" src="/resources/js/lib/jquery.simplePagination.js"></script>
        <script src="/resources/js/xml_transform_helper.js"></script>

        <script>
            $(document).ready(function() {
                populateProductNameList();
                update_lists();
                $('#bid_start_date').datetimepicker();
                $('#bid_end_date').datetimepicker();
                bid_product_name_onchange();
            });
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
                        <li class="active">
                            <a href="/admin/bid">
                                <i class="icon-legal"></i>
                                <span>Bid</span>
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
                                    <div>
                                        <input type="hidden" id="product_id" value=""/>                                        
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">ID</label>
                                        <div class="controls">
                                            <span id="bid_id" class="input-small uneditable-input" style="width: 40px;">                                             
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
                                        <label class="control-label">Product Name *</label>
                                        <div id="product_name_container" class="controls">
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
                                        <label class="control-label">Start date *</label>
                                        <div class="controls">
                                            <input id="bid_start_date" type="text">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">End date *</label>
                                        <div class="controls">
                                            <input id="bid_end_date" type="text">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Cost *</label>
                                        <div class="controls">
                                            <input id="bid_cost" type="number" class="input-small">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Status</label>
                                        <div class="controls">                                            
                                            <select id ="bid_status">
                                                <option value="UNCOMPLETED">UnCompleted</option>
                                                <option value="COMPLETED">Competed</option>                                                
                                            </select>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label"></label>
                                        <div class="controls">                                            
                                            <span class="" style="color:#0033ff; font-size: smaller">Note: All * marked fields are required</span>
                                        </div>
                                    </div>
                                    <div class="controls-button">
                                        <button type="button" id="btnSave" class="btn btn-warning disabled" onclick="insertOrUpdateBid()">Save</button>
                                        <button type="button" class="btn newBtn" onclick="clearBidDetail(this)">New</button>
                                    </div>
                                    <div>
                                        <span class="alert-info" id="result_IU_bid">VTeam - XML_Project</span>
                                    </div>
                                    <script>
            $(function() {
                setTimeout(function() {
                    $("#result_IU_bid").hide('blind', {}, 500);
                }, 2000);
            });
                                    </script>
                                </form>
                            </div>

                        </div>

                    </div>
                    <div class="span8">
                        <div id="test11"></div>
                        <c:import url="/resources/xml/bids.xml" var="bidsXML" charEncoding="UTF-8"/>
                        
                        <!-- Ongoing bids -->
                        <script>                           
                                params = extractparams(window.location.search);
                                displayOngoingPaginationResult(params['page'],params['pageSize']);                           
                        </script>
                        
                        <!-- Upcoming bids -->                        
                        <c:import url="/resources/xsl/upcomingBid_list.xsl" var="upBidXSL"/>
                        <x:transform xml="${bidsXML}" xslt="${upBidXSL}"/>
                        

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
                                    <tbody id="completedBids"></tbody>
                                </table>
                                <div class="pagination-bar">
                                    <div id="completed_pagination_bar" style="float: right">

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>    
            </div>
        </div>
    </body>
</html>
