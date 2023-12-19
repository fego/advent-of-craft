package greeting;

import java.util.List;

public class Greeter {
    Formality formality;
    private static final List<GreeterStrategy> greeters = List.of(
            new IntimateGreeter(),
            new CasualGreeter(),
            new FormalGreeter(),
            new DefaultGreeter());

    public Greeter(Formality formality) {
        this.formality = formality;
    }

    public String greet() {
        return greeters.stream()
                .filter(greeterStrategy -> greeterStrategy.canGreet(formality))
                .findFirst()
                .map(GreeterStrategy::greet)
                .orElseThrow(() -> new RuntimeException("Greeter Strategy is Missing"));
    }

}
