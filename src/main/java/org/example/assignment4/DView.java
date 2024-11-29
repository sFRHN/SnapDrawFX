package org.example.assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DView extends StackPane implements Subscriber {

    private LineModel model;
    private InteractionModel iModel;
    private GraphicsContext gc;
    private Canvas myCanvas;
    private int gridSize = 50;

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
        myCanvas.setOnKeyPressed(controller::handleKeyPressed);
        myCanvas.setOnKeyReleased(controller::handleKeyReleased);
    }

    private void draw() {
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        gc.setStroke(Color.BLACK);

        // Draw the grid
        for (int i = 0; i < myCanvas.getWidth(); i += gridSize) {
            gc.strokeLine(i, 0, i, myCanvas.getHeight());
        }
        for (int i = 0; i < myCanvas.getHeight(); i += gridSize) {
            gc.strokeLine(0, i, myCanvas.getWidth(), i);
        }

        // Draw the lines
        model.getLines().forEach(line -> {
            gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        });
    }

    public void modelUpdated() {
        draw();
    }

}
