package wheel;

import java.lang.reflect.Field;

public class Child implements Person {
    private String id;
    public String name;
    private String age;

    //构造函数1
    public Child() {

    }
    //构造函数2
    public Child(String id) {
        this.id = id;
    }
    //构造函数3
    public Child(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    //静态方法
    public static void update() {

    }

    @Override
    public void read() {

    }

    public static void main(String args[]){
        Class<?> cc =Child.class;
        //获得Child的属性 public的属性。包括父类的
        Field[] fields =cc.getFields();
        System.out.println(fields);
        for(Field field :fields){
            System.out.println(field);
        }
        System.out.println("\n");
    }
}
