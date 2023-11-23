package com.example.ChifumiChallenge;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class MainController {
    @FXML
    private ImageView imageIA;
    @FXML
    private ImageView imagePlayer;

    @FXML
    private Button papierButton;
    @FXML
    private Button ciseauButton;
    @FXML
    private Button caillouButton;
    @FXML
    private Button chifumiButton;
    @FXML
    private Button rejouerButton;

    @FXML
    private Label winnerLabel;
    @FXML
    private Label playerNameLabel;
    @FXML
    private Label IaNameLabel;
    @FXML
    private Label roundNumberLabel;
    @FXML
    private Label scoreTabLabel;

    String playerName;
    String IaName = "Ordinateur";
    int nbRoundMax;
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

    // Charger l'image depuis un fichier ou une ressource
    Image imagePapier = new Image(getClass().getResourceAsStream("papier.jpg"));
    Image imageCiseau = new Image(getClass().getResourceAsStream("ciseau.jpg"));
    Image imageCaillou = new Image(getClass().getResourceAsStream("caillou.jpg"));

    SimpleIntegerProperty nbrVictoireIA = new SimpleIntegerProperty(0);
    SimpleIntegerProperty nbrVictoirePlayer = new SimpleIntegerProperty(0);
    SimpleIntegerProperty nbrDeTour = new SimpleIntegerProperty(0);


    public void initialize() throws IOException {
        readUserFile();
        readNbRoundFile();

        chifumiButton.setDisable(true);
        rejouerButton.setDisable(true);
        createBindings(nbrDeTour, roundNumberLabel);
        createScoreBindings(nbrDeTour, scoreTabLabel);
        nbrDeTour.set(nbrDeTour.get()+1);
    }

    public boolean nbRoundIsMax(){
        if (nbrVictoireIA.getValue() == nbRoundMax || nbrVictoirePlayer.getValue() == nbRoundMax)
            return true;
        else
            return false;
    }
    public void readUserFile() throws IOException {
        String inputFile = "C:\\Users\\lieto\\Desktop\\Projets\\IHM\\ChifumiChallenge\\demo\\src\\main\\resources\\com\\example\\ChifumiChallenge\\utilisateur.txt";  // Chemin vers le fichier d'entrée
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        playerName = reader.readLine();
    }
    public void readNbRoundFile() throws IOException {
        String inputFile = "C:\\Users\\lieto\\Desktop\\Projets\\IHM\\ChifumiChallenge\\demo\\src\\main\\resources\\com\\example\\ChifumiChallenge\\rounds.txt";  // Chemin vers le fichier d'entrée
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        nbRoundMax = Integer.parseInt(reader.readLine());
    }
    public void changeToWinnerView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ChifumiChallenge/WinnerView.fxml"));
        try {
            Parent nouvelleSceneParent = loader.load();
            Scene nouvelleScene = new Scene(nouvelleSceneParent);
            Stage stage = (Stage) imageIA.getScene().getWindow();
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
    public void changeToLoserView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ChifumiChallenge/LoseView.fxml"));
        try {
            Parent nouvelleSceneParent = loader.load();
            Scene nouvelleScene = new Scene(nouvelleSceneParent);
            Stage stage = (Stage) imageIA.getScene().getWindow();
            stage.setFullScreen(true);
            stage.setScene(nouvelleScene);
            stage.setWidth(800);
            stage.setHeight(600);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createBindings(IntegerProperty LeInt, Label Lelabel){
        BooleanProperty condition = new SimpleBooleanProperty();
        condition.setValue(true);
        condition.bind(Bindings.equal(LeInt,0));

        Lelabel.textProperty().bind(Bindings.
                when(condition).
                then(" ").
                otherwise(Bindings.concat(Lelabel.getText(), LeInt)));
    }
    private void createScoreBindings(IntegerProperty LeInt, Label scoreTabLabel){
        BooleanProperty condition = new SimpleBooleanProperty();
        condition.setValue(true);
        condition.bind(Bindings.equal(LeInt,0));

        scoreTabLabel.textProperty().bind(Bindings.
                when(condition).
                then(" ").
                otherwise(Bindings.concat(scoreTabLabel.getText(), playerName , " " , nbrVictoirePlayer , " " , "-" , " " , nbrVictoireIA , " " , IaName)));
    }
    public int genererRandomInt(){
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
        return randomNumber;
    }
    public String identificationImage(Image image){
        if(image.equals(imagePapier)){
            return "papier.jpg";
        }
        else if (image.equals(imageCiseau)) {
            return "ciseau.jpg";
        }
        else if (image.equals(imageCaillou)) {
            return "caillou.jpg";
        }
        else
            return "null";
    }
    public void chifumiJeu(ImageView imageViewIA, ImageView imageViewPlayer){
        String imageIA = identificationImage(imageViewIA.getImage());
        String imagePlayer = identificationImage(imageViewPlayer.getImage());
        String scenarioGagnant = "Bravo, vous avez gagné !";
        String scenarioPerdant = "Dommage, vous avez perdu.";
        String scenarioEgalite = "Ah, il semble que vous soyez à égalité, veuillez rejouer";

        switch(imageIA){
            case("papier.jpg"):
                switch(imagePlayer){
                    case("papier.jpg"):
                        winnerLabel.setText(scenarioEgalite);
                    break;

                    case("ciseau.jpg"):
                        winnerLabel.setText(scenarioGagnant);
                        nbrVictoirePlayer.set(nbrVictoirePlayer.get()+1);
                    break;

                    case("caillou.jpg"):
                        winnerLabel.setText(scenarioPerdant);
                        nbrVictoireIA.set(nbrVictoireIA.get()+1);
                    break;
                }
                break;

            case("ciseau.jpg"):
                switch(imagePlayer){
                    case("papier.jpg"):
                        winnerLabel.setText(scenarioPerdant);
                        nbrVictoireIA.set(nbrVictoireIA.get()+1);
                    break;

                    case("ciseau.jpg"):
                        winnerLabel.setText(scenarioEgalite);
                    break;

                    case("caillou.jpg"):
                        winnerLabel.setText(scenarioGagnant);
                        nbrVictoirePlayer.set(nbrVictoirePlayer.get()+1);
                    break;
                }
                break;

            case("caillou.jpg"):
                switch(imagePlayer){
                    case("papier.jpg"):
                        winnerLabel.setText(scenarioGagnant);
                        nbrVictoirePlayer.set(nbrVictoirePlayer.get()+1);
                    break;

                    case("ciseau.jpg"):
                        winnerLabel.setText(scenarioPerdant);
                        nbrVictoireIA.set(nbrVictoireIA.get()+1);
                    break;

                    case("caillou.jpg"):
                        winnerLabel.setText(scenarioEgalite);
                    break;
                }
                break;
        }
        if(nbRoundIsMax() == true){
            if (nbrVictoireIA.getValue() > nbrVictoirePlayer.getValue()) {
                changeToLoserView();
            }
            else {
                changeToWinnerView();
            }
        }
    }

    @FXML
    public void chifumiOnAction(){
        IaNameLabel.setText(IaName);
        int randomNumber = genererRandomInt();
        switch (randomNumber){
            case(1):
                imageIA.setImage(imagePapier);
                break;

            case(2):
                imageIA.setImage(imageCiseau);
                break;

            case(3):
                imageIA.setImage(imageCaillou);
                break;
        }
        papierButton.setDisable(true);
        ciseauButton.setDisable(true);
        caillouButton.setDisable(true);
        chifumiButton.setDisable(true);
        rejouerButton.setDisable(false);

        chifumiJeu(imageIA, imagePlayer);
    }
    @FXML
    public void papierOnAction(){
        imagePlayer.setImage(imagePapier);
        playerNameLabel.setText(playerName);
        // zoneDialogue.setText("Vous avez choisi 'PAPIER'");
        chifumiButton.setDisable(false);
    }
    @FXML
    public void ciseauOnAction(){
        imagePlayer.setImage(imageCiseau);
        playerNameLabel.setText(playerName);
        //zoneDialogue.setText("Vous avez choisi 'CISEAUX'");
        chifumiButton.setDisable(false);
    }
    @FXML
    public void caillouOnAction(){
        imagePlayer.setImage(imageCaillou);
        playerNameLabel.setText(playerName);
        //zoneDialogue.setText("Vous avez choisi 'CAILLOU'");
        chifumiButton.setDisable(false);
    }
    @FXML
    public void rejouerOnAction(){
        nbrDeTour.set(nbrDeTour.get()+1);
        /*vBoxDialogue.getChildren().clear();
        zoneDialogue.setText("Veuillez faire votre choix");
        vBoxDialogue.getChildren().addAll(zoneDialogue, rejouerButton, nombreTourLabel);*/

        chifumiButton.setDisable(true);
        rejouerButton.setDisable(true);

        papierButton.setDisable(false);
        ciseauButton.setDisable(false);
        caillouButton.setDisable(false);

        imagePlayer.setImage(null);
        imageIA.setImage(null);

        winnerLabel.setText("");
        playerNameLabel.setText("");
        IaNameLabel.setText("");
    }
}