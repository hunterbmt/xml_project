function searchProductAtLocal(keyword,page){
    var query = vteam_http.getValue("appendedInputButtons");
    product_search_current_search_function = searchProductAtLocal;
    product_search_current_page = page;
    result = [];
    for(i =0;i < product_list.length;i++){
        if (product_list[i].name.indexOf(query)!= -1 || product_list[i].description.indexOf(query) != -1){
            result.push(product_list[i]);
        }
    }
    if (result.length >= page*page_size){
        product_search_current_page = 1;
        displaySearchProduct(result);
    }
    else{
        searchProduct(page);
    }
}
function searchByCategoryAtLocal(category_id,page){
     result = [];
     product_search_current_search_function = searchByCategoryAtLocal;
     product_search_current_page = page;
     product_search_current_keyword = category_id;
    for(i =0;i < product_list.length;i++){
        if (product_list[i].categoryId===category_id){
            result.push(product_list[i]);
        }
    }
    if (result.length >= page*page_size){
        product_search_current_page = 1;
        displaySearchProduct(result);
    }
    else{
        searchProductByCategory(category_id,page);
    }
}