package com.becomejavasenior;

import com.becomejavasenior.config.HibernateConfig;
import com.becomejavasenior.interfacedao.CommentDAO;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
@Transactional
@ActiveProfiles(profiles = "test")
public class CommentDAOTest {
    private final static String COMMENT_TEXT = "MOCK COMMENT";
    private final static String COMMENT_TEXT_UPDATED = "MOCK COMMENT";

    @Autowired
    @Qualifier(value = "HibernateCommentDAO")
    CommentDAO commentDAO;


    @Test
    public void crudTest() throws DataBaseException {
        Comment commentInit = new Comment();
        commentInit.setText(COMMENT_TEXT);

        Comment comment  = commentDAO.create(commentInit);
        Assert.assertEquals(COMMENT_TEXT, comment.getText());

        comment.setText(COMMENT_TEXT_UPDATED);
        commentDAO.update(comment);
        comment  = commentDAO.read(comment.getId());
        Assert.assertEquals(COMMENT_TEXT_UPDATED, comment.getText());

        commentDAO.delete(comment.getId());
        Assert.assertNull(commentDAO.read(comment.getId()));

        List<Comment> comments = commentDAO.getAllCommentsBySubjectId(1);
        Assert.assertTrue(comments.isEmpty());
    }

}
