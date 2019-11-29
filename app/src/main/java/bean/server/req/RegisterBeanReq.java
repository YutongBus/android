package bean.server.req;

/**
 * Created by john on 2019/4/27.
 */
public class RegisterBeanReq {
    private String userName;
    private String userTitle;
    private String contactName;
    private String telephoneNumber;
    private String officeTelephoneNumber;
    private String email;
    private String address;
    private String city;
    private String state;
    private String country;
    private String password;
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }
    public String getUserTitle() {
        return userTitle;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public String getContactName() {
        return contactName;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setOfficeTelephoneNumber(String officeTelephoneNumber) {
        this.officeTelephoneNumber = officeTelephoneNumber;
    }
    public String getOfficeTelephoneNumber() {
        return officeTelephoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
