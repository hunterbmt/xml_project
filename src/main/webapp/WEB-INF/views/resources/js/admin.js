function generateNinOnClick() {
    $('#nin_modal').modal('show');
    vteam_http.setHTML("nin_result",'');
    vteam_http.hide("nin_result");
}
function generateNinCode() {
    var amount = vteam_http.getValue("nin_amount");
    var quantity =vteam_http.getValue("nin_quantity");
    vteam_http.makeHttpRequest("/admin/generate_nin",
            {amount: amount, quantity: quantity},
    'POST',
            function(result) {
                if (result.status === 'success') {
                    $('#nin_modal').modal('hide');
                    window.open("/admin/export_nin_code_to_pdf","_blank");
                }
            });
}