package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeHandler(type = Type.JSON)
public class OmitValue implements Serializable {

    private static final long serialVersionUID = 6100603372513750574L;

    private String  key;
    private Integer value;
    private String  extra = "";

    public static OmitValue of(String key, Integer value) {
        return new OmitValue(key, value, "");
    }

}
