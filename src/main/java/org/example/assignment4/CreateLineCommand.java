package org.example.assignment4;

public class CreateLineCommand implements DCommand {

    private final LineModel model;
    private DLine item;
    private double mx, my;

    public CreateLineCommand(LineModel model, DLine item) {
        this.model = model;
        this.item = item;
    }

    public void doIt() {
        model.addItem(item);
    }

    public void undo() {
        model.deleteItem(item);
    }
}