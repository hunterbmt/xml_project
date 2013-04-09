var product_list_current_page;
var product_search_current_keyword;
var product_search_current_page;
var product_search_current_search_function;
var page_size = 10;
var divArray = [];
var currentPosition = -1;
var product_list = [];
var product_list_page = 0;
$(window).scroll(function() {
    if ($(window).scrollTop() == $(document).height() - $(window).height()) {
        if (divArray[currentPosition] == "product_list") {
            if (product_list_current_page >= product_list_page) {
                loadAndDisplayProduct(product_list_current_page + 1);
            } else {
                product_list_current_page = product_list_current_page + 1;
                displayProduct(product_list);
            }
        } else if (divArray[currentPosition] == "search_product_list") {
            product_search_current_search_function(product_search_current_keyword, product_search_current_page + 1);
        }
    }
});

function loadAndDisplayProduct(page) {
    vteam_http.makeHttpRequest("/product/getProductList",
            {page: page},
    'POST',
            function(result) {
                vteam_http.hide("loading");
                if (result.status === 'success') {
                    product_list_page += Math.ceil(result.numberOfRecord / page_size);
                    product_list = product_list.concat(result.productList);
                    product_list_current_page = page;
                    displayProduct(result.productList);
                }
            });
}

function displayProduct(productList) {
    if (product_list_current_page == 1) {
        vteam_http.setHTML("product_list", "");
        currentPosition += 1;
        divArray[currentPosition] = "product_list";
        generateBackAndNext();
    }
    var html = '';
    var ts = 0;
    var bid_type = "";
    var i = (product_list_current_page - 1) * page_size;
    var pageSize = i + page_size;
    var end_ts = 0;
    if (pageSize > productList.length) {
        pageSize = productList.length;
    }

    for (; i < pageSize; i++) {
        ts = productList[i].bidTimeRemain;
        if (ts <= 0) // in bid 
        {
            end_ts = productList[i].bidEndTimeRemain;
            bid_type = "<div class='onBidType'><div class='endTimeRemain'></div></div>";
        } else {
            bid_type = "<div class='upComingBidType'></div>";
        }
        html += '<div class= "bid span4" style="height: 400px">'

        html += '<div class= "bidHolder">'
        html += bid_type;
        html += '<a style ="margin-left: 14%;"href="javascript:void(0)" class="bidImage imgLink" onclick ="view_product_detail(' + productList[i].id + ')">'
        html += '<img src="' + productList[i].image + '" style="height:286px"/>'
        html += '</a>'
        html += '<div class= "convo attribution clearfix">'
        html += '<a href="javascript:void(0)" onclick ="view_product_detail(' + productList[i].id + ')">'
        html += '<h5>' + productList[i].name + '</h5>'
        html += '</a>'
        html += '<p>' + productList[i].shortDescription + '<p>'
        html += '</div>'
        html += '</div>'
        html += '</div>'
    }
    vteam_http.appendTo("product_list", html);
    hideAllDiv();
    vteam_http.show("product_list");

}
function searchProduct(page) {
    vteam_http.show('loading');
    var input = vteam_http.getValue("appendedInputButtons");
    product_search_current_keyword = input;
    product_search_current_page = page;
    product_search_current_search_function = searchProduct;
    vteam_http.makeHttpRequest("/product/searchProduct",
            {txtSearch: input, page: page},
    'POST',
            function(result) {
                vteam_http.hide('loading');
                if (result.status === 'success') {
                    displaySearchProduct(result.productList);
                }
            });
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
            vteam_http.setHTML("current_price", numberWithCommas(d.price) + " VND");
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
        clearInterval(cc);
        $('#buyNowLeft').html('');
        $('#bidButton').html('<font color="white" size="4.5em"><center>Chúc mừng!<br/><font size="2.5em"><strong>Sản phẩm đã thuộc về bạn</strong></font></center></font>');
        $('#bidButton').removeClass('v6Buy');
        $('#bidButton').addClass('v6Bought');
    });
}
var cc;
var bcount = 0;
var bid_button_content = "";
function startBuyingNow(bidId) {
    bcount = 24 * 1000;  // x seconds to expire
    vteam_http.removeClass("bidButton", "v6BuyNow");
    vteam_http.addClass("bidButton", "v6Buy");
    bid_button_content = vteam_http.getHTML("bidButton");
    var buy_link = '<a href="javascript:doBuy(' + bidId + ')"><span id="buyNowLeft"></span></a>';
    vteam_http.setHTML("bidButton", buy_link);
    cc = setInterval(buyCountDown, 1000);
    isInBidTime = true;
}

function buyCountDown() {
    if (bcount <= 0 || !(isInBidTime)) {
        clearInterval(cc);
        isInBidTime = false;
        vteam_http.setHTML("buyNowLeft", '');
        vteam_http.removeClass("bidButton", "v6Buy");
        vteam_http.addClass("bidButton", "v6BuyNow");
        vteam_http.setHTML("bidButton", bid_button_content);
        return;
    }
    vteam_http.setHTML("buyNowLeft", (bcount / 1000));
    bcount -= 1000;
}

var count = 9999;
var c;

function displayCounter() {
    count = count - 1000;
    if (count <= 0)
    {
        clearInterval(c);
        if (ec_count > 0) {
            vteam_http.setHTML("timer", "Sản phẩm có thể bid");
        }
        return;
    }
    vteam_http.setHTML("timer", toHMS(count, false));
}
function displayEndCounter() {
    ec_count -= 1000;
    if (ec_count <= 0)
    {
        clearInterval(ec);
        vteam_http.setHTML("timer", '');
        vteam_http.setHTML("bidButton","<font color='white'>Sản phẩm này đã hết thời gian bid!</font>");
        vteam_http.makeHttpRequest(
            "/admin/checkExpiredBids",{},"POST");
        setInterval(reload, 2000);
        return;
    }
    
    vteam_http.setHTML("timer", toHMS(ec_count, true));
}

function reload() {
    document.location.reload();
}
function toHMS(diff, isEndTime) {
    if (diff < 0)
        return 0;
    var diffSeconds = diff / 1000 % 60;
    var diffMinutes = Math.ceil(diff / (60 * 1000) % 60) - 1;
    var diffHours = diff / (60 * 60 * 1000);
    var returnHTML;
         
    if (isEndTime) {
        returnHTML   = "Còn <span class='clockNumber' style='font-size:1.7em'>" + Number(diffHours).toFixed(0) + "</span> Giờ "
            + "<span class='clockNumber' style='font-size:1.5em'>" + Number(diffMinutes).toFixed(0) + "</span> Phút "
            + "<span class='clockNumber' style='font-size:1.7em'>" + Number(diffSeconds).toFixed(0) + "</span>s<br/>";
    } else {
        returnHTML   = "<span class='clockNumber' >" + Number(diffHours).toFixed(0) + "</span> Giờ "
            + "<span class='clockNumber'>" + Number(diffMinutes).toFixed(0) + "</span> Phút "
            + "<span class='clockNumber'>" + Number(diffSeconds).toFixed(0) + "</span> Giây";
    }
    return returnHTML;
}

var isInBidTime = false;
function resetBiddingFlag() {
    isInBidTime = false;
}
var ec;
var ec_count = 0;
function displayProductDetail(product) {
    clearInterval(ec);
    clearInterval(c);
    var html = '';
    count = product.bidTimeRemain;
    ec_count = product.bidEndTimeRemain;
    c = setInterval(displayCounter, 1000);


    html += '<div class="row-fluid">'
    html += '<div class= "p_detail" style="border:none;">'
    html += '<div class="product-detail-img">'
    html += '<img src="' + product.image + '"/>'
    html += '</div>'
    html += '<div id= "right">'
    html += '<div class="title">'
    html += product.name
    html += '</div>'
    html += '<div class="price"><div class="price_view">'
    html += '<div class="v6Price mTop10" align="center">'
    html += '<span id="current_price">??,??? VND</span></div>'
    html += '<div class="v7inlinetype" align="center"><span id="bid_cost">' + product.bidCost + '</span> Nils/bid</div>'

    if (ec_count > 0) // end_time not meet
    {
        html += '<div id="bidButton" class="v6BuyNow">'
        html += '<a class=" fixPng" href="javascript:doBid(' + product.bidId + ')"><span id="buyNowLeft"></span></a></div>'
        html += "</div></div>"
        html += '<div class="v6BorderBot pTop5"><div class="v6Timer">'
        html += '<div class="v6Gray fl"></div>'
        html += '<div class="v6DisplayTime" id="timer">' + (toHMS(product.bidTimeRemain) == 0 ? "Sản phẩm có thể bid" : toHMS(product.bidTimeRemain))
        html += '</div>'
        if (count < 0) //in bid
        {
            ec = setInterval(displayEndCounter, 1000);
        }

    } else {

        html += '<div class="v6DisplayTime" id="timer" style="padding-left:20px"><font color="white">Sản phẩm này đã hết thời gian bid!</font></div>'

    }

    html += '</div></div>'
    html += '<div class="v6BorderBot pTop5">'
    html += '<div class="v6Buyersnew pBottom5 mTop5 ">'
    html += 'Top recent bidders<ul id="topBidders" class="firstList">'
    html += '</ul></div></div>'
    html += '<div id="product_tags" class="v6BorderBot pTop5" style="border-bottom:none"></div>'
    html += '</div> <div class="c"></div>'
    html += '<div class=" productDes" style="border-top: 1px solid #e0e0e0">'
    html += product.description
    html += '</div>'
    html += '</div>'
    html += '</div>'

    vteam_http.setHTML("product_detail", html);
    hideAllDiv();
    vteam_http.show("product_detail");
    currentPosition += 1;
    divArray[currentPosition] = "product_detail";
    generateBackAndNext()
    loadProductTags(product.id);
    getRecentBidder(product.bidId);

    if (product.bidTimeRemain > 0) {
        vteam_http.removeClass("bidButton", "v6BuyNow");
        vteam_http.addClass("bidButton", "v6BidAvoid");
        vteam_http.setHTML("bidButton", '');
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
    vteam_http.setHTML("topBidders", body);
}

function view_product_detail(pid) {
    vteam_http.show("loading");
    vteam_http.makeHttpRequest("/product/getProductById",
            {product_id: pid},
    "POST",
            function(result) {
                vteam_http.hide("loading");
                if (result.status === "success")
                {
                    displayProductDetail(result);
                } else {
                    alert("error");
                }
            });
}

function displaySearchProduct(productList) {
    if (product_search_current_page == 1) {
        vteam_http.setHTML("search_product_list", "");
        currentPosition += 1;
        divArray[currentPosition] = "search_product_list";
        generateBackAndNext()
    }
    var html = '';
    var i = (product_list_current_page - 1) * page_size;
    var pageSize = i + page_size;
    if (pageSize > productList.length) {
        pageSize = productList.length;
    }
    for (; i < pageSize; i++) {
        ts = productList[i].bidTimeRemain;
        if (ts <= 0) // in bid 
        {
            bid_type = "<div class='onBidType'></div>";
        } else {
            bid_type = "<div class='upComingBidType'></div>";
        }
        html += '<div class= "bid span4" style="height: 400px">'

        html += '<div class= "bidHolder">'
        html += bid_type;
        html += '<a style ="margin-left: 14%;"href="javascript:void(0)" class="bidImage imgLink" onclick ="view_product_detail(' + productList[i].id + ')">'
        html += '<img src="' + productList[i].image + '" style="height:286px"/>'
        html += '</a>'
        html += '<div class= "convo attribution clearfix">'
        html += '<a href="javascript:void(0)" onclick ="view_product_detail(' + productList[i].id + ')">'
        html += '<h5>' + productList[i].name + '</h5>'
        html += '</a>'
        html += '<p>' + productList[i].shortDescription + '<p>'
        html += '</div>'
        html += '</div>'
        html += '</div>'
    }
    vteam_http.appendTo("search_product_list", html);
    hideAllDiv();
    vteam_http.show("search_product_list");
}

function searchOnKeyDown(e) {

    if (e.keyCode === 13) {
        e.preventDefault();
        searchProductAtLocal('', 1);
    }
}

function changeContext() {
    currentPosition = -1;
    displayProduct(product_list);
    resetBiddingFlag();
}

function hideAllDiv() {
    vteam_http.hide("search_product_list");
    vteam_http.hide("product_detail");
    vteam_http.hide("product_list");
    vteam_http.hide("user_login");
    vteam_http.hide("user_detail");
    resetBiddingFlag();
}
function loadProductTags(id) {
    vteam_http.makeHttpRequest("/tags/getTagsByProductId",
            {product_id: id},
    "POST",
            function(result) {
                if (result.status === "success")
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
    html += '</div>';
    vteam_http.setHTML("product_tags", html);
}

function searchProductByTags(tags_id, page) {
    vteam_http.show("loading");
    product_search_current_keyword = tags_id;
    product_search_current_page = page;
    product_search_current_search_function = searchProductByTags;
    vteam_http.makeHttpRequest("/product/searchProductByTags",
            {tag_id: tags_id, page: page, pageSize: page_size},
    'POST',
            function(result) {
                $("#loading").hide();
                vteam_http.hide("loading");
                if (result.status === 'success') {
                    displaySearchProduct(result.productList);
                }
            });
}
function backOnClick() {
    if (currentPosition > 0) {
        currentPosition = currentPosition - 1;
        hideAllDiv();
        vteam_http.show(divArray[currentPosition]);
        generateBackAndNext()
    }
}
function nextOnClick() {
    if (currentPosition < divArray.length) {
        currentPosition = currentPosition + 1;
        hideAllDiv();
        vteam_http.show(divArray[currentPosition]);
        generateBackAndNext()
    }
}
function generateBackAndNext() {
    if (currentPosition == 0) {
        // hide back button;
        $("#back").hide();
        $("#next").hide();
    }
    else {
        $("#back").show()
    }
    if (currentPosition == (divArray.length - 1)) {
        $("#next").hide();
    } else {
        $("#next").show();

    }

}

