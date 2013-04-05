<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : ongoingBid_list.xsl
    Created on : April 5, 2013, 11:26 AM
    Author     : Crick
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"> 
    <xsl:output method="html"/>  
    
    <xsl:template match="/">
        <xsl:apply-templates>
            <xsl:with-param name="cDate" select="'04/05/2013 13:18:00'"/>            
        </xsl:apply-templates>
    </xsl:template>
    
    <xsl:template match="bidList">  
        <xsl:param name="cDate" select="'04/06/1989 10:00:00'"/>   
        <xsl:variable name="datetime-punctuation" select="'/: '" />
        <xsl:variable name="stripped-current-datetime"
                          select="number(translate($cDate, $datetime-punctuation, ''))" />   
        <div class="widget widget-table action-table">
            <div class="widget-header">
                <i class="icon-list-alt"></i>
                <h3>Ongoing Bids</h3>
            </div>
            <div class="widget-content">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Product</th>
                            <th>Start date</th>
                            <th>Current user</th>
                            <th>Current price</th>
                            <th>Last bid</th>
                            <th class="td-actions" style="width: 8%"></th>
                        </tr>
                    </thead>
                    <tbody id="onGoingBid">
                        <xsl:for-each 
                            select="//bid[number(translate(start_date, $datetime-punctuation, '')) 
                            &lt; $stripped-current-datetime 
                            and 
                            number(translate(end_date, $datetime-punctuation, ''))
                            > $stripped-current-datetime
                            ]"
                        >
                            <tr>
                                <td>
                                    <xsl:value-of select="id"/>
                                </td>
                                <td>
                                    <xsl:value-of select="product_name"/>
                                </td>
                                <td>
                                    <xsl:value-of select="start_date"/>
                                </td>
                                <td>
                                    <xsl:value-of select="last_username"/>
                                </td>
                                <td>
                                    <xsl:value-of select="current_price"/>
                                </td>
                                <td>
                                    <xsl:value-of select="last_edit"/>
                                </td>
                                <td class="td-actions">
                                    <a href="javascript:void(0)" 
                                       onclick="bid_details({id})" class="btn btn-warning btn-small">
                                        <i class="btn-icon-only icon-edit"></i>
                                    </a>
                                </td>
                            </tr>
                        </xsl:for-each>  
                    </tbody>
                </table>
                <div class="pagination-bar">
                    <div id="ongoing_pagination_bar" style="float: right">
                    </div>
                </div>
            </div>
        </div>           
    </xsl:template>
</xsl:stylesheet>
