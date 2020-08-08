<?php

 /* 此代码演示了如何进行拼多多的物流接口操作
	相关文档见http://help.fw199.com/docs/autosend/pddhome
	联系客服
 */
    $APP_ID = "aa" ;  // 开发者AppId
    $AppSecret = "bb";   // 开发者密钥
    $SellerNick  = "pdd928532xxx"; //要操作的拼多多商家，提前是商家要先授权给开发者
    $baseUrl =  "https://open.fw199.com"; 
      
    $LogisticesCompanyUrl = $baseUrl  .   "/gateway/pdd/logistices/getcompany";
    $LogisticesOnlineSendUrl = $baseUrl .   "/gateway/pdd/logistices/onlinesend";
	$PddOrderListUrl = $baseUrl .   "/gateway/pdd/order/list";
	$PddOrderDetailUrl = $baseUrl .   "/gateway/pdd/order/detail";
	
  // 获取物流公司列表
  function getLogisticesCompany() { 
		global $APP_ID;
		global $AppSecret;
		global $LogisticesCompanyUrl;
		global $SellerNick;

    	$apiHost = $LogisticesCompanyUrl; 
	    $config = array(
			"appid" =>   $APP_ID ,//平台ID号 
			"timestamp" => time()  , 
			"seller_nick" =>  $SellerNick   
		);
 
			 
			$sign =  _signParams($config, $AppSecret );
			$config['sign'] = $sign ;
			$response =   _curlPost( $apiHost, $config);  
		    // 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
			print $response;
		 
		  
	}
		

     // 对订单进行发货处理
	function LogisticesOnlineSend() { 
		global $APP_ID;
		global $AppSecret;
		global $LogisticesOnlineSendUrl;
		global $SellerNick;
	
			$apiHost = $LogisticesOnlineSendUrl; 
			$config = array(
				"appid" =>   $APP_ID , 
				"timestamp" => time()  , 
				"seller_nick" =>  $SellerNick ,  // 必填， 对应的卖家，注意卖家要先授权给开发者
				"order_sn" => "200803-563382026962221" , // 订单号
				"logistics_id" =>  '44', //必填，   快递公司编号， 可通过查询快递公司列表获取，注意是Id不是code
				"tracking_number" => "SF1191992347148" ,   // 必填，快递单号
				"refund_address_id" => "" ,   //  退货地址的id，不填则取商家默认地址， 非必填
				"feature" => "" ,   //  发货个性内容，非必填。 支持imei（手机串号），deviceSn（设备序列号）内容。
			); 
				$sign =  _signParams($config, $AppSecret );
				$config['sign'] = $sign ;
				$response =   _curlPost( $apiHost,$config); 
	 
				// 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
				print $response ;
			 
		}


	 // 获取商家店铺的订单列表
   	function getOrderList() { 
			global $APP_ID;
			global $AppSecret;
			global $PddOrderListUrl;
			global $SellerNick;
	
			$apiHost = $PddOrderListUrl;  
			// 必填        发货状态，1：待发货，2：已发货待签收，3：已签收 5：全部
			$order_status = "5";
			//必填	售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 5：全部
			$refund_status = "5"; 
			$page = "1"; 
    		$page_size = "20"; 
			$trade_type = "" ;
			 // 非必填	是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，
			 //但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，效率在原有的基础上有80%的提升。
			$use_has_next = "false"; 
			 // 必填，成交时间开始时间的时间戳，指格林威治时间 1970 年 01 月 01 日 00 时 00 分 00 秒
			 //(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数 
			$start_confirm_at = strtotime(date('Y-m-d H:i:s',strtotime('-1 day')));
 			// 必填，成交时间结束时间的时间戳, 开始时间和结束时间不要超过24小时
			$end_confirm_at = time(); 

			$config = array(
				"appid" =>   $APP_ID , 
				"timestamp" => time()  , 
				"seller_nick" =>  $SellerNick ,  // 必填， 对应的卖家，注意卖家要先授权给开发者
				"order_status" => $order_status , 
				"refund_status" =>  $refund_status , 
				"start_confirm_at" => $start_confirm_at  ,   
				"end_confirm_at" => $end_confirm_at ,   
				"page" => $page ,    
				"page_size" => $page_size ,    
				"trade_type" => $trade_type,
				"use_has_next" => $use_has_next 
 			); 
				$sign =  _signParams($config, $AppSecret );
				$config['sign'] = $sign ;
				$response =   _curlPost( $apiHost,$config); 
	 
				// 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
				print $response ;
			 
		}


		// 获取商家店铺的订单详情
		function getOrderDetail() { 
			global $APP_ID;
			global $AppSecret;
			global $PddOrderDetailUrl;
			global $SellerNick;

			$apiHost = $PddOrderDetailUrl;  
			// 订单号
			$order_sn = "200807-061031711860804";
			 
			$config = array(
				"appid" =>   $APP_ID , 
				"timestamp" => time()  , 
				"seller_nick" =>  $SellerNick ,  // 必填， 对应的卖家，注意卖家要先授权给开发者
				"order_sn" => $order_sn ,  
			); 
				$sign =  _signParams($config, $AppSecret );
				$config['sign'] = $sign ;
				$response =   _curlPost( $apiHost,$config);  
				// 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
				print $response ;
			
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

 
    // 获取物流公司信息接口 
    // getLogisticesCompany();

   //  拼多多物流发货
   // LogisticesOnlineSend();

	// 获取商家的订单列表
	// getOrderList();

	// 获取订单详情
	getOrderDetail();

?> 