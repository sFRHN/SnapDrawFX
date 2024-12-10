/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

package org.example.assignment4.DCommands;

import org.example.assignment4.*;

import java.util.List;

public class GroupCommand implements DCommand {

    private LineModel model;
    private Groupable group;
    private InteractionModel iModel;

    /**
     * Constructor for GroupCommand
     */
    public GroupCommand(LineModel model, InteractionModel iModel, DGroup group) {
        this.model = model;
        this.iModel = iModel;
        this.group = group;
    }

    public void doIt() {
        model.deleteItemList(group.getChildren());
        model.addItem(group);
        iModel.clearSelected();
        iModel.setSelected(group);
    }


    public void undo() {
        if (model.getItems().contains(group)) {
            iModel.clearSelected();
            model.deleteItem(group);
            model.addItemList(group.getChildren());
            for (Groupable child : group.getChildren()) {
                iModel.setSelected(child);
            }
        }
    }

}
