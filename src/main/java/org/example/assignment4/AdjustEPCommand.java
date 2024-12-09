package org.example.assignment4;

public class AdjustEPCommand implements DCommand {

    private final LineModel model;
    private final Endpoint ep;
    private double startX, startY, endX, endY;

    public AdjustEPCommand(LineModel model, Endpoint ep, double startX, double startY, double endX, double endY) {
        this.model = model;
        this.ep = ep;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void doIt() {
        model.updatePosition(ep, endX, endY);
    }


    public void undo() {
        model.updatePosition(ep, startX, startY);
    }
}
