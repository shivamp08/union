package venn;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import org.junit.Test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.junit.*;

public class VennTest {

    private Main frame;
    private Thread thread;
    
    @Before
    public void setUp() throws Exception {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
							frame = new Main();
							frame.start(new Stage());
							System.out.println(frame);
						} catch (Exception e) {
							e.printStackTrace();
						}
                    }
                });
            }
        });
        thread.start();// Initialize the thread
        Thread.sleep(1000); // Time to use the app, with out this, the thread
                                // will be killed before you can tell.
    }
    
    @Test
    public void testAddEntry() throws InterruptedException {
    	Thread.sleep(1000);
    	Platform.runLater(() -> {
    		frame.entries.addEntry(
    			new VennTextEntry("hello")
    		);
    	});
        Thread.sleep(1000);
    }
    
    @Test
    public void testAddMultipleEntries() throws InterruptedException {
    	Thread.sleep(1000);
    	Platform.runLater(() -> {
    		frame.entries.addEntry(
    			new VennTextEntry("hello")
    		);
    		frame.entries.addEntry(
    			new VennTextEntry("hello2")
    		);
    		frame.entries.addEntry(
    			new VennTextEntry("hello3")
    		);
    	});
        Thread.sleep(1000);
    }
    
    @Test
    public void testMoveMouseHover() throws InterruptedException {
    	Thread.sleep(1000);
    	Platform.runLater(() -> {
    		try {
				Robot robot = new Robot();
				robot.mouseMove(900, 500);
				robot.delay(2000);
				robot.mouseMove(500, 500);
				robot.delay(2000);
				robot.mouseMove(1200, 500);
				robot.delay(2000);
			} catch (AWTException ignored) {}
    	});
    	Thread.sleep(1000);
    }
    
//    @Ignore
//    @Test
//    public void testAddEntryDialog() throws InterruptedException {
//    	Thread.sleep(1000);
//    	Platform.runLater(() -> {
//    		VennAddEntry.display();
//    	});
//    	Thread.sleep(1000);
//    }

    @Test
    public void testDnd() throws InterruptedException {
    	Thread.sleep(1000);
    	Platform.runLater(() -> {
    		frame.entries.addEntry(
    			new VennTextEntry("hello")
    		);
    		frame.entries.addEntry(
    			new VennTextEntry("hello2")
    		);
    		frame.entries.addEntry(
    			new VennTextEntry("hello3")
    		);
    	});
    	Thread.sleep(1000);
    	Platform.runLater(() -> {
    		try {
				Robot robot = new Robot();
				robot.delay(1000);
				robot.mouseMove(500, 500);
				robot.delay(1000);
				Thread.sleep(1000);
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				Thread.sleep(1000);
			} catch (Exception ignored) {}
    	});
    	Thread.sleep(1000);
    }
    
}