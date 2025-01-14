package io.github.erwin.taskify_api.model;


import jakarta.persistence.*;

@Entity
@Table(
        name = "UserInfo",
        indexes = {
                @Index(name = "idx_location", columnList = "Location")
        }
)
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String FullName;
    private String Dob;
    private String Location;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
