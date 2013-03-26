function generateNinOnClick() {
    $('#nin_modal').modal('show')
    $("#nin_result").html('');
    $("#nin_result").hide();
}
function generateNinCode() {
    var amount = $("#nin_amount").val();
    var quantity = $("#nin_quantity").val();
    vteam_http.makeHttpRequest("/admin/generate_nin",
            {amount: amount, quantity: quantity},
    'POST',
            function(result) {
                if (result.status === 'success') {
                    $("#nin_result").html("Success!");
                    $("#nin_result").show();
                }
            });
}