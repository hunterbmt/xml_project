<%-- 
    Document   : camps
    Created on : Aug 27, 2012, 11:18:22 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="resources/css/bootstrap.css" rel="stylesheet">
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
        <script type="text/javascript">
            var base_url = "/camp";
            var currentCampID;
            $(document).ready(function(){
                $('.numbersOnly').keyup(function () { 
                    this.value = this.value.replace(/[^0-9\+\,]/g,'');
                });
            });
            
            function editOnClick(id){
                currentCampID = id;
                $('#btnSave').show();
                $('#btnClone').hide();
                $.ajax({
                    type: "GET",
                    url: base_url+"/get" ,
                    data: ({
                        campID: id
                    }),
                    success: function(hm){
                        if(hm.status == 'success'){
                            $('#editCamp').find('#inputName').val(hm.camp.name);
                            $('#editCamp').find('#inputNumbers').val(hm.camp.sendToNumbers+',');
                            $('#editCamp').find('#inputMsg').val(hm.camp.msg);
                            $('#editCamp').collapse('show');
                        }else{
                            $("#notify").html('<span class="label label-important">Have error .Try again!</span>');
                        }
                    }
                });
            }
            function createOnClick(){
                $.ajax({
                    type: "POST",
                    url: base_url+"/clone" ,
                    data: ({
                        campName: $("#inputName").val(), 
                        numbers: $("#inputNumbers").val(),
                        msg: $("#inputMsg").val()
                    }),
                    success: function(hm){
                        if(hm.status == 'success'){                                            
                            $('#notify').html('<span class="label label-success">Create new Campaign success</span>');
                            $('#listTable').before('<tr id='+hm.camp.id+'><td>'+hm.camp.name+'</td><td>'+hm.camp.status+'</td>'+
                                '<td>'+hm.camp.numbOfNumbers+'</td><td>'
                                + '<div class="control-group" >'
                                + '<button type="button"  onclick="editOnClick(' +hm.camp.id+ ')" class="btn btn-link" style="width: 80px">Edit</button>'
                                +  '<button type="button"  onclick="replicateOnClick('+hm.camp.id+')" class="btn btn-link" style="width: 80px">Replicate</button>'
                                +  '<button type="button"  onclick="deleteOnClick('+hm.camp.id+')" class="btn btn-link" style="width: 80px">Delete</button>'
                                +  '     </div></td><td>'
                                + '<button id="btnSend" type="button"  onclick="sendOnClick('+hm.camp.id+',this)" class="btn btn-link" style="width: 80px">Send</button>'
                                +'</td></tr>')
                        }else{
                            $('#notify').html('<span class="label label-important">'+hm.status+'</span>');
                        }
                    }
                });
            }
            function replicateOnClick(id){
                editOnClick(id);
                $('#btnSave').hide();
                $('#btnClone').show();
            }
            function deleteOnClick(id){
                currentCampID = id;
                $.ajax({
                    type: "GET",
                    url: base_url+"/delete" ,
                    data: ({
                        campID: id
                    }),
                    success: function(hm){
                        if(hm.status == 'success'){
                            var seletor = "#"+currentCampID+"";
                            $(seletor).remove();
                        
                        }else{
                            alert("Have error");
                        }
                    }
                });
            }
            function sendOnClick(id,e){
                currentCampID = id;
                $.ajax({
                    type: "POST",
                    url: base_url+"/send" ,
                    data: ({
                        campID: id
                    }),
                    success: function(hm){
                        if(hm.status == 'success'){
                            $(e).parent().parent().parent().find('td:nth-child(2)').text('Refresh to get status');
                            $(e).parent().find('#btnEdit').hide();
                            $(e).parent().find('#btnSend').hide();
                        }else{
                            $('#mainNotify').html('<span class="label label-important">'+hm.status+'</span>');
                        }
                    }
                });
            }
            function openOldList(){
                $('#listModal').modal('toggle');
            }
            function importOnClick(e){
                var inputNumbers = $('#editCamp').find('#inputNumbers').val();
                inputNumbers += $(e).parent().parent().find("input:hidden").val() +',';
                $('#editCamp').find('#inputNumbers').val(inputNumbers);
                //$('#editCamp').find('#inputNumbers').append($(e).parent().parent().find("input:hidden").val());
                //alert( $('#editCamp').find('#inputNumbers').val());
            }
            function saveCamp(e){
                $(e).button('loading');
                $.ajax({
                    type: "POST",
                    url: base_url+"/edit" ,
                    data: ({
                        campID : currentCampID,
                        campName: $("#inputName").val(), 
                        numbers: $("#inputNumbers").val(),
                        msg: $("#inputMsg").val()
                    }),
                    success: function(hm){
                        $(e).button('reset');
                        if(hm.status == 'success'){
                            var selector1 = '#'+currentCampID+' td:nth-child(1)';
                            var selector2 = '#'+currentCampID+' td:nth-child(3)';
                            $(selector1).text($("#inputName").val());
                            $(selector2).text(hm.numbers);
                            $('#editCamp').collapse('hide');
                        }else{
                            $("#notify").html('<span class="label label-important">'+hm.status+'</span>');
                        }
                    }
                });
            }
            function CloseOnClick(){
                $("#notify").html("");
                $('#editCamp').collapse('hide');
            }
        </script>
    </head>
    <body>
        <div style="margin-left: 20px">
            <div>
                <ul class="nav nav-tabs">
                    <li>
                        <a href="/newCamp">New Campaign</a> 
                    </li>
                    <li class="active"><a href="/camps">Campaigns</a></li>
                    <li><a href="/list">List</a></li>
                    <li ><a href="/reports">Reports</a></li>
                </ul>
            </div>
            <c:if test="${empty requestScope.CAMPS}">
                Empty Campaign List . Click <a href="/newCamp">here</a> to create new one.
            </c:if>
            <div id="mainNotify"></div>
            <div id="editCamp" class="accordion-body collapse pull-left">
                <div id="mainForm" class="form-horizontal">
                    <div class="control-group">
                        <label class="control-label" for="inputName">Name Campaign:</label>
                        <div class="controls">
                            <input class="required" type="text" style="width:422px" id="inputName" placeholder="Text input">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="inputNumbers">List of Numbers:</label>
                        <div class="controls ">
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
                    <div class="control-group pull-right">
                        <div class="row-fluid">
                            <div class="controls"  >
                                <button  class="btn btn-large" data-loading-text="Processing..." onclick="CloseOnClick()">Close</button>
                            </div>
                            <div class="controls">
                                <button id="btnSave" class="btn btn-primary btn-large" data-loading-text="Processing..." onclick="saveCamp(this)">Save</button>
                            </div>
                            <div class="controls">
                                <button id="btnClone" class="btn btn-primary btn-large" data-loading-text="Processing..." onclick="createOnClick()">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${not empty requestScope.CAMPS}">
                <table  class="table" >
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Status</th>
                            <th>Numbers</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="listTable">
                        <c:forEach items="${requestScope.CAMPS}" var="camp">
                            <tr id="${camp.id}">
                                <td>
                                    ${camp.name}
                                </td>
                                <td>
                                    ${camp.status}
                                </td>
                                <td>
                                    ${camp.numbOfNumbers}
                                </td>
                                <td>
                                    <div class="control-group" >
                                        <c:if test="${camp.send == false}">
                                            <button id="btnEdit" type="button"  onclick="editOnClick(<c:out value="${camp.id}" />)" class="btn btn-link pull-left" style="width: 80px">Edit</button>
                                        </c:if>
                                        <button type="button"  onclick="replicateOnClick(<c:out value="${camp.id}" />)" class="btn btn-link pull-left" style="width: 80px">Replicate</button>
                                        <button type="button"  onclick="deleteOnClick(<c:out value="${camp.id}" />)" class="btn btn-link pull-left" style="width: 80px">Delete</button>
                                        <c:if test="${camp.send == false}">
                                            <button id="btnSend" type="button"  onclick="sendOnClick(<c:out value="${camp.id}" />,this)" class="btn btn-link pull-left" style="width: 80px">Send</button>
                                        </c:if>
                                    </div>
                                    <br/>

                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
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
    </body>
</html>
