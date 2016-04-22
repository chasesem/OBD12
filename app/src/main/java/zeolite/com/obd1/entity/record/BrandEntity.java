package zeolite.com.obd1.entity.record;

/**
 * Created by Zeolite on 16/4/20.
 */
public class BrandEntity {

    private int id;
    private String BrandCode;
    private String BrandName;
    private String BrandStyle;
    private String StyleName;
    private String Origin;
    private String Remark;

    public BrandEntity() {
    }

    public BrandEntity(int id, String brandCode, String brandName, String brandStyle, String styleName, String origin, String remark) {
        this.id = id;
        BrandCode = brandCode;
        BrandName = brandName;
        BrandStyle = brandStyle;
        StyleName = styleName;
        Origin = origin;
        Remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandCode() {
        return BrandCode;
    }

    public void setBrandCode(String brandCode) {
        BrandCode = brandCode;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getBrandStyle() {
        return BrandStyle;
    }

    public void setBrandStyle(String brandStyle) {
        BrandStyle = brandStyle;
    }

    public String getStyleName() {
        return StyleName;
    }

    public void setStyleName(String styleName) {
        StyleName = styleName;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
