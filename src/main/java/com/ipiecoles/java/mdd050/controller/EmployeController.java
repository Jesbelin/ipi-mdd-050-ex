package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.exception.ConflictException;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/employes") /*=> si on fait ça, dans le RequestMapping suivant on ne met pas "/employes", ce n'est
plus nécessaire. C'est même mieux de faire comme ça, ça évite de se mélanger et on précise dès le début sur quoi
on travaille*/
public class EmployeController {
    @Autowired
    private EmployeService employeService;

    @RequestMapping("/count") //par défaut, si on ne précise rien, c'est un GET qui sera fait.
    //Le plus important à préciser, c'est l'adresse URL. Ici: "/employes/count" qu'on mettra après localhost:0000
    public Long count(){ //nécessaire pour dire qu'on veut retourner quelque chose en Long.
        return employeService.nombreEmployes(); //Lien avec EmployeService pour appeler la bonne méthode.
    }

    @RequestMapping(value = "/{id}") //là, l'url sera: "http://localhost:5367/employes/6" avec 6 l'id, par exemple
    /* , method = RequestMethod.GET, produces = "application/json" peut être rajouté dans la parenthèse après
     * "/{id}" mais ce n'est pas indispensable */
    public Employe infosEmploye(@PathVariable(value = "id") Long id) throws EntityNotFoundException
    {
        return employeService.informationsEmploye(id);
    }

    @RequestMapping(params = "matricule")
    public Employe matriculeEmploye (@RequestParam (value="matricule") String matricule) throws Exception {
        return employeService.rechercheMatricule(matricule);
    }

    @RequestMapping()
    public Page<Employe> findAllEmployes(
            @RequestParam ("page") Integer page,
            @RequestParam ("size") Integer size,
            @RequestParam ("sortProperty") String sortProperty,
            @RequestParam ("sortDirection") String sortDirection) throws Exception
    {
        return employeService.findAllEmployes(page, size, sortProperty, sortDirection);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json", value = "")
    public Employe createEmploye (@RequestBody Employe employe) throws ConflictException {
        return employeService.creerEmploye(employe);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Employe updateEmploye(
            @PathVariable(value = "id") Long id,
            @RequestBody Employe employe){
        return employeService.updateEmploye(id, employe);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEmploye(
            @PathVariable("id") Long id){
        employeService.deleteEmploye(id);
    }
}