package generate;

import com.hexing.system.domain.vo.FcApproveUser;

public interface FcApproveUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(FcApproveUser record);

    int insertSelective(FcApproveUser record);

    FcApproveUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FcApproveUser record);

    int updateByPrimaryKey(FcApproveUser record);
}