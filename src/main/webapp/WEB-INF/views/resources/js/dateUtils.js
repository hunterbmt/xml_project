/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function toDate(timestamp) {
    return formatDate(new Date(timestamp));
}
function formatDate(pubDate) {
    var weekday = new Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
    var monthname = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    var formattedDate = weekday[pubDate.getDay()] + ' '
            + monthname[pubDate.getMonth()] + ' '
            + pubDate.getDate() + ', ' + pubDate.getFullYear();
    return formattedDate;
}