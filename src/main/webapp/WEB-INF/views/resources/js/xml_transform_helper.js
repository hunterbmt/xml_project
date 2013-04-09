/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function loadXMLDoc(dname)
{
    if (window.XMLHttpRequest)
    {
        xhttp = new XMLHttpRequest();
    }
    else
    {
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xhttp.open("GET", dname, false);
    xhttp.send();
    return xhttp.responseXML;
}

function updateXML() {
    vteam_http.makeHttpRequest(
            "/admin/updateXML",{},"POST"
        );
}

function transformToOngoingSourceXML(cDate)
{
    updateXML();
    xml = loadXMLDoc("../resources/xml/bids.xml");
    xsl = loadXMLDoc("../resources/xsl/ongoingBid_list.xsl");

    // IE
    if (window.ActiveXObject)
    {
        ex = xml.transformNode(xsl);
        return ex;
    }
    else if (document.implementation && document.implementation.createDocument)
    {
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xsl);
        xsltProcessor.setParameter("", "cDate", cDate);

        return xsltProcessor.transformToDocument(xml);
    }
}

function viewHotBids() {
    updateXML();
    xml = transformToOngoingSourceXML(toDateAndTime2(new Date()));
    xsl = loadXMLDoc("../resources/xsl/HotBidProductIDs.xsl");
    var orderedHotProductIDs;
    if (window.ActiveXObject)
    {
        orderedHotProductIDs = xml.transformNode(xsl);        
    }
    else if (document.implementation && document.implementation.createDocument)
    {
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xsl);
        orderedHotProductIDs =  xsltProcessor.transformToDocument(xml);
    }

    var target = new Array();
    var ids = orderedHotProductIDs.getElementsByTagName("product_id");
    for (var i = 0; i < ids.length; i++) {
        target.push(ids[i].textContent);
    }

    vteam_http.makeSyncHttpRequest("/product/marshallHotBidProducts",
            {
                product_ids: target.reverse()
            },
    'POST');
    transformHotBids();
}

function transformHotBids() {
    xml = loadXMLDoc("../resources/xml/hot_bid_products.xml");
    xsl = loadXMLDoc("../resources/xsl/HotBidProducts.xsl");
    // IE
    if (window.ActiveXObject)
    {
        ex = xml.transformNode(xsl);
        document.getElementById("product_list").innerHTML = ex;
    }
    else if (document.implementation && document.implementation.createDocument)
    {
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xsl);
        resultDocument = xsltProcessor.transformToFragment(xml, document);
        //document.getElementById("product_list").appendChild(resultDocument);
        var x = resultDocument.childNodes;
        var html = "";
        
        for (i=0;i<x.length;i++) {            
            html += x[i].outerHTML;
        }
        vteam_http.setHTML("product_list","");
        vteam_http.appendTo("product_list", html);
        hideAllDiv();
        vteam_http.show("product_list");
    }
}
function displayOngoingPaginationResult(page, pageSize)
{
    if (!page) {
        page = 0;
    }
    if (!pageSize) {
        pageSize = 3;
    }
    xml = transformToOngoingSourceXML(toDateAndTime2(new Date()));
    xsl = loadXMLDoc("../resources/xsl/OnGoingBidPaging.xsl");

    // IE
    if (window.ActiveXObject)
    {
        ex = xml.transformNode(xsl);
        document.getElementById("test11").innerHTML = ex;
    }
    else if (document.implementation && document.implementation.createDocument)
    {
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xsl);
        xsltProcessor.setParameter("", "Page", page);
        xsltProcessor.setParameter("", "PageSize", pageSize);
        xsltProcessor.setParameter("", "_target", 'bid');
        resultDocument = xsltProcessor.transformToFragment(xml, document);
        document.getElementById("test11").appendChild(resultDocument);

    }
}

function extractparams(querystring) {
    var params = {'page': '', 'pagesize': ''};
    var qs = querystring.replace(/^\?/, '');
    qs = qs.split('&');
    for (var i = 0, kv; i < qs.length; i++) {
        kv = qs[i].split('=');
        if (kv.length === 2 && kv[0] in params) {
            params[kv[0]] = kv[1];
        }
    }
    return params;
}
