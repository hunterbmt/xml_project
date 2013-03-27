var product_list_current_page;
var product_search_current_keyword;
var product_search_current_page;
var product_search_current_search_function;
var page_size = 9;
var divArray = ["#product_list", "#search_product_list", "#product_detail"];
var currentPosition = 0;
$(window).scroll(function() {
    if ($(window).scrollTop() == $(document).height() - $(window).height()) {
        if (currentPosition == 0) {
            loadAndDisplayProduct(product_list_current_page + 1);
        } else if (currentPosition == 1) {
            product_search_current_search_function(product_search_current_keyword, product_search_current_page + q);
        }
    }
});

function loadAndDisplayProduct(page) {
    product_list_current_page = page;
    vteam_http.makeHttpRequest("/product/getProductList",
            {page: page, pageSize: page_size},
    'POST',
            function(result) {
                $("#loading").hide();
                if (result.status == 'success') {
                    displayProduct(result.productList);
                }
            });
}

function searchProduct(page) {
    $("#loading").show();
    var input = document.getElementById("appendedInputButtons").value;
    product_search_current_keyword = input;
    product_search_current_page = page;
    product_search_current_search_function = searchProduct;
    vteam_http.makeHttpRequest("/product/searchProduct",
            {txtSearch: input, page: page, pageSize: page_size},
    'POST',
            function(result) {
                $("#loading").hide();
                if (result.status == 'success') {

                    displaySearchProduct(result.productList)
                }
            });
}

function displayProduct(productList) {
    currentPosition = 0;
    var html = '';
    for (var i = 0; i < productList.length; i++) {

        html += '<div class= "bid span4" style="height: 400px">'

        html += '<div class= "bidHolder">'
        html +='<a style ="margin-left: 14%;"href="javascript:void(0)" class="bidImage imgLink" onclick ="view_product_detail(' + productList[i].id + ')">'
        html += '<img src="' + productList[i].image + '" style="height:286px"/>'
        html +='</a>'
        html += '<div class= "convo attribution clearfix">'
        html += '<a href="javascript:void(0)" onclick ="view_product_detail(' + productList[i].id + ')">'
        html += '<h5>' + productList[i].name + '</h5>'
        html += '</a>'
        html += '<p>' + productList[i].shortDescription + '<p>'
        html += '</div>'
        html += '</div>'
        html += '</div>';
    }

    $(html).appendTo('#product_list');
    hideAllDiv()
    $('#product_list').show();
    currentDiv = "product_list";
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
            alert(d.message);
            $("#current_price").html(numberWithCommas(d.price) + " VND");
            isInBidTime = true;
            startBuyingNow(d.bidId);
        } else {
            alert(d.message);
        }

    });
}

function doBuy(_id) {
    vteam_http.makeHttpRequest("bid/do_buy", {
        bid_id: _id
    },
    "POST", function(d) {
        if (d.allowed === 'ok') {
            alert(d.message);

        } else {
            alert(d.message);
        }

    });
}
var cc;
var bcount = 0;
function startBuyingNow(bidId) {
    bcount = 24 * 1000;  // x seconds to expire

    $('#bidButton').removeClass('v6BuyNow');
    $('#bidButton').addClass('v6Buy');
    var buy_link = '<a href="javascript:doBuy(' + bidId + ')"><span id="buyNowLeft"></span></a>';
    $('#bidButton').html(buy_link);

    cc = setInterval(buyCountDown, 1000);
}

function buyCountDown() {
    if (bcount <= 0) {
        clearInterval(cc);
        isInBidTime = false;
        //alert(bcount);
        $('#buyNowLeft').html('');
        $('#bidButton').removeClass('v6Buy');
        $('#bidButton').addClass('v6BuyNow');
        return;
    }
    $("#buyNowLeft").html((bcount) / 1000);
    bcount -= 1000;
}

var count = 9999;
var c;

function displayCounter() {
    count = count - 1000;
    if (count <= 0)
    {
        clearInterval(c);
        $('#timer').html("Sản phẩm có thể bid");
        return;
    }
    $("#timer").html(toHMS(count));
    //alert(count);
}

function toHMS(diff) {
    if (diff < 0)
        return 0;
    var diffSeconds = diff / 1000 % 60;
    var diffMinutes = diff / (60 * 1000) % 60;
    var diffHours = diff / (60 * 60 * 1000);
    return "<span class='clockNumber'>" + Number(diffHours).toFixed(0) + "</span> Giờ "
            + "<span class='clockNumber'>" + Number(diffMinutes).toFixed(0) + "</span> Phút "
            + "<span class='clockNumber'>" + Number(diffSeconds).toFixed(0) + "</span> Giây";
}

var isInBidTime = false;
function displayProductDetail(product) {
    currentPosition = 2;
    clearInterval(c);
    var html = '';
    count = product.bidTimeRemain;
    c = setInterval(displayCounter, 1000);
    html += '<div class="row-fluid">'

    html += '<div class= "p_detail" style="border:none; margin-top:0px">'
    
    html +='<div class="product-detail-img">'
    html += '<img src="' + product.image + '"/>'
    html +='</div>'
    html += '<div id= "right">'

    html += '<div class="title">'
    html += product.name
    html += '</div>'
    html += '<div class="price"><div class="price_view">'
    html += '<div class="v6Price mTop10" align="center">'
    html += '<span id="current_price">??,??? VND</span></div>'
    html += '<div class="v7inlinetype" align="center"><span id="bid_cost">' + product.bidCost + '</span> Nils/bid</div>'
    html += '<div id="bidButton" class="v6BuyNow">'
    html += '<a class=" fixPng" href="javascript:doBid(' + product.bidId + ')"><span id="buyNowLeft"></span></a></div>'
    html += "</div></div>"

    html += '<div class="v6BorderBot pTop5"><div class="v6Timer">'
    html += '<div class="v6Gray fl"></div>'
    html += '<div class="v6DisplayTime" id="timer">' + (toHMS(product.bidTimeRemain) == 0 ? "Sản phẩm có thể bid" : toHMS(product.bidTimeRemain)) + '</span></div>'
    html += '</div></div>'

    html += '<div class="v6BorderBot pTop5">'
    html += '<div class="v6Buyersnew pBottom5 mTop5 ">'
    html += 'Top recent bidders<ul id="topBidders" class="firstList">'
    html += '</ul></div></div>'
    html += '<div id="product_tags" class="v6BorderBot pTop5" style="border-bottom:none"></div>'
    html += '</div> <div class="c"></div>'
    html += '<div class="v6BorderBot productDes" style="border-top: 1px solid #e0e0e0">'
    html += product.description
    html += '</div>'
    html += '</div>'
    html += '</div>'

    $("#product_detail").html(html);
    hideAllDiv();
    $("#product_detail").show();
    loadProductTags(product.id);
    getRecentBidder(product.bidId);

    if (product.bidTimeRemain > 0) {
        $('#bidButton').removeClass("v6BuyNow");
        $('#bidButton').addClass("v6BidAvoid");
        $('#bidButton').html('');
    }
}

function getRecentBidder(bidId) {
    vteam_http.makeHttpRequest("/product/getRecentBidder",
            {bid_id: bidId},
    "POST",
            function(result) {
                displayRecentBidder(result);
            });
}

function displayRecentBidder(rs) {
    var body = "";
    for (var i = 0; i < rs.length; i++) {
        body += '<li>';
        body += rs[i];
        body += '</li>';
    }

    $('#topBidders').html(body);

}

function view_product_detail(pid) {
    $("#loading").show();
    vteam_http.makeHttpRequest("/product/getProductById",
            {product_id: pid},
    "POST",
            function(result) {
                $("#loading").hide();
                if (result.status == "success")
                {
                    displayProductDetail(result)
                } else {
                    alert("error");
                }
            });
}

function displaySearchProduct(productList) {
    currentPosition = 1;
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
        html += '<p>' + productList[i].shortDescription + '<p>'
        html += '</div>'
        html += '</div>'
        html += '</li></a>'
    }
    $("#search_product_list").html(html);
    hideAllDiv();
    $("#search_product_list").show("slide", { direction: "left" },1000);
}

function searchOnKeyDown(e) {

    if (e.keyCode == 13) {
        e.preventDefault()
        searchProduct(1);
    }
}

function changeContext() {
    $('#product_list').html('');
    loadAndDisplayProduct(1);
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
                    displayProductTags(result.tagsList);
                } else {
                    alert("error");
                }
            });
}
function displayProductTags(tags) {
    html = ""
    html += '<div class="red">'
    html += '<i class="icon-tags"/> '
    for (var i = 0; i < tags.length; i++) {
        html += '<span class= "tags">';
        html += '<a id="tags_product" href="#" onclick="searchProductByTags(' + tags[i].tagId + ',1)">'
        html += '<strong>' + tags[i].tagName + '</strong>'
        html += '</a>' + ' ';
        html += '</span>';
    }
    html += '</div>'
    $('#product_tags').html(html);
}

function searchProductByTags(tags_id, page) {
    $("#loading").show();
    product_search_current_keyword = tags_id;
    product_search_current_page = page;
    product_search_current_search_function = searchProductByTags;
    vteam_http.init();
    vteam_http.makeHttpRequest("/product/searchProductByTags",
            {tag_id: tags_id, page: page, pageSize: page_size},
    'POST',
            function(result) {
                $("#loading").hide();
                if (result.status == 'success') {
                    displaySearchProduct(result.productList);
                }
            });
}
function backOnClick() {
    if (currentPosition > 0) {
        currentPosition = currentPosition - 1;
        hideAllDiv();
        $(divArray[currentPosition]).show();
        generateBackAndNext()
    }
}
function nextOnClick() {
    if (currentPosition < 2) {
        currentPosition = currentPosition + 1;
        hideAllDiv();
        $(divArray[currentPosition]).show();
        generateBackAndNext()
    }
}
function generateBackAndNext(){
    if(currentPosition = 0){
        // hide back button;
    }
    if(currentPosition =2){
        //hide next button
    }
}