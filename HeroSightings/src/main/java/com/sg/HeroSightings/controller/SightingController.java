/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.controller;

import com.sg.HeroSightings.dao.HeroDao;
import com.sg.HeroSightings.dao.LocationDao;
import com.sg.HeroSightings.dao.SightingDao;
import com.sg.HeroSightings.entity.Hero;
import com.sg.HeroSightings.entity.Sighting;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 */
@Controller
public class SightingController {

    @Autowired
    SightingDao sightingDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    HeroDao heroDao;

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        model.addAttribute("sightings", sightingDao.getAllSightings());
        model.addAttribute("locations", locDao.getAllLocations());
        model.addAttribute("heroes", heroDao.getAllHeroes());
        model.addAttribute("sighting", new Sighting());

        return "sightings";
    }

    @PostMapping("sighting")
    public String addSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        String locId = request.getParameter("locationId");
        sighting.setLocation(locDao.getLocation(Integer.parseInt(locId)));
        
        String[] heroIds = request.getParameterValues("heroId");
        List<Hero> heroes = new ArrayList<>();

        if (heroIds != null) {
            for (String hero : heroIds) {
                heroes.add(heroDao.getHero(Integer.parseInt(hero)));
            }
        } else {
            FieldError error = new FieldError("sighting", "heroes", "Must include one Hero");
            result.addError(error);
        }

        if (result.hasErrors()) {
            model.addAttribute("sightings", sightingDao.getAllSightings());
            model.addAttribute("locations", locDao.getAllLocations());
            model.addAttribute("heroes", heroDao.getAllHeroes());
            return "sightings";
        }

        sighting.setHeroes(heroes);
        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDao.deleteSighting(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting s = sightingDao.getSighting(id);

        model.addAttribute("sighting", s);
        model.addAttribute("locations", locDao.getAllLocations());
        model.addAttribute("heroes", heroDao.getAllHeroes());

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEdit(@Valid Sighting s, BindingResult result, HttpServletRequest request, Model model) {
        String locId = request.getParameter("locationId");
        String[] heroIds = request.getParameterValues("heroId");
        List<Hero> heroes = new ArrayList<>();
        s.setLocation(locDao.getLocation(Integer.parseInt(locId)));

        if (heroIds != null) {
            for (String hero : heroIds) {
                heroes.add(heroDao.getHero(Integer.parseInt(hero)));
            }
        } else {
            FieldError error = new FieldError("sighting", "heroes", "Must include one Hero");
            result.addError(error);
        }

        if (result.hasErrors()) {
            model.addAttribute("sighting", s);
            model.addAttribute("locations", locDao.getAllLocations());
            model.addAttribute("heroes", heroDao.getAllHeroes());
            return "editSighting";
        }

        s.setHeroes(heroes);
        sightingDao.updateSighting(s);

        return "redirect:/sightings";
    }

    @GetMapping("sightings/heroes")
    public String heroesSighted(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting s = sightingDao.getSighting(id);

        model.addAttribute("sighting", s);

        return "sightingDetails";
    }

    @GetMapping("/")
    public String latestSightings(Model model) {
        List<Sighting> latest = sightingDao.latestSightings();
        model.addAttribute("latest", latest);

        return "index";
    }

}
