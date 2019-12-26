package cn.changyou.item.bo;

import cn.changyou.item.pojo.Sku;
import cn.changyou.item.pojo.Spu;
import cn.changyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * @author xgl
 * @create 2019-12-20 16:48
 */
public class SpuBo extends Spu {
    private String cname;
    private String bname;
    private List<Sku> skus;
    private SpuDetail spuDetail;

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    @Override
    public String toString() {
        return "SpuBo{" +
                "cname='" + cname + '\'' +
                ", bname='" + bname + '\'' +
                ", skus=" + skus +
                ", spuDetail=" + spuDetail +
                '}';
    }
}
