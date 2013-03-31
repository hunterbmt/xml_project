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
    $("#product_list_tbody").html(html);
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
    ;
    if (id) {
        vteam_http.makeHttpRequest("/admin/update_product",
                {productId: id,
                    productName: product_name,
                    categoryId: category_id,
                    description: description,
                    img: img,
                    minPrice: parseFloat(min_price),
                    maxPrice: parseFloat(max_price),
                    tags:tags},
        'POST', callback);
    } else {
        if(validProduct(product_name,category_id,min_price,max_price,img)){
        vteam_http.makeHttpRequest("/admin/insert_product",
                {
                    productName: product_name,
                    description: description,
                    categoryId: category_id,
                    img: img,
                    minPrice: parseFloat(min_price),
                    maxPrice: parseFloat(max_price),
                    tags:tags},
        'POST', callback);
        }
        clearValidProduct();
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
        loadProductList(current_page);
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
    $("#product_id").html('');
    $("#product_name").val('');
    $('#category_name').val('');
    editor.setValue('');
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
    product_current_category_id = detail.categoryId;
    editor.setValue(detail.description);
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
    $('#category_detail_id').html(category.id);
    $('#category_detail_description').val(category.description);
    $('#category_detail_btn').html('Update');

}
function clearCategoryDetail() {
    $('#category_detail_id').html('');
    $('#category_detail_name').val('');
    $('#category_detail_description').val('');
    $('#category_detail_btn').html('Save');
    category_current_id = null;
}
function insertOrUpdateCategory() {
    var categoryId = category_current_id;
    var description = $('#category_detail_description').val();
    var name = $('#category_detail_name').val();
    validCategory(name,description);
    if (categoryId) {
        vteam_http.makeHttpRequest("/admin/update_category",
                {categoryId: categoryId,
                    description: description},
        'POST', callbackCategoryEdit);
    } else {
        if(validCategory(name,description)){
        vteam_http.makeHttpRequest("/admin/insert_category",
                {
                    categoryName: name,
                    description: description},
        'POST', callbackCategoryEdit);
    }
    clearValidCateogry();
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
    $('#result_category').html(msg).show();
    $(function() {
        setTimeout(function() {
            $("#result_category").hide('blind', {}, 400)
        }, 10000);
    });
}
function validProduct(name,cate_name,min_price,max_price,image)
{
  if(name==null|| name==""){
      var div = $("#product_name").parents("div.control-group");
      div.removeClass("success");
      div.addClass("error");
      $("#result_product").html('Fields must be required').show();
      return false;
  }else {
      var div = $("#product_name").parents("div.control-group");
      div.removeClass("error");
      div.addClass("success");
      
  }
  if(cate_name==null|| cate_name==""){
      var div = $("#category_name").parents("div.control-group");
      div.removeClass("success");
      div.addClass("error");
      $("#result_product").html('Fields must be required').show();
      return false;
  }else {
      var div = $("#category_name").parents("div.control-group");
      div.removeClass("error");
      div.addClass("success");
     
  }
  if(min_price==null|| min_price==""){
      var div = $("#product_min_price").parents("div.control-group");
      div.removeClass("success");
      div.addClass("error");
      $("#result_product").html('Fields must be required').show();
      return false;
  }else {
      var div = $("#product_min_price").parents("div.control-group");
      div.removeClass("error");
      div.addClass("success");
      
  }
   if(max_price==null|| max_price==""){
      var div = $("#product_max_price").parents("div.control-group");
      div.removeClass("success");
      div.addClass("error");
      $("#result_product").html('Fields must be required').show();
      return false;
  }else {
      var div = $("#product_max_price").parents("div.control-group");
      div.removeClass("error");
      div.addClass("success");
      
  }
   if(image==null|| image==""){
      var div = $("#product_img").parents("div.control-group");
      div.removeClass("success");
      div.addClass("error");
      $("#result_product").html('Fields must be required').show();
      return false;
  }else {
      var div = $("#product_img").parents("div.control-group");
      div.removeClass("error");
      div.addClass("success");
      
  }
  return true;
}
function clearValidProduct(){
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
function vaildString(p){
    if(p==null||p==''){
        return false;
    }
}
function validCategory(name,des){
     if(name==null|| name==""){
      var div = $("#category_detail_name").parents("div.control-group");
      div.removeClass("success");
      div.addClass("error");
      $("#result_category").html('Fields must be required').show();
      return false;
  }else {
      var div = $("#category_detail_name").parents("div.control-group");
      div.removeClass("error");
      div.addClass("success");
      
  }
  if(des==null|| des==""){
      var div = $("#category_detail_description").parents("div.control-group");
      div.removeClass("success");
      div.addClass("error");
      $("#result_category").html('Fields must be required').show();
      return false;
  }else {
      var div = $("#category_detail_description").parents("div.control-group");
      div.removeClass("error");
      div.addClass("success");
  }
        return  true;
  }
 function clearValidCateogry(){
     var div_name = $("#category_detail_name").parents("div.control-group");
     var div_des = $("#category_detail_description").parents("div.control-group");
     div_name.removeClass("success");
     div_des.removeClass("success");
 }

