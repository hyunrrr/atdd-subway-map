package wooteco.subway.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import wooteco.subway.dao.LineDao;
import wooteco.subway.dao.SectionDao;
import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.Line;
import wooteco.subway.domain.Section;
import wooteco.subway.domain.SectionBuffer;
import wooteco.subway.domain.Station;
import wooteco.subway.domain.Subway;
import wooteco.subway.domain.entity.SectionEntity;
import wooteco.subway.dto.LineRequest;
import wooteco.subway.dto.LineResponse;
import wooteco.subway.dto.SectionRequest;
import wooteco.subway.dto.StationRequest;
import wooteco.subway.dto.StationResponse;

@Service
public class SubwayService {

    private final Subway subway;
    private final LineDao lineDao;
    private final StationDao stationDao;
    private final SectionDao sectionDao;

    public SubwayService(Subway subway, LineDao lineDao, StationDao stationDao, SectionDao sectionDao) {
        this.subway = subway;
        this.lineDao = lineDao;
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
    }

    public StationResponse saveStation(StationRequest stationRequest) {
        Station station = stationRequest.toStation();
        subway.checkAbleToAdd(stationDao.findAll(), station);
        Station newStation = stationDao.save(station);
        return new StationResponse(newStation);
    }

    public List<StationResponse> getStations() {
        return stationDao.findAll()
                .stream()
                .map(StationResponse::new)
                .collect(Collectors.toList());
    }

    public void deleteStation(Long id) {
        stationDao.deleteById(id);
    }

    public LineResponse addLine(LineRequest lineRequest) {
        Line line = lineRequest.toLine();
        subway.checkAbleToAdd(lineDao.findAll(), line);
        Line newLine = lineDao.save(line);
        saveSection(newLine.getId(), lineRequest);
        return new LineResponse(newLine, StationResponse.toStationResponses(getStationsInLine(newLine)));
    }

    public void updateLine(Long id, LineRequest lineRequest) {
        Line line = lineRequest.toLine(id);
        subway.checkAbleToAdd(lineDao.findAll(), line);
        lineDao.update(line);
    }

    public List<LineResponse> getLines() {
        return lineDao.findAll()
                .stream()
                .map(it -> new LineResponse(it, StationResponse.toStationResponses(getStationsInLine(it))))
                .collect(Collectors.toList());
    }

    public LineResponse getLine(Long id) {
        Line line = lineDao.findById(id);
        return new LineResponse(line, StationResponse.toStationResponses(getStationsInLine(line)));
    }

    public void deleteLine(Long id) {
        lineDao.deleteById(id);
    }

    public void addSection(Long lineId, SectionRequest sectionRequest) {
        Line line = lineDao.findById(lineId);
        Station upStation = stationDao.findById(sectionRequest.getUpStationId());
        Station downStation = stationDao.findById(sectionRequest.getDownStationId());

        List<Section> presentSections = toSections(sectionDao.findByLineId(lineId));
        Section newSection = new Section.Builder(line, upStation, downStation, sectionRequest.getDistance())
                .build();
        SectionBuffer buffer = subway.addSection(presentSections, newSection);
        sectionDao.deleteALl(SectionEntity.Builder.buildMany(buffer.getDeleteBuffer()));
        sectionDao.saveAll(SectionEntity.Builder.buildMany(buffer.getAddBuffer()));
    }

    public void deleteSection(Long lineId, Long stationId) {
        List<Section> presentSections = toSections(sectionDao.findByLineId(lineId));
        Station deleteStation = stationDao.findById(stationId);
        SectionBuffer buffer = subway.deleteSection(presentSections, deleteStation);
        sectionDao.deleteALl(SectionEntity.Builder.buildMany(buffer.getDeleteBuffer()));
        sectionDao.saveAll(SectionEntity.Builder.buildMany(buffer.getAddBuffer()));
    }

    private void saveSection(Long lineId, LineRequest lineRequest) {
        SectionRequest sectionRequest = new SectionRequest(lineRequest);
        SectionEntity sectionEntity = sectionRequest.toEntity(lineId);
        sectionDao.save(sectionEntity);
    }

    private List<Station> getStationsInLine(Line line) {
        List<SectionEntity> sectionEntities = sectionDao.findByLineId(line.getId());
        return SectionEntity.extractStationIds(sectionEntities)
                .stream()
                .map(stationDao::findById)
                .collect(Collectors.toList());
    }

    private Section toSection(SectionEntity sectionEntity) {
        Line line = lineDao.findById(sectionEntity.getLineId());
        Station upStation = stationDao.findById(sectionEntity.getUpStationId());
        Station downStation = stationDao.findById(sectionEntity.getDownStationId());
        return new Section.Builder(line, upStation, downStation, sectionEntity.getDistance())
                .id(sectionEntity.getId())
                .build();
    }

    private List<Section> toSections(List<SectionEntity> sectionEntities) {
        return sectionEntities.stream()
                .map(this::toSection)
                .collect(Collectors.toList());
    }
}
