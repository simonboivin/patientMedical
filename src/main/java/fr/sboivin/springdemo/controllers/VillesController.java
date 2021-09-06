package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.repositories.VilleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
            model.addAttribute("entete_titre", "Modifier Ville ID " + String.valueOf(id));
            model.addAttribute("value_nom", v.getNom());
            model.addAttribute("value_cp", v.getCodePostal());
            model.addAttribute("button_submit_text", "Mettre à jour");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La ville " + id + " n'est pas trouvée");
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans l'édition de la ville "+id);
        }
        return "redirect:/villes/list";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteVilleGet(Model model, @PathVariable int id) {
        try {
            model.addAttribute("entete_titre", "Supprimer Ville ID " + String.valueOf(id));
            Ville v = vr.findById(id).orElse(null);
            model.addAttribute("confirmation_text", "La ville " + v.getNom() + " sera supprimée");
            model.addAttribute("button_submit_text", "Supprimer");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La ville " + id + " n'est pas trouvée");
        }
        return "common/delete";
    }


    @PostMapping(value = "/delete/{id}")
    public String deleteVilleDelete(@PathVariable int id) {
        try {
            Ville v = vr.findById(id).orElse(null);
            vr.delete(v);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans la suppression de la ville "+id);
        }
        return "redirect:/villes/list";
    }

}