package generate;

import com.hexing.system.domain.vo.FcApprove;

public interface FcApproveDao {
    int deleteByPrimaryKey(Integer id);

    int insert(FcApprove record);

    int insertSelective(FcApprove record);

    FcApprove selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FcApprove record);

    int updateByPrimaryKey(FcApprove record);
}