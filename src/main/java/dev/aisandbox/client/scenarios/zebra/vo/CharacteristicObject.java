package dev.aisandbox.client.scenarios.zebra.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.Data;

@Data
@XStreamAlias("characteristicObject")
public class CharacteristicObject {
    String name;
    String positiveDescription;
    String negativeDescription;
    int icon;
    @XStreamImplicit(itemFieldName="alternativePositiveDescription")
    String[] alternativePositiveDescriptions;
    @XStreamImplicit(itemFieldName="alternativeNegativeDescription")
    String[] alternativeNegativeDescription;
}