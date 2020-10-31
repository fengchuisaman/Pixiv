package cn.youngbear.utils.util;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisSessionPool extends UnpooledDataSourceFactory {
    private static SqlSessionFactory sf = null;
    static {
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            sf = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSession() {
        return  sf.openSession(true);
    }

}
