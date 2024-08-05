package book.manage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class student {
    int sid;
   String name;
     String sex;
    int grade;

    public student(String name, String sex, int grade) {
        this.name = name;
        this.sex = sex;
        this.grade = grade;
    }
}
