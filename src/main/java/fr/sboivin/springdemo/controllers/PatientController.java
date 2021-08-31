package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.repositories.PatientRepository;
import fr.sboivin.springdemo.repositories.VilleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository pr;
    private final VilleRepository vr;

    public PatientController(PatientRepository pr, VilleRepository vr) {
        this.pr = pr;
        this.vr = vr;
    }


    @RequestMapping(value = "/")
    public String hello(Model model) {
        model.addAttribute("message", "Hello World");
        return "hello";
    }

    @GetMapping(value = "/add")
    public String addPatientGet(Model model) {
        model.addAttribute("entete_titre", "Ajouter patient");
        model.addAttribute("placeholder_nom", "Nom*");
        model.addAttribute("placeholder_prenom", "Prénom*");
        model.addAttribute("placeholder_mail", "Email*");
        model.addAttribute("placeholder_telephone", "Téléphone*");
        List<Ville> lv = (List<Ville>) vr.findAll();
        model.addAttribute("liste_villes", lv);
        model.addAttribute("ville_select", 3);
        model.addAttribute("button_submit_text", "Ajouter patient");
        return "patients/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPatientPost(HttpServletRequest request) {
        try {
            Patient p = new Patient();
            p.setNom(request.getParameter("nom"));
            p.setPrenom(request.getParameter("prenom"));
            p.setEmail(request.getParameter("email"));
            p.setTelephone(request.getParameter("telephone"));
            p.setVille(Integer.valueOf(request.getParameter("ville")));
            pr.save(p);
        } catch (Exception e) {

        }
        return "redirect:/patients/list";
    }


    @RequestMapping(value = "/list")
    public String listAll(Model model) {
        List<Patient> lp = (List<Patient>) pr.findAll();
        model.addAttribute("liste_patient", lp);
        return "patients/list";
    }

    @GetMapping(value = "/edit/{id}")
    public String editPatient(Model model, @PathVariable int id) {

        try {
            Patient p = pr.findById(id).orElse(null);
            model.addAttribute("entete_titre", "Modifier patient ID " + String.valueOf(id));
            model.addAttribute("value_nom", p.getNom());
            model.addAttribute("value_prenom", p.getPrenom());
            model.addAttribute("value_mail", p.getEmail());
            model.addAttribute("value_telephone", p.getTelephone());
            List<Ville> lv = (List<Ville>) vr.findAll();
            model.addAttribute("liste_villes", lv);
            model.addAttribute("ville_select", p.getVille());
            model.addAttribute("button_submit_text", "Mettre à jour");

        } catch (Exception e) {

        }
        return "patients/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPatientPost(HttpServletRequest request, @PathVariable int id) {
        try {
            Patient p = pr.findById(id).orElse(null);
            p.setNom(request.getParameter("nom"));
            p.setPrenom(request.getParameter("prenom"));
            p.setEmail(request.getParameter("email"));
            p.setTelephone(request.getParameter("telephone"));
            p.setVille(Integer.valueOf(request.getParameter("ville")));

            pr.save(p);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/patients/list";
    }

}
