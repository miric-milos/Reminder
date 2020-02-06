package main;

import model.Stavka;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.*;

public class Launcher {

    public static void main(String[] args) {
        staticFiles.location("/public");
        port(9000);

        String putanja = "podaci.json";
        HashMap<String, Object> polja = new HashMap<>();

        get("/", (request, response) -> {
            ArrayList<Stavka> sveStavke = Data.readFromJson(putanja);
            polja.put("stavke", sveStavke);
            return new ModelAndView(polja, "index.hbs");
        }, new HandlebarsTemplateEngine());
    }

}
