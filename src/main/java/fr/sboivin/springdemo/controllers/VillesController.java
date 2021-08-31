package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.repositories.VilleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}