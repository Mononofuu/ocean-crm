package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Tag;

import java.util.Set;

public interface SubjectTagDAO {

    /**
     * Метод возвращает все тэги по заданному subject_id
     */
    Set<Tag> getAllTagsBySubjectId(int id) throws DataBaseException;
}
