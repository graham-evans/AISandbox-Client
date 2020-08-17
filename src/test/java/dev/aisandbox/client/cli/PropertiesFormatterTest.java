package dev.aisandbox.client.cli;

import static org.junit.Assert.assertTrue;

import dev.aisandbox.launcher.AISandboxCLI;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AISandboxCLI.class})
@SpringBootTest
@Slf4j
public class PropertiesFormatterTest {

  @Autowired PropertiesFormatter formatter;

  @Test
  public void testText() {
    String text = formatter.getPropertiesDescription();
    log.info("Found Text");
    log.info(text);
    assertTrue("Text must content", text.length() > 0);
  }

}
