package com.ipiecoles.java.mdd050.service;

import com.ipiecoles.java.mdd050.exception.ConflictException;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeRepository;

    //compter le nombre d'employés
    public Long nombreEmployes (){
        return employeRepository.count(); //il existe une méthode Count() dans le repository.
    }

    //recherche d'une employé par son id
    public Employe informationsEmploye (Long id){
        Employe employe = employeRepository.findOne(id);
        if (employe == null){
            throw new EntityNotFoundException("L'employé d'identifiant " + id + " n'a pas été trouvé.");
        }
        return employe;
    }

    //recherche d'employé par matricule
    public Employe rechercheMatricule (String matricule){
        Employe employe = this.employeRepository.findByMatricule(matricule);
        if (employe == null){
            throw new EntityNotFoundException("L'employé de matricule " + matricule + " n'a pas été trouvé");
        }
        return employe;
    }

    //affichage de tous les employés, avec une pagination
    public Page <Employe> findAllEmployes (Integer page, Integer size, String sortProperty, String sortDirection){
        //vérification du paramètre page
        if (page == null){
            page = 0;
        } else if (page < 0){
            throw new IllegalArgumentException("Le numéro de page ne peut être inférieur à 0");
        } else if (page > nombreEmployes()/size){
            throw new IllegalArgumentException("Le numéro de page ne peut être supérieur à " + (nombreEmployes()/size)+1);
        }
        PageRequest pageable = new PageRequest(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        return employeRepository.findAll(pageable);
    }

    //création d'un employé
    public Employe creerEmploye(Employe employe) throws ConflictException {
        //vérifier si le matricule existe en base
        if (employeRepository.findByMatricule(employe.getMatricule())!=null){
            throw new ConflictException("Le matricule "+ employe.getMatricule() + " existe déjà");
        }
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
