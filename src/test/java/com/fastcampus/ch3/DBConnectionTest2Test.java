package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {

    @Autowired
    DataSource ds;

    @Test
    public void insertUserTest() throws Exception{
        User user = new User("asdf","1234","abc","aaa@aaa.com",new Date(), "fb",new Date());
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt==1);
    }

    private void deleteAll() throws Exception {
        Connection conn = ds.getConnection();

        String sql = "delete from user_info";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();

    }

    @Test
    public void selectUserTest() throws Exception{
        deleteAll();
        User user = new User("asdf","1234","abc","aaa@aaa.com",new Date(), "fb",new Date());
        int rowCnt = insertUser(user);
        User user2 = selectUser("asdf");
        assertTrue(user2.getId().equals("asdf"));
    }

    @Test
    public void deleteUserTest() throws Exception{
        deleteAll();
        int rowCnt = deleteUser("asdf");

        assertTrue(rowCnt==0);

        User user = new User("asdf","1234","abc","aaa@aaa.com",new Date(), "fb",new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt == 1);

        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt==1);

        assertTrue(selectUser(user.getId())==null);
    }

    // 매개변수를 받은 사용자 정보로 user_info 테이블을 update하는 메서드
    public int updateUser(User user) throws Exception{
        return 0;
    }

    public int deleteUser(String id) throws Exception{
        Connection conn = ds.getConnection();

        String sql = "delete from user_info where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,id);
//        int rowCnt = pstmt.executeUpdate();
        return pstmt.executeUpdate();
    }


    public User selectUser(String id) throws Exception{
        Connection conn = ds.getConnection();

        String sql = "select * from user_info where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery();

        if(rs.next()){
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(rs.getDate(5));
            user.setSns(rs.getString(6));
            user.setReg_date(rs.getDate(7));

            return user;
        }
        return null;
    }

    // 사용자 정보를 user_info 테이블에 저장하는 메서드
    public int insertUser(User user) throws Exception{
        Connection conn = ds.getConnection();

        String sql = "insert into user_info values(?,?,?,?,?,?,now())";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setDate(5,new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());

        int rowCnt = pstmt.executeUpdate();

        return rowCnt;
    }


    @Test
    public void springJdbcConnectionTest() throws Exception{
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn!=null); //  괄호 안의 조건식이 true면 테스트 성공
    }
}