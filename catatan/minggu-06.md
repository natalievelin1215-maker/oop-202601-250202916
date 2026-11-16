# Catatan Minggu 06: UML dan SOLID

## Yang saya kerjakan
Pada minggu 6 ini, saya menyusun dokumentasi pemodelan sistem berbasis UML dan analisis arsitektur prinsip SOLID pada direktori `docs/uml/`. Saya membuat Class Diagram komprehensif pada `docs/uml/class.md` yang memetakan seluruh kelas, interface, enum, atribut, method, visibilitas, paket, serta relasi (*inheritance*, *realization*, *aggregation*, dan *multiplicity*) secara konsisten dengan kode implementasi aktual. Selain itu, saya memodelkan Sequence Diagram pengajuan peminjaman pada `docs/uml/sequence.md` dengan skenario sukses dan bentrok menggunakan fragmen alternatif (`alt`), serta menyusun laporan evaluasi penerapan prinsip-prinsip SOLID pada `docs/uml/solid.md`.

## Keputusan desain
- **Format Diagram (Markdown + Mermaid)**: Menggunakan format Markdown yang mengintegrasikan diagram Mermaid agar diagram dapat langsung dirender secara visual di GitHub, IDE, maupun antarmuka dokumentasi tanpa membutuhkan aplikasi editor biner khusus.
- **Konsistensi Class Diagram terhadap Kode Nyata**: Diagram dirancang cermat mengikuti struktur kode sumber aktual yang telah dibangun dari minggu 1 hingga 5, membagi kelas berdasarkan paket (`kontrak`, `model`, `denda`, `katalog`, `jadwal`), membedakan kelas abstrak `Aset`, record `Peminjam`, serta mendefinisikan multiplicity relasi secara tepat.
- **Sequence Diagram dengan Fragmen `alt`**: Memodelkan interaksi dinamis metode `ajukan(Peminjaman baru)` pada `JadwalPeminjaman`. Alur memuat pengecekan ketersediaan aset `bisaDipinjam()` dan percabangan `alt` untuk menangani skenario bentrok jadwal (melempar exception) vs skenario sukses (penyimpanan ke dalam list peminjaman).
- **Pemilihan Prinsip SOLID**: Mendokumentasikan 4 prinsip SOLID utama pada `docs/uml/solid.md`:
  - *Single Responsibility Principle (SRP)*: Pemisahan tanggung jawab antara entitas `Aset`, pengindeksan `KatalogAset`, dan manajemen waktu `JadwalPeminjaman`.
  - *Open/Closed Principle (OCP)*: Fleksibilitas penambahan skema aturan denda baru via interface `AturanDenda` tanpa mengubah kode yang ada.
  - *Liskov Substitution Principle (LSP)*: Kemampuan substitusi polimorfis seluruh turunan `Aset` (`Ruang`, `Laptop`, `AlatLab`) tanpa melanggar kontrak superclass.
  - *Interface Segregation Principle (ISP)*: Pemisahan antarmuka ramping dan terfokus (`DapatDipinjam` dan `AturanDenda`).

## Kendala
Kendala utama yang dihadapi adalah menyelaraskan setiap detail pada diagram sekuensial dan diagram kelas agar benar-benar presisi dengan nama method, parameter, dan alur eksekusi pada kode Java yang sebenarnya, bukan sekadar diagram abstrak. Kendala ini diatasi dengan melakukan peninjauan ulang (*code inspection*) terhadap seluruh method dan atribut pada setiap kelas sebelum memetakannya ke notasi Mermaid.

## Penggunaan AI
- Alat yang dipakai: Gemini
- Bagian yang dibantu AI: Perancangan sintaks diagram kelas dan sekuensial dalam format Mermaid, perumusan argumen analisis prinsip SOLID pada kode nyata, serta pembuatan berkas catatan refleksi mingguan.
- Prompt utama:
  1. `Buatkan Class Diagram UML dalam format Mermaid yang mencakup seluruh paket (model, kontrak, denda, katalog, jadwal), atribut dengan visibilitasnya, method, serta relasi inheritance, realization, dan agregasi sesuai kode aktual sistem Aset-Pinjam.`
  2. `Buatkan Sequence Diagram UML dalam format Mermaid untuk skenario pengajuan peminjaman di JadwalPeminjaman yang memodelkan skenario sukses dan skenario bentrok menggunakan fragmen alt.`
  3. `Tuliskan analisis minimal 2 prinsip SOLID (seperti SRP, OCP, LSP, dan ISP) pada docs/uml/solid.md dengan menunjukkan kelas yang relevan dan memberikan alasan penerapannya.`
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Memastikan relasi agregasi pada diagram kelas secara spesifik menunjuk ke tipe objek yang ditampung (`Aset` pada `KatalogAset` dan `Peminjaman` pada `JadwalPeminjaman`), serta membatasi alur diagram sekuensial agar fokus pada interaksi objek domain yang relevan sesuai panduan praktikum.
- Satu hal yang saya pahami lebih baik sekarang: Memahami korelasi langsung antara desain arsitektur UML dan implementasi kode nyata, serta bagaimana prinsip SOLID (khususnya OCP dan LSP) menjaga sistem tetap modular, mudah diperluas, dan tahan terhadap regresi saat kode bertambah kompleks.
