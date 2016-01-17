package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.DealDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.Optional;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class DealDAOImpl extends AbstractJDBCDao<Deal> implements DealDAO{
    public static final String DEAL_SELECT_TAG = " WHERE deal.id IN(SELECT subject_id FROM subject_tag " +
            "WHERE subject_tag.tag_id IN (SELECT id FROM tag WHERE name IN (";
    private final static Logger LOGGER = LogManager.getLogger(DealDAOImpl.class);
    public static final String DEAL_SELECT_STATUS_ID = " where status_id=?";
    public static final String DEAL_SELECT_USER_ID = " where id in(select subject.id from subject " +
            "where subject.content_owner_id = ?)";
    public static final String DEAL_SELECT_OPENED = " WHERE deal.data_close IS null";
    public static final String DEAL_SELECT_BY_USER = " WHERE subject.content_owner_id=?";
    public static final String DEAL_SELECT_WITHOUT_TASKS = " WHERE NOT deal.id IN (SELECT subject_id FROM task GROUP BY subject_id)";
    public static final String DEAL_SELECT_WITH_EXPIRED_TASKS = " WHERE deal.id IN (SELECT subject_id FROM task WHERE NOT (due_date IS null) AND due_date < ? GROUP BY subject_id)";
    public static final String DEAL_SELECT_SUCCESS = " WHERE deal.status_id=5";
    public static final String DEAL_SELECT_CLOSED_AND_NOT_IMPLEMENTED = " WHERE deal.status_id=6";
    public static final String DEAL_SELECT_DELETED = " WHERE deal.status_id=7";
    public static final String DEAL_SELECT_PERIOD_CREATED_DATE = " WHERE DATE(deal.created_date) BETWEEN ? AND ?";
    public static final String DEAL_SELECT_TASK_DUE_DATE_INTERVAL = "WHERE deal.id IN (SELECT subject_id FROM task WHERE DATE(due_date) BETWEEN ? AND ? GROUP BY subject_id)";

    public DealDAOImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    protected String getConditionStatment() {
        return "WHERE deal.id = ?";
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT deal.id, status_id, currency_id, budget, contact_main_id, company_id, data_close, deal.created_date, name FROM deal JOIN subject ON subject.id=deal.id ";
    }

    @Override
    public String getReadQuery() {
        return getReadAllQuery() + getConditionStatment();
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO deal(id, status_id, currency_id, budget, contact_main_id, company_id, data_close, created_date) " +
                "VALUES(?,?,?,?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE deal SET status_id = ?, currency_id = ?, budget = ?, contact_main_id = ?," +
                "company_id = ?, data_close = ?, created_date = ? WHERE id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM deal WHERE id = ?";
    }

    @Override
    protected List<Deal> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Deal> result = new ArrayList<>();
        try {
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            GenericDao companyDao = getDaoFromCurrentFactory(Company.class);
            GenericDao userDao = getDaoFromCurrentFactory(User.class);
            GenericDao dealStatusDao = getDaoFromCurrentFactory(DealStatus.class);
            GenericDao currencyDao = getDaoFromCurrentFactory(Currency.class);
            SubjectTagDAOImpl subjectTagDAOImpl = (SubjectTagDAOImpl) getDaoFromCurrentFactory(SubjectTag.class);
            DealContactDAOImpl dealContactDAOImpl = (DealContactDAOImpl) getDaoFromCurrentFactory(DealContact.class);
            FileDAOImpl fileDao = (FileDAOImpl) getDaoFromCurrentFactory(File.class);
            CommentDAOImpl commentDAO = (CommentDAOImpl) getDaoFromCurrentFactory(Comment.class);
            TaskDAOImpl taskDAO = (TaskDAOImpl) getDaoFromCurrentFactory(Task.class);
            while (rs.next()) {
                Deal deal = new Deal();
                int id = rs.getInt("id");
                Subject subject = (Subject) subjectDao.readLite(id);
                deal.setId(id);
                deal.setName(subject.getName());
                deal.setBudget(rs.getInt("budget"));
                deal.setDateWhenDealClose(rs.getTimestamp("data_close"));
                deal.setDateCreated(rs.getTimestamp("created_date"));
                Company company = (Company) companyDao.readLite(rs.getInt("company_id"));
                deal.setDealCompany(company);
                User user = (User) userDao.read(rs.getInt("contact_main_id"));
                deal.setMainContact(user);
                DealStatus dealStatus = (DealStatus) dealStatusDao.read(rs.getInt("status_id"));
                deal.setStatus(dealStatus);
                Currency currency = (Currency) currencyDao.read(rs.getInt("currency_id"));
                deal.setCurrency(currency);
                deal.setTags(subjectTagDAOImpl.getAllTagsBySubjectId(id));
                deal.setContacts(dealContactDAOImpl.getAllContactsBySubjectId(id));
                deal.setFiles(fileDao.getAllFilesBySubjectId(id));
                deal.setComments(commentDAO.getAllCommentsBySubjectId(id));
                deal.setTasks(taskDAO.getAllTasksBySubject(deal));
                result.add(deal);
            }
        } catch (SQLException e) {
            logger.error("Error while parsing RS for deal");
            logger.catching(e);
        }
        return result;
    }


    @Override
    protected List<Deal> parseResultSetLite(ResultSet rs) throws DataBaseException {
        List<Deal> result = new ArrayList<>();
        try {
            GenericDao companyDao = getDaoFromCurrentFactory(Company.class);
            GenericDao userDao = getDaoFromCurrentFactory(User.class);
            GenericDao dealStatusDao = getDaoFromCurrentFactory(DealStatus.class);
            GenericDao currencyDao = getDaoFromCurrentFactory(Currency.class);
            SubjectTagDAOImpl subjectTagDAOImpl = (SubjectTagDAOImpl) getDaoFromCurrentFactory(SubjectTag.class);
            TaskDAOImpl taskDAO = (TaskDAOImpl) getDaoFromCurrentFactory(Task.class);
            while (rs.next()) {
                Deal deal = new Deal();
                int id = rs.getInt("id");
                deal.setId(id);
                deal.setName(rs.getString("name"));
                deal.setBudget(rs.getInt("budget"));
                deal.setDateWhenDealClose(rs.getTimestamp("data_close"));
                deal.setDateCreated(rs.getTimestamp("created_date"));
                Company company = (Company) companyDao.readLite(rs.getInt("company_id"));
                deal.setDealCompany(company);
                User user = (User) userDao.read(rs.getInt("contact_main_id"));
                deal.setMainContact(user);
                DealStatus dealStatus = (DealStatus) dealStatusDao.read(rs.getInt("status_id"));
                deal.setStatus(dealStatus);
                Currency currency = (Currency) currencyDao.read(rs.getInt("currency_id"));
                deal.setCurrency(currency);
                deal.setTags(subjectTagDAOImpl.getAllTagsBySubjectId(id));
                deal.setTasks(taskDAO.getAllTasksBySubject(deal));
                result.add(deal);
            }
        } catch (SQLException e) {
            logger.error("Error while parsing RSLite for deal");
            logger.catching(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Deal deal) throws DataBaseException {
        try {
            statement.setInt(1, createSubject(deal));

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

            Optional<User> dealMainContact = Optional.ofNullable(deal.getMainContact());
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
        } catch (SQLException e) {
            logger.error("Error while prepare statement for insert new deal");
            logger.catching(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Deal object) throws DataBaseException {
        try{
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            subjectDao.update(object);
            statement.setInt(1, object.getStatus().getId());
            statement.setInt(2, object.getCurrency().getId());
            statement.setInt(3, object.getBudget());
            statement.setInt(4, object.getMainContact().getId());
            statement.setInt(5, object.getDealCompany().getId());
            statement.setTimestamp(6, new Timestamp(object.getDateWhenDealClose().getTime()));
            statement.setTimestamp(7, new Timestamp(object.getDateCreated().getTime()));
            statement.setInt(8, object.getId());
        } catch (SQLException e) {
            logger.error("Error while prepare statement for update new deal");
            logger.catching(e);
        }
        }

    public List<Deal> readStatusFilter(int statusId) throws DataBaseException {
        List<Deal> result;
        try (PreparedStatement statement = getConnection().prepareStatement(getReadAllQuery() + DEAL_SELECT_STATUS_ID)) {
            statement.setInt(1, statusId);
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        if (result == null) {
            logger.error("Error while reading status filter");
            throw new DataBaseException();
        }
        return result;
    }

    public List<Deal> readUserFilter(int userId) throws DataBaseException {
        List<Deal> result;
        try (PreparedStatement statement = getConnection().prepareStatement(getReadAllQuery() + DEAL_SELECT_BY_USER)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        if (result == null) {
            logger.error("Error while reading user filter");
            throw new DataBaseException();
        }
        return result;
    }

    public List<Deal> readTagFilter(String tag) throws DataBaseException {
        List<Deal> result;
        try (PreparedStatement statement = getConnection().prepareStatement(getReadAllQuery() + DEAL_SELECT_TAG + tag)){
//            statement.setString(1, tag);
//            statement.setObject(1, tag);
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public List<Deal> readAllWithConditions(int condition ) throws DataBaseException {
        List<Deal> result = new ArrayList<Deal>();
        String sql = "";
        try{
            switch (condition){
                // opened deals
                case 1:
                    sql = getReadAllQuery() + DEAL_SELECT_OPENED;
                    break;
                // success deals
                case 2:
                    sql = getReadAllQuery() + DEAL_SELECT_SUCCESS;
                    break;
                // deals closed and not implemented
                case 3:
                    sql = getReadAllQuery() + DEAL_SELECT_CLOSED_AND_NOT_IMPLEMENTED;
                    break;
                // deals without tasks
                case 4:
                    sql = getReadAllQuery() + DEAL_SELECT_WITHOUT_TASKS;
                    break;
                // deals with expired tasks
                case 5:
                    sql = getReadAllQuery() + DEAL_SELECT_WITH_EXPIRED_TASKS;
                    break;
                // deleted deals
                case 6:
                    sql = getReadAllQuery() + DEAL_SELECT_DELETED;
                    break;
                default:
                    return result;
            }
            PreparedStatement statement = getConnection().prepareStatement(sql);
            switch (condition) {
                // deals with expired tasks
                case 5:
                    statement.setDate(1, new Date(System.currentTimeMillis()));
                    break;
                default:
                    break;
            };
                ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public List<Deal> readAllByCreatedDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException{
        List<Deal> result;
        try (PreparedStatement statement = getConnection().prepareStatement(getReadAllQuery() + DEAL_SELECT_PERIOD_CREATED_DATE)) {
            statement.setDate(1, dateBegin);
            statement.setDate(2, dateEnd);
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public List<Deal> readAllByTasksDueDateInterval(Date dateBegin, Date dateEnd) throws DataBaseException {
        List<Deal> result;
        try (PreparedStatement statement = getConnection().prepareStatement(getReadAllQuery() + DEAL_SELECT_TASK_DUE_DATE_INTERVAL)) {
            statement.setDate(1, dateBegin);
            statement.setDate(2, dateEnd);
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }


}
