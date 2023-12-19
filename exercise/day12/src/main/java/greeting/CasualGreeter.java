package greeting;

class CasualGreeter implements GreeterStrategy {

    @Override
    public String greet() {
        return "Sup bro?";
    }

    @Override
    public boolean canGreet(Formality formality) {
        return Formality.CASUAL.equals(formality);
    }
}
