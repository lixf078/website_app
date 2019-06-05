package com.jjd.web.util;

/**
 * @author yuansui
 */
abstract public class UrlUtil {

    //    private static String KHttpDef = "https://";
    private static String KHttpDef = "http://";
    private static String mHostName = null;

    protected static String mBase = null;

    private static boolean mIsDebug = false;

    private static void init() {
        if (mIsDebug) {
            // 测试线
            mHostName = KHttpDef + "t1.u.dajiuxing.com.cn";
        } else {
            // 正式线
            mHostName = KHttpDef + "t1.u.dajiuxing.com.cn";
        }
        mBase = mHostName + "/index.php";
    }

    public static void setDebug(boolean isDebug) {
        mIsDebug = isDebug;
        init();
    }

    public static String getHostName() {
        return mHostName;
    }

    public static String getBaseUrl() {
        return mBase;
    }

    /**
     * 用户相关
     */
    public interface UrlUser {
        String KLogin = "user/login"; // 登录
        String KProfile = "user/getuser"; // 获取信息
        String KRegister = "user/register_utou"; // 注册第一步
        String KRegisterStep = "user/register_step";// 注册后面几步
        String KCaptcha = "user/send_sms"; // 发送验证码
        String ChangePwd = "user/changepwd"; // 修改密码
        String KBuyVip = "user/buy_vip"; // 二次购买正式会员
        String KUpdate = "user/update_user"; // 修改用户信息
        String KCampaign = "user/is_campaign"; // 会员订单的优惠信息
        String KSevDesc = "user/sev_dec"; // 服务信息
        String KSevDescEdit = "user/sev_detail_edit"; // 服务信息
        String KServiceTypeAdd = "user/sev_add"; // 添加更改服务类型信息
        String KRegUrlAdd = "user/reg_url_add"; // 添加用户资质

        String KUserFavorites = "user/customer_favorites_get"; // 会员收藏
        String KUserFavoritesAdd = "user/customer_favorites_add"; // 会员增加收藏
        String KUserFavoritesDel = "user/customer_favorites_delete"; // 会员取消收藏
        String KUserFollowers = "user/followers"; // 粉丝列表
        String KSuccessCase = "user/suc_case_list"; // 成功案例
        String KSuccessCaseAdd = "user/suc_case_add"; // 添加成功案例
        String KSuccessCaseEdit = "user/suc_case_edit"; // 编辑成功案例
        String KSuccessCaseDel = "user/suc_case_del"; // 删除成功案例

        String KGloryList = "user/glory_list"; // 荣誉奖项
        String KGloryAdd = "user/glory_add_app"; // 荣誉添加
        String KGloryDel = "user/glory_del_app";// 删除荣誉

        String KCoreTeam = "user/core_team_list"; // 核心团队
        String KCoreTeamAdd = "user/core_team_add"; // 核心团队添加
        String KCoreTeamEdit = "user/core_team_edit"; // 核心团队编辑
        String KCoreTeamDel = "user/core_team_del"; // 删除核心团队成员

        String KGetCompanyTag = "user/adv_tag_app_config"; // 获取所有名片标签
        String KGetCompanyAdd = "user/adv_tag_add"; // 更改名片标签

        String KGetTimeZone = "user/time_zone_config"; // 获取时区
        String KGetUTouUserList = "index/contact_list"; // 获取会员，商家，园区，政府
        String KGetUTouMemberList = "index/member_list"; // 获取会员列表

        String KGetUserCustomFieldList = "user/self_intro"; // 获取自定义字段列表
        String KCustomFieldEdit = "user/self_intro_edit"; // 编辑自定义字段
        String KCustomFieldAdd = "user/self_intro_add"; // 编辑自定义字段
        String KCustomFieldDel = "user/self_intro_del"; // 删除自定义字段
        String KCompanyApply = "user/company_apply"; // 政府园区申请
        String KCertificationApply = "user/apply_for_certification"; // 认证申请

    }

    /**
     * 需求相关
     */
    public interface UrlDemand {
        String KCreate = "demand/demand_create";// post 添加需求
        String KUpdate = "demand/demand_update";// put 更新需求
        String KVerify = "demand/demand_verify";// post 需求确认
        String KList = "demand/demand_list";// get 需求列表
        String KDetail = "demand/demand_detail";// get 需求详情
        String KGroupDetail = "demand/group_detail"; // 群成员列表
    }

    /**
     * 竞标单(bid)相关
     */
    public interface UrlBid {
        String KCreate = "demand/bid_create";// post 创建竞标单
        String KList = "demand/bid_list";// inst 竞标单列表
        String KSelect = "demand/bid_selected";// post 选择竞标单
        String KOrderList = "demand/Business_demand_list";// post 竞标单列表
    }

    /**
     * 服务相关
     */
    public interface UrlService {
        String KCreate = "demand/service_create";// post 添加服务
        String KOption = "demand/service_operate";// post 更改服务状态
        String KEditContract = "demand/edit_contract";// 编辑合同
        String KSubmitContract = "demand/submit_contract";// 提交合同
        String KGetContract = "demand/get_contract";// 获取合同
        String KAgreeContract = "demand/agree_contract";// 是否同意合同
    }

    /**
     * 配置相关
     */
    public interface UrlConfig {
        String KGl = "config/init"; // 全局配置
        String KTopic = "index/story_config"; // 主题配置

        String KWindowServiceTopic = "index/story_config"; // 主题配置

        String kCity = "config/get_cities"; // 国内城市
        String KCityForeign = "config/get_cities_foreign"; // 国外城市
        String KVersion = "config/get_version"; // 新版本检测
        String KBank = "config/company_bank_account"; // 银行账号
    }

    /**
     * 杂项
     */
    public interface UrlOther {
        String KUploadImg = "upload/streamimage"; // 上传图片
        String KBanner = "index/banner"; // 上传图片
    }

    public interface UrlIndex {
        String KBanner = "index/banner"; // 首页banner
        String KNewBanner = "index/risk_banner"; // 新首页banner
        String KBannerAction = "index/quick_service_list"; // banner对应的sub tag内容
    }

    /**
     * 订单相关
     */
    public interface UrlOrder {
        String KList = "order/listorder";
        String KDetail = "order/orderinfo";
        String KPay = "order/pay";
    }
    /**
     * 邀请相关
     */
    public interface UrlInvite {
        String KMyInvite = "user/my_invite";
        String KInvite = "user/invite_user";
        String KDeleteInvite = "user/delete_invite";
        String KInviteAgree = "user/agree_invite_user";
    }

    /**
     * 资讯文章相关
     */
    public interface UrlArticle {
        String KFourInvest = "index/fourinv_list";
        String KTradeIntro = "index/jy_list";
        String KStoryList = "index/story_list";
        String KCkList = "index/ck_list";
        String KIndex = "index/index";
        String KFlash = "index/flash";
    }

    /**
     * 银行相关
     */
    public interface UrlBank {
        String KCard = "bank/bind_card";
        String KInfo = "bank/get_bank";
        String KInfoUpdate = "bank/update_bank";
    }

    /**
     * 首页
     */
    public interface UrlHome {
        String KBizList = "servicelist/get_service_list";
        String KRecommendServiceList = "servicelist/get_recommend_service_list";
    }

    public interface UrlWebView{
        String KErrorUrl = "?s=/api/index/more60s/id/55";
    }

    public interface UrlRisk{
        String KGetRiskList = "index/risk_list";
    }
}
