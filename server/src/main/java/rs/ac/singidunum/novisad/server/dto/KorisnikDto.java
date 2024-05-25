package rs.ac.singidunum.novisad.server.dto;

import rs.ac.singidunum.novisad.server.model.Korisnik;

import java.io.Serializable;

/**
 * DTO for {@link Korisnik}
 */
public class KorisnikDto implements Serializable {
    private  Long id;
    private  String korisnickoIme;
    private  String lozinka;

    public KorisnikDto() {
    }

    public KorisnikDto(Long id, String korisnickoIme, String lozinka) {
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public Long getId() {
        return id;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "korisnickoIme = " + korisnickoIme + ", " +
                "lozinka = " + lozinka + ")";
    }
}