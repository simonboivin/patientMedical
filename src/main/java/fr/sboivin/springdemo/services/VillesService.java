package fr.sboivin.springdemo.services;

import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.repositories.VilleRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VillesService {

    private final VilleRepository villeRepository;

    public VillesService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }


    /**
     * Insert une nouvelle ville dans la base de donnée
     *
     * @param nom                Nom de la Ville
     * @param codePostalAsString Code Postal de la ville
     */
    @Transactional
    public void addVille(String nom, String codePostalAsString) {
        Ville ville = new Ville();
        ville.setNom(nom);
        ville.setCodePostal(Integer.parseInt(codePostalAsString));
        villeRepository.save(ville);
    }

    /**
     * @return Liste des Villes dans la base de donnée
     */
    @Transactional
    public List<Ville> getVilleList() {
        return (List<Ville>) villeRepository.findAll();
    }

    /**
     * @return Ville de la base dont l'id correspond au paramètre id
     */
    @Transactional
    public Optional<Ville> getVillebyId(int id) {
        return villeRepository.findById(id);
    }

    /**
     * Edite une ville dans la base
     *
     * @param id                 Id de la ville
     * @param nom                Nouveau nom
     * @param codePostalAsString Nouveau code postal
     */
    @Transactional
    public void editVille(int id, String nom, String codePostalAsString) {
        Optional<Ville> villeOptionnal = getVillebyId(id);
        if (villeOptionnal.isPresent()) {
            Ville ville = villeOptionnal.get();
            ville.setNom(nom);
            ville.setCodePostal(Integer.parseInt(codePostalAsString));
            villeRepository.save(ville);
        } else {
            throw new ObjectNotFoundException(id, "Ville non trouvée");
        }
    }

    /**
     * Supprimer une ville dans la base
     *
     * @param id Id de la ville
     */
    @Transactional
    public void deleteVillebyID(int id) {
        Optional<Ville> villeOptionnal = getVillebyId(id);
        if (villeOptionnal.isPresent()) {
            villeRepository.delete(villeOptionnal.get());
        } else {
            throw new ObjectNotFoundException(id, "Ville non trouvée");
        }
    }

}