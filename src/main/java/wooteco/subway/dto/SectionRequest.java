package wooteco.subway.dto;

import wooteco.subway.domain.entity.SectionEntity;

public class SectionRequest {

    private Long upStationId;
    private Long downStationId;
    private int distance;

    public SectionRequest() {
    }

    public SectionRequest(Long upStationId, Long downStationId, int distance) {
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public SectionRequest(LineRequest  lineRequest) {
        this.upStationId = lineRequest.getUpStationId();
        this.downStationId = lineRequest.getDownStationId();
        this.distance = lineRequest.getDistance();
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

    public SectionEntity toEntity(Long lineId) {
        return new SectionEntity.Builder(lineId, upStationId, downStationId, distance)
                .build();
    }
}
