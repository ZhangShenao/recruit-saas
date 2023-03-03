package recruit.saas.user.service.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 运营管理系统的admin账户表，仅登录，不提供注册
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-03-02
 */
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户混合加密的盐
     */
    private String salt;

    /**
     * 备注
     */
    private String remark;

    /**
     * 头像
     */
    private String face;

    private LocalDateTime createTime;

    private LocalDateTime updatedTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "Admin{" +
        "id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", salt=" + salt +
        ", remark=" + remark +
        ", face=" + face +
        ", createTime=" + createTime +
        ", updatedTime=" + updatedTime +
        "}";
    }
}
