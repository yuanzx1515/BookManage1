package book.manage.sql;

import book.manage.Mapper.BookMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.function.Consumer;

public class SqlUtil{
    private  static SqlSessionFactory factory;
    static {
        try{
            factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static  void  doSqlWork(Consumer<BookMapper> consumer){
        try (SqlSession session = factory.openSession(true)) {
            BookMapper mapper = session.getMapper(BookMapper.class);
            consumer.accept(mapper);
        }
    }
}
