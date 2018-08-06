package ${package}.common.models;


import java.io.Serializable;

public class PageReq implements Serializable {
    private Integer pageSize;
    private Integer pageNum;

    public Integer getPageSize() {
        return pageSize;
    }

    public PageReq setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public PageReq setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public void pretreatment(int defaultPageSize, int defaultPageNum) {
        if (this.pageNum == null || this.pageNum < 1) {
            setPageNum(defaultPageNum);
        }

        if (this.pageSize == null || this.pageSize > 1000 || this.pageSize <= 0) {
            setPageSize(defaultPageSize);
        }
    }

    public void pretreatment(int pageSize) {
        pretreatment(pageSize, 1);
    }

    public void pretreatment() {
        pretreatment(20, 1);
    }

}
