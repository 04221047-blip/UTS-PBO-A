// 1. CLASS PAKET CUCIAN
class PaketCucian(val idPesanan: String, val namaPelanggan: String) {
    
    // Kriteria 1: Data Hiding (Private Set)
    // Variabel ini tidak bisa diubah dengan operator '=' dari luar class.
    var statusPembayaran: String = "Belum Lunas"
        private set 

    // Kriteria 2: Custom Setter & Validasi 
    fun lunasiPembayaran() {
        if (statusPembayaran == "Lunas") {
            println("⚠️ INFO: Pesanan $idPesanan sudah lunas sebelumnya.")
        } else {
            statusPembayaran = "Lunas"
            println("💸 BERHASIL: Pembayaran untuk $idPesanan diterima. Status menjadi LUNAS.")
        }
    }
}
    
// 2. CLASS KASIR (Mengelola Mesin)
class Kasir(val namaKasir: String, private val kapasitasMaksimal: Int) {
    
    // Kriteria 1: Data Hiding (Private Set)
    // Kapasitas terpakai tidak boleh diubah manual agar mesin tidak 'overload' gaib.
    var kapasitasTerpakai: Int = 0
        private set 

    // Kriteria 2: Custom Setter dengan Validasi (Aturan Bisnis 1: Kapasitas Penuh)
    fun prosesCucianBaru(paket: PaketCucian) {
        println("\n--> Kasir $namaKasir memproses pesanan baru: ${paket.idPesanan}...")
        
        if (kapasitasTerpakai >= kapasitasMaksimal) {
            println("⛔ GAGAL: Mesin Laundry Penuh! (Terpakai: $kapasitasTerpakai/$kapasitasMaksimal). Cucian ditolak sementara.")
        } else {
            kapasitasTerpakai++
            println("✅ SUKSES: Cucian ${paket.idPesanan} masuk mesin. (Kapasitas saat ini: $kapasitasTerpakai/$kapasitasMaksimal).")
        }
    }

    // Kriteria 2: Custom Setter dengan Validasi (Aturan Bisnis 2: Status Lunas)
    fun serahkanCucian(paket: PaketCucian) {
        println("\n--> Permintaan pengambilan cucian: ${paket.idPesanan}...")
        
        if (paket.statusPembayaran != "Lunas") {
            println("⛔ GAGAL: Cucian ditahan! Status pembayaran masih '${paket.statusPembayaran}'.")
        } else {
            kapasitasTerpakai--
            println("✅ SUKSES: Cucian ${paket.idPesanan} diserahkan ke ${paket.namaPelanggan}. (Mesin kosong: $kapasitasTerpakai/$kapasitasMaksimal).")
        }
    }
}

// 3. KRITERIA 3: SIMULASI DI MAIN FUNCTION
fun main() {
    println("=== SIMULASI SISTEM LAUNDRY MAHASISWA ===")
    
    // Inisialisasi: Mesin laundry hanya muat 1 cucian (untuk memicu error kapasitas)
    val kasirBudi = Kasir("Budi", 1)
    
    val cucianAndi = PaketCucian("LD-001", "Andi")
    val cucianSiti = PaketCucian("LD-002", "Siti")

    // --- Skenario 1: SUKSES (Cucian Masuk) ---
    kasirBudi.prosesCucianBaru(cucianAndi)

    // --- Skenario 2: GAGAL (Aturan Bisnis 1 - Kapasitas Penuh) ---
    // Mesin sudah diisi Andi (1/1), Siti mencoba masuk
    kasirBudi.prosesCucianBaru(cucianSiti)

    // --- Skenario 3: GAGAL (Aturan Bisnis 2 - Belum Lunas) ---
    // Andi nekat mengambil cucian tanpa bayar
    kasirBudi.serahkanCucian(cucianAndi)

    // --- Skenario 4: SUKSES (Bayar & Ambil) ---
    println("\n[Andi melakukan pembayaran di aplikasi...]")
    cucianAndi.lunasiPembayaran()
    kasirBudi.serahkanCucian(cucianAndi) // Sekarang berhasil, kapasitas mesin berkurang

    // --- Skenario 5: SUKSES (Kapasitas Kembali Tersedia) ---
    // Karena cucian Andi sudah diambil, mesin kosong, cucian Siti bisa diproses
    kasirBudi.prosesCucianBaru(cucianSiti)
}