<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>回款编号：</span>
          {{ paymentDetail.paymentNumber }}
        </div>
        <i class="el-icon-close" style="cursor: pointer" @click="close"></i>
      </div>
    </div>
    <div class="claim-header">
      <div style="display: flex;align-items: center">
        <div class="line-item"></div>
        <span>回款信息</span>
      </div>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>客户回款编号: {{ paymentDetail.paymentNumber }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>凭证编号: {{ paymentDetail.documentNumber }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>公司名称: {{ paymentDetail.corporateName }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>客户名称: {{ paymentDetail.customerName }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>回款币种: {{ paymentDetail.paymentCurrency }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>到账金额: {{ paymentDetail.receivedAmount }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>回款已分配金额: {{ paymentDetail.allocatedAmount }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>回款未分配金额: {{ paymentDetail.undistributedAmount }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>负责人: {{ paymentDetail.diretor }}</span>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>认领单</span>
        </div>
      </div>
      <el-table
        :data="claimList"
        border
        style="margin-top: 15px"
        size="mini"
      >
        <el-table-column label="回款认领时间" align="center" prop="createTime" min-width="120px">
        </el-table-column>
        <el-table-column label="同步SAP时间" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="认领单编号" align="center" prop="claimNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="认领金额" align="center" prop="claimAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="认领客户" align="center" prop="syncSapInfoTime" min-width="120px">
          <template slot-scope="scope">
            <span>{{ paymentDetail.customerName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status" min-width="120px">
          <template slot-scope="scope">
            <span>{{ scope.row.status == "0" ? "已撤销" : "已同步SAP" }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope" v-if="scope.row.status !='0'">
            <el-button
              size="mini"
              type="text"
              @click="cancel(scope.row)"
              v-hasPermi="['claim:list:cancel']"
            >撤销
            </el-button>
          </template>
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
import {addClaim, cancelClaim, getPayment, listPaymentClaim} from "@/api/payment";
import {listCusOrder} from "@/api/order";
import {delUser} from "@/api/system/user";

export default {
  name: "createClaim",
  dicts: ['claim_progress_status', 'sys_currency'],
  data() {
    return {
      paymentDetail: {},
      form: [],
      order: [],
      buttonLoading: false,
      claimList: []
    }
  },
  created() {
    this.getPayment()
  },
  methods: {
    getPayment() {
      const params = {id: this.$route.params.pid}
      getPayment(params).then(res => {
        this.paymentDetail = res.data
        this.getClaim()
      })
    },
    getClaim() {
      const params = {
        paymentId: this.paymentDetail.id,
        pageSize: 100,
        pageNum: 1
      }
      listPaymentClaim(params).then(res => {
        this.claimList = res.rows
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
