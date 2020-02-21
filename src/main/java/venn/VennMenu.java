package venn;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class VennMenu {
	public static MenuBar create (VennEntryHandler handler) {
		MenuBar bar = new MenuBar();
		
		Menu menu = new Menu("Venn");
		
		MenuItem add = new MenuItem("Customization");
		
		add.setOnAction(event -> VennAddEntry.add(handler));
	
		menu.getItems().add(add);
		bar.getMenus().add(menu);
		
		return bar;
	}
}
