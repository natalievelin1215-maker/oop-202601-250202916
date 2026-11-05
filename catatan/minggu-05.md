# Catatan Minggu 05: Abstraction

## Yang saya kerjakan
Pada minggu 5 ini, saya mengimplementasikan konsep abstraksi dengan memanfaatkan abstract class dan interface. Saya mengubah kelas `Aset` menjadi kelas abstrak serta menjadikan metode `hitungDenda(int hariTerlambat)` sebagai metode abstrak, sekaligus mengimplementasikan metode `bisaDipinjam()` dari interface `DapatDipinjam` dengan memvalidasi status dan kondisi aset. Selain itu, saya mengimplementasikan interface `AturanDenda` pada kelas `DendaFlat` (denda tetap per hari mengikuti perhitungan aset) dan kelas `DendaProgresif` (denda bertingkat yang semakin tinggi jika durasi keterlambatan bertambah).

## Keputusan desain
- **Pemilihan Abstract Class vs Interface**:
  - `Aset` didefinisikan sebagai `abstract class` karena memiliki state internal bersama (field `kode`, `nama`, `status`, `kondisi`) serta method konkrit yang dipakai bersama, sementara metode penentu seperti `hitungDenda(int)` dan `getJenis()` dijadikan abstrak untuk diserahkan ke masing-masing subclass.
  - `DapatDipinjam` dan `AturanDenda` dirancang sebagai `interface` murni untuk mendefinisikan kontrak kapabilitas perilaku tanpa menyimpan state, mendukung fleksibilitas implementasi strategi denda yang dapat diganti-ganti (pola Strategy).
- **Logika Ketersediaan Peminjaman (`bisaDipinjam`)**:
  Aset hanya valid untuk dipinjam apabila `status == StatusAset.TERSEDIA` dan `kondisi != Kondisi.RUSAK_BERAT` (kondisi `RUSAK_RINGAN` masih diizinkan dipinjam sesuai aturan bisnis).
- **Rumus Denda Progresif (A-08 di docs/asumsi.md)**:
  Untuk 0 hari menghasilkan 0 rupiah. Pada hari ke-1 hingga ke-7 berlaku denda flat. Keterlambatan hari ke-8 ke atas dikenakan penalti tambahan sebesar 50% dari tarif harian aset (`aset.hitungDenda(1) / 2`) untuk setiap hari kelebihannya. Formula: `flat + (hariTerlambat - 7) * (tarifHarian / 2)`. Keputusan ini memastikan denda tidak pernah lebih murah dari flat, tidak pernah turun seiring bertambahnya hari, dan pada hari ke-30 lebih mahal dari tarif flat.

## Kendala
Kendala yang dihadapi adalah merumuskan formula denda progresif yang stabil dan terhindar dari potensi pembulatan floating-point, sembari memastikan kriteria uji terpenuhi (nilai denda tidak boleh turun di setiap transisi hari dan harus lebih mahal dari flat pada hari ke-30). Kendala ini diatasi dengan menggunakan formula denda bertingkat berbasis integer arithmetic menggunakan kelipatan tarif dasar harian.

## Penggunaan AI
- Alat yang dipakai: Gemini
- Bagian yang dibantu AI: Implementasi method abstract pada `Aset`, logika metode `bisaDipinjam()`, pembuatan kelas `DendaFlat`, formulasi matematis untuk `DendaProgresif`, serta penyusunan entri keputusan desain pada `docs/asumsi.md`.
- Prompt utama:
  1. `Bantu saya mengimplementasikan DendaFlat dan merumuskan formula DendaProgresif agar memenuhi syarat: 0 hari = 0, >= flat pada tiap hari 1-30, monotonik naik, dan hari ke-30 > flat.`
  2. `Bagaimana mengubah Aset menjadi abstract class dan mengimplementasikan bisaDipinjam() sesuai aturan bisnis?`
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Menyederhanakan rumus progresif dari saran awal yang menggunakan eksponensial/pangkat menjadi formula penalti bertingkat berbasis threshold 7 hari dengan penalti 50% dari tarif harian, agar lebih mudah dipahami, tidak rentan overflow, dan konsisten dengan sistem denda harian.
- Satu hal yang saya pahami lebih baik sekarang: Memahami batas peran antara abstract class (berbagi state dan kode dasar antar kelas turunan yang berkerabat dekat) dan interface (kontrak kontrak perilaku murni yang memungkinkan polimorfisme lintas hierarki kelas yang berbeda).

