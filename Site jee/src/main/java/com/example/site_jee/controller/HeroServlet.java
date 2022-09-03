package com.example.site_jee.controller;

import com.example.site_jee.DatabaseConnection;
import com.example.site_jee.response.NominatimResponse;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@MultipartConfig
@WebServlet(name = "HeroServlet", value = "/hero")
public class HeroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connectToDatabase();
        request.setAttribute("heroes", databaseConnection.selectHero());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Formulaire create hero
        String name = request.getParameter("name");
        String tel = request.getParameter("tel");
        String adresse = request.getParameter("adresse");

        Part image = request.getPart("file");
        String encodedImage = Base64.getEncoder().encodeToString(IOUtils.toByteArray(image.getInputStream()));

        String incident = String.format(
                "%s, %s, %s",
                request.getParameter("incident1"),
                request.getParameter("incident2"),
                request.getParameter("incident3")
        );


        OkHttpClient client = new OkHttpClient();
        Request httpRequest = new Request.Builder()
                .url("https://nominatim.openstreetmap.org/search?format=json&countrycodes=fr&limit=1&q="+adresse)
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

        if (nominatimResponse.length == 0) return;

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connectToDatabase();
        databaseConnection.createHero(name, tel, nominatimResponse[0].getLon(), nominatimResponse[0].getLat(), incident, encodedImage);
        doGet(request, response);
    }
}
