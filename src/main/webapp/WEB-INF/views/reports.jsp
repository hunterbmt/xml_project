<%-- 
    Document   : reports
    Created on : Aug 30, 2012, 10:29:54 AM
    Author     : Administrator
--%>

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
        <script src="resources/js/lib/raphael.js"></script>
        <script src="resources/js/lib/g.raphael.js"></script>
        <script src="resources/js/lib/g.pie.js"></script>


        <script>
            var base_url = "/reports";
            var values = [];
            var labels = [];
            function createData(report){
                values = [];
                labels = [];
                for (i in report.data) {
                    var item = report.data[i];
                    labels.push('%%.%% - '+item.label);
                    values.push(item.number);
                }
            };
            function drawCampDetail(data){
                $('#campInfo').find('#campName').text(data.name);
                $('#campInfo').find('#campNumbers').text(data.numbOfNumbers);
                $('#campInfo').find('#campCreate').text(data.createDate);
                $('#campInfo').show();
            }
            function drawDetailTable(data){
                $('#reportTable').find(' tbody').html('');
                $.each(data,function(){
                    var label = this.label;
                    $.each(this.phoneNumbers, function() {
                       $('#reportTable').find(' tbody').append(
                        '<tr><td>'
                            +this
                            +'</td><td>'
                            +label
                            +'</td></tr>'
                    );
                    });
                });
                $('#reportTable').show();
            };
            function drawChart(data){
                createData(data);
                $('#chart').html('');
                r = Raphael("chart");
                var pie = r.piechart(200, 120, 100, values, {legend: labels, legendpos: "east"});
                pie.hover(function () {
                    this.sector.stop();
                    this.sector.scale(1.1, 1.1, this.cx, this.cy);

                    if (this.label) {
                        this.label[0].stop();
                        this.label[0].attr({ r: 7.5 });
                        this.label[1].attr({ "font-weight": 800 });
                    }
                }, function () {
                    this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 500, "bounce");

                    if (this.label) {
                        this.label[0].animate({ r: 5 }, 500, "bounce");
                        this.label[1].attr({ "font-weight": 400 });
                    }
                });
            }
            $(document).ready(function(){
                var r = Raphael("chart");
                $('#reportTable').hide();
                $('#campInfo').hide();
                $('#combobox').change(function() {
                    $('#reportTable').hide();
                    $('#campInfo').hide();
                    $('#chart').html('');
                    var id = $(this).val();
                    if(id !=0){
                        var name =$(this).find('option:selected').text();
                        $.ajax({
                            type: "GET",
                            url: base_url+"/get" ,
                            data: ({
                                campID: id
                            }),
                            success: function(hm){
                                if(hm.status == 'success'){
                                    drawCampDetail(hm.report.camp);
                                    drawChart(hm.report);
                                    drawDetailTable(hm.report.data);
                                }else{
                           
                                }
                            }
                        });
                    }
                });
                
            });
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
                    <li><a href="/list">List</a></li>
                    <li class="active"><a href="/reports">Reports</a></li>
                </ul>
            </div>
            <legend>
                <div class="row-fluid ">
                    <select id="combobox" class="pull-right">
                        <option value="0">Choice one to view Report</option>
                        <c:forEach var="camp" items="${requestScope.CAMPS}">
                            <option value="${camp.id}">${camp.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </legend>

            <div class="row-fluid">
                <div id="reportChart" class="span6">
                    <div id="chart" style="height: 250px">
                    </div> 
                </div>
                <div id="campInfo" class="span4">
                    <strong id="campName"></strong>
                    <ul>
                        <li>
                            <div class="control-group">
                                <label>Numbers :</label>
                                <div id="campNumbers"></div>
                            </div>
                        </li>
                        <li>
                            <div class="control-group">
                                <label>Create Date :</label>
                                <div id="campCreate"></div>
                            </div>
                        </li>
                    </ul>
                    
                </div>
            </div>
            <div id ="reportTable">
                <table class="table table-striped">
                    <thead>
                    <th>Number </th>
                    <th>Status </th>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>

        </div>
    </body>
</html>
