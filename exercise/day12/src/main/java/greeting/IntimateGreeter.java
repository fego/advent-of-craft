package greeting;

class IntimateGreeter implements GreeterStrategy {

    @Override
    public String greet() {
        return "Hello Darling!";
    }

    @Override
    public boolean canGreet(Formality formality) {
        return Formality.INTIMATE.equals(formality);
    }
}
