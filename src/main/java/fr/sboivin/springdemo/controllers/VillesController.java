package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.entities.Ville;
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
@RequestMapping("/villes")
public class VillesController {

    private final VilleRepository vr;

    public VillesController(VilleRepository vr) {
        this.vr = vr;
    }

    @GetMapping(value = "/add")
    public String addVilleGet(Model model) {
        model.addAttribute("entete_titre", "Ajouter ville");
        model.addAttribute("placeholder_nom", "Nom*");
        model.addAttribute("placeholder_cp", "Code Postal*");
        model.addAttribute("button_submit_text", "Ajouter ville");
        return "villes/add_edit";
    }


    @PostMapping(value = "/add")
    public String addVillesPost(HttpServletRequest request) {
        try {

            Ville v = new Ville();
            v.setNom(request.getParameter("nom"));
            v.setCodePostal(Integer.parseInt(request.getParameter("cp")));
            vr.save(v);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/villes/list";
    }


    @RequestMapping(value = "/list")
    public String listAll(Model model) {
        List<Ville> lv = (List<Ville>) vr.findAll();
        model.addAttribute("liste_villes", lv);
        return "villes/list";
    }

    @GetMapping(value = "/edit/{id}")
    public String editVilles(Model model, @PathVariable int id) {
        try {
            Ville v = vr.findById(id).orElse(null);
            model.addAttribute("entete_titre", "Modifier VilleID " + String.valueOf(id));
            model.addAttribute("value_nom", v.getNom());
            model.addAttribute("value_cp", v.getCodePostal());
            model.addAttribute("button_submit_text", "Mettre à jour");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "villes/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPatientPost(HttpServletRequest request, @PathVariable int id) {
        try {
            Ville v = vr.findById(id).orElse(null);
            v.setNom(request.getParameter("nom"));
            v.setCodePostal(Integer.parseInt(request.getParameter("cp")));
            vr.save(v);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/villes/list";
    }

}