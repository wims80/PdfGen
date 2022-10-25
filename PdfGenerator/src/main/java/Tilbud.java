public abstract class Tilbud {
    public String companyName;
    public String produktnavn;
    public String informasjon;
    public String egenandel;
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

    public String getPris() {
        return pris;
    }

    public void setPris(String pris) {
        this.pris = pris;
    }
}
