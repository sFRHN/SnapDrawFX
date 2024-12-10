package org.example.assignment4;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.assignment4.DCommands.*;

import java.util.List;

public class AppController {

    private LineModel model;
    private InteractionModel iModel;
    private ControllerState currentState;
    private double startX, startY, prevX, prevY, snapX, snapY, rotateCount = 0, scaleCount = 0;

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
            startX = prevX;
            startY = prevY;

            if (e.isShiftDown()) {
                snap(e.getX(), e.getY());
            }
            else if (iModel.getSelected() != null && iModel.onHandle(e.getX(), e.getY())) {
                currentState = resizing;
            }
            else if (model.overItem(e.getX(), e.getY()) != null) {
                if (!iModel.getSelected().contains(model.overItem(e.getX(), e.getY()))) {
                    iModel.clearSelected();
                    iModel.setSelected(model.overItem(e.getX(), e.getY()));
                }
            }
        }

        void handleReleased(MouseEvent e) {
            if (model.overItem(e.getX(), e.getY()) != null) {
                iModel.clearSelected();
                iModel.setSelected(model.overItem(e.getX(), e.getY()));
            }
            else {
                iModel.clearSelected();
            }
        }

        void handleDragged(MouseEvent e) {
            if (e.isShiftDown()) {

                iModel.clearSelected();
                DLine line = model.createLine(snapX, snapY, snapX, snapY);
                DCommand addCommand = new CreateLineCommand(model, line);
                addCommand.doIt();

                iModel.getUndoStack().push(addCommand);
                iModel.getRedoStack().clear();
                iModel.setSelected(line);

                currentState = creating;
            }
            else if (iModel.getSelected() != null && model.overItem(e.getX(), e.getY()) != null) {
                startX = prevX;
                startY = prevY;
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
                    DCommand delCommand = new DeleteItemCommand(model, iModel.getSelected());
                    iModel.getUndoStack().push(delCommand);
                    iModel.getRedoStack().clear();
                    delCommand.doIt();
                    break;
                case UP:
                    model.scaleItem(iModel.getSelected(),"up");
                    scaleCount++;
                    break;
                case DOWN:
                    model.scaleItem(iModel.getSelected(),"down");
                    scaleCount--;
                    break;
                case LEFT:
                    model.rotateItem(iModel.getSelected(),"counterclockwise");
                    rotateCount--;
                    break;
                case RIGHT:
                    model.rotateItem(iModel.getSelected(),"clockwise");
                    rotateCount++;
                    break;
                case CONTROL:
                    currentState = selecting;
                    break;
                case G:
                    group(iModel.getSelected());
                    break;
                case U:
                    iModel.getSelected()
                            .stream()
                            .filter(item -> item instanceof DGroup)
                            .findFirst().ifPresent(item -> ungroup(item));
                    break;
                case Z:
                    undo();
                    break;
                case R:
                    redo();
                    break;
                default:
                    break;
            }
        }

        void handleKeyReleased(KeyEvent e) {
            switch (e.getCode()) {
                case LEFT:
                case RIGHT:
                    DCommand rotateCommand = new RotateCommand(model, iModel.getSelected(), rotateCount);
                    iModel.getUndoStack().push(rotateCommand);
                    iModel.getRedoStack().clear();
                    rotateCount = 0;
                    break;
                case UP:
                case DOWN:
                    DCommand scaleCommand = new ScaleCommand(model, iModel.getSelected(), scaleCount);
                    iModel.getUndoStack().push(scaleCommand);
                    iModel.getRedoStack().clear();
                    scaleCount = 0;
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
            double oldSnapX = snapX;
            double oldSnapY = snapY;
            snap(e.getX(), e.getY());

            Endpoint ep = iModel.whichEndPoint(e.getX(), e.getY());
            DCommand adjustEPCommand = new AdjustEPCommand(model, ep, oldSnapX, oldSnapY, snapX, snapY);
            adjustEPCommand.doIt();
            iModel.getUndoStack().push(adjustEPCommand);
            iModel.getRedoStack().clear();

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

            DCommand moveCommand = new MoveCommand(model, iModel.getSelected(), startX, startY, e.getX(), e.getY());
            iModel.getUndoStack().push(moveCommand);
            iModel.getRedoStack().clear();

            currentState = ready;
        }

    };


    /* -------------------  RESIZING STATE ------------------- */
    ControllerState resizing = new ControllerState() {

        Endpoint ep;

        void handleDragged(MouseEvent e) {
            ep = iModel.whichEndPoint(prevX, prevY);
            model.updatePosition(ep, e.getX(), e.getY());

            prevX = e.getX();
            prevY = e.getY();
        }

        void handleReleased(MouseEvent e) {
            snap(e.getX(), e.getY());
            ep = iModel.whichEndPoint(prevX, prevY);

            DCommand adjustCommand = new AdjustEPCommand(model, ep, startX, startY, snapX, snapY);
            adjustCommand.doIt();
            iModel.getUndoStack().push(adjustCommand);
            iModel.getRedoStack().clear();
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


    /* -------------------  SNAPPING ------------------- */
    public void snap(double x, double y) {
        Endpoint ep = model.findGrid(x, y);
        snapX = ep.getX();
        snapY = ep.getY();
    }


    /* -------------------  GROUPING / UNGROUPING ------------------- */
    private void group(List<Groupable> items) {
        if (items.isEmpty()) {
            System.out.println("Attempting to group, but no items selected");
        } else {
            DGroup dg = new DGroup(items);
            DCommand groupCommand = new GroupCommand(model, iModel, dg);
            groupCommand.doIt();
            iModel.getUndoStack().push(groupCommand);
            iModel.getRedoStack().clear();
        }
    }

    private void ungroup(Groupable group) {
        if (group.isGroup()) {
            DCommand ungroupCommand = new UngroupCommand(model, iModel, (DGroup) group);
            ungroupCommand.doIt();
            iModel.getUndoStack().push(ungroupCommand);
            iModel.getRedoStack().clear();
        }
        else {
            System.out.println("Attempting to ungroup, but item is not a group");
        }
    }


    /* -------------------  UNDO / REDO ------------------- */
    private void undo() {
        if (!iModel.getUndoStack().isEmpty()) {
            iModel.getRedoStack().push(iModel.getUndoStack().peek());
            iModel.getUndoStack().pop().undo();
        }
        else {
            System.out.println("Undo stack empty");
        }
    }

    private void redo() {
        if (!iModel.getRedoStack().isEmpty()) {
            iModel.getUndoStack().push(iModel.getRedoStack().peek());
            iModel.getRedoStack().pop().doIt();
        }
        else {
            System.out.println("Redo stack empty");
        }
    }

}
