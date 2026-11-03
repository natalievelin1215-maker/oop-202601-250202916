# Catatan Minggu 02: Struktur OOP: Class, Object, Encapsulation

## Yang saya kerjakan
Pada minggu ini, saya menyelesaikan tugas implementasi kelas `Aset.java` pada modul `core` (`id.ac.upb.asetpinjam.model`). Saya mendefinisikan seluruh field secara private (`kode`, `nama`, `status`, dan `kondisi`) serta menginisialisasi nilai default status menjadi `StatusAset.TERSEDIA` dan kondisi menjadi `Kondisi.BAIK` di dalam konstruktor. Selain itu, saya mengimplementasikan metode getter untuk semua field dan setter dengan validasi untuk field yang dapat diubah (`nama`, `status`, `kondisi`), serta memastikan field `kode` bersifat read-only tanpa menyediakan setter.

## Keputusan desain
- **Enkapsulasi Penuh**: Seluruh field pada kelas `Aset` dideklarasikan dengan modifier `private` agar keadaan internal objek terlindungi dari modifikasi langsung di luar kelas.
- **Immutability pada `kode`**: Atribut `kode` tidak dibuatkan metode setter (`setKode`) karena kode bertindak sebagai identitas unik aset yang tidak boleh diubah setelah objek diinstansiasi.
- **Validasi Nilai Kosong dengan `isBlank()`**: Validasi untuk `kode` dan `nama` menggunakan pengecekan `null` dan `isBlank()`, sehingga nilai yang hanya berisi spasi atau whitespace dianggap tidak valid dan langsung melempar `IllegalArgumentException`.
- **Nilai Awal Default**: Status dan kondisi diinisialisasi langsung di dalam konstruktor ke nilai default bisnis (`StatusAset.TERSEDIA` dan `Kondisi.BAIK`) untuk menjamin status awal yang konsisten.

## Kendala
Kendala yang dihadapi terjadi saat pengujian unit test (`Minggu02AsetTest`), di mana input string yang hanya berisi spasi kosong (misalnya `"   "`) sempat lolos jika hanya menggunakan validasi `isEmpty()`. Kendala ini berhasil diatasi dengan mengganti validasi menjadi `isBlank()`, sehingga semua string kosong maupun yang hanya berisi spasi tertolak dengan `IllegalArgumentException`. Selain itu, saya juga harus teliti agar tidak membuat `setKode` agar lulus pengujian refleksi `semuaFieldPrivatDanKodeTidakBisaDiubah`.

## Penggunaan AI
- Alat yang dipakai: Gemini
- Bagian yang dibantu AI: Implementasi validasi pada konstruktor dan setter di kelas `Aset.java`, penyesuaian pengecekan spasi kosong dengan `isBlank()`, serta penyusunan dokumentasi catatan minggu 02.
- Prompt utama:
  1. `gemini bantu saya mengerjakan minggu-02, saya di suruh mengerjakan aset.java yang ada Todo minggu 2, bantu saya mengerjakan agar saya pahami.`
  2. `kerjakan catatan/minggu-02.md`
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Mengubah pengecekan `isEmpty()` menjadi `isBlank()` pada validasi `kode` dan `nama` karena pengujian unit test juga memeriksa penolakan terhadap spasi kosong (whitespace).
- Satu hal yang saya pahami lebih baik sekarang: Memahami esensi enkapsulasi (encapsulation) dan keamanan objek, di mana hak akses `private`, metode getter/setter dengan validasi ketat, serta peniadaan setter pada identitas objek (`kode`) sangat penting untuk menjaga integritas data.
