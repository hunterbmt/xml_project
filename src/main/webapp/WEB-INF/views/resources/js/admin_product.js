/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var current_page;
var current_page_size;

function loadProductList(page, page_size) {
    current_page = page;
    current_page_size = page_size;
    vteam_http.makeHttpRequest("/product/getProductList",
            {page: page, pageSize: page_size},
    'POST',
            function(result) {
                if (result.status == 'success') {
                    displayProduct(result.productList);
                }
            });
}
function displayProduct(productList) {
    var html = '';
    for (var i = 0; i < productList.length; i++) {
        html += "<tr>"
        html += "<td>" + productList[i].id + "</td>"
        html += "<td>" + productList[i].name + "</td>"
        html += "<td>" + productList[i].description + "</td>"
        html += '<td class="td-actions">'
        html += '<a href="javascript:;" class="btn btn-small btn-warning" onclick="getProductDetail(' + productList[i].id + ')" >'
        html += '<i class="btn-icon-only icon-edit"></i>'
        html += '</a>'
        html += '<a href="javascript:;" class="btn btn-small" onclick="deleteProduct(' + productList[i].id + '">';
        html += '<i class="btn-icon-only icon-remove"></i>'
        html += '</a>'
        html += '</td>'
        html += '</tr>'
    }
    $("#product_list_tbody").html(html);
}
function insertOrUpdateProduct() {
    var id = parseInt($("#product_id").html());
    var product_name = $("#product_name").val();
    var category_id = product_current_category_id;
    var editor = CKEDITOR.instances.product_description;
    var description = escape(editor.getData().toString());
    var min_price = $("#product_min_price").val();
    var max_price = $("#product_max_price").val();
    var img = $("#product_img").val();
    if (id) {
        vteam_http.makeHttpRequest("/admin/update_product",
                {productId: id,
                    productName: product_name,
                    categoryId: category_id,
                    description: description,
                    img: img,
                    minPrice: parseFloat(min_price),
                    maxPrice: parseFloat(max_price)},
        'POST', callback);
    } else {
        vteam_http.makeHttpRequest("/admin/insert_product",
                {
                    productName: product_name,
                    description: description,
                    categoryId: category_id,
                    img: img,
                    minPrice: parseFloat(min_price),
                    maxPrice: parseFloat(max_price)},
        'POST', callback);
    }
}
function callback(result) {
    if (result.status === 'success') {
        $('#result_product').html('Successful !').show();
        $(function() {
            setTimeout(function() {
                $("#result_product").hide('blind', {}, 400)
            }, 2000);
        });
    } else {
        $('#result_product').html(result.msg).show();
        $(function() {
            setTimeout(function() {
                $("#result_product").hide('blind', {}, 400)
            }, 2000);
        });
    }
    clearProductDetail();
}
function clearProductDetail() {
    $("#product_id").val('');
    $("#product_name").val('');
    $('#category_name').val('');
    var editor = CKEDITOR.instances.product_description;
    editor.setData('');
    $("#product_min_price").val('');
    $("#product_max_price").val('');
    $("#product_img").val('');
}
function getProductDetail(id) {
    vteam_http.makeHttpRequest('/product/getProductById', {product_id: id}, 'POST',
            function(result) {
                if (result.status == "success") {
                    displayProductDetail(result);
                }
                else {
                    alert(result.status);
                }
            })
}
function displayProductDetail(detail) {
    $("#product_id").html(detail.id);
    $("#product_name").val(detail.name);
    $("#category_name").val(detail.categoryName);
    product_current_category_id = detail.categoryId
    var editor = CKEDITOR.instances.product_description;
    editor.setData(detail.description);
    $("#product_min_price").val(detail.minPrice);
    $("#product_max_price").val(detail.maxPrice);
    $("#product_img").val(detail.imageName);
}
function populateCategoryNameList() {
    vteam_http.makeHttpRequest("/admin/getCategoryNameList",
            {
            },
            'POST',
            function(result) {
                if (result.status === 'success') {
                    populateList(result.categoryList)
                }
            });
}

var category_array_source;
var product_current_category_id;
var category_current_id;
function populateList(list) {
    category_array_source = new Array();

    for (i = 0; i < list.length; i++) {
        category_array_source.push({label: list[i].name, value: list[i].name,id:list[i].id});
    }
    $("#category_name").autocomplete({
        source: category_array_source,
        select: function(event, ui) {
            product_current_category_id = ui.item.id;
        }
    });
    $("#category_detail_name").autocomplete({
        source: category_array_source,
        select: function(event, ui) {
            category_current_id = ui.item.id;
            loadAndDisplatCategoryDetail(category_current_id);
        }
    });
}
function loadAndDisplatCategoryDetail(id) {
    vteam_http.makeHttpRequest("/category/getCategoryDetail",
            {
                category_id: id
            },
    'POST', function(result) {
        if (result.status === 'success') {
            displayCategoryDetail(result);
        }
    });
}
function displayCategoryDetail(category) {
    $('#category_detail_id').html(category.id);
    $('#category_detail_description').val(category.description);
    $('#category_detail_btn').html('Update');

}
function clearCategoryDetail(){
    $('#category_detail_id').html('');
    $('#category_detail_name').val('');
    $('#category_detail_description').val('');
    $('#category_detail_btn').html('Save');
}
function insertOrUpdateCategory(){
    var categoryId = category_current_id;
    var description = $('#category_detail_description').val();
    var name = $('#category_detail_name').val();
    if (categoryId) {
        vteam_http.makeHttpRequest("/admin/update_category",
                {categoryId: categoryId,
                 description: description},
        'POST', callbackCategoryEdit);
    } else {
        vteam_http.makeHttpRequest("/admin/insert_category",
                {
                    categoryName: name,
                    description: description},
        'POST', callbackCategoryEdit);
    }
}
function callbackCategoryEdit(result){
    if(result.status ==='success'){
        displayCategoryMsg("Successfull")
    }else {
        displayCategoryMsg(result.msg);
    }
    clearCategoryDetail();
}
function displayCategoryMsg(msg){
    $('#result_category').html(msg).show();
        $(function() {
            setTimeout(function() {
                $("#result_category").hide('blind', {}, 400)
            }, 10000);
        });
}