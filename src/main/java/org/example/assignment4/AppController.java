package org.example.assignment4;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AppController {

    private enum State {READY, CREATING}
    private LineModel model;
    private InteractionModel iModel;
    private ControllerState currentState;
    private double prevX, prevY, snapx, snapy;


    public abstract static class ControllerState {
        void handlePressed(MouseEvent event) {}
        void handleDragged(MouseEvent event) {}
        void handleReleased(MouseEvent event) {}
        void handleKeyPressed(KeyEvent event) {}
        void handleKeyReleased(KeyEvent event) {}
    }

    public AppController() { currentState = ready; }

    public void setModel(LineModel model) {
        this.model = model;
    }

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }

    public void handlePressed(MouseEvent event) { currentState.handlePressed(event); }
    public void handleDragged(MouseEvent event) { currentState.handleDragged(event); }
    public void handleReleased(MouseEvent event) { currentState.handleReleased(event); }
    public void handleKeyPressed(KeyEvent event) { currentState.handleKeyPressed(event); }
    public void handleKeyReleased(KeyEvent event) { currentState.handleKeyReleased(event); }



    ControllerState ready = new ControllerState() {

        @Override
        void handlePressed(MouseEvent event) {

            if (event.isShiftDown()) {
                snap(event.getX(), event.getY());
                DLine line = model.addLine(snapx, snapy, snapx, snapy);
                iModel.setSelected(line);
                currentState = creating;
            }

        }

    };


    ControllerState creating = new ControllerState() {

        @Override
        void handleDragged(MouseEvent event) {
            model.adjustLine(iModel.getSelected(), event.getX(), event.getY());
        }

        @Override
        void handleReleased(MouseEvent event) {
            snap(event.getX(), event.getY());
            model.adjustLine(iModel.getSelected(), snapx, snapy);
            currentState = ready;
        }

    };


    public void snap(double x, double y) {
        Endpoint ep = model.findGrid(x, y);
        snapx = ep.x;
        snapy = ep.y;

    }

}
