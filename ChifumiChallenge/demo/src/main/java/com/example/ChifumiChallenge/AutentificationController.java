package com.example.ChifumiChallenge;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AutentificationController {
    @FXML
    private TextField playerNameTextField;
    @FXML
    private ComboBox nbRoundsComboBox;
    @FXML
    private Button playButton;

    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

    public void initialize(){
        playerNameTextField.setPrefWidth(50);
        initializeComboBox(nbRoundsComboBox);
        nbRoundsComboBox.setValue(3);
    }
    public void initializeComboBox(ComboBox comboBox){
        ObservableList<String> comboBoxItems = comboBox.getItems();
        for (int i = 1; i <= 100; i++) {
            comboBoxItems.add(String.valueOf(i));
        }
        comboBox.setItems(comboBoxItems);
    }
    public void textFieldOnAction(){
        playButton.setDisable(false);
        playerNameTextField.getText();
    }
    public void writeUserOnFile() throws IOException {
        String outputFile = "C:\\Users\\lieto\\Desktop\\Projets\\IHM\\ChifumiChallenge\\demo\\src\\main\\resources\\com\\example\\ChifumiChallenge\\utilisateur.txt";  // Chemin vers le fichier de sortie
        StringBuilder modifiedContent = new StringBuilder();
        modifiedContent.append(playerNameTextField.getText());
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.write(modifiedContent.toString());
        writer.close();
    }
    public void writeNbRoundOnFile() throws IOException {
        String outputFile = "C:\\Users\\lieto\\Desktop\\Projets\\IHM\\ChifumiChallenge\\demo\\src\\main\\resources\\com\\example\\ChifumiChallenge\\rounds.txt";  // Chemin vers le fichier de sortie
        StringBuilder modifiedContent = new StringBuilder();
        modifiedContent.append(nbRoundsComboBox.getValue());
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.write(modifiedContent.toString());
        writer.close();
    }
    public void changeToMainView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ChifumiChallenge/MainView.fxml"));
        try {
            Parent nouvelleSceneParent = loader.load();
            Scene nouvelleScene = new Scene(nouvelleSceneParent);
            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            stage.setScene(nouvelleScene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void playButtonOnAction() throws IOException {
        writeUserOnFile();
        writeNbRoundOnFile();
        changeToMainView();
    }
}