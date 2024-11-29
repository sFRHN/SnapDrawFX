package org.example.assignment4;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AppController {

    private enum State {READY, CREATING}
    private State currentState = State.READY;
    private LineModel model;
    private InteractionModel iModel;
    private double prevX, prevY, snapx, snapy;

    public AppController() {}

    public void setModel(LineModel model) {
        this.model = model;
    }

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }

    public void handlePressed(MouseEvent e) {
        switch (currentState) {
            case READY:
                snap(e.getX(), e.getY());
                DLine line = model.addLine(snapx, snapy, snapx, snapy);
                iModel.setSelected(line);
                currentState = State.CREATING;
                break;
        }
    }

    public void handleDragged(MouseEvent e) {
        switch (currentState) {
            case CREATING:
                model.adjustLine(iModel.getSelected(), e.getX(), e.getY());
                currentState = State.CREATING;
                break;
        }
    }

    public void handleReleased(MouseEvent e) {
        switch (currentState) {
            case CREATING:
                snap(e.getX(), e.getY());
                model.adjustLine(iModel.getSelected(), snapx, snapy);
                currentState = State.READY;
                break;
        }
    }

    public void handleKeyPressed(KeyEvent e) {}

    public void handleKeyReleased(KeyEvent e) {}

    public void snap(double x, double y) {
        Endpoint ep = model.findNearPoint(x, y);
        if (ep != null) {
            snapx = ep.x;
            snapy = ep.y;
        } else {
            snapx = x;
            snapy = y;
        }
    }

}
