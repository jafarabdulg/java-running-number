package org.util.running.controller;

import org.util.running.model.RunningNumber;
import org.util.running.service.RunningNumberService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class RunningNumberController {
    private final RunningNumberService service;

    public RunningNumberController(RunningNumberService service) {
        this.service = service;
    }

    public String generateRunningNumber(int runningNumberId) {
        String result = null;
        try {
            RunningNumber runningNumber = service.getRunningNumberById(runningNumberId);

            String prefix = this.getPrefix(runningNumber);
            HashMap<String, Object> mapCurrNumber = this.getCurrentNumber(runningNumber);

            service.updateCurrentRunningNumber(runningNumberId, (Integer) mapCurrNumber.get("int"));

            System.out.println(mapCurrNumber.get("string"));
            result = prefix + mapCurrNumber.get("string").toString();
        } catch (Exception e) {
            System.out.println("Exception at generateRunningNumber");
        }

        return result;
    }

    private String getPrefix(RunningNumber runningNumber) throws RuntimeException {
        switch (runningNumber.prefixType()) {
            case 1 -> {
                return runningNumber.prefixContent();
            }
            case 2 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(runningNumber.prefixContent());
                return formatter.format(LocalDate.now());
            }
            default -> {
                throw  new RuntimeException("Not found prefixType");
            }
        }
    }

    private HashMap<String, Object> getCurrentNumber(RunningNumber runningNumber) throws Exception {
        var map = new HashMap<String, Object>();

        if (isResetCurrentNumber(runningNumber)) {
            int currentNumber = 1;
            String result = String.format("%0" + runningNumber.length() + "d", currentNumber);

            map.put("string", result);
            map.put("int", currentNumber);
            return map;
        } else {
            int currentNumber = runningNumber.currentNumber() + 1;
            String result = String.format("%0" + runningNumber.length() + "d", currentNumber);

            map.put("string", result);
            map.put("int", currentNumber);
            return map;
        }
    }

    private boolean isResetCurrentNumber(RunningNumber runningNumber) throws RuntimeException {
        if (runningNumber.updDt() == null) return false;

        LocalDate today = LocalDate.now();
        LocalDate updDt = runningNumber.updDt();

        switch (runningNumber.resetPeriod()) {
            case 0 -> {
                return false;
            }
            case 1 -> {
                return !today.isEqual(updDt);
            }
            case 2 -> {
                return !(today.getMonthValue() == updDt.getMonthValue() && today.getYear() == updDt.getYear());
            }
            case 3 -> {
                return !(today.getYear() == updDt.getYear());
            }
            default -> throw new RuntimeException("Error at isResetCurrentNumber");
        }
    }
}
