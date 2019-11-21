package dev.aisandbox.client.scenarios.zebra.vo;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.Data;

import java.util.ArrayList;

@Data
@XStreamAlias("characteristic")
public class Characteristic {
    private String name;
    @XStreamImplicit(itemFieldName="instance")
    private List<CharacteristicObject> instances=new ArrayList<>();

}