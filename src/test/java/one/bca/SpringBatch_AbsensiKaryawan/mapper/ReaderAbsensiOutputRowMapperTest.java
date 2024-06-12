package one.bca.SpringBatch_AbsensiKaryawan.mapper;

import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiOutputCSV;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ReaderAbsensiOutputRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        //Arrange
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getLong("k.karyawan_id")).thenReturn(1L);
        Mockito.when(rs.getString("nama_depan")).thenReturn("John");
        Mockito.when(rs.getString("nama_belakang")).thenReturn("Doe");
        Mockito.when(rs.getInt("jumlah_cuti_tersisa")).thenReturn(10);
        Mockito.when(rs.getInt("jumlah_cuti_telah_diambil")).thenReturn(5);
        Mockito.when(rs.getString("total_durasi_lembur")).thenReturn("1 days 01:02:03");
        Mockito.when(rs.getInt("total_kehadiran")).thenReturn(20);

        RowMapper<AbsensiOutputCSV> rowMapper = new ReaderAbsensiOutputRowMapper();

        // Act
        AbsensiOutputCSV result = rowMapper.mapRow(rs, 1);

        // Assert
        Assertions.assertThat(result.getKaryawanId()).isEqualTo(1L);
        Assertions.assertThat(result.getNamaDepan()).isEqualTo("John");
        Assertions.assertThat(result.getNamaBelakang()).isEqualTo("Doe");
        Assertions.assertThat(result.getJumlahCutiTersisa()).isEqualTo(10);
        Assertions.assertThat(result.getJumlahCutiTelahDiambil()).isEqualTo(5);
        Assertions.assertThat(result.getTotalDurasiLembur().toDays()).isEqualTo(1);
        Assertions.assertThat(result.getTotalDurasiLembur().toHoursPart()).isEqualTo(1);
        Assertions.assertThat(result.getTotalDurasiLembur().toMinutesPart()).isEqualTo(2);
        Assertions.assertThat(result.getTotalDurasiLembur().toSecondsPart()).isEqualTo(3);
        Assertions.assertThat(result.getTotalKehadiran()).isEqualTo(20);
    }
}