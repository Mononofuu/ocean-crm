package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfaceDAO.GenericTemplateDAO;
import com.becomejavasenior.mapper.DealRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author PeterKramar
 */

public class DealTemplateDAOImpl extends JdbcDaoSupport implements GenericTemplateDAO<Deal> {

    public void create(final Deal deal) {
        String sql = "INSERT INTO deal(id, status_id, currency_id, budget, contact_main_id, company_id, data_close, created_date, responsible_id) VALUES(?,?,?,?,?,?,?,?,?)";

        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, deal.getId());
                preparedStatement.setInt(2, deal.getStatus().getId());
                preparedStatement.setInt(3, deal.getCurrency().getId());
                preparedStatement.setInt(4, deal.getBudget());
                if (deal.getMainContact() == null) {
                    preparedStatement.setNull(5, Types.INTEGER);
                }else{
                    preparedStatement.setInt(5, deal.getMainContact().getId());
                }
                preparedStatement.setInt(6, deal.getDealCompany().getId());
                if (deal.getDateWhenDealClose() == null) {
                    preparedStatement.setTimestamp(7, null);
                }else{
                    preparedStatement.setTimestamp(7, new Timestamp(deal.getDateWhenDealClose().getTime()));
                }
                preparedStatement.setTimestamp(8, new Timestamp(deal.getDateCreated().getTime()));
                preparedStatement.setInt(9, deal.getResponsible().getId());
            }
        });

    }

    //заготовка
    public Deal read(int id) {
        String sql = "SELECT * FROM deal WHERE id = ?";
        return getJdbcTemplate().queryForObject(
                sql, new Object[]{id},
                new DealRowMapper());
    }

    //заготовка
    public List<Deal> readAll() {

        List<Deal> deals = new ArrayList<Deal>();
        String sql = "SELECT * FROM deal";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> row : rows) {
            Deal deal = new Deal();
            deal.setId((Integer) row.get("id"));
            deals.add(deal);
        }
        return deals;
    }

    public void update(final Deal deal) {
        String sql = "UPDATE deal SET status_id = ?, currency_id = ?, budget = ?, contact_main_id = ?,company_id = ?, data_close = ?, created_date = ?, responsible_id = ? WHERE id = ?";
        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, deal.getStatus().getId());
                preparedStatement.setInt(2, deal.getCurrency().getId());
                preparedStatement.setInt(3, deal.getBudget());
                if (deal.getMainContact() == null) {
                    preparedStatement.setNull(4, Types.INTEGER);
                }else{
                    preparedStatement.setInt(4, deal.getMainContact().getId());
                }
                preparedStatement.setInt(5, deal.getDealCompany().getId());
                if (deal.getDateWhenDealClose() == null) {
                    preparedStatement.setTimestamp(6, null);
                }else{
                    preparedStatement.setTimestamp(6, new Timestamp(deal.getDateWhenDealClose().getTime()));
                }
                preparedStatement.setTimestamp(7, new Timestamp(deal.getDateCreated().getTime()));
                preparedStatement.setInt(8, deal.getResponsible().getId());
                preparedStatement.setInt(9, deal.getId());
            }
        }
        );

    }

    public void delete(final int id) {
        String sql = "DELETE FROM deal WHERE id= ?;";
        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, id);
            }
        });
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
