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

        Reservasi(int idReservasi, String namaTamu, Kamar kamar) {
            this.idReservasi = idReservasi;
            this.namaTamu = namaTamu;
            this.kamar = kamar;
            this.checkedIn = false;
            this.pembayaranSelesai = false;
        }
    }

    // Daftar untuk menyimpan kamar dan reservasi
    static List<Kamar> kamarList = new ArrayList<>();
    static List<Reservasi> reservasiList = new ArrayList<>();
    static int nextKamarId = 1;
    static int nextReservasiId = 1;

    // Fungsi clear screen
    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    // Tambah kamar baru
    public static void tambahKamar(String tipeKamar, double harga) {
        Kamar kamar = new Kamar(nextKamarId++, tipeKamar, harga);
        kamarList.add(kamar);
        System.out.println("Kamar ditambahkan: " + tipeKamar + " (ID: " + kamar.id + ")");
    }

    // Hapus kamar berdasarkan ID
    public static void hapusKamar(int idKamar) {
        for (Kamar kamar : kamarList) {
            if (kamar.id == idKamar) {
                if (!kamar.dipesan) {
                    kamarList.remove(kamar);
                    System.out.println("Kamar dengan ID " + idKamar + " berhasil dihapus.");
                } else {
                    System.out.println("Kamar tidak dapat dihapus karena sedang dipesan.");
                }
                System.out.println("\nTekan Enter untuk melanjutkan...");
                new Scanner(System.in).nextLine();  // Menunggu pengguna menekan Enter
                return;
            }
        }
        System.out.println("ID kamar tidak ditemukan.");
        System.out.println("\nTekan Enter untuk melanjutkan...");
        new Scanner(System.in).nextLine();  // Menunggu pengguna menekan Enter
    }

    // Tampilkan kamar yang tersedia
    public static void tampilkanKamarTersedia() {
        System.out.println("\nKamar Tersedia:");
        for (Kamar kamar : kamarList) {
            if (!kamar.dipesan) {
                System.out.println("ID Kamar: " + kamar.id + ", Tipe: " + kamar.tipeKamar + ", Harga: " + kamar.harga);
            }
        }
        System.out.println("\nTekan Enter untuk melanjutkan...");
        new Scanner(System.in).nextLine();  // Menunggu pengguna menekan Enter
    }

    // Pemesanan kamar
    public static void bookingKamar(String namaTamu, int idKamar) {
        for (Kamar kamar : kamarList) {
            if (kamar.id == idKamar && !kamar.dipesan) {
                kamar.dipesan = true;
                Reservasi reservasi = new Reservasi(nextReservasiId++, namaTamu, kamar);
                reservasiList.add(reservasi);
                System.out.println("Kamar berhasil dipesan untuk " + namaTamu + " (ID Reservasi: " + reservasi.idReservasi + ")");
                System.out.println("\nTekan Enter untuk melanjutkan...");
                new Scanner(System.in).nextLine();  // Menunggu pengguna menekan Enter
                return;
            }
        }
        System.out.println("Kamar tidak tersedia atau tidak ada.");
        System.out.println("\nTekan Enter untuk melanjutkan...");
        new Scanner(System.in).nextLine();  // Menunggu pengguna menekan Enter
    }

}