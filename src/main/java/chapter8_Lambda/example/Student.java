package chapter8_Lambda.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    /**
     * id
     */
    private Long id;
    /**
     * 学号 唯一标识
     */
    private String code;
    /**
     * 学生名字
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 分数
     */
    private Double scope;

    /**
     * 要学习的课程
     */
    private List<Course> learningCources;
}
