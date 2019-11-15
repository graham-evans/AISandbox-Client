package dev.aisandbox.client.scenarios;

import dev.aisandbox.client.AISandboxClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Tests to be run on every available scenario
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScenarioAvailabilityTests {

    private static final Logger LOG = Logger.getLogger(ScenarioAvailabilityTests.class.getName());

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private List<Scenario> scenarioList;

    @Test
    public void ScenariosAvailableTest() {
        assertNotNull("Null scenarios", scenarioList);
        assertTrue("No scenarios", scenarioList.size() > 0);
    }

    @Test
    /**
     * Check each scenario has a unique name
     */
    public void UniqueNamesTest() {
        Set<String> names = new HashSet<>();
        for (Scenario scenario : scenarioList) {
            assertNotNull("Scenario " + scenario.getClass().getName() + " has no name", scenario.getName());
            assertTrue("Scenario " + scenario.getClass().getName() + " has short name", scenario.getName().length() > 2);
            assertFalse("Scenario name '" + scenario.getName() + "' already used", names.contains(scenario.getName()));
            names.add(scenario.getName());
        }
    }

    @Test
    public void GroupTest() {
        for (Scenario scenario : scenarioList) {
            assertNotNull("Scenario " + scenario.getClass().getName() + " has a null group", scenario.getGroup());
        }
    }

    @Test
    public void OverviewTest() {
        Set<String> text = new HashSet<>();
        for (Scenario scenario : scenarioList) {
            assertNotNull("Scenario " + scenario.getClass().getName() + " has no overview", scenario.getOverview());
            assertTrue("Scenario " + scenario.getClass().getName() + " has short overview", scenario.getOverview().length() > 2);
            assertFalse("Scenario overview '" + scenario.getName() + "' already used", text.contains(scenario.getOverview()));
            text.add(scenario.getOverview());
        }
    }

    @Test
    public void DescriptionTest() {
        for (Scenario scenario : scenarioList) {
            assertNotNull("Scenario " + scenario.getClass().getName() + " has no description", scenario.getDescription());
            assertTrue("Scenario " + scenario.getClass().getName() + " has short description", scenario.getDescription().length() > 2);
        }
    }

    @Test
    public void AgentCountTest() {
        for (Scenario scenario : scenarioList) {
            assertTrue("Scenario " + scenario.getClass().getName() + " has minAgent=0", scenario.getMinAgentCount() > 0);
            assertTrue("Scenario " + scenario.getClass().getName() + " has maxAgent=0", scenario.getMaxAgentCount() > 0);
            assertTrue("Scenario " + scenario.getClass().getName() + " has minAgent>maxAgent", scenario.getMinAgentCount() <= scenario.getMaxAgentCount());
        }
    }

}
