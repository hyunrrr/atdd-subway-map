package wooteco.subway.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import wooteco.subway.domain.Line;
import wooteco.subway.domain.entity.SectionEntity;

@JdbcTest
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class SectionDaoTest {

    @Autowired
    private DataSource dataSource;
    private SectionDao sectionDao;
    private LineDao lineDao;
    private Long lineId;

    @BeforeEach
    void setUp() {
        lineDao = new LineDao(dataSource);
        sectionDao = new SectionDao(dataSource);

        Line line = new Line.Builder("2호선", "초록색")
                .build();
        lineId = lineDao.save(line).getId();
    }

    @Test
    @DisplayName("section을 저장하고 id가 추가된 section을 반환한다.")
    void saveSection() {
        SectionEntity section = new SectionEntity.Builder(lineId, 1L, 2L, 3)
                .build();

        SectionEntity persistSection = sectionDao.save(section);

        assertThat(persistSection.getId()).isNotNull();
        assertThat(persistSection.getUpStationId()).isEqualTo(1L);
        assertThat(persistSection.getDownStationId()).isEqualTo(2L);
        assertThat(persistSection.getDistance()).isEqualTo(3);
    }

    @Test
    @DisplayName("lineId에 해당되는 모든 section을 반환한다.")
    void findByLineId() {
        SectionEntity section1 = new SectionEntity.Builder(lineId, 1L, 2L, 3)
                .build();
        SectionEntity section2 = new SectionEntity.Builder(lineId, 2L, 3L, 3)
                .build();
        SectionEntity section3 = new SectionEntity.Builder(lineId, 3L, 4L, 3)
                .build();
        sectionDao.save(section1);
        sectionDao.save(section2);
        sectionDao.save(section3);

        List<SectionEntity> sections = sectionDao.findByLineId(lineId);

        assertThat(sections.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("section을 삭제한다.")
    void delete() {
        SectionEntity section1 = new SectionEntity.Builder(lineId, 1L, 2L, 3)
                .id(1L)
                .build();
        SectionEntity section2 = new SectionEntity.Builder(lineId, 2L, 3L, 3)
                .id(2L)
                .build();
        SectionEntity section3 = new SectionEntity.Builder(lineId, 3L, 4L, 3)
                .id(3L)
                .build();
        sectionDao.save(section1);
        sectionDao.save(section2);
        sectionDao.save(section3);

        sectionDao.delete(section1);
        List<SectionEntity> sections = sectionDao.findByLineId(lineId);
        assertThat(sections.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("section들을 삭제한다.")
    void deleteAll() {
        SectionEntity section1 = new SectionEntity.Builder(lineId, 1L, 2L, 3)
                .id(1L)
                .build();
        SectionEntity section2 = new SectionEntity.Builder(lineId, 2L, 3L, 3)
                .id(2L)
                .build();
        SectionEntity section3 = new SectionEntity.Builder(lineId, 3L, 4L, 3)
                .id(3L)
                .build();
        sectionDao.save(section1);
        sectionDao.save(section2);
        sectionDao.save(section3);

        sectionDao.deleteALl(List.of(section1, section2));
        List<SectionEntity> sections = sectionDao.findByLineId(lineId);
        assertThat(sections.size()).isEqualTo(1);
    }
}
