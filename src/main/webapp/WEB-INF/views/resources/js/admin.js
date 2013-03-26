$("#nin_code_modal").dialog({
    autoOpen: false,
    height: 300,
    width: 350,
    modal: true,
    buttons: {
        "Generate Nin Code": function() {
            var amount = $("#nin_amount").val();
            var quatity = $("#nin_quantity").val();
            generateNinCode(amount, quatity);

        },
        Cancel: function() {
            $(this).dialog("close");
        }
    },
    close: function() {
        allFields.val("").removeClass("ui-state-error");
    }
});
function generateNinOnClick() {
    $( "#nin_code_modal" ).dialog( "open" );
}
function generateNinCode(amount, quantity) {
    vteam_http.makeHttpRequest("/admin/generate_nin",
            {amount: amount, quantity: quantity},
    'POST',
            function(result) {
                if (result.status === 'success') {
                    $("#nin_result").html("Success!");
                    $("#nin_result").save();
                }
            });
}