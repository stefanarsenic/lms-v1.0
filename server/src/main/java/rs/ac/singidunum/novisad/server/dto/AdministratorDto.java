package rs.ac.singidunum.novisad.server.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link rs.ac.singidunum.novisad.server.model.korisnik.Administrator}
 */

public class AdministratorDto implements Serializable {
    Long id;
    String korisnickoIme;
    String lozinka;
    String email;
    String ime;
    String prezime;


    public AdministratorDto() {
    }

    public AdministratorDto(Long id, String korisnickoIme, String lozinka, String email, String ime, String prezime) {
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.email = email;
        this.ime = ime;
        this.prezime = prezime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}