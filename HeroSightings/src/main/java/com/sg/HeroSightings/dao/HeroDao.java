/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.entity.Hero;
import java.util.List;

/**
 *
 */
public interface HeroDao {
    
    Hero addHero(Hero hero);
    Hero getHero(int id);
    List<Hero> getAllHeroes();
    void updateHero(Hero hero);
    void deleteHero(int id);
    
}
