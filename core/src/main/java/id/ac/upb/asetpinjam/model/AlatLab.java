package id.ac.upb.asetpinjam.model;
import id.ac.upb.asetpinjam.kontrak.DapatDipinjam; 

/** Minggu 3: alat laboratorium. Laboratorium tidak boleh kosong; risiko tidak boleh null. */
public class AlatLab extends Aset {

    // TODO minggu 3: field laboratorium dan tingkatRisiko.
    private String laboratorium;
    private TingkatRisiko tingkatRisiko;

    public AlatLab(String kode, String nama, String laboratorium, TingkatRisiko tingkatRisiko) {
        super(kode, nama);
        if (laboratorium == null || laboratorium.isBlank()) {
            throw new IllegalArgumentException(
                "Laboratorium tidak boleh null atau kosong"
            );
        }
        if (tingkatRisiko == null) {
            throw new IllegalArgumentException(
                "Tingkat risiko tidak boleh null"
            );
        }
        this.laboratorium = laboratorium;
        this.tingkatRisiko = tingkatRisiko;
    }

    public String getLaboratorium() {
        return laboratorium;
    }

    public TingkatRisiko getTingkatRisiko() {
        return tingkatRisiko;
    }

    // TODO minggu 3: override getJenis(). Minggu 4: override hitungDenda(int).
    @Override 
    public JenisAset getJenis() {
        return JenisAset.ALAT_LAB;
    }
}
