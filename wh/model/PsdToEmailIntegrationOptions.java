package wh.model;
// Generated May 2, 2015 9:19:20 PM by Hibernate Tools 3.2.1.GA



/**
 * PsdToEmailIntegrationOptions generated by hbm2java
 */
public class PsdToEmailIntegrationOptions  implements java.io.Serializable {


     private Long emailIntegrationId;
     private Boolean campaignMonitor;
     private Boolean mailChimp;
     private Boolean zohoCampaigns;

    public PsdToEmailIntegrationOptions() {
    }

    public PsdToEmailIntegrationOptions(Boolean campaignMonitor, Boolean mailChimp, Boolean zohoCampaigns) {
       this.campaignMonitor = campaignMonitor;
       this.mailChimp = mailChimp;
       this.zohoCampaigns = zohoCampaigns;
    }
   
    public Long getEmailIntegrationId() {
        return this.emailIntegrationId;
    }
    
    public void setEmailIntegrationId(Long emailIntegrationId) {
        this.emailIntegrationId = emailIntegrationId;
    }
    public Boolean getCampaignMonitor() {
        return this.campaignMonitor;
    }
    
    public void setCampaignMonitor(Boolean campaignMonitor) {
        this.campaignMonitor = campaignMonitor;
    }
    public Boolean getMailChimp() {
        return this.mailChimp;
    }
    
    public void setMailChimp(Boolean mailChimp) {
        this.mailChimp = mailChimp;
    }
    public Boolean getZohoCampaigns() {
        return this.zohoCampaigns;
    }
    
    public void setZohoCampaigns(Boolean zohoCampaigns) {
        this.zohoCampaigns = zohoCampaigns;
    }




}

