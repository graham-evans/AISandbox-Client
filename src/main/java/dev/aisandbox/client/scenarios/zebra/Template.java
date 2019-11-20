package dev.aisandbox.client.scenarios.zebra;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;

import lombok.Data;
import lombok.Getter;

@XStreamAlias("template")
@Data
public class Template {
    @Getter
    private final List<Characteristic> characteristics = new ArrayList<>();
}