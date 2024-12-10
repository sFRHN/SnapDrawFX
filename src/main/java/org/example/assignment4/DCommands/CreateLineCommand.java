/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

package org.example.assignment4.DCommands;

import org.example.assignment4.DCommand;
import org.example.assignment4.DLine;
import org.example.assignment4.LineModel;

public class CreateLineCommand implements DCommand {

    private final LineModel model;
    private DLine item;
    private double mx, my;


    /**
     * Constructor for CreateLineCommand
     */
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