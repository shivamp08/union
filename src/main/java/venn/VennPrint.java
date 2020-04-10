package venn;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.print.Printer;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Optional;

import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;
import javafx.stage.Window;

public class VennPrint {
	Main app; 
	
	//list
	ChoiceDialog<Printer> printerList;
	
	public VennPrint(Main app) {
		this.app = app; 
	}
	
	public Printer listPrinters() {
		ChoiceDialog<Printer> dialog = new ChoiceDialog<Printer>(Printer.getDefaultPrinter(), Printer.getAllPrinters());
		dialog.setHeaderText(VennInternationalization.get("print_list_header"));
		dialog.setContentText(VennInternationalization.get("print_list_context"));
		dialog.setTitle(VennInternationalization.get("print_list_title"));
		Optional<Printer> choice = dialog.showAndWait();
		if (choice.isPresent()) {
			Printer printer = choice.get(); 
			return printer; 
		}
		return null;
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
		Printer printer = listPrinters();
		if (printer != null) {
	        ButtonType printButton = new ButtonType(VennInternationalization.get("print_warning_proceed"));
	        ButtonType cancel = new ButtonType(VennInternationalization.get("print_warning_cancel"));
	        
	        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", printButton, cancel);
	        
            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(e -> alert.hide());
	        
			alert.setTitle("Print Warning");
			alert.setContentText(VennInternationalization.get("print_warning_context") + " " + printer.getName() + "\n\n" + VennInternationalization.get("print_warning_question"));
			Optional<ButtonType> choice = alert.showAndWait();
			
			if (choice.isPresent() && choice.get() == printButton) {
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
			else if (choice.isPresent() && choice.get() == cancel) {
				alert.hide();
			}
		}
	}

}
