  
<?php
 


// Encrypt code, please  chnage it  !  加密 
function CheeliEncrypt($data , $CheeliPrivateKey) { 
  
    $iv     = $CheeliPrivateKey; 
    $encrypted = mcrypt_encrypt(MCRYPT_RIJNDAEL_128, $CheeliPrivateKey , $data, MCRYPT_MODE_CBC, $iv);
    return base64_encode($encrypted);
}


   function http_post_data($url, $data_string) {

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $data_string);
		curl_setopt($ch, CURLOPT_HTTPHEADER, array(
			'Content-Type: application/json; charset=utf-8',
			'Content-Length: ' . strlen($data_string))
		);
        ob_start();
        curl_exec($ch);
        $return_content = ob_get_contents();
        ob_end_clean();

        $return_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        return array($return_code, $return_content);
    }

  
  $url  = "http://tbapi.fw199.com/tbbridge/trade/sendgoods"; 
 
           
$userkey  = "201572ji29km22@1"; //private key  ，please change ! 自行修改
$sellerid =  606498133; //卖家ID please change !  自行修改
$tid = "1220430421535830" ;// 订单号  please change !  自行修改
$encryptedtid =CheeliEncrypt($tid, $userkey);//  please change the encrypt implement function  !   对$tid进行AES加密, !  自行修改


$token = md5($userkey . $tid  );
$buyer_nick = "卖家张三";

$encrypted_buyer_nick =CheeliEncrypt($buyer_nick, $userkey)//  please change the encrypt implement function !  买家的昵称， 需要aes128 加密, 自行修改
$buyer_message ="很好";//buyer_message, 买家留言,自行修改
$buyer_email ="sc@163.com";//买家邮件 
$alipay_no ="381471347183413";//支付宝号
 
$data = json_encode(array('tb_seller_id'=>$sellerid , 'tid'=>$encryptedtid, 'token'=>$token,'buyer_nick'=>$encrypted_buyer_nick,  'buyer_message' => $buyer_message,  'buyer_email' => $buyer_email, 'alipay_no' =>$alipay_no,
	'orders' =>
	 array(
        array('price' =>"10.11" ,  'num_iid' =>317483147, 'payment'=>"10.11", 'title'=>'盛大游戏卡',  'num' =>1, 'outer_iid'=> '', 'outer_sku_id' =>'', 'content'=>"please go to www.g2a.com download" )
     )
));
 
      

//echo $data; 
 list($return_code, $return_content) = http_post_data($url, $data);  
 echo $return_content; 

?>
 