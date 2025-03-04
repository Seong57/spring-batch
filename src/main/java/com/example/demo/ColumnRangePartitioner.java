package com.example.demo;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ColumnRangePartitioner implements Partitioner {

    private JdbcTemplate jdbcTemplate;

    public ColumnRangePartitioner(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Integer min = jdbcTemplate.queryForObject("SELECT MIN(id) FROM User", Integer.class); // 1
        Integer max = jdbcTemplate.queryForObject("SELECT MAX(id) FROM User", Integer.class); // 20
        int targetSize = (max - min) / gridSize + 1; // 20

        final Map<String, ExecutionContext> result = new HashMap<>();
        int number = 0;
        int start = min;    // 1
        int end = start + targetSize - 1; // 20

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) {
                end = max;
            }

            value.putInt("minValue", start);
            value.putInt("maxValue", end);

            start += targetSize;
            end += targetSize;
            number++;

        }
        return result;
    }

    // partition 1, 20
    // partition 21, 40
    // partition 41, 60
    // partition 61, 80
    // partition 81, 100

}
