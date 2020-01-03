package chapter8_Lambda.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    /**
     * 课程 ID
     */
    private Long id;

    /**
     * 课程 name
     */
    private String name;

}
