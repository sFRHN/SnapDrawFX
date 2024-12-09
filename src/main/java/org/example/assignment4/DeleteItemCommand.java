package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class DeleteItemCommand implements DCommand {

    private final LineModel model;
    private List<Groupable> items;
    private double mx, my;

    public DeleteItemCommand(LineModel model, List<Groupable> items) {
        this.model = model;
        this.items = new ArrayList<>(items);
    }

    public void doIt() {
        model.deleteItemList(items);
    }

    public void undo() {
        model.addItemList(items);
    }

}
