package hellojpa;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable // 값 타입을 정의하는 곳에 표시
public class Period {

    // Period
    LocalDate startDate;
    LocalDate endDate;

    public Period() {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
