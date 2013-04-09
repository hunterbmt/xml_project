var current_page;
page_size = 15;
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
        html += "<td>" + orderList[i].user + "</td>"
        html += "<td>" + orderList[i].productName + "</td>"
        html += "<td>" + orderList[i].address + "</td>"
        html += "<td>" + orderList[i].ammount + "</td>"
        html += "<td>" + orderList[i].orderDay + "</td>"
        html += "<td>" + orderList[i].orderStatus + "</td>"
        html += '<td class="td-actions">'
        html += '<a href="javascript:;" class="btn btn-small btn-warning" onclick="getProductDetail(' + orderList[i].id + ')" >'
        html += '<i class="btn-icon-only icon-edit"></i>'
        html += '</a>'
        html += '<a href="javascript:;" class="btn btn-small btn-success" onclick="getProductDetail(' + orderList[i].id + ')" >'
        html += '<i class="btn-icon-only icon-check"></i>'
        html += '</a>'
        html += '<a href="javascript:;" class="btn btn-small" onclick="deleteProductOnClick(' + orderList[i].id + ',\'' + orderList[i].user + '\')">';
        html += '<i class="btn-icon-only icon-remove"></i>'
        html += '</a>'
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
