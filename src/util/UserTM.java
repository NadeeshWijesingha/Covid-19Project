package util;

public class UserTM {

    private String name;
    private String contact;
    private String mail;
    private String username;
    private String password;
    private String role;
    private String selection;

    public UserTM() {
    }

    public UserTM(String name, String contact, String mail, String username, String password, String role, String selection) {
        this.name = name;
        this.contact = contact;
        this.mail = mail;
        this.username = username;
        this.password = password;
        this.role = role;
        this.selection = selection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    @Override
    public String toString() {
        return "UserTM{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", mail='" + mail + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", selection='" + selection + '\'' +
                '}';
    }
}
