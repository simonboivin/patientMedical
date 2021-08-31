package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.repositories.PatientRepository;
import fr.sboivin.springdemo.repositories.VilleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        Ville villeDefaut = vr.findById(1).orElse(null);
        model.addAttribute("ville_select", villeDefaut);
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
            Ville v = vr.findById(Integer.valueOf(request.getParameter("ville"))).orElse(null);
            p.setVille(v);
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
            Ville v = vr.findById(Integer.valueOf(request.getParameter("ville"))).orElse(null);
            p.setVille(v);
            pr.save(p);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/patients/list";
    }

    @GetMapping(value = "/delete/{id}")
    public String deletePatientGet(Model model, @PathVariable int id) {
        model.addAttribute("entete_titre", "Supprimer patient ID " + String.valueOf(id));
        Patient p = pr.findById(id).orElse(null);
        model.addAttribute("confirmation_text", "Le patient "+ p.getPrenom() +" "+ p.getNom().toUpperCase() +" va être supprimer");
        model.addAttribute("button_submit_text", "Supprimer");
        return  "common/delete";
    }


    @PostMapping(value = "/delete/{id}")
    public String deletePatientDelete(@PathVariable int id) {
        try{
            Patient p = pr.findById(id).orElse(null);
            pr.delete(p);
        } catch (Exception e) {
            System.out.println(e);
        }
        return  "redirect:/patients/list";
    }

}
