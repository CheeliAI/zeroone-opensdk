using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Web;

namespace H7BSDK
{
    class MainClass
    {
        
        public static void Main(string[] args)
        {
            Console.WriteLine("开始获取订单列表");
            GetTradeList();

        }

        /// <summary>
        /// 获取淘宝订单列表
        /// 接口文档：http://help.fw199.com/docs/h7b/tradelist
        /// </summary>
        public static void GetTradeList()
        {

            string url = "https://open.fw199.com/gateway/taobao/order/list"; 
            long ts = H7BUtil.GetTimeStamp();
            var requestArgs = new Dictionary<string, string>()
            {
               
                  {"tb_seller_nick","源丰有限贸易"},
                  {"buyer_nick",""},
                  {"start_created","2021-06-01 00:00:00"},
                  {"end_created","2021-06-10 00:00:00"},
                  {"status","WAIT_SELLER_SEND_GOODS"},
                  {"page_no","1"},
                  {"page_size","30"},
                  {"use_has_next","true"},
                  {"timestamp", ts.ToString()}


            };
            string response = H7BUtil.HttpPost(url, requestArgs);
            Console.WriteLine(response);


        }

    }
}