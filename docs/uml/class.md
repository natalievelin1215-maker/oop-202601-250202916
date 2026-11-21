# Class Diagram Sistem Aset-Pinjam

Berikut adalah diagram kelas UML yang merepresentasikan struktur aktual dari kode sumber sistem Aset-Pinjam. Diagram mencakup paket, kelas abstrak, antarmuka (*interface*), *enumeration*, atribut, method berserta tipe dan visibilitasnya, serta relasi (*inheritance*, *realization*, *aggregation*, dan *dependency*) beserta kardinalitasnya (*multiplicity*).

``` mermaid
classDiagram
    direction TB

    namespace id.ac.upb.asetpinjam.kontrak {
        class DapatDipinjam {
            <<interface>>
            +bisaDipinjam() boolean
        }

        class AturanDenda {
            <<interface>>
            +hitung(aset: Aset, hariTerlambat: int) long
        }
    }

    namespace id.ac.upb.asetpinjam.model {
        class StatusAset {
            <<enumeration>>
            TERSEDIA
            DIPINJAM
            RUSAK
        }

        class Kondisi {
            <<enumeration>>
            BAIK
            RUSAK_RINGAN
            RUSAK_BERAT
        }

        class JenisAset {
            <<enumeration>>
            RUANG
            LAPTOP
            ALAT_LAB
        }

        class TingkatRisiko {
            <<enumeration>>
            RENDAH
            SEDANG
            TINGGI
        }

        class StatusPeminjaman {
            <<enumeration>>
            DIAJUKAN
            DISETUJUI
            DITOLAK
            DIBATALKAN
            DIPINJAM
            TERLAMBAT
            DIKEMBALIKAN
        }

        class Aset {
            <<abstract>>
            -String kode
            -String nama
            -StatusAset status
            -Kondisi kondisi
            +Aset(kode: String, nama: String)
            +getKode() String
            +getNama() String
            +getStatus() StatusAset
            +getKondisi() Kondisi
            +setNama(nama: String) void
            +setStatus(status: StatusAset) void
            +setKondisi(kondisi: Kondisi) void
            +bisaDipinjam() boolean
            +getJenis() JenisAset*
            +hitungDenda(hariTerlambat: int) long*
        }

        class Ruang {
            -int kapasitas
            +Ruang(kode: String, nama: String, kapasitas: int)
            +getKapasitas() int
            +getJenis() JenisAset
            +hitungDenda(hariTerlambat: int) long
        }

        class Laptop {
            -String spesifikasi
            -String nomorInventaris
            +Laptop(kode: String, nama: String, spesifikasi: String, nomorInventaris: String)
            +getSpesifikasi() String
            +getNomorInventaris() String
            +getJenis() JenisAset
            +hitungDenda(hariTerlambat: int) long
        }

        class AlatLab {
            -String laboratorium
            -TingkatRisiko tingkatRisiko
            +AlatLab(kode: String, nama: String, laboratorium: String, tingkatRisiko: TingkatRisiko)
            +getLaboratorium() String
            +getTingkatRisiko() TingkatRisiko
            +getJenis() JenisAset
            +hitungDenda(hariTerlambat: int) long
        }

        class Peminjam {
            <<record>>
            -String id
            -String nama
            +Peminjam(id: String, nama: String)
            +id() String
            +nama() String
        }

        class Peminjaman {
            -String id
            -Peminjam peminjam
            -Aset aset
            -LocalDateTime mulai
            -LocalDateTime selesai
            -StatusPeminjaman status
            +Peminjaman(id: String, peminjam: Peminjam, aset: Aset, mulai: LocalDateTime, selesai: LocalDateTime)
            +getId() String
            +getPeminjam() Peminjam
            +getAset() Aset
            +getMulai() LocalDateTime
            +getSelesai() LocalDateTime
            +getStatus() StatusPeminjaman
            +setStatusDariPenyimpanan(status: StatusPeminjaman) void
            +ubahStatus(baru: StatusPeminjaman) void
        }
    }

    namespace id.ac.upb.asetpinjam.denda {
        class DendaFlat {
            +hitung(aset: Aset, hariTerlambat: int) long
        }

        class DendaProgresif {
            +hitung(aset: Aset, hariTerlambat: int) long
        }
    }

    namespace id.ac.upb.asetpinjam.katalog {
        class KatalogAset {
            -List~Aset~ daftar
            +tambah(aset: Aset) void
            +semua() List~Aset~
            +cari(kata: String) List~Aset~
            +cari(kata: String, jenis: JenisAset) List~Aset~
        }
    }

    namespace id.ac.upb.asetpinjam.jadwal {
        class JadwalPeminjaman {
            -List~Peminjaman~ daftar
            -int kuotaLaptop
            +JadwalPeminjaman(kuotaLaptop: int)
            #kuotaLaptop() int
            #daftarMutable() List~Peminjaman~
            +tambah(p: Peminjaman) void
            +bentrok(baru: Peminjaman) boolean
            +semuaUrutMulai() List~Peminjaman~
            +perAset() Map~String, List~Peminjaman~ ~
            +hapus(id: String) boolean
            +ajukan(baru: Peminjaman) void
        }
    }

    %% Relasi Realization (Implements)
    DapatDipinjam <|.. Aset : implements
    AturanDenda <|.. DendaFlat : implements
    AturanDenda <|.. DendaProgresif : implements

    %% Relasi Inheritance (Generalization)
    Aset <|-- Ruang : extends
    Aset <|-- Laptop : extends
    Aset <|-- AlatLab : extends

    %% Relasi Asosiasi / Komposisi / Agregasi
    Aset "1" --> "1" StatusAset : status
    Aset "1" --> "1" Kondisi : kondisi
    AlatLab "1" --> "1" TingkatRisiko : tingkatRisiko

    Peminjaman "1" --> "1" Peminjam : peminjam
    Peminjaman "1" --> "1" Aset : aset
    Peminjaman "1" --> "1" StatusPeminjaman : status

    KatalogAset "1" o-- "*" Aset : agregasi
    JadwalPeminjaman "1" o-- "*" Peminjaman : agregasi

    %% Dependency
    AturanDenda ..> Aset : relies on
    KatalogAset ..> JenisAset : uses
```

## Penjelasan Relasi & Multiplicity:
1. **Generalization/Inheritance**: `Ruang`, `Laptop`, dan `AlatLab` mewarisi kelas abstrak `Aset`.
2. **Realization/Interface Implementation**:
   - `Aset` merealisasikan `DapatDipinjam` (`bisaDipinjam()`).
   - `DendaFlat` dan `DendaProgresif` merealisasikan antarmuka `AturanDenda`.
3. **Aggregation (`o--`)**:
   - `KatalogAset` menampung koleksi 0 atau banyak (`*`) objek `Aset`.
   - `JadwalPeminjaman` menampung koleksi 0 atau banyak (`*`) objek `Peminjaman`.
4. **Association**:
   - Setiap `Peminjaman` merujuk tepat 1 `Peminjam` dan 1 `Aset` yang dipinjam.
   - `Aset` memiliki 1 `StatusAset` dan 1 `Kondisi`.
