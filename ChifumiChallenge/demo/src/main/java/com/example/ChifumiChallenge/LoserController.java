package com.example.ChifumiChallenge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class LoserController {
    @FXML
    private ImageView imageLoser;
    @FXML
    private Button relancerButton;

    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

    public void initialize() throws IOException {
        Image image = new Image(getClass().getResourceAsStream("lose.gif"));
        imageLoser.setImage(image);
    }

    public void gameOnAction(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ChifumiChallenge/AutentificationView.fxml"));
        try {
            Parent nouvelleSceneParent = loader.load();
            Scene nouvelleScene = new Scene(nouvelleSceneParent);
            Stage stage = (Stage) relancerButton.getScene().getWindow();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            stage.centerOnScreen();
            stage.setScene(nouvelleScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}