package animals;

import configs.MyConfig;
import entities.Cat;
import entities.Dog;
import entities.Parrot;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Cat cat = context.getBean(Cat.class);
        Dog dog = (Dog) context.getBean("getDog");
        Parrot parrot = context.getBean("parrot-kesha", Parrot.class);
        System.out.println(cat.getName());
        System.out.println(dog.getName());
        System.out.println(parrot.getName());
    }
}
