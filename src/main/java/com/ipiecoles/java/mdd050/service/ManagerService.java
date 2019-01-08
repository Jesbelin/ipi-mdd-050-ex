package com.ipiecoles.java.mdd050.service;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityNotFoundException;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private TechnicienRepository technicienRepository;

    public void deleteTechniciens(Long idManager, Long idTechnicien) {
        Manager m = managerRepository.findOneWithEquipeById(idManager);
        if (m == null){
            throw new EntityNotFoundException("Le manager d'id " + idManager + " n'existe pas");
        }
        Technicien t = technicienRepository.findOne(idTechnicien);
        if(t == null){
            throw new EntityNotFoundException("Le technicien d'id " + idTechnicien + " n'existe pas");
        }

        //vérifie que le technicien a bien pour manager celui qu'on cherche
        if (t.getManager().getId().equals(m.getId())){
            //m.getEquipe().remove(t);
            t.setManager(null);
            //managerRepository.save(m);
            technicienRepository.save(t);
        } else


        m.getEquipe().remove(t);
        managerRepository.save(m);

        t.setManager(null);
        technicienRepository.save(t);
    }

    public Technicien addTechniciens(Long idManager, String matricule){
        Manager m = managerRepository.findOne(idManager);
        if (m == null){
            throw new EntityNotFoundException("Le manager d'id " + idManager + " n'existe pas");
        }

        Technicien t = technicienRepository.findByMatricule(matricule);
        if (t == null){
            throw new EntityNotFoundException("Le technicien de matricule " + matricule + " n'existe pas");
        }

        if(t.getManager() != null){
            throw new IllegalArgumentException("Le technicien de matricule " + matricule + " a déjà un manager");
        }

        m.ajoutTechnicienEquipe(t);
        managerRepository.save(m);

        t.setManager(m);
        technicienRepository.save(t);

        return t;
    }

    public Manager addManager(Long idTechnicien, String matriculeManager){
        Manager m = managerRepository.findByMatricule(matriculeManager);
        if (m == null){
            throw new EntityNotFoundException("Le manager de matricule " + matriculeManager + " n'existe pas");
        }

        Technicien t = technicienRepository.findById(idTechnicien);
        if (t == null){
            throw new EntityNotFoundException("Le technicien d'id " + idTechnicien + " n'existe pas");
        }

        if(t.getManager() != null){
            throw new IllegalArgumentException("Le technicien d'id " + idTechnicien + " a déjà un manager: " +
                    t.getManager().getPrenom() + " " + t.getManager().getNom() + " (matricule " +
                    t.getManager().getMatricule() + ")");
        }

        m.ajoutTechnicienEquipe(t);
        managerRepository.save(m);

        t.setManager(m);
        technicienRepository.save(t);

        return m;
    }
}
