import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManajemenHotel {

    // Enum untuk metode pembayaran
    enum MetodePembayaran {
        TRANSFER_BANK, KARTU_KREDIT, E_WALLET
    }

    // Class Kamar untuk menyimpan informasi kamar
    static class Kamar {
        int id;
        String tipeKamar;
        double harga;
        boolean dipesan;

        Kamar(int id, String tipeKamar, double harga) {
            this.id = id;
            this.tipeKamar = tipeKamar;
            this.harga = harga;
            this.dipesan = false;
        }
    }

    // Class Reservasi untuk menyimpan informasi reservasi
    static class Reservasi {
        int idReservasi;
        String namaTamu;
        Kamar kamar;
        boolean checkedIn;
        boolean pembayaranSelesai;
        MetodePembayaran metodePembayaran;

        Reservasi(int idReservasi, String namaTamu, Kamar kamar, MetodePembayaran metodePembayaran) {
            this.idReservasi = idReservasi;
            this.namaTamu = namaTamu;
            this.kamar = kamar;
            this.checkedIn = false;
            this.pembayaranSelesai = false;
            this.metodePembayaran = metodePembayaran;
        }
    }

    // Daftar untuk menyimpan kamar dan reservasi
    static List<Kamar> kamarList = new ArrayList<>();
    static List<Reservasi> reservasiList = new ArrayList<>();
    static int nextKamarId = 1;
    static int nextReservasiId = 1;

    // Fungsi untuk menambahkan kamar
    public static void tambahKamar(String tipeKamar, double harga) {
        Kamar kamar = new Kamar(nextKamarId++, tipeKamar, harga);
        kamarList.add(kamar);
        System.out.println("Kamar ditambahkan: " + tipeKamar + " (ID: " + kamar.id + ")");
    }

    // Fungsi untuk menghapus kamar
    public static void hapusKamar(int idKamar) {
        for (Kamar kamar : kamarList) {
            if (kamar.id == idKamar) {
                if (!kamar.dipesan) {
                    kamarList.remove(kamar);
                    System.out.println("Kamar dengan ID " + idKamar + " berhasil dihapus.");
                } else {
                    System.out.println("Kamar tidak dapat dihapus karena sedang dipesan.");
                }
                return;
            }
        }
        System.out.println("ID kamar tidak ditemukan.");
    }

    // Fungsi untuk menampilkan kamar yang tersedia
    public static void tampilkanKamarTersedia() {
        System.out.println("\nKamar Tersedia:");
        for (Kamar kamar : kamarList) {
            if (!kamar.dipesan) {
                System.out.println("ID Kamar: " + kamar.id + ", Tipe: " + kamar.tipeKamar + ", Harga: " + kamar.harga);
            }
        }
    }

    // Fungsi untuk memesan kamar dengan metode pembayaran
    public static void bookingKamar(String namaTamu, int idKamar) {
        Scanner scanner = new Scanner(System.in);

        for (Kamar kamar : kamarList) {
            if (kamar.id == idKamar && !kamar.dipesan) {
                // Memilih metode pembayaran
                System.out.println("Pilih metode pembayaran: ");
                System.out.println("1. Transfer Bank");
                System.out.println("2. Kartu Kredit");
                System.out.println("3. E-Wallet");
                int pilihanPembayaran = scanner.nextInt();

                MetodePembayaran metodePembayaran;
                switch (pilihanPembayaran) {
                    case 1:
                        metodePembayaran = MetodePembayaran.TRANSFER_BANK;
                        break;
                    case 2:
                        metodePembayaran = MetodePembayaran.KARTU_KREDIT;
                        break;
                    case 3:
                        metodePembayaran = MetodePembayaran.E_WALLET;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Menggunakan Transfer Bank sebagai default.");
                        metodePembayaran = MetodePembayaran.TRANSFER_BANK;
                        break;
                }

                kamar.dipesan = true;
                Reservasi reservasi = new Reservasi(nextReservasiId++, namaTamu, kamar, metodePembayaran);
                reservasiList.add(reservasi);

                System.out.println("Kamar berhasil dipesan untuk " + namaTamu + " (ID Reservasi: " + reservasi.idReservasi + ")");
                System.out.println("Metode Pembayaran: " + metodePembayaran);
                return;
            }
        }
        System.out.println("Kamar tidak tersedia atau tidak ada.");
    }

    // Fungsi untuk menampilkan semua reservasi
    public static void tampilkanReservasi() {
        System.out.println("\nDaftar Reservasi:");
        for (Reservasi reservasi : reservasiList) {
            System.out.println("ID Reservasi: " + reservasi.idReservasi + ", Nama Tamu: " + reservasi.namaTamu +
                    ", ID Kamar: " + reservasi.kamar.id + ", Metode Pembayaran: " + reservasi.metodePembayaran);
        }
    }

    // Fungsi utama untuk menjalankan sistem
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Manajemen Hotel ===");
            System.out.println("1. Tambah Kamar");
            System.out.println("2. Hapus Kamar");
            System.out.println("3. Tampilkan Kamar Tersedia");
            System.out.println("4. Booking Kamar");
            System.out.println("5. Tampilkan Reservasi");
            System.out.println("6. Keluar");
            System.out.print("Pilih opsi: ");
            int opsi = scanner.nextInt();

            switch (opsi) {
                case 1:
                    System.out.print("Masukkan tipe kamar: ");
                    String tipeKamar = scanner.next();
                    System.out.print("Masukkan harga kamar: ");
                    double harga = scanner.nextDouble();
                    tambahKamar(tipeKamar, harga);
                    break;
                case 2:
                    System.out.print("Masukkan ID kamar yang akan dihapus: ");
                    int idKamar = scanner.nextInt();
                    hapusKamar(idKamar);
                    break;
                case 3:
                    tampilkanKamarTersedia();
                    break;
                case 4:
                    System.out.print("Masukkan nama tamu: ");
                    String namaTamu = scanner.next();
                    System.out.print("Masukkan ID kamar yang ingin dipesan: ");
                    int idKamarPesan = scanner.nextInt();
                    bookingKamar(namaTamu, idKamarPesan);
                    break;
                case 5:
                    tampilkanReservasi();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
