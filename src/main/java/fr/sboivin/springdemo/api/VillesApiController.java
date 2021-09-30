package fr.sboivin.springdemo.api;

import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.services.VillesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/villes")
public class VillesApiController {

    private final VillesService villesService;


    public VillesApiController(VillesService villesService) {
        this.villesService = villesService;
    }

    @GetMapping(path = "", produces = "application/json")
    public Iterable<Ville> getVilleListApi() {
        return villesService.getVilleList();
    }


    @GetMapping(path = "/{id}", produces = "application/json")
    public Ville getCityByIdApi(@PathVariable("id") int id) {
        Optional<Ville> cityOptional = villesService.getVillebyId(id);
        if (cityOptional.isPresent()) {
            return cityOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The city is not found");
        }
    }

    /**
     * Add a new city
     */
    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<Ville> addCityApi(@RequestBody Ville cityInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                villesService.addVille(
                        cityInput.getNom(),
                        cityInput.getCodePostal()));
    }

    /**
     * Edit a patient
     */
    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Ville> editCityApi(@PathVariable("id") int id, @RequestBody Ville cityInput) {
        Optional<Ville> cityOptional = villesService.getVillebyId(id);
        if (cityOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(villesService.editVille(id, cityInput.getNom(), cityInput.getCodePostal()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The city is not found");
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void deleteCityApi(@PathVariable("id") int id) {
        Optional<Ville> cityOptional = villesService.getVillebyId(id);
        if (cityOptional.isPresent()) {
            villesService.deleteVillebyID(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The city is not found");
        }
    }

}
