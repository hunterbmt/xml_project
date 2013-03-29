function populateTagsNameList() {
    vteam_http.makeHttpRequest("/tags/getTagsList",
            {
            },
            'POST',
            function(result) {
                if (result.status === 'success') {
                    populateList(result.tagsList)
                }
            });
}
var tags_array_source;
var product_current_tags_id;
var tags_current_id;
function populateList(list) {
    tags_array_source = new Array();

    for (i = 0; i < list.length; i++) {
        tags_array_source.push({label: list[i].name, value: list[i].name, id: list[i].id});
    }
    $("#tags_id").autocomplete({
        source: tags_array_source,
        select: function(event, ui) {
            product_current_category_id = ui.item.id;
        }
    });
    $("#tags_name").autocomplete({
        source: tags_array_source,
        select: function(event, ui) {
            tags_current_id = ui.item.id;
        }
    });
}

