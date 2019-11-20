package dev.aisandbox.client.scenarios.zebra;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.Data;

@Data
@XStreamAlias("characteristicObject")
public class CharacteristicObject {
    String name;
    String positiveDescription;
    String negativeDescription;
    String icon;
    @XStreamImplicit
    String[] alternativePositiveDescriptions;
    @XStreamImplicit
    String[] alternativeNegativeDescription;
}