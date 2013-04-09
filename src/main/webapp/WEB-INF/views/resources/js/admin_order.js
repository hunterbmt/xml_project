var current_page;
page_size = 15;
var current_order_id;
function loadOrderList(page) {
    current_page = page;
    vteam_http.makeHttpRequest("/admin/get_order_list", {page: page, pageSize: page_size}, 'POST',
            function(result) {
                if (result.status == "success") {
                    displayOrderList(result.orderList);
                    if (result.numberOfOrder > page_size) {
                        displayPagination(page, result.numberOfOrder);
                    }
                }
            })
}
function displayOrderList(orderList) {
    var html = '';
    for (var i = 0; i < orderList.length; i++) {
        html += "<tr>"
        html += "<td>" + orderList[i].id + "</td>"
        if (orderList[i].phoneNumber == null) {
            html += "<td>" + orderList[i].user + "</td>"
        }
        else {
            html += "<td>" + orderList[i].phoneNumber + "</td>"
        }
        html += "<td>" + orderList[i].productName + "</td>"
        html += "<td>" + orderList[i].address + "</td>"
        html += "<td>" + orderList[i].ammount + "</td>"
        html += "<td>" + orderList[i].orderDay + "</td>"
        html += "<td>" + orderList[i].orderStatus + "</td>"
        html += '<td class="td-actions">'
        if (orderList[i].orderStatus == 'NEW') {
            html += '<a href="javascript:;" class="btn btn-small btn-warning" onclick="loadOrderDetail(' + orderList[i].id + ')" >'
            html += '<i class="btn-icon-only icon-edit"></i>'
            html += '</a>'
            html += '<a href="javascript:;" class="btn btn-small btn-success" onclick="finishOrderOnClick(' + orderList[i].id + ')" >'
            html += '<i class="btn-icon-only icon-check"></i>'
            html += '</a>'
            html += '<a href="javascript:;" class="btn btn-small" onclick="cancelOrderOnClick(' + orderList[i].id + ')">';
            html += '<i class="btn-icon-only icon-remove"></i>'
            html += '</a>'
        }
        html += '</td>'
        html += '</tr>'
    }
    vteam_http.setHTML("order_list_tbody", html);
}
function displayPagination(currentPage, total_number) {
    $("#pagination_bar").pagination({
        items: total_number,
        itemsOnPage: page_size,
        currentPage: currentPage,
        cssStyle: 'light-theme',
        onPageClick: function(pageNumber, event) {
            loadOrderList(pageNumber);
        }
    });
}
function loadOrderDetail(id) {
    current_order_id = id;
    vteam_http.makeHttpRequest("/admin/get_order_detail", {orderId: id}, 'GET',
            function(result) {
                if (result.status == 'success') {
                    displayOrderDetail(result);
                }
            }
    )
}
function displayOrderDetail(orderDetail) {
    if (orderDetail.orderStatus != 'NEW') {
        alert("Can't edit FINISHED or CANCELED order");
        return;
    }
    vteam_http.setValue('order_detal_address', orderDetail.address);
    $('#order_detail_modal').modal('show');
}
function updateOrder() {
    var address = vteam_http.getValue('order_detal_address');
    vteam_http.makeHttpRequest("/admin/update_order_detail", {orderId: current_order_id, address: address}, 'POST',
            function(result) {
                if (result.status == 'success') {
                    $('#order_detail_modal').modal('hide');
                    loadOrderList(current_page);
                }
            }
    );
}
function finishOrderOnClick(orderId) {
    vteam_http.setHTML("order_action_confirm_msg", "Do you wanna finish order  " + orderId + " ?")
    $("#order_action_confirm").dialog({
        resizable: false,
        height: 140,
        modal: true,
        buttons: {
            "Ok": function() {
                finishOrder(orderId);
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
}
function finishOrder(orderId) {
    vteam_http.makeHttpRequest("/admin/finish_order", {orderId: orderId}, 'POST',
            function(result) {
                loadOrderList(current_page);
            }
    )
}
function cancelOrderOnClick(orderId) {
    vteam_http.setHTML("order_action_confirm_msg", "Do you wanna cancel order  " + orderId + " ?")
    $("#order_action_confirm").dialog({
        resizable: false,
        height: 140,
        modal: true,
        buttons: {
            "Ok": function() {
                cancelOrder(orderId);
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        }
    });
}
function cancelOrder(orderId) {
    vteam_http.makeHttpRequest("/admin/cancel_order", {orderId: orderId}, 'POST',
            function(result) {
                loadOrderList(current_page);
            }
    )
}