package venn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The class for Gson to import and export, for easy handling
 */
public class VennExport {
    @SerializedName("e")
    @Expose
    VennEntryHandler elements;
    @SerializedName("r")
    @Expose
    VennSectionRight right;
    @SerializedName("l")
    @Expose
    VennSectionLeft left;
    @SerializedName("i")
    @Expose
    VennIntersection intersection;

    public VennExport (VennEntryHandler handler, VennSectionRight right, VennSectionLeft left, VennIntersection intersection) {
        this.elements = handler;
        this.right = right;
        this.left = left;
        this.intersection = intersection;
    }
}
