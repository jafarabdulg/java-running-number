package org.util.running.model;

import java.time.LocalDate;

public record RunningNumber(int id, int length, int resetPeriod, int currentNumber, int prefixType,
                            String prefixContent, LocalDate creDt, LocalDate updDt) {

}
