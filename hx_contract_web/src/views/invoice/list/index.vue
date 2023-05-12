<template>
  <div class="app-container">
    <div class="angel-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-row>
          <el-col :span="6">
            <el-form-item label="开票编号" prop="invoiceNumber">
              <el-input
                v-model="queryParams.invoiceNumber"
                placeholder="请输入开票编号"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="审批状态" prop="approvalStatus">
              <el-select
                v-model="queryParams.approvalStatus"
                placeholder="请选择"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.approve_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item style="width:100%;text-align: right">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="angel-card-table">
      <el-table v-loading="loading" :data="deliveryList" border
                row-key="id">
        <el-table-column label="开票编码" align="center" key="invoiceNumber" prop="invoiceNumber"/>
        <el-table-column label="订单名称" align="center" key="orderTitle" prop="orderTitle"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="客户名称" align="center" key="customer" prop="customer"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="开票金额" align="center" prop="totalAmountWithTax">
        </el-table-column>
        <el-table-column label="审批状态" align="center" key="amount" prop="amount"
                         :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.approve_status" :value="scope.row.approvalStatus"/>
          </template>
        </el-table-column>
        <el-table-column label="创建日期" align="center" key="" prop="createTime"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="创建人" align="center" key="" prop="createBy"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="合同" align="center" key="" prop="contractName"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="所属办事处" align="center" key="" prop=""
                         :show-overflow-tooltip="true"/>
        <el-table-column label="事业部" align="center" key="" prop="businessUnit"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="工厂" align="center" key="" prop="factory"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="SAP同步时间" align="center" key="" prop="syncSapTime"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="SAP订单" align="center" key="" prop="orderNumber"
                         :show-overflow-tooltip="true"/>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              v-show="scope.row.approvalStatus == '2' || scope.row.approvalStatus == '3' || scope.row.approvalStatus == '4'"
              @click="updateHandle(scope.row)"
            >编辑
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="detail(scope.row)"
              v-hasPermi="['invoice:list:add']"
            >详情
            </el-button>
            <el-button
              size="mini"
              type="text"
              v-show="scope.row.approvalStatus == '0'"
              @click="revokeApprove(scope.row)"
            >撤销审批
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
import {listInvoice,approveInvoice} from "@/api/invoice";

export default {
  name: "index",
  dicts: ['approve_status'],
  data() {
    return {
      queryParams: {
        pageSize: 10,
        pageNum: 1,
        approvalStatus: null,
        invoiceNumber: null
      },
      loading: false,
      deliveryList: [],
      total: 0,
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList()
    },
    getList() {
      listInvoice(this.queryParams).then(res => {
        this.deliveryList = res.rows
        this.total = res.total
      })
    },
    /** 开票详情**/
    detail(row) {
      this.$router.push(`/invoice/detail/index/${row.id}`)
    },
    revokeApprove(row){
      const params = {id: row.id, approvalStatus: 3}
      this.$modal.confirm('确认撤销该开票的审批？').then(function () {
        approveInvoice(params).then(res => {
          this.$modal.msgSuccess("撤销成功");
          this.getList();
        })
      })
    },
    updateHandle(row) {
      this.$router.push(`/invoice/update/index/${row.id}`)
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    }
  }
}
</script>

<style scoped>

</style>
