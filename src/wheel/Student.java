package wheel;

public class Student {
    public static final String test1 = "final_test";
    public static String test2 = "static_test";
    static{
        System.out.println("init static");
    }
    private String name;
    private int id;
    private String grade;

    public static void test1()throws Exception{
        //获取Class对象的三种方式
        try{
            //使用Class.forName()方法载入的时候,需要类的全限定名
            Class<?> cc = Class.forName("wheel.Student");
            System.out.println(cc);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static void test2()throws Exception{
        //类字面常量
        Class<?> cc1=Student.class;
        System.out.println(cc1);
    }
    public static void test3()throws Exception{
        //对象getClass获得
        Student student = new Student();
        Class<?> cc2=student.getClass();
        System.out.println(cc2);
    }
    public static void main(String args[]) throws Exception {
        Student.test2();
    }
}
