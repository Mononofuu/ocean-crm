package com.becomejavasenior.mapper;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Deal;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.interfacedao.CurrencyDAO;
import com.becomejavasenior.interfacedao.DealStatusDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DealRowMapper implements RowMapper<Deal> {
    private final static Logger LOGGER = LogManager.getLogger(DealRowMapper.class);
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private DealStatusDAO dealStatus;
    @Autowired
    private CurrencyDAO currencyDAO;


    public Deal mapRow(ResultSet resultSet, int i) throws SQLException {
        Deal deal = new Deal();
        deal.setId(resultSet.getInt("id"));
        deal.setName(resultSet.getString("name"));
        try {
            deal.setUser(userDAO.read(resultSet.getInt("content_owner_id")));
            deal.setMainContact(contactDAO.read(resultSet.getInt("contact_main_id")));
            deal.setResponsible(userDAO.read(resultSet.getInt("responsible_id")));
            deal.setStatus(dealStatus.read(resultSet.getInt("status_id")));
            deal.setCurrency(currencyDAO.read(resultSet.getInt("currency_id")));

        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        deal.setRemoved(resultSet.getBoolean("removed"));
        deal.setBudget(resultSet.getInt("budget"));
        deal.setDateWhenDealClose(resultSet.getTimestamp("data_close"));
        deal.setDateCreated(resultSet.getTimestamp("created_date"));
        return deal;
    }
}
