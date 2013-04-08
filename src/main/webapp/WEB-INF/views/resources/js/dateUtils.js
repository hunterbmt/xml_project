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
            + pubDate.getDate() + ', ' + pubDate.getFullYear();
    return formattedDate;
}

function formatDate1(ts) {
    var pubDate = new Date(ts);
    var month = (pubDate.getMonth() + 1);
    month = (month < 10)? "0" + month: month;
    var day = pubDate.getDate();
    day = (day < 10)? "0" + day: day;
    //var formattedDate = month + '/' + day + '/'+pubDate.getFullYear()+' '+ getTime(ts);
    var formattedDate = pubDate.getFullYear()+ '/' + month + '/' + day + ' '+ getTime(ts); //yyyy/MM/dd HH:mm:ss
    return formattedDate;
}


function getTime(ts) {
    var date = new Date(ts);

    var hours = date.getHours();

    var minutes = date.getMinutes();

    var seconds = date.getSeconds();
    if (hours < 10)
        hours = '0' + hours;

    if (minutes < 10)
        minutes = '0' + minutes;

    if (seconds < 10)
        seconds = '0' + seconds;

    var formattedTime = hours + ':' + minutes + ':' + seconds;

    return formattedTime;
}