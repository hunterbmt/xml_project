var vteam_http = {
    init: function () {
        vteam_http.req = new XMLHttpRequest();
    },
    makeHttpRequest: function (url, params, method, callbackFunction) {
        vteam_http.req = new XMLHttpRequest();
        var http = vteam_http.req;
        var queryString = converJSonToQueryString(params);
        if (method.toUpperCase() == 'GET') {
            http.open(
                "GET", url + "?" + queryString, true);
            http.onreadystatechange = function () {
                if (http.readyState == 4 && http.status == 200) {
                    callbackFunction(eval("(" + http.response + ")"));
                }
            }
            http.send(null);
        } else if (method.toUpperCase() == 'POST') {
            http.open("POST", url, true);
            http.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.onreadystatechange = function () {
                if (http.readyState == 4 && http.status == 200) {
                    callbackFunction(eval("(" + http.response + ")"));
                }
            }
            http.send(queryString);
        } else {
            throw new Error("Unsupport this method");
        }
    }
}

    function converJSonToQueryString(json) {
        str = '';
        for (key in json) {
            str += key + '=' + escape(json[key]) + '&';
        }
        str = str.slice(0, str.length - 1);
        return str;
    }