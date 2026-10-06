<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    <xsl:strip-space elements="*"/>

    <xsl:key name="firearms-by-origin" match="Firearm" use="Origin"/>

    <xsl:template match="/Gun">
        <Gun>
            <!-- Muenchian grouping: take the first firearm of every origin -->
            <xsl:for-each select="Firearm[generate-id() = generate-id(key('firearms-by-origin', Origin)[1])]">
                <xsl:sort select="Origin"/>
                <xsl:element name="{Origin}">
                    <xsl:apply-templates select="key('firearms-by-origin', Origin)"/>
                </xsl:element>
            </xsl:for-each>
        </Gun>
    </xsl:template>

    <!-- copies elements without the namespace declarations inherited from the source root -->
    <xsl:template match="*">
        <xsl:element name="{local-name()}">
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
