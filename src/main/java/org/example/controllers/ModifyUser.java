package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.models.User;
import org.example.services.Crud_user;
import org.example.utils.Encryptor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyUser implements Initializable {
    @javafx.fxml.FXML
    private TextField userrole;
    @javafx.fxml.FXML
    private TextField UserEmail;
    @javafx.fxml.FXML
    private TextField Password;
    @javafx.fxml.FXML
    private ImageView returnButton;
    private User user;
    @javafx.fxml.FXML
    private TextField userNumTel;

    public void setUser(User user) {
        this.user = user;
        userNumTel.setText(String.valueOf(user.getNumTel()));
        userrole.setText(user.getRole());
        UserEmail.setText(user.getEmail());




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    @javafx.fxml.FXML
    public void returnToPreviousScene(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AfficherListeUser.fxml"));
            Parent root = loader.load();

            AfficherListeUser controller = loader.getController();
            controller.setPreviousScene(((Node) event.getSource()).getScene());

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

        } catch (
                IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void modifiyUser(ActionEvent actionEvent) {
        String email = UserEmail.getText();
        String password = Password.getText();
        String role = userrole.getText();
        String numtel=userNumTel.getText();
        String encryptedPassword = Encryptor.encryptPassword(password);
        int cin;
        try {
            cin = Integer.parseInt(userNumTel.getText());
        } catch (NumberFormatException e) {
            System.out.println("Le champ CIN doit contenir un entier valide");
            return;
        }
        if (role == null || role.isEmpty() || email.isEmpty() || password.isEmpty()) {

        }
        user.setNumTel(numtel);
        user.setEmail(email);
        user.setRole(role);
        user.setMot_de_passe(encryptedPassword);
        Crud_user us = new Crud_user();
            if (us.modifier(user)) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Operation Successful");
                successAlert.setContentText("The operation was completed successfully!");

                // Show the alert
                successAlert.showAndWait();
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AfficherListeUser.fxml"));
                    Parent root = loader.load();

                    AfficherListeUser controller = loader.getController();

                    controller.setPreviousScene(((Node) actionEvent.getSource()).getScene());

                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);

                } catch (
                        IOException e) {
                    System.err.println(e.getMessage());
                }

            }



        }
}

