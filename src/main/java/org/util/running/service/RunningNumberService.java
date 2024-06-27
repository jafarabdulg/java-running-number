package org.util.running.service;

import org.util.running.datasource.PostgreSQLJDBC;
import org.util.running.model.RunningNumber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RunningNumberService {
    public RunningNumber getRunningNumberById(int id) throws Exception {
        RunningNumber runningNumber = null;

        //language=PostgreSQL
        String SQL = " SELECT id, length, reset_period, current_number, prefix_type, prefix_content, cre_dt, upd_dt " +
                     " FROM running_number WHERE id = ?";

        try (Connection conn = PostgreSQLJDBC.getDataSource();
             PreparedStatement statement = conn.prepareStatement(SQL)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int i = 1;
                runningNumber = new RunningNumber(
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getInt(i++),
                        rs.getString(i++),
                        LocalDate.parse(rs.getString(i++)),
                        rs.getString(i) == null ? null : LocalDate.parse(rs.getString(i))
                );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving RunningNumber", e);
        }

        return runningNumber;
    }

    public void updateCurrentRunningNumber(int id, int currentNumber) throws Exception {
        //language=PostgreSQL
        String SQL = "UPDATE running_number SET current_number = ?, upd_dt = now() WHERE id = ?";

        try (Connection conn = PostgreSQLJDBC.getDataSource();
             PreparedStatement statement = conn.prepareStatement(SQL)) {

            statement.setInt(1, currentNumber);
            statement.setInt(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error update currentRunningNumber", e);
        }
    }
}
