package entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Identity {
    private String token; //token序列
    private String id; // 对应user的id
    private String issuer; //签发者
    private String role; // 角色
    private String userName; //用户名
    private Long duration; // 有效时长，单位毫秒
}
