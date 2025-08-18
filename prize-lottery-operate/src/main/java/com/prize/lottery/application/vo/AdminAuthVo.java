package com.prize.lottery.application.vo;

import com.prize.lottery.infrast.persist.enums.AdminLevel;
import lombok.Data;

@Data
public class AdminAuthVo {

    private String   token;
    private String   name;
    private AuthRole role;
    private Long     expireAt;

    public void role(AdminLevel adminLevel) {
        this.role = new AuthRole(adminLevel);
    }

    @Data
    public static class AuthRole {
        private Integer level;
        private String  role;
        private String  roleName;

        public AuthRole(AdminLevel adminLevel) {
            this(adminLevel.getLevel(), adminLevel.getRole(), adminLevel.getName());
        }

        public AuthRole(Integer level, String role, String roleName) {
            this.level    = level;
            this.role     = role;
            this.roleName = roleName;
        }
    }
}
