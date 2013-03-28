
function loadAndDisplayCategory() {
    vteam_http.makeHttpRequest("/category/getCategoryList", {},
            'GET',
            function(result) {
                if (result.status === 'success') {
                    displayCategory(result.categoryList)
                }
            });
}
function displayCategory(categoryList) {
    var html = '';
    //html += '<ul class="nav nav-list>'
    for (var i = 0; i < categoryList.length; i++) {

        html += '   <li class="submenu"> <a class="nav" href="#" onclick="searchProductByCategory('
        html += categoryList[i].id +',1'
        html += ')" id= "category_id">' + categoryList[i].name + '</a> </li>'

    }
    vteam_http.setHTML("CategoriesBar_ul",html);
}

function searchProductByCategory(id,page) {
    vteam_http.show("loading");
    product_search_current_page = page;
    product_search_current_keyword = id;
    product_search_current_search_function = searchProductByCategory;
    vteam_http.makeHttpRequest("/category/searchProductByCategoryId",
            {category_id: id,page:page,pageSize:page_size},
    'POST',
            function(result) {
                vteam_http.hide("loading");
                if (result.status == 'success') {

                    displaySearchProduct(result.productList);
                }
            });
}