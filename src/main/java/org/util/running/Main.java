package org.util.running;

import org.util.running.controller.RunningNumberController;
import org.util.running.service.RunningNumberService;

public class Main {
    public static void main(String[] args) {

        try {
            RunningNumberController controller = new RunningNumberController(new RunningNumberService());

            System.out.println(controller.generateRunningNumber(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}