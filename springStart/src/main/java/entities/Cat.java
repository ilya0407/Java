package entities;

import org.springframework.stereotype.Component;

@Component
public class Cat extends animal{
    private String name = "Барсик";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
