using System;
namespace H7BSDK
{
    public class TaoBaoTrade
    {
        /// <summary>
           /// 合作伙伴AppId	
           /// </summary>
        public string appid { get; set; }

        /// <summary>
        /// 当前时间戳
        /// </summary>
        public string timestamp { get; set; }

        /// <summary>
        /// 淘宝卖家店铺登录账号，非店铺名称
        /// </summary>
        public string tb_seller_nick { get; set; }
        /// <summary>
        /// 口签名	如何计算生成见示例代码
        /// </summary>
        public string sign { get; set; }

        /// <summary>
        /// 必填， Date	查询三个月内交易创建时间开始。格式:yyyy-MM-dd HH:mm:ss	
        /// </summary>
        public string start_created { get; set; }

        /// <summary>
        /// 必填， Date	查询交易创建时间结束。格式:yyyy-MM-dd HH:mm:ss	
        /// </summary>
        public string end_created { get; set; }
        /// <summary>
        /// 非必填   买家昵称，如果不按某个买家查询，此值可传空字符串
        /// </summary>
        public string buyer_nick { get; set; }
        /// <summary>
        /// 可选， 交易状态 ，默认查询所有交易状态的数据，
        /// 除了默认值外每次只能查询一种状态。
        /// WAIT_BUYER_PAY：等待买家付款 
        /// WAIT_SELLER_SEND_GOODS：等待卖家发货 、
        /// SELLER_CONSIGNED_PART：卖家部分发货 
        /// WAIT_BUYER_CONFIRM_GOODS：等待买家确认收货
        /// TRADE_BUYER_SIGNED：买家已签收（货到付款专用）
        /// TRADE_FINISHED：交易成功 
        /// TRADE_CLOSED：交易关闭	
        /// </summary>
        public string status { get; set; }
        /// <summary>
        /// 可选, 当前页,默认1	
        /// </summary>
        public int page_no { get; set; }
        /// <summary>
        /// 可选， 每页大小， 最大30
        /// </summary>
        public int page_size { get; set; }
        /// <summary>
        /// 是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数total_results（此值一直为0） ，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，接口调用成功率在原有的基础上有所提升。
        /// </summary>
        public bool use_has_next { get; set; }

    
    }
}
