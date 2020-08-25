<?php

     
    $APP_ID = "aa" ;  // 开发者AppId
    $AppSecret = "bb";   // 开发者密钥
    $TBSellerNick  = "百鞋馆"; //要操作的商家，提前是商家要先授权给开发者
    $baseUrl = "https://open.fw199.com";
      
    $OrderListUrl = $baseUrl  .   "/gateway/taobao/order/list";
    $OrderDetailUrl = $baseUrl .   "/gateway/taobao/order/detail";


	// 获取淘宝订单列表 , 
	// 接口说明：http://help.fw199.com/docs/autosend/autosend-tb
  function getOrderList() { 
		global $APP_ID;
		global $AppSecret;
		global $OrderListUrl;
		global $TBSellerNick;

		$apiHost = $OrderListUrl; 
		$start_confirm_at = date("Y-m-d H:i:s",strtotime('-14 day') );
	    $end_confirm_at = date("Y-m-d H:i:s", time() );  
 
	    $config = array(
			"appid" =>   $APP_ID , 
			"timestamp" => time()  , 
			"tb_seller_nick" =>  $TBSellerNick  ,   
			"start_created" => $start_confirm_at ,     
			"end_created" =>  $end_confirm_at ,    
			"status" =>  '',   
			"buyer_nick" =>  '',   
			"page_no" =>  '1', 
			"page_size" =>  '20',   
			"use_has_next" =>  'false' 
		);
 
			 
			$sign =  _signParams($config, $AppSecret );
			$config['sign'] = $sign ;

		     
			$response =   _curlPost( $apiHost, $config);  
		    // 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
			print $response;
		 
		  
	}
		


	 // 获取淘宝订单详情 , 
	// 接口说明：http://help.fw199.com/docs/autosend/autosend-tb
	function getOrderDetail() { 
		global $APP_ID;
		global $AppSecret;
		global $OrderDetailUrl;
		global $TBSellerNick;

    	$apiHost = $OrderDetailUrl; 
 
			$config = array (
				"appid" =>   $APP_ID , 
				"timestamp" => time()  , 
				"tb_seller_nick" =>  $TBSellerNick  ,   
				"tid" =>  "1189348257255565830"  
			 );

			$sign =  _signParams($config, $AppSecret );
			$config['sign'] = $sign ;
			$response =   _curlPost( $apiHost, $config);  
		    // 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
			print $response;
		 
		  
	}



	  function _curlPost($url,$params){
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL,$url);
		curl_setopt($ch, CURLOPT_HEADER, 0);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_TIMEOUT,300); //设置超时
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE); // https请求 不验证证书和hosts
		curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, FALSE);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $params);
		$result = curl_exec($ch);
		curl_close($ch);
		return $result;	
	}



  function _signParams($params , $secret) {
	$sign = $signstr = "";
	if(!empty($params)){
		ksort($params);
		reset($params);
		
		foreach ($params AS $key => $val) {
			if ($key == 'sign') { 
				 continue; 
			}
			$signstr .= $key.$val;
		}
		$sign = md5($secret.$signstr.$secret);
	}
	return $sign;
  
  }

 
  // 获取订单列表
    getOrderList();

   // 获取订单详情
     getOrderDetail();

?> 