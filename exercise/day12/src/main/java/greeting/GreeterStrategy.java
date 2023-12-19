package greeting;

interface GreeterStrategy {
    String greet();

    boolean canGreet(Formality formality);
}
