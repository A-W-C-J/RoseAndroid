package com.rose.android.model;

import java.util.List;

/**
 * Created by wenen on 2017/10/26.
 */

public class MTaskModel extends BaseModel {

  /**
   * data : {"taskList":[{"title":"新手任务","list":[{"id":10,"enable":1,"status":0,"type":1,"maxProcess":100,"currentProcess":0,"name":"完成注册","hint":"","description":"完成注册","iconUrl":"","toProtocol":"","toUrl":""},{"id":11,"enable":1,"status":0,"type":1,"maxProcess":100,"currentProcess":0,"name":"实名认证","hint":"","description":"实名认证","iconUrl":"","toProtocol":"","toUrl":""},{"id":12,"enable":1,"status":0,"type":1,"maxProcess":100,"currentProcess":0,"name":"绑定银行卡","hint":"","description":"绑定银行卡","iconUrl":"","toProtocol":"","toUrl":""},{"id":13,"enable":1,"status":0,"type":1,"maxProcess":100,"currentProcess":0,"name":"首次配资","hint":"","description":"除体验合约","iconUrl":"","toProtocol":"","toUrl":""}]},{"title":"常规任务","list":[{"id":14,"enable":1,"status":0,"type":2,"maxProcess":100,"currentProcess":0,"name":"首次上传头像","hint":"","description":"首次上传头像","iconUrl":"","toProtocol":"","toUrl":""},{"id":15,"enable":1,"status":0,"type":2,"maxProcess":100,"currentProcess":0,"name":"完成新手问答","hint":"","description":"完成新手问答","iconUrl":"","toProtocol":"","toUrl":""}]}]}
   */

  private DataBean data;

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public static class DataBean {
    private List<TaskListBean> taskList;

    public List<TaskListBean> getTaskList() {
      return taskList;
    }

    public void setTaskList(List<TaskListBean> taskList) {
      this.taskList = taskList;
    }

    public static class TaskListBean {
      /**
       * title : 新手任务
       * list : [{"id":10,"enable":1,"status":0,"type":1,"maxProcess":100,"currentProcess":0,"name":"完成注册","hint":"","description":"完成注册","iconUrl":"","toProtocol":"","toUrl":""},{"id":11,"enable":1,"status":0,"type":1,"maxProcess":100,"currentProcess":0,"name":"实名认证","hint":"","description":"实名认证","iconUrl":"","toProtocol":"","toUrl":""},{"id":12,"enable":1,"status":0,"type":1,"maxProcess":100,"currentProcess":0,"name":"绑定银行卡","hint":"","description":"绑定银行卡","iconUrl":"","toProtocol":"","toUrl":""},{"id":13,"enable":1,"status":0,"type":1,"maxProcess":100,"currentProcess":0,"name":"首次配资","hint":"","description":"除体验合约","iconUrl":"","toProtocol":"","toUrl":""}]
       */

      private String title;
      private List<ListBean> list;

      public String getTitle() {
        return title;
      }

      public void setTitle(String title) {
        this.title = title;
      }

      public List<ListBean> getList() {
        return list;
      }

      public void setList(List<ListBean> list) {
        this.list = list;
      }

      public static class ListBean {
        /**
         * id : 10
         * enable : 1
         * status : 0
         * type : 1
         * maxProcess : 100
         * currentProcess : 0
         * name : 完成注册
         * hint :
         * description : 完成注册
         * iconUrl :
         * toProtocol :
         * toUrl :
         */

        private int id;
        private int enable;
        private int status;
        private int type;
        private int maxProcess;
        private int currentProcess;
        private String name;
        private String hint;
        private String description;
        private String iconUrl;
        private String toProtocol;
        private String toUrl;

        public int getId() {
          return id;
        }

        public void setId(int id) {
          this.id = id;
        }

        public int getEnable() {
          return enable;
        }

        public void setEnable(int enable) {
          this.enable = enable;
        }

        public int getStatus() {
          return status;
        }

        public void setStatus(int status) {
          this.status = status;
        }

        public int getType() {
          return type;
        }

        public void setType(int type) {
          this.type = type;
        }

        public int getMaxProcess() {
          return maxProcess;
        }

        public void setMaxProcess(int maxProcess) {
          this.maxProcess = maxProcess;
        }

        public int getCurrentProcess() {
          return currentProcess;
        }

        public void setCurrentProcess(int currentProcess) {
          this.currentProcess = currentProcess;
        }

        public String getName() {
          return name;
        }

        public void setName(String name) {
          this.name = name;
        }

        public String getHint() {
          return hint;
        }

        public void setHint(String hint) {
          this.hint = hint;
        }

        public String getDescription() {
          return description;
        }

        public void setDescription(String description) {
          this.description = description;
        }

        public String getIconUrl() {
          return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
          this.iconUrl = iconUrl;
        }

        public String getToProtocol() {
          return toProtocol;
        }

        public void setToProtocol(String toProtocol) {
          this.toProtocol = toProtocol;
        }

        public String getToUrl() {
          return toUrl;
        }

        public void setToUrl(String toUrl) {
          this.toUrl = toUrl;
        }
      }
    }
  }
}
