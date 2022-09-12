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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
            return new ResponseEntity<>("Error al consultar blueprints",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Gson().toJson(jsonString), HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
        Set<Blueprint> blueprintSet;
        String jsString = "";
        try {
            blueprintSet = bPServices.getBlueprintsByAuthor(author);
            jsString = crearJsonString(blueprintSet);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al consultar blueprints por autor",HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(new Gson().toJson(jsString), HttpStatus.ACCEPTED);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/{author}/{bpname}")
    public ResponseEntity<?> getBlueprint (@PathVariable String author, @PathVariable String bpname){
        Set<Blueprint> blueprintSet = new HashSet<Blueprint>();
        String jsString = "";
        try {
            blueprintSet.add(bPServices.getBlueprint(author,bpname));
            jsString = crearJsonString(blueprintSet);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al consultar blueprint",HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(new Gson().toJson(jsString), HttpStatus.ACCEPTED);
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

