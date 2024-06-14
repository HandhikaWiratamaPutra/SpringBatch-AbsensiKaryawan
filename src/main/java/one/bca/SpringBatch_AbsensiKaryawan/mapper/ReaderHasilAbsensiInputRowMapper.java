package one.bca.SpringBatch_AbsensiKaryawan.mapper;

import one.bca.SpringBatch_AbsensiKaryawan.model.HasilAbsensiInput;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReaderHasilAbsensiInputRowMapper implements RowMapper<HasilAbsensiInput> {

    @Override
    public HasilAbsensiInput mapRow(ResultSet rs, int rowNum) throws SQLException {
        HasilAbsensiInput hasilAbsensiInput = new HasilAbsensiInput();

        // find "k.karyawan_id" karena sudah di update AS "k.karyawan_id"
        hasilAbsensiInput.setKaryawanId(rs.getLong("k.karyawan_id"));
        hasilAbsensiInput.setNamaDepan(rs.getString("nama_depan"));
        hasilAbsensiInput.setNamaBelakang(rs.getString("nama_belakang"));
        hasilAbsensiInput.setJumlahCutiTersisa(rs.getInt("jumlah_cuti_tersisa"));
        hasilAbsensiInput.setJumlahCutiTelahDiambil(rs.getInt("jumlah_cuti_telah_diambil"));
        hasilAbsensiInput.setTotalDurasiLembur(rs.getString("total_durasi_lembur"));
        hasilAbsensiInput.setTotalKehadiran(rs.getInt("total_kehadiran"));
//        System.out.println("absensiOutputCSV : " + absensiOutputCSV.toString());
        return hasilAbsensiInput;
    }
}
