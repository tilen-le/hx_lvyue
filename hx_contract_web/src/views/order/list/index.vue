<template>
  <div class="app-container">
    <div class="angel-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-row>
          <el-col :span="6">
            <el-form-item label="订单编号" prop="orderNumber">
              <el-input
                v-model="queryParams.orderNumber"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收货人" prop="reciver">
              <el-input
                v-model="queryParams.reciver"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="发货状态" prop="consignmentStatus">
              <el-select
                v-model="queryParams.consignmentStatus"
                placeholder="请选择"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.delivery_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="库存状态" prop="storeStatus">
              <el-select
                v-model="queryParams.storeStatus"
                placeholder="请选择"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.store_status"
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
      <el-row :gutter="10" class="mb8">
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
      </el-row>
    </div>
    <div class="angel-card-table">
      <el-table v-loading="loading" :data="customerList" border
                row-key="id">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-table :data="props.row.products"
                      row-key="id">
              <el-table-column label="产品编码" align="center" key="productNumber" prop="productNumber"/>
              <el-table-column label="产品名称" align="center" key="productName" prop="productName"/>
              <el-table-column label="订单数量" align="center" key="num" prop="num"/>
              <el-table-column label="单价" align="center" key="unitPrice" prop="unitPrice"/>
              <el-table-column label="库存数量" align="center" key="inStorageNum" prop="inStorageNum"/>
              <el-table-column label="已发货数量" align="center" key="inTransitNum" prop="inTransitNum"/>
              <el-table-column label="未发货数量" align="center" key="notSentNum" prop="notSentNum"/>
              <el-table-column label="在途数量" align="center" key="inTransitNum" prop="inTransitNum"/>
            </el-table>
          </template>
        </el-table-column>
        <el-table-column label="订单编号" align="center" key="orderNumber" prop="orderNumber"
                         v-if="columns[0].visible"/>
        <el-table-column label="订单名称" align="center" key="orderTitle" prop="orderTitle" v-if="columns[1].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="客户名称" align="center" key="soldToParty" prop="soldToParty"
                         v-if="columns[2].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="发货状态" align="center" key="status" v-if="columns[3].visible">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.delivery_status" :value="scope.row.consignmentStatus"/>
          </template>
        </el-table-column>
        <el-table-column label="订单总金额" align="center" key="amount" prop="amount"
                         v-if="columns[4].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="订单总数量" align="center" key="sum" prop="sum"
                         v-if="columns[5].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="在途库数量" align="center" key="sumInTransitNum" prop="sumInTransitNum"
                         v-if="columns[6].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="合同编号" align="center" key="contractNumber" prop="contractNumber"
                         v-if="columns[7].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="合同名称" align="center" key="contractName" prop="contractName"
                         v-if="columns[8].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="预计发货时间" align="center" key="requireDeliveryDate" prop="requireDeliveryDate"
                         v-if="columns[9].visible"
                         :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.requireDeliveryDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
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
              v-hasPermi="['system:user:edit']"
            >详情
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="addInvoice(scope.row)"
              v-hasPermi="['system:user:edit']"
            >开票
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="delivery(scope.row)"
              v-hasPermi="['system:user:edit']"
            >发货
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
import {listOrder} from "@/api/order";

export default {
  name: "index",
  dicts: ['order_status','delivery_status','store_status'],
  data() {
    return {
      queryParams: {
        pageSize: 10,
        pageNum: 1
      },
      showSearch: true,
      loading: false,
      customerList: [],
      total: 0,
      columns: [
        {key: 0, label: `订单编号`, visible: true},
        {key: 1, label: `订单名称`, visible: true},
        {key: 2, label: `客户名称`, visible: true},
        {key: 3, label: `发货状态`, visible: true},
        {key: 4, label: `订单总金额`, visible: true},
        {key: 5, label: `订单总数量`, visible: true},
        {key: 6, label: `在途库数量`, visible: true},
        {key: 7, label: `合同编号`, visible: true},
        {key: 8, label: `合同名称`, visible: true},
        {key: 9, label: `预计发货时间`, visible: true}
      ],
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    handleQuery() {

      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm");
      // this.resetForm('queryForm')
    },
    addInvoice(row) {
      this.$router.push(`/invoice/create/index/${row.id}`)
    },
    detail(row) {
      this.$router.push(`/order/detail/index/${row.id}`)
    },
    delivery(row) {
      this.$router.push(`/delivery/create/index/${row.id}`)
    },
    getList() {
      listOrder(this.queryParams).then(res => {
        this.customerList = res.rows
        this.total = res.total
      })
    }
  }
}
</script>

<style scoped>

</style>
