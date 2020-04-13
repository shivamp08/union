package venn;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

import static venn.Main.changeHandler;
import static venn.Main.gameModeHandler;

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
            // no removing when in game mode
            if (gameModeHandler.running.get()) return;

            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        pane.setOnDragDropped(event -> {
            if (gameModeHandler.running.get()) return;

            VennTextEntry entry = this.handler.getEntryById(event.getDragboard().getString());

            System.out.println("change from deletion");
            changeHandler.calculateChange();

            System.out.println(entry);

            if (entry.section != null) {
                entry.section.elements.remove(entry);

                Parent parent = entry.draggable.getParent();

                if (parent != null) {
                    ((Group) parent).getChildren().remove(entry.draggable);
                }
                this.handler.deleteEntry(entry, false);
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
