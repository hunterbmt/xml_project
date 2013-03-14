
function loadAndDisplayCategory() {
    vteam_http.init();
    vteam_http.makeHttpRequest("/category/getCategoryList", {},
            'GET',
            function(result) {
                if (result.status == 'success') {
                    displayCategory(result.result.categoryList)
                }
            });
}
function displayCategory(categoryList) {
    var html = '';
    //html += '<ul class="nav nav-list>'
    html += '<li class = "nav-header" >Category</li>'
    for (var i = 0; i < categoryList.length; i++) {

        html += '   <li> <a href = "#" > <i class = "icon-star-empty" > </i> ' + categoryList[i].name + '</a> </li>'

    }
    //html += '   </ul>'
    $("#side-section > ul").html(html)
}


