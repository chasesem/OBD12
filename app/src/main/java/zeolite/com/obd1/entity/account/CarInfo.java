package zeolite.com.obd1.entity.account;

/**
 * Created by Zeolite on 16/4/10.
 */
public class CarInfo {

    private int Id;
    private String BrandCode;
    private String BrandStyle;
    private String CCode;
    private String OCode;
    private String ProductYears;
    private String AnnualDate;
    private String Color;
    private String kilometers;
    private String Remark;


    public CarInfo() {
    }

    public CarInfo(int id, String brandCode, String brandStyle, String CCode, String OCode, String productYears, String annualDate, String color, String kilometers, String remark) {
        Id = id;
        BrandCode = brandCode;
        BrandStyle = brandStyle;
        this.CCode = CCode;
        this.OCode = OCode;
        ProductYears = productYears;
        AnnualDate = annualDate;
        Color = color;
        this.kilometers = kilometers;
        Remark = remark;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBrandCode() {
        return BrandCode;
    }

    public void setBrandCode(String brandCode) {
        BrandCode = brandCode;
    }

    public String getBrandStyle() {
        return BrandStyle;
    }

    public void setBrandStyle(String brandStyle) {
        BrandStyle = brandStyle;
    }

    public String getCCode() {
        return CCode;
    }

    public void setCCode(String CCode) {
        this.CCode = CCode;
    }

    public String getOCode() {
        return OCode;
    }

    public void setOCode(String OCode) {
        this.OCode = OCode;
    }

    public String getProductYears() {
        return ProductYears;
    }

    public void setProductYears(String productYears) {
        ProductYears = productYears;
    }

    public String getAnnualDate() {
        return AnnualDate;
    }

    public void setAnnualDate(String annualDate) {
        AnnualDate = annualDate;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
