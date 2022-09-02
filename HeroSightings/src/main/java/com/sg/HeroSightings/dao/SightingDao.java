/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.entity.Sighting;
import java.util.List;

/**
 *
 */
public interface SightingDao {
    Sighting addSighting(Sighting sighting);
    Sighting getSighting(int id);
    List<Sighting> getAllSightings();
    void updateSighting(Sighting sighting);
    void deleteSighting(int id);
    
    List<Sighting> latestSightings();
    
}
