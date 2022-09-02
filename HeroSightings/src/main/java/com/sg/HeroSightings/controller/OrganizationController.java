/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.controller;

import com.sg.HeroSightings.dao.HeroDao;
import com.sg.HeroSightings.dao.OrganizationDao;
import com.sg.HeroSightings.entity.Hero;
import com.sg.HeroSightings.entity.Organization;
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
public class OrganizationController {

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    HeroDao heroDao;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        model.addAttribute("organizations", orgDao.getAllOrganization());
        model.addAttribute("members", heroDao.getAllHeroes());
        model.addAttribute("organization", new Organization());

        return "organizations";
    }

    @PostMapping("organization")
    public String addOrganization(@Valid Organization org, BindingResult result, HttpServletRequest request, Model model) {
        String[] heroIds = request.getParameterValues("heroList");
        List<Hero> heroes = new ArrayList<>();

        if (heroIds != null) {
            for (String hero : heroIds) {
                heroes.add(heroDao.getHero(Integer.parseInt(hero)));
            }
        } else {
            FieldError error = new FieldError("organization", "heroes", "Must include one Hero");
            result.addError(error);
        }
        
        if (result.hasErrors()) {
            model.addAttribute("organizations", orgDao.getAllOrganization());
            model.addAttribute("members", heroDao.getAllHeroes());
            return "organizations";
        }

        org.setHeroes(heroes);
        orgDao.addOrganization(org);
        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        orgDao.deleteOrganization(id);

        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = orgDao.getOrganization(id);

        model.addAttribute("organization", org);
        model.addAttribute("members", heroDao.getAllHeroes());
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEdit(@Valid Organization org, BindingResult result, HttpServletRequest request, Model model) {
        String[] heroIds = request.getParameterValues("heroList");
        List<Hero> heroes = new ArrayList<>();

        if (heroIds != null) {
            for (String hero : heroIds) {
                heroes.add(heroDao.getHero(Integer.parseInt(hero)));
            }
        } else {
            FieldError error = new FieldError("organization", "heroes", "Must include one Hero");
            result.addError(error);
        }

        if (result.hasErrors()) {
            model.addAttribute("organization", org);
            model.addAttribute("members", heroDao.getAllHeroes());
            return "editOrganization";
        }

        org.setHeroes(heroes);
        orgDao.updateOrganization(org);

        return "redirect:/organizations";
    }

    @GetMapping("organizations/members")
    public String orgMembers(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = orgDao.getOrganization(id);
        model.addAttribute("organization", org);

        return "organizationMembers";
    }

}
