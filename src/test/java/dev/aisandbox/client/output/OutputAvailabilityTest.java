package dev.aisandbox.client.output;

import static org.junit.Assert.assertFalse;

import dev.aisandbox.launcher.AISandboxCLI;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/** Tests to be run on all available FrameOutput implementations */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AISandboxCLI.class})
@SpringBootTest
public class OutputAvailabilityTest {

  @Autowired private ApplicationContext appContext;

  @Autowired private List<FrameOutput> outputList;

  @Test
  public void UniqueNameTest() {
    Set<String> names = new HashSet<>();
    for (FrameOutput out : outputList) {
      assertFalse(
          "Name for " + out.getClass().getName() + " is not unique",
          names.contains(out.getName(Locale.UK)));
      names.add(out.getName(Locale.UK));
    }
  }
}
