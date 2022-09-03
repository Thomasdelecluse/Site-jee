package com.example.site_jee;

import com.example.site_jee.entity.Hero;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseConnection {

    private Connection connection;

    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //co a la db
    public boolean connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hero", "root", "");
            return connection != null && connection.isValid(5000) && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //Cr&ation d'un hero
    public boolean createHero(String name, String tel, double longitude, double latitude, String incident, String encodedImage) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO heros (nom, tel, longitude, latitude, incident, image) VALUES (?, ?, ?, ?, ?, ?)"
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, tel);
            preparedStatement.setDouble(3, longitude);
            preparedStatement.setDouble(4, latitude);
            preparedStatement.setString(5, incident);
            preparedStatement.setString(6, encodedImage);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertVille(String ville, String incident, double longitude, double latitude) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO ville (ville, longitude, latitude, incident) VALUES (?, ?, ?, ?)"
            );
            preparedStatement.setString(1, ville);
            preparedStatement.setDouble(2, longitude);
            preparedStatement.setDouble(3, latitude);
            preparedStatement.setString(4, incident);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //affichage des hero sur la page index
    public List<Hero> selectHero() {
        try {
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM heros").executeQuery();
            List<Hero> heroes = new ArrayList<>();
            while(resultSet.next()) {
                Hero hero = new Hero();
                hero.setNom(resultSet.getString("nom"));
                hero.setTel(resultSet.getString("tel"));
                hero.setLatitude(resultSet.getDouble("latitude"));
                hero.setLongitude(resultSet.getDouble("longitude"));
                hero.setIncident(resultSet.getString("incident"));
                hero.setImage(resultSet.getString("image"));
                heroes.add(hero);
            }
            return heroes;
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyList();
    }

}
