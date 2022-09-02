/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.controller;

import com.sg.HeroSightings.dao.LocationDao;
import com.sg.HeroSightings.entity.Location;
import java.math.BigDecimal;
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
public class LocationController {
    
    @Autowired
    LocationDao locDao;
    
    @GetMapping("locations")
    public String displayLoactions(Model model) {
        model.addAttribute("locations", locDao.getAllLocations());
        model.addAttribute("location", new Location());
        
        return "locations";
    }
    
    @PostMapping("location")
    public String addLocation(@Valid Location loc, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("locations", locDao.getAllLocations());
            return "locations";
        }
        locDao.addLocation(loc);
        return "redirect:/locations";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locDao.deleteLocation(id);
        
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location loc = locDao.getLocation(id);
        
        model.addAttribute("location", loc);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String performEdit(@Valid Location loc, BindingResult error) {
        if (error.hasErrors()) {
            return "editLocation";
        }
        
        locDao.updateLocation(loc);
        
        return "redirect:/locations";
    }
    
}
