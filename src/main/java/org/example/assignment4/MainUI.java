package org.example.assignment4;

import javafx.scene.layout.StackPane;

public class MainUI extends StackPane {

    public MainUI() {

        LineModel model = new LineModel();
        InteractionModel iModel = new InteractionModel();
        DView view = new DView();
        AppController controller = new AppController();

        controller.setModel(model);
        controller.setIModel(iModel);

        view.setModel(model);
        view.setiModel(iModel);
        view.setupEvents(controller);

        model.addSubscriber(view);
        iModel.addSubscriber(view);

        this.getChildren().add(view);
    }

}
