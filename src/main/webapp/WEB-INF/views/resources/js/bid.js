/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var bid_completed_current_page = 1;
var bid_completed_page_size = 5;
var bid_ongoing_current_page = 1;
var bid_ongoing_page_size = 5;
var bid_upcoming_current_page = 1;
var bid_upcoming_page_size = 5;

function bid_product_name_onchange() {
    document.getElementById('bid_product_name').onchange = function() {
        enableSave();
        clearBidDetail(this);
        vteam_http.setValue('bid_cost', 1);
    };
}
function enableSave() {
    vteam_http.removeClass("btnSave", 'disabled');
}

function disableSave() {
    vteam_http.addClass("btnSave", 'disabled');
}
function bid_details(bid_id) {
    enableSave();
    vteam_http.makeHttpRequest(
            "/bid/get_bid_by_id",
            {
                _id: bid_id
            },
    "POST",
            function(result) {
                if (result.status === "success") {
                    displayBidDetails(result);
                }
            }
    );
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function displayBidDetails(bid) {
    vteam_http.setHTML("bid_id", bid.id);
    vteam_http.setHTML("bid_last_userid", bid.last_username);
    vteam_http.setValue("bid_product_name", bid.product_name);
    vteam_http.setHTML("bid_current_price", numberWithCommas(bid.current_price) + " VND");
    vteam_http.setHTML('bid_last_edit', toDateAndTime2(bid.last_edit));
    vteam_http.setValue('bid_start_date', toDateAndTime2(bid.start_date));
    vteam_http.setValue('bid_end_date', toDateAndTime2(bid.end_date));
//    vteam_http.setValue('bid_status',bid.status);
    $('#bid_status').val(bid.status);
    vteam_http.setValue('bid_cost', bid.cost);
}
function insertOrUpdateBid() {
    var id = vteam_http.getHTML("bid_id");
    var product_name = vteam_http.getValue("bid_product_name");
    var start_date = vteam_http.getValue("bid_start_date");
    var end_date = vteam_http.getValue("bid_end_date");
    var cost = vteam_http.getValue("bid_cost");
//    var status = vteam_http.getValue('bid_status').toUpperCase();
    var status = $('#bid_status').val();
    if (id) { // update
        //check for valid fields
        if (validBid(product_name, start_date, end_date, cost)) {
            var p_id = all_keys[all_values.indexOf($("#bid_product_name").val())];
            vteam_http.makeHttpRequest("/admin/update_bid",
                    {bid_id: id,
                        product_id: p_id,
                        start_date: start_date,
                        end_date: end_date,
                        status: status,
                        cost: cost
                    },
            'POST', callback);
        }
        clearValidBid();
    } else { // insert
        if (validBid(product_name, start_date, end_date, cost)) {
            var p_id = array_keys[array_values.indexOf($("#bid_product_name").val())];
            vteam_http.makeHttpRequest("/admin/insert_bid",
                    {product_id: p_id,
                        start_date: start_date,
                        end_date: end_date,
                        cost: cost},
            'POST', callback);
        }
        clearValidBid();
        
    }
    
}

function refresh(timeout) {
    refresh = setTimeout(function() {
        window.location.reload(true);
    }, timeout);
}
var msg = "Thành công!";
function callback(result) {
    if (result.status === 'success') {
       // msg = "Thành công !";
        clearBidDetail();
        update_lists();
        getAllProductNamList();
    } else {
        msg = result.msg;        
    }
    refresh(100);
}

function update_lists() {
    //_displayOngoingBid(bid_ongoing_current_page);
    //_displayUpcomingBid(bid_upcoming_current_page);
    _displayCompletedBids(bid_completed_current_page);
}
function clearBidDetail(self) {
    vteam_http.setHTML("bid_id", '');
    vteam_http.setHTML("bid_last_userid", '');
    vteam_http.setValue('bid_product_name', '');
    vteam_http.setHTML('bid_current_price', '');
    vteam_http.setHTML('bid_last_edit', '');
    vteam_http.setValue('bid_start_date', '');
    vteam_http.setValue('bid_end_date', '');
//    vteam_http.setValue('bid_status', '');
    $('#bid_status').val('uncompleted');
    vteam_http.setValue('bid_cost', '');
    if ($(self).hasClass('newBtn')) {
        enableSave();
        vteam_http.setValue('bid_cost', 1);
    }
}

function populateProductNameList() {
    vteam_http.makeHttpRequest("/admin/getProductNameList",
            {
            },
            'POST', populateList);
    getAllProductNamList();
}

function getAllProductNamList() {
    vteam_http.makeHttpRequest("/admin/getAllProductNameList",
            {
            },
            'POST', getAllProductNameList);
}
var all_keys;
var all_values;
function getAllProductNameList(list) {
    all_keys = new Array();
    all_values = new Array();
    for (var key in list) {
        all_keys.push(key);
        all_values.push(list[key]);
    }
}

var array_keys;
var array_values;
function populateList(list) {
    array_keys = new Array();
    array_values = new Array();
    for (var key in list) {
        array_keys.push(key);
        array_values.push(list[key]);
    }
    if (array_values.length > 0) {
        $("#bid_product_name").autocomplete({
            source: array_values
        });
    } else {
        var newProductButton = '<br/><a class="btn-small admin_bid_new_product" href="product">Thêm sản phẩm</a>';
        newProductButton += '<span style="margin-left:25%; font-size:0.9em">Hết sản phẩm!</span>';
        $("#bid_product_name").after(newProductButton);
    }

}

function _displayOngoingBid(page) {
    bid_ongoing_current_page = page;
    vteam_http.makeHttpRequest(
            "/bid/get_ongoing_bids",
            {
                page: page,
                pageSize: bid_ongoing_page_size
            },
    "POST",
            function(result) {
                if (result.status === "success") {
                    displayOngoingBid(result.bidList);
                    if (result.numberOfBid > bid_ongoing_page_size) {
                        displayOngoingPagination(page, result.numberOfBid);
                    }
                }
            }
    );
}
function displayOngoingPagination(currentPage, total_number) {
    $("#ongoing_pagination_bar").pagination({
        items: total_number,
        itemsOnPage: bid_ongoing_page_size,
        currentPage: currentPage,
        cssStyle: 'light-theme',
        onPageClick: function(pageNumber, event) {
            _displayOngoingBid(pageNumber);
        }
    });
}
function displayOngoingBid(bidList) {
    var cnt = '';
    for (var i = 0; i < bidList.length; i++) {
        cnt += "<tr>";
        cnt += "<td>" + bidList[i].id + "</td>";
        cnt += "<td>" + bidList[i].product_name + "</td>";
        cnt += "<td>" + toDate(bidList[i].start_date) + "</td>";
        cnt += "<td>" + bidList[i].last_username + "</td>";
        cnt += "<td>" + bidList[i].current_price + "</td>";
        cnt += "<td>" + toDateAndTime(bidList[i].last_edit) + "</td>";
        cnt += '<td class="td-actions">';
        cnt += '<a href="javascript:bid_details(';
        cnt += bidList[i].id + ');" class="btn btn-warning btn-small"><i class="btn-icon-only icon-edit"></i></a>';
        cnt += "</tr>";
    }
    vteam_http.setHTML('onGoingBid', cnt);
}
function _displayUpcomingBid(page) {
    bid_upcoming_current_page = page;
    vteam_http.makeHttpRequest(
            "/bid/get_upcoming_bids",
            {
                page: bid_upcoming_current_page,
                pageSize: bid_upcoming_page_size
            },
    "POST",
            function(result) {
                if (result.status === "success") {
                    displayUpcomingBid(result.bidList);
                    if (result.numberOfBid > bid_upcoming_page_size) {
                        displayUpcomingPagination(bid_upcoming_current_page, result.numberOfBid);
                    }
                }
            }
    );
}
function displayUpcomingPagination(currentPage, total_number) {
    $("#upcoming_pagination_bar").pagination({
        items: total_number,
        itemsOnPage: bid_upcoming_page_size,
        currentPage: currentPage,
        cssStyle: 'light-theme',
        onPageClick: function(pageNumber, event) {
            _displayUpcomingBid(pageNumber);
        }
    });
}

function displayUpcomingBid(bidList) {
    var cnt = '';
    for (var i = 0; i < bidList.length; i++) {
        cnt += "<tr>";
        cnt += "<td>" + bidList[i].id + "</td>";
        cnt += "<td>" + bidList[i].product_name + "</td>";
        cnt += "<td>" + toDateAndTime(bidList[i].start_date) + "</td>";
        cnt += '<td class="td-actions">';
        cnt += '<a href="javascript:bid_details(';
        cnt += bidList[i].id + ');" class="btn btn-warning btn-small"><i class="btn-icon-only icon-edit"></i></a>';
        cnt += '<a href="javascript:bid_remove(';
        cnt += bidList[i].id + ');" class="btn btn-small"><i class="btn-icon-only icon-remove"></i></a>';
        cnt += "</tr>";
    }
    vteam_http.setHTML('upComingBid', cnt);
}

function displayCompletedBids(bidList) {
    var cnt = '';
    for (var i = 0; i < bidList.length; i++) {
        cnt += "<tr>";
        cnt += "<td>" + bidList[i].id + "</td>";
        cnt += "<td>" + bidList[i].product_name + "</td>";
        cnt += "<td>" + toDate(bidList[i].start_date) + "</td>";
        cnt += "<td>" + bidList[i].last_username + "</td>";
        cnt += "<td>" + bidList[i].current_price + "</td>";
        cnt += "<td>" + toDateAndTime(bidList[i].last_edit) + "</td>";
        cnt += "</tr>";
    }
    vteam_http.setHTML('completedBids', cnt);
}

function _displayCompletedBids(page) {
    bid_completed_current_page = page;
    vteam_http.makeHttpRequest(
            "/bid/get_completed_bids",
            {
                page: page,
                pageSize: bid_completed_page_size
            },
    "POST",
            function(result) {
                if (result.status === "success") {
                    displayCompletedBids(result.bidList);
                    if (result.numberOfBid > bid_completed_page_size) {
                        displayCompletedPagination(bid_completed_current_page, result.numberOfBid);
                    }
                }
            }
    );
}
function displayCompletedPagination(currentPage, total_number) {
    $("#completed_pagination_bar").pagination({
        items: total_number,
        itemsOnPage: bid_completed_page_size,
        currentPage: currentPage,
        cssStyle: 'light-theme',
        onPageClick: function(pageNumber, event) {
            _displayCompletedBids(pageNumber);
        }
    });
}

function validBid(name, startdate, enddate, cost)
{

    if (!name) {
        var div = $("#bid_product_name").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_IU_bid").html('"Product name" is required').show();
        $("#bid_product_name").focus();
        return false;
    } else {
        var div = $("#bid_product_name").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");

    }
    if (!startdate) {
        var div = $("#bid_start_date").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_IU_bid").html('"Start date" is required').show();
        $("#bid_start_date").focus();
        return false;
    } else {
        var div = $("#bid_start_date").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");

    }
    if (!enddate) {
        var div = $("#bid_end_date").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_IU_bid").html('"End date" is required').show();
        $("#bid_end_date").focus();
        return false;
    } else if (enddate < startdate) {
        var div = $("#bid_end_date").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_IU_bid").html('"End date" must > "Start date"').show();
        $("#bid_end_date").focus();
        return false;
    } else {
        var div = $("#bid_end_date").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");

    }
    if (!cost) {
        var div = $("#bid_cost").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_IU_bid").html('"Cost" is required').show();
        $("#bid_cost").focus();
        return false;
    } else {
        var div = $("#bid_cost").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");

    }

    return true;
}
function clearValidBid() {
    var div_pname = $("#bid_product_name").parents("div.control-group");
    var div_start = $("#bid_start_date").parents("div.control-group");
    var div_end = $("#bid_end_date").parents("div.control-group");
    var div_cost = $("#bid_cost").parents("div.control-group");

    div_pname.removeClass("success");
    div_start.removeClass("success");
    div_end.removeClass("success");
    div_cost.removeClass("success");
}