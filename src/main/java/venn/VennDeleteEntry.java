package venn;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

public class VennDeleteEntry {
    VennEntryHandler handler;

    StackPane pane;

    public VennDeleteEntry(VennEntryHandler handler) {
        this.handler = handler;

        this.draw();
        this.initHandlers();
    }

    protected void initHandlers () {
        pane.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        pane.setOnDragDropped(event -> {
            VennTextEntry entry = this.handler.getEntryById(event.getDragboard().getString());

            if (entry.section != null) {
                entry.section.elements.remove(entry);

                Parent parent = entry.draggable.getParent();

                if (parent != null) {
                    ((Group) parent).getChildren().remove(entry.draggable);
                }
                this.handler.deleteEntry(entry);
            } else {
                this.handler.removeFromDragContainer(entry);
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }

    public void draw () {
        //Recyling Bin
        StackPane pane = new StackPane();

        ImageView bin = new ImageView(getClass().getResource("/recyclingbin.png").toExternalForm());
        bin.setFitHeight(50);
        bin.setFitWidth(50);

        pane.getChildren().add(bin);
        this.pane = pane;
    }
}
