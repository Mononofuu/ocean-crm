package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractJDBCDao;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Tag;
import com.becomejavasenior.interfacedao.TagDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDAOImpl extends AbstractJDBCDao<Tag> implements TagDAO {
    public TagDAOImpl(Connection connection) throws DataBaseException {
        super(connection);
    }

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
        return "INSERT INTO tag (name) VALUES (?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Tag object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
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
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
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
}
