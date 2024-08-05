package book.manage;

import book.manage.Mapper.BookMapper;
import book.manage.entity.Book;
import book.manage.entity.student;
import book.manage.sql.SqlUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.LogManager;

@Log
public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            LogManager logManager = LogManager.getLogManager();
            logManager.readConfiguration(Resources.getResourceAsStream("logging.properties"));

            while (true) {
                System.out.println("========================================");
                System.out.println("1, 录入学生信息");
                System.out.println("2, 录入书籍信息");
                System.out.println("3, 借书");
                System.out.println("4, 查询借阅信息");
                System.out.println("5，查询学生信息");
                System.out.println("6, 查询书籍信息");
                System.out.print("输入您想要执行的操作，按任意数字退出：");

                int input;
                try {
                    input = scanner.nextInt();
                } catch (Exception e) {
                    return;
                }
                scanner.nextLine(); // 消耗掉换行符

                switch (input) {
                    case 1:
                        addStudent(scanner);
                        break;
                    case 2:
                        addBook(scanner);
                        break;
                    case 3:
                        addBorrow(scanner);
                        break;
                        case 4:
                            showBorrow();
                            break;
                            case 5:
                                showStudent();
                                break;
                                case 6:
                                    showBook();
                                    break;
                    default:
                        return; // 退出程序
                }
            }
        }
    }

    private static void addBook(Scanner scanner) {
        System.out.print("请输入书籍名字：");
        String name = scanner.nextLine();

        System.out.print("请输入书籍介绍：");
        String desc = scanner.nextLine();

        System.out.print("请输入书籍价格：");
        double price = scanner.nextDouble();
        scanner.nextLine(); // 消耗掉换行符

        Book book = new Book(name, desc, price);
        SqlUtil.doSqlWork(bookMapper -> {
            int i = bookMapper.addBook(book);
            if (i > 0) {
                System.out.println("书籍插入成功");
                log.info("新添加了一条书籍信息：" + book);
            } else {
                System.out.println("录入失败，请检查原因");
            }
        });
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("请输入学生名字：");
        String name = scanner.nextLine();

        System.out.print("请输入性别：");
        String sex = scanner.nextLine();

        System.out.print("请输入年级：");
        int grade = scanner.nextInt();
        scanner.nextLine(); // 消耗掉换行符

        student student = new student(name, sex, grade);
        SqlUtil.doSqlWork(bookMapper -> {
            int i = bookMapper.addStudent(student);
            if (i > 0) {
                System.out.println("学生插入成功");
                log.info("新添加了一条学生信息：" + student);
            } else {
                System.out.println("录入失败，请检查原因");
            }
        });
    }

    private static void addBorrow(Scanner scanner) {
        System.out.print("请输入书籍编号：");
        int bid = scanner.nextInt();

        System.out.print("请输入学号：");
        int sid = scanner.nextInt();
        scanner.nextLine(); // 消耗掉换行符

        SqlUtil.doSqlWork(bookMapper -> {
            int i = bookMapper.borrowBook(sid, bid);
            if (i > 0) {
                System.out.println("借书成功");
            } else {
                System.out.println("借书失败，请检查原因");
            }
        });
    }

    private static void showBorrow() {
        SqlUtil.doSqlWork(bookMapper -> {
            bookMapper.getBorrowList().forEach(borrow ->
                    System.out.println(borrow.getStudent().getName()+"-> "+borrow.getBook().getTitle())
            );
        });
    }
    private static void showStudent() {
        SqlUtil.doSqlWork(bookMapper -> {
            bookMapper.getStudentList().forEach(student ->
                    System.out.println(student.getName()+" " +" "+student.getSex()+" "+" "+student.getSid()+" "+student.getGrade())
            );
        });
    }
    private static void showBook() {
        SqlUtil.doSqlWork(Mapper -> {
            Mapper.getBookList().forEach(book -> {
                System.out.println(book.getTitle()+" "+book.getDesc()+" "+book.getPrice());
            });
        });
    }
}



