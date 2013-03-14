/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function bid_details(bid_id) {
    vteam_http.makeHttpRequest(
            "/bid/get_bid_by_id",
            {
                _id : bid_id
            },
            "POST",
            function(data) {
                if (data.status == "success") {
                    displayBidDetails(data.bid);
                }
            }
    );
}
function displayBidDetails(bid) {
    $('#bid_id').html(bid.id);
    $('#bid_last_userid').html(bid.last_userid);
    $('#bid_product_name').html(bid.product_name);
    $('#bid_current_price').html(bid.current_price);
    $('#bid_last_edit').html(toDateAndTime(bid.last_edit));
    $('#bid_start_date').val(toDateAndTime(bid.start_date));
    $('#bid_end_date').val(toDateAndTime(bid.end_date));
    $('#bid_status').val(bid.status);
}

function _displayOngoingBid() {
    vteam_http.makeHttpRequest(
            "/bid/get_ongoing_bids",
            {
            },
            "POST",
            function(data) {
                if (data.status == "success") {
                    displayOngoingBid(data.ongoing_bid_list.lists);
                    _displayUpcomingBid();

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
                if (data.status == "success") {
                    displayUpcomingBid(data.upcoming_bid_list.lists);
                    _displayCompletedBids();
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
                if (data.status == "success") {
                    displayCompletedBids(data.completed_bid_list.lists);
                }
            }
    );
}