package db.entity;

public class GradeBook {
    private Course course;
    private User user;
    private int grade;

    public GradeBook() {
    }

    public GradeBook(Course course, User user, int grade) {
        this.course = course;
        this.user = user;
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "GradeBook{" +
                "course=" + course +
                ", user=" + user +
                ", grade=" + grade +
                '}';
    }
}
