package org.example.controllers.Candidat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Candidat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class CandidatItemController implements Initializable {



    @FXML
    private ImageView imgItemCandidat;
    @FXML
    private Label nomItemCandidat;
    @FXML
    private Label ageItemCandidat;
    @FXML
    private Label penomItemCandidat;
    @FXML
    private Button btnAcceuil;

    @FXML
    private Button btnBoutique;

    @FXML
    private Button btnContrats;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnMatch;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnSignout;


    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            ageItemCandidat.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    public void setData(Candidat candidat) {
        nomItemCandidat.setText(candidat.getNomC());
        penomItemCandidat.setText(candidat.getPrenomC());
        ageItemCandidat.setText(String.valueOf(candidat.getAgeC()));

        String imageCPath = candidat.getImgCpath();

        if (imageCPath != null && !imageCPath.isEmpty()) {
            // Load the image from the specified path
            try (InputStream stream = getClass().getResourceAsStream(imageCPath)) {
                if (stream != null) {
                    Image image = new Image(stream);
                    imgItemCandidat.setImage(image);
                } else {
                    System.err.println("Image not found:" + imageCPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading image:" + e.getMessage());
            }
        } else {
            // Handle the case when the image path is not provided in the database
            System.err.println("Image path not found in the database for Candidat: " + candidat.getNomC());
        }

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnContrats.setOnAction(e -> {
            naviguezVers("/Employee/Contrat.fxml");
        });
        btnBoutique.setOnAction(e -> {
            naviguezVers("/Boutique/AfficherProduit.fxml");
        });
        btnElection.setOnAction(e -> {
            naviguezVers("/Election/DashbordElection.fxml");
        });
        btnReservation.setOnAction(e -> {
            naviguezVers("/Reservation/Reservation.fxml");
        });
        btnJoueurs.setOnAction(e -> {
            naviguezVers("/Employee/AffichageJoueur.fxml");
        });
        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });
    }

}
