
function loadAndDisplayCategory() {
    vteam_http.makeHttpRequest("/category/getCategoryList", {},
            'GET',
            function(result) {
                if (result.status == 'success') {
                    displayCategory(result.categoryList)
                }
            });
}
function displayCategory(categoryList) {
    var html = '';
    //html += '<ul class="nav nav-list>'
    html += '<li class = "nav-header" >Category</li>'
    for (var i = 0; i < categoryList.length; i++) {

        html += '   <li> <a href="#" onclick="searchProductByCategory('
        html += categoryList[i].id +',1'
        html += ')" id= "category_id"> <i class = "icon-star-empty" > </i> ' + categoryList[i].name + '</a> </li>'

    }
    //html += '   </ul>'
    $("#side-section > ul").html(html)
}

function searchProductByCategory(id,page) {
    $("#loading").show();
    product_search_current_page = page;
    product_search_current_keyword = id;
    product_search_current_search_function = searchProductByCategory;
    vteam_http.makeHttpRequest("/category/searchProductByCategoryId",
            {category_id: id,page:page,pageSize:page_size},
    'POST',
            function(result) {
                $("#loading").hide();
                if (result.status == 'success') {

                    displaySearchProduct(result.productList);
                }
            });
}




