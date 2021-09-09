package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.repositories.PatientRepository;
import fr.sboivin.springdemo.services.PatientsService;
import fr.sboivin.springdemo.services.VillesService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository pr;
    private final PatientsService patientsService;
    private final VillesService villesService;

    public PatientController(PatientRepository pr, PatientsService patientsService, VillesService villesService) {
        this.pr = pr;
        this.patientsService = patientsService;
        this.villesService = villesService;
    }

    @RequestMapping(value = "/list")
    public String listAll(Model model) {
        model.addAttribute("liste_patient", patientsService.getPatientsList());
        return "patients/list";
    }

    @GetMapping(value = "/add")
    public String addPatientGet(Model model) {
        model.addAttribute("entete_titre", "Ajouter patient");
        model.addAttribute("liste_villes", villesService.getVilleList());
        Ville villeDefaut = villesService.getVillebyId(1).orElse(null);
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
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Erreur lors de la création");
        }
        return "redirect:/patients/list";
    }


    @GetMapping(value = "/edit/{id}")
    public String editPatient(Model model, @PathVariable int id) {

        Optional<Patient> patientOptional = patientsService.getPatientById(id);
        if (patientOptional.isPresent()) {
            Patient p = patientOptional.get();
            model.addAttribute("entete_titre", "Modifier patient ID " + String.valueOf(id));
            model.addAttribute("value_nom", p.getNom());
            model.addAttribute("value_prenom", p.getPrenom());
            model.addAttribute("value_mail", p.getEmail());
            model.addAttribute("value_telephone", p.getTelephone());
            model.addAttribute("liste_villes", villesService.getVilleList());
            model.addAttribute("ville_select", p.getVille());
            model.addAttribute("button_submit_text", "Mettre à jour");
            return "patients/add_edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouvé");
        }
    }

    @PostMapping(value = "/edit/{id}")
    public String editPatientPost(HttpServletRequest request, @PathVariable int id) {
        Optional<Patient> patientOptional = patientsService.getPatientById(id);
        if (patientOptional.isPresent()) {
            Patient p = patientOptional.get();
            p.setNom(request.getParameter("nom"));
            p.setPrenom(request.getParameter("prenom"));
            p.setEmail(request.getParameter("email"));
            p.setTelephone(request.getParameter("telephone"));
            Optional<Ville> villeOptional = villesService.getVillebyId(Integer.valueOf(request.getParameter("ville")));
            if (villeOptional.isPresent()) {
                p.setVille(villeOptional.get());
            }
            pr.save(p);
            return "redirect:/patients/list";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans l'édition du patient " + id);
        }

    }

    @GetMapping(value = "/delete/{id}")
    public String deletePatientGet(Model model, @PathVariable int id) {
        try {
            model.addAttribute("entete_titre", "Supprimer patient ID " + String.valueOf(id));
            Patient p = pr.findById(id).orElse(null);
            model.addAttribute("confirmation_text", "Le patient " + p.getPrenom() + " " + p.getNom().toUpperCase() + " sera supprimé");
            model.addAttribute("button_submit_text", "Supprimer");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouvé");
        }
        return "common/delete";
    }


    @PostMapping(value = "/delete/{id}")
    public String deletePatientDelete(@PathVariable int id) {
        try {
            Patient p = pr.findById(id).orElse(null);
            pr.delete(p);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans la suppression du patient " + id);
        }
        return "redirect:/patients/list";
    }

}
