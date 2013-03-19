/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function insertOrUpdateProduct() {
    var id = $("#product_id").val();
    var product_name = $("#product_name").val();
    var editor = CKEDITOR.instances.product_description;
    var description = escape(editor.getData().toString());
    var min_price = $("#product_min_price").val();
    var max_price = $("#product_max_price").val();
    var img = $("#product_img").val();
    vteam_http.init();
    if (id) {
        vteam_http.makeHttpRequest("/admin/update_product",
                {id: id,
                    productName: product_name,
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
                    img: img,
                    minPrice: parseFloat(min_price),
                    maxPrice: parseFloat(max_price)},
        'POST', callback);
    }
}
function callback(result) {
    alert(result);
    clearProductDetail()
}
function clearProductDetail() {
    $("#product_id").val('');
    $("#product_name").val('');
    var editor = CKEDITOR.instances.product_description;
    editor.setData('');
    $("#product_min_price").val('');
    $("#product_max_price").val('');
    $("#product_img").val('');
}
function getProductDetail(id) {
    vteam_http.makeHttpRequest('/product/getProductById', {product_id: id}, 'POST',
            function(resut) {
                if (result.status == "success") {
                    displayProductDetail(resut.product_detail);
                }
                else {
                    alert(result.status);
                }
            })
}
function displayProductDetail(detail) {
    $("#product_id").val(detail.id);
    $("#product_name").val(detail.name);
    $("#category_name").val(detail.categoryName);

    var editor = CKEDITOR.instances.product_description;
    editor.setData(detail.description);
    $("#product_min_price").val(detail.minPrice);
    $("#product_max_price").val(detail.maxPrice);
    $("#product_img").val(detail.image);
}