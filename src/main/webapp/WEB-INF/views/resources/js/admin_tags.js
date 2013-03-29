function populateTagsNameList() {
    vteam_http.makeHttpRequest("/tags/getTagsList",
            {
            },
            'POST',
            function(result) {
                if (result.status === 'success') {
                    populateTagList(result.tagsList);
                }
            });
}
var tags_array_source;
var tags_current_id;
function populateTagList(list) {
    tags_array_source = new Array();

    for (i = 0; i < list.length; i++) {
        tags_array_source.push({label: list[i].tagName, value: list[i].tagName, id: list[i].tagId});
    }
    $("#tags_name").autocomplete({
        source: tags_array_source,
        select: function(event, ui) {
            event.preventDefault();
            html ='';
            html+='<li class="select2-search-choice">'
            html+=    '<div>'+ui.item.label+'</div>'
            html+=    '<a href="#" onclick="return false;" class="select2-search-choice-close" tabindex="-1"></a>'
            html+='</li>'
            $(".select2-search-field").before(html);
            $("#tags_id").val($("#tags_id").val()+";"+ui.item.id);
            $("#tags_name").val('');
        }
    });
}

