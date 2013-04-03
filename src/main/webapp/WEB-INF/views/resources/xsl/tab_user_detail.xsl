<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : tab_user_detail.xsl
    Created on : April 2, 2013, 9:16 AM
    Author     : TH11032013
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="TabItems">
        <xsl:for-each select="Item">
            <li class="{class}">
                <a href="{href}" data-toggle="tab"><xsl:value-of select="name"/></a>
            </li>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
