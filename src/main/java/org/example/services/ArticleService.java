package org.example.services;

import org.example.models.Article;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleService  {

    private final Connection connection;

    public ArticleService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    
    public void add(Article article) throws SQLException {
        String sql = "INSERT INTO article ( IdJournaliste,Titre, Description,Image) VALUES ( ?,?,?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, article.getIdJournaliste());
            preparedStatement.setString(2, article.getTitreArticle());
            preparedStatement.setString(3, article.getContenuArticle());
            preparedStatement.setString(4, article.getImageArticle());

            preparedStatement.executeUpdate();
        }
    }


    
    public void modify(Article article) throws SQLException {
        String sql = "update article set  IdJournaliste = ?, Titre = ? , Description=?, Image=?  where idArticle = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,article.getIdJournaliste());
        preparedStatement.setString(2, article.getTitreArticle());
        preparedStatement.setString(3, article.getContenuArticle());
        preparedStatement.setString(4, article.getImageArticle());
        preparedStatement.setInt(5, article.getIdArticle());

        preparedStatement.executeUpdate();
    }


    
    public void remove(int id) throws SQLException {
        String sql = "delete from article where IdArticle = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    
    public List<Article> getall() throws SQLException {
        String sql = "select * from article";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Article> article = new ArrayList<>();
        while (rs.next()) {
            Article a = new Article();
            a.setIdArticle(rs.getInt("IdArticle"));
            a.setTitreArticle(rs.getString("Titre"));
            a.setContenuArticle(rs.getString("Description"));
            a.setImageArticle(rs.getString("Image"));


            article.add(a);
        }
        return article;
    }
    public List<Article> searchByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM article WHERE Titre LIKE ?";
        return executeSearchQuery(sql, "%" + title + "%");
    }

    // Add this method to your ArticleService class
    private List<Article> executeSearchQuery(String sql, String parameter) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, parameter);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Article> searchResults = new ArrayList<>();

                while (resultSet.next()) {
                    Article article = new Article();
                    article.setIdArticle(resultSet.getInt("IdArticle"));
                    article.setTitreArticle(resultSet.getString("Titre"));
                    article.setContenuArticle(resultSet.getString("Description"));
                    article.setImageArticle(resultSet.getString("Image"));

                    searchResults.add(article);
                }

                return searchResults;
            }
        }
    }


}
