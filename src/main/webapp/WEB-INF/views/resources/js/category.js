function searchProductByCategory(id,page) {
    vteam_http.show("loading");
    product_search_current_page = page;
    product_search_current_keyword = id;
    product_search_current_search_function = searchProductByCategory;
    vteam_http.makeHttpRequest("/category/searchProductByCategoryId",
            {categoryId: id,page:page},
    'POST',
            function(result) {
                vteam_http.hide("loading");
                if (result.status == 'success') {
                    displaySearchProduct(result.productList);
                }
            });
}