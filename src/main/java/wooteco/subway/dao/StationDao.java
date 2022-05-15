package wooteco.subway.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import wooteco.subway.domain.Station;

@Repository
public class StationDao {

    private static final RowMapper<Station> STATION_MAPPER = (resultSet, rowNum) -> new Station.Builder(
            resultSet.getString("name"))
            .id(resultSet.getLong("id"))
            .build();

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public StationDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("STATION")
                .usingGeneratedKeyColumns("id");
    }

    public Station save(Station station) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(station);
        Long id = simpleInsert.executeAndReturnKey(parameters).longValue();
        return station.addId(id);
    }

    public List<Station> findAll() {
        String sql = "SELECT * FROM STATION";
        return jdbcTemplate.query(sql, STATION_MAPPER);
    }
    
    public void deleteById(Long id) {
        String sql = "DELETE FROM STATION WHERE id = :id";
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, parameters);
    }

    public Station findById(Long id) {
        String sql = "SELECT * FROM STATION WHERE id = :id";
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, parameters, STATION_MAPPER);
    }
}
