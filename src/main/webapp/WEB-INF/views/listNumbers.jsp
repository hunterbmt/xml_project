
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            var base_url = "/listNumbers";
            var currentListID;
            var currentNumber=0;
            var encoded;
            $(document).ready(function(){
                $('#btnSave').show();
                $('#btnCreate').hide();
                $('.numbersOnly').keyup(function () { 
                    this.value = this.value.replace(/[^0-9\+\,]/g,'');
                });
            });
            function setNumberOnly(){
                $('.numbersOnly').keyup(function () { 
                    this.value = this.value.replace(/[^0-9\+\,]/g,'');
                });
            }
            function closeDialog() {
                $("#notify").html("");
                $('#editNumber').collapse('hide');
                $('#newListBtn').show();
            };
            function editOnClick(id){
                currentListID =id;
                currentNumber = 0;
                $('#btnSave').show();
                $('#btnCreate').hide();
                $.ajax({
                    type: "GET",
                    url: base_url+"/get" ,
                    data: ({
                        listID: id
                    }),
                    success: function(hm){
                        if(hm.status == 'success'){
                            $('#editNumberTable > tbody').html('');
                            var listNumber = hm.listNumbers;
                            $('#listName').val(listNumber.name) ;
                            $('#sendToNumbers').val(listNumber.numbers);
                            $('#editNumber').collapse('show');
                        }else{
                            $("#notify").html('<span class="label label-important">'+hm.status+'</span>');
                        }
                    }
                });
            }
            function newNumberOnClick(){
                $('#editNumberTable > tbody').append(
                '<tr><td><input id="idOfNumber" type ="hidden" value ="0"/><input id="nameOfNumber" value ="" placeholder="batman"/>'
                    + '</td><td><input id="numberOfNumber" class="numbersOnly" value="" placeholder="+849999999"/>'
                    +'</td><td>'
                    +'<a class="btn btn-danger" onclick="removeNumberOnClick(this)" href="#"><i class="icon-remove icon-white"></i></a>'
                    +'</td></tr>'
            );
                setNumberOnly();
            };
            function removeNumberOnClick(e){
                $(e).parent().parent().remove();
            };
            function createJSONString(){
                var newList = new Object();
                
                var sendToNumbers = $('#sendToNumbers').val();
                var numbers = sendToNumbers.split(',');;
                newList['id'] = currentListID;
                newList['name'] = $('#listName').val();
                newList['numbOfNumbers'] = numbers.length;
                currentNumber = numbers.length;
                newList['numbers']= sendToNumbers;
                encoded = JSON.stringify(newList);
            }
            function saveOnClick(){
                createJSONString();
                $.ajax({
                    type: "POST",
                    url: base_url+"/edit" ,
                    data: ({
                        listNumber :encoded
                    }),
                    dataType: 'json',
                    traditional: true,
                    success: function(hm){
                        if(hm.status == 'success'){
                            $("#notify").html('<span class="label label-success">Edit list success</span>');
                            var seletor1 = "#"+currentListID+" td:nth-child(1)";
                            var seletor2 = "#"+currentListID+" td:nth-child(2)"
                            $(seletor1).html($('#listName').val());
                            $(seletor2).text(currentNumber);
                            currentNumber = 0;
                            closeDialog();
                            /*  if(hm.returnList != null){                         
                                $('#listTable tbody:first').before('<tr id='+hm.returnList.id+' class="draggable">'
                                    +'<td><button type="button"  onclick="editOnClick('+hm.returnList.id+')" class="btn btn-link">'
                                    +hm.returnList.name+'</button></td>'
                                    +'<td>'+hm.returnList.numbOfNumbers+'</td>'
                                    +'<td><input type="hidden" id="'+hm.returnList.id+'" value="'+hm.returnList.numbers+'"/></td>'
                                    +'<td><button type="button"  onclick="deleteOnClick('+hm.returnList.id+')" class="btn btn-link">Delete</button></td>'
                                    +'</tr>'
                            )
                            }*/
                        }else{
                            $("#notify").html('<span class="label label-important">'+hm.status+'</span>');
                        }
                    }
                });
            }
            function createNewOnClick(){
                createJSONString();
                $.ajax({
                    type: "POST",
                    url: base_url+"/create" ,
                    data: ({
                        listNumber :encoded
                    }),
                    dataType: 'json',
                    traditional: true,
                    success: function(hm){
                        if(hm.status == 'success'){
                            if(hm.returnList != null){
                                $("#notify").html('<span class="label label-success">Create list success</span>');
                                $('#listTable').before(
                                '<tr id='+hm.returnList.id+'><td>'+hm.returnList.name+'</td>'
                                    +'<td>'+hm.returnList.numbOfNumbers+'</td><td>'
                                    + '<div class="control-group" >'
                                    + '<button type="button"  onclick="editOnClick(' +hm.returnList.id+ ')" class="btn btn-link">Edit</button>'
                                    +  '<button type="button"  onclick="replicateOnClick('+hm.returnList.id+')" class="btn btn-link">Replicate</button>'
                                    +  '<button type="button"  onclick="deleteOnClick('+hm.returnList.id+')" class="btn btn-link">Delete</button>'
                                    +  '     </div></td></tr>'
                            );
                                closeDialog();
                            }
                            currentNumber = 0;
                        }else{
                            $("#notify").html('<span class="label label-important">'+hm.status+'</span>');
                        }
                    }
                });
            }
            function replicateOnClick(id){
                editOnClick(id);
                $('#btnSave').hide();
                $('#btnCreate').show();
                $('#editNumber').collapse('show');
            }
            function newListOnClick(){
                $('#editNumberTable > tbody').html('');
                newNumberOnClick();
                $('#editNumber').collapse('show');
                $('#btnSave').hide();
                $('#btnCreate').show();
                $('#newListBtn').hide();
            }
            function deleteOnClick(id){
                $.ajax({
                    type: "GET",
                    url: base_url+"/delete" ,
                    data: ({
                        listID: id
                    }),
                    success: function(hm){
                        if(hm.status == 'success'){
                            var seletor = "#"+id+"";
                            $(seletor).remove();
                        }
                        else{
                            $("#notify").html('<span class="label label-important">Have error .Try again!</span>');
                        }
                    }
                });
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
                    <li><a href="/camps">Campaigns</a></li>
                    <li class="active"><a href="/list">List</a></li>
                    <li ><a href="/reports">Reports</a></li>
                </ul>
            </div>
            <div id="notify"></div>
            <div id="editNumber" class="accordion-body collapse">
                <div class="modal-footer">
                    <div class="row-fluid">
                        <input class="pull-left" id ="listName" placeholder="input name" type="text" style="width:422px" />
                        <a href="#" id="btnCreate" class="btn btn-primary" onclick="createNewOnClick();">Done</a>
                        <a href="#" id="btnSave" class="btn btn-primary" onclick="saveOnClick();">Done</a>
                        <a href="#" class="btn" onclick="closeDialog();">Close</a>
                    </div>
                    <div class="row-fluid">
                        <textarea rows="5" id="sendToNumbers" class="pull-left numbersOnly" placeholder="input numbers" style="width:422px" ></textarea>
                    </div>
                </div>
            </div>
            <a href="#" class="btn btn-success" id="newListBtn" onclick="newListOnClick()"><i class="icon-plus-sign"></i> Add new List</a>
            <c:if test="${not empty requestScope.LIST}">
                <table  class="table" >
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Numbers</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="listTable">
                        <c:forEach items="${requestScope.LIST}" var="list">
                            <tr id="${list.id}">
                                <td>
                                    ${list.name}
                                </td>
                                <td>
                                    ${list.numbOfNumbers}
                                </td>
                                <td>
                                    <div class="control-group" >
                                        <button type="button"  onclick="editOnClick(<c:out value="${list.id}" />)" class="btn btn-link ">Edit</button>
                                        <button type="button"  onclick="replicateOnClick(<c:out value="${list.id}" />)" class="btn btn-link ">Replicate</button>
                                        <button type="button"  onclick="deleteOnClick(<c:out value="${list.id}" />)" class="btn btn-link ">Delete</button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>
