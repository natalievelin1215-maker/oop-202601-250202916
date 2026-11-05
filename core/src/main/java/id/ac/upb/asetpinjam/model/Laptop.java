package id.ac.upb.asetpinjam.model;
import id.ac.upb.asetpinjam.config.Varian;

/** Minggu 3: aset berupa laptop. Spesifikasi dan nomor inventaris tidak boleh kosong. */
public class Laptop extends Aset {

    // TODO minggu 3: field spesifikasi dan nomorInventaris.
    private String spesifikasi;
    private String nomorInventaris;

    public Laptop(String kode, String nama, String spesifikasi, String nomorInventaris) {
        super(kode, nama);
        if (spesifikasi == null || spesifikasi.isBlank()) {
            throw new IllegalArgumentException("Spesifikasi tidak boleh null atau kosong");
        }

        if (nomorInventaris == null || nomorInventaris.isBlank()) {
            throw new IllegalArgumentException("Nomor inventaris tidak boleh null atau kosong");
        }
        this.spesifikasi = spesifikasi;
        this.nomorInventaris = nomorInventaris;
    }

    public String getSpesifikasi() {
        return spesifikasi;
    }

    public String getNomorInventaris() {
        return nomorInventaris;
    }

    // TODO minggu 3: override getJenis(). Minggu 4: override hitungDenda(int).
    @Override
    public JenisAset getJenis() {
        return JenisAset.LAPTOP;
    }

    /** Minggu 4: denda laptop = 2x tarif dasar x hari keterlambatan. */
    @Override
    public long hitungDenda(int hariTerlambat) {
        if (hariTerlambat < 0) {
            throw new IllegalArgumentException("Hari keterlambatan tidak boleh negatif");
        }
        return (long) hariTerlambat * 2 * Varian.aktif().dendaPerHari;
    }
}
