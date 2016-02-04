package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.SubjectDAO;
import com.becomejavasenior.interfacedao.SubjectTagDAO;
import com.becomejavasenior.interfacedao.TagDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class SubjectDAOImpl extends AbstractJDBCDao<Subject> implements SubjectDAO {
    @Autowired
    public UserDAO userDAO;
    @Autowired
    public TagDAO tagDAO;
    @Autowired
    public SubjectTagDAO subjectTagDAO;

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM subject WHERE id= ?;";
    }

    @Override
    public String getReadAllQuery() {
        return "SELECT * FROM subject";
    }

    @Override
    protected List<Subject> parseResultSet(ResultSet rs) throws DataBaseException {
        List<Subject> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Subject subject = new Subject() {
                };
                User user = userDAO.read(rs.getInt("content_owner_id"));
                subject.setUser(user);
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                result.add(subject);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    protected List<Subject> parseResultSetLite(ResultSet rs) throws DataBaseException {
        List<Subject> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Subject subject = new Subject() {
                };
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                result.add(subject);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO subject (content_owner_id, name) VALUES (?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Subject object) throws DataBaseException {
        try {
            if (object.getUser() != null) {
                statement.setInt(1, object.getUser().getId());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, object.getName());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE subject SET name = ?, content_owner_id = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Subject object) throws DataBaseException {
        try {
            statement.setString(1, object.getName());
            if (object.getUser() != null) {
                statement.setInt(2, object.getUser().getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.setInt(3, object.getId());
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }

    }

    public <T extends Subject> int createSubject(T object) throws DataBaseException {
        if (object != null) {
            Subject subject = create(object);
            createTags(object.getTags(), subject);
            return subject.getId();
        } else {
            throw new DataBaseException();
        }
    }

    private <T extends Subject> void createTags(Set<Tag> tags, T object) throws DataBaseException {
        if (tags != null) {
            for (Tag tag : tags) {
                SubjectTag subjectTag = new SubjectTag();
                if (object instanceof Contact) {
                    tag.setSubjectType(SubjectType.CONTACT_TAG);
                } else if (object instanceof Company) {
                    tag.setSubjectType(SubjectType.COMPANY_TAG);
                } else if (object instanceof Deal) {
                    tag.setSubjectType(SubjectType.DEAL_TAG);
                }
                tag = tagDAO.create(tag);
                subjectTag.setTag(tag);
                subjectTag.setSubject(object);
                subjectTagDAO.create(subjectTag);
            }
        }
    }


}
