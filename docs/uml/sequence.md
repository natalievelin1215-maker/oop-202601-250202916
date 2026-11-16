# Sequence Diagram: Pengajuan Peminjaman

Diagram sekuensial ini memodelkan proses pengajuan peminjaman (`ajukan(Peminjaman baru)`) pada kelas `JadwalPeminjaman`. Diagram mencakup skenario sukses dan skenario gagal/bentrok dengan memanfaatkan fragmen alternatif (`alt`).

```mermaid
sequenceDiagram
    autonumber
    actor Pengguna as Aktor / Peminjam
    participant Klien as Klien / Service
    participant Peminjaman as baru: Peminjaman
    participant Jadwal as jadwal: JadwalPeminjaman
    participant Aset as aset: Aset
    participant Daftar as daftar: List~Peminjaman~

    Pengguna->>Klien: Ajukan peminjaman aset
    activate Klien

    Klien->>Peminjaman: new Peminjaman(id, peminjam, aset, mulai, selesai)
    activate Peminjaman
    Peminjaman-->>Klien: baru (status = DIAJUKAN)
    deactivate Peminjaman

    Klien->>Jadwal: ajukan(baru)
    activate Jadwal

    Jadwal->>Peminjaman: getAset()
    activate Peminjaman
    Peminjaman-->>Jadwal: aset
    deactivate Peminjaman

    Jadwal->>Aset: bisaDipinjam()
    activate Aset
    Aset-->>Jadwal: true
    deactivate Aset

    Jadwal->>Jadwal: bentrok(baru)
    activate Jadwal
    Note over Jadwal, Daftar: Memeriksa tumpang tindih rentang waktu<br/>peminjaman pada aset yang sama
    Jadwal-->>Jadwal: boolean statusBentrok
    deactivate Jadwal

    alt Skenario Bentrok (statusBentrok == true)
        Note over Jadwal, Klien: Jadwal tumpang tindih pada aset yang sama
        Jadwal-->>Klien: throw PeminjamanBentrokException("Bentrok jadwal peminjaman")
        Klien-->>Pengguna: Notifikasi: Pengajuan ditolak (jadwal bentrok)
    else Skenario Sukses (statusBentrok == false)
        Note over Jadwal, Daftar: Jadwal aman dan tidak tumpang tindih
        Jadwal->>Daftar: add(baru)
        activate Daftar
        Daftar-->>Jadwal: void
        deactivate Daftar

        Jadwal-->>Klien: return (sukses terdaftar)
        deactivate Jadwal

        Klien-->>Pengguna: Notifikasi: Pengajuan berhasil diajukan (status DIAJUKAN)
    end
    deactivate Klien
```

## Deskripsi Skenario:

1. **Inisialisasi**: Pengguna menginisiasi permohonan peminjaman melalui sistem/klien. Dibuat instansi `baru: Peminjaman` dengan status awal `DIAJUKAN`.
2. **Validasi Kesiapan Aset**: Objek `JadwalPeminjaman` memeriksa apakah aset dapat dipinjam via `aset.bisaDipinjam()`.
3. **Pengecekan Bentrok Waktu**:
   - Jadwal memanggil metode internal `bentrok(baru)` untuk memeriksa daftar peminjaman aset yang sama pada rentang waktu `[mulai, selesai]`.
4. **Fragmen `alt`**:
   - **Cabang Bentrok**: Jika terdapat peminjaman aset yang sama dalam rentang waktu yang bertabrakan, sistem melempar exception `PeminjamanBentrokException`.
   - **Cabang Sukses**: Jika tidak ada jadwal yang berbenturan, objek peminjaman disimpan ke dalam koleksi internal `daftar` dan sistem mengonfirmasi keberhasilan pengajuan kepada peminjam.
