package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractJDBCDao;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.SubjectType;
import com.becomejavasenior.Tag;
import com.becomejavasenior.interfacedao.TagDAO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagDAOImpl extends AbstractJDBCDao<Tag> implements TagDAO {

    public static final String SELECT_ALL_SUBJECT_TAGS = " WHERE id IN(SELECT tag_id FROM subject_tag WHERE subject_id = ";

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM tag";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM tag WHERE id= ?;";
    }

    @Override
    protected List<Tag> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Tag> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setId(rs.getInt("id"));
                tag.setName(rs.getString("name"));
                result.add(tag);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO tag (name, subject_type) VALUES (?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Tag object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getSubjectType().ordinal());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE tag SET name = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Tag object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public Tag create(Tag object) throws DataBaseException {
        int checkedId = checkIfExists(object);
        if (checkedId < 0) {
            return super.create(object);
        } else {
            return read(checkedId);
        }

    }

    //Поверяет есть ли такой тэг в базе. Если есть, возвращает его id, если нет то возвращает -1.
    public int checkIfExists(Tag tag) throws DataBaseException {
        String query = "SELECT * FROM tag WHERE name = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tag.getName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<Tag> readAllSubjectTags(int subjectId) throws DataBaseException {
        return realiseQuery(getReadAllQuery() + SELECT_ALL_SUBJECT_TAGS + subjectId + ")");
    }

    @Override
    public List<Tag> readAll(SubjectType subjectType) throws DataBaseException {
        return realiseQuery(getReadAllQuery()+" WHERE subject_type = "+subjectType.ordinal());
    }
}
