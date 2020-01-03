package chapter8_Lambda.example;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 常用方法
 */
@Slf4j
public class LambdaExpression {

    private final List<Student> students = new ArrayList<Student>() {
        {
            // 添加学生数据
            add(new Student(1L, "W199", "小美", "WM", 100D, new ArrayList<Course>() {
                {
                    // 添加学生学习的课程
                    add(new Course(300L, "语文"));
                    add(new Course(301L, "数学"));
                    add(new Course(302L, "英语"));
                }
            }));
            add(new Student(2L, "W25", "小美", "WM", 100D, Collections.emptyList()));
            add(new Student(3L, "W3", "小名", "M", 90D, new ArrayList<Course>() {
                {
                    add(new Course(300L, "语文"));
                    add(new Course(304L, "体育"));
                }
            }));
            add(new Student(4L, "W1", "小蓝", "M", 10D, new ArrayList<Course>() {
                {
                    add(new Course(301L, "数学"));
                    add(new Course(305L, "美术"));
                }
            }));
        }
    };
    private static final List<String> list = ImmutableList.of("hello", "world");

    // 测试过滤
    @Test
    public void testFilter() {
        List<String> newList = list.stream()
                //过滤掉希望留下来的值
                .filter(s -> StringUtils.equals(s, "hello"))
                //Collections.toList()帮助我们构建最后的返回结果
                .collect(Collectors.toList());
        log.info("TestFilter result is {}", JSON.toJSONString(newList));
    }

    // map 的使用
    @Test
    public void testMap(){
        // 得到所有学生的学号
        List<String> codes = students.stream().map(Student::getCode).collect(Collectors.toList());
        log.info("TestMap 所有学生的学号为{}",JSON.toJSONString(codes));
    }

    // mapToInt 的使用
    @Test
    public void testMapToInt(){
        List<Integer> ids = students.stream().mapToInt(s->Integer.valueOf(s.getId()+"")).mapToObj(s->s).collect(Collectors.toList());
        log.info("TestMapToInt is {}",JSON.toJSONString(ids));
    }

    // flatMap 的用法
    @Test
    public void testFlatMap(){
        //计算学生所有的学习课程，flatMap返回List<课程>格式
        List<Course> courses = students.stream().flatMap(s->s.getLearningCources().stream()).collect(Collectors.toList());
        log.info("TestMapToInt flatMap 计算学生的所有学习课程如下{}",JSON.toJSONString(courses));
        // 计算学生所有的学习课程，map返回两层课程嵌套格式
        List<List<Course>> courses2 = students.stream().map(student -> student.getLearningCources()).collect(Collectors.toList());
        log.info("TestMapToInt map 计算学生的所有学习课程如下 {}",JSON.toJSONString(courses2));
        List<Stream<Course>> course3 = students.stream().map(s->s.getLearningCources().stream()).collect(Collectors.toList());
        log.info("TestMapToInt map 计算学生的所有学习课程如下 {}",JSON.toJSONString(course3));
    }

    // distinct 的用法
    @Test
    public void testDistinct(){
        // 得到学生所有的名字，要求是去重过的
//        List<String> beforeNames = students.stream().map(student -> student.getName()).collect(Collectors.toList());
        List<String> beforeNames = students.stream().map(Student::getName).collect(Collectors.toList());
        log.info("TestDistinct 没有去重前的学生名单{}",JSON.toJSONString(beforeNames));
        List<String> distinctNames = beforeNames.stream().distinct().collect(Collectors.toList());
        log.info("TestDistinct 去重之后的学生名单{}",JSON.toJSONString(distinctNames));
    }

    // Sorted
    @Test
    public void testSorted(){
        //学生按照学号排序
        List<String> beforeCodes = students.stream().map(Student::getCode).collect(Collectors.toList());
        log.info("TestSorted 按照学号排序之前{}",JSON.toJSONString(beforeCodes));
        List<String> sortedCodes = beforeCodes.stream().sorted().collect(Collectors.toList());
        log.info("TestSorted 按照学号排序之后是{}",JSON.toJSONString(sortedCodes));

        // 直接连接起来写
        List<String> codes = students.stream().map(Student::getCode).sorted().collect(Collectors.toList());
        log.info("TestSorted 自然排序是{}",JSON.toJSONString(codes));
        // 自定义排序器
        List<String> codes2 = students.stream().map(Student::getCode).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        log.info("TestSorted 反自然排序是{}",JSON.toJSONString(codes2));
    }

    // peek
    @Test
    public void testPeek(){
        students.stream().map(Student::getCode).peek(s -> log.info("当前循环的学号是{}",s)).collect(Collectors.toList());
    }

    // limit
    @Test
    public void testLimit(){
        List<String> beforeCodes = students.stream().map(Student::getCode).collect(Collectors.toList());
        log.info("TestLimit 限制之前学生的学号为{}",JSON.toJSONString(beforeCodes));
        List<String> limitCodes = beforeCodes.stream().limit(2L).collect(Collectors.toList());
        log.info("TestLimit 限制最大限制2个学生的学号{}",JSON.toJSONString(limitCodes));
        //直接连起来写
        List<String> codes = students.stream().map(Student::getCode).limit(2L).collect(Collectors.toList());
        log.info("TestLimit 限制最大限制2个学生的学号{}",JSON.toJSONString(limitCodes));
    }
    // reduce
    //reduce 方法允许我们在循环里边叠加计算值
    public void testReduce(){
        // 计算一下学生的总分数
        Double sum = students.stream().map(Student::getScope).reduce((scope1,scope2)->scope1+scope2).orElse(0D);
        log.info("总成绩为{}",sum);
        // 第一个参数表示成绩的基数，会从100开始加
        Double sum1 = students.stream().map(Student::getScope).reduce(100D,(scope1,scope2)->scope1+scope2);
        log.info("总成绩为{}",sum1);
    }
    // findFirst
    //findFist 表示匹配到第一个满足条件的值就返回
    public void testFindFirst(){
        //存在空指针异常的风险
        Long id = students.stream().filter(student -> StringUtils.equals(student.getName(),"小美")).findFirst().get().getId();
        log.info("testFindFirst 小美同学的id {}",id);
        // 防止空指针
        Long id2 = students.stream().filter(student -> StringUtils.equals(student.getName(),"小天")).findFirst().orElse(new Student()).getId();
        log.info("testFindFirst 小天同学的id {}",id2);
        Optional<Student> student = students.stream().filter(s->StringUtils.equals(s.getName(),"小天")).findFirst();
        if (student.isPresent()){
            log.info("testFindFirst 小天同学的id {}",student.get().getId());
            return;
        }
        log.info("testFindFirst 找不到名为小天的同学");
    }

    // groupingBy && toMap
    // groupingBy 是能够根据字段进行分组,toMap是把List的数据格式转化成Map的格式
    @Test
    public void testListToMap(){
        // 学生根据名字进行分类
        Map<String,List<Student>> map1 = students.stream().collect(Collectors.groupingBy(Student::getName));
        log.info("testListToMap groupingBy 学生根据名字进行分类 result is Map<String,List<Student>> {}",JSON.toJSONString(map1));
        // 统计姓名重名的学生有哪些
        Map<String,Set<String>> map2 = students.stream().collect(Collectors.groupingBy(Student::getName,Collectors.mapping(Student::getCode,Collectors.toSet())));
        log.info("testListToMap groupingBy 统计姓名重名结果 is {}",JSON.toJSONString(map2));
        // 学生转化成学号为key 的map
        Map<String,Student> map3 = students.stream()
                // 第一个入参表示 map中的key 的值
                // 第二个参数表示 map中value的取值
                // 第三个入参表示，如果前后的 key 是相同的，是覆盖还是不覆盖，(s1,s2)->s1 表示不覆盖，(s1,s2)->s2 表示覆盖
                .collect(Collectors.toMap(s-> s.getCode(),s->s,(s1,s2)->s1));
        log.info("testListToMap groupingBy 学生转化成学号为 key 的 map result is{}", JSON.toJSONString(map3));

    }


}
