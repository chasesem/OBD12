package zeolite.com.obd1.entity.record;

/**
 * Created by Zeolite on 16/1/26.
 */
public class RecordEntity {
    private String time;
    private String currentmeil;
    private String fixtype;
    private String cost;
    private String fixitem;
    private String save;

    private String ratings;
    private String comment;

    public RecordEntity(String time, String currentmeil, String fixtype, String cost, String fixitem, String save, String ratings, String comment) {
        this.time = time;
        this.currentmeil = currentmeil;
        this.fixtype = fixtype;
        this.cost = cost;
        this.fixitem = fixitem;
        this.save = save;
        this.ratings = ratings;
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCurrentmeil() {
        return currentmeil;
    }

    public void setCurrentmeil(String currentmeil) {
        this.currentmeil = currentmeil;
    }

    public String getFixtype() {
        return fixtype;
    }

    public void setFixtype(String fixtype) {
        this.fixtype = fixtype;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getFixitem() {
        return fixitem;
    }

    public void setFixitem(String fixitem) {
        this.fixitem = fixitem;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
