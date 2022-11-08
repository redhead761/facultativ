package db.entity;

public enum Role {
    ADMIN("admin"),
    TEACHER("teacher"),
    STUDENT("student");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
