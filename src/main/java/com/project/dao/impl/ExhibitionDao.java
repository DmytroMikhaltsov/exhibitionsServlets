package com.project.dao.impl;

import com.project.dao.EntityDao;
import com.project.entities.Exhibition;
import com.project.persistance.DataSourceFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionDao implements EntityDao<Exhibition> {
    private static final Logger LOG = Logger.getLogger(ExhibitionDao.class);

    @Override
    public List<Exhibition> getAll() {
        List<Exhibition> result = new ArrayList<>();
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM exhibitions.exhibitions ");) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idTheme = resultSet.getInt("id_theme");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                Time startTime = resultSet.getTime("start_time");
                Time endTime = resultSet.getTime("end_time");
                int cost = resultSet.getInt("ticket_cost");
                String status = resultSet.getString("status");

                Exhibition exhibitionData = new Exhibition(id, idTheme, startDate, endDate, startTime, endTime, cost, status);
                LOG.info(exhibitionData);
                result.add(exhibitionData);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Exhibition getById(int inputId) {
        Exhibition exhibition = null;
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM exhibitions where id = ?");) {
            preparedStatement.setInt(1, inputId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idTheme = resultSet.getInt("id_theme");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                Time startTime = resultSet.getTime("start_time");
                Time endTime = resultSet.getTime("end_time");
                int cost = resultSet.getInt("ticket_cost");
                String status = resultSet.getString("status");
                exhibition = new Exhibition(id, idTheme, startDate, endDate, startTime, endTime, cost, status);
            }
        } catch (SQLException throwables) {
            LOG.error(throwables.getMessage(), throwables);
        }
        return exhibition;
    }

    @Override
    public int create(Exhibition entity) {
        int result = 0;
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO exhibitions (id_theme, start_date, end_date, start_time, end_time, ticket_cost, status) VALUES (?, ?, ?, ?, ?, ?, ? )");) {
            preparedStatement.setInt(1, entity.getIdTheme());
            preparedStatement.setDate(2, entity.getStartDate());
            preparedStatement.setDate(3, entity.getEndDate());
            preparedStatement.setTime(4, entity.getStartTime());
            preparedStatement.setTime(5, entity.getEndTime());
            preparedStatement.setInt(6, entity.getCost());
            preparedStatement.setString(7, entity.getStatus());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;

    }

    @Override
    public int update(Exhibition entity) {

        int result = 0;
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE exhibitions SET(id_theme, start_date, end_date, start_time, end_time, ticket_cost, status) VALUES (?, ?, ?, ?, ?, ?, ? )");) {

            preparedStatement.setInt(1, entity.getIdTheme());
            preparedStatement.setDate(2, entity.getStartDate());
            preparedStatement.setDate(3, entity.getEndDate());
            preparedStatement.setTime(4, entity.getStartTime());
            preparedStatement.setTime(5, entity.getEndTime());
            preparedStatement.setInt(6, entity.getCost());
            preparedStatement.setString(7, entity.getStatus());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }
}