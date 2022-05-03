package wooteco.subway.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import wooteco.subway.domain.Line;

class LineDaoTest {

    @AfterEach
    void cleanUp() {
        LineDao.findAll().clear();
    }

    @DisplayName("노선 저장 기능을 테스트한다.")
    @Test
    void saveLine() {
        Line line = new Line("2호선", "초록색");

        Line persistLine = LineDao.save(line);

        assertThat(persistLine.getId()).isNotNull();
        assertThat(persistLine.getName()).isEqualTo("2호선");
        assertThat(persistLine.getColor()).isEqualTo("초록색");
    }

    @DisplayName("중복된 이름의 노선을 저장할 경우 예외가 발생한다.")
    @Test
    void saveDuplicateNameLine() {
        Line line = new Line("2호선", "초록색");
        LineDao.save(line);

        assertThatThrownBy(() -> LineDao.save(line))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @DisplayName("전체 노선의 개수가 맞는지 확인한다.")
    @Test
    void find_All_Line() {
        Line lineTwo = new Line("2호선", "초록색");
        Line lineEight = new Line("8호선", "분홍색");
        LineDao.save(lineTwo);
        LineDao.save(lineEight);

        assertThat(LineDao.findAll().size()).isEqualTo(2);
    }

    @DisplayName("특정 id를 가지는 노선을 조회한다.")
    @Test
    void findById() {
        Line line = new Line("2호선", "초록색");
        Long id = LineDao.save(line).getId();

        Line actual = LineDao.findById(id);
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo("2호선");
        assertThat(actual.getColor()).isEqualTo("초록색");
    }

    @DisplayName("특정 id를 가지는 라인의 이름과 색을 변경한다.")
    @Test
    void updateLineById() {
        Line line = new Line("2호선", "초록색");
        Long id = LineDao.save(line).getId();

        Line updateLine = new Line("8호선", "분홍색");
        LineDao.updateById(id, updateLine);

        Line actual = LineDao.findAll().get(0);
        assertThat(actual.getName()).isEqualTo("8호선");
        assertThat(actual.getColor()).isEqualTo("분홍색");
    }

    @DisplayName("특정 id를 가지는 노선을 삭제한다.")
    @Test
    void deleteById() {
        Line line = new Line("2호선", "초록색");
        Long id = LineDao.save(line).getId();

        LineDao.deleteById(id);

        assertThat(LineDao.findAll().size()).isEqualTo(0);
    }
}
