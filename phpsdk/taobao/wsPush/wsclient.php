<?php
 
/**
 * 蜂巢开放平台websocket客户端
 * http://help.fw199.com/docs/h7b/taobao-preface
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
        if ($second > 10 ) { 
            $last_beat = time();
            $result = $wsclient->websocket_write( '{"cmd":"beat"}'); 
            echo 'send beat to server, result:' . $result .  "\r\n"; 
            if ($result == 0){
                $wsclient->Connect();
            } 
        } 
 
    } catch (\WebSocket\ConnectionException $e) { 
        echo  '连接异常: ' . $e . "\r\n";
    }
}
 
 
?>
