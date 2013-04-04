function searchProductAtLocal(){
    var query = vteam_http.getValue("appendedInputButtons");
    result = [];
    for(i =0;i < product_list.length;i++){
        if (product_list[i].name.indexOf(query)!= -1 || product_list[i].description.indexOf(query) != -1){
            result.push(product_list[i]);
        }
    }
    if (result.length >= page_size){
        product_search_current_page = 1;
        displaySearchProduct(result);
    }
    else{
        searchProduct(1);
    }
}
function searchByCategoryAtLocal(category_id){
     result = [];
    for(i =0;i < product_list.length;i++){
        if (product_list[i].categoryId===category_id){
            result.push(product_list[i]);
        }
    }
    if (result.length >= page_size){
        product_search_current_page = 1;
        displaySearchProduct(result);
    }
    else{
        searchProductByCategory(1,1);
    }
}