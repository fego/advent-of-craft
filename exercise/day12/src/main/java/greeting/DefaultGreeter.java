package greeting;

class DefaultGreeter implements GreeterStrategy {

    @Override
    public String greet() {
        return "Hello.";
    }

    @Override
    public boolean canGreet(Formality formality) {
        return true;
    }
}
