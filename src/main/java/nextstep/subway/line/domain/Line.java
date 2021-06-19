package nextstep.subway.line.domain;

import nextstep.subway.BaseEntity;
import nextstep.subway.station.domain.Station;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String color;

    @Embedded
    private SectionList sections = new SectionList(new ArrayList<>());

    /**
     * 생성자
     */
    public Line() {
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Line(String name, String color, Station upStation, Station downStation, int distance) {
        this(name, color);
        sections.addToCreateLine(Section.create(this, upStation, downStation, distance));
    }

    //테스트용
    public Line(Long id, String name, String color, SectionList sections) {
        this(name, color);
        this.id = id;
        this.sections = sections;
    }

    /**
     * 생성 메소드
     */
    public static Line create(String name, String color, Station upStation, Station downStation, int distance) {
        verifyAvailable(name, color, upStation, downStation);
        Line line = new Line(name, color);
        line.sections.addToCreateLine(Section.create(line, upStation, downStation, distance));
        return line;
    }

    private static void verifyAvailable(String name, String color, Station upStation, Station downStation) {
        if (Strings.isBlank(name) || Strings.isBlank(color)) {
            throw new IllegalArgumentException("노선 정보가 충분하지 않습니다.");
        }

        if (Objects.isNull(upStation) || Objects.isNull(downStation)) {
            throw new IllegalArgumentException("역을 식별할 수 없습니다.");
        }
    }

    /**
     * 비즈니스 메소드
     */
    public void update(Line line) {
        this.name = line.getName();
        this.color = line.getColor();
    }

    public void addSection(Station upStation, Station downStation, int distance) {
        sections.addSection(this, upStation, downStation, distance);
    }

    public void removeSection(Station station) {
        sections.removeSection(this, station);
    }

    public List<Station> findStationsOrderUpToDown() {
        return sections.findStationsOrderUpToDown();
    }

    /**
     * 기타 메소드
     */
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
