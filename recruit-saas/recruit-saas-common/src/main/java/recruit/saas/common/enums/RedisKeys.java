package recruit.saas.common.enums;

/**
 * @author ZhangShenao
 * @date 2023/2/21 5:58 PM
 * Description 统一管理Redis Key
 */
public enum RedisKeys {
    //短信相关
    SMS_CODE("auth:sms:code:%s", "短信验证码", false, 60L * 5),
    SMS_CODE_IP_LOCK("auth:sms:code:lock:%s", "短信验证码IP锁", false, 60L * 5),

    
    ;
    private String key; //key
    private String desc;    //描述
    private boolean persist;    //是否为持久化key
    private long ttlInSec;  //key过期时间

    RedisKeys(String key, String desc, boolean persist, long ttlInSec) {
        this.key = key;
        this.desc = desc;
        this.persist = persist;
        this.ttlInSec = ttlInSec;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isPersist() {
        return persist;
    }

    public long getTtlInSec() {
        return ttlInSec;
    }
}
