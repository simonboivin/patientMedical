package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.services.PatientsService;
import fr.sboivin.springdemo.services.VillesService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientsService patientsService;
    private final VillesService villesService;

    public PatientController(PatientsService patientsService, VillesService villesService) {
        this.patientsService = patientsService;
        this.villesService = villesService;
    }

    @RequestMapping(value = "/list")
    public String listAll(Model model) {
        model.addAttribute("liste_patient", patientsService.getPatientsList());
        model.addAttribute("liste_villes", villesService.getVilleList());
        Ville villeDefaut = villesService.getVillebyId(1).orElse(null);
        model.addAttribute("ville_select", villeDefaut);
        return "patients/list";
    }

    @GetMapping(value = "/add")
    public String addPatientGet(Model model) {
        model.addAttribute("entete_titre", "Ajouter patient");
        model.addAttribute("liste_villes", villesService.getVilleList());
        Ville villeDefaut = villesService.getVilleList().get(0);
        model.addAttribute("ville_select", villeDefaut);
        model.addAttribute("button_submit_text", "Ajouter patient");
        return "patients/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPatientPost(HttpServletRequest request) {
        try {
            patientsService.addPatient(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("email"),
                    request.getParameter("telephone"), Integer.valueOf(request.getParameter("ville")));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Erreur lors de la cr??ation");
        }
        return "redirect:/patients/list";
    }


    @GetMapping(value = "/edit/{id}")
    public String editPatient(Model model, @PathVariable int id) {

        Optional<Patient> patientOptional = patientsService.getPatientById(id);
        if (patientOptional.isPresent()) {
            Patient p = patientOptional.get();
            model.addAttribute("entete_titre", "Modifier patient ID " + id);
            model.addAttribute("value_nom", p.getNom());
            model.addAttribute("value_prenom", p.getPrenom());
            model.addAttribute("value_mail", p.getEmail());
            model.addAttribute("value_telephone", p.getTelephone());
            model.addAttribute("liste_villes", villesService.getVilleList());
            model.addAttribute("ville_select", p.getVille());
            model.addAttribute("button_submit_text", "Mettre ?? jour");
            return "patients/add_edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouv??");
        }
    }

    @PostMapping(value = "/edit/{id}")
    public String editPatientPost(HttpServletRequest request, @PathVariable int id) {
        try {
            patientsService.editPatient(id, request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("email"),
                    request.getParameter("telephone"), Integer.valueOf(request.getParameter("ville")));
            return "redirect:/patients/list?success";
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouv??");
        }
    }

    @GetMapping(value = "/delete/{id}")
    public String deletePatientGet(Model model, @PathVariable int id) {
        Optional<Patient> patientOptional = patientsService.getPatientById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            model.addAttribute("entete_titre", "Supprimer patient ID " + id);
            model.addAttribute("confirmation_text", "Le patient " + patient.getPrenom() + " " + patient.getNom().toUpperCase() + " sera supprim??");
            model.addAttribute("button_submit_text", "Supprimer");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouv??");
        }
        return "common/delete";
    }


    @PostMapping(value = "/delete/{id}")
    public String deletePatientDelete(@PathVariable int id) {
        try {
            patientsService.deletePatientById(id);
            return "redirect:/patients/list";
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouv??");
        }
    }

    @GetMapping(value = "/check")
    public ResponseEntity<Boolean> checkIsEmailExist(@RequestParam("email") String email) {
        System.out.println("Requ??te re??ue pour l'email " + email);
        System.out.println("resultat: " + patientsService.existPatientByEmail(email));
        return ResponseEntity.status(HttpStatus.OK).body(patientsService.existPatientByEmail(email));
    }

}

