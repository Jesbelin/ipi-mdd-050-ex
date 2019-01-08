package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/techniciens")
public class TechnicienController {
    @Autowired
    private ManagerService managerService;

    //pour enlever un manager à un technicien
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{idTechnicien}/manager/{matricule}/remove"
    )
    public void deleteManager(
            @PathVariable Long idTechnicien,
            @PathVariable Long idManager
    ){
        managerService.deleteTechniciens(idManager, idTechnicien);
    }

    //pour ajouter un manager à un technicien
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{idTechnicien}/manager/{matricule}/add"
    )
    public Manager addManager(
            @PathVariable Long idTechnicien,
            @PathVariable String matricule
    ){
        return this.managerService.addManager(idTechnicien, matricule);
    }
}
