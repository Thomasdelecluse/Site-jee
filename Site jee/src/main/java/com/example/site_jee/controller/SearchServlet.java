package com.example.site_jee.controller;

import com.example.site_jee.DatabaseConnection;
import com.example.site_jee.entity.Hero;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "SearchServlet", value = "/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connectToDatabase();
        //recherche hero
        List<Hero> heros=databaseConnection.selectHero();
        heros=heros.stream().filter(hero->hero.getNom().contains(request.getParameter("recherche"))).collect(Collectors.toList());
        request.setAttribute("heroes",heros);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
