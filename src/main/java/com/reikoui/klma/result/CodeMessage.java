package com.reikoui.klma.result;

public class CodeMessage {
    public static CodeMessage SUCCESS = new CodeMessage(0, "success");
    public static CodeMessage USER_EMPTY = new CodeMessage(101, "用户为空");
    public static CodeMessage PASSWD_ERROR = new CodeMessage(102, "密码错误");
    public static CodeMessage ID_REPETITION = new CodeMessage(103, "账号重复");
    public static CodeMessage NOT_LOGIN = new CodeMessage(104, "没有登录");
    public static CodeMessage BIND_ERROR = new CodeMessage(201, "参数校验异常：%s");
    public static CodeMessage FILE_EMPTY = new CodeMessage(301, "文件为空");
    public static CodeMessage PARA_INCOMPLETE = new CodeMessage(302, "参数不完整");

    public static CodeMessage SERVER_ERROR = new CodeMessage(-1, "服务端异常");


    private int code;
    private String msg;


    CodeMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMessage fillMessage(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMessage(code, message);
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
