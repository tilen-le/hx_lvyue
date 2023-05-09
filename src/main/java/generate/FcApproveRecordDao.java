package generate;

import com.hexing.system.domain.vo.FcApproveRecord;

public interface FcApproveRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(FcApproveRecord record);

    int insertSelective(FcApproveRecord record);

    FcApproveRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FcApproveRecord record);

    int updateByPrimaryKey(FcApproveRecord record);
}