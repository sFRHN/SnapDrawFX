package org.example.assignment4;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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

    public void setModel(LineModel model) { this.model = model; }
    public void setIModel(InteractionModel iModel) { this.iModel = iModel; }

    public void handleMoved(MouseEvent event) { currentState.handleMoved(event); }
    public void handlePressed(MouseEvent event) { currentState.handlePressed(event); }
    public void handleDragged(MouseEvent event) { currentState.handleDragged(event); }
    public void handleReleased(MouseEvent event) { currentState.handleReleased(event); }
    public void handleKeyPressed(KeyEvent event) { currentState.handleKeyPressed(event); }
    public void handleKeyReleased(KeyEvent event) { currentState.handleKeyReleased(event); }


    /* -------------------  READY STATE ------------------- */
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
            else  if (model.overItem(e.getX(), e.getY()) != null) {
                iModel.clearSelected();
                iModel.setSelected(model.overItem(e.getX(), e.getY()));
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
            else if (iModel.getSelected() != null && model.overItem(e.getX(), e.getY()) != null) {
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
                    model.deleteItem(iModel.getSelected());
                    break;
                case UP:
                    model.scaleItem(iModel.getSelected(),"up");
                    break;
                case DOWN:
                    model.scaleItem(iModel.getSelected(),"down");
                    break;
                case LEFT:
                    model.rotateItem(iModel.getSelected(),"counterclockwise");
                    break;
                case RIGHT:
                    model.rotateItem(iModel.getSelected(),"clockwise");
                    break;
                case CONTROL:
                    currentState = selecting;
                    break;
                default:
                    break;
            }
        }

        void handleMoved(MouseEvent e) {
            Groupable item = model.overItem(e.getX(), e.getY());
            if (item != null && !item.isGroup()) {
                iModel.setHovered((DLine)item);
            }
            else {
                iModel.setHovered(null);
            }
        }

    };


    /* -------------------  CREATING STATE ------------------- */
    ControllerState creating = new ControllerState() {

        void handleDragged(MouseEvent e) {
            model.adjustLine((DLine)(iModel.getSelected().getFirst()), e.getX(), e.getY());
        }

        void handleReleased(MouseEvent e) {
            snap(e.getX(), e.getY());
            model.adjustLine((DLine)(iModel.getSelected().getFirst()), snapX, snapY);
            currentState = ready;
        }

    };


    /* -------------------  DRAGGING STATE ------------------- */
    ControllerState dragging = new ControllerState() {

        void handleDragged(MouseEvent e) {

            double dx = e.getX() - prevX;
            double dy = e.getY() - prevY;

            model.moveItem(iModel.getSelected(), dx, dy);

            prevX = e.getX();
            prevY = e.getY();

        }

        void handleReleased(MouseEvent e) {
            currentState = ready;
        }

    };


    /* -------------------  RESIZING STATE ------------------- */
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
            ep = iModel.whichEndPoint(prevX, prevY);
            iModel.updatePosition(ep, snapX, snapY);
            currentState = ready;
        }

    };


    /* -------------------  SELECTING STATE ------------------- */
    ControllerState selecting = new ControllerState() {

        void handlePressed(MouseEvent e) {

            prevX = e.getX();
            prevY = e.getY();

            if (model.overItem(e.getX(), e.getY()) != null) {
                iModel.multiSelect(model.overItem(e.getX(), e.getY()));
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

            List<Groupable> itemsWithin;
            itemsWithin = iModel.getRubberBand().getItemsWithin(model.getItems());

            if (e.isControlDown()) {
                itemsWithin.forEach(line -> iModel.multiSelect(line));
            } else {
                itemsWithin.forEach(line -> iModel.setSelected(line));
                currentState = ready;
            }

            iModel.resetRubberband();
        }

        void handleKeyReleased(KeyEvent e) {
            iModel.resetRubberband();
            currentState = ready;
        }

        void handleMoved(MouseEvent e) {
            Groupable item = model.overItem(e.getX(), e.getY());
            if (item != null && !item.isGroup()) {
                iModel.setHovered((DLine)item);
            }
            else {
                iModel.setHovered(null);
            }
        }

    };


    public void snap(double x, double y) {
        Endpoint ep = model.findGrid(x, y);
        snapX = ep.getX();
        snapY = ep.getY();
    }

}
