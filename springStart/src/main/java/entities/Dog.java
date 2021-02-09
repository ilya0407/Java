package entities;

import org.springframework.stereotype.Component;

@Component
public class Dog extends animal {
    private String name = "Тузик";

    @Override
    public String getName() {
        return name;
    }

}
