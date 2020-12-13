package cale.spring.Movies.authorization;

public class Authorized {

    private Boolean authorized;
    private String returnMessage;

    public Authorized() { }

    public Authorized(Boolean authorized, String returnMessage) {
        this.authorized = authorized;
        this.returnMessage = returnMessage;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    @Override
    public String toString() {
        return "auth{" +
                "authorized=" + authorized +
                ", returnMessage='" + returnMessage + '\'' +
                '}';
    }
}
