<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>认领单编号：</span>
          {{ claimDetail.claimNumber }}
        </div>
        <i class="el-icon-close" style="cursor: pointer" @click="close"></i>
      </div>
    </div>
    <div class="claim-header">
      <div style="display: flex;align-items: center">
        <div class="line-item"></div>
        <span>认领单信息</span>
      </div>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>回款金额: {{ claimDetail.receivedAmount }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>回款编号: {{ claimDetail.paymentNumber }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>回款币种: {{ claimDetail.paymentCurrency }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>同步SAP时间: {{ claimDetail.syncSapInfoTime }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>认领金额: {{ claimDetail.claimAmount }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>同步SAP状态: {{ claimDetail.syncSapStatus }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>同步SAP信息: {{ claimDetail.syncSapInfo }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>同步SAP信息时间: {{ claimDetail.syncSapInfoTime }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>客户: {{ claimDetail.customerName }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>认领单状态: {{ claimDetail.status == '1' ? '生效' : '失效' }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>撤销同步SAP时间: {{ claimDetail.cancelSapTime }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>撤销同步SAP信息时间: {{ claimDetail.cancelSapInfoTime }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>撤销同步SAP信息: {{ claimDetail.cancelSapInfo }}</span>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>认领单明细</span>
        </div>
      </div>
      <el-table
        :data="claimList"
        border
        style="margin-top: 15px"
        size="mini"
      >
        <el-table-column label="认领单编号" align="center" prop="claimNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="订单编号" align="center" prop="orderNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="订单名称" align="center" prop="orderTitle" min-width="120px">
        </el-table-column>
        <el-table-column label="认领里程碑" align="center" prop="milestonesId" min-width="120px">
          <template slot-scope="scope">
            <span>{{ scope.row.milestonesId }}</span>
          </template>
        </el-table-column>
        <el-table-column label="认领币种" align="center" prop="currency" min-width="120px">
        </el-table-column>
        <el-table-column label="计划回款金额" align="center" prop="receivedAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="已回款" align="center" prop="allocatedAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="未回款" align="center" prop="undistributedAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="本次认领" align="center" prop="amount" min-width="120px">
        </el-table-column>
      </el-table>
    </div>
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>接口日志</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {addClaim, cancelClaim, getPayment, listClaimDetail, listClaimDetailList, listPaymentClaim} from "@/api/payment";
import {listCusOrder} from "@/api/order";
import {delUser} from "@/api/system/user";

export default {
  name: "createClaim",
  dicts: ['claim_progress_status', 'sys_currency'],
  data() {
    return {
      claimDetail: {},
      form: [],
      order: [],
      buttonLoading: false,
      claimList: []
    }
  },
  created() {
    this.getClaimDetail()
  },
  methods: {
    getClaimDetail() {
      const params = {id: this.$route.params.cid}
      listClaimDetail(params).then(res => {
        this.claimDetail = res.data
        this.getClaim()
      })
    },
    getClaim() {
      const params = {
        id: this.claimDetail.id,
      }
      listClaimDetailList(params).then(res => {
        this.claimList = res.data
        console.log(this.claimList)
      })
    },
    handleRemovePrice(idx, index) {
      this.form.splice(index, 1);
    },
    addAllAmount(idx, index) {
      idx.amount = idx.unAmount
    },
    close() {
      this.form = []
      this.$router.go(-1)
    },
    cancel(row) {
      this.$modal.confirm('是否确认撤销该笔认领单数据项？').then(function () {
        const params = {
          id: row.id
        }
        return cancelClaim(params);
      }).then(() => {
        this.getClaim()
        this.$modal.msgSuccess("撤销成功");
      }).catch(() => {
      });
    }
  }
}
</script>

<style scoped lang="scss">
.claim-header {
  background: white;
  border-radius: 4px;
  margin-top: 15px;
  padding-top: 15px;
  padding-bottom: 10px;
  width: 100%;
}

.line-item {
  background: blue;
  height: 15px;
  margin-right: 5px;
  border-bottom-right-radius: 5px;
  border-top-right-radius: 5px;
  width: 4px
}
</style>
