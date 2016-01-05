package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.SubjectTagDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubjectTagDAOImpl extends AbstractJDBCDao<SubjectTag> implements SubjectTagDAO {

    @Override
    protected String getConditionStatment() {
        return " WHERE subject_id = ?";
    }

    //Переопределяем метод чтобы возвращал null и не ломал метод read().
    @Override
    public SubjectTag create(SubjectTag object) throws DataBaseException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getCreateQuery(), Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, object); // помещаем в запрос параметры object
            statement.executeUpdate();
            // получаем обратно новую запись через возвращенный id записи
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int key = rs.getInt(1);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return null;
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT subject_id, tag_id, tag.name FROM subject_tag JOIN subject ON subject_tag.subject_id = subject.id JOIN tag ON subject_tag.tag_id = tag.id ";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM subject_tag WHERE id= ?;";
    }

    @Override
    protected List<SubjectTag> parseResultSet(ResultSet rs) throws DataBaseException {
        List<SubjectTag> result = new ArrayList<>();
        try {
            GenericDao subjectDao = getDaoFromCurrentFactory(Subject.class);
            GenericDao tagDao = getDaoFromCurrentFactory(Tag.class);
            while (rs.next()) {
                SubjectTag subjectTag = new SubjectTag();
                Subject subject = (Subject) subjectDao.read(rs.getInt("subject_id"));
                subjectTag.setSubject(subject);
                Tag tag = (Tag) tagDao.read(rs.getInt("tag_id"));
                subjectTag.setTag(tag);
                result.add(subjectTag);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected List<SubjectTag> parseResultSetLite(ResultSet rs) throws DataBaseException {
        List<SubjectTag> result = new ArrayList<>();
        try {
            while (rs.next()) {
                SubjectTag subjectTag = new SubjectTag();
                Tag tag = new Tag(rs.getString("name"));
                subjectTag.setTag(tag);
                result.add(subjectTag);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO subject_tag (subject_id, tag_id) VALUES (?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, SubjectTag object) throws DataBaseException {
        try {
            statement.setInt(1, object.getSubject().getId());
            statement.setInt(2, object.getTag().getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE subject_tag SET subject_id = ?, tag_id=? WHERE subject_id = ? AND tag_id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, SubjectTag object) throws DataBaseException {
        int subjectId = object.getSubject().getId();
        int tagId = object.getTag().getId();
        try {
            statement.setInt(1, subjectId);
            statement.setInt(2, tagId);
            statement.setInt(3, subjectId);
            statement.setInt(4, tagId);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    /**
     * Метод возвращает все тэги по заданному subject_id
     */
    public Set<Tag> getAllTagsBySubjectId(int id) throws DataBaseException {
        Set<Tag> result = new HashSet<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getReadAllQuery() + getConditionStatment())) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<SubjectTag> list = parseResultSetLite(rs);
            for (SubjectTag subjectTag : list) {
                result.add(subjectTag.getTag());
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }
}
