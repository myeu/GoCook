package com.example.marisayeung.gocook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marisayeung
 */
public class NYTimesRecipe {
    Document doc;
    String yield;
    Elements sections;
    Boolean nyTimes;

    public NYTimesRecipe(String input, boolean filename) {
        if (filename) {
            File inputFile = new File(input);
            try {
                doc = Jsoup.parse(inputFile, "UTF-8");
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                doc = Jsoup.parse(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        yield = "";
//        System.out.println(doc.getElementsByClass("ennote").first().children().get(1));
        sections = doc.getElementsByClass("ennote").first().children();
        nyTimes = input.contains("cooking.nytimes.com");

    }

    public boolean isNYTimesRecipe() {
        return nyTimes;
//        Element e = doc.getElementsByAttributeValue("name", "source-url").first();
//        if (e != null && e.attributes().get("source-url").contains("cooking.nytimes.com")) {
//            return true;
//        } else {
//            return false;
//        }
    }

    public String getTitle() {
//        Element e = doc.getElementsByTag("title").first();
        Element e = doc.getElementsByTag("h1").first();
        return e.ownText();
    }

    public String getAuthor() {
//        Elements eles = doc.getElementsByClass("name");
//        Element e = doc.getElementsByAttributeValue("name", "author").first();
        Element header = doc.getElementsByTag("h3").first();
        Element e = header.getElementsByTag("p").first();
//        return e.attributes().get("content");
        if (e != null) {
            return e.ownText();
        } else {
            return "";
        }
    }

    public String getTime() {
        String time = "";
        Element info = doc.getElementsByTag("h3").first();
        for (Element e: info.getElementsByTag("span")) {
            if (e.text().contains("Time")) {
                time = e.nextElementSibling().text();
            }
            if (e.text().contains("Yield")) {
                yield = e.nextElementSibling().text();
            }
        }
        return time;
    }

    public String getYield() {
        if (yield.equals("")){
            Element yieldEle = doc.getElementsByTag("h3").first();
            for (Element e: yieldEle.getElementsByTag("span")) {
                if (e.text().contains("Yield")) {
                    yield = e.nextElementSibling().text();
                }
            }
        }

        return yield;
    }

    public String getDescription() {
        StringBuilder description = new StringBuilder();
        Element mainSection = sections.get(1);
//        System.out.println(mainSection.toString());
        for (Element e : mainSection.children()) {
            if (e.tagName().equals("p")) {
                if (!e.ownText().contains("view this recipe on")) {
                    description.append(e.ownText());
                }
            }
            if (e.tagName().equals("h3")) {
                break;
            }

        }
        return description.toString();
    }

    public List<String> getNotes() {
        List<String> s = new ArrayList<String>();
        s.add("");
        return s;
    }

    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        Element ingredientList = doc.getElementsByTag("h3").get(1).nextElementSibling();
        for (Element e: ingredientList.getElementsByTag("li")) {
            Ingredient ingredient = new Ingredient();
            if (e.child(0).hasText()) {
                ingredient.setAmount(e.child(0).ownText());
            } else {
                ingredient.setAmount("");
            }
            ingredient.setIngredient(e.child(1).ownText());
            ingredient.setNote("");
            ingredient.setProcessing("");
            ingredient.setUnit("");
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    public List<String> getSteps() {
        List<String> steps = new ArrayList<String>();
        Element stepsList = doc.getElementsByTag("h3").get(2).nextElementSibling();
        for (Element e: stepsList.getElementsByTag("li")) {
            steps.add(e.ownText().toString());
        }
        return steps;
    }

    public List<String> getImageURLs() {
        List<String> images = new ArrayList<>();
        Elements imageEls = doc.getElementsByTag("h3").get(0).parent().getElementsByTag("img");
        for (Element e : imageEls) {
            images.add(e.attributes().get("name"));
            images.add(e.attributes().get("src"));
        }
        return images;
    }
}

