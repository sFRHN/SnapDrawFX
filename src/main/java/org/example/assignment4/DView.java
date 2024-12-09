package org.example.assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DView extends StackPane implements Subscriber {

    private LineModel model;
    private InteractionModel iModel;
    private final GraphicsContext gc;
    private final Canvas myCanvas;

    public DView() {
        myCanvas = new Canvas(1000, 800);
        gc = myCanvas.getGraphicsContext2D();
        this.getChildren().add(myCanvas);
    }

    public void setModel(LineModel model) { this.model = model; }
    public void setiModel(InteractionModel iModel) { this.iModel = iModel;}

    public void setupEvents(AppController controller) {
        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseDragged(controller::handleDragged);
        myCanvas.setOnMouseReleased(controller::handleReleased);
        myCanvas.setOnMouseMoved(controller::handleMoved);
        setOnKeyPressed(controller::handleKeyPressed);
        setOnKeyReleased(controller::handleKeyReleased);
    }

    private void draw() {
        int gridSize = 20;
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        gc.setLineWidth(1);
        gc.setLineDashes(0);
        gc.setStroke(new Color(0,0,0,0.1));

        // Draw the grid
        for (int i = 0; i < myCanvas.getWidth(); i += gridSize) {
            gc.strokeLine(i, 0, i, myCanvas.getHeight());
        }
        for (int i = 0; i < myCanvas.getHeight(); i += gridSize) {
            gc.strokeLine(0, i, myCanvas.getWidth(), i);
        }

        model.getItems().forEach(item -> {
            if (iModel.getSelected().contains(item)) {
                item.draw(gc, true);
            }
            else {
                item.draw(gc, false);
            }
            if (iModel.getHovered() == (DLine)item) {
                gc.setStroke(Color.rgb(128, 128, 128, 0.25));
                gc.setLineWidth(10);
                gc.strokeLine(((DLine)item).getX1(), ((DLine)item).getY1(), ((DLine)item).getX2(), ((DLine)item).getY2());
            }
        });

        // Draw the rubberband
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.setLineDashes(10);
        gc.strokeRect(iModel.getRubberBand().getX(), iModel.getRubberBand().getY(), iModel.getRubberBand().getWidth(), iModel.getRubberBand().getHeight());

    }

    public void modelUpdated() {
        draw();
    }

}
