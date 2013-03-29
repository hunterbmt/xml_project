function searchProductAtLocal(){
    var query = vteam_http.getValue("appendedInputButtons");
    result = [];
    for(i =0;i < product_list.length;i++){
        if (product_list[i].name.indexOf(query)!= -1 || product_list[i].description.indexOf(query) != -1){
            result.push(product_list[i]);
        }
    }
    if (result.length >= 9){
        displaySearchProduct(result);
    }
    else{
        searchProduct(1);
    }
}

