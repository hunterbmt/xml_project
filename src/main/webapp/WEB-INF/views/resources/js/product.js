var current_page;
var current_page_size;

function loadAndDisplayProduct(page, page_size) {
    current_page = page;
    current_page_size = page_size;
    vteam_http.makeHttpRequest("/product/getProductList",
            {page: page, pageSize: page_size},
    'POST',
            function(result) {
                if (result.status == 'success') {
                    displayProduct(result.product_result.productList);
                }
            });
}

function searchProduct() {
    var input = document.getElementById("appendedInputButtons").value;
    vteam_http.init();
    vteam_http.makeHttpRequest("/product/searchProduct",
            {txtSearch: input},
    'POST',
            function(result) {
                if (result.status == 'success_search') {

                    displaySearchProduct(result.search_result.productList)
                    $('#search_product_list').show();
                }
            });
}

function displayProduct(productList) {
    var html = '';
    for (var i = 0; i < productList.length; i++) {

        html += '<li class= "span4">'

        html += '<div class= "thumbnail">'
        html += '<img data-src="holder.js/320x280" src="' + productList[i].image + '"/>'

        html += '<div class= "caption">'
        html += '<a href="#" onclick ="view_product_detail(' + productList[i].id + ')">'
        html += '<h6>'

        html += productList[i].name
        html += '</h6></a>'
        html += '<p>' + productList[i].description + '<p>'
        html += '</div>'
        html += '</div>'
        html += '</li></a>'
    }

    $("#product_list .thumbnails").html(html)
    hideAllDiv()
    $('#product_list').show();
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function doBid(_id) {
    vteam_http.makeHttpRequest("bid/do_bid", {
        bid_id: _id
    },
    "POST", function(d) {
        if (d.allowed === 'ok') {
            alert('Successful! ' + d.message);
            $("#current_price").html(numberWithCommas(d.price) + " VND");
        } else {
            alert('Unallowed! ' + d.message);
        }

    });
}
function displayProductDetail(product) {
    var html = '';


    html += '<li class= "span4">'

    html += '<div class= "p_detail" style="border:none; margin-top:0px">'

    html += '<img src="' + product.image + '"/>'

    html += '<div id= "right">'

    html += '<div class="title">'
    html += product.name
    html += '</div>'
    html += '<div class="price"><div class="price_view">'
    html += '<div class="v6Price mTop10" align="center">'
    html += '<span id="current_price">$60,000</span></div>'
    html += '<div class="v7inlinetype" align="center">20 nils per bid</div>'
    html += '<div class="v6BuyNow">'
    html += '<a class=" fixPng" href="javascript:doBid(' + product.bid_id + ')"></a></div>'
    html += "</div></div>"

    html += '<div class="v6BorderBot pTop5"><div class="v6Timer">'
    html += '<div class="v6Gray fl">Chỉ còn</div>'
    html += '<div class="v6DisplayTime" id=""><span>3 ngày 20:56’:45”</span></div>'
    html += '</div></div>'

    html += '<div class="v6BorderBot pTop5">'
    html += '<div class="v6Buyersnew pBottom5 mTop5 ">'
    html += 'Top 5 recent bidders<ul class="firstList">'
    html += '<li>Pro1</li>'
    html += '<li>Pro2</li>'
    html += '<li>Pro3</li>'
    html += '</ul></div></div>'
    html += '<div id="product_tags" class="v6BorderBot pTop5" style="border-bottom:none">Tags</div>'
    html += '</div> <div class="c"></div>'
    html += '<div class="v6BorderBot" style="border-top: 1px solid #e0e0e0">'
    html += product.description
    html += '</div>'
    html += '</div>'
    html += '</li>'

    $("#product_detail .p_detail").html(html);
    hideAllDiv()
    $("#product_detail").show()
    loadProductTags(product.id)
}

function view_product_detail(pid) {
    vteam_http.makeHttpRequest("/product/getProductById",
            {product_id: pid},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    displayProductDetail(result.product_detail)
                } else {
                    alert("error");
                }
            });
}

function displaySearchProduct(productList) {
    var html = '';
    for (var i = 0; i < productList.length; i++) {

        html += '<li class= "span4">'

        html += '<div class= "thumbnail">'
        html += '<img data-src="holder.js/320x280" src="' + productList[i].image + '"/>'

        html += '<div class= "caption">'
        html += '<a href="#" onclick ="view_product_detail(' + productList[i].id + ')">'
        html += '<h6>'

        html += productList[i].name
        html += '</h6></a>'
        html += '<p>' + productList[i].description + '<p>'
        html += '</div>'
        html += '</div>'
        html += '</li></a>'
    }
    $("#search_product_list .thumbnails").html(html);
    hideAllDiv();
    $("#search_product_list").show();

}

function searchOnKeyDown(e) {

    if (e.keyCode == 13) {
        e.preventDefault()
        searchProduct();
    }
}

function changeContext() {
    loadAndDisplayProduct(current_page, current_page_size)
}

function hideAllDiv() {
    $("#search_product_list").hide();
    $("#product_detail").hide();
    $("#product_list").hide();
}
function loadProductTags(id) {
    vteam_http.makeHttpRequest("/tags/getTagsByProductId",
            {product_id: id},
    "POST",
            function(result) {
                if (result.status == "success")
                {
                    displayProductTags(result.result.tagsList);
                } else {
                    alert("error");
                }
            });
}
function displayProductTags(tags) {
    html = ""
    html += '<div class="red">'
    html += 'Tags: '
    for (var i = 0; i < tags.length; i++) {
        html += '<span class= "tags">';
        html += '<a href="#" onclick="searchProductByTags(' + tags.id +')">'
        html += '<strong>' + tags[i].tagName + '</strong>'
        html += '</a>' + ' , ';
        html += '</span>';
    }
    html += '</div>'
    $('#product_tags').html(html);
}

function searchProductByTags(tags_id) {
    
    vteam_http.init();
    vteam_http.makeHttpRequest("/product/searchProductByTags",
            {tags_id: tags_id},
    'POST',
            function(result) {
                if (result.status == 'success') {

                    displayProduct(result.search_tags.productList);
                }
            });
}