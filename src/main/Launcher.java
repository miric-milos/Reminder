package main;

import model.Stavka;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        get("/strana/dodaj", (request, response) -> {
            return new ModelAndView(null, "dodavanje.hbs");
        }, new HandlebarsTemplateEngine());

        get("/strana/izmeni", (request, response) -> {
            return new ModelAndView(null, "izmeni.hbs");
        }, new HandlebarsTemplateEngine());

        post("/akcija/dodaj", (request, response) -> {
            String tekst = request.queryParams("tekst");
            ArrayList<Stavka> sveStavke = Data.readFromJson(putanja);

            Stavka stavka = new Stavka();
            stavka.setId(Data.dodeliNovId(sveStavke));
            stavka.setTekst(tekst);
            stavka.setDatum(new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date()));
            sveStavke.add(stavka);
            Data.writeToJson(sveStavke, putanja);
            return new ModelAndView(null, "dodavanje.hbs");
        }, new HandlebarsTemplateEngine());

        post("/akcija/izmeni", (request, response) -> {
            int id = Integer.parseInt(request.queryParams("id"));
            String tekst = request.queryParams("tekst");
            String datum = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date());
            ArrayList<Stavka> sveStavke = Data.readFromJson(putanja);
            for(Stavka s : sveStavke) {
                if(s.getId() == id) {
                    s.setId(Data.dodeliNovId(sveStavke));
                    s.setDatum(datum);
                    s.setTekst(tekst);
                }
            }
            Data.writeToJson(sveStavke, putanja);
            return "uspeh";
        });

        post("/akcija/sortiraj", (request, response) -> {
            String kriterijum = request.queryParams("kriterijum");
            ArrayList<Stavka> sveStavke = Data.readFromJson(putanja);


        })
    }

}
