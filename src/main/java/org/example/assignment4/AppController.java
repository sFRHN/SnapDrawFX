package org.example.assignment4;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class AppController {

    private LineModel model;
    private InteractionModel iModel;
    private ControllerState currentState;
    private double prevX, prevY, snapX, snapY;

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
                iModel.clearSelected();
                iModel.setSelected(model.overLine(e.getX(), e.getY()));
            }
            else {
                iModel.clearSelected();
            }
        }

        void handleDragged(MouseEvent e) {
            if (e.isShiftDown()) {
                DLine line = model.addLine(snapX, snapY, snapX, snapY);
                iModel.clearSelected();
                iModel.setSelected(line);
                currentState = creating;
            }
            else if (iModel.getSelected() != null && model.overLine(e.getX(), e.getY()) != null) {
                currentState = dragging;
            }
            else {
                currentState = selecting;
            }
        }

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
                case CONTROL:
                    currentState = selecting;
                    break;
                default:
                    break;
            }
        }

        void handleMoved(MouseEvent e) {
            DLine line = model.overLine(e.getX(), e.getY());
            iModel.setHovered(line);
        }

    };


    ControllerState creating = new ControllerState() {

        void handleDragged(MouseEvent e) {
            model.adjustLine(iModel.getSelected().getFirst(), e.getX(), e.getY());
        }

        void handleReleased(MouseEvent e) {
            snap(e.getX(), e.getY());
            model.adjustLine(iModel.getSelected().getFirst(), snapX, snapY);
            currentState = ready;
        }

    };



    ControllerState dragging = new ControllerState() {

        void handleDragged(MouseEvent e) {

            double dx = e.getX() - prevX;
            double dy = e.getY() - prevY;

            model.moveLine(iModel.getSelected(), dx, dy);

            prevX = e.getX();
            prevY = e.getY();

        }

        void handleReleased(MouseEvent e) {
            currentState = ready;
        }

    };



    ControllerState resizing = new ControllerState() {

        Endpoint ep;

        void handleDragged(MouseEvent e) {
            ep = iModel.whichEndPoint(prevX, prevY);
            iModel.updatePosition(ep, e.getX(), e.getY());

            prevX = e.getX();
            prevY = e.getY();
        }

        void handleReleased(MouseEvent e) {
            snap(e.getX(), e.getY());
            iModel.updatePosition(ep, snapX, snapY);
            currentState = ready;
        }

    };


    ControllerState selecting = new ControllerState() {

        void handlePressed(MouseEvent e) {

            prevX = e.getX();
            prevY = e.getY();

            if (model.overLine(e.getX(), e.getY()) != null) {
                iModel.multiSelect(model.overLine(e.getX(), e.getY()));
            }

        }

        void handleDragged(MouseEvent e) {

            double newX = Math.min(e.getX(), prevX);
            double newY = Math.min(e.getY(), prevY);
            double newWidth = Math.abs(e.getX() - prevX);
            double newHeight = Math.abs(e.getY() - prevY);

            iModel.resizeRubberband(newWidth, newHeight);
            iModel.reposRubberband(newX, newY);

        }

        void handleReleased(MouseEvent e) {

            List<DLine> linesWithin;
            linesWithin = iModel.getRubberBand().getLinesWithin(model.getLines());
            System.out.println(linesWithin);

            for (DLine line : linesWithin) {
                if (e.isControlDown()) {
                    iModel.multiSelect(line);
                }
                else {
                    iModel.setSelected(line);
                    currentState = ready;
                }
            }

            iModel.resetRubberband();

        }

        void handleKeyReleased(KeyEvent e) {
            iModel.resetRubberband();
            currentState = ready;
        }

        void handleMoved(MouseEvent e) {
            DLine line = model.overLine(e.getX(), e.getY());
            iModel.setHovered(line);
        }

    };


    public void snap(double x, double y) {
        Endpoint ep = model.findGrid(x, y);
        snapX = ep.getX();
        snapY = ep.getY();
    }

}
