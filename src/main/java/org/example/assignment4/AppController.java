package org.example.assignment4;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AppController {

    private LineModel model;
    private InteractionModel iModel;
    private ControllerState currentState;
    private double prevX, prevY, snapx, snapy;


    public abstract static class ControllerState {
        void handleMoved(MouseEvent event) {}
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

    public void handleMoved(MouseEvent event) { currentState.handleMoved(event); }
    public void handlePressed(MouseEvent event) { currentState.handlePressed(event); }
    public void handleDragged(MouseEvent event) { currentState.handleDragged(event); }
    public void handleReleased(MouseEvent event) { currentState.handleReleased(event); }
    public void handleKeyPressed(KeyEvent event) { currentState.handleKeyPressed(event); }
    public void handleKeyReleased(KeyEvent event) { currentState.handleKeyReleased(event); }



    ControllerState ready = new ControllerState() {

        @Override
        void handlePressed(MouseEvent e) {

            prevX = e.getX();
            prevY = e.getY();

            if (e.isShiftDown()) {
                snap(e.getX(), e.getY());
            }
            else if (iModel.getSelected() != null && iModel.onHandle(e.getX(), e.getY())) {
                currentState = resizing;
            }
            else  if (model.overLine(e.getX(), e.getY()) != null) {
                iModel.setSelected(model.overLine(e.getX(), e.getY()));
            }
            else {
                iModel.setSelected(null);
            }
        }

        @Override
        void handleDragged(MouseEvent e) {
            if (e.isShiftDown()) {
                DLine line = model.addLine(snapx, snapy, snapx, snapy);
                iModel.setSelected(line);
                currentState = creating;
            }
            else if (iModel.getSelected() != null) {
                currentState = dragging;
            }
        }


        @Override
        void handleKeyPressed(KeyEvent e) {
            switch (e.getCode()) {

                case DELETE:
                case BACK_SPACE:
                    model.deleteLine(iModel.getSelected());
                    break;
                case UP:
                    model.scaleLine(iModel.getSelected(),"up");
                    break;
                case DOWN:
                    model.scaleLine(iModel.getSelected(),"down");
                    break;
                case LEFT:
                    model.rotateLine(iModel.getSelected(),"counterclockwise");
                    break;
                case RIGHT:
                    model.rotateLine(iModel.getSelected(),"clockwise");
                    break;
                default:
                    break;
            }
        }


        @Override
        void handleMoved(MouseEvent e) {
            DLine line = model.overLine(e.getX(), e.getY());
            if (line != null) {
                iModel.setHovered(line);
            }
            else {
                iModel.setHovered(null);
            }
        }

    };


    ControllerState creating = new ControllerState() {

        @Override
        void handleDragged(MouseEvent e) {
            model.adjustLine(iModel.getSelected(), e.getX(), e.getY());
        }

        @Override
        void handleReleased(MouseEvent e) {
            snap(e.getX(), e.getY());
            model.adjustLine(iModel.getSelected(), snapx, snapy);
            currentState = ready;
        }

    };



    ControllerState dragging = new ControllerState() {

        @Override
        void handleDragged(MouseEvent e) {

            double dx = e.getX() - prevX;
            double dy = e.getY() - prevY;

            model.moveLine(iModel.getSelected(), dx, dy);

            prevX = e.getX();
            prevY = e.getY();

        }

        @Override
        void handleReleased(MouseEvent e) {
            currentState = ready;
        }

    };



    ControllerState resizing = new ControllerState() {

        Endpoint ep;

        @Override
        void handleDragged(MouseEvent e) {
            ep = iModel.whichEndPoint(prevX, prevY);
            iModel.updatePosition(ep, e.getX(), e.getY());

            prevX = e.getX();
            prevY = e.getY();
        }

        @Override
        void handleReleased(MouseEvent e) {
            snap(e.getX(), e.getY());
            iModel.updatePosition(ep, snapx, snapy);
            currentState = ready;
        }

    };


    public void snap(double x, double y) {
        Endpoint ep = model.findGrid(x, y);
        snapx = ep.getX();
        snapy = ep.getY();
    }

}
