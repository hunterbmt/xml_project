var vteam_http = {
    init: function() {
        vteam_http.req = new XMLHttpRequest();
    },
    getXMLObj: function(url, params, callbackFunction) {
        vteam_http.req = new XMLHttpRequest();
        var http = vteam_http.req;
        var queryString = converJSonToQueryString(params);

        http.open(
                "GET", url + "?" + queryString, true);
        http.onreadystatechange = function() {
            if (http.readyState == 4 && http.status == 200) {
                callbackFunction(http.responseXML);
            }
        }
        http.send(null);

    },
    makeHttpRequest: function(url, params, method, callbackFunction) {
        vteam_http.req = new XMLHttpRequest();
        var http = vteam_http.req;
        var queryString = converJSonToQueryString(params);
        if (method.toUpperCase() == 'GET') {
            http.open(
                    "GET", url + "?" + queryString, true);
            http.onreadystatechange = function() {
                if (http.readyState == 4 && http.status == 200) {
                    callbackFunction(eval("(" + http.response + ")"));
                }
            }
            http.send(null);
        } else if (method.toUpperCase() == 'POST') {
            http.open("POST", url, true);
            http.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.onreadystatechange = function() {
                if (http.readyState == 4 && http.status == 200) {
                    callbackFunction(eval("(" + http.response + ")"));
                }
            }
            http.send(queryString);
        } else {
            throw new Error("Unsupport this method");
        }
    },
    getByID: function(id) {
        return document.getElementById(id);
    },
    hasClass: function(el, cl) {
        var regex = new RegExp('(?:\\s|^)' + cl + '(?:\\s|$)');
        return !!el.className.match(regex);
    },
    addClass: function(id, cl) {

        el = this.getByID(id);
        if (!this.hasClass(el, cl)) {
            el.className += ' ' + cl;
        }
    },
    removeClass: function(id, cl) {
        el = this.getByID(id);
        var regex = new RegExp('(?:\\s|^)' + cl + '(?:\\s|$)', "gi");
        el.className = el.className.replace(regex, ' ');
    },
    toggleClass: function(el, cl) {
        hasClass(el, cl) ? removeClass(el, cl) : addClass(el, cl);

    },
    show: function(id) {
        this.removeClass(id, "hide");
    },
    hide: function(id) {
        this.addClass(id, "hide");
    },
    setHTML: function(id, html) {
        el = this.getByID(id);
        el.innerHTML = html;
    },
    getHTML: function(id) {
        el = this.getByID(id);
        return el.innerHTML;
    },
    appendTo: function(id, html) {
        el = this.getByID(id);
        el.innerHTML = el.innerHTML + html;
    },
    getValue: function(id) {
        el = this.getByID(id);
        return el.value;
    },
    setValue: function(id, value) {
        el = this.getByID(id);
        el.value = value;
    }
}

function converJSonToQueryString(json) {
    str = '';
    for (key in json) {
        str += key + '=' + json[key] + '&';
    }
    str = str.slice(0, str.length - 1);
    return str;
}