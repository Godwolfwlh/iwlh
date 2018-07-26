package com.iwulh.iwulhdemo.utils;

public class Constants {

    // request参数
    public static final int REQ_QR_CODE = 11002; // // 打开扫描界面请求码
    public static final int REQ_PERM_CAMERA = 11003; // 打开摄像头

    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";

    public static final String USER_HTTP = "http://192.168.1.189:8080/SSMDemo/";

    public static final String USER_QUERBYNAME = USER_HTTP + "user/queryByUserNameInUlist.do";

    public static final String USER_GETBYNAME = USER_HTTP + "user/queryByUserNameInJson.do";

    public static final String USER_LOGIN = USER_HTTP + "user/login.do";

    public static final String JOURNALISM_ALL = USER_HTTP + "journalism/all/journalismAll.do";

    public static final String JOURNALISM_NEW = USER_HTTP + "journalism/new/journalismNew.do";
}
