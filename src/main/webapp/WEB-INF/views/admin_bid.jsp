
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
        <script src="/resources/js/lib/jquery.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
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
                                            <span class="input-medium uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Last user ID</label>
                                        <div class="controls">
                                            <span class="input-medium uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Product ID</label>
                                        <div class="controls">
                                            <span class="input-medium uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Current price</label>
                                        <div class="controls">
                                            <span class="input-medium uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Last edit</label>
                                        <div class="controls">
                                            <span class="input-medium uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Start date</label>
                                        <div class="controls">
                                            <input type="text">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">End date</label>
                                        <div class="controls">
                                            <input type="text">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Status</label>
                                        <div class="controls">
                                            <input type="text">
                                        </div>
                                    </div>
                                    <div class="controls-button">
                                        <button type="submit" class="btn btn-warning">Save</button>
                                        <button type="submit" class="btn">New</button>
                                    </div>
                                </form>
                            </div>

                        </div>
                        
                    </div>
                    <div class="span8">
                        <!-- ongoing bids -->
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
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>IPhone4</td>
                                            <td>04/04/2013 08:00:00</td>
                                            <td>Le Anh</td>
                                            <td>450</td>
                                            <td>04/04/2013 09:33:45</td>
                                            <td class="td-actions">
                                                <a href="javascript:;" class="btn btn-small">
                                                    <i class="btn-icon-only icon-stop"></i>										
                                                </a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>IPhone 3GS</td>
                                            <td>04/04/2013 08:00:00</td>
                                            <td>Le Anh Minh</td>
                                            <td>1450</td>
                                            <td>04/04/2013 09:33:45</td>
                                            <td class="td-actions">                                        
                                                <a href="javascript:;" class="btn btn-small">
                                                    <i class="btn-icon-only icon-stop"></i>										
                                                </a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>IPhone 3GS</td>
                                            <td>04/04/2013 08:00:00</td>
                                            <td>Le Anh Minh</td>
                                            <td>1450</td>
                                            <td>04/04/2013 09:33:45</td>
                                            <td class="td-actions">                                        
                                                <a href="javascript:;" class="btn btn-small">
                                                    <i class="btn-icon-only icon-stop"></i>										
                                                </a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>4</td>
                                            <td>IPhone 3GS</td>
                                            <td>04/04/2013 08:00:00</td>
                                            <td>Le Anh Minh</td>
                                            <td>1450</td>
                                            <td>04/04/2013 09:33:45</td>
                                            <td class="td-actions">                                        
                                                <a href="javascript:;" class="btn btn-small">
                                                    <i class="btn-icon-only icon-stop"></i>										
                                                </a>
                                            </td>
                                        </tr>
                                        
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
                        <!-- unapproved bids -->
                        <div class="widget widget-table action-table">

                            <div class="widget-header">
                                <i class="icon-list-alt"></i>
                                <h3>Unapproved Bids</h3>
                            </div>

                            <div class="widget-content">
                                <table class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Product</th>
                                            <th>Start Date</th>
                                            <th class="td-actions"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>IPhone4</td>
                                            <td>04/04/2013 19:55:00</td>
                                            <td class="td-actions">
                                                <a href="javascript:;" class="btn btn-small btn-warning">
                                                    <i class="btn-icon-only icon-book"></i>										
                                                </a>
                                                <a href="javascript:;" class="btn btn-small">
                                                    <i class="btn-icon-only icon-remove"></i>										
                                                </a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>IPhone 5</td>
                                            <td>04/04/2013 19:55:00</td>
                                            <td class="td-actions">
                                                <a href="javascript:;" class="btn btn-small btn-warning">
                                                    <i class="btn-icon-only icon-book"></i>										
                                                </a>
                                                <a href="javascript:;" class="btn btn-small">
                                                    <i class="btn-icon-only icon-remove"></i>										
                                                </a>
                                            </td>
                                        </tr>
                                        
                                       
                                        
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
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>IPhone4</td>
                                            <td>04/04/2013 08:00:00</td>
                                            <td>Le Anh</td>
                                            <td>1450</td>
                                            <td>04/04/2013 09:33:45</td>
                                            
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>IPhone 3GS</td>
                                            <td>04/04/2013 08:00:00</td>
                                            <td>Le Anh Minh</td>
                                            <td>1050</td>
                                            <td>04/04/2013 09:33:45</td>
                                            
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>IPhone 3GS</td>
                                            <td>04/04/2013 08:00:00</td>
                                            <td>Le Anh Minh</td>
                                            <td>1390</td>
                                            <td>04/04/2013 09:33:45</td>
                                           
                                        </tr>
                                        <tr>
                                            <td>4</td>
                                            <td>IPhone 3GS</td>
                                            <td>04/04/2013 08:00:00</td>
                                            <td>Le Anh Minh</td>
                                            <td>450</td>
                                            <td>04/04/2013 09:33:45</td>
                                            
                                        </tr>
                                        
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
