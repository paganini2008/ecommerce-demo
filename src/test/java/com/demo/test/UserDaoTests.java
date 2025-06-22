// package com.demo.test;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import java.util.List;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import com.demo.springjpa.SpringJpaMain;
// import com.demo.springjpa.dao.OrderDao;
// import com.demo.springjpa.dao.OrderProductDao;
// import com.demo.springjpa.dao.ProductDao;
// import com.demo.springjpa.dao.UserDao;
// import com.demo.springjpa.entity.User;
// import jakarta.transaction.Transactional;
//
//
// @SpringBootTest(classes = SpringJpaMain.class)
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// public class UserDaoTests {
//
// private static final Logger log = LoggerFactory.getLogger(UserDaoTests.class);
//
// @Autowired
// private UserDao userDao;
//
// @Autowired
// private OrderDao orderDao;
//
// @Autowired
// private ProductDao productDao;
//
// @Autowired
// private OrderProductDao orderProductDao;
//
// @BeforeAll
// public void init() {
// List.of(new User("Jack", "123456", "Jack001@jpatest.com"),
// new User("Petter", "123456", "Petter002@jpatest.com"),
// new User("Scott", "123456", "scott003@jpatest.com"),
// new User("Lee", "123456", "lee004@jpatest.com"),
// new User("Terry", "123456", "terry005@jpatest.com")).forEach(user -> {
// userDao.saveAndFlush(user);
// });
// log.info("Total users: {}", userDao.count());
// }
//
// @Test
// public void testTotalCount() {
// User user = userDao.query().eq(User::getUsername, "Jack").eq(User::getPassword, "123456")
// .selectThis().first();
// log.info("Load user: {}", user);
// assertEquals(user, new User("Jack", "123456", "Jack001@jpatest.com"));
//
// }
//
// @Transactional
// @AfterAll
// public void clean() {
// // userDao.delete().execute();
// }
//
//
//
// }
