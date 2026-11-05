# Asumsi dan Keputusan Desain

Isi bagian ini sendiri. Satu entri per keputusan. Dinilai dari kejelasan alasan dan konsistensi dengan kode dan tes Anda,
bukan dari jawaban "benar".

Format tiap entri:

```
## A-NN: judul singkat
- Pertanyaan: ...
- Keputusan: ...
- Alasan: ...
- Dampak ke kode: ... (kelas/metode)
- Tes yang membuktikan: ... (nama tes di folder mahasiswa/)
```

## Aturan ambigu dari docs/aturan-bisnis.md

Salin setiap pertanyaan ambigu (nomor 1 sampai 7) ke sini dan jawab. Contoh:

## A-01: batas waktu bentrok
- Pertanyaan: 09:00-11:00 dan 11:00-13:00 pada aset yang sama bentrok atau tidak?
- Keputusan: (isi)
- Alasan: (isi)
- Dampak ke kode: (isi)
- Tes yang membuktikan: (isi)

## Asumsi lain

## A-08: Rumus Denda Progresif
- Pertanyaan: Bagaimana formula perhitungan denda progresif untuk keterlambatan pengembalian aset?
- Keputusan: Keterlambatan hari 1-7 mengikuti denda flat standar (`aset.hitungDenda(hari)`). Untuk keterlambatan di atas 7 hari (mulai hari ke-8), dikenakan biaya penalti tambahan sebesar 50% dari tarif dasar harian aset (`aset.hitungDenda(1) / 2`) untuk setiap hari kelebihannya. Rumus: `denda = flat + (hariTerlambat - 7) * (tarifHarian / 2)`.
- Alasan: Memberikan masa tarif normal pada minggu pertama (hari 1-7), kemudian meningkatkan beban denda secara proporsional dan progresif agar peminjam segera mengembalikan aset, serta menjamin nilai denda tidak pernah turun dan pada hari ke-30 dipastikan lebih mahal dari denda flat.
- Dampak ke kode: Kelas `DendaProgresif.java` (metode `hitung`).
- Tes yang membuktikan: `Minggu05AbstraksiTest.dendaProgresifMemenuhiSyaratMinimal`.
