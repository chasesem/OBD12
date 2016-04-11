package zeolite.com.obd1.entity.account;

/**
 * Created by Zeolite on 16/4/10.
 */
public class CarOwnerInfo {

    private  int id;
    private  String userCode;
    private  String userName;
    private  String sex;
    private  String phone;
    private  String telephone;
    private  String province;
    private  String city;
    private  String area;
    private  String street;


    public CarOwnerInfo(int id, String userCode, String userName, String sex, String phone, String telephone, String province, String city, String area, String street) {
        this.id = id;
        this.userCode = userCode;
        this.userName = userName;
        this.sex = sex;
        this.phone = phone;
        this.telephone = telephone;
        this.province = province;
        this.city = city;
        this.area = area;
        this.street = street;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
