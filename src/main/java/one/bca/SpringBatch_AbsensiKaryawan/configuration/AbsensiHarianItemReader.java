package one.bca.SpringBatch_AbsensiKaryawan.configuration;

import one.bca.SpringBatch_AbsensiKaryawan.mapper.AbsensiHarianRowMapper;
import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiHarian;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class AbsensiHarianItemReader {
    @Bean
    public JdbcPagingItemReader<AbsensiHarian> readerAbsensiHarian(DataSource dataSource) {
        return new JdbcPagingItemReaderBuilder<AbsensiHarian>()
                .dataSource(dataSource)
                .name("absensiHarianReader")
                .queryProvider(queryProvider())
                .rowMapper(new AbsensiHarianRowMapper())
                .pageSize(100)
                .build();
    }

    private PagingQueryProvider queryProvider() {
        PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
        queryProvider.setSelectClause("SELECT karyawan_id, tanggal_absen, durasi_lembur");
        queryProvider.setFromClause("FROM absensi_harian");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("karyawan_id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        return queryProvider;
    }
}
