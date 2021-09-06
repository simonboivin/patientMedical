package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.services.VillesService;
import org.hibernate.ObjectNotFoundException;
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
@RequestMapping("/villes")
public class VillesController {

    private final VillesService villesService;

    public VillesController(VillesService villesservice) {
        this.villesService = villesservice;
    }

    /**
     * Page: Ajout d'une ville
     */
    @GetMapping(value = "/add")
    public String addVilleGet(Model model) {
        model.addAttribute("entete_titre", "Ajouter ville");
        model.addAttribute("button_submit_text", "Ajouter ville");
        return "villes/add_edit";
    }

    /**
     * Requête POST: Ajout d'une ville
     */
    @PostMapping(value = "/add")
    public String addVillesPost(HttpServletRequest request) {
        try {
            villesService.addVille(request.getParameter("nom"), request.getParameter("cp"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Erreur lors de la création");
        }
        return "redirect:/villes/list";
    }

    /**
     * Page: Liste des villes
     */
    @RequestMapping(value = "/list")
    public String listAll(Model model) {
        model.addAttribute("liste_villes", villesService.getVilleList());
        return "villes/list";
    }

    /**
     * Page: édition d'une ville
     */
    @GetMapping(value = "/edit/{id}")
    public String editVilles(Model model, @PathVariable int id) {
        Optional<Ville> optionnalVille = villesService.getVillebyId(id);
        if (optionnalVille.isPresent()) {
            Ville v = optionnalVille.get();
            model.addAttribute("entete_titre", "Modifier Ville ID " + id);
            model.addAttribute("value_nom", v.getNom());
            model.addAttribute("value_cp", v.getCodePostal());
            model.addAttribute("button_submit_text", "Mettre à jour");
            return "villes/add_edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La ville " + id + " n'est pas trouvée");
        }
    }

    /**
     * Requête POST: édition d'une ville
     */
    @PostMapping(value = "/edit/{id}")
    public String editPatientPost(HttpServletRequest request, @PathVariable int id) {
        try {
            villesService.editVille(id, request.getParameter("nom"), request.getParameter("cp"));
            return "redirect:/villes/list";
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans l'édition de la ville " + id);
        }
    }

    /**
     * Page de confirmation de suppression d'une ville
     */
    @GetMapping(value = "/delete/{id}")
    public String deleteVilleGet(Model model, @PathVariable int id) {
        Optional<Ville> optionalVille = villesService.getVillebyId(id);
        if (optionalVille.isPresent()) {
            Ville ville = optionalVille.get();
            model.addAttribute("entete_titre", "Supprimer Ville ID " + id);
            model.addAttribute("confirmation_text", "La ville " + ville.getNom() + " sera supprimée");
            model.addAttribute("button_submit_text", "Supprimer");
            return "common/delete";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La ville " + id + " n'est pas trouvée");
        }
    }

    /**
     * Requête POST: suppression d'une ville
     */
    @PostMapping(value = "/delete/{id}")
    public String deleteVilleDelete(@PathVariable int id) {
        try {
            villesService.deleteVillebyID(id);
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans la suppression de la ville " + id);
        }
        return "redirect:/villes/list";
    }

}