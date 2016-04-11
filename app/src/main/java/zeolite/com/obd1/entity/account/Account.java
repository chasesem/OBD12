package zeolite.com.obd1.entity.account;

/**
 * Created by Zeolite on 16/4/10.
 */
public class Account {

    private String phoneNum;
    private boolean isLogin;

    public Account() {

    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}

