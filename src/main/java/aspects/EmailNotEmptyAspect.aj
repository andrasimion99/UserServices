package aspects;

import dao.UserDao;
import entity.User;

public aspect EmailNotEmptyAspect {
    pointcut checkNotEmpty(User user, UserDao userDao):
            call(User UserDao.create(User)) && args(user) && target(userDao);

    before(User user, UserDao userDao): checkNotEmpty(user, userDao) {}

    User around(User user, UserDao userDao) :
            checkNotEmpty(user, userDao) {
        if(user.getEmail().isEmpty()) {
            return null;
        }
        return proceed(user, userDao);
    }

    after(User user, UserDao userDao): checkNotEmpty(user, userDao) {}
}
