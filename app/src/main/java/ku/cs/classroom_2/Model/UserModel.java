package ku.cs.classroom_2.Model;

public class UserModel {

    private String email;
    private String fullname;
    private String password;
    private String role;

    public UserModel(String email, String fullname, String password, String role) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
