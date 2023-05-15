<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>开票编号：</span>
          {{ fcOrderInvoice.invoiceNumber }}
        </div>
        <div>
          <el-button @click="approveInvoice(1)" type="primary" v-hasPermi="['invoice:approve:pass']"  v-show="invoiceForm.hasConsApprove && fcOrderInvoice.approvalStatus=='0'">
            审批通过</el-button>
          <el-button @click="approveInvoice(2)" type="primary" v-hasPermi="['invoice:approve:reject']"  v-show="invoiceForm.hasConsApprove && fcOrderInvoice.approvalStatus=='0'">
            审批驳回</el-button>
          <i class="el-icon-close" style="cursor: pointer;margin-left: 15px" @click="close"></i>
        </div>
      </div>
    </div>

    <el-descriptions title="开票信息" size="medium" border :column="3">
      <el-descriptions-item label="开票类型">标准开票</el-descriptions-item>
      <el-descriptions-item label="订单名称">{{ fcOrderInvoice.orderTitle}}</el-descriptions-item>
      <el-descriptions-item label="客户名称">{{ fcOrderInvoice.customer }}</el-descriptions-item>
      <el-descriptions-item label="发票类型">
        <dict-tag :options="dict.type.invoice_type" :value="fcOrderInvoice.invoiceType"/>
      </el-descriptions-item>
      <el-descriptions-item label="卖方银行">{{ fcOrderInvoice.saleBankName }}</el-descriptions-item>
      <el-descriptions-item label="收票方">{{ fcOrderInvoice.consigneeName }}</el-descriptions-item>
      <el-descriptions-item label="开户行">{{ invoiceForm.fcCustomerInvoice.openingBank }}</el-descriptions-item>
      <el-descriptions-item label="送货日期">{{ fcOrderInvoice.arrivalDate }}</el-descriptions-item>
      <el-descriptions-item label="验收日期">{{ fcOrderInvoice.checkDate }}</el-descriptions-item>
      <el-descriptions-item label="含税金额合计">{{ fcOrderInvoice.totalAmountWithTax }}</el-descriptions-item>
      <el-descriptions-item label="不含税金额合计">{{  fcOrderInvoice.totalAmountWithoutTax}}</el-descriptions-item>
    </el-descriptions>
    <el-descriptions title="客户接收信息" size="medium" border :column="3">
      <el-descriptions-item label="收件人">{{ fcCustomerConsignment.name }}</el-descriptions-item>
      <el-descriptions-item label="联系电话">{{ fcCustomerConsignment.phone }}</el-descriptions-item>
      <el-descriptions-item label="收货地址">{{ fcCustomerConsignment.location }}</el-descriptions-item>
      <el-descriptions-item label="收货人地址">{{ fcCustomerConsignment.address }}</el-descriptions-item>
    </el-descriptions>

    <!--    开票明细-->
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>开票明细</span>
        </div>
      </div>
      <el-table
        :data="fcOrderInvoiceDetail"
        border
        style="margin-top: 15px"
        size="mini"
      >
        <el-table-column label="行项目号" align="center" prop="product.sapDetailNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="产品型号" align="center" prop="product.productModel" min-width="120px">
        </el-table-column>
        <el-table-column label="产品名称" align="center" prop="product.productName" min-width="120px">
        </el-table-column>
        <el-table-column label="SAP物料编码" align="center" prop="product.productNumber" min-width="120px">
        </el-table-column>
        <el-table-column label="单位" align="center" prop="unit" min-width="120px">
        </el-table-column>
        <el-table-column label="财务软件编码" align="center" prop="sapFinancialCode" min-width="120px">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.finance_cate" :value="scope.row.sapFinancialCode"/>
          </template>
        </el-table-column>
        <el-table-column label="工厂" align="center" prop="factory" min-width="120px">
            {{ fcOrderInvoice.factory }}
        </el-table-column>
        <el-table-column label="申请开票数量" align="center" prop="appliedQuantity" min-width="120px">
        </el-table-column>
        <el-table-column label="订单含税单价" align="center" prop="invoicingUnitPriceWithTax" min-width="120px">
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
        <div style="padding: 15px">
          <el-link v-for="fileItem in invoiceForm.ossList"
                   :underline="false"  type="primary" target="_blank" :href="`${fileItem.url}`" class="fileShow">
            {{ fileItem.originalName }} </el-link>
        </div>
      </div>

    </div>

    <!--    接口日志-->
<!--    <div class="claim-header">
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
    </div>-->
  </div>
</template>

<script>

import {getInvoiceDetail,approveInvoice} from "@/api/invoice";

export default {
  name: "createClaim",
  dicts: ['invoice_type', 'finance_cate'],
  data() {
    return {
      // 开票信息
      invoiceForm: {},
      //主体
      fcOrderInvoice: {},
      // 客户收信息
      fcCustomerConsignment: {},
      // 开票明细
      fcOrderInvoiceDetail: [],
      // 附件
      annex: [],
      // 接口日志列表
      logList: [],
      id: null
    }
  },
  created() {
    this.getInvoiceInfo()
  },
  methods: {
    getInvoiceInfo(){
      const oid = this.$route.params.oid;
      const params = {id: oid}
      this.id = oid
      getInvoiceDetail(params).then(res =>{
        this.invoiceForm = res.data
        this.fcOrderInvoice = res.data.fcOrderInvoice
        this.fcOrderInvoiceDetail = res.data.fcOrderInvoiceDetail
        this.fcCustomerConsignment = res.data.fcCustomerConsignment
      })
    },
    close() {
    },
    approveInvoice(val) {
      const params = {id: this.id, approvalStatus: val}
      this.$modal.confirm('是否确认审批该开票？').then(function () {
        approveInvoice(params).then(res => {
          this.$modal.msgSuccess("审批成功");
        })
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
