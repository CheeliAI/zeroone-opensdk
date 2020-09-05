<?php

    $APP_ID = "aaa" ;  // 开发者AppId
    $AppSecret = "bbbbb";   // 开发者密钥
    $TBSellerNick  = "百鞋馆"; //要操作的商家，提前是商家要先授权给开发者
    $baseUrl = "https://open.fw199.com";
      
   
	  $TaoBaoCaiNiaoCloudPrintStdtemplatesUrl =$baseUrl  .    "/gateway/taobao/cainiao/cloudprint/getstdtemplates";
	  $TaoBaoCaiNiaoWayBillSearchUrl = $baseUrl  .  "/gateway/taobao/cainiao/waybill/search";
      $TaoBaoCaiNiaoWayBillGetUrl = $baseUrl  .    "/gateway/taobao/cainiao/waybill/get";



  // 获取所有的菜鸟标准电子面单模板
  function TaoBaoCaiNiaoCloudPrintStdtemplates() { 
		global $APP_ID;
		global $AppSecret;
		global $TaoBaoCaiNiaoCloudPrintStdtemplatesUrl;
		global $TBSellerNick;

    	$apiHost = $TaoBaoCaiNiaoCloudPrintStdtemplatesUrl; 
	    $config = array(
			"appid" =>   $APP_ID ,//平台ID号 
			"timestamp" => time()  , 
			"tb_seller_nick" =>  $TBSellerNick   
		);
 
			 
			$sign =  _signParams($config, $AppSecret );
			$config['sign'] = $sign ;
			$response =   _curlPost( $apiHost, $config);  
		    // 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
			print $response;
		 
		  
	}
		

   // 查询面单服务订购及面单使用情况
	function TaoBaoCaiNiaoWayBillSearch() { 
		global $APP_ID;
		global $AppSecret;
		global $TaoBaoCaiNiaoWayBillSearchUrl;
		global $TBSellerNick;
	
			$apiHost = $TaoBaoCaiNiaoWayBillSearchUrl; 
			$config = array(
				"appid" =>   $APP_ID , 
				"timestamp" => time()  , 
				"tb_seller_nick" =>  $TBSellerNick ,  
				"cp_code" => "ZTO" ,  //物流公司code,非必填	
			); 
				$sign =  _signParams($config, $AppSecret );
				$config['sign'] = $sign ;
				$response =   _curlPost( $apiHost,$config);  
				// 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
				print $response ;
			 
		}

		// 电子面单云打印接口
		function TaoBaoCaiNiaoWayBillGet() { 
			global $APP_ID;
			global $AppSecret;
			global $TaoBaoCaiNiaoWayBillGet;
			global $TBSellerNick;

			
			    //   // tradeOrderInfoDto
				//   CainiaoWaybillIiGetRequest.TradeOrderInfoDto tradeOrderInfoDto = new CainiaoWaybillIiGetRequest.TradeOrderInfoDto();
				//   // 如不需要特殊服务，该值为空
				//   tradeOrderInfoDto.setLogisticsServices("");
				//   //请求ID
				//   tradeOrderInfoDto.setObjectId("1"); // 必填
				//   // 云打印标准模板URL（组装云打印结果使用，值格式http://cloudprint.cainiao.com/template/standard/${模板ID}）
				//   tradeOrderInfoDto.setTemplateUrl("http://cloudprint.cainiao.com/template/standard/256771/9"); //必填
		  
				//   //使用者ID（使用电子面单账号的实际商家ID，如存在一个电子面单账号多个店铺使用时，请传入店铺的商家ID）
				//   tradeOrderInfoDto.setUserId(0L); //  必填,统一传0，则后台计算
		  
				//   CainiaoWaybillIiGetRequest.OrderInfoDto orderInfoDto = new CainiaoWaybillIiGetRequest.OrderInfoDto();
				//   // 	订单渠道平台编码
				//   orderInfoDto.setOrderChannelsType("TB");// 必填 TM	天猫  JD	京东 PIN_DUO_DUO 拼多多
				//   //订单号,数量限制100，订单号（只限传入数字、字母、下划线和中划线，为避免出现冲突，请按电商平台真实订单号传入，请避免使用同个订单号重复取号）
				//   List<String >  tids = new ArrayList<>();
				//   tids.add("90020206078841641229"); //   必填 订单号
				//   orderInfoDto.setTradeOrderList(tids); // 必填
				//   tradeOrderInfoDto.setOrderInfo(orderInfoDto);
				//   listTradeOrderInfoDto.add(tradeOrderInfoDto);
		  
				//   // 包裹
				//   CainiaoWaybillIiGetRequest.PackageInfoDto packageInfoDto = new CainiaoWaybillIiGetRequest.PackageInfoDto();
				//   // 包裹id，用于拆合单场景（只能传入数字、字母和下划线；批量请求时值不得重复，大小写敏感，即123A,123a 不可当做不同ID，否则存在一定可能取号失败）
				//   packageInfoDto.setId("1");
				//   List<CainiaoWaybillIiGetRequest.Item> items  = new ArrayList<CainiaoWaybillIiGetRequest.Item>();
				//   CainiaoWaybillIiGetRequest.Item item = new CainiaoWaybillIiGetRequest.Item();
				//   item.setCount(50L); // 数量 ， 必填
				//   item.setName("名片"); // 宝贝名称，  必填
				//   items.add(item);
				//   packageInfoDto.setItems(items); // 必填
				//   packageInfoDto.setVolume(1L); //体积, 单位 ml
				//   packageInfoDto.setWeight(1L); // 重量,单位 g
		  
				//   tradeOrderInfoDto.setPackageInfo(packageInfoDto);
				//   // 收件人
				//   CainiaoWaybillIiGetRequest.UserInfoDto receUserInfoDto= new CainiaoWaybillIiGetRequest.UserInfoDto();
				//   CainiaoWaybillIiGetRequest.AddressDto receAddressDto  = new CainiaoWaybillIiGetRequest.AddressDto();
		  
				//   receUserInfoDto.setMobile("13357035578");
				//   receUserInfoDto.setName("洪勇军修改-智能发货"); // 必填 姓名
				//   receUserInfoDto.setPhone("057123222233");
				//   receAddressDto.setCity("杭州市");
				//   receAddressDto.setDetail("横村镇桐千路898号华艺大酒店单身公寓10楼");  //// 必填  详细地址
				//   receAddressDto.setDistrict("桐庐县");
				//   receAddressDto.setProvince("浙江省");
				//   receAddressDto.setTown("横村镇");
				//   receUserInfoDto.setAddress(receAddressDto);
				//   tradeOrderInfoDto.setRecipient(receUserInfoDto);
				//   newRequest.setTradeOrderInfoDtos(listTradeOrderInfoDto);
				//   // 仓code， 仓库WMS系统对接落地配业务，其它场景请不要使用
				//   newRequest.setStoreCode("");
				//   // 配送资源code， 仓库WMS系统对接落地配业务，其它场景请不要使用
				//   newRequest.setResourceCode("");
				//   // 是否使用智分宝预分拣， 仓库WMS系统对接落地配业务，其它场景请不要使用
				//   newRequest.setDmsSorting(false);

            /** 本请求内容为json,参数字段说明见上面字段注释  */
			 $reqeustData ="{\"cp_code\":\"ZTO\",\"dms_sorting\":false,\"resource_code\":\"\",\"sender\":{\"address\":{\"city\":\"长沙市\",\"detail\":\"望城大道61号丰树物流园三期（于野彩卡）\",\"district\":\"望城区\",\"province\":\"湖南省\"},\"mobile\":\"1326443654\",\"name\":\"XX印务-as\",\"phone\":\"0571232222\"},\"store_code\":\"\",\"trade_order_info_dtos\":[{\"object_id\":\"1\",\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"90020206078841641229\"]},\"package_info\":{\"id\":\"1\",\"items\":[{\"count\":50,\"name\":\"名片\"}],\"volume\":1,\"weight\":1},\"recipient\":{\"address\":{\"city\":\"杭州市\",\"detail\":\"横村镇桐千路8118号华艺大酒店单身公寓12楼\",\"district\":\"桐庐县\",\"province\":\"浙江省\",\"town\":\"横村镇\"},\"mobile\":\"13357035578\",\"name\":\"洪勇军修改-智能发货\",\"phone\":\"057123222233\"},\"template_url\":\"http:\\/\\/cloudprint.cainiao.com\\/template\\/standard\\/256771\\/9\",\"user_id\":0}]}";


				$apiHost = $TaoBaoCaiNiaoWayBillGet; 
				$config = array(
					"appid" =>   $APP_ID , 
					"timestamp" => time()  , 
					"tb_seller_nick" =>  $TBSellerNick ,  
					"request_data" => $reqeustData  ,  //电子面单的发货数据。
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

  
 
?> 