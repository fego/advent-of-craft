package greeting;

class FormalGreeter implements GreeterStrategy {

    @Override
    public String greet() {
        return "Good evening, sir.";
    }

    @Override
    public boolean canGreet(Formality formality) {
        return Formality.FORMAL.equals(formality);
    }
}
