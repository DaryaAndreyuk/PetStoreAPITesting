package models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Integer userStatus;

    public User() {
    }

    public User(Integer id, String username, String firstName, String lastName, String email, String password, String phone, Integer userStatus) {
        super();
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.firstName == null)? 0 :this.firstName.hashCode()));
        result = ((result* 31)+((this.lastName == null)? 0 :this.lastName.hashCode()));
        result = ((result* 31)+((this.password == null)? 0 :this.password.hashCode()));
        result = ((result* 31)+((this.userStatus == null)? 0 :this.userStatus.hashCode()));
        result = ((result* 31)+((this.phone == null)? 0 :this.phone.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.email == null)? 0 :this.email.hashCode()));
        result = ((result* 31)+((this.username == null)? 0 :this.username.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof User) == false) {
            return false;
        }
        User rhs = ((User) other);
        return (((((((((this.firstName == rhs.firstName)||((this.firstName!= null)&&this.firstName.equals(rhs.firstName)))&&((this.lastName == rhs.lastName)||((this.lastName!= null)&&this.lastName.equals(rhs.lastName))))&&((this.password == rhs.password)||((this.password!= null)&&this.password.equals(rhs.password))))&&((this.userStatus == rhs.userStatus)||((this.userStatus!= null)&&this.userStatus.equals(rhs.userStatus))))&&((this.phone == rhs.phone)||((this.phone!= null)&&this.phone.equals(rhs.phone))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.email == rhs.email)||((this.email!= null)&&this.email.equals(rhs.email))))&&((this.username == rhs.username)||((this.username!= null)&&this.username.equals(rhs.username))));
    }

}