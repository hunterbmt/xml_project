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
function enableSave() {
    vteam_http.removeClass("btnSave",'disabled');
}

function disableSave() {
    vteam_http.addClass("btnSave",'disabled')
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
function displayBidDetails(bid) {
    vteam_http.setHTML("bid_id",bid.id);
    vteam_http.setHTML("bid_last_userid",bid.last_username);
    vteam_http.setValue("bid_product_name",bid.product_name);
    vteam_http.setHTML("bid_current_price",bid.current_price);
    vteam_http.setHTML('bid_last_edit',toDateAndTime2(bid.last_edit));
    vteam_http.setValue('bid_start_date',toDateAndTime2(bid.start_date));
    vteam_http.setValue('bid_end_date',toDateAndTime2(bid.end_date));
    vteam_http.setValue('bid_status',bid.status);
    vteam_http.setValue('bid_cost',bid.cost);
}
function insertOrUpdateBid() {
    var id = $("#bid_id").html();

    var start_date = $("#bid_start_date").val();
    var end_date = $("#bid_end_date").val();
    var cost = $("#bid_cost").val();
    var status = $('#bid_status').val().toUpperCase();
    if ((status !== 'UNCOMPLETED') && (status !== 'COMPLETED'))
    {
        status = 'UNCOMPLETED';
    }
    if (id) { // update
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
    } else { // insert
        var p_id = array_keys[array_values.indexOf($("#bid_product_name").val())];
        vteam_http.makeHttpRequest("/admin/insert_bid",
                {product_id: p_id,
                    start_date: start_date,
                    end_date: end_date,
                    cost: cost},
        'POST', callback);
    }
}
function callback(result) {

    if (result.status === 'success') {
        $('#result_IU_bid').html('Successful !').show();
        $(function() {
            setTimeout(function() {
                $("#result_IU_bid").hide('blind', {}, 400)
            }, 2000);
        });
        clearBidDetail();
        //disableSave();
        update_lists();
        getAllProductNamList();
    } else {
        $('#result_IU_bid').html(result.msg).show();
        $(function() {
            setTimeout(function() {
                $("#result_IU_bid").hide('blind', {}, 400)
            }, 2000);
        });
        //alert("Insert/Update bid result: " + result.status);
    }
}

function update_lists() {
    _displayOngoingBid(bid_ongoing_current_page);
    _displayUpcomingBid(bid_upcoming_current_page);
    _displayCompletedBids(bid_completed_current_page);
}
function clearBidDetail(self) {
    $('#bid_id').html('');
    $('#bid_last_userid').html('');
    $('#bid_product_name').val('');
    $('#bid_current_price').html('');
    $('#bid_last_edit').html('');
    $('#bid_start_date').val('');
    $('#bid_end_date').val('');
    $('#bid_status').val('');
    $('#bid_cost').val('');
    if ($(self).hasClass('newBtn')) {
        enableSave();
        $('#bid_cost').val(1);
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
    $("#bid_product_name").autocomplete({
        source: array_values
    });

}

function _displayOngoingBid(page) {
    bid_ongoing_current_page = page;
    vteam_http.makeHttpRequest(
            "/bid/get_ongoing_bids",
            {
                page: bid_ongoing_current_page,
                pageSize: bid_ongoing_page_size
            },
    "POST",
            function(result) {
                if (result.status === "success") {
                    displayOngoingBid(result.bidList);
                    if (result.numberOfBid > bid_ongoing_page_size) {
                        displayOngoingPagination(bid_ongoing_current_page, result.numberOfBid);
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
//        cnt += '<a href="javascript:bid_remove(';
//        cnt += bidList[i].id + ');" class="btn btn-small"><i class="btn-icon-only icon-remove"></i></a>';
        cnt += "</tr>";
    }
    $("#onGoingBid").html(
            cnt
            );
}
function _displayUpcomingBid(page) {
    bid_upcoming_current_page = page;
    vteam_http.makeHttpRequest(
            "/bid/get_upcoming_bids",
            {
                page: page,
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
    $("#upComingBid").html(cnt);
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
    $("#completedBids").html(cnt);
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
