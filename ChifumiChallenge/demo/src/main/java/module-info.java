module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.ChifumiChallenge to javafx.fxml;
    exports com.example.ChifumiChallenge;
}