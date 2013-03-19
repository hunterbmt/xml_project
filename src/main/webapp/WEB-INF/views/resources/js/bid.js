/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function enableSave() {
    var saveBtn = $('#btnSave');
    if (saveBtn.hasClass('disabled')) {
        saveBtn.removeClass('disabled');
    }
}

function disableSave() {
    var saveBtn = $('#btnSave');
    if (!saveBtn.hasClass('disabled')) {
        saveBtn.addClass('disabled');
    }
}
function bid_details(bid_id) {
    enableSave();
    vteam_http.makeHttpRequest(
            "/bid/get_bid_by_id",
            {
                _id: bid_id
            },
    "POST",
            function(data) {
                if (data.status === "success") {
                    displayBidDetails(data.bid);
                }
            }
    );
}
function displayBidDetails(bid) {
    $('#bid_id').html(bid.id);
    $('#bid_last_userid').html(bid.last_username);
    $('#bid_product_name').val(bid.product_name);
    $('#bid_current_price').html(bid.current_price);
    $('#bid_last_edit').html(toDateAndTime2(bid.last_edit));
    $('#bid_start_date').val(toDateAndTime2(bid.start_date));
    $('#bid_end_date').val(toDateAndTime2(bid.end_date));
    $('#bid_status').val(bid.status);

    $('#product_id').val(bid.product_id);
}
function insertOrUpdateBid() {
    var id = $("#bid_id").html();
    var p_id = array_keys[array_values.indexOf($("#bid_product_name").val())];
    var start_date = $("#bid_start_date").val();
    var end_date = $("#bid_end_date").val();
    var status = $('#bid_status').val().toUpperCase();
    if ((status !== 'UNCOMPLETED') && (status !== 'COMPLETED'))
    {
        status = 'UNCOMPLETED';
    }
    if (id) {
        vteam_http.makeHttpRequest("/admin/update_bid",
                {bid_id: id,
                    product_id: p_id,
                    start_date: start_date,
                    end_date: end_date,
                    status: status
                },
        'POST', callback);
    } else {
        vteam_http.makeHttpRequest("/admin/insert_bid",
                {product_id: p_id,
                    start_date: start_date,
                    end_date: end_date},
        'POST', callback);
    }
}
function callback(result) {

    if (result.status !== 'error') {
        $('#result_IU_bid').html('Successful !').show();
        $(function() {
            setTimeout(function() {
                $("#result_IU_bid").hide('blind', {}, 400)
            }, 2000);
        });
        clearBidDetail();
        disableSave();
        update_lists();
    } else {
        $('#result_IU_bid').html('Error !').show();
        $(function() {
            setTimeout(function() {
                $("#result_IU_bid").hide('blind', {}, 400)
            }, 2000);
        });
        //alert("Insert/Update bid result: " + result.status);
    }
}

function update_lists() {
    _displayOngoingBid();
    _displayUpcomingBid();
    _displayCompletedBids();
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
    if ($(self).hasClass('newBtn')) {
        enableSave();
    }
}

function populateProductNameList() {
    vteam_http.makeHttpRequest("/admin/getProductNameList",
            {
            },
            'POST', populateList);
}

var array_keys;
var array_values;
function populateList(list) {
    //var b = "<select id=\"product_name\">";

    array_keys = new Array();
    array_values = new Array();

    for (var key in list) {
        array_keys.push(key);
        array_values.push(list[key]);
    }
//    for (var i = 0; i < array_keys.length; i++) {
//        b += "<option value='" + array_keys[i] + "'>" + array_values[i] + "</option>";
//    }
//    b += "</select>"
    $("#bid_product_name").autocomplete({
        source: array_values
    });
//    $('#product_name_container').html(b);

}

function _displayOngoingBid() {
    vteam_http.makeHttpRequest(
            "/bid/get_ongoing_bids",
            {
            },
            "POST",
            function(data) {
                if (data.status === "success") {
                    displayOngoingBid(data.ongoing_bid_list.lists);

                }
            }
    );
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
        cnt += bidList[i].id + ');" class="btn btn-small"><i class="btn-icon-only icon-stop"></i>';
        cnt += "</tr>";
    }
    $("#onGoingBid").html(
            cnt
            );
}
function _displayUpcomingBid() {
    vteam_http.makeHttpRequest(
            "/bid/get_upcoming_bids",
            {
            },
            "POST",
            function(data) {
                if (data.status === "success") {
                    displayUpcomingBid(data.upcoming_bid_list.lists);

                }
            }
    );
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
        cnt += bidList[i].id + ');" class="btn btn-small"><i class="btn-icon-only icon-stop"></i>';
        cnt += '</a></td>';
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

function _displayCompletedBids() {

    vteam_http.makeHttpRequest(
            "/bid/get_completed_bids",
            {
            },
            "POST",
            function(data) {
                if (data.status === "success") {
                    displayCompletedBids(data.completed_bid_list.lists);
                }
            }
    );
}

