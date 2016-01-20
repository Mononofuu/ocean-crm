package com.becomejavasenior.mapper;

import com.becomejavasenior.Deal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DealRowMapper implements RowMapper<Deal> {

    //заготовка
    public Deal mapRow(ResultSet resultSet, int i) throws SQLException {
        Deal deal = new Deal();
        deal.setId(resultSet.getInt("id"));
        return deal;
    }
}
