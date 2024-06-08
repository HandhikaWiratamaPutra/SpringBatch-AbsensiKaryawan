package one.bca.SpringBatch_AbsensiKaryawan.configuration;

import one.bca.SpringBatch_AbsensiKaryawan.mapper.ReaderAbsensiOutputRowMapper;
import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiOutputCSV;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
public class BatchConfiguration {
    private final DataSourceTransactionManager transactionManager;
    private final JobRepository jobRepository;
    String lastMonthFirstDayString;
    public static String[] namesofExtractorAbsensiOutputCSV = new String[] { "karyawanId", "namaDepan", "namaBelakang", "jumlahCutiTersisa",
            "jumlahCutiTelahDiambil", "totalDurasiLembur", "totalKehadiran" };
//    public static String sqlToReadAbsensiOutputCSV = "SELECT k.karyawan_id, k.nama_depan, k.nama_belakang, k.jumlah_cuti_tersisa, k.jumlah_cuti_telah_diambil, ab.total_durasi_lembur, ab.total_kehadiran FROM KARYAWAN k, ABSENSI_BULANAN ab WHERE k.karyawan_id = ab.karyawan_id AND ab.bulan_dan_tahun_absensi = '2024-06-01' ORDER BY k.karyawan_id ASC LIMIT 3;";

    public BatchConfiguration(DataSourceTransactionManager transactionManager, JobRepository jobRepository, RowMapper<AbsensiOutputCSV> readerAbsensiOutputRowMapper) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Subtract one month from the current date and set the day to the first day of the month
        LocalDate lastMonthFirstDay = currentDate.minusMonths(1).withDayOfMonth(1);

        // Format the last month's first day as a string in the format "YYYY-MM-DD"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        lastMonthFirstDayString = lastMonthFirstDay.format(formatter);

        // Output the string representation of the last month's first day
        System.out.println("Last month's first day         : " + lastMonthFirstDayString);

        //dummy date ke 2024-06-01
        lastMonthFirstDayString = "2024-06-01";
        System.out.println("Last month's first day [dummy] : " + lastMonthFirstDayString);
    }

    public Job jobStart() throws Exception {
        return new JobBuilder("jobPengolahanAbsensiKaryawan", jobRepository)
                .start(stepCreateOutputCSVAbsensi())
                .build();
    }

    public Step stepCreateOutputCSVAbsensi() throws Exception {
        return new StepBuilder("stepCreateOutputCSVAbsensi", jobRepository)
                .<AbsensiOutputCSV, AbsensiOutputCSV>chunk(3, transactionManager)
                .reader(readBahanAbsensiOutputCSV())
                .writer(writeAbsensiOutputCSV())
                .build();
    }

//    @Bean
//    public RowMapper<AbsensiOutputCSV> readerAbsensiRowMapper() {
//        return new ReaderAbsensiOutputRowMapper();
//    }

    @Bean
//    @StepScope
    public JdbcPagingItemReader<AbsensiOutputCSV> readBahanAbsensiOutputCSV() throws Exception {
        return new JdbcPagingItemReaderBuilder<AbsensiOutputCSV>()
                .dataSource(transactionManager.getDataSource())
                .queryProvider(queryProviderOfReadBahanAbsensiOutputCSV())
                .rowMapper(new ReaderAbsensiOutputRowMapper())
                .pageSize(3) // Set page size as needed
                .name("pagingItemReader")
                .build();
    }

    public PagingQueryProvider queryProviderOfReadBahanAbsensiOutputCSV() throws Exception {
        System.out.println("querry provider started");
        PostgresPagingQueryProvider factory = new PostgresPagingQueryProvider();

        // SELECT k.karyawan_id AS "k.karyawan_id" -> di set alias sebagai "k.karyawan_id" karena karyawan_id dimiliki dua table.
        // jika hanya SELECT karyawan_id, Postgresql tidak bisa tahu jika hanya find dari karyawan_id saja karena ambiguous
        // jika hanya SELECT k.karyawan_id, ResultSet tidak bisa sort dari k.karywan_id karena tahu resultnya "karyawan_id"
        // maka dari itu di set alias ke "k.karyawan_id" agar tetap cocok dengan sintaks sql, dan ResultSet tahu kolomnya

        factory.setSelectClause("SELECT k.karyawan_id AS \"k.karyawan_id\", k.nama_depan, k.nama_belakang, k.jumlah_cuti_tersisa, k.jumlah_cuti_telah_diambil, ab.total_durasi_lembur, ab.total_kehadiran");
        factory.setFromClause("FROM karyawan k, absensi_bulanan ab");
        factory.setWhereClause("WHERE k.karyawan_id = ab.karyawan_id AND ab.bulan_dan_tahun_absensi = '" + lastMonthFirstDayString + "'");
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("k.karyawan_id", Order.ASCENDING);
        factory.setSortKeys(sortKeys);
        factory.init(Objects.requireNonNull(transactionManager.getDataSource()));
        System.out.println("querry provider ended");
        return factory;
    }

    public ItemWriter<AbsensiOutputCSV> writeAbsensiOutputCSV(){
        FlatFileItemWriter<AbsensiOutputCSV> itemWriter = new FlatFileItemWriter<>();

        itemWriter.setResource(new FileSystemResource("data/absensi_output.csv"));

        DelimitedLineAggregator<AbsensiOutputCSV> aggregator = new DelimitedLineAggregator<AbsensiOutputCSV>();
        aggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<AbsensiOutputCSV> fieldExtractor = new BeanWrapperFieldExtractor<AbsensiOutputCSV>();
        fieldExtractor.setNames(namesofExtractorAbsensiOutputCSV);
        aggregator.setFieldExtractor(fieldExtractor);

        itemWriter.setLineAggregator(aggregator);
        return itemWriter;
    }
}
