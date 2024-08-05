package book.manage.entity;

import lombok.Data;

@Data
public class borrow {
    int id;
    student student;
    Book book;
}
