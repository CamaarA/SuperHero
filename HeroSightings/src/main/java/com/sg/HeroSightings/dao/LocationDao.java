/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.entity.Location;
import java.util.List;

/**
 *
 */
public interface LocationDao {
    Location addLocation(Location location);
    Location getLocation(int id);
    List<Location> getAllLocations();
    void updateLocation(Location location);
    void deleteLocation(int id);
    
}
