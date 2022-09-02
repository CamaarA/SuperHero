/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.entity.Organization;
import java.util.List;

/**
 *
 */
public interface OrganizationDao {
    Organization addOrganization(Organization org);
    Organization getOrganization(int id);
    List<Organization> getAllOrganization();
    void updateOrganization(Organization org);
    void deleteOrganization(int id);
    
}
