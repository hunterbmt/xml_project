
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
        <script src="/resources/js/lib/ckeditor/ckeditor.js"></script>
        <script src="/resources/js/lib/ckeditor/config.js"></script>
        <script src="/resources/js/lib/ckeditor/styles.js"></script>
        <script src="/resources/js/vteam.js"></script>
        <script src="/resources/js/admin_product.js"></script>
        <script>
            $(document).ready(function() {
                 populateCategoryNameList()
                 loadProductList(1,5);
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
                        <li  class="active">
                            <a href="/admin/product">
                                <i class="icon-book"></i>
                                <span>Product</span>
                            </a>	    				
                        </li>
                        <li>
                            <a href="/admin/users">
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
                            <a href="/admin/payment">
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
                    <div class="span5">
                        <div class="widget">
                            <div class="widget-header">
                                <i class="icon-tasks"></i>
                                <h3>Product detail</h3>
                            </div>

                            <div class="widget-content">
                                <form action="/product/update" method="POST" class="form-horizontal">
                                    <div class="control-group">
                                        <label class="control-label">Id</label>
                                        <div class="controls">
                                            <span id="product_id" class="input-medium uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Name</label>
                                        <div class="controls">
                                            <input id="product_name" type="text">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Category</label>
                                        <div class="controls">
                                            <input id="category_name" type="text"/>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="row">
                                            <label class="control-label">Description</label>
                                        </div>
                                        <textarea class="ckeditor" id="product_description"></textarea>

                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Min price</label>
                                        <div class="controls">
                                            <input type="text" id="product_min_price">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Max price</label>
                                        <div class="controls">
                                            <input type="text" id="product_max_price">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Image</label>
                                        <div class="controls">
                                            <input type="text" id="product_img">
                                        </div>
                                    </div>
                                    <div class="controls-button">
                                        <button type="button" class="btn btn-warning" onclick="insertOrUpdateProduct()">Save</button>
                                        <button type="button" class="btn" onclick="clearProductDetail()">New</button>
                                    </div>
                                </form>
                            </div>

                        </div>
                        <div class="widget">
                            <div class="widget-header">
                                <i class="icon-tasks"></i>
                                <h3>Category detail</h3>
                            </div>

                            <div class="widget-content">
                                <form action="#" method="get" class="form-horizontal">
                                    <div class="control-group">
                                        <label class="control-label">Id</label>
                                        <div class="controls">
                                            <span class="input-medium uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Name</label>
                                        <div class="controls">
                                            <input type="text">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Description</label>
                                        <div class="row">
                                            <textarea style="height: 120px"></textarea>
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
                    <div class="span7">
                        <div class="widget widget-table action-table">

                            <div class="widget-header">
                                <i class="icon-list-alt"></i>
                                <h3>Recent Payment</h3>
                            </div>

                            <div class="widget-content">
                                <table class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Description</th>
                                            <th class="td-actions"></th>
                                        </tr>
                                    </thead>
                                    <tbody id="product_list_tbody">
                                      
                                    </tbody>
                                </table>
                                <div class="pagination-bar">
                                    <a tabindex="0" class="ui-button last">Last</a>
                                    <a tabindex="0" class="ui-button next">Next</a>

                                    <span>
                                        <a tabindex="0" class="ui-button active">1</a>
                                        <a tabindex="0" class="ui-button ">2</a>
                                        <a tabindex="0" class="ui-button ">3</a>
                                        <a tabindex="0" class="ui-button ">4</a>
                                        <a tabindex="0" class="ui-button ">5</a>
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
