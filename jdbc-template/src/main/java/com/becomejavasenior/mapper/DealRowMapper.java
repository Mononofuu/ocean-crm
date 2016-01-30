package com.becomejavasenior.mapper;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Deal;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.interfacedao.CurrencyDAO;
import com.becomejavasenior.interfacedao.DealStatusDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DealRowMapper implements RowMapper<Deal> {
    private final static Logger LOGGER = LogManager.getLogger(DealRowMapper.class);
    private UserDAO userDAO;
    private ContactDAO contactDAO;
    private DealStatusDAO dealStatus;
    private CurrencyDAO currencyDAO;

//    @Autowired
    public void setUp(UserDAO userDAO, ContactDAO contactDAO, DealStatusDAO dealStatus, CurrencyDAO currencyDAO) {
        this.userDAO = userDAO;
        this.contactDAO = contactDAO;
        this.dealStatus = dealStatus;
        this.currencyDAO = currencyDAO;
    }

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
