package dev.aisandbox.client.scenarios.zebra;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

import java.util.ArrayList;

@Data
@XStreamAlias("characteristic")
public class Characteristic {
    private String name;

    private List<CharacteristicObject> instances=new ArrayList<>();

}