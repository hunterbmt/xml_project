var current_page;
var current_page_size;


function loadAndDisplayCategory() {
    vteam_http.makeHttpRequest("/category/getCategoryList", {},
            'GET',
            function(result) {
                if (result.status == 'success') {
                    displayCategory(result.result.categoryList)
                    //displaySearchProductByCategoryId(productList)
                }
            });
}
function displayCategory(categoryList) {
    var html = '';
    //html += '<ul class="nav nav-list>'
    html += '<li class = "nav-header" >Category</li>'
    for (var i = 0; i < categoryList.length; i++) {

        html += '   <li> <a href="#" onclick="searchProductByCategory('
        html += categoryList[i].id
        html += ')" id= "category_id"> <i class = "icon-star-empty" > </i> ' + categoryList[i].name + '</a> </li>'

    }
    //html += '   </ul>'
    $("#side-section > ul").html(html)
}

function searchProductByCategory(id) {
    
    vteam_http.init();
    vteam_http.makeHttpRequest("/category/searchProductByCategoryId",
            {category_id: id},
    'POST',
            function(result) {
                if (result.status == 'successSearchProductByCategoryId') {

                    displayProduct(result.searchCategoryResult.productList);
                }
            });
}




