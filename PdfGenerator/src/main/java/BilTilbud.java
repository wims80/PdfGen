public class BilTilbud extends Tilbud {

    public BilTilbud(String companyName, String produktnavn, String informasjon, String egenandel, String kjoerelengde, String pris) {
        this.companyName = companyName;
        this.produktnavn = produktnavn;
        this.informasjon = informasjon;
        this.egenandel = egenandel;
        this.kjoerelengde = kjoerelengde;
        this.pris = pris;
    }

    public String kjoerelengde;

    public String getKjoerelengde() {
        return kjoerelengde;
    }

    public void setKjoerelengde(String kjoerelengde) {
        this.kjoerelengde = kjoerelengde;
    }
}
