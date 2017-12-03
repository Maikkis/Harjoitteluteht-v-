package haroittelutehtava;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database db = new Database("jdbc:sqlite:Juomat.db");

        AnnosDao annokset = new AnnosDao(db);
        RaakaAineDao raakaAineet = new RaakaAineDao(db);
        AnnosRaakaAineDao annosRaakaAine = new AnnosRaakaAineDao(db);

        //annoslistaus
        Spark.get("/annokset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annokset.findAll());
            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());

//ainelistaus
        Spark.get("/aineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aineet", raakaAineet.findAll());
            return new ModelAndView(map, "raakaAine");
        }, new ThymeleafTemplateEngine());

        //uuden aineen luonti
        Spark.post("/aine", (req, res) -> {
            String nimi = req.queryParams("nimi");
            RaakaAine ra = new RaakaAine(-1, nimi);
            raakaAineet.saveOrUpdate(ra);
            res.redirect("/aineet");
            return "";
        });

        //yksittäinen annos
        Spark.get("/annos/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer annosId = Integer.parseInt(req.params(":id"));
            map.put("annos", annokset.findOne(annosId));
            System.out.println(annosId);
            List<String> nimet = annosRaakaAine.findRaakaAineNames(annosId);
            map.put("nimet", nimet);

            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());

        //annoksen luonti
        Spark.post("/uusiannos", (req, res) -> {
            String nimi = req.queryParams("nimi");
            Annos annos = new Annos(-1, nimi);
            annokset.saveOrUpdate(annos);
            res.redirect("/uusiannos");
            return "";
        });

        //luo uuden AnnosRaakaAineen
        Spark.post("/uusiannosraakaaine", (req, res) -> {
            int maara = Integer.parseInt(req.queryParams("maara"));
            int jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            int annosid = Integer.parseInt(req.queryParams("annos"));
            int aineid = Integer.parseInt(req.queryParams("aine"));
            String ohje = req.queryParams("ohje");
            System.out.println(jarjestys);
            System.out.println(maara);
            System.out.println(ohje);
            System.out.println(aineid);
            System.out.println(annosid);

            AnnosRaakaAine ara = new AnnosRaakaAine(annosid, aineid, jarjestys, maara, ohje);
            annosRaakaAine.saveOrUpdate(ara);
            res.redirect("/uusiannos");
            return "";
        });

        // luo uuden AnnosRaakaAineen
        Spark.get("/uusiannos", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annokset.findAll());
            map.put("aineet", raakaAineet.findAll());
            return new ModelAndView(map, "annostenLisays");
        }, new ThymeleafTemplateEngine());

        Spark.post("/poista", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            System.out.println(id);
            raakaAineet.delete(id);
            annosRaakaAine.delete(id);
            res.redirect("/aineet");
            return "";
        });

        Spark.get("/*", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annokset.findAll());
            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());

    }

}
