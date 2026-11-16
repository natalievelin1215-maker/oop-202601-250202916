# Analisis Penerapan Prinsip SOLID pada Sistem Aset-Pinjam

Dokumen ini menjelaskan penerapan prinsip-prinsip desain berorientasi objek SOLID pada arsitektur kode sistem Aset-Pinjam, lengkap dengan penunjukan kelas terkait dan alasan desainnya.

---

## 1. Single Responsibility Principle (SRP)
> *Sebuah kelas seharusnya hanya memiliki satu alasan untuk berubah (hanya memiliki satu tanggung jawab utama).*

- **Kelas yang Diterapkan**:
  - `id.ac.upb.asetpinjam.model.Aset`
  - `id.ac.upb.asetpinjam.katalog.KatalogAset`
  - `id.ac.upb.asetpinjam.jadwal.JadwalPeminjaman`
- **Alasan & Analisis Kode**:
  - Kelas `Aset` hanya bertanggung jawab untuk mengelola identitas, atribut, validasi data dasar, dan state individual suatu aset (`kode`, `nama`, `status`, `kondisi`). Kelas ini tidak dibebani oleh tugas manajemen pencarian atau penyimpanan koleksi di memori.
  - Tanggung jawab penampungan, pengelompokan, dan pencarian teks nama aset (*case-insensitive*) didelegasikan secara terpisah kepada kelas `KatalogAset`.
  - Tanggung jawab penjadwalan waktu, deteksi tabrakan rentang peminjaman (*bentrok*), dan batasan kuota peminjaman didelegasikan ke kelas `JadwalPeminjaman`.
  - Dengan pemisahan ini, perubahan aturan pencarian aset tidak akan memengaruhi logika entitas `Aset`, begitu pula perubahan aturan penjadwalan hanya akan mengubah kelas `JadwalPeminjaman`.

---

## 2. Open/Closed Principle (OCP)
> *Entitas perangkat lunak harus terbuka untuk ekstensi (open for extension), tetapi tertutup untuk modifikasi (closed for modification).*

- **Antarmuka dan Kelas yang Diterapkan**:
  - Interface `id.ac.upb.asetpinjam.kontrak.AturanDenda`
  - Kelas `id.ac.upb.asetpinjam.denda.DendaFlat`
  - Kelas `id.ac.upb.asetpinjam.denda.DendaProgresif`
- **Alasan & Analisis Kode**:
  - Pola perhitungan denda keterlambatan dirancang menggunakan Strategy Pattern berbasis antarmuka `AturanDenda`.
  - Jika di masa mendatang institusi menginginkan strategi perhitungan denda baru (misalnya `DendaLibur`, `DendaMahasiswaBerprestasi`, atau denda berbasis poin pelanggaran), pengembang cukup membuat kelas baru yang mengimplementasikan metode `hitung(Aset aset, int hariTerlambat)` dari interface `AturanDenda`.
  - Kode klien atau kelas-kelas strategi yang sudah ada (`DendaFlat` dan `DendaProgresif`) tidak perlu disentuh atau dimodifikasi sama sekali, sehingga meminimalkan risiko regresi bug pada sistem lama.

---

## 3. Liskov Substitution Principle (LSP)
> *Objek dari superclass harus dapat digantikan oleh objek dari subclass-nya tanpa mengubah kebenaran atau perilaku program yang diharapkan.*

- **Kelas yang Diterapkan**:
  - Superclass abstrak `id.ac.upb.asetpinjam.model.Aset`
  - Subclass `Ruang`, `Laptop`, dan `AlatLab`
- **Alasan & Analisis Kode**:
  - Seluruh kelas turunan (`Ruang`, `Laptop`, `AlatLab`) memenuhi kontrak invariant yang ditetapkan oleh `Aset`.
  - Konstruktor dari setiap subclass memanggil `super(kode, nama)` sehingga validasi dasar (kode dan nama tidak boleh null atau kosong) tetap ditegakkan secara seragam.
  - Subclass meng-override metode abstrak `hitungDenda(int hariTerlambat)` dan `getJenis()` dengan tetap mempertahankan kontrak nilai kembalian: tidak melempar unexpected exception baru, mengembalikan nilai denda yang positif/nol untuk hari >= 0, dan menolak hari negatif secara konsisten dengan `IllegalArgumentException`.
  - Kode klien yang memproses koleksi polimorfis `List<Aset>` (sebagaimana diuji pada `Minggu03HierarkiTest` dan `Minggu04PolimorfismeTest`) dapat memperlakukan seluruh objek subclass secara seragam tanpa perlu melakukan type casting atau pengondisian tipe objek yang rapuh.

---

## 4. Interface Segregation Principle (ISP)
> *Klien tidak boleh dipaksa untuk bergantung pada antarmuka (interface) yang metodenya tidak mereka gunakan.*

- **Antarmuka yang Diterapkan**:
  - `id.ac.upb.asetpinjam.kontrak.DapatDipinjam`
  - `id.ac.upb.asetpinjam.kontrak.AturanDenda`
- **Alasan & Analisis Kode**:
  - Interface dibuat ramping, kohesif, dan fokus pada satu kapabilitas tertentu (*role interface*).
  - Interface `DapatDipinjam` hanya mendeklarasikan satu fungsi yaitu `boolean bisaDipinjam()`. Sistem tidak menggabungkan fungsi perhitungan denda atau operasi katalog ke dalam antarmuka ini.
  - Komponen sistem yang hanya bertugas memvalidasi kelayakan peminjaman hanya bergantung pada `DapatDipinjam`, tanpa terbebani atau terikat oleh dependensi perhitungan keuangan dari `AturanDenda`.
