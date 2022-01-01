package test.dao;

import constants.QueryConstants;
import dao.JpaDao;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JpaDaoTest {
    private JpaDao<User> jpaDao;
    @Mock
    private EntityManager em;
    User user;

    @Before
    public void setup() {
        jpaDao = new JpaDao<>(em);
        user = new User(1, "TestUser", "test1234", "test@gmail.com");
    }

    @Test
    public void givenEntity_whenCreate_thenEntityIsAdded() {
        doNothing().when(em).persist(user);
        doNothing().when(em).flush();
        doNothing().when(em).refresh(user);

        jpaDao.create(user);

        verify(em, times(1)).persist(user);
        verify(em, times(1)).flush();
        verify(em, times(1)).refresh(user);
    }

    @Test
    public void givenEntity_whenUpdate_thenEntityIsUpdated() {
        User updatedUser = user;
        updatedUser.setName("NewName");
        when(em.merge(user)).thenReturn(updatedUser);

        User response = jpaDao.update(user);

        assertEquals(updatedUser, response);
    }

    @Test
    public void givenId_whenGet_thenReturnEntity() {
        when(em.find(User.class, user.getId())).thenReturn(user);

        User response = jpaDao.get(User.class, user.getId());

        assertEquals(user, response);
    }

    @Test
    public void givenId_whenDelete_thenEntityIsDeleted() {
        when(em.getReference(User.class, user.getId())).thenReturn(user);
        doNothing().when(em).remove(user);

        jpaDao.delete(User.class, user.getId());

        verify(em, times(1)).remove(user);
    }

    @Test
    public void givenQuery_whenGetAll_thenReturnAllEntities() {
        Query mockedQuery = mock(Query.class);
        when(em.createNamedQuery(QueryConstants.GET_ALL_USERS)).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(new ArrayList<User>());

        List<User> response = jpaDao.getAll(QueryConstants.GET_ALL_USERS);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    public void givenQueryAndProp_whenGetByProperty_thenReturnEntity() {
        Optional<User> optionalUser = Optional.of(user);
        Query mockedQuery = mock(Query.class);
        when(em.createNamedQuery(QueryConstants.FIND_USER_BY_EMAIL)).thenReturn(mockedQuery);
        when(mockedQuery.setParameter(1, user.getEmail())).thenReturn(mockedQuery);
        when(mockedQuery.setMaxResults(1)).thenReturn(mockedQuery);
        when(mockedQuery.getSingleResult()).thenReturn(user);

        Optional<User> response = jpaDao.getByProperty(QueryConstants.FIND_USER_BY_EMAIL, user.getEmail());

        assertEquals(optionalUser, response);
    }
}
