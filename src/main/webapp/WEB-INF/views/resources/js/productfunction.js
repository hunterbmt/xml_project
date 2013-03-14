
$(document).ready(function() {
    vteam_http.init();
    vteam_http.makeHttpRequest("/product/getProductList",
            {page: 1, pageSize: 5},
    'POST',
            function(result) {
                if (result.status == 'success') {
                    displayProduct(result.result.productList)
                }
            });
});
function displayProduct(productList) {
    var html = '';
    for (var i = 0; i < productList.length; i++) {
        
        html += '<li class= "span4">'
        html += '<div class= "thumbnail">'
        html += '<img data-src="holder.js/300x200" src="'+productList[i].image+'"/>'
        html += '<div class= "caption">'
        html += '<h6>'
        html += '<a href>'+ productList[i].name + '</a>'
        html += '</h6>'
        html += '<p>' + productList[i].description + '<p>'   
        html += '</div>' 
        html += '</div>'
        html += '</li>'
    }
    $(".thumbnails").html(html)
}


