package one.bca.SpringBatch_AbsensiKaryawan.mapper;

import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiOutputCSV;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Arrays;

@Component
public class ReaderAbsensiOutputRowMapper implements RowMapper<AbsensiOutputCSV> {

    @Override
    public AbsensiOutputCSV mapRow(ResultSet rs, int rowNum) throws SQLException {
        AbsensiOutputCSV absensiOutputCSV = new AbsensiOutputCSV();

        // find "k.karyawan_id" karena sudah di update AS "k.karyawan_id"
        absensiOutputCSV.setKaryawanId(rs.getLong("k.karyawan_id"));
        absensiOutputCSV.setNamaDepan(rs.getString("nama_depan"));
        absensiOutputCSV.setNamaBelakang(rs.getString("nama_belakang"));
        absensiOutputCSV.setJumlahCutiTersisa(rs.getInt("jumlah_cuti_tersisa"));
        absensiOutputCSV.setJumlahCutiTelahDiambil(rs.getInt("jumlah_cuti_telah_diambil"));

        // Mendapatkan nilai total_durasi_lembur dari database (dalam bentuk String)
        String totalDurasiLemburString = rs.getString("total_durasi_lembur");

        // Mengonversi String ke Duration
        if (totalDurasiLemburString != null) {
            // Parsing interval ke dalam format yang sesuai
            String[] parts = totalDurasiLemburString.split(" ");
//            System.out.println("parts : " + Arrays.toString(parts));
            long days = 0;
            long hours = 0;
            long minutes = 0;
            long seconds = 0;
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("days")) {
                    days = Long.parseLong(parts[i - 1]);
                } else if (parts[i].contains(":")) {
                    String[] timeParts = parts[i].split(":");
                    hours = Long.parseLong(timeParts[0]);
                    minutes = Long.parseLong(timeParts[1]);
                    seconds = Long.parseLong(timeParts[2]);
                }
            }
            // Menghitung total durasi dalam detik
            long totalSeconds = (days * 24 * 3600) + (hours * 3600) + (minutes * 60) + seconds;
            // Membuat objek Duration
            Duration totalDurasiLembur = Duration.ofSeconds(totalSeconds);
            absensiOutputCSV.setTotalDurasiLembur(totalDurasiLembur);
        }
        absensiOutputCSV.setTotalKehadiran(rs.getInt("total_kehadiran"));
//        System.out.println("absensiOutputCSV : " + absensiOutputCSV.toString());
        return absensiOutputCSV;
    }
}
