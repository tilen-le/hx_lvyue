<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>订单编号：</span>
          {{ orderDetail.order.orderNumber }}
        </div>
        <div>
<!--          在该订单有库存的情况下，显示发货，点击进入穿件发货界面-->
          <el-button :loading="buttonLoading" type="primary" @click="toDelivery">发货</el-button>
<!--          在该订单存在在途库时显示开票操作，点击进入创建开票界面-->
          <el-button @click="toInvoice" type="primary">申请开票</el-button>
          <i class="el-icon-close" style="cursor: pointer;margin-left: 15px" @click="close"></i>
        </div>
      </div>
    </div>
<!--    订单基本信息-->
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
            <span>合同: {{ null==orderDetail.contract?"":orderDetail.contract.contractName }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>SAP合同号: {{ null==orderDetail.contract?"":orderDetail.contract.contractNumber }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>库存状态: {{ orderDetail.storeStatus}}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>发货状态: {{ orderDetail.deliveryStatus}}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>SAP创建时间: {{ parseTime(orderDetail.order.sapCreateTime, '{y}-{m}-{d}') }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>售达方: {{ orderDetail.order.soldToParty }}</span>
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
            <span>是否备表直发: {{ orderDetail.order.isBackupTableDirectly }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>送货方/收货方: {{ orderDetail.order.reciver }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>要求交货日期: {{ parseTime(orderDetail.order.requireDeliveryDate, '{y}-{m}-{d}') }}</span>
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
            <span>收票方: {{ orderDetail.order.billee }}</span>
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
            <span>收款方: {{ orderDetail.order.payee }}</span>
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
            <span>预计发货日期: </span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>预计到货日期: </span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>预计验收日期: </span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>预计质保日期: </span>
          </div>
        </el-col>
      </el-row>
    </div>
<!--    订单产品信息-->
    <div class="claim-header">
      <div style="display: flex;align-items: center">
        <div class="line-item"></div>
        <span>订单产品信息</span>
      </div>
      <el-table
        :data="orderProductList"
        border
        style="margin-top: 15px"
        size="mini"
      >
        <el-table-column label="产品编号" align="center" prop="productNumber" min-width="120px"/>
        <el-table-column label="产品型号" align="center" prop="productModel" min-width="120px"/>
        <el-table-column label="产品名称" align="center" prop="productName" min-width="120px"/>
        <el-table-column label="数量" align="center" prop="num" min-width="120px"/>
        <el-table-column label="单价" align="center" prop="unitPrice" min-width="120px">
          <template scope="scope"> {{ orderDetail.order.currency+scope.row.unitPrice }} </template>
        </el-table-column>
        <el-table-column label="入库数" align="center" prop="inStorageNum" min-width="120px"/>
        <el-table-column label="在途" align="center" prop="inTransitNum" min-width="120px"/>
        <el-table-column label="未发" align="center" prop="notSentNum" min-width="120px"/>
      </el-table>
    </div>
<!--    收款里程碑-->
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>收款里程碑</span>
        </div>
      </div>
      <el-table
        :data="orderMilestoneList"
        border
        style="margin-top: 15px"
        size="mini"
      >
        <el-table-column label="收款里程碑" align="center" prop="type" min-width="120px">
        </el-table-column>
        <el-table-column label="预计回款时间" align="center" prop="expectPayDate" min-width="120px">
        </el-table-column>
        <el-table-column label="预计回款比例" align="center" prop="expectPayScale" min-width="120px">
          <template scope="scope"> {{ scope.row.expectPayScale }}% </template>
        </el-table-column>
        <el-table-column label="剩余回款金额" align="center" prop="surplusPayAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="计划回款金额" align="center" prop="planPayAmount" min-width="120px">
        </el-table-column>
      </el-table>
    </div>
<!--    发货单-->
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>发货单明细</span>
        </div>
      </div>
      <el-table
        :data="orderConsignmentDetailList"
        border
        style="margin-top: 15px"
        size="mini">
        <el-table-column label="发货明细编号" align="center" prop="consigmentNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="记录类型" align="center" prop="saleType" min-width="120px">
        </el-table-column>
        <el-table-column label="发货总金额" align="center" prop="amount" min-width="120px">
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" min-width="120px">
        </el-table-column>
        <el-table-column label="订单名称" align="center" prop="orderTitle" min-width="120px">
        </el-table-column>
        <el-table-column label="审批状态" align="center" prop="approvalStatus" min-width="120px">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.approve_status" :value="scope.row.approvalStatus"/>
          </template>
        </el-table-column>
        <el-table-column label="客户" align="center" prop="customer" min-width="120px">
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
              @click="orderConsignmentDetail(scope.row)"
            >查看详情
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="cancel(scope.row)"
            >撤销
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="cancel(scope.row)"
            >催办
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
<!--    认领单-->
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
        <el-table-column label="认领单编号" align="center" prop="claimNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="回款编号" align="center" prop="paymentNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="认领币种" align="center" prop="claimCurrency" min-width="120px">
        </el-table-column>
        <el-table-column label="认领金额" align="center" prop="claimAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="回款币种" align="center" prop="paymentCurrency" min-width="120px">
        </el-table-column>
        <el-table-column label="回款金额" align="center" prop="receivedAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="回款类型" align="center" prop="paymentType" min-width="120px">
        </el-table-column>
        <el-table-column label="回款日期" align="center" prop="postingDate" min-width="120px">
        </el-table-column>
        <el-table-column label="创建日期" align="center" prop="syncSapTime" min-width="120px">
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
              @click="paymentCliamDetail(scope.row)"
              v-hasPermi="['claim:list:cancel']"
            >查看详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
<!--    接口日志-->
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>接口日志</span>
        </div>
      </div>
      <el-table
        :data="logList"
        border
        style="margin-top: 15px"
        size="mini">
        <el-table-column label="执行时间" align="center" prop="createTime" min-width="120px">
        </el-table-column>
        <el-table-column label="所属模块" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="接口ID" align="center" prop="claimNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="接口名" align="center" prop="claimAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="状态" align="center" prop="syncSapInfoTime" min-width="120px">
        </el-table-column>
        <el-table-column label="日志信息" align="center" prop="syncSapInfoTime" min-width="120px">
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
            >接口重推
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import {addClaim, cancelClaim, getPayment, listPaymentClaim} from "@/api/payment";
import {getOrderDetail, listCusOrder} from "@/api/order";
import {delUser} from "@/api/system/user";

export default {
  name: "createClaim",
  dicts: ['claim_progress_status', 'sys_currency','approve_status'],
  data() {
    return {
      orderDetail: {},
      form: [],
      order: [],
      buttonLoading: false,
      // 认领单列表
      claimList: [],
      // 产品信息列表
      orderProductList: [],
      // 发货单明细列表
      orderConsignmentDetailList: [],
      // 回款单列表
      orderMilestoneList: [],
      // 接口日志列表
      logList: [],
    }
  },
  created() {
    this.getPayment()
  },
  methods: {
    // 获取订单详情
    getPayment() {
      const params = {id: this.$route.params.oid}
      getOrderDetail(params).then(res => {
        this.orderDetail = res.data
        this.orderProductList = res.data.products
        this.orderMilestoneList =res.data.milestones
        this.claimList=res.data.paymentClaims
        this.orderConsignmentDetailList=res.data.consignments
      })
    },
    // 获取认领单列表
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
    // 获取日志列表
    getLogList() {
      const params = {
        paymentId: this.paymentDetail.id,
        pageSize: 100,
        pageNum: 1
      }
      listPaymentClaim(params).then(res => {
        this.logList = res.rows
      })
    },
    handleRemovePrice(idx, index) {
      this.form.splice(index, 1);
    },
    addAllAmount(idx, index) {
      idx.amount = idx.unAmount
    },
    // 跳转开票
    toInvoice() {
      this.$router.push(`/invoice/create/index/${this.orderDetail.order.id}`)
    },
    // 跳转创建发货界面
    toDelivery() {
      this.$router.push(`/delivery/create/index/${this.orderDetail.order.id}`)
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
    },
    // 查看发货单详情
    orderConsignmentDetail(row) {
      this.$router.push(`/order/detail/index/${row.id}`)
    },
    // 查看认领单详情
    paymentCliamDetail(row) {
      this.$router.push(`/claim/detail/index/${row.claimId}`)
    },
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
