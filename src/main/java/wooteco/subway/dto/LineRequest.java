package wooteco.subway.dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import wooteco.subway.domain.Line;

public class LineRequest {

    @Length(max = 255, message = "노선 이름은 255자 이하여야 합니다.")
    @NotBlank(message = "노선 이름은 공백일 수 없습니다.")
    private String name;
    private String color;
    private Long upStationId;
    private Long downStationId;
    private int distance;

    public LineRequest() {
    }

    public LineRequest(String name, String color, Long upStationId, Long downStationId, int distance) {
        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }

    public Line toLine() {
        return new Line.Builder(name, color)
                .build();
    }

    public Line toLine(Long id) {
        return new Line.Builder(name, color)
                .id(id)
                .build();
    }
}
