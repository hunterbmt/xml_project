<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.1"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                exclude-result-prefixes="fo">
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="my-page">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="my-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size = "20pt" font-family = "sans-serif" line-height = "24pt"
                              text-align = "center" background-color="gray" >
                        Product List
                    </fo:block>
                    <fo:block padding-top="15pt">
                        <fo:table>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border-color="blue" border="solid 1px black" background-color="cyan" 
                                       text-align="center" font-weight="bold">
                                        <fo:block>
                                            No.
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border="solid 1px black" background-color="cyan"
                                       text-align="center" font-weight="bold">
                                        <fo:block>
                                            Name
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border="solid 1px black" background-color="cyan"
                                       text-align="center" font-weight="bold">
                                        <fo:block>
                                            Category
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border="solid 1px black" background-color="cyan"
                                       text-align="center" font-weight="bold">
                                        <fo:block>
                                            Price
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="//product">
                                    <fo:table-row>
                                        <fo:table-cell border="solid 1px black" text-align="center">
                                            <fo:block>
                                                <xsl:value-of select="position()" />
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="solid 1px black" text-align="center">
                                            <fo:block>
                                                <xsl:value-of select="name" />
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="solid 1px black" text-align="center">
                                            <fo:block>
                                                <xsl:value-of select="categoryName" />
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="solid 1px black" text-align="center">
                                            <fo:block>
                                                <xsl:value-of select="currentPrice" />
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                        <fo:block padding-top="10pt" padding-left="15pt" text-align="right" margin-right="23pt" >                           
                                Total: <xsl:value-of select="sum(//currentPrice)"/> vnd
                        </fo:block>
                    </fo:block>
                    
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>