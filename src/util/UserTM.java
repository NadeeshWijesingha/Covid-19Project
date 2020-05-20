package util;

import javafx.scene.control.Button;

public class UserTM {
    private String username;
    private String name;
    private Button delete;

    public UserTM() {
    }

    public UserTM(String username, String name, Button delete) {
        this.username = username;
        this.name = name;
        this.delete = delete;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "UserTM{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", delete=" + delete +
                '}';
    }
}
