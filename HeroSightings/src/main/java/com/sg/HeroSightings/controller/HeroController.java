/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.controller;

import com.sg.HeroSightings.dao.HeroDao;
import com.sg.HeroSightings.entity.Hero;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 */
@Controller
public class HeroController {

    @Autowired
    HeroDao heroDao;

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        model.addAttribute("heroes", heroDao.getAllHeroes());
        model.addAttribute("hero", new Hero());

        return "heroes";
    }

    @PostMapping("hero")
    public String addHero(@Valid Hero hero, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("heroes", heroDao.getAllHeroes());
            return "heroes";
        }
        heroDao.addHero(hero);
        return "redirect:/heroes";
    }

    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDao.deleteHero(id);

        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHero(id);

        model.addAttribute("hero", hero);
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEdit(@Valid Hero hero, BindingResult result) {
        if (result.hasErrors()) {
            return "editHero";
        }
        
        heroDao.updateHero(hero);

        return "redirect:/heroes";
    }

}
