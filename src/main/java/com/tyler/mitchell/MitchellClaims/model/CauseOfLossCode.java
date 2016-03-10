//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.09 at 09:33:25 PM PST 
//


package com.tyler.mitchell.MitchellClaims.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CauseOfLossCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CauseOfLossCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Collision"/>
 *     &lt;enumeration value="Explosion"/>
 *     &lt;enumeration value="Fire"/>
 *     &lt;enumeration value="Hail"/>
 *     &lt;enumeration value="Mechanical Breakdown"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CauseOfLossCode")
@XmlEnum
public enum CauseOfLossCode {

    @XmlEnumValue("Collision")
    COLLISION("Collision"),
    @XmlEnumValue("Explosion")
    EXPLOSION("Explosion"),
    @XmlEnumValue("Fire")
    FIRE("Fire"),
    @XmlEnumValue("Hail")
    HAIL("Hail"),
    @XmlEnumValue("Mechanical Breakdown")
    MECHANICAL_BREAKDOWN("Mechanical Breakdown"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    CauseOfLossCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CauseOfLossCode fromValue(String v) {
        for (CauseOfLossCode c: CauseOfLossCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
