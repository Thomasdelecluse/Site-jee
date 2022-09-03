package com.example.site_jee.controller;

import com.example.site_jee.DatabaseConnection;
import com.example.site_jee.entity.Hero;
import com.example.site_jee.response.NominatimResponse;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet(name = "VilleServlet", value = "/ville")
public class VilleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connectToDatabase();
        request.setAttribute("heroes", new ArrayList<Hero>());
        request.getRequestDispatcher("/aide.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ville = request.getParameter("ville");
        String incident = request.getParameter("incident");

        OkHttpClient client = new OkHttpClient();
        Request httpRequest = new Request.Builder()
                .url("https://nominatim.openstreetmap.org/search?format=json&countrycodes=fr&limit=1&q="+ville)
                .build();

        Call call = client.newCall(httpRequest);
        NominatimResponse[] nominatimResponse;
        try {
            Response httpResponse = call.execute();
            String jsonResponse = Objects.requireNonNull(httpResponse.body()).string();
            nominatimResponse = new Gson().fromJson(jsonResponse, NominatimResponse[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("NM " + nominatimResponse);
        if (nominatimResponse.length == 0) return;

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connectToDatabase();
        // Filtres des heros pour la partis aide
        List<Hero> heroes = databaseConnection.selectHero();
        heroes = heroes.stream()
                .filter(hero -> {
                    double distance = distance(hero.getLongitude(), nominatimResponse[0].getLon(), hero.getLatitude(), nominatimResponse[0].getLat());
                    return distance <= 50.0
                                    && hero.getIncident().contains(incident);
                        }
                )
                .collect(Collectors.toList());

        request.setAttribute("heroes", heroes);
        request.getRequestDispatcher("/aide.jsp").forward(request, response);
    }

    public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2) {
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));
        double r = 6371;

        return c*r;
    }

}
