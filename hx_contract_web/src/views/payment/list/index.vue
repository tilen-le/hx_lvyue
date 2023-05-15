<template>
  <div class="app-container">
    <div class="angel-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-row>
          <el-col :span="6">
            <el-form-item label="客户回款编号" prop="paymentNumber">
              <el-input
                v-model="queryParams.paymentNumber"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户" prop="customerName">
              <el-input
                v-model="queryParams.customerName"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="认领状态" prop="claimStatus">
              <el-select
                v-model="queryParams.claimStatus"
                placeholder="请选择"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.payment_claim_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item style="width:100%;text-align: right">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery" v-hasPermi="['payment:list:query']">查询</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery" v-hasPermi="['payment:list:reset']">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="angel-card-table">
      <el-table v-loading="loading" :data="paymentList" border>
        <el-table-column label="客户回款编号" align="center" key="paymentNumber" prop="paymentNumber"/>
        <el-table-column label="凭证编号" align="center" key="documentNumber" prop="documentNumber"/>
        <el-table-column label="公司名称" align="center" key="corporateName" prop="corporateName">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_receive_master" :value="scope.row.corporateName"/>
            </template>
        </el-table-column>
        <el-table-column label="过账日期" align="center" key="postingDate" prop="postingDate">
            <template slot-scope="scope">
              {{ parseTime(scope.row.postingDate, '{y}-{m}-{d}') }}
          </template>
        </el-table-column>
        <el-table-column label="回款币种" align="center" key="paymentCurrency" prop="paymentCurrency"/>
        <el-table-column label="到账金额" align="center" key="receivedAmount" prop="receivedAmount"/>
        <el-table-column label="回款已分配金额" align="center" key="allocatedAmount" prop="allocatedAmount"/>
        <el-table-column label="创建日期" align="center" key="createTime" prop="createTime"/>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope" v-if="scope.row.userId !== 1">
            <el-button
              size="mini"
              type="text"
              @click="detail(scope.row)"
              v-hasPermi="['payment:list:detail']"
            >详情
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="createClaim(scope.row)"
              v-hasPermi="['payment:claim:add']"
            >回款认领
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
import {listPayment} from "@/api/payment";

export default {
  name: "index",
  dicts: ['payment_claim_status','sys_receive_master'],
  data() {
    return {
      queryParams: {
        pageSize: 10,
        pageNum: 1
      },
      loading: false,
      paymentList: [],
      total: 0,

    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleQuery() {

      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    createClaim(row){
      this.$router.push(`/claim/create/index/${row.id}`)
    },
    getList() {
      listPayment(this.queryParams).then(res => {
        this.paymentList = res.rows
        this.total = res.total
      })
    },
    detail(row) {
      this.$router.push(`/payment/detail/index/${row.id}`)
    }
  }
}
</script>

<style scoped>

</style>
