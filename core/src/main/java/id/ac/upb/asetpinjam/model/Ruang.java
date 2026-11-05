package id.ac.upb.asetpinjam.model;

import id.ac.upb.asetpinjam.config.Varian;

/** Minggu 3: aset berupa ruang. Kapasitas harus lebih dari 0 (IllegalArgumentException). */
public class Ruang extends Aset {

    // TODO minggu 3: field kapasitas.
    private int kapasitas;

    public Ruang(String kode, String nama, int kapasitas) {
        super(kode, nama);
        if (kapasitas <= 0) {
            throw new IllegalArgumentException("Kapasitas harus lebih dari 0");
        }
        this.kapasitas = kapasitas;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    // TODO minggu 3: override getJenis(). Minggu 4: override hitungDenda(int).
    @Override 
    public JenisAset getJenis() {
        return JenisAset.RUANG;
    }

    /** Minggu 4: denda ruang = 1x tarif dasar x hari keterlambatan. */
    @Override
    public long hitungDenda(int hariTerlambat) {
        if (hariTerlambat < 0) {
            throw new IllegalArgumentException("Hari keterlambatan tidak boleh negatif");
        }
        return (long) hariTerlambat * 1 * Varian.aktif().dendaPerHari;
    }
}
