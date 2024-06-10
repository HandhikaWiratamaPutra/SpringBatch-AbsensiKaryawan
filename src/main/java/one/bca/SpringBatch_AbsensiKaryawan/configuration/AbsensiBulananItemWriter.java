package one.bca.SpringBatch_AbsensiKaryawan.configuration;

import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiBulanan;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class AbsensiBulananItemWriter {

    @Bean
    public JdbcBatchItemWriter<AbsensiBulanan> writerAbsensiBulanan(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<AbsensiBulanan>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO absensi_bulanan (karyawan_id, bulan_dan_tahun_absensi, total_durasi_lembur, total_kehadiran) " +
                        "VALUES (:karyawanId, :bulanDanTahunAbsensi, :totalDurasiLembur, :totalKehadiran)")
                .dataSource(dataSource)
                .build();
    }
}
