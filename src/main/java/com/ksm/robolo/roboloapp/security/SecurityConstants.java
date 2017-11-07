package com.ksm.robolo.roboloapp.security;

public class SecurityConstants {
	public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    
    //REGISTRATION AND PASSWORD
    public static final String SIGN_UP_URL = "/register";
    public static final String REGISTRATION_CONFIRM_URL = "/confirm/**";
    public static final String PASSWORD_RETRIEVE_URL = "/password/retrieve/**";
    
    //SWAGGER
    public static final String SWAGGER_UI_URL = "/swagger-ui.html/**";
    public static final String SWAGGER_RESOURCES_URL = "/swagger-resources/**";
    public static final String SWAGGER_API_DOCS_URL = "/v2/api-docs/**";
    public static final String SWAGGER_FAVICON_URL = "/**/favicon.ico";
    public static final String SWAGGER_WEBJARS_URL = "/webjars/**";
    
    //H2
    public static final String H2_URL = "/h2-console/**";
}
