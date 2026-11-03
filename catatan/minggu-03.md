# Catatan Minggu 03: Inheritance

## Yang saya kerjakan
Pada minggu 3 ini, saya menyelesaikan tugas implementasi konsep pewarisan (inheritance) pada hierarki kelas turunan `Aset`, yaitu `Ruang.java`, `Laptop.java`, dan `AlatLab.java` pada modul `core` (`id.ac.upb.asetpinjam.model`). Pada masing-masing subclass, saya memanggil konstruktor induk menggunakan `super(kode, nama)`, mendefinisikan field spesifik dengan hak akses `private`, menerapkan validasi input pada konstruktor, membuat metode getter, serta meng-override metode `getJenis()` agar mengembalikan nilai enum `JenisAset` yang sesuai (`RUANG`, `LAPTOP`, `ALAT_LAB`).

## Keputusan desain
- **Pemisahan Field Induk vs Subclass**:
  - Field umum yang berlaku untuk seluruh jenis aset diletakkan di kelas induk (`Aset`): `kode`, `nama`, `status`, dan `kondisi`. Validasi terhadap kode dan nama dipusatkan di kelas induk dan dieksekusi melalui pemanggilan `super(kode, nama)`.
  - Field spesifik hanya diletakkan pada subclass yang relevan: `kapasitas` pada `Ruang`, `spesifikasi` dan `nomorInventaris` pada `Laptop`, serta `laboratorium` dan `tingkatRisiko` pada `AlatLab`.
- **Validasi Nilai Input**:
  - `Ruang`: kapasitas divalidasi harus lebih besar dari 0 (`kapasitas <= 0` melempar `IllegalArgumentException`).
  - `Laptop`: spesifikasi dan nomor inventaris divalidasi tidak boleh `null` maupun kosong/spasi menggunakan `isBlank()`.
  - `AlatLab`: nama laboratorium tidak boleh `null`/kosong menggunakan `isBlank()`, dan `tingkatRisiko` tidak boleh `null`.
- **Polimorfisme `getJenis()`**: Meng-override metode `getJenis()` pada setiap subclass sehingga pemanggil dapat mengetahui jenis aset secara seragam saat objek diperlakukan sebagai tipe induk `Aset`.

### Keterbatasan Inheritance vs Komposisi di Sistem Ini
Salah satu contoh di sistem ini di mana inheritance kurang tepat dibanding komposisi adalah pemodelan aset hibrida atau atribut yang dinamis. Misalnya, ada aset seperti *Ruang Laboratorium Komputer* yang memiliki kapasitas ruangan sekaligus berisi inventaris alat lab dengan tingkat risiko tertentu. Karena Java hanya mendukung *single inheritance*, kita tidak bisa membuat class turunan sekaligus dari `Ruang` dan `AlatLab`. Jika menggunakan pendekatan komposisi (composition over inheritance), karakteristik seperti `LokasiRuangan`, `SpesifikasiPerangkat`, atau `ProfilRisiko` dapat dijadikan objek terpisah yang disematkan ke dalam `Aset`, sehingga aset menjadi jauh lebih fleksibel dan tidak terikat pada hierarki kelas yang kaku.

## Kendala
Kendala yang dihadapi adalah memastikan urutan instruksi di dalam konstruktor subclass, di mana pemanggilan `super(kode, nama)` wajib berada di baris paling awal sebelum validasi field milik subclass dilakukan. Selain itu, harus dipastikan bahwa validasi string pada subclass menggunakan `isBlank()` agar nilai yang hanya berisi spasi whitespace (seperti `" "`) tertolak sesuai spesifikasi pengujian `Minggu03HierarkiTest`.

## Penggunaan AI
- Alat yang dipakai: Gemini
- Bagian yang dibantu AI: Implementasi konstruktor dan validasi pada kelas `Ruang`, `Laptop`, dan `AlatLab`, penyesuaian override `getJenis()`, serta perumusan analisis inheritance vs komposisi pada catatan.
- Prompt utama:
  1. `kerjakan docs/minggu-03.md`
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Memastikan seluruh validasi teks pada subclass konsisten menggunakan `isBlank()` alih-alih `isEmpty()` agar spasi kosong tertolak sesuai test case.
- Satu hal yang saya pahami lebih baik sekarang: Memahami cara kerja pewarisan (*code reuse* dan validasi warisan melalui `super`), serta menyadari batas keleluasaan *is-a relationship* ketika entitas memiliki karakteristik yang saling bersilangan.

