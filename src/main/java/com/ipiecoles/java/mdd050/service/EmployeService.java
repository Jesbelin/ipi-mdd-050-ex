package com.ipiecoles.java.mdd050.service;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeRepository;

    //compter le nombre d'employés
    public Long nombreEmployes (){
        return employeRepository.count(); //il existe une méthode Count() dans le repository.
    }

    //recherche d'une employé apr son id
    public Employe informationsEmploye (Long id){
        return employeRepository.findOne(id);
    }

    //recherche d'employé par matricule
    public Employe rechercheMatricule (String matricule){
        return employeRepository.findByMatricule(matricule);
    }

    //affichage de tous les employés, avec une pagination
    public Page <Employe> findAllEmployes (Integer page, Integer size, String sortProperty, String sortDirection){
        PageRequest pageable = new PageRequest(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        return employeRepository.findAll(pageable);
    }

    //création d'un employé
    public Employe creerEmploye(Employe employe){
        return employeRepository.save(employe);
    }

    //modification d'un employé
    public Employe updateEmploye(Long id, Employe employe){
        return employeRepository.save(employe);
    }

    //suppression d'un employé
    public void deleteEmploye(Long id){
        employeRepository.delete(id);
    }
}
