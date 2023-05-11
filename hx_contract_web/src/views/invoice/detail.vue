<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>开票编号：</span>
          {{ invoiceForm.invoiceNumber }}
        </div>
      </div>
    </div>
    <!--    开票信息 -->
    <div class="claim-header">
      <div style="display: flex;align-items: center">
        <div class="line-item"></div>
        <span>开票信息</span>
      </div>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>开票类型: 标准开票</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>订单名称: {{ invoiceForm.id }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>客户名称: {{ invoiceForm.soldToPartyCd }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>发票类型: {{ invoiceForm.invoiceType }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>买方银行: {{ invoiceForm.saleBank }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>收票方: {{ invoiceForm.consigneeId}}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>开户行: {{ invoiceForm.openingBank}}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>送货日期: {{ invoiceForm.arrivalDate }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>验收日期: {{ invoiceForm.checkDate }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>含税金额合计: {{ invoiceForm.totalAmountWithTax }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>不含税金额合计: {{ invoiceForm.totalAmountWithoutTax }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>是否退开票: {{ invoiceForm.amount }}</span>
          </div>
        </el-col>
      </el-row>
    </div>
    <!--    客户接受信息-->
    <div class="claim-header">
      <div style="display: flex;align-items: center">
        <div class="line-item"></div>
        <span>客户接受信息</span>
      </div>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>收件人: {{ customerGetMsg.name }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>联系电话: {{ customerGetMsg.phone }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>收货人地址: {{ customerGetMsg.address }}</span>
          </div>
        </el-col>
      </el-row>
    </div>
    <!--    开票明细-->
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>开票明细</span>
        </div>
      </div>
      <el-table
        :data="invoiceDetail"
        border
        style="margin-top: 15px"
        size="mini"
      >
        <el-table-column label="开票明细编号" align="center" prop="productNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="订单产品" align="center" prop="productModel" min-width="120px">
        </el-table-column>
        <el-table-column label="产品型号" align="center" prop="productModel" min-width="120px">
        </el-table-column>
        <el-table-column label="SAP明细编码" align="center" prop="sapDetailNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="产品名称" align="center" prop="productName" min-width="120px">
        </el-table-column>
        <el-table-column label="SAP物料编码" align="center" prop="planPayAmount" min-width="120px">
        </el-table-column>
        <el-table-column label="单位" align="center" prop="unit" min-width="120px">
        </el-table-column>
        <el-table-column label="财务软件编码" align="center" prop="sapFinancialCode" min-width="120px">
        </el-table-column>
        <el-table-column label="工厂" align="center" prop="factory" min-width="120px">
        </el-table-column>
        <el-table-column label="申请开票数量" align="center" prop="appliedQuantity" min-width="120px">
        </el-table-column>
        <el-table-column label="订单含税单价" align="center" prop="unitPrice" min-width="120px">
        </el-table-column>
      </el-table>
    </div>
    <!--    附件-->
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>附件</span>
        </div>
      </div>

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

import { getInvoiceDetail } from "../../api/invoice/index";

export default {
  name: "createClaim",
  dicts: ['claim_progress_status', 'sys_currency','approve_status'],
  data() {
    return {
      // 开票信息
      invoiceForm: [],
      // 客户收信息
      customerGetMsg: {},
      // 开票明细
      invoiceDetail: [],
      // 附件
      annex: [],
      // 接口日志列表
      logList: [],
    }
  },
  created() {
    this.getInvoiceInfo()
  },
  methods: {
    getInvoiceInfo(){
      const params = {id:this.$route.params.oid }
      getInvoiceDetail(params).then(res =>{
        this.invoiceForm = res.data.fcOrderInvoice
        this.invoiceDetail = res.data.fcOrderInvoiceDetail
        this.customerGetMsg = res.data.fcCustomerConsignment
      })
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
