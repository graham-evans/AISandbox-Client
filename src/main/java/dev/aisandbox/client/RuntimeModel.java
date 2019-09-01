package dev.aisandbox.client;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import dev.aisandbox.client.scenarios.Scenario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

@Component
public class RuntimeModel {

    @Getter
    Scenario scenario;

    @Getter
    @Setter
    Locale locale = Locale.UK;

    @Getter
    IntegerProperty minAgents = new SimpleIntegerProperty(1);

    @Getter
    IntegerProperty maxAgents = new SimpleIntegerProperty(1);

    @Getter
    ObservableList<Agent> agentList = FXCollections.observableList(new ArrayList<>());

    @Getter
    BooleanProperty valid = new SimpleBooleanProperty(false);

    Logger LOG = Logger.getLogger(RuntimeModel.class.getName());

    public enum OUTPUT_TYPE {NONE, FILE, VIDEO}

    public RuntimeModel() {
        // TODO - load default values

        // bind validation rules
        valid.bind(Bindings.and(Bindings.size(agentList).greaterThanOrEqualTo(minAgents), Bindings.size(agentList).lessThanOrEqualTo(maxAgents)));
    }

    public void setScenario(Scenario s) {
        this.scenario = s;
        LOG.info("changing scenario to " + s.getName(Locale.UK));
        minAgents.setValue(s.getMinAgentCount());
        maxAgents.setValue(s.getMaxAgentCount());
    }


}
