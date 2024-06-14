package one.bca.SpringBatch_AbsensiKaryawan.model;

public class AbsensiOutputCSV {
    Long karyawanId;
    String namaDepan;
    String namaBelakang;
    int jumlahCutiTersisa;
    int jumlahCutiTelahDiambil;
    int totalKehadiran;

    // Constructor, getters, setters, and toString method

    public Long getKaryawanId() {
        return karyawanId;
    }

    public void setKaryawanId(Long karyawanId) {
        this.karyawanId = karyawanId;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public int getJumlahCutiTersisa() {
        return jumlahCutiTersisa;
    }

    public void setJumlahCutiTersisa(int jumlahCutiTersisa) {
        this.jumlahCutiTersisa = jumlahCutiTersisa;
    }

    public int getJumlahCutiTelahDiambil() {
        return jumlahCutiTelahDiambil;
    }

    public void setJumlahCutiTelahDiambil(int jumlahCutiTelahDiambil) {
        this.jumlahCutiTelahDiambil = jumlahCutiTelahDiambil;
    }

    public int getTotalKehadiran() {
        return totalKehadiran;
    }

    public void setTotalKehadiran(int totalKehadiran) {
        this.totalKehadiran = totalKehadiran;
    }
}