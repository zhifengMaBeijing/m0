package com.bfd.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.MidiDevice.Info;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.codec.binary.StringUtils;
import org.mortbay.util.ajax.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.netease.common.util.HttpUtil;
import com.netease.common.util.MD5Util;
import com.netease.common.util.PropertyUtil;
import com.netease.common.util.XStreamUtil;
import com.netease.module.constant.LogonStatus;
import com.netease.module.util.Config;
import com.netease.module.util.JSPHelper;
import com.netease.module.util.ValidatorUtil;
import com.netease.shop.constant.order.OrderConstant;
import com.netease.shop.entity.order.Order;
import com.netease.shop.entity.order.OrderItem;
import com.netease.shop.service.gorder.IGeneralOrderService;
import com.netease.train.cache.ActivityCache;
import com.netease.train.cache.SMSIPCache;
import com.netease.train.cache.TicketBookingCache;
import com.netease.train.cache.WebPageCache;
import com.netease.train.constant.CommonConstant;
import com.netease.train.constant.LogConstant;
import com.netease.train.constant.TrainConstant;
import com.netease.train.dao.RobTicketDao;
import com.netease.train.dao.StartupPicDao;
import com.netease.train.dao.SubscriptionDao;
import com.netease.train.dao.TicketBookingDao;
import com.netease.train.dao.TrainIniDao;
import com.netease.train.dao.TripMallConfigDao;
import com.netease.train.init.InitBean;
import com.netease.train.model.ActivityNote;
import com.netease.train.model.ActivityPrizeInfoModel;
import com.netease.train.model.ClientPassenger;
import com.netease.train.model.HuocheBackendOrderMiscEntity;
import com.netease.train.model.HuocheBackendOrderMiscEntity.contactInfo;
import com.netease.train.model.HuocheBackendOrderMiscEntity.orderInfo;
import com.netease.train.model.Award;
import com.netease.train.model.InsuranceClientPassenger;
import com.netease.train.model.OtherProductCoupon;
import com.netease.train.model.ReceiveCode;
import com.netease.train.model.RobTicketRecord;
import com.netease.train.model.SimpleClientPassenger;
import com.netease.train.model.StartupPic;
import com.netease.train.model.TopNav;
import com.netease.train.model.TripMallEntity;
import com.netease.train.result.ResultInfo;
import com.netease.train.result.ResultInfoObject;
import com.netease.train.service.ClientPassengerService;
import com.netease.train.service.InsuranceService;
import com.netease.train.service.OtherCouponService;
import com.netease.train.service.PrizeService;
import com.netease.train.service.ReceiveCodeService;
import com.netease.train.service.RobTicketRecordService;
import com.netease.train.service.TicketBookingService;
import com.netease.train.util.AES;
import com.netease.train.util.CityTrainStation;
import com.netease.train.util.CtripHotelCityData;
import com.netease.train.util.HelpUtil;
import com.netease.train.util.PropertyUtils;
import com.netease.train.util.RequestUtil;
import com.netease.train.util.TrainUtil;
import com.netease.util.JniAesCbcEncrypt;

import freemarker.template.utility.StringUtil;

@Controller
public class WebPageController extends BaseController {
	
	@Resource
	StartupPicDao startupPicDao = null;
	
	@Resource
	SubscriptionDao subscriptionDao = null;
	
	@Resource
	TicketBookingService ticketBookingService = null;
	
	@Resource
	PrizeService prizeService = null;
	
	@Resource
	ActivityCache activityCache = null;
	
	@Resource
	TicketBookingCache ticketBookingCache = null;
	
	@Resource
    private SMSIPCache smsIPCache = null;
	
	@Resource
	ReceiveCodeService receiveCodeService = null;
	
	@Resource
	OtherCouponService otherCouponService;
	
	@Resource
	private ClientPassengerService clientPassengerService;
	
	@Resource
	private TripMallConfigDao tripMallConfigDao;
	
	@Resource
	private WebPageCache webPageCache;
	
	@Resource
	private IGeneralOrderService generalOrderService;
	
	@Resource
	private InsuranceService insuranceService;
	
	@Resource(name = "trainIniDao")
	private TrainIniDao trainIniDao;
	
	@Resource
	RobTicketDao robTicketDao;
	
	@Resource
	RobTicketRecordService robTicketRecordService;
	
	@Resource
	private TicketBookingService ticketBookService;
	private static final String TICKET_BOOKING = "ntes_ticket_booking";
	
	private static final String PRIZE_MAIL_CLUB = "@huoche_prize_mail_club";
	private static final String PRIZE_MAIN = "@huoche_prize";
	private static final String WARN_WAP_MSG = "announcement";
	
	
	private static final int STARTUP_PIC_CACHE_TIME = 1 * 24 * 60 * 60;
	
	@RequestMapping("/huoche/wap/banner/tuipiaoxian.html")
	public void tuipiaoxian(HttpServletRequest request,HttpServletResponse response) {
		try{
			response.sendRedirect("http://baoxian.163.com/m/train/ioslow.html");
		}catch(Exception ex){
			
		}
	}
	
	/**
	 * 开机弹窗
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/huoche/activity/note")
	@ResponseBody
	public ActivityNote activityNote(HttpServletRequest request,HttpServletResponse response) {
		ActivityNote note = new ActivityNote();
		String activityImgUrl = InitBean.getIniStringValue("activity_img_url");
		String activity_jump_url = InitBean.getIniStringValue("activity_jump_url");
		int  activityId = InitBean.getIniIntValue("activity_id");
		if(Strings.isNullOrEmpty(activityImgUrl)
				||Strings.isNullOrEmpty(activityImgUrl)
				||activityId ==0){
			note.setRetcode(TrainConstant.SYS_ERROR_CODE);
			note.setRetdesc(TrainConstant.SYS_ERROR_DESC);
			return note;
		}
		if(Strings.isNullOrEmpty(activityImgUrl.trim())||
				Strings.isNullOrEmpty(activityImgUrl.trim())){
			note.setRetcode(TrainConstant.SYS_ERROR_CODE);
			note.setRetdesc(TrainConstant.SYS_ERROR_DESC);
			return note;
		}
		
		note.setImgUrl(activityImgUrl);
		note.setJumpUrl(activity_jump_url);
		note.setId(activityId);
		note.setRetcode(TrainConstant.OK_CODE);
		note.setRetdesc(TrainConstant.OK_DESC);
		return note;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/huoche/index_pic.shtml")
	@ResponseBody
	public ResultInfoObject fetchIndexPic(HttpServletRequest request,
			HttpServletResponse response) {

		int mobileType = -1;
		int width = -1;
		int height = -1;
		String mobileTypeStr = request.getParameter("mobile_type");
		String clientVersion = request.getParameter("client_version");
		String widthStr = request.getParameter("pic_width");
		String heightStr = request.getParameter("pic_height");

		ResultInfoObject resultInfoObject = new ResultInfoObject();
		if (Strings.isNullOrEmpty(mobileTypeStr)
				|| Strings.isNullOrEmpty(clientVersion)) {
			resultInfoObject.setRetcode(TrainConstant.PARAM_ERROR_CODE);
			resultInfoObject.setRetdesc(TrainConstant.PARAM_ERROR_DESC);
			return resultInfoObject;
		}
		if (!Strings.isNullOrEmpty(mobileTypeStr)
				&& mobileTypeStr.equalsIgnoreCase("iphone")) {
			mobileType = 2;
		} else {
			mobileType = 1;
		}
//		String key = mobileType+clientVersion;
		try {
//			boolean isExist = activityCache.isExists(key);
//			LogConstant.runLog.info("key:"+key+"isExist:"+isExist);
//			if (isExist) {
//				resultInfoObject = (ResultInfoObject) activityCache
//						.getEntity(key);
//				
//			}else {
				if (!Strings.isNullOrEmpty(widthStr)) {
					width = Integer.parseInt(widthStr);
				}
				if (!Strings.isNullOrEmpty(heightStr)) {
					height = Integer.parseInt(heightStr);
				}

				Map paramMap = new HashMap();
				paramMap.put("mobileType", mobileType);
				paramMap.put("clientVersion", clientVersion);
				if (width != -1 & mobileType == 2) {
					paramMap.put("width", width);
				}
				if (height != -1 & mobileType == 2) {
					paramMap.put("height", height);
				}

				Timestamp nowtime = Timestamp.valueOf(TrainUtil
						.getDateTimeNow());
				List<StartupPic> resultList = new ArrayList<StartupPic>();
				List<StartupPic> startupList = startupPicDao
						.queryAllStartupList(paramMap);
				if (startupList != null && startupList.size() > 0) {
					for (StartupPic startupPic : startupList) {
						String startDate = startupPic.getStartDate();
						String endDate = startupPic.getEndDate();
						Timestamp startTime = Timestamp.valueOf(startDate);
						Timestamp endTime = Timestamp.valueOf(endDate);
						int low = startTime.compareTo(nowtime);
						int high = endTime.compareTo(nowtime);
						if (low <= 0 && high >= 0) {
							resultList.add(startupPic);
						}
					}
				}

				resultInfoObject.setRetcode(TrainConstant.OK_CODE);
				resultInfoObject.setRetdesc(TrainConstant.OK_DESC);
				resultInfoObject.setObject(resultList);
//				activityCache.putEntity(key, resultInfoObject, true,STARTUP_PIC_CACHE_TIME);
//			}
			return resultInfoObject;
		} catch (Exception e) {
			LogConstant.runLog.warn("获取开机图片列表出错了", e);
			resultInfoObject.setRetcode(TrainConstant.SYS_ERROR_CODE);
			resultInfoObject.setRetdesc(TrainConstant.SYS_ERROR_DESC);
			return resultInfoObject;
		}
	}
	
	/**
	 * 活动页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/web/activity")
	public String couponActivity(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {
		
		modelMap.put("iosDownloadLink", PropertyUtil.getProperty("iosDownloadLink"));

		setCommonVar(modelMap, true, "/huoche/web/activity.do");
		// 取赠送现金总额
//		int totalAmount = receiveCodeService.queryTotalAmount();
//		totalAmount = (int) (totalAmount * 7.3);
//		String totalAmountStr = String.valueOf(totalAmount);
//		List<String> totalAmountList = new ArrayList<String> ();
//		int zeroNum = 0;
//		if (totalAmountStr.length() < 8) {
//		    zeroNum = 8 - totalAmountStr.length();
//		}
//		for(int i=0;i<zeroNum;i++) totalAmountList.add("0");
//		for(int j=0;j<8-zeroNum;j++) totalAmountList.add(String.valueOf(totalAmountStr.charAt(j)));
//		modelMap.put("totalAmountList", totalAmountList);
		
		// 送现金文案
		List<String> rules = new ArrayList<String>();
		rules.add("所有用户每天可获得3次免费游戏机会，买票或分享可额外获得游戏机会，100%中奖");
		rules.add("中奖奖品请在客户端【个人中心】-【我的优惠】中查收");
		rules.add("活动时间：2014年11月20日-2015年3月1日");
		modelMap.put("rules", rules);

		return "web/activity/scratchHaze20141120";
	}
	
	@RequestMapping("/huoche/wap/activity/share/scratchHaze.html")
	public String wapScratchHaze(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {
		
		setCommonVar(modelMap,false,"");
		modelMap.put("iosDownloadLink", PropertyUtil.getProperty("iosDownloadLink"));
		
		
		// 送现金文案
		List<String> rules = new ArrayList<String>();
		rules.add("所有用户每天可获得3次免费游戏机会，买票或分享可额外获得游戏机会，100%中奖");
		rules.add("中奖奖品请在客户端【个人中心】-【我的优惠】中查收");
		rules.add("活动时间：2014年11月20日-2015年3月1日");
		modelMap.put("rules", rules);
//		return "web/activity/1yuanTicket20140718/main";
//		return "web/activity/trainRun20140917";
		return "wap/activity/scratchHaze20141120";
	}
	
	@RequestMapping("/huoche/wap/insurance")
	public String insurance(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {

		setCommonVar(modelMap,false,"");

		return "wap/doc/insurance";
	}
	
	@RequestMapping("/huoche/wap/daigou.html")
	public String weixinDaigou(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {

		setCommonVar(modelMap,false,"");
		
		return "wap/doc/daigou";
	}
	
	/**
	 * 春运抢票wap页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/wap/newYearTicket.html")
	public String springFesitivalTicket(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {

		setCommonVar(modelMap,false,"");
		
		return "wap/activity/springFesitival";
	}
	
	
	@RequestMapping("/huoche/wap/newyearticket.html")
	public String springfesitivalticket(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {

		setCommonVar(modelMap,false,"");
		
		return "wap/activity/springFesitival";
	}
	
	/**
	 * 告警栏通用页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/wap/warn.html")
	public String generalWarnMsg(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {

		setCommonVar(modelMap, false, "");
		String announcement = null;
		// 获取常规Ini配置项内容
		List<String> iniList = trainIniDao.getIniByPrefix(WARN_WAP_MSG);
		if (iniList != null) {
			announcement = iniList.get(0);
			//System.out.println("announcement:" + announcement);
		}
		modelMap.put("announcement", announcement);

		return "wap/announcement";
	}
	
	/**
	 * 扫描兑换码跳到的wap页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/wap/partner/coupon.html")
	public String partnerCoupon(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {

		setCommonVar(modelMap,false,"");
		modelMap.put("code", request.getParameter("code"));
		
		return "wap/app/coupon";
	}
	
	/**
	 * 保险红包合作页面入口
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/partner/baoxian/coupon.html")
	public String baoxianCouponMain(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {
		
		String queryString = request.getQueryString();
		if(Strings.isNullOrEmpty(queryString) || !queryString.contains("sign")){
			queryString = "";
			for(String key: request.getParameterMap().keySet()){
				String value = request.getParameter(key);
				if(!Strings.isNullOrEmpty(value)){
					if(queryString.length()>1) queryString += "&";
					queryString += key  + "=" + value;
				}
			}
		}
		
		String userAgent = request.getHeader("User-Agent");
		LogConstant.runLog.info("保险页面ua:"+ userAgent + "请求参数：" + queryString);
		if(userAgent!=null){
			userAgent=userAgent.toLowerCase();
			if(userAgent.contains("android") || userAgent.contains("mac os")){
				try{
//					response.sendRedirect("http://baoxian.163.com/m/tripiframe.html?" + queryString);
					return "redirect:http://baoxian.163.com/m/tripiframe.html?" + queryString;
				}catch(Exception ex){
					LogConstant.runLog.info("重定向到保险的wap页面失败");
				}
			}
		}
		
		setCommonVar(modelMap,false,"");
		modelMap.put("iframeLink", "http://baoxian.163.com/zengxian/tripiframe.html?" + queryString );
		List<String> rules = new ArrayList<String>();
		rules.add("参与活动即可获得 5—100元网易火车票红包，买票直减。每天更有100张火车票免单券等你拿； ");
		rules.add("下载网易火车票客户端：http://trip.163.com/,在【个人中心】-【我的优惠】，中输入兑换码即可使用；");
		rules.add("您领取红包的同时将会获得网易保险送出的最高50W交通保障；");
		rules.add("活动期间，每位用户限领一份；");
		rules.add("红包有效期：2015年2月28日过期失效；");
		rules.add("买票时，每个订单仅能使用1个红包；票款金额需大于红包金额，不可拆分；");
		rules.add("关于火车票红包及火车票免单的任何问题，您可以致电网易火车票专属客服：020-83568090-2-7");
		return "web/activity/baoxian/index";
	}
	
	/**
	 * 帮助
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/web/help")
	public String help(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {
	
		String userAgent = request.getHeader("User-Agent");
		
		if (null != userAgent) {
		    userAgent = userAgent.toLowerCase();
		    if (userAgent.contains("android") || userAgent.contains("iphone")) {
		    	try {
		    		List<String> helpCategories = HelpUtil.helpCategories;

		    		modelMap.put("helpCategories", helpCategories);
		    		setCommonVar(modelMap,false,"/huoche/web/help.do");
		    		return "wap/help/index";
				} catch (Exception e) {
					LogConstant.runLog.warn("移动端帮助页重定向出错了");
				}
		    }
		}

		setCommonVar(modelMap,true,"/huoche/web/help.do");
		
		return "web/help";
	}

	
	@RequestMapping("/huoche/help/fetchAnswer")
	public void helpWap(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {

		try {
			String fullContentType = "text/html" + ";charset=UTF-8";
			String qType = request.getParameter("content");
			
			List<String> answers = HelpUtil.answerSet.get(qType);
			String content = JSON.toString(answers);

			response.setCharacterEncoding("UTF-8");
			response.setContentType(fullContentType);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (Exception e) {
			LogConstant.runLog.warn("返回帮助问题答案列表出错了",e);
		}
	}
	
	/**
	 * 跳转到刮刮卡页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/activity/scratchCard")
	public String showScratchCard(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

		String gorderId = request.getParameter("gorderId");
		String currentLoginUserName = request.getParameter("currentLoginUserName");
		String fromPaySuccess = request.getParameter("fromPaySuccess");
		
		if(Strings.isNullOrEmpty(gorderId)){
			String[] ssnDomain = RequestUtil.getSsnFromCookie(request);
			if(ssnDomain == null || ssnDomain.length < 2 || !ssnDomain[0].equals(LogonStatus.ALREADY_LOG_ON)){
			}else{
				String userName= ssnDomain[1];
				Map cond = new HashMap();
				cond.put("userName", userName);
				currentLoginUserName = userName;
				
				cond.put("type", OtherProductCoupon.COUPON_TYPE_SCRATCH_CARD);
				
				List<OtherProductCoupon> couponOrderList = otherCouponService.selectEntityListByCond(cond, null);
				if(couponOrderList != null && couponOrderList.size()>0){
					gorderId = couponOrderList.get(0).getGorderId();
					LogConstant.runLog.info("gorderId" + couponOrderList.get(0).getGorderId() );
				}
			}
		}
		
		modelMap.put("gorderId", gorderId);
		modelMap.put("currentLoginUserName", currentLoginUserName);
		modelMap.put("systemTimeMsec", System.currentTimeMillis());
		modelMap.put("cdnBaseUrl", PropertyUtils.getProperty("cdnBaseUrl"));
		modelMap.put("cdnVersion", PropertyUtils.getProperty("cdnVersion"));
		List<String> rules = new ArrayList<String>();
		rules.add("所有用户买票支付后即可参与刮奖（100%中奖）。1元火车票、火车票现金红包、彩票红包、电影票红包、布丁酒店优惠券等你拿！");
		rules.add("中奖奖品请在客户端【我的】-【我的优惠】-【我的奖品】中查收。若刮中1元火车票，则将支付款1元以上部分返回中奖用户网易宝账号，出行后3个工作日内到账。");
		rules.add("活动时间：7月18日至8月20日。");
		modelMap.put("rules", rules);
		modelMap.put("shareText", "#暑期大放价，1元火车票# 1元买不了吃亏，1元买不到上当，1元让你走遍天下！走过路过请不要错过！@网易火车票， 就是这么给力！猛戳这里： http://trip.163.com/huoche/web/activity.do");
		
		if(!Strings.isNullOrEmpty(fromPaySuccess)){
			modelMap.put("fromPaySuccess", fromPaySuccess);
		}

		return "wap/activity/1yuanTicket20140718";
	}

	//首页
	@RequestMapping("/index.html")
	public String portal(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {
		
		setCommonVar(modelMap,true,"/#home");
		modelMap.put("iosDownloadLink", PropertyUtil.getProperty("iosDownloadLink"));
		modelMap.put("jumpUrl", "http://trip.163.com/huoche/web/activity/chunyun.html");
		return "web/download";
	}
	
	//用户协议
	@RequestMapping("/huoche/web/agreement")
	public String agreement(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {
		
		setCommonVar(modelMap,false,"");
		
		return "wap/doc/agreement";
	}
	
	/**
	 * 预售日期
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/wap/presal2015.html")
	public String guoqingPresal(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {
		
		setCommonVar(modelMap,false,"");
		
		return "wap/doc/2014saleTime";
	}
	
	/**
     * 互助抢票
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/huoche/web/helpRobTicket.html")
    public String helpRobTicket(HttpServletRequest request,HttpServletResponse response, 
            ModelMap modelMap) {
        modelMap.put("iosDownloadLink", PropertyUtil.getProperty("iosDownloadLink"));
        setCommonVar(modelMap,true,"/huoche/web/helpRobTicket.html");
        
        return "web/popularize/huzhuRobTicket";
    }
	
	/**
	 * 元旦预售页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/web/activity/yuandan.html")
	public String yuandanPresale(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {
		
		setCommonVar(modelMap,true,"/huoche/web/activity/yuandan.html");
		
		List<String> awardStrList = null;
		List<Award> awardList = new ArrayList<Award>();
		try{
			awardStrList = (List<String>)activityCache.getEntity(CommonConstant.CACHE_TOKEN_ACTIVITY_AWARDLIST);
			if(awardStrList != null && awardStrList.size()>0){
				
				for(int i=0;i<awardStrList.size();i++){
					String string = awardStrList.get(i);
					String[] info = StringUtil.split(string, '^');
					if(info.length<2)continue;
					
					Award award = new Award();
					award.setAccountName(info[0]);
					award.setBonus(info[1]);
					awardList.add(award);
				}
			}
			
			if(TrainUtil.bDebug){
				LogConstant.runLog.info("awardStrList:" + awardStrList);
				LogConstant.runLog.info("awardList" + awardList);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		modelMap.put("awardList", awardList);
		
		return "web/activity/yuanDan20141128";
	}
	
	@RequestMapping("/huoche/web/getTicketBookingCount")
	@ResponseBody
	public String getTicketBookingCount() {
        int totalNum = 0;
        try {
            totalNum = ticketBookService.getTicketBookingCount("1");
            totalNum = (int) ((totalNum - 5986) * 7.38 + 33879);
        } catch (Exception ex) {
            totalNum = 59876423;
        }
        return String.valueOf(totalNum);
	}
	
	@RequestMapping("/huoche/web/activity/aoyou.html")
	public String aoYouCooperation(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
	    springFestival(request,response,modelMap);
	    return "web/activity/aoYouCooperation20141219";
	}
	
	/**
	 * 春运活动页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/huoche/web/activity/chunyun.html")
	public String springFestival(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
	    setCommonVar(modelMap, true, "/huoche/web/activity/chunyun.html");
        List<String> awardStrList = null;
        List<Award> awardList = new ArrayList<Award>();
        try{
            awardStrList = (List<String>)activityCache.getEntity(CommonConstant.CACHE_TOKEN_ACTIVITY_AWARDLIST);
            if(awardStrList != null && awardStrList.size()>0){
                for(int i=0;i<awardStrList.size();i++){
                    String string = awardStrList.get(i);
                    String[] info = StringUtil.split(string, '^');
                    if(info.length<2)continue;
                    Award award = new Award();
                    award.setAccountName(info[0]);
                    award.setBonus(info[1]);
                    awardList.add(award);
                }
            }
            if(TrainUtil.bDebug){
                LogConstant.runLog.info("awardStrList:" + awardStrList);
                LogConstant.runLog.info("awardList" + awardList);
            }
        }catch (Exception e) {
            LogConstant.runLog.info("春运活动获取领取奖品人列表时出错",e);
        }
        modelMap.put("awardList", awardList);
        List<String> rules = new ArrayList<String>() ;
        rules.add("下载网易火车票客户端，点击首页活动入口，玩游戏即有机会获得火车票免单、2-20元火车票红包、3元彩票红包、120元易到用车红包等。");
        rules.add("活动时间：2014年12月1日至2015年3月1日。");
        modelMap.put("rules", rules);
        
        String pinganUrl  = "https://member.pingan.com.cn/pinganone/pa/commRegisterEntry.view?ptag=teckit&appId=10125&withMobile=1&signtype=MD5&";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stamp = null;
        String signature = null;
        try {
//           if(TrainUtil.bDebug){
               date.setMinutes(date.getMinutes() + 2);
               stamp =  sdf.format(date);
               pinganUrl += "timestamp=" + stamp;
               signature = "ef2bccdc9b3bf72a68adeb72e3782f5eappId=10125&timestamp=" + stamp + "&withMobile=1ef2bccdc9b3bf72a68adeb72e3782f5e";
               signature = TrainUtil.convertToMd5(signature);
               pinganUrl += "&signature=" + signature;
//           } else {
//               pinganUrl = "http://one.pingan.com/";
//           }
        } catch (Exception ex) {
            pinganUrl = "http://one.pingan.com/";
        }
        List<Map<String,String>> productList = new ArrayList<Map<String,String>>();
        Map<String,String> item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/product19.jpg");
        item.put("link", pinganUrl);
        item.put("desc", "平安一账通，百万积分等你花");
        productList.add(item);
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/sunning.jpg");
        item.put("link", "http://sale.suning.com/images/advertise/001/chaoshi1/index.html");
        item.put("desc", "超级年货节 让大家过个好年");
        productList.add(item);

        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/product17.png");
        item.put("link", "http://www.smzdm.com");
        item.put("desc", "高性价比网购商品推荐网站");
        productList.add(item); 
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/landou.jpg");
        item.put("link", "http://gad.netease.com/mmad/access?s=Z1%2FwDgZAmFBSLJu66ZIUaijKnZo%3D&project_id=20239&monitor_type=1");
        item.put("desc", "乱斗西游伴你一路同行");
        productList.add(item);
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/product02.png");
        item.put("link", "http://tuanche.com");
        item.put("desc", "低价团新车，高价卖旧车");
        productList.add(item);
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/product04.gif");
        item.put("link", "http://www.wumii.com/");
        item.put("desc", "无秘：匿名朋友圈");
        productList.add(item);
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/qunao.jpg");
        item.put("link", "http://wan.8264.com");
        item.put("desc", "提供户外旅行 深度游销售平台");
        productList.add(item);
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/product08.jpg");
        item.put("link", "http://mail.163.com/dashi/activity/tequan/index.do?from=trip");
        item.put("desc", "享衣食住行各种特权优惠");
        productList.add(item);
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/product06.png");
        item.put("link", "http://www.yongche.com/?utm_source=baidu&utm_medium=pinzhuan&utm_campaign=biaoti");
        item.put("desc", "畅享都市用车新生活");
        productList.add(item);
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/product03.png");
        item.put("link", "http://www.luobo360.com/page/capping");
        item.put("desc", "送你一节课 改变你一生");
        productList.add(item); 
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/product11.png");
        item.put("link", "http://caipiao.163.com/?from=pzsempz");
        item.put("desc", "中了2亿回家，倍儿有面");
        productList.add(item); 
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/product12.png");
        item.put("link", "http://baoxian.163.com/");
        item.put("desc", "一份保障，安心到家");
        productList.add(item); 
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/harda.jpg");
        item.put("link", "http://www.harda.cn/trip");
        item.put("desc", "懂生活的人带你一起旅行");
        productList.add(item); 
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/product14.png");
        item.put("link", "http://news.163.com/");
        item.put("desc", "春运途中阅览天下事");
        productList.add(item); 
        
        item = Maps.newHashMap();
        item.put("img", "http://pimg1.126.net/trip/img/activity/springFestival20141202/music.jpg");
        item.put("link", "http://music.163.com");
        item.put("desc", "千位明星都在用的音乐APP");
        productList.add(item); 
        modelMap.put("productList", productList);
        
        List<Map<String,String>> strategyList = new ArrayList<Map<String,String>>();
        item = Maps.newHashMap();
        item.put("title", "退票改签规则大调整");
        item.put("content","铁路客票延长预售期后，为了最大限度方便旅客、最大限度让旅客充分利用车票资源，中国铁路总公司决定进一步优化... &nbsp;");
        item.put("link", "http://www.12306.cn/mormhweb/zxdt/201411/t20141130_2335.html");
        strategyList.add(item);
        
        item = Maps.newHashMap();
        item.put("title", "预售时间延长60天");
        item.put("content","自2014年12月1日起，铁路互联网售票、电话订票的预售期由20天延长至60天。具体为：12月1日，预售期由现行20... &nbsp;");
        item.put("link", "http://www.12306.cn/mormhweb/zxdt/201411/t20141129_2334.html");
        strategyList.add(item);
        
        item = Maps.newHashMap();
        item.put("title", "起售时间大调整");
        item.put("content","自2014年11月28日起，铁路部门将对互联网、电话订票的起售时间进行调整。放票时间点从16个调整为21个，即... &nbsp;");
        item.put("link", "http://www.12306.cn/mormhweb/zxdt/201411/t20141126_2316.html");
        strategyList.add(item);
        
        item = Maps.newHashMap();
        item.put("title", "2015春运新政详细解析");
        item.put("content","今年春运有啥变化，一张图带你看明白。<br />");
        item.put("link", "http://mp.weixin.qq.com/s?__biz=MzA3MTEyNTkyMA==&mid=201474344&idx=1&sn=8bde7d4cd0c2f1134de6f11d24f291ee");
        strategyList.add(item);
        
        item = Maps.newHashMap();
        item.put("title", "春节回家必备神器");
        item.put("content","春运回家必备神器，没有这些怎么任性？<br />");
        item.put("link", "http://blog.ximalaya.com/%e6%98%a5%e8%8a%82%e5%9b%9e%e5%ae%b6%e5%bf%85%e5%a4%87%e7%a5%9e%e5%99%a8%ef%bc%9a%e6%b2%a1%e6%9c%89%e8%bf%99%e4%ba%9b%ef%bc%8c%e6%80%8e%e4%b9%88%e4%bb%bb%e6%80%a7%ef%bc%9f/");
        strategyList.add(item);
        
        item = Maps.newHashMap();
        item.put("title", "春运抢票史上最强攻略");
        item.put("content","网易火车票倾情奉上史上最强抢票攻略，看好啦！不谢~ &nbsp;");
        item.put("link", "http://mp.weixin.qq.com/s?__biz=MzA3MTEyNTkyMA==&mid=201645801&idx=3&sn=86a4547c219ca2b0ef89063c169c83e4");
        strategyList.add(item);        
        modelMap.put("strategyList", strategyList);
        
        int totalNum = 0;
        try {
            totalNum = ticketBookService.getTicketBookingCount("1");
            totalNum = (int) ((totalNum - 5986) * 7.38 + 33879);
        } catch (Exception ex) {
            LogConstant.runLog.info("获取抢票总数出错",ex);
            totalNum = 59876423;
        }
        modelMap.put("totalNum", String.valueOf(totalNum));
        List<Map<String,String>> hotHolidays = new ArrayList<Map<String,String>>();
        item = Maps.newHashMap(); 
        item.put("name", "除夕火车票");
        item.put("desc", "在2014年12月21日开抢");
        item.put("startDate", "2014-12-21");
        hotHolidays.add(item);
        item = Maps.newHashMap();
        item.put("name", "初一火车票");
        item.put("desc", "在2014年12月22日开抢");
        item.put("startDate", "2014-12-22");
        hotHolidays.add(item);
        item = Maps.newHashMap();
        item.put("name", "初五火车票");
        item.put("desc", "在2014年12月26日开抢");
        item.put("startDate", "2014-12-26");
        hotHolidays.add(item); 
        item = Maps.newHashMap();
        item.put("name", "初六火车票");
        item.put("desc", "在2014年12月27日开抢");
        item.put("startDate", "2014-12-27");
        hotHolidays.add(item); 
        item = Maps.newHashMap();
        item.put("name", "初七火车票");
        item.put("desc", "在2014年12月28日开抢");
        item.put("startDate", "2014-12-28");
        hotHolidays.add(item);
        item = Maps.newHashMap();
        item.put("name", "十五火车票");
        item.put("desc", "在2015年01月05日开抢");
        item.put("startDate", "2015-01-15");
        hotHolidays.add(item); 
        modelMap.put("hotHolidays", hotHolidays);
        return "web/activity/springFestival20141202";
	}
	
	/**
	 * 设置前端页面的公共变量
	 * @param modelMap 
	 * @param bHome 标志是否是首页,为false时currUrl可以不传，即设置导航栏上的当前选中页面
	 */
	private void setCommonVar(ModelMap modelMap, boolean bHome, String currUrl){
		if(modelMap == null) return;
		modelMap.put("systemTimeMsec", System.currentTimeMillis());
		modelMap.put("cdnBaseUrl", PropertyUtils.getProperty("cdnBaseUrl"));
		modelMap.put("cdnVersion", PropertyUtils.getProperty("cdnVersion"));
		
		//首页上导航栏的信息
		if(bHome){
			List <TopNav> topNavList = new ArrayList <TopNav>();
			TopNav topNav1 = new TopNav();
			topNav1.setUrl("/#home");
			if (topNav1.getUrl().equals(currUrl))topNav1.setActive(true);
			else topNav1.setActive(false);
			topNav1.setText("首页");
			topNavList.add(topNav1);
			
			TopNav topNav2 = new TopNav();
			topNav2.setUrl("/huoche/web/help.do");
			if (topNav2.getUrl().equals(currUrl))topNav2.setActive(true);
			else topNav2.setActive(false);
			topNav2.setText("帮助");
			topNavList.add(topNav2);
			
//			TopNav topNav3 = new TopNav();
//			topNav3.setUrl("/huoche/web/activity.do");
//			if (topNav3.getUrl().equals(currUrl))topNav3.setActive(true);
//			else topNav3.setActive(false);
//			topNav3.setText("活动");
//			topNav3.setHasFlag(true);
//			topNav3.setFlagStyle("hot");
//			topNavList.add(topNav3);
			
//			TopNav topNav4 = new TopNav();
//			topNav4.setUrl("/huoche/web/activity/yuandan.html");
//			if (topNav4.getUrl().equals(currUrl))topNav4.setActive(true);
//			else topNav4.setActive(false);
//			topNav4.setText("元旦");
//			topNav4.setHasFlag(true);
//			topNav4.setFlagStyle("new");
//			topNavList.add(topNav4);
			
			TopNav topNav5 = new TopNav();
			topNav5.setUrl("/huoche/web/activity/chunyun.html");
            if (topNav5.getUrl().equals(currUrl))topNav5.setActive(true);
            else topNav5.setActive(false);
            topNav5.setText("春运");
            topNav5.setHasFlag(true);
            topNav5.setFlagStyle("new");
            topNavList.add(topNav5);
            
            TopNav topNav6 = new TopNav();
            topNav6.setUrl("/huoche/web/helpRobTicket.html");
            if (topNav6.getUrl().equals(currUrl))topNav6.setActive(true);
            else topNav6.setActive(false);
            topNav6.setText("帮抢票");
            topNavList.add(topNav6);
			
			modelMap.put("topNavList", topNavList);
		}
	}
	
	/**
	 * @author bjpengpeng
	 * 刷新系统缓存
	 * @since 2014/7/25
	 */
	//更新系统缓存配置 
	@RequestMapping("/huoche/web/refreshCdnVersion")
	@ResponseBody
	public ResultInfo refreshCdnVersion(HttpServletRequest request) {
		ResultInfo result = new ResultInfo();
		String accessIp = JSPHelper.getRemoteAddr(request).trim();
		try {
			String _ipList = Config.getConfig("config.properties", "cdn_white_ip_list");
			if(!Strings.isNullOrEmpty(_ipList)){
				LogConstant.runLog.info("获取ip白名单：" + _ipList);
				String[] ipList = StringUtil.split(_ipList, ',');
				ipList = (null == ipList)? new String[0] : ipList;
				for (String ip : ipList) {
					if (ip.trim().equalsIgnoreCase(accessIp)) {
						//刷新缓存
						PropertyUtils.refresh();
						result.setRetcode(TrainConstant.OK_CODE);
						result.setRetdesc(TrainConstant.OK_DESC + "-CDN-" + PropertyUtils.getProperty("cdnVersion"));
						return result;
					}
				}
				result.setRetcode(TrainConstant.SIGN_CHECK_FAIL_CODE);
				result.setRetdesc(TrainConstant.SIGN_CHECK_FAIL_DESC);
			}
		} catch (IOException e) {
			LogConstant.runLog.error("刷新CDN缓存失败", e);
			result.setRetcode(TrainConstant.SYS_ERROR_CODE);
			result.setRetdesc(TrainConstant.SYS_ERROR_DESC);
		}
		return result;
	}
	
	/**
	 * 获取火车票红包的接口结果的页面
	 * @param 
	 * @return
	 */
	@RequestMapping("/huoche/web/parterner/trainCouponResult.html")
	public String getTrainCouponResultPage(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap){
		String from = request.getParameter("from");
		String type= request.getParameter("type");
		
		setCommonVar(modelMap, true, "/huoche/web/activity.do");
		
		modelMap.put("resultIcon", "http://pimg1.126.net/trip/img/activity/cooperation201406/status_suc.png");
		
		modelMap.put("resultBtnText", "立即下载客户端");
		modelMap.put("resultUrl", "http://trip.163.com/#home");
		
		List<String> rules = new ArrayList<String>();
		
		
		if(from != null && from.equals("mail163")){
			modelMap.put("resultDesc","恭喜您成功领取：邮箱签到礼包<em>5元火车票红包</em>");
			modelMap.put("procedurePicUrl", "http://pimg1.126.net/trip/img/activity/cooperation201406/procedure.jpg");
		    rules.add("5元网易火车票抵现红包，买票时享受立减5元！");
		    rules.add("您需要在2014年9月30日前完成兑及使用，过期失效。");
			rules.add("活动期间，每位用户限领1份。");
			rules.add("买票时，每个订单仅能使用1个红包；票款金额需大于红包金额，不可拆分使用。");
		} else if (from != null && from.equalsIgnoreCase("travel")) {
            modelMap.put("resultDesc","恭喜您成功领取：网易旅行<em>5元火车票红包</em>");
            modelMap.put("procedurePicUrl", "http://pimg1.126.net/trip/img/activity/cooperation201406/procedure.jpg");
            rules.add("5元网易火车票抵现红包，买票时享受立减5元！");
            rules.add("您需要在2015年2月15日前完成兑及使用，过期失效。");
            rules.add("活动期间，每位用户限领1份。");
            rules.add("买票时，每个订单仅能使用1个红包；票款金额需大于红包金额，不可拆分使用。");		    
		}
		else if (from != null && from.equals("wyb1")){
			modelMap.put("resultDesc","恭喜您成功领取：网易宝新人礼<em>5元火车票红包</em>");
			modelMap.put("procedurePicUrl", "http://pimg1.126.net/trip/img/activity/cooperation201406/procedure.jpg");
		    rules.add("下载网易火车票客户端，绑定网易帐号，买票立减5元！");
		    rules.add("请在领取后30天内使用，过期失效。");
			rules.add("买票时，每个订单仅能使用1个红包；票款金额需大于红包金额，不可拆分使用。");
		}else if (from != null && from.equals("wyb2")){
			modelMap.put("resultDesc","恭喜您成功领取：网易宝秒杀礼品<em>5元火车票红包</em>");
			modelMap.put("procedurePicUrl", "http://pimg1.126.net/trip/img/activity/cooperation201406/procedure.jpg");
		    rules.add("5元网易火车票抵现红包，买票时享受立减5元！");
		    rules.add("您需要在2014年8月10日前完成兑及使用，过期失效。");
			rules.add("活动期间，每位用户限领1份。");
			rules.add("买票时，每个订单仅能使用1个红包；票款金额需大于红包金额，不可拆分使用。");
		}else if(from != null && from.equals("mailMaster")){
			if(!Strings.isNullOrEmpty(type) && type.equals("miandan")) {
				modelMap.put("resultDesc","恭喜您成功获取： 网易邮箱大师火车票免单");
				rules.add("下载网易火车票客户端，购买100元以内火车票，出行成功后，请寄给我们您的出行车票，我们将全额返还票款。");
				rules.add("请在2014年10月31日前使用，过期失效。");
				rules.add("如有任何疑问： 请致电010-8255 8675 。");
			} else {
				modelMap.put("resultDesc","恭喜您成功领取：网易邮箱大师<em>5元火车票红包</em>");
				rules.add("下载网易火车票客户端，绑定网易帐号，买票立减5元！");
			    rules.add("红包已领取，30日内有效，请及时使用。");
				rules.add("买票时，每个订单仅能使用1个红包；票款金额需大于红包金额，不可拆分使用。");
				rules.add("活动期间，每位用户限领1份。");
			}
			
			modelMap.put("procedurePicUrl", "http://pimg1.126.net/trip/img/activity/cooperation201406/procedure.jpg");
		    
		} else if (from != null && from.equals("chexianhuodong")) {
		    modelMap.put("resultDesc","恭喜您成功领取： 网易火车票5元红包");
		    modelMap.put("procedurePicUrl", "http://pimg1.126.net/trip/img/activity/cooperation201406/procedure.jpg");
            rules.add("下载网易火车票客户端， 在【个人中心】-【我的优惠】中输入兑换码即可使用；");
            rules.add("红包已领取，请在2015年3月1日前使用；");
            rules.add("买票时，每个订单仅能使用1个红包；票款金额需大于红包金额，不可拆分使用；");
            rules.add("活动期间，每位用户限领1份。");
		} else if (from != null && from.equals("lianheyingxiao")) {
            modelMap.put("resultDesc","恭喜您成功领取： 网易火车票10元红包");
            modelMap.put("procedurePicUrl", "http://pimg1.126.net/trip/img/activity/cooperation201406/procedure.jpg");
            rules.add("下载网易火车票客户端，绑定网易账号，买票立减10元！");
            rules.add("红包已领取，请在2015年2月28日前使用；");
            rules.add("买票时，每个订单仅能使用1个红包；票款金额需大于红包金额，不可拆分使用；");
            rules.add("活动期间，每位用户限领1份。");
        }
		
		
		modelMap.put("rules", rules);
		if(from != null && from.equals("lianheyingxiao")) {
		    modelMap.put("activityUrl", "http://trip.163.com/huoche/web/activity/yuandan.html");
	        modelMap.put("activityPicUrl", "http://pimg1.126.net/trip/img/activity/cooperation201406/lianheyingxiao.jpg");
		} else if (from != null && from.equals("chexianhuodong")) {
		    modelMap.put("activityUrl", "http://trip.163.com/huoche/web/activity/chunyun.html");
	        modelMap.put("activityPicUrl", "http://pimg1.126.net/trip/img/activity/cooperation201406/lianheyingxiao.jpg");
		} else {
		    modelMap.put("activityUrl", "http://trip.163.com/huoche/web/activity.do");
		    modelMap.put("activityPicUrl", "http://pimg1.126.net/trip/img/activity/cooperation201406/activityPic1.jpg");
		}
		
		
		return "web/activity/cooperation201406/cooperation";
	}
	
	
	
	@RequestMapping("/huoche/queryTicketSuccessCount")
	@ResponseBody
	public int queryTicketBookingCount(HttpServletRequest request,HttpServletResponse response) {
		try {
			int count = ticketBookingService.getTicketBookingCount("1");
			
			// 修改抢票成功数量
			count = (int) ((count - 5986) * 7.38 + 33879);
			
			return count;
		} catch (Exception e) {
			LogConstant.runLog.warn("获取抢票成功个数信息出错了", e);
			return -1;
		}
	}
	
	//邮箱俱乐部的活动
//	@RequestMapping("/huoche/mailClub/exchange")
//	@ResponseBody
	public int mailClubExchange(HttpServletRequest request,HttpServletResponse response) {
		String[] ssnDomain = RequestUtil.getSsnFromCookie(request);
		
		//需要登录
		if(ssnDomain == null || ssnDomain.length < 2 || !ssnDomain[0].equals(LogonStatus.ALREADY_LOG_ON)){
			return 0;
		}
		
		LogConstant.runLog.info("user:" + ssnDomain[1] + " exchange, mobile:" + ssnDomain[2]);
		
		String userIP = JSPHelper.getRemoteAddr(request);
		
		//防止恶意刷该接口，对同一IP做限制
		try{
			Object v = smsIPCache.getIP("#" + userIP);
			if(v!= null){
				LogConstant.runLog.info("ip:" + userIP + " call too much!");
				return 5;
			}
			smsIPCache.putIP("#" + userIP, 1, false, 10);
		}catch(Exception ex){
			LogConstant.runLog.warn("smsIPCache读写出错", ex);
		}
		
		String userName = ssnDomain[1];
		
		ActivityPrizeInfoModel entity = new ActivityPrizeInfoModel();
		entity.setMail(userName);
		
		//已经参加过活动
		if(prizeService.findEntityById(entity) != null){
			return 2;
		}
		
		//流水号唯一，使用时间和用户哈希共同组成
		String time = String.valueOf(System.currentTimeMillis());
		time = time.substring(time.length()-5);
		String hashcode = String.valueOf(userName.hashCode());
		hashcode = hashcode.substring(hashcode.length()-5);
		
		String query = "?uid=" + userName + "&userip=" + userIP + "&sn=" + time + hashcode;
		LogConstant.runLog.info("邮箱俱乐部扣除积分请求：" + query);
		try{
			String result = new String(HttpUtil.sendGetRequest(PropertyUtil.getProperty("mailClubConsumeUrl") + query));
			LogConstant.runLog.info("调用邮箱俱乐部消费接口结果：" + result);
			String code = result == null ? "-1" : result.split("\n")[0];
			
			//调用人气接口
			query = "?uid=" + userName.toLowerCase() + "&aid=" + PropertyUtil.getProperty("mailClubAID");
			HttpUtil.sendGetRequest(PropertyUtil.getProperty("mailClubParticipateUrl") + query);
			
			if(code.equals("0")){
				//没有手机号或者手机号非法，直接返回成功，不参与抽奖
				if(ssnDomain.length < 3 || ssnDomain[2] == null || !TrainUtil.verifyMobile(ssnDomain[2])){
					LogConstant.runLog.info("调用邮箱俱乐部消费接口结果：" + ssnDomain[1]  + "没有手机号");
					return 3;
				}
				
				//成功，添加到表中
				entity.setFlag("club");
				entity.setPrizeStatus(0);
				entity.setTel(ssnDomain[2]);
				entity.setSn(time + hashcode);
			
				prizeService.insertEntity(entity);
				
				return 3;
				
			}else if(code.equals("-5")){
				LogConstant.runLog.info("mailClubExchange :积分不足");
				return 4;
			}else if(code.equals("-4")){
				LogConstant.runLog.info("mailClubExchange :扣分次数超过限制");
				return 2;
			}
			
		}catch(Exception ex){
			LogConstant.runLog.warn("调用积分消费接口失败", ex);
			return 5;
		}
		
		return 5;
	}
	
	/**
	 * 订单详情页酒店图片url
	 */
	@RequestMapping("/huoche/ctripHotelImageUrl")
	@ResponseBody
	public String ctripHotelImageUrl() {
		String imageUrl = InitBean.getIniStringValue("ctripHotel_img_url");
		return imageUrl;
	}
	
	@RequestMapping("/huoche/ctripHotelList")
	public void ctripHotelList(HttpServletRequest request,HttpServletResponse response) {
		String ctripHotelUrl = TrainConstant.CTRIP_HOTEL_URL;
//		StringBuffer ctripCityHotelListUrl = new StringBuffer(TrainConstant.CTRIP_CITYHOTEL_List);

		try {
			
			String ticketToURLEncode = request.getParameter("ticketTo");
			String arriveTime = request.getParameter("arriveTime");
			
			LogConstant.runLog.info("ctripHotelList, 目的地火车站URLEncode:" + ticketToURLEncode);
			
			if (Strings.isNullOrEmpty(ticketToURLEncode)) {
				ctripHotelUrl = TrainConstant.CTRIP_HOTEL_URL_WITHARGS 
						+ "&cityid=2&atime=" + arriveTime;
				LogConstant.runLog.info("ctripHotelList, ctripHotelUrl:" + ctripHotelUrl);
				response.sendRedirect(ctripHotelUrl);
			} else {
				String ticketTo = URLDecoder.decode(ticketToURLEncode, "UTF-8");
				//根据火车站获取目的地城市名
				String cityName = CityTrainStation.getCityByTrainStation(ticketTo + "火车站");
				
				LogConstant.runLog.info("ctripHotelList, 目的地火车站:" + ticketTo
						+ ", 所属城市名:" + cityName + ", 到站时间:" + arriveTime);
				
				if (Strings.isNullOrEmpty(cityName)) {
					LogConstant.runLog.info("ctripHotelList, ctripHotelUrl:" + ctripHotelUrl);
					response.sendRedirect(ctripHotelUrl);
				} else {
//					String cityUrlEncode = URLEncoder.encode(URLEncoder.encode(cityName,"UTF-8"),"UTF-8");
//					ctripCityHotelListUrl = ctripCityHotelListUrl.append("&ACity="+cityUrlEncode+"&ATime="+arriveTime);
					
					//根据城市名获取携程的城市ID
					String cityId = CtripHotelCityData.getCityIdByName(cityName);
					
					ctripHotelUrl = TrainConstant.CTRIP_HOTEL_URL_WITHARGS 
							+ "&cityid=" + cityId + "&atime=" + arriveTime;
					
					LogConstant.runLog.info("ctripHotelList, ctripCityHotelListUrl:" + ctripHotelUrl);
					response.sendRedirect(ctripHotelUrl);
				}
			}
		} catch (Exception e) {
			LogConstant.runLog.error("ctripHotelList出错了",e);
			try {
				response.sendRedirect(ctripHotelUrl);
			} catch (IOException e1) {
				LogConstant.runLog.error("ctripHotelList出错了,response.sendRedirect()异常",e);
			}
		}
	}

	
	
	/*
	 * 保险第一期   修改保险跳转
	 */
	@RequestMapping("/huoche/redirectBaoXian.html")
	public void redirectBaoXian(HttpServletRequest request,HttpServletResponse response) {
	
		try {
		    String urlString = InitBean.getIniStringValue("bannerBaoXianUrl");
//			response.sendRedirect("http://baoxian.163.com/m/product3/4351.html");
		    response.sendRedirect(urlString);
		} catch (Exception e) {
			LogConstant.runLog.info("重定向保险时出错",e);
			return;
		}
		
	}
	
	/*
	 * 保险第一期   修改保险跳转
	 */
	@RequestMapping("/huoche/ios/redirectBaoXian.html")
	public void redirectAndroidBaoXian(HttpServletRequest request,HttpServletResponse response) {
	
		try {
		    String urlString = InitBean.getIniStringValue("bannerIosBaoXianUrl");
//			response.sendRedirect("http://baoxian.163.com/m/product3/4351.html");
		    response.sendRedirect(urlString);
		} catch (Exception e) {
			LogConstant.runLog.info("重定向保险时出错",e);
			return;
		}
		
	}
	
	/**
	 * 保险传递账号
	 * @param request
	 * @param response
	 */
	@RequestMapping("/huoche/transferAccount.do")
	public void transferAccount(HttpServletRequest request,HttpServletResponse response) {
		String[] ssnDomain = RequestUtil.getSsnFromCookie(request);
		//String[] ssnDomain = new String[]{new String("3"),new String("yysun@trip163.live.cn")};
		StringBuilder insuranceUrl = new StringBuilder("http://baoxian.163.com/m/train/t4351.html");
		try {
			if(ssnDomain.length < 2|| ssnDomain == null || !ssnDomain[0].equals(LogonStatus.ALREADY_LOG_ON)) {
				response.sendRedirect(insuranceUrl.toString());
			} else {
				String userAccount = "";
				String verifyPara = null;
				userAccount = ssnDomain[1];
				verifyPara = userAccount + "train163baoxian";
				verifyPara = TrainUtil.convertToMd5(verifyPara);
				LogConstant.runLog.info("传递参数为identsign="+verifyPara+"&identaccount="+userAccount);
				insuranceUrl.append("?identsign="+verifyPara+"&identaccount="+userAccount);
				response.sendRedirect(insuranceUrl.toString());
			}
		} catch (Exception e) {
			LogConstant.runLog.info("向保险传递用户账号时出错",e);
			return;
		}
	}
	
	@RequestMapping("/huoche/transferUserInfoToIns.do")
	public void transferUserInfoToIns(HttpServletRequest request,HttpServletResponse response) {
		String userAccount = request.getParameter("identaccount");
		String verifyPara = request.getParameter("identsign");
		String callback = request.getParameter("callback");
		JSONObject returnJson = new JSONObject();
		String returnStr = null;
		Writer out = null;
		List<InsuranceClientPassenger> list = null;
		//StringBuilder insuranceUrl = new StringBuilder("");//保险回传url,不用回传，直接写过去
		LogConstant.runLog.info("获得的参数为：userAccount="+userAccount+",identsign="+verifyPara+",callback="+callback);
		try {
			if(Strings.isNullOrEmpty(userAccount) || Strings.isNullOrEmpty(verifyPara)) {
				returnJson.put("status", "0");
				returnJson.put("reason", "nullPara");
				returnJson.put("data", "");
				returnStr = returnJson.toString();
			} else {
				String verifyStr = TrainUtil.convertToMd5(userAccount+"getinfo@train");
	//System.out.println(verifyStr+","+verifyPara+","+verifyPara.equals(verifyStr));
				if(!verifyPara.equalsIgnoreCase(verifyStr)) {
					returnJson.put("status", "0");
					returnJson.put("reason", "invalidPara");
					returnJson.put("data", "");
					returnStr = returnJson.toString();
				} else {
					Map<String,Object> cond = Maps.newHashMap();
					cond.put("ursAccount", userAccount);
					list = clientPassengerService.selectDisPassengerList(cond);
					if(list == null || list.size() <= 0) {
						returnJson.put("status", "0");
						returnJson.put("reason", "hasNotPassenger");
						returnJson.put("data", "");
					} else {
						returnJson.put("status", "1");
						returnJson.put("reason", "success");
						JSONArray jsonArray = new JSONArray();
						JSONObject json = null;
						for(InsuranceClientPassenger icp : list) {
							json = new JSONObject();
							json.put("id", icp.getId());
							json.put("phone",icp.getPhone() == null ? "":icp.getPhone());//因为等于null时，就不会出现
							json.put("userName", icp.getUserName());
							jsonArray.add(json);
						}
						returnJson.put("data", jsonArray);
						returnStr = returnJson.toString();
					}
				}
			}
			
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
//			returnStr = new String(returnStr.getBytes(),"ISO-8859-1");
//			returnStr = new String(returnStr.getBytes("ISO-8859-1"),"UTF-8");
//System.out.println("-----"+returnStr);
//			response.getOutputStream().print(returnStr);
			out = response.getWriter();
			if(!Strings.isNullOrEmpty(callback)) {
				out.write(callback + "(" + returnStr + ")");
			} else {
				out.write(returnStr);
			}
			
		} catch (Exception e) {
			//LogConstant.runLog.info(e);
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					LogConstant.runLog.info("关闭Writer时出错",e);
				}
			}
		}
	}
	
	/*@RequestMapping("testOut")*/
	public void getStringByHttp(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		URL urlGet = null;
		HttpURLConnection http = null;
		BufferedReader br =null;
		InputStream is = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		request.setCharacterEncoding("utf-8");
		try {
			urlGet = new URL("http://localhost:8080/ecom_trainTicket/huoche/transferUserInfoToIns.do?identsign=FFAE7F83C4B420A11B836BE395FEDB87&identaccount=yysun@trip163.live.cn");
			http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET");
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			is = http.getInputStream();
			br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String message;
			StringBuffer bf = new StringBuffer();
			message = br.readLine();
			while(message != null ){
				bf.append(message);
				message = br.readLine(); 
			}
			System.out.println(bf.toString());
			
		} catch (MalformedURLException e) {
			LogConstant.runLog.info("url出错",e);
		}catch (ProtocolException e) {
			LogConstant.runLog.info("setRequestMethod出错",e);
		} catch (IOException e) {
			LogConstant.runLog.info("打开连接出错或读取数据出错出错",e);
		} catch(Exception e) {
			LogConstant.runLog.info("获取用户openId出现其他异常",e);
		} finally {
			if(br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					LogConstant.runLog.info("关闭流出错",e);
				}
			}
		}
		
	}
	
	
	/**
	 * 十一预售页面
	 */
	@RequestMapping("/qiangPiao.html")
	public String guoQingYuShou(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
		Date yuShouDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 19);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String monthAndDay = sdf.format(yuShouDate);
		String[] mad = monthAndDay.split("-");
		String month = mad[0];
		String date = mad[1];
		String cdnString= PropertyUtils.getProperty("cdnBaseUrl");
		modelMap.put("month", month);
		modelMap.put("date", date);
		modelMap.put("showIframe", true);
		modelMap.put("cdnBaseUrl", PropertyUtils.getProperty("cdnBaseUrl"));
		modelMap.put("cdnVersion", PropertyUtils.getProperty("cdnVersion"));
		modelMap.put("activityUrl", "/huoche/web/activity.do");
		
		ArrayList<Map<String,String>> picList = new ArrayList<Map<String,String>>();
		Map<String,String> pic1 = Maps.newHashMap();
		pic1.put("img", cdnString + "/img/activity/guoqingTripRecommend/1.jpg");
		pic1.put("link", "http://travel.163.com/special/guoqing2013/");
		Map<String,String> pic2 = Maps.newHashMap();
		pic2.put("img", cdnString +"/img/activity/guoqingTripRecommend/2_1.jpg");
		pic2.put("link", "http://baoxian.163.com/activity/travel_201408.html?from=train");
		Map<String,String> pic3 = Maps.newHashMap();
		pic3.put("img", cdnString +"/img/activity/guoqingTripRecommend/3_1.jpg");
		pic3.put("link", "http://www.tuniu.com/static/hoteltuan/?p=15873&utm_source=163app&utm_medium=163app&utm_campaign=AppWebview&utm_content=AppRollBanner");
		Map<String,String> pic4 = Maps.newHashMap();
		pic4.put("img", cdnString +"/img/activity/guoqingTripRecommend/4.jpg");
		pic4.put("link", "http://travel.163.com/");
		Map<String,String> pic5 = Maps.newHashMap();
		pic5.put("img", cdnString +"/img/activity/guoqingTripRecommend/5.jpg");
		pic5.put("link", "http://travel.163.com/special/tibet/");
		Map<String,String> pic6 = Maps.newHashMap();
		pic6.put("img", cdnString +"/img/activity/guoqingTripRecommend/6.jpg");
		pic6.put("link", "http://travel.163.com/special/travelnotes128/");
		Map<String,String> pic7 = Maps.newHashMap();
		pic7.put("img", cdnString +"/img/activity/guoqingTripRecommend/7.jpg");
		pic7.put("link", "http://travel.163.com/special/travelnotes127/");
		Map<String,String> pic8 = Maps.newHashMap();
		pic8.put("img", cdnString +"/img/activity/guoqingTripRecommend/8_1.jpg");
		pic8.put("link", "http://www.mafengwo.cn/oad/oad.php?key=YVNSQA&skey=YlRbR1BBcQ");
		
		picList.add(pic1);
		picList.add(pic2);
		picList.add(pic3);
		picList.add(pic4);
		picList.add(pic5);
		picList.add(pic6);
		picList.add(pic7);
		picList.add(pic8);
		modelMap.put("picList", picList);
		
		return "web/activity/guoqingPresall20140823";
	}
	
	/**
	 * 商城首页自动配置
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/mall/indexConf.html")
	public String mallIndexConf(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
		List<TripMallEntity> lists = null;
		try {//因为商城每次打开mall.163.com都要请求，这里将数据进行缓存，保存24小时
			lists = (List<TripMallEntity>)webPageCache.getEntity("TRIP_MALL_LIST");
			if(lists == null || lists.size() <= 0) {
				lists = tripMallConfigDao.queryTripMallList();
				webPageCache.putEntity("TRIP_MALL_LIST", lists, true, 24 * 60 * 60);
				LogConstant.runLog.info("数据来自数据库并存入缓存"+lists.size());
			} else {
				LogConstant.runLog.info("数据来自缓存"+lists.size());
			}
		} catch (Exception e) {
			LogConstant.runLog.info("商城写缓存出错",e);
			return null;
		}
		
		if(lists == null || lists.size() <= 0) {
			return null;
		}
		
//		System.out.println(lists.size());
		TreeMap<Integer,TripMallEntity> mainLinkMap = Maps.newTreeMap();
		List<Map<String,String>> mainLinkList = new ArrayList<Map<String,String>>();
		
		List<Map<String,String>> listLink = new ArrayList<Map<String,String>>();
		Map<String,Object> listLinkMaps = Maps.newLinkedHashMap();
		listLinkMaps.put("title", "常见问题");
		
		List<Map<String,String>> topLinkList = new ArrayList<Map<String,String>>();
		
		for(TripMallEntity tripMallEntity : lists) {
			if(tripMallEntity.getLocation().equalsIgnoreCase("A")) {
				modelMap.put("name",tripMallEntity.getText());
				modelMap.put("url", tripMallEntity.getUrl());
			}
			
			if(tripMallEntity.getLocation().equalsIgnoreCase("B")) {
				Map<String,String> topLink = Maps.newHashMap();
				topLink.put("text", tripMallEntity.getText());
				topLink.put("url",tripMallEntity.getUrl());
				topLinkList.add(topLink);
			}
			
			if(tripMallEntity.getLocation().equalsIgnoreCase("C")) {
				mainLinkMap.put(new Integer(tripMallEntity.getImageNum()), tripMallEntity);
			}

			if(tripMallEntity.getLocation().equalsIgnoreCase("D")) {
				Map<String,String> listLiMap = Maps.newHashMap();
				listLiMap.put("text", tripMallEntity.getText());
				listLiMap.put("url", tripMallEntity.getUrl());
				listLink.add(listLiMap);
			}
		}
		listLinkMaps.put("list", listLink);
		
		Iterator<Map.Entry<Integer, TripMallEntity>> iterator = mainLinkMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Integer, TripMallEntity> entry = iterator.next();
			Map<String,String> mainLink = Maps.newHashMap();
			mainLink.put("title", entry.getValue().getText());
			mainLink.put("image",entry.getValue().getImageLoc());
			
			mainLink.put("url",entry.getValue().getUrl());
			mainLinkList.add(mainLink);
		}
		modelMap.put("topLink", topLinkList);
		modelMap.put("mainLink", mainLinkList);
		modelMap.put("listLink", listLinkMaps);
		modelMap.put("key", "trip");
		modelMap.put("type", "b2s3");
		
		return "web/popularize/mall/indexConf";
	}
	
	/*
	 * 添加或替换广告位banner
	 * result({"imgSrc":"","imgTitle":"","retCode":,"targetUrl":""})
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/huoche/updateBanner")
	@ResponseBody
	public Object updateBanner(HttpServletRequest request,HttpServletResponse response) {
		Map img=new HashMap();
		img.put("imgSrc","http://pimg1.126.net/trip/img/ad/ursAd4.jpg" );
		img.put("imgTitle","" );
		img.put("retCode",200);
		img.put("targetUrl","http://trip.163.com/");

		return img;
		
		
	}
	
	@RequestMapping("/huoche/epayNested.html")
	public String epayNested(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
		modelMap.put("cdnBaseUrl", PropertyUtils.getProperty("cdnBaseUrl"));
		modelMap.put("cdnVersion", PropertyUtils.getProperty("cdnVersion"));
		return "web/popularize/epayNested20140924";
	}
	
	//后台不需要对应controller也会有accesslog
/*	@RequestMapping("/huoche/visitRecord/{button}")
	@ResponseBody
	public void visitRecord() {
		
	}
	*/
	
	@RequestMapping("/huoche/ursPopularize.html")
	public String ursPopularize(ModelMap modelMap) {
	    modelMap.put("cdnBaseUrl", PropertyUtils.getProperty("cdnBaseUrl"));
	    modelMap.put("cdnVersion", PropertyUtils.getProperty("cdnVersion"));
	    return "web/popularize/ursPopularize20141208";
	}
	
	
	/**
	 * Web版优惠券兑换
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/coupon.html")
	public String webExchangeCoupon(ModelMap modelMap) {
	    setCommonVar(modelMap, true, "/huoche/web/activity.do");
	    List<String> rules = new ArrayList<String>();
	    rules.add("买票时,每个订单仅能使用一个;");
	    rules.add("火车票金额需大于红包金额，红包不能拆分使用;");
	    rules.add("您可登录客户端，点击【个人中心】-【我的优惠】-【火车票红包】中查询红包的有效期和使用情况;");
	    rules.add("更多问题请拨打网易火车票客服电话:" + PropertyUtils.getProperty("kefuPhone") + ",服务时间: 8:00-24:00;");
	    modelMap.put("rules", rules);
	    return "web/activity/coupon2014";
	}
	
	/**
	 * 抢票策略
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/robTicketStrategy.html")
	public String robTicketStrategy(ModelMap modelMap) {
	    setCommonVar(modelMap, false, "");
	    return "wap/activity/strategy20141219";
	}
	
	/**
	 * 抢票策略
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/grabTicket.html")
	public String grabTicket(ModelMap modelMap) {
	    return "wap/huzhuRobTicket/grabTicket";
	}
	
	/**
	 * 抢票战绩wap页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/huoche/wap/robTicketRecord.html")
	public String robTicketRecord(HttpServletRequest request,HttpServletResponse response, 
			ModelMap modelMap) {
		String token = request.getParameter("token");
		String accountId = "";
		String isButtonAppear = "";    // 抢票按钮是否显示,“0”：不显示，”1”：显示
		String key = "token_key";
		String preEncryptData = "rob_ticket_record-"; // 增加加密后的token长度
		
		try {
			if (Strings.isNullOrEmpty(token)) {
				// 需要登录, 从cookie中解析用户名
				String[] ssnDomain = RequestUtil.getSsnFromCookie(request);
				if(ssnDomain == null || ssnDomain.length < 2 || !ssnDomain[0].equals(LogonStatus.ALREADY_LOG_ON)){
					LogConstant.runLog.info("获取robTicketRecord时cookie时，用户未登录");
					// 从login_id中解析userName getUserNameByLoginId
					String loginId = request.getParameter("login_id");
					String loginToken = request.getParameter("login_token");
					
					LogConstant.runLog.info("获取抢票战绩用户名, loginId: " + loginId + ", loginToken: " + loginToken);
					if (ValidatorUtil.isEmpty(loginId) || ValidatorUtil.isEmpty(loginToken)) {
						LogConstant.runLog.info("获取robTicketRecord时login_id用户未登录");
						return "web/error";
					}
					accountId = TrainUtil.getUserNameByLoginId(loginId, loginToken);
				} else {
					accountId = ssnDomain[1].trim();
				}
//				accountId = "test1";
				LogConstant.runLog.info("已登录，获取抢票战绩用户名，accountId=" + accountId);
				if (ValidatorUtil.isEmpty(accountId)) {
					LogConstant.runLog.info("获取robTicketRecord时用户名错误");
					return "web/error";
				}
				isButtonAppear = "0";
				
//				token = JniAesCbcEncrypt.encrypt(key, preEncryptData + accountId);
				token = AES.encrypt(preEncryptData + accountId,"ios","1.6");
				LogConstant.runLog.info("加密后的token=" + token);
			} else {
				LogConstant.runLog.info("传入的token=" + token);
				isButtonAppear = "1";
				// 不需要登录，从token中解析用户名
//				String decryptData = JniAesCbcEncrypt.decrypt(key, token);
				String decryptData = AES.decrypt(token,"ios","1.6");
				LogConstant.runLog.info("传入的token解密后decryptData=" + decryptData);
				if (decryptData == null || decryptData.length() <= preEncryptData.length()) {
					LogConstant.runLog.info("传入的token解密后不正确");
					return "web/error";
				}
				accountId = decryptData.substring(preEncryptData.length());
				LogConstant.runLog.info("传入的token解密后accountId=" + accountId);
			}
			
			String cacheKey = "rob_ticket_record_" + accountId;
			Map<String, Integer> countMap = (Map<String, Integer>)webPageCache.getEntity(cacheKey);
			
			if (countMap == null) {
				Map<String, BigDecimal> robTicketCountMap = robTicketDao.getRobTicketCount(accountId);
				// 抢票单个数
				BigDecimal robTicketNum = robTicketCountMap.get("ROBTICKETNUM");
				// 抢票次数
				BigDecimal robCount = robTicketCountMap.get("ROBCOUNT") == null ? new BigDecimal(0) : robTicketCountMap.get("ROBCOUNT");
				// 抢票成功单数
				BigDecimal robTicketSuccessNum = robTicketCountMap.get("ROBTICKETSUCCESSNUM") == null ? new BigDecimal(0) : robTicketCountMap.get("ROBTICKETSUCCESSNUM");
				
				Map<String, BigDecimal> friendHelpCountMap = robTicketDao.getAllFriendHelpCount(accountId);
				// 帮助我的好友数量
				BigDecimal friendHelpNum = friendHelpCountMap.get("FRIENDHELPNUM");
				// 好友帮我抢的次数
				BigDecimal friendHelpRobCount = friendHelpCountMap.get("FRIENDHELPROBCOUNT") == null ? new BigDecimal(0) : friendHelpCountMap.get("FRIENDHELPROBCOUNT");
				
				Map<String, BigDecimal> helpFriendCountMap = robTicketDao.getHelpFriendCount(accountId);
				// 帮助我的好友数量
				BigDecimal helpFriendNum = helpFriendCountMap.get("HELPFRIENDNUM");
				// 好友帮我抢的次数
				BigDecimal helpFriendRobCount = helpFriendCountMap.get("HELPFRIENDROBCOUNT") == null ? new BigDecimal(0) : helpFriendCountMap.get("HELPFRIENDROBCOUNT");
				
				countMap = new HashMap<String, Integer>();
				countMap.put("robTicketNum", robTicketNum.intValue());
				countMap.put("robCount", robCount.intValue());
				countMap.put("robTicketSuccessNum", robTicketSuccessNum.intValue());
				countMap.put("friendHelpNum", friendHelpNum.intValue());
				countMap.put("friendHelpRobCount", friendHelpRobCount.intValue());
				countMap.put("helpFriendNum", helpFriendNum.intValue());
				countMap.put("helpFriendRobCount", helpFriendRobCount.intValue());
				webPageCache.putEntity(cacheKey, countMap, true, 20);
			}
			
			// 抢票战绩分享url
			String robRecordShareUrl = "http://trip.163.com/huoche/wap/robTicketRecord.html?token=" + URLEncoder.encode(token, "utf-8");
			LogConstant.runLog.info("抢票战绩分享url=" + robRecordShareUrl);
			
			// 分享的logo地址
			String shareLogoUrl = InitBean.getIniStringValue("rob_record_share_logo_url");
			// 分享的Title
			String shareTitle = InitBean.getIniStringValue("rob_record_share_title");
			// 分享的内容
			String shareContent = InitBean.getIniStringValue("rob_record_share_content");
			// 是否有分享得红包的功能
			String canGetShareCoupon = InitBean.getIniStringValue("canGetShareCoupon", "1");
			// 分享微博的内容
			String desForSms = InitBean.getIniStringValue("rob_record_share_sms_content");
			// 分享微博的内容
			String desForWb = InitBean.getIniStringValue("rob_record_share_weibo_content");

			modelMap.put("userAgent", request.getHeader("User-Agent"));
			modelMap.put("systemTimeMsec", System.currentTimeMillis());
			modelMap.put("cdnBaseUrl", PropertyUtils.getProperty("cdnBaseUrl"));
			modelMap.put("cdnVersion", PropertyUtils.getProperty("cdnVersion"));
			modelMap.put("robTicketNum", countMap.get("robTicketNum"));
			modelMap.put("robCount", countMap.get("robCount"));
			modelMap.put("robTicketSuccess", countMap.get("robTicketSuccessNum"));
			modelMap.put("friendHelpNum", countMap.get("friendHelpNum"));
			modelMap.put("helpFriendNum", countMap.get("helpFriendNum"));
			modelMap.put("friendHelpRobCount", countMap.get("friendHelpRobCount"));
			modelMap.put("helpFriendRobCount", countMap.get("helpFriendRobCount"));
			modelMap.put("robRecordShareUrl", robRecordShareUrl);
			modelMap.put("isButtonAppear", isButtonAppear);
			modelMap.put("shareLogoUrl", shareLogoUrl);
			modelMap.put("shareTitle", shareTitle);
			modelMap.put("shareContent", shareContent);
			modelMap.put("desForSms", desForSms);
			modelMap.put("desForWb", desForWb);
			modelMap.put("canGetShareCoupon", canGetShareCoupon);
			
			return "wap/robTicket/robRecord";
		} catch (Exception e) {
			LogConstant.runLog.warn("获取抢票战绩数据异常, token=" + token
					+ ", accountId=" + accountId, e);
			return "web/error";
		}
	}
	
	public static void main(String[] args){
		String test ="";
		if(Strings.isNullOrEmpty(test)||Strings.isNullOrEmpty(test.trim())){
		System.out.println("dsag");	
		}
//		Date yuShouDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 19);
//		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
//		String monthAndDay = sdf.format(yuShouDate);
//		String[] mad = monthAndDay.split("-");
//		String month = mad[0];
//		String date = mad[1];
//		
//		System.out.println(month +":"+date);
		
//		List<InsuranceClientPassenger> list = new ArrayList<InsuranceClientPassenger>();
//		InsuranceClientPassenger client1 = new InsuranceClientPassenger();
//		client1.setId("11111");
//		client1.setPhone("123");
//		client1.setUserName("小张");
//		list.add(client1);
//		InsuranceClientPassenger client2 = new InsuranceClientPassenger();
//		client2.setId("11111");
//		client2.setPhone("123");
//		client2.setUserName("小张");
//		list.add(client2);
//		System.out.println(JsonUtil.ListToJson(list));
		
//		String teString = "ｈｅｌｌｏ";
//		
//		teString = new String(teString.getBytes("UTF-8"),"GB2312");
//		byte []bytes = teString.getBytes();
//		System.out.println(bytes.length);
//		for (int i=0;i<bytes.length;i++) {
//			System.out.print(bytes[i]);
//		}
//		if (teString.equals(new String(teString.getBytes("ISO-8859-1"), "ISO-8859-1"))) {
//			System.out.println("iso");
//		} else if (teString.equals(new String(teString.getBytes("UTF-8"), "UTF-8"))) {
//			System.out.println("utf");;
//		} else if (teString.equals(new String(teString.getBytes("GBK"), "GBK"))) {
//			System.out.println("gbk");;
//		} else if (teString.equals(new String(teString.getBytes("GB2312"), "GB2312"))) {
//			System.out.println("GB2312");
//		}
//		List<TripMallEntity> list = (List<TripMallEntity>) null;
//		try {
//			System.out.println( new String(HttpUtil.sendGetRequest("http://reg.163.com/services/queryUserMobile?username="
//					+ CodecUtil.urlEncode("qmqu@corp.netease.com", "UTF-8") + "&product=huoche_client")));
		    /*JSONObject jsonObject = new JSONObject();
		    WuGuang wuGuang = new WuGuang();
		    jsonObject.put("key", wuGuang);
		    wuGuang.setName("wuguang");
		    
		    System.out.println(wuGuang == jsonObject.get("key"));
		    System.out.println(jsonObject);*/
//			System.out.println(HttpUtil.sendPostRequest("http://trip.163.com/huoche/web/parterner/trainCouponResult.html", "from=a", "UTF-8"));
/*		    Date date = new Date();
		    date.setMinutes(date.getMinutes() + 2);
		    System.out.println(date.toLocaleString());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}