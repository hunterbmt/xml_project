<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : main_page_menu.xsl
    Created on : March 28, 2013, 8:57 PM
    Author     : Lenovo
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="MenuItems">
        <xsl:for-each select="Item">
            <li class="{class}">
                <a href="javascript:void(0)" onclick="{function}"><xsl:value-of select="name"/></a>
            </li>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
