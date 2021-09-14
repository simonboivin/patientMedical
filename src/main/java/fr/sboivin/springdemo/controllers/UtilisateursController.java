package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.entities.User;
import fr.sboivin.springdemo.repositories.UserRepository;
import fr.sboivin.springdemo.security.ApplicationConfig;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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
@Secured("ROLE_ADMIN")
@RequestMapping("/users")
public class UtilisateursController {

    final UserRepository ur;
    final ApplicationConfig applicationConfig;

    public UtilisateursController(UserRepository ur, ApplicationConfig applicationConfig) {
        this.ur = ur;
        this.applicationConfig = applicationConfig;

    }

    @RequestMapping(value = "/list")
    public String listAll(Model model) {
        List<User> lu = (List<User>) ur.findAll();
        model.addAttribute("liste_utilisateurs", lu);
        model.addAttribute("value_photouser", "user.png");
        model.addAttribute("as_admin", false);
        model.addAttribute("is_edit", false);
        return "utilisateurs/list";
    }


    @GetMapping(value = "/add")
    public String addUserGet(Model model) {
        model.addAttribute("entete_titre", "Ajouter utilisateur");
        model.addAttribute("value_photouser", "user.png");
        model.addAttribute("as_admin", false);
        model.addAttribute("is_edit", false);
        model.addAttribute("button_submit_text", "Ajouter utilisateur");
        return "utilisateurs/add_edit";
    }

    @PostMapping(value = "/add")
    public String addUserPost(HttpServletRequest request) {
        try {
            User u = new User();
            u.setName(request.getParameter("nom"));
            u.setEmail(request.getParameter("email"));
            u.setPassword(applicationConfig.encodePassword(request.getParameter("password")));
            u.setPhotouser(request.getParameter("photouser"));
            u.setRoles(request.getParameter("roles"));
            ur.save(u);
        } catch (Exception e) {
        }
        return "redirect:/users/list";
    }


    @GetMapping(value = "/edit/{id}")
    public String editUserget(Model model, @PathVariable int id) {
        try {
            User u = ur.findById(id).orElse(null);
            model.addAttribute("entete_titre", "Modifier Utilisateur ID " + String.valueOf(id));
            model.addAttribute("value_nom", u.getName());
            model.addAttribute("value_mail", u.getEmail());
            model.addAttribute("value_photouser", u.getPhotouser());
            model.addAttribute("as_admin", u.getRoles().equals("ROLE_ADMIN"));
            model.addAttribute("is_edit", true);
            model.addAttribute("button_submit_text", "Mettre à jour");

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouvé");
        }
        return "utilisateurs/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editUserPost(HttpServletRequest request, @PathVariable int id) {
        try {
            User u = ur.findById(id).orElse(null);
            u.setName(request.getParameter("nom"));
            u.setEmail(request.getParameter("email"));
            if (request.getParameter("password") != null) {
                u.setPassword(applicationConfig.encodePassword(request.getParameter("password")));
            }
            u.setPhotouser(request.getParameter("photouser"));
            u.setRoles(request.getParameter("roles"));
            ur.save(u);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans l'édition du patient " + id);
        }
        return "redirect:/users/list";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUserGet(Model model, @PathVariable int id) {
        try {
            model.addAttribute("entete_titre", "Supprimer utilisateur ID " + String.valueOf(id));
            User u = ur.findById(id).orElse(null);
            model.addAttribute("confirmation_text", "L'utilisateur " + u.getName() + " sera supprimé");
            model.addAttribute("button_submit_text", "Supprimer");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'utilisateur " + id + " n'est pas trouvé");
        }
        return "common/delete";
    }


    @PostMapping(value = "/delete/{id}")
    public String deleteUserDelete(@PathVariable int id) {
        try {
            User u = ur.findById(id).orElse(null);
            ur.delete(u);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur pendant la suppression de l'utilisateur "+id);
        }
        return "redirect:/users/list";
    }


}
