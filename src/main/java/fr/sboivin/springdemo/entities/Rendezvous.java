package fr.sboivin.springdemo.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
public class Rendezvous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="patient", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Patient_ID_FK"), nullable = false)
    private Patient patient;

    @Basic
    private LocalDateTime dateheure;

    @Basic
    private String type;

    @Basic
    private int duree;

    @Basic
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateheure() {
        return dateheure;
    }

    public void setDateheure(LocalDateTime dateheure) {
        this.dateheure = dateheure;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rendezvous that = (Rendezvous) o;
        return id == that.id && duree == that.duree && Objects.equals(patient, that.patient) && Objects.equals(dateheure, that.dateheure) && Objects.equals(type, that.type) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patient, dateheure, type, duree, note);
    }
}
