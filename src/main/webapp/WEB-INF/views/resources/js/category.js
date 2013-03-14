
function loadAndDisplayCategory(){
    vteam_http.init();
    vteam_http.makeHttpRequest("/category/getCategoryList",{},            
    'GET',
            function(result) {
                if (result.status == 'success') {
                    displayCategory(result.result.categoryList)
                }
            });
}
function displayCategory(categoryList) {
    var html = '';
    for (var i = 0; i < categoryList.length; i++) {
        html += '<div>'
        html += '<ul class="nav nav-list">'
        html += '<li class="nav-header">'
        html += '<i class="icon-lock">'
        html += '</i>'
        html += '</li>'
        html += '<ul class="categorylist">'
        html += '<li>' + categoryList[i].name + '</li>'
        html += '</ul>'
        html += '</div>'
    }
    $(".categorylist").html(html)
}


