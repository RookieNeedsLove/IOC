package test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SimpleIOC {
    private Map<String,Object> beanMap = new HashMap<>();

    public SimpleIOC(String location) throws  Exception{
            loadBeans(location);
    }

    public Object getBean(String name){
        Object bean = beanMap.get(name);
        if(bean==null){
            throw new IllegalArgumentException("there is no bean with name" +name);
        }
        return bean;
    }

    private void loadBeans(String location)throws Exception{
        //加载xml配置文件  xml转成输入流
        InputStream inputStream = new FileInputStream(location);
        //抽象工厂 不能直接实例化 通过newInstance方法  创建工厂对象并返回
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //获得DOM解析器对象
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        //得到xml文档
        Document doc = docBuilder.parse(inputStream);
        //获得xml文档的的根节点
        Element root =doc.getDocumentElement();
        //得到节点的子节点
        NodeList nodes = root.getChildNodes();

        //便利<bean>标签
        for(int i = 0;i<nodes.getLength();i++){
            Node node = nodes.item(i);
            if(node instanceof Element){
                Element ele =(Element) node;
                String id =ele.getAttribute("id");
                String className = ele.getAttribute("class");
                //加载beanClass
                Class beanCalss = null;
                try{
                    beanCalss = Class.forName(className);
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                    return;
                }
                //加载bean
                Object bean = beanCalss.newInstance();
                //遍历<property> 标签
                NodeList propertyNodes = ele.getElementsByTagName("property");
                for(int j = 0;j<propertyNodes.getLength();j++){
                    Node propertyNode = propertyNodes.item(j);
                    if(propertyNode instanceof Element){
                        Element propertyElement = (Element) propertyNode;
                        String name = propertyElement.getAttribute("name");
                        String value = propertyElement.getAttribute("value");

                        //将反射bean相关的字段访问权限设为可访问
                        Field declareField = bean.getClass().getDeclaredField(name);
                        declareField.setAccessible(true);

                        if(value !=null &&value.length()>0){
                            //将属性值填充到相关字段中
                            declareField.set(bean,value);
                        }else {
                            String ref = propertyElement.getAttribute("ref");
                            if(ref ==null||ref.length()==0){
                                throw new IllegalArgumentException("rsf config error");
                            }
                            //将引用填充到字段中
                            declareField.set(bean,getBean(ref));
                        }
                        //bean注册到bean容器中
                        registerBean(id,bean);
                    }
                }

            }

        }



    }

    private void registerBean(String id,Object bean){
        beanMap.put(id,bean);
    }

}
