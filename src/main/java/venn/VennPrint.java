package venn;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.print.Printer;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;

public class VennPrint {
	Main app; 
	
	ChoiceDialog<Printer> printerList;
	
	public VennPrint(Main app) {
		this.app = app; 
	}
	
	public BufferedImage bufferedScreenshot() {
        Group vennGroup = this.app.vennGroup;
        WritableImage writableImage = new WritableImage(
            (int) vennGroup.getBoundsInParent().getWidth(),
            (int) vennGroup.getBoundsInParent().getHeight()
        );
        SnapshotParameters spa = new SnapshotParameters();
        Image snapshot = vennGroup.snapshot(spa, writableImage);
        spa.setTransform(Transform.scale(5, 5));
        
        BufferedImage buffImg = SwingFXUtils.fromFXImage(snapshot, null); 
        return buffImg; 
	}
	
	public void printImage() {
		PrinterJob printJob = PrinterJob.getPrinterJob(); 
		
		BufferedImage image = bufferedScreenshot();
		
		if (printJob == null) {
			return; 
		}
		
		boolean proceed = printJob.printDialog(); 
		if (proceed) {
		    printJob.setPrintable(new Printable() {
				@Override
				public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			          pageFormat.setOrientation(PageFormat.LANDSCAPE);
			          int x = (int) Math.ceil(pageFormat.getImageableX());
			          int y = (int) Math.ceil(pageFormat.getImageableY());
			          
			          System.out.println(x  + " " + y);
			          System.out.println(image.getWidth() + " " + image.getHeight());
			          
			          if (pageIndex != 0) {
			            return NO_SUCH_PAGE;
			          }
			          graphics.drawImage(image, x, y, image.getHeight(), image.getWidth(), null);
			          return PAGE_EXISTS;
				}
		    });
	    	try {
	    		printJob.setJobName("Screenshot");
	    		printJob.print();
	    	} catch (PrinterException e) {
	    		e.printStackTrace();
	    	}
		}
	}

}
