package com.spshop.utils;

import java.io.Serializable;


/**
 * author Delgado
 */
@Deprecated
public class AllConstants {

	public static String JMAGICK_SYSTEM_PROPERTY = "jmagick.systemclassloader";
	public static String NO = "no";
	
	public static String ADMIN_LOGIN_INFO = "ADMIN_LOGIN_INFO";
	
	public static String HTTP_PROTOCOL = "http://";
	
	public static String SUCCESS_VALUE = "Success";
	public static String fAILURE_VALUE = "Failure";
	public static String SPECIAL_CATEGORY_VALUE = "Special";
	
	public static String URL_SEPERATOR = "/";
	public static String DOT = ".";
	public static String GREATER = "&gt;";
	public static String CATEGORY_URL = "c";
	public static String PRODUCT_URL = "product";
	public static String HOME_URL = "home";
	public static String KEYWORDS_URL = "keywords";
	public static String EMPTY_STR = "";
	
	//URL Parameters' name and Attributes' name
	public static String CATEGORY_ID = "categoryId";
	public static String START_INDEX = "startIndex";
	public static String PAGE_SIZE = "pageSize";
	public static String END_INDEX = "endIndex";
	public static String PAGE_NUM = "pageNum";
	public static String PROD_IN_CATEGORY_PAGE = "productsInCategoryPage";
	public static String PROD_COUNT = "productsCount";
	public static String ACTION = "action";
	public static String LOGIN_ACTION = "login";
	public static String LOGOUT_ACTION = "logout";
	public static String USER_INFO = "userInfo";
	public static String SUBSCRIBE_EMAL = "subscribeEmail";
	public static String PAGE_INDEX = "pageIndex";
	
	public static String CATEGORY_CACHE = "catetoryCache";
	public static String ORDER_CACHE = "orderCache";
	
	public static final String LOGIN_FAILURE = "username or password not correct, please check";
	
	public static final long DEFAULT_SITE_ID = 1L;
	public static final Serializable DEFAULT_SITE_CACHE = "DEFAULT_SITE_CACHE";
	public static final Serializable DEFAULT_TOPSELLING_CACHE = "DEFAULT_TOPSELLING_CACHE";
	public static final Serializable DEFAULT_TABSELLING_CACHE = "DEFAULT_TABSELLING_CACHE";
	
	//Email
	public static final String REGISTER_MAIL_HOST_NAME = "register.email.host.name";
	public static final String REGISTER_MAIL_FROM_ACCOUNT = "register.email.from.account";
	public static final String REGISTER_MAIL_FROM_PASSWORD = "register.email.from.password";
	public static final String REGISTER_MAIL_TITLE = "register.email.title";
	public static final String REGISTER_MAIL_CONTENT = "register.email.content";
	
	public static final String RECOVERY_MAIL_HOST_NAME = "recovery.email.host.name";
	public static final String RECOVERY_MAIL_FROM_ACCOUNT = "recovery.email.from.account";
	public static final String RECOVERY_MAIL_FROM_PASSWORD = "recovery.email.from.password";
	public static final String RECOVERY_MAIL_TITLE = "recovery.email.title";
	public static final String RECOVERY_MAIL_CONTENT = "recovery.email.content";
	
	public static final String DEFAULT_REGISTER_MAIL_TITLE = "Your account, in Honey-Buy, is created!";
	public static final String DEFAULT_REGISTER_MAIL_CONTENT = "Congratulations! You r the registered user of Honey-Buy!";
	public static final String DEFAULT_RECOVERY_MAIL_TITLE = "Your account, in Honey-Buy, is recoveried!";
	public static final String DEFAULT_RECOVERY_MAIL_CONTENT = "Congratulations! Your password is ...";
	
	public static final String DEFAULT_MAIL_CHARSET = "UTF-8";
    public static final String DEFAULT_MAIL_HOST_NAME = "smtpout.secureserver.net";
    public static final String DEFAULT_MAIL_FROM_ACCOUNT = "no-reply@honeybuy.com";
    public static final String DEFAULT_MAIL_FROM_PASSWORD = "20091125!@#A";
    
    public static final String MAIL_HOST_NAME = ".host.name";
    public static final String MAIL_FROM_ACCOUNT = ".from.account";
    public static final String MAIL_FROM_PASSWORD = ".from.password";
    public static final String MAIL_FROM_NAME = "HoneyBuy";
    
    //Gender
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    public static final String GENDER_OTHERS = "unknown";
    
    public static final String REQUEST_ERROR = "requestErrors";
    public static final String REQUEST_MSG = "requestMsg";
    public static final String DEFAULT_ORDER = "defaultOrder";
}
