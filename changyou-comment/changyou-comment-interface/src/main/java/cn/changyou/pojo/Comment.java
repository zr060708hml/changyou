package cn.changyou.pojo;

import javax.persistence.*;

/**
 * @author GM
 * @create 2020-01-08 12:17
 */

@Table(name = "cy_remake")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;     //评论id
    private Long skuId;  //sku表中的商品id
    private Long spuId;  //spu表中的商品id
    private Long userId; //用户id
    private String remakeContent; //评论内容
    private String remakeImg; //评论图片,两张之间图片以逗号隔开
    private Boolean isParent; //是不是1级评论
    private Long parentId; //父级id
    private Integer goodScore; //商品评分
    private Integer serviceScore;//服务评分
    private Long orderId; //订单id
    private Long replyNum;//评论数量
    private boolean is_unread;//是否已读

    @Transient
    private String userImg;

    @Transient
    private String nickName;

    public boolean getUnread() {
        return is_unread;
    }

    public void setUnread(boolean is_unread) {
        this.is_unread = is_unread;
    }


    public Long getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Long replyNum) {
        this.replyNum = replyNum;
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRemakeContent() {
        return remakeContent;
    }

    public void setRemakeContent(String remakeContent) {
        this.remakeContent = remakeContent;
    }

    public String getRemakeImg() {
        return remakeImg;
    }

    public void setRemakeImg(String remakeImg) {
        this.remakeImg = remakeImg;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getGoodScore() {
        return goodScore;
    }

    public void setGoodScore(Integer goodScore) {
        this.goodScore = goodScore;
    }

    public Integer getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Integer serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", spuId=" + spuId +
                ", userId=" + userId +
                ", remakeContent='" + remakeContent + '\'' +
                ", remakeImg='" + remakeImg + '\'' +
                ", isParent=" + isParent +
                ", parentId=" + parentId +
                ", goodScore=" + goodScore +
                ", serviceScore=" + serviceScore +
                ", orderId=" + orderId +
                ", replyNum=" + replyNum +
                ", userImg='" + userImg + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
