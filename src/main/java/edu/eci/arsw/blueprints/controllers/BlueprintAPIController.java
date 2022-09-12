/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import com.google.gson.Gson;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
    
    @Autowired
    BlueprintsServices bPServices;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprints() {
        Set<Blueprint> blueprintSet;
        String jsonString = "";
        try {
            blueprintSet = bPServices.getAllBlueprints();
            jsonString = crearJsonString(blueprintSet);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
        }


        return new ResponseEntity<>(new Gson().toJson(jsonString), HttpStatus.ACCEPTED);
    }

    
    private String crearJsonString(Set<Blueprint> blueprints) {
        List<Blueprint> blueprintList = new ArrayList<>(blueprints);
        String blueprintsString = "{\"blueprints\" : ";

        for (Blueprint blueprint:blueprintList) {
            String author = blueprint.getAuthor();
            String name = blueprint.getName();
            String points = blueprint.getPointsString();
            blueprintsString += "{\"Author\": \"" + author + "\", \"Name\": \"" + name + "\", \"Points\": \"" + points + "\"}";
        }
        blueprintsString += "}";

        return blueprintsString;
    }
    
    
    
    
}

