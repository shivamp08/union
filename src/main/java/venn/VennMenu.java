package venn;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class VennMenu {
	public static MenuBar create (VennEntryHandler handler) {
		MenuBar bar = new MenuBar();
		
		Menu menu = new Menu("Venn");
		
		MenuItem add = new MenuItem("Add");
		
		add.setOnAction(event -> {
			Text data = VennAddEntry.display();
			if (data.getText().contentEquals("")) return;

			VennTextEntry entry = new VennTextEntry(data.getText());

			handler.addEntry(entry);
		});
	
		menu.getItems().add(add);
		bar.getMenus().add(menu);
		
		return bar;
	}
}
