public class Tilbud {



    public Tilbud(String companyName, String produktnavn, String informasjon, String egenandel, String kjoerelengde, String pris) {
        this.companyName = companyName;
        this.produktnavn = produktnavn;
        this.informasjon = informasjon;
        this.egenandel = egenandel;
        this.kjoerelengde = kjoerelengde;
        this.pris = pris;
    }

    public String companyName;
    public String produktnavn;
    public String informasjon;
    public String egenandel;
    public String kjoerelengde;
    public String pris;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProduktnavn() {
        return produktnavn;
    }

    public void setProduktnavn(String produktnavn) {
        this.produktnavn = produktnavn;
    }

    public String getInformasjon() {
        return informasjon;
    }

    public void setInformasjon(String informasjon) {
        this.informasjon = informasjon;
    }

    public String getEgenandel() {
        return egenandel;
    }

    public void setEgenandel(String egenandel) {
        this.egenandel = egenandel;
    }

    public String getKjoerelengde() {
        return kjoerelengde;
    }

    public void setKjoerelengde(String kjoerelengde) {
        this.kjoerelengde = kjoerelengde;
    }

    public String getPris() {
        return pris;
    }

    public void setPris(String pris) {
        this.pris = pris;
    }
}
