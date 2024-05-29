package rs.ac.singidunum.novisad.server.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Zvanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datumIzbora;
    private LocalDateTime datumPrestanka;

    @ManyToOne
    private Nastavnik nastavnik;
    @OneToOne
    private TipZvanja tipZvanja;
    @OneToOne
    private NacunaOblast nacunaOblast;

    public Zvanje() {
    }

    public Zvanje(Long id, LocalDateTime datumIzbora, LocalDateTime datumPrestanka, Nastavnik nastavnik, TipZvanja tipZvanja, NacunaOblast nacunaOblast) {
        this.id = id;
        this.datumIzbora = datumIzbora;
        this.datumPrestanka = datumPrestanka;
        this.nastavnik = nastavnik;
        this.tipZvanja = tipZvanja;
        this.nacunaOblast = nacunaOblast;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatumIzbora() {
        return datumIzbora;
    }

    public void setDatumIzbora(LocalDateTime datumIzbora) {
        this.datumIzbora = datumIzbora;
    }

    public LocalDateTime getDatumPrestanka() {
        return datumPrestanka;
    }

    public void setDatumPrestanka(LocalDateTime datumPrestanka) {
        this.datumPrestanka = datumPrestanka;
    }

    public Nastavnik getNastavnik() {
        return nastavnik;
    }

    public void setNastavnik(Nastavnik nastavnik) {
        this.nastavnik = nastavnik;
    }

    public TipZvanja getTipZvanja() {
        return tipZvanja;
    }

    public void setTipZvanja(TipZvanja tipZvanja) {
        this.tipZvanja = tipZvanja;
    }

    public NacunaOblast getNacunaOblast() {
        return nacunaOblast;
    }

    public void setNacunaOblast(NacunaOblast nacunaOblast) {
        this.nacunaOblast = nacunaOblast;
    }
}
