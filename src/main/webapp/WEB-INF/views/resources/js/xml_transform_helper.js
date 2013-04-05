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

var transformeddoc = null;
var params = extractparams(window.location.search);


function transformToOngoingSourceXML(cDate)
{
    xml = loadXMLDoc("../resources/xml/bids.xml");
    xsl = loadXMLDoc("../resources/xsl/ongoingBid_list.xsl");

    // code for IE
    if (window.ActiveXObject)
    {
        ex = xml.transformNode(xsl);
        return ex;
    }
    else if (document.implementation && document.implementation.createDocument)
    {
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xsl);
        xsltProcessor.setParameter("","cDate",cDate);
        
        return xsltProcessor.transformToDocument(xml);        
    }
}

function displayOngoingPaginationResult(page, pageSize)
{
    if (!page) { page = 0 }
    if (!pageSize) { pageSize = 2 }
    xml = transformToOngoingSourceXML(new Date());
    xsl = loadXMLDoc("../resources/xsl/OnGoingBidPaging.xsl");

    // code for IE
    if (window.ActiveXObject)
    {
        ex = xml.transformNode(xsl);
        document.getElementById("example").innerHTML = ex;
    }
    else if (document.implementation && document.implementation.createDocument)
    {
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xsl);
        xsltProcessor.setParameter("","Page",page);
        xsltProcessor.setParameter("","PageSize",pageSize);
        xsltProcessor.setParameter("","_target",'bid');
        resultDocument = xsltProcessor.transformToFragment(xml,document);
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
