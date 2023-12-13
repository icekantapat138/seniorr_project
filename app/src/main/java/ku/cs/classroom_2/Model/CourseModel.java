package ku.cs.classroom_2.Model;

public class CourseModel {

    private String courseID;
    private String courseName;
    private String usernameID;
    private String latitude;
    private String longtitude;
    private String distance;
    private String status;

    public CourseModel(String courseID, String courseName, String usernameID, String latitude, String longtitude, String distance, String status) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.usernameID = usernameID;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.distance = distance;
        this.status = status;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUsernameID() {
        return usernameID;
    }

    public void setUsernameID(String usernameID) {
        this.usernameID = usernameID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
