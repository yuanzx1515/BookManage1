package book.manage.Mapper;

import book.manage.entity.Book;
import book.manage.entity.borrow;
import book.manage.entity.student;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface BookMapper {
    @Insert("insert  into  student(name,sex,grade) values (#{name},#{sex},#{grade})")
    int addStudent(student student);
    @Insert("insert  into  book(title,`desc`,price) values (#{title},#{desc},#{price})")
    int addBook(Book book);

    @Insert("insert  into  borrow(sid,bid) values (#{sid},#{bid})")
    int borrowBook(@Param("sid") int sid ,@Param("bid") int bid);

    @Results({
            @Result(column ="id",property = "id", id = true),
            @Result(column = "sid",property = "student" , one = @One(
                    select = "getStudentByBid")),
            @Result(column = "bid",property = "book",one = @One(
                    select = "getBookByBid"))
    })
    @Select("select  * from  borrow")
    List<borrow> getBorrowList();

    @Select("select  * from  student")
    List<student> getStudentList();

    @Select("select * from  book")
    List<Book> getBookList();



    @Select("select  * from  student where  sid = #{sid}")
    student getStudentByBid(int sid);

    @Select("select  * from  book where  bid = #{bid}")
    Book getBookByBid(int bid);
}
