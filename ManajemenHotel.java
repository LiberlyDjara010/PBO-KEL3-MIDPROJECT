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
        public static void checkIn (int idReservasi) {
        Scanner scanner = new Scanner(System.in);
        for (Reservasi reservasi : reservasiList) {
            if (reservasi.idReservasi == idReservasi) {
                if (!reservasi.pembayaranSelesai) {
                    System.out.println("Pembayaran belum selesai. Harap selesaikan pembayaran terlebih dahulu.");
                    System.out.println("\nTekan Enter untuk melanjutkan...");
                    scanner.nextLine();
                    return;
                }
                if (!reservasi.checkedIn) {
                    reservasi.checkedIn = true;
                    System.out.println("Check-in berhasil untuk " + reservasi.namaTamu);
                } else {
                    System.out.println("Tamu sudah check-in.");
                }
                System.out.println("\nTekan Enter untuk melanjutkan...");
                scanner.nextLine();
                return;
            }
        }
        System.out.println("ID reservasi tidak valid.");
        System.out.println("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }
    public static void checkOut(int idReservasi) {
        for (Reservasi reservasi : reservasiList) {
            if (reservasi.idReservasi == idReservasi) {
                reservasi.kamar.dipesan = false;
                reservasiList.remove(reservasi);
                System.out.println("Check-out berhasil untuk " + reservasi.namaTamu);
                System.out.println("\nTekan Enter untuk melanjutkan...");
                new Scanner(System.in).nextLine();
                return;
            }
        }
        System.out.println("ID reservasi tidak valid.");
        System.out.println("\nTekan Enter untuk melanjutkan...");
        new Scanner(System.in).nextLine();
    }

    public static void bayarReservasi(int idReservasi) {
        Scanner scanner = new Scanner(System.in);
        for (Reservasi reservasi : reservasiList) {
            if (reservasi.idReservasi == idReservasi) {
                if (!reservasi.pembayaranSelesai) {
                    System.out.println("Pilih metode pembayaran: ");
                    System.out.println("1. Transfer Bank");
                    System.out.println("2. Kartu Kredit");
                    System.out.println("3. E-Wallet");
                    int metode = scanner.nextInt();

                    switch (metode) {
                        case 1:
                            reservasi.metodePembayaran = MetodePembayaran.TRANSFER_BANK;
                            break;

                        case 2:
                            reservasi.metodePembayaran = MetodePembayaran.KARTU_KREDIT;
                            break;

                        case 3:
                            reservasi.metodePembayaran = MetodePembayaran.E_WALLET;
                            break;

                        default:
                            System.out.println("Pilihan metode pembayaran tidak valid.");
                            System.out.println("\nTekan Enter untuk melanjutkan...");
                            scanner.nextLine();
                            return;
                    }
                    reservasi.pembayaranSelesai = true;
                    System.out.println("Pembayaran berhasil untuk reservasi ID " + reservasi.idReservasi + "dengan metode: " + reservasi.metodePembayaran);
                } else {
                    System.out.println("Reservasi sudah di bayar.");
                }
                System.out.println("\nTekan Enter untuk melanjutkan...");
                scanner.nextLine();
                return;
            }
        }
        System.out.println("ID reservasi tidak valid.");
        System.out.println("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }

    public static void lihatReservasi() {
        System.out.println("\nSemua Reservasi: ");
        for (Reservasi reservasi : reservasiList) {
            String statusCheckIn = reservasi.checkedIn ? "Checked-In" : "Pending";
            String statusPembayaran = reservasi.pembayaranSelesai ? "Paid" : "Unpaid";
            String metodePembayaran = reservasi.pembayaranSelesai ? reservasi.metodePembayaran.toString() : "-";
            System.out.println("ID Reservasi: " + reservasi.idReservasi + ", Tamu: " + reservasi.namaTamu + ", ID Kamar: " + reservasi.kamar.id + ", Status: " + statusCheckIn + ", Pembayaran: " + statusPembayaran + ", Metode: " + metodePembayaran);
        }
    }

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pilihan;

        tambahKamar("Single", 100.000);
        tambahKamar("Double", 150.000);
        tambahKamar("Suite", 300.000);

        do {
            clearScreen();
            System.out.println("\n--- SISTEM MANAJEMEN HOTEL ---");
            System.out.println("1. Tambah Kamar");
            System.out.println("2. Tampilkan Kamar Tersedia");
            System.out.println("3. Hapus Kamar");
            System.out.println("4. Booking Kamar");
            System.out.println("5. Check-in");
            System.out.println("6. Check-out");
            System.out.println("7. Bayar Reservasi");
            System.out.println("8. Lihat Semua Reservasi");
            System.out.println("9. Keluar");
            System.out.print("Pilih opsi: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {

                case 1:
                    System.out.println("Masukkan tipe kamar: ");
                    String tipeKamar = scanner.nextLine();
                    System.out.print("Masukkan harga kamar: ");
                    double harga = scanner.nextDouble();
                    tambahKamar(tipeKamar, harga);
                    break;

                case 2:
                    tampilkanKamarTersedia();
                    break;

                case 3:
                    System.out.print("Masukkan ID kamar yang akan dihapus: ");
                    int idKamar = scanner.nextInt();
                    hapusKamar(idKamar);
                    break;

                case 4:
                    System.out.print("Masukkan nama tamu: ");
                    String namaTamu = scanner.nextLine();
                    System.out.print("Masukkan ID kamar untuk dipesan: ");
                    idKamar = scanner.nextInt();
                    bookingKamar(namaTamu, idKamar);
                    break;

                case 5:
                    System.out.print("Masukkan ID reservasi: ");
                    int idReservasi = scanner.nextInt();
                    checkIn(idReservasi);
                    break;

                case 6:
                    System.out.print("Masukkan ID reservasi: ");
                    idReservasi = scanner.nextInt();
                    checkOut(idReservasi);
                    break;

                case 7:
                    System.out.print("Masukkan ID reservasi: ");
                    idReservasi = scanner.nextInt();
                    bayarReservasi(idReservasi);
                    break;

                case 8:
                    lihatReservasi();
                    break;

                case 9:
                    System.out.println("Keluar dari sistem...");
                    break;

                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
                    break;
            }

            if (pilihan != 9 && pilihan != 1) {
                System.out.println("\nTekan Enter untuk melanjutkan...");
                scanner.nextLine();
            }
        } while (pilihan != 9);
        scanner.close();
    }
}
}
