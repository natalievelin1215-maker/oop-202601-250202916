package id.ac.upb.asetpinjam.denda;

import id.ac.upb.asetpinjam.kontrak.AturanDenda;
import id.ac.upb.asetpinjam.model.Aset;

/**
 * Minggu 5: denda yang makin berat bila makin lama terlambat.
 *
 * <p>Rumus progresifnya KEPUTUSAN ANDA (catat di docs/asumsi.md). Syarat minimal yang diuji:
 * 0 hari = 0; tidak pernah lebih murah dari DendaFlat; tidak pernah turun saat hari bertambah;
 * pada hari ke-30 lebih mahal dari flat.
 */
public class DendaProgresif implements AturanDenda {

    @Override
    public long hitung(Aset aset, int hariTerlambat) {
        if (aset == null) {
            throw new IllegalArgumentException("Aset tidak boleh null");
        }
        if (hariTerlambat < 0) {
            throw new IllegalArgumentException("Hari keterlambatan tidak boleh negatif");
        }
        if (hariTerlambat == 0) {
            return 0;
        }

        long flat = aset.hitungDenda(hariTerlambat);
        if (hariTerlambat <= 7) {
            return flat;
        }

        // Keterlambatan di atas 7 hari (mulai hari ke-8 dst) dikenakan biaya tambahan progresif
        // sebesar 50% dari tarif dasar harian untuk setiap hari kelebihannya.
        long tarifHarian = aset.hitungDenda(1);
        long hariKelebihan = hariTerlambat - 7;
        long penaltiTambahan = hariKelebihan * (tarifHarian / 2);

        return flat + penaltiTambahan;
    }
}
