package com.rose.android.model;

import java.util.List;

/**
 * Created by wenen on 2017/12/6.
 */

public class MNoticeModel extends BaseModel {

  /**
   * data : {"newsList":[{"title":"天舟文化：控股股东减持意为提前化解财务风险","link":"http://finance.eastmoney.com/news/1354,20171206809938675.html","summary":null,"source":null,"createTime":"2017-12-06 15:40:00","timeText":"4分钟前","imageUrl":"http://jinniubao.oss-cn-shenzhen.aliyuncs.com/financialNews/7.png"},{"title":"海南：商品住宅销售擅自提价或停发预售许可证","link":"http://finance.eastmoney.com/news/1355,20171206809938488.html","summary":null,"source":null,"createTime":"2017-12-06 15:40:00","timeText":"4分钟前","imageUrl":"http://jinniubao.oss-cn-shenzhen.aliyuncs.com/financialNews/8.png"}]}
   * page : {"pageNo":1,"pageSize":10}
   * total : 10
   */

  private DataBean data;
  private PageBean page;
  private int total;

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public PageBean getPage() {
    return page;
  }

  public void setPage(PageBean page) {
    this.page = page;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public static class DataBean {
    private List<NewsListBean> newsList;

    public List<NewsListBean> getNewsList() {
      return newsList;
    }

    public void setNewsList(List<NewsListBean> newsList) {
      this.newsList = newsList;
    }

    public static class NewsListBean {
      /**
       * title : 天舟文化：控股股东减持意为提前化解财务风险
       * link : http://finance.eastmoney.com/news/1354,20171206809938675.html
       * summary : null
       * source : null
       * createTime : 2017-12-06 15:40:00
       * timeText : 4分钟前
       * imageUrl : http://jinniubao.oss-cn-shenzhen.aliyuncs.com/financialNews/7.png
       */

      private String title;
      private String link;
      private Object summary;
      private Object source;
      private String createTime;
      private String timeText;
      private String imageUrl;

      public String getTitle() {
        return title;
      }

      public void setTitle(String title) {
        this.title = title;
      }

      public String getLink() {
        return link;
      }

      public void setLink(String link) {
        this.link = link;
      }

      public Object getSummary() {
        return summary;
      }

      public void setSummary(Object summary) {
        this.summary = summary;
      }

      public Object getSource() {
        return source;
      }

      public void setSource(Object source) {
        this.source = source;
      }

      public String getCreateTime() {
        return createTime;
      }

      public void setCreateTime(String createTime) {
        this.createTime = createTime;
      }

      public String getTimeText() {
        return timeText;
      }

      public void setTimeText(String timeText) {
        this.timeText = timeText;
      }

      public String getImageUrl() {
        return imageUrl;
      }

      public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
      }
    }
  }

  public static class PageBean {
    /**
     * pageNo : 1
     * pageSize : 10
     */

    private int pageNo;
    private int pageSize;

    public int getPageNo() {
      return pageNo;
    }

    public void setPageNo(int pageNo) {
      this.pageNo = pageNo;
    }

    public int getPageSize() {
      return pageSize;
    }

    public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
    }
  }
}
