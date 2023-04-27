<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>订单编号：</span>
          {{ orderDetail.order.orderNumber }}
        </div>
        <div>
          <el-button :loading="buttonLoading" type="primary" @click="toDelivery">发货</el-button>
          <el-button @click="toInvoice" type="primary">申请开票</el-button>
          <i class="el-icon-close" style="cursor: pointer;margin-left: 15px" @click="close"></i>
        </div>
      </div>
    </div>
    <div class="claim-header">
      <div style="display: flex;align-items: center">
        <div class="line-item"></div>
        <span>订单基本信息</span>
      </div>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>订单编号: {{ orderDetail.order.orderNumber }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>订单标题: {{ orderDetail.order.orderTitle }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>币种: {{ orderDetail.order.currency }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>合同: {{ orderDetail.contract.contractName }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>SAP合同号: {{ orderDetail.contract.contractNumber }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>订单状态: {{ orderDetail.receivedAmount }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>SPA创建时间: {{ orderDetail.order.sapCreateTime }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>售达方: {{ orderDetail.order.soldToPartyCd }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>订单金额: {{ orderDetail.order.amount }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>是否备表直发: {{ orderDetail.order.payeeCd }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>送货方/收货方: {{ orderDetail.order.reciverCd }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>要求交货日期: {{ orderDetail.order.requireDeliveryDate }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>客户经理: {{ orderDetail.order.customerManager }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>收票方: {{ orderDetail.order.bileeCd }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>事业部: {{ orderDetail.order.businessUnit }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>销售组织: {{ orderDetail.order.saleOrg }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>销售类型: {{ orderDetail.order.saleType }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>收款方: {{ orderDetail.order.payeeCd }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>分销渠道: {{ orderDetail.order.distributionChannel }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>国内/国际营销部: {{ orderDetail.order.marketingDepartment }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>预计发货日期: {{ orderDetail.order.saleType }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>预计到货日期: {{ orderDetail.order.payeeCd }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>预计验收日期: {{ orderDetail.order.distributionChannel }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>预计质保日期: {{ orderDetail.order.marketingDepartment }}</span>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="claim-header">
      <div style="display: flex;align-items: center">
        <div class="line-item"></div>
        <span>订单产品信息</span>
      </div>
      <el-table
        :data="claimList"
        border
        style="margin-top: 15px"
        size="mini"
      >
        <el-table-column label="产品编号" align="center" prop="createTime" min-width="120px"/>
        <el-table-column label="产品型号" align="center" prop="createTime" min-width="120px"/>
        <el-table-column label="产品名称" align="center" prop="createTime" min-width="120px"/>
        <el-table-column label="数量" align="center" prop="createTime" min-width="120px"/>
        <el-table-column label="单价" align="center" prop="createTime" min-width="120px"/>
        <el-table-column label="入库数" align="center" prop="createTime" min-width="120px"/>
        <el-table-column label="在途" align="center" prop="createTime" min-width="120px"/>
        <el-table-column label="未发" align="center" prop="createTime" min-width="120px"/>
      </el-table>
    </div>
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>收款里程碑</span>
        </div>
      </div>
      <el-table
        :data="claimList"
        border
        style="margin-top: 15px"
        size="mini"
      >
        <el-table-column label="收款里程碑" align="center" prop="createTime" min-width="120px">
        </el-table-column>
        <el-table-column label="预计回款时间" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="预计回款比例" align="center" prop="claimNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="剩余回款金额" align="center" prop="claimAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="计划回款金额" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
      </el-table>
    </div>
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>发货单明细</span>
        </div>
      </div>
      <el-table
        :data="claimList"
        border
        style="margin-top: 15px"
        size="mini">
        <el-table-column label="发货明细编号" align="center" prop="createTime" min-width="120px">
        </el-table-column>
        <el-table-column label="记录类型" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="发货总金额" align="center" prop="claimNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="claimAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="订单名称" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="审批状态" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="客户" align="center" prop="syncSapInfoTime" min-width="120px">
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
            >查看详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
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
        size="mini">
        <el-table-column label="认领单编号" align="center" prop="createTime" min-width="120px">
        </el-table-column>
        <el-table-column label="回款编号" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="认领币种" align="center" prop="claimNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="认领金额" align="center" prop="claimAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="回款币种" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="回款金额" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="回款类型" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="回款日期" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="创建日期" align="center" prop="syncSapInfoTime" min-width="120px">
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
            >查看详情
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
import {getOrderDetail, listCusOrder} from "@/api/order";
import {delUser} from "@/api/system/user";

export default {
  name: "createClaim",
  dicts: ['claim_progress_status', 'sys_currency'],
  data() {
    return {
      orderDetail: {},
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
      const params = {id: this.$route.params.oid}
      getOrderDetail(params).then(res => {
        this.orderDetail = res.data
        // this.getClaim()
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
    toInvoice() {
      this.$router.push(`/invoice/create/index/${this.orderDetail.order.id}`)
    },
    toDelivery() {

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
