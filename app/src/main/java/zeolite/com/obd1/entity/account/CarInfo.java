package zeolite.com.obd1.entity.account;

/**
 * Created by Zeolite on 16/4/10.
 */
public class CarInfo {

    private int id;
    private String code;
    private String bCode;
    private String productYear;
    private String annualDate;
    private String color;
    private String remark;


    public CarInfo() {
    }

    public CarInfo(int id, String code, String bCode, String productYear, String annualDate, String color, String remark) {
        this.id = id;
        this.code = code;
        this.bCode = bCode;
        this.productYear = productYear;
        this.annualDate = annualDate;
        this.color = color;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getbCode() {
        return bCode;
    }

    public void setbCode(String bCode) {
        this.bCode = bCode;
    }

    public String getProductYear() {
        return productYear;
    }

    public void setProductYear(String productYear) {
        this.productYear = productYear;
    }

    public String getAnnualDate() {
        return annualDate;
    }

    public void setAnnualDate(String annualDate) {
        this.annualDate = annualDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
