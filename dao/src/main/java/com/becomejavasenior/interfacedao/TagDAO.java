package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;
import com.becomejavasenior.SubjectType;
import com.becomejavasenior.Tag;

import java.util.List;

public interface TagDAO extends GenericDao<Tag>{
    int checkIfExists(Tag tag) throws DataBaseException;
    List<Tag> readAllSubjectTags(int subjectId) throws DataBaseException;
    List<Tag> readAll(SubjectType subjectType) throws DataBaseException;
}
