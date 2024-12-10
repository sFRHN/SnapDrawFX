/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

package org.example.assignment4.DCommands;

import org.example.assignment4.*;

import java.util.List;

public class UngroupCommand implements DCommand {

    private LineModel model;
    private Groupable group;
    private InteractionModel iModel;

    public UngroupCommand(LineModel model, InteractionModel iModel, DGroup group) {
        this.model = model;
        this.iModel = iModel;
        this.group = group;
    }

    public void doIt() {
        if (model.getItems().contains(group)) {
            iModel.clearSelected();
            model.deleteItem(group);
            model.addItemList(group.getChildren());
            for (Groupable child : group.getChildren()) {
                iModel.setSelected(child);
            }
        }
    }


    public void undo() {
        model.deleteItemList(group.getChildren());
        model.addItem(group);
        iModel.clearSelected();
        iModel.setSelected(group);
    }

}
