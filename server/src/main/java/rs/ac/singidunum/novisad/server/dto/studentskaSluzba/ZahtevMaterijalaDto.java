package rs.ac.singidunum.novisad.server.dto.studentskaSluzba;

import lombok.Value;
import rs.ac.singidunum.novisad.server.dto.AdministratorDto;
import rs.ac.singidunum.novisad.server.dto.RegistrovaniKorisnikDto;
import rs.ac.singidunum.novisad.server.model.korisnik.Administrator;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link rs.ac.singidunum.novisad.server.model.studetnskaSluzba.ZahtevMaterijala}
 */
public class ZahtevMaterijalaDto implements Serializable {
    Long id;
    RegistrovaniKorisnikDto korisnik;
    String naslov;
    String opis;
    Timestamp datumPodnosenja;
    String status;
    AdministratorDto admin;
    Timestamp datumIzmena;


    public ZahtevMaterijalaDto() {
    }

    public ZahtevMaterijalaDto(Long id, RegistrovaniKorisnikDto korisnik, String naslov, String opis, Timestamp datumPodnosenja, String status, AdministratorDto admin, Timestamp datumIzmena) {
        this.id = id;
        this.korisnik = korisnik;
        this.naslov = naslov;
        this.opis = opis;
        this.datumPodnosenja = datumPodnosenja;
        this.status = status;
        this.admin = admin;
        this.datumIzmena = datumIzmena;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegistrovaniKorisnikDto getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(RegistrovaniKorisnikDto korisnik) {
        this.korisnik = korisnik;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Timestamp getDatumPodnosenja() {
        return datumPodnosenja;
    }

    public void setDatumPodnosenja(Timestamp datumPodnosenja) {
        this.datumPodnosenja = datumPodnosenja;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AdministratorDto getAdmin() {
        return admin;
    }

    public void setAdmin(AdministratorDto admin) {
        this.admin = admin;
    }

    public Timestamp getDatumIzmena() {
        return datumIzmena;
    }

    public void setDatumIzmena(Timestamp datumIzmena) {
        this.datumIzmena = datumIzmena;
    }
}