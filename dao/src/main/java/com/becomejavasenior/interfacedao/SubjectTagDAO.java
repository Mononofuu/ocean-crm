package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;
import com.becomejavasenior.SubjectTag;
import com.becomejavasenior.Tag;

import java.util.Set;

public interface SubjectTagDAO extends GenericDao<SubjectTag> {

    /**
     * Метод возвращает все тэги по заданному subject_id
     */
    Set<Tag> getAllTagsBySubjectId(int id) throws DataBaseException;
}
