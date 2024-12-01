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
        gc.setStroke(new Color(0,0,0,0.1));

        // Draw the grid
        for (int i = 0; i < myCanvas.getWidth(); i += gridSize) {
            gc.strokeLine(i, 0, i, myCanvas.getHeight());
        }
        for (int i = 0; i < myCanvas.getHeight(); i += gridSize) {
            gc.strokeLine(0, i, myCanvas.getWidth(), i);
        }
        model.getLines().forEach(this::drawLines);
    }

    private void drawLines(DLine line) {

        gc.setLineWidth(2);
        if (iModel.getSelected() == line) {
            gc.setStroke(Color.PINK);
        } else {
            gc.setStroke(Color.MEDIUMPURPLE);
        }
        gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());

        if (iModel.getHovered() == line) {
            gc.setStroke(Color.rgb(128, 128, 128, 0.25));
            gc.setLineWidth(10);
            gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        }

        if (iModel.getSelected() == line) {
            drawHandles(line);
        }


    }

    private void drawHandles(DLine line) {
        gc.setFill(Color.WHITE);
        gc.setLineWidth(2);
        double circleRadius = iModel.getRadius();
        gc.strokeOval(line.getX1()- circleRadius, line.getY1() - circleRadius, 2 * circleRadius, 2 * circleRadius);
        gc.strokeOval(line.getX2() - circleRadius, line.getY2() - circleRadius, 2 * circleRadius, 2 * circleRadius);
        gc.fillOval(line.getX1()- circleRadius, line.getY1() - circleRadius, 2 * circleRadius, 2 * circleRadius);
        gc.fillOval(line.getX2() - circleRadius, line.getY2() - circleRadius, 2 * circleRadius, 2 * circleRadius);
    }



    public void modelUpdated() {
        draw();
    }

}
