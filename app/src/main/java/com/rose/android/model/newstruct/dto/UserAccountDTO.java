package com.rose.android.model.newstruct.dto;

public class UserAccountDTO {

    private Integer id;
    private String kid;
    private Long cash_asset;
    private Long cash_frozen_asset;
    private Long cash_frozen_promotion_fee;
    private Long total_cash_deposit;
    private Long total_cash_consumption;
    private Long total_cash_promotion_fee;
    private Long total_cash_withdraw;
    private String comment;
    private String create_time;
    private String update_time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public Long getCash_asset() {
        return cash_asset;
    }

    public void setCash_asset(Long cash_asset) {
        this.cash_asset = cash_asset;
    }

    public Long getCash_frozen_asset() {
        return cash_frozen_asset;
    }

    public void setCash_frozen_asset(Long cash_frozen_asset) {
        this.cash_frozen_asset = cash_frozen_asset;
    }

    public Long getCash_frozen_promotion_fee() {
        return cash_frozen_promotion_fee;
    }

    public void setCash_frozen_promotion_fee(Long cash_frozen_promotion_fee) {
        this.cash_frozen_promotion_fee = cash_frozen_promotion_fee;
    }

    public Long getTotal_cash_deposit() {
        return total_cash_deposit;
    }

    public void setTotal_cash_deposit(Long total_cash_deposit) {
        this.total_cash_deposit = total_cash_deposit;
    }

    public Long getTotal_cash_consumption() {
        return total_cash_consumption;
    }

    public void setTotal_cash_consumption(Long total_cash_consumption) {
        this.total_cash_consumption = total_cash_consumption;
    }

    public Long getTotal_cash_promotion_fee() {
        return total_cash_promotion_fee;
    }

    public void setTotal_cash_promotion_fee(Long total_cash_promotion_fee) {
        this.total_cash_promotion_fee = total_cash_promotion_fee;
    }

    public Long getTotal_cash_withdraw() {
        return total_cash_withdraw;
    }

    public void setTotal_cash_withdraw(Long total_cash_withdraw) {
        this.total_cash_withdraw = total_cash_withdraw;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
