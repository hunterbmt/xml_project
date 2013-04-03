/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var current_page;
var page_size = 5;

function loadProductList(page) {
    current_page = page;
    vteam_http.makeHttpRequest("/admin/getProductList",
            {page: page, pageSize: page_size},
    'POST',
            function(result) {
                if (result.status == 'success') {
                    displayProduct(result.productList);
                    if (result.numberOfProduct > page_size) {
                        displayPagination(page, result.numberOfProduct);
                    }
                }
            });
}
function displayPagination(currentPage, total_number) {
    $("#pagination_bar").pagination({
        items: total_number,
        itemsOnPage: page_size,
        currentPage: currentPage,
        cssStyle: 'light-theme',
        onPageClick: function(pageNumber, event) {
            loadProductList(pageNumber);
        }
    });
}
function displayProduct(productList) {
    var html = '';
    for (var i = 0; i < productList.length; i++) {
        html += "<tr>"
        html += "<td>" + productList[i].id + "</td>"
        html += "<td>" + productList[i].name + "</td>"
        html += "<td>" + productList[i].shortDescription + "</td>"
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
    vteam_http.setHTML("product_list_tbody",html);  
}
function insertOrUpdateProduct() {
    var id = parseInt(vteam_http.getHTML("product_id"));
    var product_name = vteam_http.getValue("product_name");
    var category_id = product_current_category_id;
    var description = encodeURI(editor.getValue());
    var min_price = vteam_http.getValue("product_min_price");
    var max_price = vteam_http.getValue("product_max_price");
    var img = vteam_http.getValue("product_img");
    var tags = vteam_http.getValue('tags_id');
    if (id) {
        vteam_http.makeHttpRequest("/admin/update_product",
                {productId: id,
                    productName: product_name,
                    categoryId: category_id,
                    description: description,
                    img: img,
                    minPrice: parseFloat(min_price),
                    maxPrice: parseFloat(max_price),
                    tags: tags},
        'POST', callback);
    } else {
        if (validProduct(product_name, category_id, min_price, max_price, img)) {
            vteam_http.makeHttpRequest("/admin/insert_product",
                    {
                        productName: product_name,
                        description: description,
                        categoryId: category_id,
                        img: img,
                        minPrice: parseFloat(min_price),
                        maxPrice: parseFloat(max_price),
                        tags: tags},
            'POST', callback);
        }
        clearValidProduct();
    }
}
function callback(result) {
    if (result.status === 'success') {
        vteam_http.setHTML("result_product","Successful !");
        vteam_http.show("result_product");
        $(function() {
            setTimeout(function() {
                vteam_http.hide("result_product");
            }, 20000);
        });
        loadProductList(current_page);
    } else {
        vteam_http.setHTML("result_product",result.msg);
        vteam_http.show("result_product");
        $(function() {
            setTimeout(function() {
                vteam_http.hide("result_product");
            }, 20000);
        });
    }
    clearProductDetail();
}
function clearProductDetail() {
    vteam_http.setHTML("product_id",'');
    vteam_http.setValue("product_name",'');
    vteam_http.setValue('category_name',"");
    editor.setValue('');
    vteam_http.setValue("product_min_price",'');
    vteam_http.setValue("product_max_price",'');
    vteam_http.setValue("product_img",'');
    $('.select2-search-choice').remove();
    vteam_http.setValue('tags_id','');
    vteam_http.setValue('tags_name','');
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
    vteam_http.setHTML("product_id",detail.id);
    vteam_http.setValue("product_name",detail.name);
    vteam_http.setValue("category_name",detail.categoryName);
    product_current_category_id = detail.categoryId;
    editor.setValue(detail.description);
    vteam_http.setValue("product_min_price",detail.minPrice);
    vteam_http.setValue("product_max_price",detail.maxPrice);
    vteam_http.setValue("product_img",detail.imageName);
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
        category_array_source.push({label: list[i].name, value: list[i].name, id: list[i].id});
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
    vteam_http.setHTML('category_detail_id',category.id);
    vteam_http.setValue('category_detail_description',category.description);
    vteam_http.setHTML('category_detail_btn','Update');

}
function clearCategoryDetail() {
    vteam_http.setHTML('category_detail_id','');
    vteam_http.setValue('category_detail_name','');
    vteam_http.setValue('category_detail_description','');
    vteam_http.setHTML('category_detail_btn','Save');
    category_current_id = null;
}
function insertOrUpdateCategory() {
    var categoryId = category_current_id;
    var description = vteam_http.getValue('category_detail_description');
    var name = vteam_http.getValue('category_detail_name');
    validCategory(name, description);
    if (categoryId) {
        vteam_http.makeHttpRequest("/admin/update_category",
                {categoryId: categoryId,
                    description: description},
        'POST', callbackCategoryEdit);
    } else {
        if (validCategory(name, description)) {
            vteam_http.makeHttpRequest("/admin/insert_category",
                    {
                        categoryName: name,
                        description: description},
            'POST', callbackCategoryEdit);
        }
        clearValidCategory();
    }
}
function callbackCategoryEdit(result) {
    if (result.status === 'success') {
        displayCategoryMsg("Successfull");
        populateCategoryNameList();
    } else {
        displayCategoryMsg(result.msg);
    }
    clearCategoryDetail();
}
function displayCategoryMsg(msg) {
    vteam_http.setHTML("result_category",msg);
    vteam_http.show("result_category");
    $(function() {
        setTimeout(function() {
            vteam_http.hide("result_category");
        }, 20000);
    });
}
function validProduct(name, cate_name, min_price, max_price, image)
{

    if (!name) {
        var div = $("#product_name").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_product").html('Fields must be required').show();
        return false;
    } else {
        var div = $("#product_name").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");

    }
    if (!cate_name) {
        var div = $("#category_name").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_product").html('Fields must be required').show();
        return false;
    } else {
        var div = $("#category_name").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");

    }
    if (!min_price) {
        var div = $("#product_min_price").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_product").html('Fields must be required').show();
        return false;
    } else {
        var div = $("#product_min_price").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");

    }
    if (!max_price) {
        var div = $("#product_max_price").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_product").html('Fields must be required').show();
        return false;
    } else {
        var div = $("#product_max_price").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");

    }
    if (!image) {
        var div = $("#product_img").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_product").html('Fields must be required').show();
        return false;
    } else {
        var div = $("#product_img").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");

    }
    return true;
}
function clearValidProduct() {
    var div_pname = $("#product_name").parents("div.control-group");
    var div_cname = $("#category_name").parents("div.control-group");
    var div_min = $("#product_min_price").parents("div.control-group");
    var div_max = $("#product_max_price").parents("div.control-group");
    var div_img = $("#product_img").parents("div.control-group");
    div_pname.removeClass("success");
    div_cname.removeClass("success");
    div_min.removeClass("success");
    div_max.removeClass("success");
    div_img.removeClass("success");
}
function validCategory(name, des) {
    if (!name) {
        var div = $("#category_detail_name").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_category").html('Fields must be required').show();
        return false;
    } else {
        var div = $("#category_detail_name").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");

    }
    if (!des) {
        var div = $("#category_detail_description").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_category").html('Fields must be required').show();
        return false;
    } else {
        var div = $("#category_detail_description").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");
    }
    return  true;
}
function clearValidCategory() {
    var div_name = $("#category_detail_name").parents("div.control-group");
    var div_des = $("#category_detail_description").parents("div.control-group");
    div_name.removeClass("success");
    div_des.removeClass("success");
}

