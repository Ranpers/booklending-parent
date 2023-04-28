package pers.yiran.booklending.common;

/**
 * @author Yiran
 */

public enum AccessLevel {
    ALL(-1, "all"),
    READER(0, "reader"),
    EMPLOYEE(1, "employee"),
    ADMIN(2, "admin");

    final int code;
    final String msg;

    AccessLevel(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
