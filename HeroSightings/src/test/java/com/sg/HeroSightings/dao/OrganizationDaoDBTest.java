/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.entity.Hero;
import com.sg.HeroSightings.entity.Organization;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoDBTest {
    
    @Autowired
    OrganizationDao orgDao;
    
    @Autowired
    HeroDao heroDao;
    
    public OrganizationDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Organization> orgs = orgDao.getAllOrganization();
        
        for(Organization o : orgs) {
            orgDao.deleteOrganization(o.getId());
        }
        
        List<Hero> heroes = heroDao.getAllHeroes();
        
        for(Hero h : heroes) {
            heroDao.deleteHero(h.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddOrganization() {
        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("Group of heroes");
        o.setAddress("101 Main Street");
        o.setContact("1-800-555-0202");
        
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        List<Hero> heroes = heroDao.getAllHeroes();
        o.setHeroes(heroes);
        
        o = orgDao.addOrganization(o);
        
        
        Organization fromDao = orgDao.getOrganization(o.getId());
        
        assertEquals(fromDao, o);
    }

    /**
     * Test of getAllOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganization() {
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        List<Hero> heroes = heroDao.getAllHeroes();
        
        Organization o = new Organization();
        o.setName("Justice League");
        o.setDescription("Group of super heroes");
        o.setAddress("Metropolis");
        o.setContact("1-800-555-4040");
        
        o.setHeroes(heroes);
        o = orgDao.addOrganization(o);
        
        Organization o2 = new Organization();
        o2.setName("Avengers");
        o2.setDescription("Group of heroes");
        o2.setAddress("101 Main Street");
        o2.setContact("1-800-555-0202");
        
        o2.setHeroes(heroes);
        o2 = orgDao.addOrganization(o2);
        
        List<Organization> orgs = orgDao.getAllOrganization();
        
        assertEquals(2, orgs.size());
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        List<Hero> heroes = heroDao.getAllHeroes();
        
        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("Group of heroes");
        o.setAddress("101 Main Street");
        o.setContact("1-800-555-0202");
        
        o.setHeroes(heroes);
        o = orgDao.addOrganization(o);
        
        o.setName("The Avengers");
        
        orgDao.updateOrganization(o);
        Organization fromDao = orgDao.getOrganization(o.getId());
        
        assertTrue(o.getName().equalsIgnoreCase("The Avengers"));
        assertEquals(fromDao, o);
    }

    /**
     * Test of deleteOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganization() {
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("Group of heroes");
        o.setAddress("101 Main Street");
        o.setContact("1-800-555-0202");
        o.setHeroes(heroDao.getAllHeroes());
        
        o = orgDao.addOrganization(o);
        
        assertEquals(o, orgDao.getOrganization(o.getId()));
        
        orgDao.deleteOrganization(o.getId());
        
        assertNull(orgDao.getOrganization(o.getId()));
        
        
    }
    
}
