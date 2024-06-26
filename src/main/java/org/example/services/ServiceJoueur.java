package org.example.services;

import org.example.models.Joueur;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceJoueur implements IService<Joueur>{
    private Connection connection;
    public ServiceJoueur(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Joueur joueur) throws SQLException {
        String sql ="INSERT INTO `joueur`(`Position`, `Hauteur`, `Poids`,`Piedfort`,`Nom`,`Prenom`,`Age`,`imagePath` ) VALUES " +
                "('"+joueur.getPosition()+"'," +
                "'"+joueur.getHauteur()+
                "','"+joueur.getPoids()+
                "','"+joueur.getPiedfort()+
                "','"+joueur.getNom()+
                "','"+joueur.getPrenom()+
                "','"+joueur.getAge()+
                "','"+joueur.getImagePath()+"')";
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    @Override
    public void modifier(Joueur joueur) {
        try {
            String sql = "UPDATE joueur SET Position=?, Hauteur=?, Poids=?, Piedfort=?, Nom=?, Prenom=?, Age=?, imagePath=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set values for each parameter in the prepared statement
            preparedStatement.setString(1, joueur.getPosition());
            preparedStatement.setInt(2, joueur.getHauteur());
            preparedStatement.setInt(3, joueur.getPoids());
            preparedStatement.setString(4, joueur.getPiedfort());
            preparedStatement.setString(5, joueur.getNom());
            preparedStatement.setString(6, joueur.getPrenom());
            preparedStatement.setInt(7, joueur.getAge());
            preparedStatement.setString(8, joueur.getImagePath());
            preparedStatement.setInt(9, joueur.getId());

            // Execute the update operation
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Update successful!");
            } else {
                System.out.println("No rows affected. ID not found or values unchanged.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM `joueur` WHERE id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Joueur> afficher() throws SQLException {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "select * from joueur";
        Statement statement = connection.createStatement();
        ResultSet rs =  statement.executeQuery(sql);
        while(rs.next()){
            Joueur p = new Joueur();
            p.setId(rs.getInt("id"));
            p.setHauteur(rs.getInt("Hauteur"));
            p.setPosition(rs.getString("Position"));
            p.setPoids(rs.getInt("Poids"));
            p.setPiedfort(rs.getString("Piedfort"));
            p.setNom(rs.getString("Nom"));
            p.setPrenom(rs.getString("Prenom"));
            p.setAge(rs.getInt("Age"));
            p.setImagePath(rs.getString("ImagePath"));

            joueurs.add(p);
        }
        return joueurs;
    }
}
