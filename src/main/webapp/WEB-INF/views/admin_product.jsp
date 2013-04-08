
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="/resources/css/bootstrap-wysihtml5.css" rel="stylesheet">
        <link href="/resources/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
        <link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="/resources/css/font-awesome.css" rel="stylesheet">
        <link href="/resources/css/jquery-ui-helper.css" rel="stylesheet">
        <link href="/resources/css/jquery.fileupload-ui.css" rel="stylesheet">
        <link href="/resources/css/simplePagination.css" rel="stylesheet">
        <link href="/resources/css/admin.css" rel="stylesheet">
        <link href="/resources/css/select2.css" rel="stylesheet">


        <script src="/resources/js/lib/jquery.js"></script>
        <script src="/resources/js/lib/wysihtml5-0.3.0.js"></script>
        <script type="text/javascript" src="/resources/js/lib/jquery.simplePagination.js"></script>
        <script src="/resources/js/lib/jquery.url.js"></script>
        <script src="/resources/js/lib/jquery-ui.js"></script>
        <script src="/resources/js/lib/bootstrap.min.js"></script>
        <script src="/resources/js/lib/bootstrap-wysihtml5.js"></script>
        <script src ="/resources/js/lib/jquery.ui.widget.js"></script>
        <script src ="/resources/js/lib/jquery.iframe-transport.js"></script>
        <script src ="/resources/js/lib/jquery.fileupload.js"></script>
        <script src="/resources/js/lib/jquery.validate.js"></script>
        <script src="/resources/js/vteam.js"></script>
        <script src="/resources/js/admin_product.js"></script>
        <script src="/resources/js/admin_tags.js"></script>
        <script src="/resources/js/upload.js"></script>
        <script>
            window.onload = function() {
                populateCategoryNameList()
                populateTagsNameList()
                loadProductList(1);
                $('#product_img_file').fileupload({
                    dataType: 'text',
                    url: "/admin/upload_product_img",
                    paramName: 'uploadedFileItem',
                    sequentialUploads: true,
                    add: function(e, data) {
                        disableFileUpload();
                        beforeSend(data);
                    },
                    progressall: function(e, data) {
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                        showProgress(progress);
                        disableFileUpload();
                    },
                    done: function(e, data) {
                        uploadComplete(jQuery.parseJSON(data.result));
                        enableFileUpload();
                    },
                    fail: function(e, data) {
                        uploadFail();
                        enableFileUpload();
                    }
                });
            };
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
                        <li class="active">
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
                        <li>
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
                    <div class="span5">
                        <div class="widgetresult_product">
                            <div class="widget-header">
                                <i class="icon-tasks"></i>
                                <h3>Product detail</h3>
                            </div>

                            <div class="widget-content">
                                <div class ="form-horizontal" id="product_detail">
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
                                            <input autocomplete="off" id="category_name" type="text"/>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Tags</label>
                                        <div class="controls">
                                            <div class="select2-container select2-container-multi" id="s2id_autogen2">  
                                                <input id="tags_id" type="hidden" class="select2-input" tabindex="0" style="width: 200px;">
                                                <ul class="select2-choices">  
                                                    <li class="select2-search-field">    
                                                        <input id="tags_name" autocomplete="off" type="text" class="select2-input" tabindex="0" style="width: 200px;">
                                                    </li>
                                                </ul>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="row">
                                            <label class="control-label">Description</label>
                                        </div>
                                        <textarea class="textarea" id="product_description" style="width: 430px;height: 300px"></textarea>
                                        <script>
                                            $('#product_description').wysihtml5();
                                        </script>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Min price</label>
                                        <div class="controls">
                                            <input type="number" id="product_min_price">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Max price</label>
                                        <div class="controls">
                                            <input type="number" id="product_max_price">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Image</label>
                                        <div>
                                            <image style="max-height: 300px" id="product_img"/>
                                            <input type="hidden" id="product_img_name"/>
                                        </div>
                                        <div class="controls">
                                            <div class="uploader" id="uniform-undefined">
                                                <input type="file" size="19" style="opacity: 0;" id="product_img_file" accept="image/*">
                                                <span class="filename">No file selected</span>
                                                <span class="action">Choose File</span>
                                            </div>
                                            <div id ="progress_div"class="progress hide">
                                                <div class="bar"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="controls-button">
                                        <button type="button" class="btn btn-warning" onclick="insertOrUpdateProduct()">Save</button>
                                        <button type="button" class="btn" onclick="clearProductDetail()">New</button>
                                    </div>
                                    <div>
                                        <span class="alert-info hide" id="result_product"></span>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>
                    <div class="span7">
                        <div class="widget widget-table action-table">

                            <div class="widget-header">
                                <i class="icon-list-alt"></i>
                                <h3>Products</h3>
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
                                    <div id="pagination_bar" style="float: right">

                                    </div>
                                </div>
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
                                            <span id="category_detail_id"class="input-medium uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Name</label>
                                        <div class="controls">
                                            <input id="category_detail_name" type="text"/>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Description</label>
                                        <div class="row">
                                            <textarea id="category_detail_description" style="height: 120px"></textarea>
                                        </div>
                                    </div>
                                    <div class="controls-button">
                                        <button type="button" id="category_detail_btn" class="btn btn-warning" onclick="insertOrUpdateCategory()">Save</button>
                                        <button type="button" class="btn" onclick="clearCategoryDetail()">New</button>
                                    </div>
                                    <div>
                                        <span class="alert-info hide" id="result_category"></span>
                                    </div>
                                </form>
                            </div>

                        </div>
                        <div class="widget">
                            <div class="widget-header">
                                <i class="icon-tasks"></i>
                                <h3>Tags detail</h3>
                            </div>

                            <div class="widget-content">
                                <form action="#" method="get" class="form-horizontal">
                                    <div class="control-group">
                                        <label class="control-label">Id</label>
                                        <div class="controls">
                                            <span id="tag_detail_id"class="input-medium uneditable-input"></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Name</label>
                                        <div class="controls">
                                            <input id="tag_detail_name" type="text"/>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Description</label>
                                        <div class="row">
                                            <textarea id="tag_detail_description" style="height: 120px"></textarea>
                                        </div>
                                    </div>
                                    <div class="controls-button">
                                        <button type="button" id="tag_detail_btn" class="btn btn-warning" onclick="insertOrUpdateTag()">Save</button>
                                        <button type="button" class="btn" onclick="clearTagDetail()">New</button>
                                    </div>
                                    <div>
                                        <span class="alert-info hide" id="result_tag"></span>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div id="delete_product_confirm" title="Delete the product ?">
                        <p><i class="icon-warning-sign"></i> <span id="delete_product_confirm_msg"></span></p>
                    </div>
                </div>    
            </div>
        </div>
    </body>
</html>
