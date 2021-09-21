package fr.sboivin.springdemo.controllers;


import fr.sboivin.springdemo.entities.Rendezvous;
import fr.sboivin.springdemo.services.PatientsService;
import fr.sboivin.springdemo.services.RendezvousService;
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
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/rdv")
public class RendezvousController {

    private final RendezvousService rendezvousService;
    private final PatientsService patientsService;

    public RendezvousController(RendezvousService rendezvousService, PatientsService patientsService) {
        this.rendezvousService = rendezvousService;
        this.patientsService = patientsService;
    }

    @GetMapping(value = "/list")
    public String listRendezvous(Model model) {
        model.addAttribute("listerdv", rendezvousService.getAllRendezvous());
        model.addAttribute("liste_patients", patientsService.getPatientsList());
        model.addAttribute("patient_select", patientsService.getPatientsList().get(0));
        return "rdv/list";
    }

    @PostMapping(value = "/add")
    public String addRendezvous(HttpServletRequest request) {
        rendezvousService.addRendezvous(Integer.valueOf(request.getParameter("patient")),
                LocalDateTime.parse(request.getParameter("dateheure")), request.getParameter("type"),
                Integer.valueOf(request.getParameter("duree")), request.getParameter("notes"));
        return "redirect:/rdv/list?success";
    }


    @GetMapping(value = "/edit/{id}")
    public String editRendezvousGet(@PathVariable Integer id, Model model) {
        Optional<Rendezvous> rendezvousOptional = rendezvousService.getRendezvousById(id);
        if (rendezvousOptional.isPresent()) {
            model.addAttribute("liste_patients", patientsService.getPatientsList());
            Rendezvous rendezvous = rendezvousOptional.get();
            model.addAttribute("patient_select", rendezvous.getPatient());
            model.addAttribute("dateheure_value", rendezvous.getDateheure());
            model.addAttribute("type_value", rendezvous.getType());
            model.addAttribute("duree_value", rendezvous.getDuree());
            model.addAttribute("notes_value", rendezvous.getNote());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le rendez-vous n°" + id + " n'a pas été trouvé");
        }
        return "/rdv/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editRendezvous(@PathVariable Integer id, HttpServletRequest request) {
        rendezvousService.updateRendezvousById(id, Integer.valueOf(request.getParameter("patient")),
                LocalDateTime.parse(request.getParameter("dateheure")), request.getParameter("type"),
                Integer.valueOf(request.getParameter("duree")), request.getParameter("notes"));
        return "redirect:/rdv/list?success";
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteRendezvousDelete(@PathVariable int id) {
        try {
            rendezvousService.deleteRendezvousById(id);

            return "redirect:/rdv/list?sucess";
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le rendez-vous n°" + id + " n'a pas été trouvé");
        }
    }
}
