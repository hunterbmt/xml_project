/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function loadAndDisplayOrder() {
    var id=document.getElementById("user_id").value;
    vteam_http.makeHttpRequest("/order/getOrderList",
    {id:id},
    'POST',
            function(result) {
                if (result.status == 'success') {
                    displayOrder(result.orderList);
                }
            });
}

function displayOrder(orderList) {
    var html = '';
    for (var i = 0; i < orderList.length; i++) {
        html += '<tr>'
        html += '<td>'
        html += orderList[i].productName
        html += '</td>'
        html += '<td>'
        html += orderList[i].address
        html += '</td>'
        html += '<td>'
        html += toDateAndTime2(orderList[i].date)
        html += '</td>'
        html += '<td>'
        html += orderList[i].ammount
        html += '</td>'
        html += '</tr>'
    }
    $("#orderResult").html(html);
    $("#orderResult").show();
}
