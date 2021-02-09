package entities;

import org.springframework.stereotype.Component;

@Component("parrot-kesha")
public class Parrot extends animal {
    private String name = "Кеша";

    @Override
    public String getName() {
        return name;
    }
}
