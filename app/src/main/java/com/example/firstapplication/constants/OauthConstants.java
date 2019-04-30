package com.example.firstapplication.constants;

public class OauthConstants {
    public static final String CLIENT_ID = "ee9692edff6b60f43361a074070ff9c35e8b3f89";
    public static final String SCOPE = "read_qiita";
    public static final String CLIENT_SECRET = "66537bd50a619a684571b5fce2467ae661a1aa30";
    public static final String HOST = "https://qiita.com/api/v2/";
    public static final String AUTH_URL
            = HOST + "oauth?client_id=" + CLIENT_ID + "&scope=" + SCOPE;
    public static final String TOKEN_URL = HOST + "access_tokens";
}


