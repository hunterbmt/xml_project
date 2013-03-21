/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var current_page;
var current_page_size;

function loadProductList(page,page_size){
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
function displayProduct(productList){
    var html = '';
    for (var i = 0; i < productList.length; i++) {
        html+="<tr>"
        html+="<td>"+productList[i].id+"</td>"
        html+="<td>"+productList[i].name+"</td>"
        html+="<td>"+productList[i].description+"</td>"
        html+='<td class="td-actions">'
        html+='<a href="javascript:;" class="btn btn-small btn-warning" onclick="getProductDetail('+productList[i].id+')" >'
        html+='<i class="btn-icon-only icon-edit"></i>'									
        html+='</a>'
        html+='<a href="javascript:;" class="btn btn-small" onclick="deleteProduct('+productList[i].id+'">';
        html+='<i class="btn-icon-only icon-remove"></i>'										
        html+='</a>'
        html+='</td>'
        html+='</tr>'
    }
    $("#product_list_tbody").html(html);
}
function insertOrUpdateProduct() {
    var id = $("#product_id").val();
    var product_name = $("#product_name").val();
    var category_id = array_keys[array_values.indexOf($("#category_name").val())];
    var editor = CKEDITOR.instances.product_description;
    var description = escape(editor.getData().toString());
    var min_price = $("#product_min_price").val();
    var max_price = $("#product_max_price").val();
    var img = $("#product_img").val();
    vteam_http.init();
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
            'POST', populateList);
}

var array_keys;
var array_values;
function populateList(list) {
    array_keys = new Array();
    array_values = new Array();

    for (var key in list) {
        array_keys.push(key);
        array_values.push(list[key]);
    }
    $("#category_name").autocomplete({
        source: array_values
    });
}