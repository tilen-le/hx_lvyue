package com.hexing.system.service;

import com.hexing.system.domain.FcApproveUser;

/**
 * @author firerock_tech
 */
public interface IFcApproveUserService {

    int saveFcApproveUser(FcApproveUser fcApproveUser);
    int updateFcApproveUser(FcApproveUser fcApproveUser);

}
