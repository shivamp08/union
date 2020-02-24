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
import java.util.concurrent.CountDownLatch;

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
    public void manualTest() throws InterruptedException {
    	Platform.runLater(() -> {
    		frame.entries.addEntry(
    			new VennTextEntry("manual test")
    		);
    		frame.entries.addEntry(
    			new VennTextEntry("drag me 1")
    		);
    		frame.entries.addEntry(
    			new VennTextEntry("drag me 2")
    		);
    	});
    	Thread.sleep(60000);
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
    		for (int i = 0; i < 100; i++) {
    			frame.entries.addEntry(
	    			new VennTextEntry("entry #" + i)
	    		);
    		}
    	});
        Thread.sleep(1000);
    }

    @Test
    public void testMoveMouseHover() throws InterruptedException {
    	Thread.sleep(1000);
    	try {
			runAndWaitOnJavaFx(() -> {
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
		} catch (Throwable ignored) {}
    	Thread.sleep(1000);
    }

    @Test
    public void testAddEntryDialog() throws Throwable {
    	Thread.sleep(1000);
    	try {
			runAndWaitOnJavaFx(() -> {
				VennEntryModalHandler.add(frame.entries);
			});
		} catch (Exception ignored) {}
    	Thread.sleep(1000);
    }

    private void runAndWaitOnJavaFx(Runnable guiWorker) throws Throwable {
    	final CountDownLatch latchToWaitForJavaFx = new CountDownLatch(1);
    	final Throwable[] javaFxException = {null};
    	Platform.runLater(() -> {
    		try {
    			guiWorker.run();
    		} catch (Throwable e) {
    			javaFxException[0] = e;
    		} finally {
    			latchToWaitForJavaFx.countDown();
    		}
    	});
    	latchToWaitForJavaFx.await();
    	if (javaFxException[0] != null) {
    		throw javaFxException[0];
    	}
    }

}