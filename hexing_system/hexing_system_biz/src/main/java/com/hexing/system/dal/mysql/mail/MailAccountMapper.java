package com.hexing.system.dal.mysql.mail;

import com.hexing.common.pojo.PageResult;
import com.hexing.database.mybatis.core.mapper.BaseMapperX;
import com.hexing.database.mybatis.core.query.LambdaQueryWrapperX;
import com.hexing.system.controller.admin.mail.vo.account.MailAccountPageReqVO;
import com.hexing.system.entity.mail.MailAccountDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailAccountMapper extends BaseMapperX<MailAccountDO> {

    default PageResult<MailAccountDO> selectPage(MailAccountPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<MailAccountDO>()
                .likeIfPresent(MailAccountDO::getMail, pageReqVO.getMail())
                .likeIfPresent(MailAccountDO::getUsername , pageReqVO.getUsername()));
    }

}
