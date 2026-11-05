# Catatan Minggu 04: Polymorphism

## Yang saya kerjakan
Pada minggu 4 ini, saya mengimplementasikan konsep polimorfisme dalam OOP melalui method overriding dan method overloading. Saya meng-override metode `hitungDenda(int hariTerlambat)` pada kelas `Aset` dan ketiga subclass-nya (`Ruang` dengan multiplier 1x, `Laptop` dengan multiplier 2x, dan `AlatLab` dengan multiplier 4x) yang dihitung berdasarkan tarif dasar dari `Varian.aktif().dendaPerHari`. Selain itu, saya mengimplementasikan dua metode overloading `cari(...)` pada kelas `KatalogAset` (`cari(String kata)` dan `cari(String kata, JenisAset jenis)`) untuk mendukung pencarian aset secara case-insensitive.

## Keputusan desain
- **Dynamic Binding pada Perhitungan Denda (Overriding)**:
  Metode `hitungDenda(int)` didefinisikan pada kelas induk `Aset` dan di-override secara spesifik di tiap kelas turunan (`Ruang`, `Laptop`, `AlatLab`). Ketika objek diakses melalui referensi tipe induk `Aset`, JVM secara dinamis mengikat dan memanggil implementasi subclass yang sesuai.
- **Validasi dan Tipe Data**:
  Nilai hari negatif (`hariTerlambat < 0`) secara tegas divalidasi dengan melempar `IllegalArgumentException`, sedangkan jika bernilai 0 menghasilkan denda 0. Perkalian denda dikonversi ke tipe `long` untuk mencegah risiko overflow numerik.
- **Overloading pada `KatalogAset` dan Prinsip DRY**:
  Metode `cari(String kata, JenisAset jenis)` memanfaatkan kembali hasil pemanggilan `cari(String kata)` kemudian melakukan pemfilteran berdasarkan enum `JenisAset`. Hal ini menghindari duplikasi logika pencarian teks nama aset.
- **Pencarian Bebas Huruf Besar/Kecil**:
  Pencarian menggunakan `toLowerCase(Locale.ROOT)` untuk memastikan pencarian substring nama aset bersifat case-insensitive secara aman di berbagai sistem/locale.



## Kendala
Kendala yang dihadapi adalah memahami secara praktis perbedaan mekanisme resolusi compile-time pada overloading (berdasarkan jumlah dan tipe argumen) dengan dynamic dispatch pada runtime overriding. Selain itu, penting untuk memastikan bahwa pembacaan tarif dasar dari `Varian.aktif().dendaPerHari` dan pemfilteran enum `JenisAset` terintegrasi dengan baik dengan pengujian unit di `Minggu04PolimorfismeTest`.

## Penggunaan AI
- Alat yang dipakai: Gemini
- Bagian yang dibantu AI: Implementasi metode `hitungDenda` pada kelas `Aset`, `Ruang`, `Laptop`, dan `AlatLab`, implementasi overloading metode `cari` pada `KatalogAset`, serta penyusunan berkas refleksi catatan minggu 04.
- Prompt utama:Bantu saya menyusun berkas refleksi catatan minggu 4

- Yang saya ubah atau tolak dari saran AI, dan alasannya: Menyederhanakan implementasi `cari(String, JenisAset)` dengan mendelegasikannya ke `cari(String)` melalui stream filter agar kode tetap bersih dan tidak redundan.
- Satu hal yang saya pahami lebih baik sekarang: Memahami dengan jelas perbedaan antara polimorfisme statis/compile-time (method overloading) dan polimorfisme dinamis/runtime (method overriding melalui dynamic method lookup).

