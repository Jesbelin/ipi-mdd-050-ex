package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    //Pour enlever un technicien de l'équipe d'un manager
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{idManager}/equipe/{idTechnicien}/remove")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTechniciens(
            @PathVariable("idManager") Long idManager,
            @PathVariable("idTechnicien") Long idTechnicien) {
        this.managerService.deleteTechniciens(idManager, idTechnicien);
    }

    //pour ajouter un technicien à l'équipe d'un manager
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{idManager}/equipe/{matricule}/add")
    //au lieu de void on met Technicien car c'est bien de renvoyer le technicien
    public Technicien addTechniciens(
            @PathVariable Long idManager,
            @PathVariable String matricule) {
        return this.managerService.addTechniciens(idManager, matricule);
    }
}