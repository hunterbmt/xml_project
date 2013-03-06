

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="resources/css/bootstrap.css" rel="stylesheet">
        <link href="resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="resources/css/bootstrap-responsive.css" rel="stylesheet">
        <script type="text/javascript"
        src="http://platform.twitter.com/widgets.js"></script>
        <script src="resources/js/lib/prettify.js"></script>
        <script src="https://secure.hoiio.com/user/hoiioapp.js"></script>

        <script src="resources/js/lib/jquery.js"></script>
        <script src="resources/js/lib/jquery.url.js"></script>
        <script src="resources/js/lib/bootstrap-transition.js"></script>
        <script src="resources/js/lib/bootstrap-alert.js"></script>
        <script src="resources/js/lib/bootstrap-modal.js"></script>
        <script src="resources/js/lib/bootstrap-dropdown.js"></script>
        <script src="resources/js/lib/bootstrap-scrollspy.js"></script>
        <script src="resources/js/lib/bootstrap-tab.js"></script>
        <script src="resources/js/lib/bootstrap-tooltip.js"></script>
        <script src="resources/js/lib/bootstrap-popover.js"></script>
        <script src="resources/js/lib/bootstrap-button.js"></script>
        <script src="resources/js/lib/bootstrap-collapse.js"></script>
        <script src="resources/js/lib/bootstrap-carousel.js"></script>
        <script src="resources/js/lib/bootstrap-typeahead.js"></script>
        <script src="resources/js/lib/bootstrap-affix.js"></script>
        <script src="resources/js/lib/application.js"></script>
        <script src="resources/js/lib/jquery-ui.js"></script>
        <script>
            var currentNumber = 0;
            var currentListOfNumber ='';
            var camp_base_url = "/camp";
            var list_base_url = "/listNumbers";
            var target_url="/edit";
            var currentListID;
            $(document).ready(function(){
                $('.numbersOnly').keyup(function () { 
                    this.value = this.value.replace(/[^0-9\+\,]/g,'');
                });
            });
            function saveCamp(e){
                $(e).button('loading');
                $.ajax({
                    type: "POST",
                    url: camp_base_url+"/create" ,
                    data: ({
                        campName: $("#inputName").val(), 
                        numbers: $("#inputNumbers").val(),
                        msg: $("#inputMsg").val()
                    }),
                    success: function(hm){
                        $(e).button('reset');
                        if(hm.status == 'success'){
                            $("#notify").html('<span class="label label-success">Create new Campaign success</span>');
                        }else{
                            $("#notify").html('<span class="label label-important">'+hm.status+'</span>');
                        }
                    }
                });
            }
            function saveAndSendCamp(e){
                $(e).button('loading');
                $.ajax({
                    type: "POST",
                    url: camp_base_url+"/saveAndSend" ,
                    data: ({
                        campName: $("#inputName").val(), 
                        numbers: $("#inputNumbers").val(),
                        msg: $("#inputMsg").val()
                    }),
                    success: function(hm){
                        $(e).button('reset');
                        if(hm.status == 'success'){
                            $("#notify").html('<span class="label label-success">Create new Campaign success</span>');
                        }else{
                            $("#notify").html('<span class="label label-important">'+hm.status+'</span>');
                        }
                    }
                });
            }
            function closeDialog() {
                $("#notifyList").html("");
                /* var seletor1 = "#"+currentListID+" td:nth-child(1)";
                var seletor2 = "#"+currentListID+" td:nth-child(2)";
                var seletor3 = "#"+currentListID+" td:nth-child(3)";
                $(seletor1).find(':button').html($('#listName').val());
                $(seletor2).text(currentNumber);
                $(seletor3).find(':hidden').val(currentListOfNumber); */
                $('#numberTable > tbody').html('');
                $('#listModal').modal('hide');
            };
            function openOldList(){
                $('#listModal').modal('toggle');
            }
            function editOnClick(id){
                currentListID = id;
                currentListOfNumber='';
                currentNumber = 0;
                $.ajax({
                    type: "GET",
                    url: list_base_url+"/get" ,
                    data: ({
                        listID: id
                    }),
                    success: function(hm){
                        if(hm.status == 'success'){
                            $('#numberTable > tbody').html('');
                            var listNumber = hm.listNumbers;
                            $('#listName').html(listNumber.name) ;
                            $.each(listNumber.phoneNumber, function() {
                                $('#numberTable > tbody').append(
                                '<tr><td>'
                                    + this.name + '</td><td>'
                                    + this.number +'</td></tr>'
                            );
                            });
                            $('#listModal').modal('toggle')
                        }else{
                            $("#notifyList").html('<span class="label label-important">'+hm.status+'</span>');
                        }
                    }
                });
            }
            /* function okClicked(){
                var newList = new Object();
                var numbers = [];
                $('#numberTable > tbody').children('tr').each(function(){
                    var nameofNumber = $(this).find('#nameOfNumber').val();
                    var numberofNumber = $(this).find('#numberOfNumber').val();
                    var idOfNumber = $(this).find('idOfNumber').val();
                    if(nameofNumber !=''&& numberofNumber!=''){
                        numbers.push({id:idOfNumber, name: nameofNumber, number:numberofNumber});
                        currentNumber += 1;
                        currentListOfNumber += numberofNumber+',';
                    }
                });
                newList['id'] = currentListID;
                newList['name'] = $('#listName').val();
                newList['phoneNumber'] = numbers;
                var encoded = JSON.stringify(newList);
                $.ajax({
                    type: "POST",
                    url: list_base_url+target_url ,
                    data: ({
                        listNumber :encoded
                    }),
                    dataType: 'json',
                    traditional: true,
                    success: function(hm){
                        if(hm.status == 'success'){
                            $("#notifyList").html('<span class="label label-success">Edit list success</span>');
                            if(hm.returnList != null){                         
                                $('#oldListTable tbody:first').before('<tr id='+hm.returnList.id+' class="draggable">'
                                    +'<td><button type="button"  onclick="editOnClick('+hm.returnList.id+')" class="btn btn-link">'
                                    +hm.returnList.name+'</button></td>'
                                    +'<td>'+hm.returnList.numbOfNumbers+'</td>'
                                    +'<td><input type="hidden" id="'+hm.returnList.id+'" value="'+hm.returnList.numbers+'"/></td>'
                                    +'<td><button type="button"  onclick="deleteOnClick('+hm.returnList.id+')" class="btn btn-link">Delete</button></td>'
                                    +'</tr>'
                            )
                            }
                        }else{
                            $("#notifyList").html('<span class="label label-important">Have error .Try again!</span>');
                        }
                    }
                });
            } */
            /* function newNumberOnClick(){
                $('#numberTable > tbody').append(
                '<tr><td><input id="nameOfNumber" value =""/>'
                    + '</td><td><input id="numberOfNumber" value=""/>'
                    +'</td><td>'
                    +'<a class="btn btn-danger" onclick="removeNumberOnClick(this)" href="#"><i class="icon-remove icon-white"></i></a>'
                    +'</td></tr>'
            );
            }
            function removeNumberOnClick(e){
                $(e).parent().parent().remove();
            }
            function newListOnClick(){
                target_url = "/create";
                newNumberOnClick();
                $('#listModal').modal('toggle');
            }
            function deleteOnClick(id){
                $.ajax({
                    type: "GET",
                    url: list_base_url+"/delete" ,
                    data: ({
                        listID : id
                    }),
                    success: function(hm){
                        if(hm.status == 'success'){
                            var seletor = "#"+id+"";
                            $(seletor).remove();
                        }else{
                            $("#notify").html('<span class="label label-important">Have error .Try again!</span>');
                        }
                    }
                });
            }*/
            function importOnClick(e){
                $('#inputNumbers').append($(e).parent().parent().find("input:hidden").val()+',');
            }
        </script>
    </head>
    <body>
        <div style="margin-left: 30px">
            <div>
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="/newCamp">New Campaign</a> 
                    </li>
                    <li><a href="/camps">Campaigns</a></li>
                    <li><a href="/list">List</a></li>
                    <li ><a href="/reports">Reports</a></li>
                </ul>
            </div>
            <div id="notify"></div>
            <div class="row">
                <div id="mainForm" class="form-horizontal">
                    <div class="control-group">
                        <label class="control-label" for="inputName">Name Campaign:</label>
                        <div class="controls">
                            <input class="required" type="text" style="width:422px" id="inputName" placeholder="Text input">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="inputNumbers">List of Numbers:</label>
                        <div class="controls">
                            <textarea  class="numbersOnly required"id="inputNumbers" style="width:422px" placeholder="Input numbers" rows="6"></textarea>
                            <div  style="margin-top: 10px;">
                                <a onclick="openOldList()"href="#" style="margin-right: 90px;" class="collapsed">
                                    Import old list</a>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="inputMsg">Boardcast Message:</label>
                        <div class="controls">
                            <textarea class="required" id="inputMsg" style="width:422px" placeholder="Input msg" rows="6"></textarea>
                        </div>
                    </div>               
                    <div class="control-group">
                        <div class="row-fluid">
                            <div class="controls">
                                <button  class="btn btn-large" data-loading-text="Processing..." onclick="saveCamp(this)">Save</button>
                            </div>
                            <div class="controls" >
                                <button  class="btn btn-primary btn-large" data-loading-text="Processing..." onclick="saveAndSendCamp(this)">Save and Send</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!--    <div class="span2">
                        <div class="btn-group">
                            <a class="btn btn-primary" href="#"><i class="icon-book icon-white"></i> List Number</a>
                            <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a data-toggle="collapse" data-target="#oldList" href="#"><i class="icon-share-alt"></i> Old List</a></li>
                                <li><a href="#" onclick="newListOnClick()"><i class="icon-plus-sign"></i> Add new List</a></li>
                            </ul>
                        </div>
    
                    </div> -->
                <div class="modal hide fade" role="dialog" id="listModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-body">
                        <table id="oldListTable" class="table table-striped ">
                            <thead>
                            <th> Name </th>
                            <th> Numbers</th>
                            <th>  </th>
                            </thead>
                            <tbody>
                                <c:forEach var="list" items="${requestScope.LIST}">
                                    <tr id="${list.id}" class="draggable">
                                        <td><button type="button"  onclick="importOnClick(this)"class="btn btn-link">${list.name}</button></td>
                                        <td>${list.numbOfNumbers}<input type="hidden" id="${list.id}" value="${list.numbers}"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
