package com.becomejavasenior;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface TagService {
    Tag saveTag(Tag tag)throws DataBaseException;
    void deleteTag(int id)throws DataBaseException;
    Tag findTagById(int id)throws DataBaseException;
    List<Tag> getAllTags()throws DataBaseException;
}
