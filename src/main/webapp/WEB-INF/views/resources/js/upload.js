MAX_FILE_UPLOAD = 5242880;
function showProgress(progress) {
    var bar = $('.bar');
    var percentVal = progress + '%';
    bar.width(percentVal);
    $('#progress_div').show();
}
function enableFileUpload() {
    $('#product_img_file').removeAttr('disabled', 'disabled');
}

function disableFileUpload() {
    $('#product_img_file').attr('disabled', 'disabled');
}

function beforeSend(data) {
    if (data.files[0] != null) {
        var size = data.files[0].size;
        var type = data.files[0].type;
        if (size != null && size > MAX_FILE_UPLOAD) {
            showUploadStatus("Max file size is 5MB");
            enableFileUpload();
            return;
        }
        if (type != null && type!="image/png" && type!="image/jpeg") {
            showUploadStatus("File error format(Only PNG and JPEG file is supported)");
            enableFileUpload();
            return;
        }
    }
    data.submit();
    showProgress(0);
}

function uploadComplete(result) {
    $('#progress_div').hide();
    if (result.status == "success") {
        vteam_http.setValue("product_img_name",result.imgName);
        document.getElementById("product_img").setAttribute("src",result.imgPath);
    } else {
        showUploadStatus(result.msg);
    }
}

function uploadFail() {
    showUploadStatus("Have some error");
}
function showUploadStatus(msg) {
    alert(msg)
}