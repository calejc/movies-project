package cale.spring.Movies.authorization;

import java.util.ArrayList;
import java.util.List;

public class Authorized {

    private Boolean create, update, delete;
    private String returnMessage;

    public Authorized() { }

    public Authorized(Boolean create, Boolean update, Boolean delete, String returnMessage) {
        this.create = create;
        this.update = update;
        this.delete = delete;
        this.returnMessage = returnMessage;
    }

    public Boolean getCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage() {
        List<String> authActions = new ArrayList<>();
        if (this.create){
            authActions.add("create");
        }
        if (this.update){
            authActions.add("update");
        }
        if (this.delete){
            authActions.add("delete");
        }
        if (!this.create && !this.update && !this.delete){
            this.returnMessage = "Not authorized to perform any database transactions";
        } else {
            this.returnMessage = String.format("Authorized to perform %s actions only", authActions);
        }
    }

    @Override
    public String toString() {
        return "Authorized{" +
                "create=" + create +
                ", update=" + update +
                ", delete=" + delete +
                ", returnMessage='" + returnMessage + '\'' +
                '}';
    }
}
