package com.prize.lottery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SmsCheckCmd implements Serializable {

    private static final long serialVersionUID = 3802123428808736369L;

    private String phone;
    private String channel;
    private String code;


    public SmsCheckCmd(String phone, String code, SmsChannel channel) {
        this.phone   = phone;
        this.channel = channel.getChannel();
        this.code    = code;
    }
}
