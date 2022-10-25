import java.util.List;

public abstract class CalculatorInput {

    public String       mellomnavn;
    public String       etternavn;
    public String       foedselsnr;
    public List<String> foreningsMedlemsskap;

    public String getMellomnavn() {
        return mellomnavn;
    }

    public void setMellomnavn(String mellomnavn) {
        this.mellomnavn = mellomnavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getFoedselsnr() {
        return foedselsnr;
    }

    public void setFoedselsnr(String foedselsnr) {
        this.foedselsnr = foedselsnr;
    }

    public List<String> getForeningsMedlemsskap() {
        return foreningsMedlemsskap;
    }

    public void setForeningsMedlemsskap(List<String> foreningsMedlemsskap) {
        this.foreningsMedlemsskap = foreningsMedlemsskap;
    }
}
