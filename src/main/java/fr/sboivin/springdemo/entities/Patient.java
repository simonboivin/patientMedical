package fr.sboivin.springdemo.entities;

import javax.persistence.*;

@Entity
public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String photo;
    private int ville;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "photo")
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (id != patient.id) return false;
        if (nom != null ? !nom.equals(patient.nom) : patient.nom != null) return false;
        if (prenom != null ? !prenom.equals(patient.prenom) : patient.prenom != null) return false;
        if (email != null ? !email.equals(patient.email) : patient.email != null) return false;
        if (telephone != null ? !telephone.equals(patient.telephone) : patient.telephone != null) return false;
        if (photo != null ? !photo.equals(patient.photo) : patient.photo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "ville", nullable = true)
    public Integer getVille() {
        return ville;
    }

    public void setVille(Integer ville) {
        this.ville = ville;
    }
}
