package recruit.saas.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 举报职位
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-03-02
 */
public class ReportJob implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 被举报的职位id
     */
    private String jobId;

    /**
     * 举报人id
     */
    private String reportUserId;

    /**
     * 举报人姓名
     */
    private String reportUserName;

    /**
     * 举报原因
     */
    private String reportReason;

    /**
     * 被举报的职位名称
     */
    private String jobName;

    /**
     * 被举报的公司名称
     */
    private String companyName;

    /**
     * 处理状态：0：待处理，1：已处理，2：忽略、无需处理
     */
    private Integer dealStatus;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(String reportUserId) {
        this.reportUserId = reportUserId;
    }

    public String getReportUserName() {
        return reportUserName;
    }

    public void setReportUserName(String reportUserName) {
        this.reportUserName = reportUserName;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "ReportJob{" +
        "id=" + id +
        ", jobId=" + jobId +
        ", reportUserId=" + reportUserId +
        ", reportUserName=" + reportUserName +
        ", reportReason=" + reportReason +
        ", jobName=" + jobName +
        ", companyName=" + companyName +
        ", dealStatus=" + dealStatus +
        ", createdTime=" + createdTime +
        ", updatedTime=" + updatedTime +
        "}";
    }
}
