package dev.aisandbox.client.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;

import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.scenarios.maze.MazeScenario;
import dev.aisandbox.client.scenarios.maze.MazeSize;
import dev.aisandbox.client.scenarios.maze.MazeType;
import dev.aisandbox.client.scenarios.mine.MineHunterScenario;
import dev.aisandbox.client.scenarios.mine.SizeEnum;

public class PropertiesParserTest {


    @Test
    public void readMine1Test() {
        Properties props = new Properties();
        props.setProperty("scenario", "mine");
        props.setProperty("salt", "123456");
        props.setProperty("size", "small");
        PropertiesParser parser = new PropertiesParser();
        RuntimeModel model = parser.parseConfiguration(new RuntimeModel(),props);
        assertTrue("incorrect scenario",model.getScenario() instanceof MineHunterScenario);
        MineHunterScenario mine = (MineHunterScenario) model.getScenario();
        assertEquals("Incorrect salt",123456,(long) mine.getScenarioSalt());
        assertEquals("Incorrect board size",SizeEnum.SMALL,mine.getMineHunterBoardSize());
    }

        @Test
    public void readMine2Test() {
        Properties props = new Properties();
        props.setProperty("scenario", "mine");
        props.setProperty("salt", "234567");
        props.setProperty("size", "medium");
        PropertiesParser parser = new PropertiesParser();
        RuntimeModel model = parser.parseConfiguration(new RuntimeModel(),props);
        assertTrue("incorrect scenario",model.getScenario() instanceof MineHunterScenario);
        MineHunterScenario mine = (MineHunterScenario) model.getScenario();
        assertEquals("Incorrect salt",234567,(long) mine.getScenarioSalt());
        assertEquals("Incorrect board size",SizeEnum.MEDIUM,mine.getMineHunterBoardSize());
    }

        @Test
    public void readMine3Test() {
        Properties props = new Properties();
        props.setProperty("scenario", "mine");
        props.setProperty("salt", "654321");
        props.setProperty("size", "large");
        PropertiesParser parser = new PropertiesParser();
        RuntimeModel model = parser.parseConfiguration(new RuntimeModel(),props);
        assertTrue("incorrect scenario",model.getScenario() instanceof MineHunterScenario);
        MineHunterScenario mine = (MineHunterScenario) model.getScenario();
        assertEquals("Incorrect salt",654321,(long) mine.getScenarioSalt());
        assertEquals("Incorrect board size",SizeEnum.LARGE,mine.getMineHunterBoardSize());
    }

     @Test
    public void readMaze1Test() {
        Properties props = new Properties();
        props.setProperty("scenario", "maze");
        props.setProperty("salt", "654321");
        props.setProperty("size", "small");
        props.setProperty("type","binarytree");
        PropertiesParser parser = new PropertiesParser();
        RuntimeModel model = parser.parseConfiguration(new RuntimeModel(),props);
        assertTrue("incorrect scenario",model.getScenario() instanceof MazeScenario);
        MazeScenario maze = (MazeScenario) model.getScenario();
        assertEquals("Incorrect salt",654321,(long) maze.getScenarioSalt());
        assertEquals("Incorrect board size",MazeSize.SMALL,maze.getMazeSize());
        assertEquals("Inncorrect maze type",MazeType.BINARYTREE,maze.getMazeType());
    }
     @Test
    public void readMaze2Test() {
        Properties props = new Properties();
        props.setProperty("scenario", "maze");
        props.setProperty("salt", "654321");
        props.setProperty("size", "medium");
        props.setProperty("type","sidewinder");
        PropertiesParser parser = new PropertiesParser();
        RuntimeModel model = parser.parseConfiguration(new RuntimeModel(),props);
        assertTrue("incorrect scenario",model.getScenario() instanceof MazeScenario);
        MazeScenario maze = (MazeScenario) model.getScenario();
        assertEquals("Incorrect salt",654321,(long) maze.getScenarioSalt());
        assertEquals("Incorrect board size",MazeSize.MEDIUM,maze.getMazeSize());
        assertEquals("Inncorrect maze type",MazeType.SIDEWINDER,maze.getMazeType());
    }
         @Test
    public void readMaze3Test() {
        Properties props = new Properties();
        props.setProperty("scenario", "maze");
        props.setProperty("salt", "654321");
        props.setProperty("size", "large");
        props.setProperty("type","recursivebacktracker");
        PropertiesParser parser = new PropertiesParser();
        RuntimeModel model = parser.parseConfiguration(new RuntimeModel(),props);
        assertTrue("incorrect scenario",model.getScenario() instanceof MazeScenario);
        MazeScenario maze = (MazeScenario) model.getScenario();
        assertEquals("Incorrect salt",654321,(long) maze.getScenarioSalt());
        assertEquals("Incorrect board size",MazeSize.LARGE,maze.getMazeSize());
        assertEquals("Inncorrect maze type",MazeType.RECURSIVEBACKTRACKER,maze.getMazeType());
    }

            @Test
    public void readMaze4Test() {
        Properties props = new Properties();
        props.setProperty("scenario", "maze");
        props.setProperty("salt", "654321");
        props.setProperty("size", "large");
        props.setProperty("type","braided");
        PropertiesParser parser = new PropertiesParser();
        RuntimeModel model = parser.parseConfiguration(new RuntimeModel(),props);
        assertTrue("incorrect scenario",model.getScenario() instanceof MazeScenario);
        MazeScenario maze = (MazeScenario) model.getScenario();
        assertEquals("Incorrect salt",654321,(long) maze.getScenarioSalt());
        assertEquals("Incorrect board size",MazeSize.LARGE,maze.getMazeSize());
        assertEquals("Inncorrect maze type",MazeType.BRAIDED,maze.getMazeType());
    }

    @Test
    public void readLimitTest() {
        Properties props = new Properties();
        props.setProperty("steps", "100");
        PropertiesParser parser = new PropertiesParser();
        RuntimeModel model = parser.parseConfiguration(new RuntimeModel(),props);
        assertTrue("Step limit not set",model.isLimitRuntime());
        assertEquals("Step limit incorrect",100,model.getMaxStepCount());
    }

    @Test
    public void readNoLimitTest() {
        Properties props = new Properties();
        PropertiesParser parser = new PropertiesParser();
        RuntimeModel model = parser.parseConfiguration(new RuntimeModel(),props);
        assertFalse("Step limit set",model.isLimitRuntime());
    }
}