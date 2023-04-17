package com.hexing.system.mq.message.permission;

import com.hexing.mq.core.pubsub.AbstractChannelMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户与角色的数据刷新 Message
 *
 * @author firerock_tech
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoleRefreshMessage extends AbstractChannelMessage {

    @Override
    public String getChannel() {
        return "system.user-role.refresh";
    }

}
