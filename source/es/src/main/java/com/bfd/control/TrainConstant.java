package com.bfd.control;

public class TrainConstant {
	
	public static final String RET_CODE = "retCode";
	
	public static final String RET_DESC = "retDesc";

	public static final String TYPE_ANDROID = "android";
	
	public static final String TYPE_IPHONE = "iPhone";
	
	public static final int CHECK_PAY = 1;
	
	public static final int CHECK_TICKET_RETURN = 2;
	
	public static final int APP_ID_NTES = 1;
	
	public static final int APP_ID_LEDE = 2;
	
	public static final int PUSH_TYPE_IPHONE = 1;
	
	public static final int PUSH_TYPE_ANDROID = 2;
	
	public static final int CLIENT_TYPE_IPHONE = 3;
	
	public static final int CLIENT_TYPE_ANDROID = 4;
	
	public static final int FEEDBACK_CONTENT_MIN = 1;
	public static final int FEEDBACK_CONTENT_MAX = 300;
	
	public static final String TRAIN_ONLINE_DATE = "2013-12-23"; //火车票项目上线时间
	
	public static final String TRAIN_SEATTYPE_DATE = "2014-09-09"; //火车票项目上线时间
	
	public static final String VERSION = "version";//最新版本号
	
	public static final String MINORVER = "minorVer";//最低版本号
	
	public static final String INVER = "inver";//内部版本号
	
	public static final String DESC = "desc"; //版本更新的描述,描述以“|”分割，以表示1,2,3更新提示
	
	public static final String WAP_URL= "wapUrl";//客户端下载wap地址
	
	public static final String SERVER_SYS_TIME = "serverSysTime";//服务器当前系统时间。格式：当前系统时间的毫秒数，例如：1323075982564
	
	public static final String LOGIN_ID = "login_id";
	public static final String LOGIN_TOKEN = "login_token";
	public static final String SIGN = "sign";
	public static final String DEVICE_ID = "device_id";
	public static final String OLD_DEVICE_ID = "old_device_id";
	public static final String MOBILE_OS_TYPE = "mobile_os_type";
	public static final String PUSH_TOKEN = "push_token";
	public static final String MOBILE_OS_VERSION = "mobile_os_version";
	public static final String MOBILE_TYPE_VERSION = "mobile_type_version";
	public static final String CLIENT_VERSION = "client_version";
	public static final String ACCOUNT_ID = "account_id";
	public static final String CHANNEL = "channel";
	
	public static final String URS_ACCOUNT_ID = "ursAccountId";
	public static final String PUSH_TYPE = "pushType";
	public static final String PRODUCT = "product";
	public static final String PDT = "huoche";
	
	public static final int PARAM_MAX_LENGTH = 200;
	
	public static final int OK_CODE = 200;
	public static final String OK_DESC = "操作成功";
	
	public static final int ID_ALREADY_EXITS_CODE = 300;
	public static final String ID_ALREADY_EXITS_DESC = "ID已经存在";
	
	public static final int RULE_DISABLE_CODE = 301;
	public static final String RULE_DISABLE_DESC = "当前时间无法下单或退票";
	
	public static final int SYS_ERROR_CODE = 400;
	public static final String SYS_ERROR_DESC = "系统异常";
	
	public static final int SIGN_CHECK_FAIL_CODE = 401;
	public static final String SIGN_CHECK_FAIL_DESC = "签名校验失败";
	
	public static final int IDDATA_IS_UPDATING_CODE = 402;
	public static final String IDDATA_IS_UPDATING_DESC = "ID对应数据正在被更新中";
	
	public static final int MOBILE_LOGIN_CHECK_FAIL_CODE = 410;
	public static final String MOBILE_LOGIN_CHECK_FAIL_DESC = "登录校验失败";

	public static final int PARAM_ERROR_CODE = 460;
	public static final String PARAM_ERROR_DESC = "传入参数有误";
	
	public static final int MOBILE_OS_TYPE_ERROR_CODE = 481;
	public static final String MOBILE_OS_TYPE_ERROR_DESC = "mobile_os_type 参数错误";
	
	public static final int MOBILE_OS_VERSION_ERROR_CODE = 482;
	public static final String MOBILE_OS_VERSION_ERROR_DESC = "mobile_os_version 参数错误";
	
	public static final int MOBILE_TYPE_VERSION_ERROR_CODE = 483;
	public static final String MOBILE_TYPE_VERSION_ERROR_DESC = "mobile_type_version 参数错误";
	
	public static final int CONTENT_LENGTH_ERROR_CODE = 484;
	public static final String CONTENT_LENGTH_ERROR_DESC = "content 长度错误";

	public static final int CLIENT_VERSION_ERROR_CODE = 485;
	public static final String CLIENT_VERSION_ERROR_DESC = "client_version 参数错误";
	
	public static final int PASSENGER_DUPL_ERROR_CODE = 486;
	public static final String PASSENGER_DUPL_ERROR_DESC = "联系人重复";
	
	// 订单相关
	public static final int GORDER_ID_NOT_EXIST_CODE = 487;
	public static final String GORDER_ID_NOT_EXIST_DESC = "没有找到 gorder_id 对应的订单";
	
	public static final int ORDER_ID_NOT_EXIST_CODE = 488;
	public static final String ORDER_ID_NOT_EXIST_DESC = "没有找到 gorder_id对应的order_id";
	
	public static final int PAY_ENABLE_CODE = 489;
	public static final String PAY_ENABLE_DESC = "无法下单，票量过少，无法保证出票";
	
	public static final int CANCEL_ORDER_FAILED_CODE = 490;
	public static final String CANCEL_ORDER_FAILED_DESC = "取消订单失败";
	
	public static final int ORDER_ITEM_ID_NOT_EXIST_CODE = 491;
	public static final String ORDER_ITEM_ID_NOT_EXIST_DESC = "没有找到order_item_id对应的数据";
	
	public static final int TICKET_RETURN_DUPL_CODE = 492;
	public static final String TICKET_RETURN_DUPL_DESC = "退票请求重复提交";
	
	public static final int TIEYOU_TICKET_RETURN_ERROR_CODE = 493;
	public static final String TIEYOU_TICKET_RETURN_ERROR_DESC = "向合作方发送退票请求无响应";
	
	public static final int TIEYOU_TICKET_RETURN_ENABLE_CODE = 494;
	public static final String TIEYOU_TICKET_RETURN_ENABLE_DESC = "太晚了，无法受理此退票";
	
	public static final int TICKET_INFO_ERROR_CODE = 495;
	public static final String TICKET_INFO_ERROR_DESC = "此票信息有误，缺少坐席信息";
	
	public static final int USER_NOT_LOGIN_CODE = 496;
	public static final String USER_NOT_LOGIN_DESC = "用户未登录";
	
	public static final int TIEYOU_NOT_AVILABLE_CODE = 497;
	public static final String TIEYOU_NOT_AVILABLE_DESC = "合作方暂不支持";
	
	public static final int WYB_NOT_ACTIVE_CODE = 498;
	public static final String WYB_NOT_ACTIVE_DESC = "网易宝未激活";
	
	public static final int ACCOUNT_BIND_EXCEED_LIMIT_CODE = 500;
	public static final String ACCOUNT_BIND_EXCEED_LIMIT_DESC = "您的网易帐号绑定超过了系统的最大限制";
	
	public static final int ACCOUNT_UNBIND_FAIL_CODE = 501;
	public static final String ACCOUNT_UNBIND_FAIL_DESC = "解绑失败";
	
	public static final int ACCOUNT_UNBIND_STATUS_CODE = 502;
	public static final String ACCOUNT_UNBIND_STATUS_DESC = "帐号未绑定";
	
	public static final int TIEYOU_QUERY_CODE = 600;
	public static final String TIEYOU_QUERY_DESC = "铁友查询";
	
	public static final int KEFU_EXCEED_LIMIT_CODE = 601;
	public static final String KEFU_EXCEED_LIMIT_DESC = "12306可用但不可以后台支付";
	
	public static final int CANNOT_USE_12306_CODE = 602;
	public static final String CANNOT_USE_12306_DESC = "12306不可用";
	
	public static final int CANNOT_USE_TIEYOU_CODE = 603;
	public static final String CANNOT_USE_TIEYOU_DESC = "红包出票繁忙，请稍后再试。如情况紧急，请取消红包下单。";
	
	public static final int CANNOT_TICKET_NIGHT_CODE = 604;
	public static final String CANNOT_TICKET_NIGHT_DESC = "晚23点至早7点暂不可订票，请在其他时间订票。";
	
	public static final int DOWNLOAD_HIGH_VERSION_CODE = 605;
    public static final String DOWNLOAD_HIGH_VERSION_DESC = "请更新客户端至最新版本，进行订票";

	public static final int MAX_SHARE_LIMIT_CODE = 700;
	public static final String MAX_SHARE_LIMIT_DESC = "超过了最大分享次数";
	
	public static final int NOT_PLAY_GAME_CODE = 701;
	public static final String NOT_PLAY_GAME_DESC = "请玩后，再分享";

	public static final int NO_CHANCE_TO_PLAY_GAME_CODE = 702;
	public static final String NO_CHANCE_TO_PLAY_GAME_DESC = "你已经没有玩游戏的机会啦";
	
	public static final int  UN_START_PLAY_GAME_CODE = 703;
	public static final String UN_START_PLAY_GAME_DESC = "你还未开始玩游戏呢";
	
	
	public static final int  SYS_REJECT_COUPON_CODE = 704;
	public static final String SYS_REJECT_COUPON_DESC = "系统拒绝分发红包";
	
	public static final int  SHARE_ACTIVITY_ERROR_CODE = 705;
    public static final String SHARE_ACTIVITY_ERROR_DESC = "活动分享错误";
    
    public static final int NO_TRIP163_ACCOUNT_CODE = 706;
    public static final String NO_TRIP163_ACCOUNT_DESC = "非透明帐号";
	
	public static final int asyncTimeOut = 5000; //Servlet异步最大等待时间（毫秒）
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// 证件类型,type{1,2,3,4}对应name{"二代身份证", "港澳通行证", "台湾通行证", "护照"}
	public static final int ID = 0;
	public static final int HONGKONG = 1;
	public static final int TAIWAN = 2;
	public static final int PASSPORT = 3;
	public static final String[] IDNAME= {"二代身份证", "港澳通行证", "台湾通行证", "护照"};
	
	//乘客类型,type{1,2,3,4}对应name{"成人", "儿童", "学生", "残军"}
	public static final String[] PASSENGERTYPENAME= {"成人票", "儿童票", "学生票", "残军票"};
	
	//返回状态
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	
	
	//铁友接口
	public static final String OPERATION = "updateAction";
	public static final String OPERATION_PARAM_NAME = "updateActionRequest";
	public static final String ENCODING_STYLE = "http://schemas.xmlsoap.org/soap/encoding/";
	public static final String webServiceEndPoint = "http://dts.99pto.com/wsdl/server.php";
	public static final String TIEYOU_QUERY_URL = "http://data.99pto.com/index.php";
	
	//铁友订单接口ServiceName
	public static final String TIEYOU_REQUEST_addOrder = "order.addOrder";
	public static final String TIEYOU_REQUEST_payOrder = "order.pay";
	public static final String TIEYOU_REQUEST_cancelOrder = "order.cancelOrder";
	public static final String TIEYOU_REQUEST_refundNotify = "order.refundNotify";
	public static final String TIEYOU_REQUEST_payFail = "order.payFail";
	public static final String TIEYOU_REQUEST_orderDetail = "order.detail";
	public static final String TIEYOU_REQUEST_orderYuPiao = "order.yupiao";
	public static final String TIEYOU_REQUEST_ticketReturn = "order.ticketReturn";
	public static final String TIEYOU_REQUEST_orderRule = "order.rule";
	
	public static final String TIEYOU_RESPONSE_LockOrder = "web.order.lockOrder";
	public static final String TIEYOU_RESPONSE_NotifyNoTicket = "web.order.notifyNoTicket";
	public static final String TIEYOU_RESPONSE_NotifyTicket = "web.order.NotifyTicket";
	public static final String TIEYOU_RESPONSE_notifyTicket = "web.order.notifyTicket";
	public static final String TIEYOU_RESPONSE_returnTicketNotice = "web.order.returnTicketNotice"; // 退票结果通知
	public static final String TIEYOU_RESPONSE_requestRefund = "web.order.requestRefund"; // 退款通知
	public static final String TIEYOU_RESPONSE_notifyNoTicket = "web.order.notifyNoTicket"; // 无票通知
	public static final String TIEYOU_RESPONSE_cancelOrder = "web.order.cancelOrder";//取消订单通知
	public static final String TIEYOU_RESPONSE_setSuccess = "web.order.setSuccess";//订单签收通知
	
	//铁友订单接口返回的字段名
	public static final String TIEYOU_RESPONSE_ORDER_NUMBER = "OrderNumber"; //订单号
	public static final String TIEYOU_RESPONSE_DISCRIPTION = "Discription";
	public static final String TIEYOU_RESPONSE_STATUS = "Status";
	
	//返回给铁友的字段名常量
	public static final String RESPONSE_TIEYOU_ERROR_MSG = "ErrorMessage";
	public static final String RESPONSE_TIEYOU_SERVICE_NAME = "ServiceName";
	
	//铁友接口相关的其他常量
	public static final String TIEYOU_PARTNER_NAME = "wangyi";
	public static final String TIEYOU_PAY_TYPE = "epay";
	public static final String TIEYOU_CHANNEL_NAME = "wangyi";
	public static final String ENCODING = "utf-8";
	
	//铁友异步接口ID
	public static final int LOCK_ORDER = 0; // 订单锁定
	public static final int NOTIFY_TICKET = 1; // 有票通知
	public static final int NOTIFY_NO_TICKET = 2; // 无票通知
	public static final int REQUEST_REFUND = 3; // 差额退款通知
	public static final int RETURN_TICKET_NOTICE = 4; // 退票结果通知
	public static final int CANCEL_ORDER = 5; // 取消订单结果通知
	public static final int SET_SUCCESS = 6; // 有票通知后，会推送一个订单签收通知，不需要做处理，只需要返回成功即可
	public static final String[] PUSH_ACTION_MAP = {"web.order.lockOrder", "web.order.notifyTicket", "web.order.notifyNoTicket",
										"web.order.requestRefund", "web.order.returnTicketNotice", "web.order.cancelOrder", "web.order.setSuccess"};

	//订单模块中需要的订单状态
	public static final String PARTNER_PREPARE_DELIVER ="prepare"; // 订单锁定
	public static final String PARTNER_DELIVER_SUCCESS ="success"; // 有票通知
	public static final String PARTNER_DELIVER_FAIL ="fail"; // 无票通知
	public static final String PARTNER_DELIVER_PARTIAL_SUCCESS ="partialSuccess"; // 差额退款
	public static final String PARTNER_REFUND_ORDER="refundOrder"; // 无票通知
	public static final String PARTNER_CANCEL_ORDER="cancelOrder"; // 退票结果
	public static final int TICKET_RETURN_UNHANDLED = 0; // 退票未受理
	public static final int TICKET_RETURN_HANDLING = 1; // 正在受理
	public static final int TICKET_RETURN_SUCCEED = 2; // 退票成功
	public static final int TICKET_RETURN_FAILED = 3; // 退票失败
	public static final int TICKET_RETURN_ENABLE = 4; // 无法退票
	public static final int CAN_REFUND = 1; // 可以退款

	//订单相关模块
	public static final int DEFAULT_PAGE_NO = 1;
	public static final int DEFAULT_PAGE_SIZE = 20;
	public static final int TICKET_TYPE_ADULT = 1; // 成人票
	public static final int TICKET_TYPE_CHILD = 2; // 儿童票
	
	//赠险相关
	public static final int INSURANCE_INIT = 0;
	public static final int INSURANCE_SUCCEED = 1;
	public static final int INSURANCE_FAILED = 2;
	
	//本地订单相关变量
	public static final String SELLER_NICKNAME = "网易火车票";
	public static final String GOODS_NAME = "火车票";
	
	//向用户发的短信类型
	public static final int MESSAGE_PAY_SUCESS = 1;
	public static final int MESSAGE_NOTIFY_TICKET = 2;
	public static final int MESSAGE_RETURN_TICKET = 3;
	public static final int MESSAGE_NO_TICKET = 4;
	public static final int MESSAGE_REQUEST_REFUND = 5;
	//向买票金额大于50元的用户发送易道用车的优惠券
	public static final int MESSAGE_YIDAO_COUPON = 6;
	public static final int MESSAGE_LOTTERY_COUPON = 7;
	
	public static final String insuranceURL = "http://baoxian.163.com/zengxian/exfetchZengxian.html";
	
	//火车票优惠券相关
	public static final int COUPON_CODE_INVALID = 0;
	public static final int COUPON_CODE_VALID = 1;
	public static final int COUPON_CODE_EXCHANGED = 2;
	public static final int COUPON_CODE_INUSE = 3;
	public static final int COUPON_CODE_EXPIRED = 4;
	
	//火车票免单相关
	public static final int FREE_AMOUNT_UPPER = 100;
	public static final int FREE_COUNT_LIMIT = 10;
	
	//易信出票相关
	public static final String YIXIN_ORIGIN = "yixin_origin";
	public static final String YIXIN_CACHE_KEY = "yixin_cache_20140522";
	
	//缓存相关
	public static final String deliveredOrderToken = "deliveredOrder";
	
	//铁友相关
	public static final String TIEYOU_NAME = "tieyou";
		
	//Trip163相关
	public static final String TRIP163_PARTNER_NAME = "trip163";
	public static final int CANCEL_Trip163ORDER_FAILED_CODE =603 ;
	public static final String CANCEL_Trip163ORDER_FAILED_DESC = "取消合作方订单失败";
	public static final int TRIP163ORDER_PAGE_SIZE = 100;
	
	//trip163异步通知请求
	public static final String TRIP163_REQUEST_NOTIFYTICKET = "web.order.notifyTicket";	//后台支付成功有票通知
	public static final String TRIP163_REQUEST_REQUESTREFUND = "web.order.requestRefund"; //退款通知
	public static final String TRIP163_REQUEST_CANCELORDER = "web.order.cancelOrder"; //取消订单通知
	
	//客服操作员表相关
	public static final int OPERATOR_STATUS_INIT = 0;//初始化状态
    public static final int OPERATOR_STATUS_LOGIN = 1;//登陆状态
    public static final int OPERATOR_STATUS_OPERAT = 2;//操作装态
    public static final int OPERATOR_STATUS_LOGOUT = 3;//登出状态
    public static final int OPERATOR_STATUS_BUSY = 4;//繁忙，离开状态
    public static final int AUTHENTICATION_INIT = 0;//待审核
    public static final int AUTHENTICATION_SUCCESS = 1;//审核通过
    public static final int AUTHENTICATION_FAILED = 2;//审核未通过
	
	//getSaveAmount接口中orderStatus相关
	public static final int PAY_UNSUCCESSED = 0;
	public static final int PAY_SUCCESSED = 1;
	
	//携程酒店相关
	//携程酒店查询首页
	public static final String CTRIP_HOTEL_URL = "http://u.ctrip.com/union/CtripRedirect.aspx?TypeID=636&sid=457642&allianceid=23133&ouid=huochepiao";
	//携程酒店查询首页(代入城市和入住日期)
	public static final String CTRIP_HOTEL_URL_WITHARGS = "http://u.ctrip.com/union/CtripRedirect.aspx?TypeID=648&sid=457642&allianceid=23133&ouid=huochepiao&sourceid=";
	//携程城市酒店列表页
	public static final String CTRIP_CITYHOTEL_List = "http://u.ctrip.com/union/CtripRedirect.aspx?TypeID=12&AllianceID=23133&SID=457642&Ouid=&Days=&Sales=&BrandID=&Query=&Price=&Star=";
	
	//改变红包有效时间分割日期
	public static final String COUPON_FLAG_TIME = "2014-09-04 00:00:00";
	//红包兑换码长度
	public static final int RECEIVECODE_LENGTH = 12;
	
	//内部员工发放红包ID
	public static final int STAFF_COUPON_ID = 1005;
	
	//与获取邮箱内容接口约定的DES加解密秘钥
	public static final String DES_KEY = "Ljk7yXunWllMEJ3";
	//火车票推送产品代号
	public static final String PUSHMAIL_TARGET = "Netease_train";
	
	// 票操作类型,type{0,1,2,3}对应 {"出票成功", "改签",, "退票" "其他"}
	public static final int ORDER_SUCCESS = 0;
	public static final int ORDER_EXCHANGE= 1;
	public static final int ORDER_RETURN = 2;
	public static final int ORDER_ELSE = 3;
	public static final String[] orderOperatorType={"出票成功", "改签", "退票", "其他"};
	
	// 云抢票查询方案增量缓存key
	public static final String CLOUD_QIANGPIAO_QUERY_SOLUTION_KEY = "CLOUD_QIANGPIAO_QUERY_SOLUTION_NUM";
	// 云抢票上传余票信息增量缓存key
	public static final String CLOUD_QIANGPIAO_UPLOAD_YUPIAO_KEY = "CLOUD_QIANGPIAO_UPLOAD_YUPIAO_NUM";
	// 云抢票抢票成功增量缓存key
	public static final String CLOUD_QIANGPIAO_SUCC_KEY = "CLOUD_QIANGPIAO_SUCC_NUM";
	// 云抢票抢票次数增量缓存key
	public static final String CLOUD_QIANGPIAO_KEY = "CLOUD_QIANGPIAO_NUM";
	// 云抢票下单成功订单数增量缓存key
	public static final String CLOUD_QIANGPIAO_ADDORDER_SUCC_KEY = "CLOUD_QIANGPIAO_ADDORDER_SUCC_NUM";
	
	// 城市车站信息缓存token
	public final static String TRAIN_CITY_STATION_TOKEN = "train_city_station:";
	
	//主账号标识
	public final static String MASTER_FLAG = "Y";
	//改签新票标识
	public final static int EXCHANGE_SUCCESS_STATUS = 4;
	
	//退票险理赔状态
	//0初始状态，1为理赔成功，2为需要审核状态，3为审核通过，4为审核不通过,5表示不通过并加入黑名单
	public final static String TICKET_RETURN_INSUR_UN_SETTLE = "0";
	public final static String TICKET_RETURN_INSUR_SETTLED = "1";
	public final static String TICKET_RETURN_INSUR_NEED_APPROVE = "2";
	public final static String TICKET_RETURN_INSUR_APPROVED = "3";
	public final static String TICKET_RETURN_INSUR_UN_APPROVED = "4";
	public final static String TICKET_RETURN_INSUR_BLACKLIST = "5";
	//黑名单表中的status,0表示为真正加入黑名单，1表示加入了黑名单
	public final static String IS_BLACK_LIST = "1";
	public final static String IS_NOT_BLACK_LIST = "0";
	
	// 访问平台类型
	public final static int APP_TYPE_ANDROID = 10;
	public final static int APP_TYPE_IPHONE = 20;
	public final static int APP_TYPE_WEB = 30;
	
	// 车次类型
	public static final String[] TRAIN_TYPE={"C", "D","G", "K","L", "M", "P", "O","T","Y","Z"};
    //席别类型
	public static final String[] TRAIN_SEAT_TYPE={"硬座", "硬卧上","硬卧中", "硬卧","软座", "软卧上", "软卧中", "软卧","商务座","观光座","一等包座","特等座","一等座","二等座","高级软卧上","高级软卧","其他"};
    
    
    
    
}
