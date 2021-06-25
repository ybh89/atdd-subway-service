package nextstep.subway.path.domain;

import java.util.Objects;

public class ShortestDistance {
    private int distance;

    public ShortestDistance(int distance) {
        verifyAvailable(distance);
        this.distance = distance;
    }

    private void verifyAvailable(int distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("최단경로는 0 이상이어야 합니다.");
        }
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortestDistance that = (ShortestDistance) o;
        return getDistance() == that.getDistance();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDistance());
    }
}