package com.becomejavasenior.impl;

import com.becomejavasenior.Deal;
import com.becomejavasenior.GenericTemplateDAO;
import com.becomejavasenior.mapper.DealRowMapper;
import com.becomejavasenior.*;
import com.becomejavasenior.Currency;
import com.becomejavasenior.interfacedao.DealDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */

public class DealTemplateDAOImpl extends SubjectTemplateDAOImpl<Deal> implements DealDAO {
    @Autowired
    private RowMapper<Deal> dealRowMapper;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private DataSource dataSource;
    private final static Logger LOGGER = LogManager.getLogger(DealTemplateDAOImpl.class);
    private static final String DEAL_SELECT_TAG = " WHERE deal.id IN(SELECT subject_id FROM subject_tag " +
            "WHERE subject_tag.tag_id IN (SELECT id FROM tag WHERE name IN (";
    private static final String DEAL_SELECT_STATUS_ID = " where status_id=?";
    private static final String DEAL_SELECT_OPENED = " WHERE deal.data_close IS null";
    private static final String DEAL_SELECT_BY_USER = " WHERE responsible_id=?";
    private static final String DEAL_SELECT_WITHOUT_TASKS = " WHERE NOT deal.id IN (SELECT subject_id FROM task GROUP BY subject_id)";
    private static final String DEAL_SELECT_WITH_EXPIRED_TASKS = " WHERE deal.id IN (SELECT subject_id FROM task WHERE NOT (due_date IS null) AND due_date < ? GROUP BY subject_id)";
    private static final String DEAL_SELECT_SUCCESS = " WHERE deal.status_id=5";
    private static final String DEAL_SELECT_CLOSED_AND_NOT_IMPLEMENTED = " WHERE deal.status_id=6";
    private static final String DEAL_SELECT_DELETED = " WHERE deal.status_id=7";
    private static final String DEAL_SELECT_PERIOD_CREATED_DATE = " WHERE DATE(deal.created_date) BETWEEN ? AND ?";
    private static final String DEAL_SELECT_TASK_DUE_DATE_INTERVAL = "WHERE deal.id IN (SELECT subject_id FROM task WHERE DATE(due_date) BETWEEN ? AND ? GROUP BY subject_id)";
    private static final String READ_ALL_QUERY = "SELECT deal.id, status_id, currency_id, budget, contact_main_id, company_id, data_close, deal.created_date, name, responsible_id, content_owner_id, removed FROM deal JOIN subject ON subject.id=deal.id ";
    private static final String INSERT_QUERY = "INSERT INTO deal(id, status_id, currency_id, budget, contact_main_id, company_id, data_close, created_date, responsible_id) " +
            "VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE deal SET status_id = ?, currency_id = ?, budget = ?, contact_main_id = ?," +
            "company_id = ?, data_close = ?, created_date = ?, responsible_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM deal WHERE id = ?";

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public List<Deal> readAllLite() throws DataBaseException {
        return null;
    }

    public Deal create(final Deal deal) throws DataBaseException {
        KeyHolder holder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
                try {
                    statement.setInt(1, createSubject(deal));
                } catch (DataBaseException e) {
                    LOGGER.error(e);
                }

                Optional<DealStatus> dealStatus = Optional.ofNullable(deal.getStatus());
                if (dealStatus.isPresent()){
                    statement.setInt(2, dealStatus.get().getId());
                }else {
                    statement.setNull(2, Types.INTEGER);
                }

                Optional<Currency> dealCurrency = Optional.ofNullable(deal.getCurrency());
                if (dealCurrency.isPresent()){
                    statement.setInt(3, dealCurrency.get().getId());
                }else {
                    statement.setNull(3, Types.INTEGER);
                }

                Optional<Integer> dealBudget = Optional.ofNullable(deal.getBudget());
                if (dealBudget.isPresent()){
                    statement.setInt(4, dealBudget.get());
                }else {
                    statement.setNull(4, Types.INTEGER);
                }

                Optional<Contact> dealMainContact = Optional.ofNullable(deal.getMainContact());
                if (dealMainContact.isPresent()){
                    statement.setInt(5, dealMainContact.get().getId());
                }else {
                    statement.setNull(5, Types.INTEGER);
                }

                Optional<Company> dealCompany = Optional.ofNullable(deal.getDealCompany());
                if (dealCompany.isPresent()){
                    statement.setInt(6, dealCompany.get().getId());
                }else {
                    statement.setNull(6, Types.INTEGER);
                }

                Optional<java.util.Date> dealCloseDate = Optional.ofNullable(deal.getDateWhenDealClose());
                if (dealCloseDate.isPresent()){
                    statement.setTimestamp(7, new Timestamp(dealCloseDate.get().getTime()));
                }else {
                    statement.setNull(7, Types.TIMESTAMP);
                }

                Optional<java.util.Date> dealCreatedDate = Optional.ofNullable(deal.getDateCreated());
                if (dealCreatedDate.isPresent()){
                    statement.setTimestamp(8, new Timestamp(dealCreatedDate.get().getTime()));
                }else {
                    statement.setNull(8, Types.TIMESTAMP);
                }

                Optional<User> dealResponsible = Optional.ofNullable(deal.getResponsible());
                if (dealResponsible.isPresent()){
                    statement.setInt(9, dealResponsible.get().getId());
                }else {
                    statement.setNull(9, Types.INTEGER);
                }
                return statement;
            }
        }, holder);
        return read((Integer)holder.getKeys().get("id"));
    }

    public Deal read(int key) throws DataBaseException {
        return getJdbcTemplate().queryForObject(READ_ALL_QUERY+"WHERE deal.id = ?", new Object[]{key}, dealRowMapper);
    }

    public Deal readLite(int key) throws DataBaseException {
        return getJdbcTemplate().queryForObject(READ_ALL_QUERY+"WHERE deal.id = ?", new Object[]{key}, dealRowMapper);
    }

    public void update(final Deal deal) throws DataBaseException {
        String query = "UPDATE deal SET status_id = :status_id, currency_id = :currency_id, budget = :budget, contact_main_id = :contact_main_id," +
                "company_id = :company_id, data_close = :data_close, created_date = :created_date, responsible_id = :responsible_id WHERE id = :id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status_id", deal.getStatus().getId());
        params.put("currency_id", deal.getCurrency().getId());
        params.put("budget", deal.getBudget());
        params.put("contact_main_id", deal.getMainContact().getId());
        params.put("company_id", deal.getDealCompany().getId());
        params.put("data_close", deal.getDateWhenDealClose());
        params.put("created_date", deal.getDateCreated());
        params.put("responsible_id", deal.getResponsible().getId());
        params.put("id", deal.getId());
        namedParameterJdbcTemplate.update(query, params);
    }

    public void delete(final int id) throws DataBaseException {
        getJdbcTemplate().update(DELETE_QUERY, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, id);
            }
        });
    }

    public List<Deal> readAll() throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL_QUERY, dealRowMapper);
    }

    public List<Deal> readStatusFilter(int statusId) throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL_QUERY+DEAL_SELECT_STATUS_ID, new Object[]{statusId}, dealRowMapper);
    }

    public List<Deal> readUserFilter(int userId) throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL_QUERY+DEAL_SELECT_BY_USER, new Object[]{userId}, dealRowMapper);
    }

    public List<Deal> readTagFilter(String tag) throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL_QUERY+DEAL_SELECT_TAG+tag, dealRowMapper);
    }

    public List<Deal> readAllWithConditions(DealFilters condition) throws DataBaseException {
        return null;
    }

    public List<Deal> readAllWithConditions(int condition) throws DataBaseException {


        List<Deal> result = new ArrayList<Deal>();
        String sql;
        switch (condition){
            // opened deals
            case 1:
                sql = READ_ALL_QUERY + DEAL_SELECT_OPENED;
                break;
            // success deals
            case 2:
                sql = READ_ALL_QUERY + DEAL_SELECT_SUCCESS;
                break;
            // deals closed and not implemented
            case 3:
                sql = READ_ALL_QUERY + DEAL_SELECT_CLOSED_AND_NOT_IMPLEMENTED;
                break;
            // deals without tasks
            case 4:
                sql = READ_ALL_QUERY + DEAL_SELECT_WITHOUT_TASKS;
                break;
            // deals with expired tasks
            case 5:
                sql = READ_ALL_QUERY + DEAL_SELECT_WITH_EXPIRED_TASKS;
                break;
            // deleted deals
            case 6:
                sql = READ_ALL_QUERY + DEAL_SELECT_DELETED;
                break;
            default:
                return result;
        }
        if(condition == 5){
            return getJdbcTemplate().query(sql, dealRowMapper);
        } else{
            return getJdbcTemplate().query(sql, new Object[]{new Date(System.currentTimeMillis())}, dealRowMapper);
        }
    }


    public List<Deal> readAllByCreatedDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL_QUERY+DEAL_SELECT_PERIOD_CREATED_DATE, new Object[]{dateBegin,dateEnd}, dealRowMapper);
    }

    public List<Deal> readAllByTasksDueDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException {
        return getJdbcTemplate().query(READ_ALL_QUERY+DEAL_SELECT_TASK_DUE_DATE_INTERVAL, new Object[]{dateBegin,dateEnd}, dealRowMapper);
    }

    public List<Deal> readAllWithConditions(List<String> conditionsList) throws DataBaseException {
        return null;
    }

    public int findTotalDeals(){
        String sql = "SELECT COUNT(*) FROM deal";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

    public int findTotalDealsBudget(){
        String sql = "SELECT SUM (budget) FROM deal";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

    public int findTotalDealsWithTasks(){
        String sql = "SELECT COUNT(*) FROM deal WHERE id IN (SELECT subject_id FROM task GROUP BY subject_id)";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

    public int findTotalDealsWithoutTasks(){
        String sql = "SELECT COUNT(*) FROM deal WHERE NOT id IN (SELECT subject_id FROM task GROUP BY subject_id)";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

    public int findTotalSuccessDeals(){
        String sql = "SELECT COUNT(*) FROM deal WHERE status_id=5";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }

    public int findTotalUnsuccessClosedDeals(){
        String sql = "SELECT COUNT(*) FROM deal WHERE status_id=6";
        int total = getJdbcTemplate().queryForObject(
                sql, Integer.class);
        return total;
    }


}
