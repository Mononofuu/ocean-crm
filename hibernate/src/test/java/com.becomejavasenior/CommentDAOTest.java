package com.becomejavasenior;

import com.becomejavasenior.config.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
public class CommentDAOTest {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    @Transactional
    public void CrudCommentTest() {
        Session session = sessionFactory.openSession();
        Comment commentInit = new Comment();
        User tempUser = new User();
        tempUser.setId(1);
        commentInit.setUser(tempUser);
        commentInit.setText("MOCK COMMENT");
        Subject subject = new Company();
        subject.setId(1);
        commentInit.setSubject(subject);
        int id = (int) session.save(commentInit);

        Comment comment = (Comment) session.get(Comment.class, id);
        Assert.assertEquals(commentInit.getText(), comment.getText());
        session.close();
    }

}
