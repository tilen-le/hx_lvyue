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
            <el-form-item label="订单" prop="orderTitle">
              <el-input
                v-model="queryParams.orderTitle"
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
          <el-col :span="12">
            <el-form-item style="width:100%;text-align: right">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery" has-permi="['claim:all:list']">查询</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery" has-permi="['claim:list:reset']">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="angel-card-table">
      <el-table v-loading="loading" :data="paymentList" border>
        <el-table-column label="认领单编号" align="center" key="claimNumber" prop="claimNumber"/>
        <el-table-column label="客户回款编号" align="center" key="paymentNumber" prop="paymentNumber"/>
        <el-table-column label="认领金额" align="center" key="claimAmount" prop="claimAmount"/>
        <el-table-column label="回款币种" align="center" key="paymentCurrency" prop="paymentCurrency"/>
        <el-table-column label="回款金额" align="center" key="receivedAmount" prop="receivedAmount"/>
        <el-table-column label="客户" align="center" key="customerName" prop="customerName"/>
        <el-table-column label="回款日期" align="center" key="postingDate" prop="postingDate"/>
        <el-table-column label="认领人" align="center" key="nickName" prop="nickName"/>
        <el-table-column label="认领单创建日期" align="center" key="createTime" prop="createTime"/>
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
              @click="detail(scope.row)"
              v-hasPermi="['claim:list:detail']"
            >详情
            </el-button>
            <el-button
              size="mini"
              type="text"
              v-if="scope.row.status !='1'"
              @click="createClaim(scope.row)"
              v-hasPermi="['claim:list:cancel']"
            >撤销
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
import {cancelClaim, listAllClaim, listPayment} from "@/api/payment";

export default {
  name: "index",
  dicts: ['payment_claim_status'],
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
    createClaim(row) {
      this.$modal.confirm('是否确认撤销该笔认领单数据项？').then(function () {
        const params = {
          id: row.id
        }
        return cancelClaim(params);
      }).then(() => {
        this.handleQuery()
        this.$modal.msgSuccess("撤销成功");
      }).catch(() => {
      });
    },
    getList() {
      listAllClaim(this.queryParams).then(res => {
        this.paymentList = res.rows
        this.total = res.total
      })
    },
    detail(row) {
      this.$router.push(`/claim/detail/index/${row.id}`)
    }
  }
}
</script>

<style scoped>

</style>
