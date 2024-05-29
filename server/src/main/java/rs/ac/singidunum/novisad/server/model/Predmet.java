package rs.ac.singidunum.novisad.server.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Predmet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;
    private Integer esbp;
    private Boolean obavezan;
    private Integer brojPredavanja;
    private Integer brojVezbi;
    private Integer drugiObliciNastave;
    private Integer istrazivackiRad;
    private Integer ostaliCasovi;
    @OneToMany(mappedBy = "predmet")
    private Set<Ishod> silabus;
    @OneToMany(mappedBy = "predmet", cascade = CascadeType.ALL)
    private Set<Predmet> preduslov;
    @OneToMany(mappedBy = "predmet")
    private Set<PredmetPlanaZaGodinu> planovi;
    @ManyToOne
    private Predmet predmet;

    public Predmet() {
    }

    public Predmet(Long id, String naziv, Integer esbp, Boolean obavezan, Integer brojPredavanja, Integer brojVezbi, Integer drugiObliciNastave, Integer istrazivackiRad, Integer ostaliCasovi, Set<Ishod> silabus, Set<Predmet> preduslov, Set<PredmetPlanaZaGodinu> planovi, Predmet predmet) {
        this.id = id;
        this.naziv = naziv;
        this.esbp = esbp;
        this.obavezan = obavezan;
        this.brojPredavanja = brojPredavanja;
        this.brojVezbi = brojVezbi;
        this.drugiObliciNastave = drugiObliciNastave;
        this.istrazivackiRad = istrazivackiRad;
        this.ostaliCasovi = ostaliCasovi;
        this.silabus = silabus;
        this.preduslov = preduslov;
        this.planovi = planovi;
        this.predmet = predmet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getEsbp() {
        return esbp;
    }

    public void setEsbp(Integer esbp) {
        this.esbp = esbp;
    }

    public Boolean getObavezan() {
        return obavezan;
    }

    public void setObavezan(Boolean obavezan) {
        this.obavezan = obavezan;
    }

    public Integer getBrojPredavanja() {
        return brojPredavanja;
    }

    public void setBrojPredavanja(Integer brojPredavanja) {
        this.brojPredavanja = brojPredavanja;
    }

    public Integer getBrojVezbi() {
        return brojVezbi;
    }

    public void setBrojVezbi(Integer brojVezbi) {
        this.brojVezbi = brojVezbi;
    }

    public Integer getDrugiObliciNastave() {
        return drugiObliciNastave;
    }

    public void setDrugiObliciNastave(Integer drugiObliciNastave) {
        this.drugiObliciNastave = drugiObliciNastave;
    }

    public Integer getIstrazivackiRad() {
        return istrazivackiRad;
    }

    public void setIstrazivackiRad(Integer istrazivackiRad) {
        this.istrazivackiRad = istrazivackiRad;
    }

    public Integer getOstaliCasovi() {
        return ostaliCasovi;
    }

    public void setOstaliCasovi(Integer ostaliCasovi) {
        this.ostaliCasovi = ostaliCasovi;
    }

    public Set<Ishod> getSilabus() {
        return silabus;
    }

    public void setSilabus(Set<Ishod> silabus) {
        this.silabus = silabus;
    }

    public Set<Predmet> getPreduslov() {
        return preduslov;
    }

    public void setPreduslov(Set<Predmet> preduslov) {
        this.preduslov = preduslov;
    }

    public Set<PredmetPlanaZaGodinu> getPlanovi() {
        return planovi;
    }

    public void setPlanovi(Set<PredmetPlanaZaGodinu> planovi) {
        this.planovi = planovi;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }
}
