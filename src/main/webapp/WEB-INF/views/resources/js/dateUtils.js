/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function toDate(timestamp) {
    return formatDate(new Date(timestamp));
}

function toDateAndTime(ts) {
    return formatDate(new Date(ts)) + ' <strong>' + getTime(ts) + '</strong>';
}

function toDateAndTime1(ts) {
    return formatDate(new Date(ts)) + ' ' + getTime(ts);
}

function toDateAndTime2(ts) {
    return formatDate1(ts);
}

function formatDate(pubDate) {
    var weekday = new Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
    var monthname = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    var formattedDate = weekday[pubDate.getDay()] + ' '
            + monthname[pubDate.getMonth()] + ' '
            + pubDate.getDate() + ', ' + pubDate.getFullYear() ;
    return formattedDate;
}

function formatDate1(ts) {
    var pubDate = new Date(ts);
    var formattedDate = 
            (pubDate.getMonth()+1) + '/' + (pubDate.getDate()) + '/'
            +  pubDate.getFullYear() + ' ' + getTime(ts);
    return formattedDate;
}


function getTime(ts) {
    var date = new Date(ts);
// hours part from the timestamp
var hours = date.getHours();
// minutes part from the timestamp
var minutes = date.getMinutes();
// seconds part from the timestamp
var seconds = date.getSeconds();

// will display time in 10:30:23 format
var formattedTime = hours + ':' + minutes + ':' + seconds;

return formattedTime;
}