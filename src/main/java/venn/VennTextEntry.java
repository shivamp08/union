package venn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.UUID;

//import static venn.VennEntryHandler.getWebColor;

public class VennTextEntry extends Region {
    @SerializedName("s")
    @Expose
    StringProperty string;
    @SerializedName("l")
    @Expose
    EntryLocations location;
    
    @SerializedName("d")
    @Expose
    StringProperty description;
    
    Region draggable;
    HBox pane;
    VennSection section;
    @SerializedName("x")
    @Expose
    double xCoordinate;
    @SerializedName("y")
    @Expose
    double yCoordinate;

    @SerializedName("i")
    @Expose
    String id;

    @SerializedName("c")
    @Expose
    ObjectProperty<Color> draggableColor;
    
    @SerializedName("f")
    @Expose
    ObjectProperty<Font> draggableFont;

    public VennTextEntry(String string, String description, String color, Font font) {
        super();
        this.string = new SimpleStringProperty(string);
        this.description = new SimpleStringProperty();
        if (description != null) {
            this.description.set(description);
        }
        
        draggableColor =  new SimpleObjectProperty<Color>(Color.valueOf(color));
        draggableFont = new SimpleObjectProperty<Font>(font); 

        this.id = UUID.randomUUID().toString();

        this.section = null;

        // the location where it is after creation
        this.location = EntryLocations.Draggable;

        this.xCoordinate = -1;
        this.yCoordinate = -1;
        
        this.draggable = null;

        this.draw();
    }

    public VennTextEntry(String string) {
        this(string, null, null, null);
    }
    
    public void setLocation(EntryLocations location) {
        this.location = location;
    }

    public void setDraggable () {
        StackPane pane = new StackPane();
        Label label = new Label("");
        label.textProperty().bind(this.string);
        label.setTextFill(Color.BLACK);
        label.setFont(draggableFont.getValue());
        
        draggableFont.addListener((observable, oldValue, newValue) -> {
        	label.setFont(draggableFont.getValue());
        });
        

        pane.getStyleClass().add("rounded-label");

        //if (this.draggableColor == null) this.draggableColor = VennEntryHandler.generateColour();

        pane.setStyle("-fx-background-color: " + VennEntryHandler.getWebColor(draggableColor.getValue()));
        
        draggableColor.addListener((observable, oldValue, newValue) -> {
        	pane.setStyle("-fx-background-color: " + VennEntryHandler.getWebColor(draggableColor.getValue()));
        });

        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(this.string);
        Tooltip.install(label, tooltip);

        pane.getChildren().add(label);

        this.initClickHandlers(pane);

        // store the x
        pane.widthProperty().addListener((obs, oldValue, newValue) -> {
            this.positionDraggable(this.xCoordinate, this.yCoordinate, true, false);
        });
        pane.layoutXProperty().addListener((obs, oldValue, newValue) -> {
            this.xCoordinate = (double) newValue;
        });
        pane.layoutYProperty().addListener((obs, oldValue, newValue) -> {
            this.yCoordinate = (double) newValue;
        });

        this.draggable = pane;
    }

    private void initClickHandlers (Pane pane) {
        pane.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                if (event.getClickCount() == 2){
                    VennEntryModalHandler.edit(this.string, this.description, this.draggableColor, this.draggableFont);
                }
            }
        });
    }

    public void positionDraggable (double x, double y, boolean isCenterCoordinate, boolean doCentreY) {
        // if centered around this point, subtract to get the min

        double centerX = x;
        double centerY = y;
        if (isCenterCoordinate) {
            centerX -= (draggable.getWidth() / 2);
            if (doCentreY) centerY -= (draggable.getHeight() / 2);
        }
        draggable.relocate(centerX, centerY);

        this.xCoordinate = x;
    }

    public HBox draw () {
        Label text = new Label();
        text.textProperty().bind(this.string);

        this.pane = new HBox();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.getChildren().addAll(text);

        this.pane.setPadding(new Insets(5));
        this.pane.getStyleClass().add("el-default");
        
        this.pane.setPrefWidth(200);

        this.pane.setUserData(this);
        
        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(this.string);
        Tooltip.install(this.pane, tooltip);

        return this.pane;
    }

    @Override
    public String toString() {
        return "VennTextEntry{" +
                "string=" + string +
                ", x=" + xCoordinate +
                ", y=" + yCoordinate +
                ", location=" + location +
                ", section=" + section +
                '}';
    }
}
