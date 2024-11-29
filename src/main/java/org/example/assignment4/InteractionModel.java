package org.example.assignment4;

public class InteractionModel {

    private DLine selected;

    public InteractionModel() {
        selected = null;
    }

    public DLine getSelected() {
        return selected;
    }

    public void setSelected(DLine line) {
        selected = line;
    }

    public void clearSelection() {
        selected = null;
    }

}
