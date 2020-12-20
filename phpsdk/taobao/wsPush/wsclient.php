<?php
 
/**
 * 蜂巢开放平台websocket客户端
 * 本客户端仅是数据通讯模型，开发者需要根据业务修改逻辑。
 * 重要提醒： 客户端确保能重连，因为服务器会有重启，导致长连接会被断开，所以确保客户端重连机制有效，否则将收不到消息。
 * 文档：http://help.fw199.com/docs/h7b/taobao-preface
 * 消息文档：http://help.fw199.com/docs/h7b/ws-message
 * Auth:Miller   
 * Date:2020-11-11
 */
require "./H7BWebSocket.php";
 

$server = 'open.fw199.com';
$port  = 443; 
$ssl = true;

// 开发者AppId
$appId= '你的appid';
// 开发者的密钥
$appSecret = '你的appSecret';

$token = md5($appSecret . $appId . $appSecret);
 
$path =  '/acc?appid=' . $appId . "&token=" . $token  .  "&version=v1.1" ."&clientid=server007"; 
$wsclient= new H7BWebSocket($server,$port,'', $errstr,$ssl, $path );  
$ok = $wsclient->Connect();
 
echo "Connecting to server: $server " .$ok . "\n"; 
$last_beat = time();
while (true) {
    try {
          
         $server_message = $wsclient->websocket_read($errstr); 
         
        if ($server_message !== false) { 
            echo 'receiver server msg：' . $server_message . "\r\n";   
            // 处理消息
            $server_response = json_decode($server_message,true);  

            /** 以下业务逻辑处理 */  
            $code = $server_response['code']; 
            if ($code  == 0) {
                // 正常的消息
               $topic = $server_response['topic']; 
                // 根据不同的$topic进行逻辑相应的topic， 见文档 
               // handle($server_message);


            } else {
                // 服务端有相应的错误返回过来
                $msg = $server_response['msg']; 
                echo $msg;
            }

             /** 如果是需要确认的消息，则确认此消息，否则服务端消息将会重发。*/
            $uuid = $server_response['uuid']; 
            if ($uuid != "") {
              $ack_cmd =  json_encode(array ('cmd'=>"ack_sync_data",'seq'=> $uuid ));
              $wsclient->websocket_write($ack_cmd);
            }

        }

        // 心跳
        $second =  time()  -  $last_beat ; 
        if ($second > 20 ) { 
            $last_beat = time();
            $result = $wsclient->websocket_write( '{"cmd":"beat"}'); 
            echo 'send beat to server, result:' . $result .  "\r\n"; 
            if ($result == 0){
                $wsclient->Connect();
            } 
        } 
 
    } catch (\WebSocket\ConnectionException $e) { 
               echo '连接异常: ' . $e->getMessage() . '#'.$e->getFile(). '#'. $e->getLine(). "\r\n"; 
                // 重新连接一下
                $wsclient = new H7BWebSocket($server, $port, '', $errstr, $ssl, $path);
                $ok = $wsclient->Connect();
                echo "reConnecting to server: $server " . $ok . "\n";
    }
}





    /**
     * 文档：http://help.fw199.com/docs/h7b/ws-message 
     * 请根据文档修改相应的业务逻辑。
     * @param $message
     */
 function handle($message)
    {
        $all_data = json_decode($message, true);
        if (isset($all_data['code']) && $all_data['code'] === 0) {
            $data = [];
            if (!empty($all_data['data'])) {
                if ($all_data['topic'] === 'tb_tradememo_modified_with_trade') {
                    $data = json_decode($all_data['data'], true);
                    // 从消息中获取相应的值，比如
                    $sellerNick = $data['trade_info']['seller_nick'];
                }
 

            }

            if (empty($seller_nick) || empty($data)) {
                return;
            }

            switch ($all_data['topic']) {
                //买家已付款 ，即等待卖家发货
                case 'tb_push_wait_seller_send_trade':
                    $data['orders'] = json_encode($data['orders'], JSON_UNESCAPED_UNICODE);
                    
                    break;
//                //买家已确认收货，即交易成功
//                case 'tb_push_success_trade':
//                    break;
//                //交易备注修改消息
//                case 'tb_tradememo_modified':
//                    break;
                //交易备注修改消息 ，附带订单信息
                case 'tb_tradememo_modified_with_trade':
                    //$data = json_encode($data['trade_info'], JSON_UNESCAPED_UNICODE);
                    $tradeMemo = json_decode($data['trade_memo'], true);
                    $data = $data['trade_info'];
                    
                    break;
//                //订单退款创建消息
//                case 'tb_refund_refundcreated':
//                    break;
//                //订单退款关闭消息
//                case 'tb_refund_refundclosed':
//                    break;
                //订单退款成功消息
                case 'tb_refund_refundsuccess':
                    
                    break;
                //卖家订购智能发货服务
                case 'tb_fuwu_seller_orderpaid':
                    
                    break;
                case 'tb_trade_tradesellership':
                    
                    break;
                default:
                    break;
            }
        }
    }


 
 
?>
