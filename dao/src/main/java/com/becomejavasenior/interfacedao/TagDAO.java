package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Tag;

import java.util.List;

public interface TagDAO {
    int checkIfExists(Tag tag) throws DataBaseException;
    List<Tag> readAllSubjectTags(int subjectId) throws DataBaseException;
}
