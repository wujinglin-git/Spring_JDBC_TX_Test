package com.wjl;

import static org.junit.Assert.assertTrue;

import com.wjl.pojo.UserInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    private ApplicationContext atx = null;
    private JdbcTemplate jdbcTemplate=null;
    {
        atx = new ClassPathXmlApplicationContext("applicationContext1.xml");
        jdbcTemplate = (JdbcTemplate)atx.getBean("jdbcTemplate");



    }
        /*
        * 更新操作
        * */
    @Test
    public void testUpdate(){
        String sql = "update userinfo set role=?  where id  = ?";

        System.out.println(jdbcTemplate.update(sql,"test",1 ));
    }

    /*
    * 批量更新
    * */
    @Test
    public void testBatchInsert(){
    String sql = "insert into userinfo( name,password,role ) values(?,?,?) ";
    List<Object[]> batchArgs =new ArrayList<>();
    for (int i = 0; i <1000 ; i++) {
        batchArgs.add(new Object[]{"name"+i,"pw"+i,"role"+i});
    }


    jdbcTemplate.batchUpdate(sql,batchArgs);
}

    /*
    * 获取某一对象
    *
    * Rowmapper常用于映射结果集的行，BeanPropertyRowMapper是其实现类。
    *
    *
    *
    * */
    @Test
    public void testqueryForObject() throws SQLException {
        String sql = "select * from userinfo where id = ?";
        RowMapper<UserInfo> rowMapper = new BeanPropertyRowMapper<>(UserInfo.class);
        UserInfo userInfo = jdbcTemplate.queryForObject(sql,rowMapper,15);
        System.out.println(userInfo);
    }


    @Test
    public void testDateSource() throws SQLException {
        DataSource dataSource = atx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }
}
