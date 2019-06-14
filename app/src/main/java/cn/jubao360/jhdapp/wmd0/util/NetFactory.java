package cn.jubao360.jhdapp.wmd0.util;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lib.network.model.NetworkRequest;
import lib.network.model.NetworkRequest.Builder;
import lib.network.param.NameValuePair;

/**
 * 网络请求工厂
 * 参数都使用@interface而且小写是为了跟实际参数匹配上
 */
public class NetFactory {

    private @interface BaseParam {
        String device_os = "device_os";
        String appversion_code = "appversion_code";
        String language = "language";
        String device_id = "device_id";
        String access_token = "access_token";
        String user_type = "user_type";
        String s = "s";
        String app_type = "app_type";
    }

    private @interface DemandParam {
        String country_id = CommonParam.country_id;
        String demand_id = "demand_id";// 需求id
        String demand_category_id = "demand_category_id";// 需求类别id
        String desc = "desc";// 需求描述
        String desc_translate = "desc_translate";// 需求描述翻译
        String pics = "pics";// 多图片url, 非必传
        String demand_status_id = "demand_status_id";// 需求状态(商家区分)
        String amount = "amount";
        String service_id = "service_id";
        String bid_id = "bid_id";
        String service_type = "service_type";
        String service_type_id = "service_type_id";
        String start_time = "start_time";
        String duration = "duration";
        String group_id = "group_id";
        String service_provider_uid = "service_provider_uid"; // 特定服务商uid
        String id = "id"; // 合同id
        String agree = "agree"; // 合同id
        String end_time = "end_time"; // 结束时间
        String service_type_ids = "service_type_ids"; // 服务类型ids
        String sev_id = "sev_id";
        String detail = "detail";
    }

    public @interface UserParam {
        String mobile = "mobile";// 手机号
        String pwd = "pwd";// 密码
        String user_name = "user_name";// 密码
        String check_code = "check_code";// 验证码
        String invite_code = "invite_code";// 客户经理ID
        String uid = "uid";
        String skip_check_code = "skip_check_code"; // 是否跳过验证码（临时解决收不到验证码问题）
        /**
         * 企业注册后面几步参数
         */
        String header_url = "header_url";
        String regist_code = "regist_code";
        String buy_country = "buy_country";
        String country_id = "country_id";
        String country = "country";
        //        String province = "province";
        String city = "city";
        String cityId = "city_id";
        String addr = "addr";
        String address = "address";
        String real_name = "real_name";
        String corporation = "corporation"; // 代表人
        String step = "step";
        String currency = "currency"; // 货币类型
        String reg_url = "reg_url"; // 注册工商图片
        String invest_type = "invest_type"; // 投资类型
        String company_type = "company_type"; // 公司类型
        String service_type = "service_type"; // 服务类型, {0, 1, 2...}
        String ename = "ename"; // 商家企业名称 英文
        String cname = "cname"; // 商家企业名称 中文

        String nation = "nation";
        String open_time = "open_time";
        String open_time_en = "open_time_en";
        String email = "email";
        String company_email = "company_email";
        String language = "language";
        String language_en = "language_en";
        String uid_provider = "uid_provider";
        String tax_no = "tax_no";
        String telephone = "telephone";
        String adv_tag_ids = "adv_tag_ids";
        String logo_url = "logo_url";
        String card_url = "card_url";
        String pid = "pid";
    }

    private @interface CommonParam {
        String page = "page";
        String count = "pagesize";
        //        String method = "method";
        String timestamp = "timestamp";
        String country_id = "country_id";// 国家ID
        String is_reg_type = "is_reg_type";
        String column_id = "column_id";
        String name = "name";
        String job = "job";
        String intro = "intro";
        String year = "year";
        String content_json = "content_json";
    }

    private @interface BankParams {
        String card_number = "card_number";// 卡号
        String card_user_name = "card_user_name";// 用户名
        String card_bank_name = "card_bank_name";// 银行开户地址
        String code = "code";// 短信的验证码
        String card_type = "card_type";// 不填默认1（1银行 2 支付宝 3 papal）

        String cart_linkman = "cart_linkman";
        String contact_number = "contact_number";
        String contanct_email = "contanct_email";
        String currency = "currency";
        String swift = "swift";
        String address = "address";
        String bank_address = "bank_address";
    }

    private @interface ServiceStatusParam {
        String action = "action";// rework/submit/verify
        String pass = "pass";// 0/1。0为不通过,1为通过,只有action是varify时才需要传,其他情况可传可不传
    }

    protected @interface UploadImageParams {
        String fext = "fext";
        String fdata = "fdata";
        String sig = "sig";
    }

    private @interface OrderParams {
        String type = "type";
        String status_id = "status_id";
        String uid = "uid";
        String order_id = "order_id";
        String area = "area";
    }

    private @interface HomeParams {
        String user_type = "user_type";
        String country_id = CommonParam.country_id;
        String column_id = "column_id";
        String demand_category_id = "demand_category_id";
    }

    private @interface InviteParams {
        String user_name = "user_name";
        String mobile = "mobile";
        String agree = "agree";
        String main_uid = "main_uid";
    }

    private @interface FourInvestParams {
        String inv_type = "inv_type";
        String sort = "sort";
    }

    private @interface TradeIntroParams {
        String sort = "sort";
    }

    private @interface HeadlineParams {
        String column_id = "column_id";
    }

    private @interface MainCalcHeadlineParams {
        String pub_area = "pub_area";
    }

    /***********************************
     * tools start
     ***********************************/
    /**
     * @param url
     * @return
     */
    private static Builder newPost(Context c, String url) {
        return new Builder(UrlUtil.getBaseUrl() + "?s=/api/" + url)
                .post()
                .param(getGlobalParams(c));
    }

    private static Builder newGet(Context c, String url) {
        return new Builder(UrlUtil.getBaseUrl())
                .get()
                .param(BaseParam.s, "/api/" + url)
                .param(getGlobalParams(c));
    }

    private static Builder newUpload(Context c, String url) {
        return new Builder(UrlUtil.getBaseUrl() + "?s=/api/" + url)
                .upload()
                .param(getGlobalParams(c));
    }

    public static Builder newDownload(String url, String dir, String fileName) {
        return new Builder(url)
                .downloadFile(dir, fileName);
    }
    /***********************************
     * tools-finish
     ***********************************/

    /**
     * 获取所有验证参数
     *
     * @return
     */
    public static List<NameValuePair> getGlobalParams(Context context) {
        List<NameValuePair> params = new ArrayList<>();

        params.add(getNameValuePair(BaseParam.device_os, "android"));

        return params;
    }

    private static NameValuePair getNameValuePair(String key, Object value) {
        NameValuePair pair = null;
        if (value != null) {
            if (value instanceof String) {
                pair = new NameValuePair(key, (String) value);
            } else {
                pair = new NameValuePair(key, String.valueOf(value));
            }
        }
        return pair;
    }

    /**
     * 下载文件任务
     *
     * @param url
     * @return
     */
    public static NetworkRequest downloadFile(String url, String dir, String fileName) {
        return newDownload(url, dir, fileName).build();
    }


}
