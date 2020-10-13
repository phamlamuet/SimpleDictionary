package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Vector;

public class MyChoiceBox extends Application {
    ComboBox choice;

    @Override
    public void start(Stage primaryStage) throws Exception {
        choice = new ComboBox<>();
        choice.getItems().add("Bashful");
        choice.getItems().add("Doc");
        choice.getItems().add("Dopey");
        choice.getItems().add("Grumpy");
        choice.getItems().add("Happy");
        choice.getItems().add("Sleepy");
        choice.getItems().add("Sneezy");
        choice.setValue("Dopey");
        choice.setEditable(true);
        choice.setOnAction(e -> btnOK_Click());


        ListView<String> listView = new ListView<>();
        listView.setItems(choice.getItems());

        VBox pane = new VBox(10);
        pane.getChildren().addAll(choice, listView);
        Scene scene = new Scene(pane, 100, 100);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void btnOK_Click() {
        String message = "You chose ";
        message += choice.getValue();
        MessageBox.show(message, "Your Favorite Astronaut");
    }
}
