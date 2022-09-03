<%@ page import="com.example.site_jee.entity.Hero" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Site Menu</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css">
    <link rel="stylesheet" href="css_menu.css">
    <script src="https://kit.fontawesome.com/c0ee880a9e.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="script.js"></script>

</head>
<body>
<%--Style formulaire--%>
<style>
    .form-style-2{
        max-width: 500px;
        padding: 20px 12px 10px 20px;
        font: 13px Arial, Helvetica, sans-serif;
        margin-top: 3rem;
    }
    .form-style-2-heading{
        font-weight: bold;
        font-style: italic;
        border-bottom: 2px solid #ddd;
        margin-bottom: 20px;
        font-size: 15px;
        padding-bottom: 3px;
        color: white;
    }
    .form-style-2 label{
        display: block;
        margin: 0px 0px 15px 0px;
    }
    .form-style-2 label > span{
        width: 100px;
        font-weight: bold;
        float: left;
        padding-top: 8px;
        padding-right: 5px;
    }
    .form-style-2 span.required{
        color:red;
    }
    .form-style-2 .tel-number-field{
        width: 206px;
        text-align: center;
    }
    .form-style-2 input.input-field, .form-style-2 .select-field{
        width: 48%;
    }
    .form-style-2 input.input-field,
    .form-style-2 .tel-number-field,
    .form-style-2 .textarea-field,
    .form-style-2 .select-field{
        box-sizing: border-box;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        border: 1px solid #C2C2C2;
        box-shadow: 1px 1px 4px #EBEBEB;
        -moz-box-shadow: 1px 1px 4px #EBEBEB;
        -webkit-box-shadow: 1px 1px 4px #EBEBEB;
        border-radius: 3px;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        padding: 7px;
        outline: none;
    }
    .form-style-2 .input-field:focus,
    .form-style-2 .tel-number-field:focus,
    .form-style-2 .textarea-field:focus,
    .form-style-2 .select-field:focus{
        border: 1px solid #0C0;
    }
    .form-style-2 .textarea-field{
        height:100px;
        width: 55%;
    }
    .form-style-2 input[type=submit],
    .form-style-2 input[type=button]{
        border: none;
        padding: 8px 15px 8px 15px;
        background: #FF8500;
        color: #fff;
        box-shadow: 1px 1px 4px #DADADA;
        -moz-box-shadow: 1px 1px 4px #DADADA;
        -webkit-box-shadow: 1px 1px 4px #DADADA;
        border-radius: 3px;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
    }
    .form-style-2 input[type=submit]:hover,
    .form-style-2 input[type=button]:hover{
        background: #EA7B00;
        color: #fff;
    }
</style>
<%--Début de la page--%>
<div class="container">
    <div class="modal">
            <div class="item1">
                <img src="img/Marvel_Logo.svg.png" style="width: 70%; margin: auto; padding-top: 1.5rem; display: block">
                <div class="display">
                    <a href="/Site_jee-1.0-SNAPSHOT/hero" class="HOVER">
                        <span></span>
                        <text><i class="fas fa-mask"></i>&nbsp; &nbsp; Heros</text>
                    </a>
                    <a href="/Site_jee-1.0-SNAPSHOT/ville" class="HOVER">
                        <span></span>
                        <text><i  class="fas fa-hands-helping"></i>&nbsp; &nbsp; Aide</text>
                    </a>
                </div>
            </div>
            <div class="item2">
                <div class="menu">
                    <form action="search" method="post">
                    <div class="container1">
                        <input type="text" name="recherche" placeholder="Rechercher...">
                        <div class="search"></div>
                    </div>
                    </form>
                    <img class="image" src="img/logo.png" alt="">
                </div>
                <h4>Recrutement</h4>
                <div class="modal2">
                    <%
                        ArrayList<Hero> heroes = (ArrayList<Hero>)  request.getAttribute("heroes");
                        for (Hero hero : heroes) {
                    %>
                    <div class ="itm1">
                        <img class="img" src="data:image/png;base64, <%= hero.getImage() %>" alt="" >
                        <h1><%= hero.getNom() %></h1>
                        <p>Tel:<%= hero.getTel() %></p>
                        <p>Lat:<%= hero.getLatitude() %></p>
                        <p>Long:<%= hero.getLongitude() %></p>
                    </div>
                    <%}%>
                </div>

            </div>
            <div class="item3">
                <div class="form-style-2">
                    <div class="form-style-2-heading">Ajouter un hero</div>
                    <form action="" method="post" enctype="multipart/form-data">
                        <label ><span>Nom <span class="required">*</span></span><input type="text" class="input-field" name="name" value="" /></label>
                        <label ><span>Incident Maitriser 1</span><select name="incident1" class="select-field" required>
                            <option value="Incendie">Incendie</option>
                            <option value="Accident routier">Accident routier</option>
                            <option value="Accident fluviale">Accident fluviale</option>
                            <option value="Accident aérien">Accident aérien</option>
                            <option value="Eboulement">Eboulement</option>
                            <option value="Invasion de serpent">Invasion de serpent</option>
                            <option value="Fuite de gaz">Fuite de gaz</option>
                            <option value="Braquage">Braquage</option>
                            <option value="Manifestation">Manifestation</option>
                            <option value="Evasion d’un prisonnier">Evasion d’un prisonnier</option>


                        </select></label>
                        <label ><span>Incident Maitriser 2</span><select name="incident2" class="select-field">
                            <option value="Incendie">Incendie</option>
                            <option value="Accident routier">Accident routier</option>
                            <option value="Accident fluviale">Accident fluviale</option>
                            <option value="Accident aérien">Accident aérien</option>
                            <option value="Eboulement">Eboulement</option>
                            <option value="Invasion de serpent">Invasion de serpent</option>
                            <option value="Fuite de gaz">Fuite de gaz</option>
                            <option value="Braquage">Braquage</option>
                            <option value="Manifestation">Manifestation</option>
                            <option value="Evasion d’un prisonnier">Evasion d’un prisonnier</option>


                        </select></label>
                        <label ><span>Incident Maitriser 3</span><select name="incident3" class="select-field">
                            <option value="Incendie">Incendie</option>
                            <option value="Accident routier">Accident routier</option>
                            <option value="Accident fluviale">Accident fluviale</option>
                            <option value="Accident aérien">Accident aérien</option>
                            <option value="Eboulement">Eboulement</option>
                            <option value="Invasion de serpent">Invasion de serpent</option>
                            <option value="Fuite de gaz">Fuite de gaz</option>
                            <option value="Braquage">Braquage</option>
                            <option value="Manifestation">Manifestation</option>
                            <option value="Evasion d’un prisonnier">Evasion d’un prisonnier</option>


                        </select></label>
                        <label><span>Telephone <span class="required">*</span></span><input type="text" class="tel-number-field" name="tel" value="" maxlength="10" /></label>
                        <label ><span>Image du hero <span class="required">*</span></span><input type="file" name="file" class="input-field"></label>

                        <div class="form-style-2-heading">Habitation</div>
                        <label ><span>Adresse <span class="required">*</span></span><input type="text" name="adresse" class="input-field"></label>
                        <label><span> </span><input type="submit" value="Submit" /></label>
                    </form>
                </div>
            </div>

    </div>
</div>

</body>
</html>