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
            vteam_http.setValue('tags_id',vteam_http.getValue("tags_id")+";"+ui.item.id);
            vteam_http.setValue("tags_name",'');
        }
    });
    $("#tag_detail_name").autocomplete({
        source: tags_array_source,
        select: function(event, ui) {
            tags_current_id = ui.item.id;
            loadAndDisplayTagDetail(tags_current_id);
        }
    });
}

function clearTagDetail() {
    vteam_http.setHTML("tag_detail_id",'');
    vteam_http.setValue("tag_detail_name",'');
    vteam_http.setValue('tag_detail_description','');
    vteam_http.setHTML('tag_detail_btn','Save');
    tags_current_id = null;
}
function insertOrUpdateTag() {
    var tagId = tags_current_id;
    var description = vteam_http.getValue('tag_detail_description');
    var name = vteam_http.getValue('tag_detail_name');
    validTag(name, description);
    if (tagId) {
        vteam_http.makeHttpRequest("/admin/update_tag",
                {tagId: tagId,
                    description: description},
        'POST', callbackTagEdit);
    } else {
        if (validTag(name, description)) {
            vteam_http.makeHttpRequest("/admin/insert_tag",
                    {
                        tagName: name,
                        description: description},
            'POST', callbackTagEdit);
        }
    }
}

function callbackTagEdit(result) {
    if (result.status === 'success') {
        displayTagMsg("Successfull");
        populateTagsNameList();
    } else {
        displayTagMsg(result.msg);
    }
    clearTagDetail();
}
function displayTagMsg(msg) {
    vteam_http.setHTML("result_tag",msg);
    vteam_http.show("result_tag");
    $(function() {
        setTimeout(function() {
            vteam_http.hide("result_tag");
        }, 20000);
    });
}

function validTag(name, des) {
    if (!name) {
        var div = $("#tag_detail_name").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_tag").html('Fields must be required').show();
        return false;
    } else {
        var div = $("#tag_detail_name").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");
        return true;
    }
    if (!des) {
        var div = $("#tag_detail_description").parents("div.control-group");
        div.removeClass("success");
        div.addClass("error");
        $("#result_tag").html('Fields must be required').show();
        return false;
    } else {
        var div = $("#tag_detail_description").parents("div.control-group");
        div.removeClass("error");
        div.addClass("success");
        return true;
    }
}

function loadAndDisplayTagDetail(id) {
    vteam_http.makeHttpRequest("/tags/getTagDetail",
            {
                tag_id: id
            },
    'POST', function(result) {
        if (result.status === 'success') {
            displayTagDetail(result);
        }
    });
}
function displayTagDetail(tag) {
    vteam_http.setHTML("tag_detail_id",tag.tagId);
    vteam_http.setValue("tag_detail_description",tag.tagDescription);
    vteam_http.setHTML('tag_detail_btn','Update');

}
