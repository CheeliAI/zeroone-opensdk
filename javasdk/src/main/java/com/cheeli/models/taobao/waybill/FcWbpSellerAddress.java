package com.cheeli.models.taobao.waybill;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * 蜂巢打单，卖家地址库。与淘宝地址库保持一致对象 fc_wbp_seller_address
 *
 * @author hanon
 * @date 2021-08-20
 */
public class FcWbpSellerAddress
{
    private static final long serialVersionUID = 1L;

    /** 自增 */
    private Long id;


    private String sellerNick;


    private Long contactId;

    /** 联系人姓名 长度不可超过20个字节 */

    private String contactName;

    /** 所在省 */

    private String province;

    /** 所在市 */

    private String city;

    /** 区、县 */

    private String country;

    /** 详细街道地址，不需要重复填写省/市/区 */

    private String addr;

    /** 地区邮政编码 */

    private String zipCode;

    /** 电话号码,手机与电话必需有一个 */

    private String phone;

    /** 电话号码,手机与电话必需有一个 */

    private String mobilePhone;

    /** 公司名称, */

    private String sellerCompany;

    /** 备注,备注不能超过256字节 */

    private String memo;

    /** 默认取货地址。选择此项(true,1)，将当前地址设为默认取货地址，撤消原默认取货地址 */
    private Integer getDef;

    /** 默认退货地址。选择此项(true, 1)，将当前地址设为默认退货地址，撤消原默认退货地址 */
    private Integer cancelDef;

    /** 区域ID */
    private Long areaId;

    /** 是否默认发货地址, 是为1，不是为0 */
    private Integer sendDef;

    /** 修改日期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date modifyDate;

    private String town;

    private Long partnerId;

    private Long op;

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getOp() {
        return op;
    }

    public void setOp(Long op) {
        this.op = op;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSellerNick(String sellerNick)
    {
        this.sellerNick = sellerNick;
    }

    public String getSellerNick()
    {
        return sellerNick;
    }
    public void setContactId(Long contactId)
    {
        this.contactId = contactId;
    }

    public Long getContactId()
    {
        return contactId;
    }
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    public String getContactName()
    {
        return contactName;
    }
    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getProvince()
    {
        return province;
    }
    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCity()
    {
        return city;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCountry()
    {
        return country;
    }
    public void setAddr(String addr)
    {
        this.addr = addr;
    }

    public String getAddr()
    {
        return addr;
    }
    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getZipCode()
    {
        return zipCode;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public String getMobilePhone()
    {
        return mobilePhone;
    }
    public void setSellerCompany(String sellerCompany)
    {
        this.sellerCompany = sellerCompany;
    }

    public String getSellerCompany()
    {
        return sellerCompany;
    }
    public void setMemo(String memo)
    {
        this.memo = memo;
    }

    public String getMemo()
    {
        return memo;
    }
    public void setGetDef(Integer getDef)
    {
        this.getDef = getDef;
    }

    public Integer getGetDef()
    {
        return getDef;
    }
    public void setCancelDef(Integer cancelDef)
    {
        this.cancelDef = cancelDef;
    }

    public Integer getCancelDef()
    {
        return cancelDef;
    }
    public void setAreaId(Long areaId)
    {
        this.areaId = areaId;
    }

    public Long getAreaId()
    {
        return areaId;
    }
    public void setSendDef(Integer sendDef)
    {
        this.sendDef = sendDef;
    }

    public Integer getSendDef()
    {
        return sendDef;
    }
    public void setModifyDate(Date modifyDate)
    {
        this.modifyDate = modifyDate;
    }

    public Date getModifyDate()
    {
        return modifyDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sellerNick", getSellerNick())
                .append("contactId", getContactId())
                .append("contactName", getContactName())
                .append("province", getProvince())
                .append("city", getCity())
                .append("country", getCountry())
                .append("addr", getAddr())
                .append("zipCode", getZipCode())
                .append("phone", getPhone())
                .append("mobilePhone", getMobilePhone())
                .append("sellerCompany", getSellerCompany())
                .append("memo", getMemo())
                .append("getDef", getGetDef())
                .append("cancelDef", getCancelDef())
                .append("areaId", getAreaId())
                .append("sendDef", getSendDef())
                .append("modifyDate", getModifyDate())
                .toString();
    }
}