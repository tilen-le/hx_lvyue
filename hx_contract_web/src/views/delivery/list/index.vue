<template>
  <div class="app-container">
    <div class="angel-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-row>
          <el-col :span="6">
            <el-form-item label="订单编号" prop="name">
              <el-input
                v-model="queryParams.consigmentNumber"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户名称" prop="name">
              <el-input
                v-model="queryParams.customer"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="发货状态" prop="deliveryStatus">
              <el-select
                v-model="queryParams.deliveryStatus"
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
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="审批状态" prop="approveStatus">
              <el-select
                v-model="queryParams.approveStatus"
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
            <el-form-item label="订单状态" prop="name">
              <el-input
                v-model="queryParams.name"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="合同编号" prop="status">
              <el-input
                v-model="queryParams.name"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="预计发货时间" prop="status">
              <el-date-picker
                v-model="queryParams.devlieryTime"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
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
        <el-table-column label="发货单编号" align="center" key="consigmentNumber" prop="consigmentNumber"/>
        <el-table-column label="记录类型" align="center" key="" prop=""
                         :show-overflow-tooltip="true"/>
        <el-table-column label="发货总金额" align="center" key="amount" prop="amount"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="订单名称" align="center" key="orderTitle" prop="orderTitle">
        </el-table-column>
        <el-table-column label="审批状态" align="center" key="amount" prop="amount"
                         :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.approve_status" :value="scope.row.approvalStatus"/>
          </template>
        </el-table-column>
        <el-table-column label="客户名称" align="center" key="customer" prop="customer"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="创建时间" align="center" key="createTime" prop="createTime"
                         :show-overflow-tooltip="true"/>
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
              @click="removeApprove(scope.row)"
              v-hasPermi="['system:user:edit']"
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
import {listOrderDelivery} from "@/api/order";
import {delApproveConfig} from "@/api/system/config";

export default {
  name: "index",
  dicts: ['sys_customer_status','approve_status','delivery_status','store_status'],
  data() {
    return {
      queryParams: {
        pageSize: 10,
        pageNum: 1
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
    handleQuery(){
      this.getList()
    },
    getList() {
      listOrderDelivery(this.queryParams).then(res => {
        this.deliveryList=res.rows
        this.total=res.total
      })
    },
    removeApprove(row){
      this.$modal.confirm('确认撤销该发货单的审批？').then(function () {
        return delApproveConfig(row);
      }).then(() => {
        this.$modal.msgSuccess("撤销成功");
        this.getDeliveryConfig()
        this.getInvoiceConfig()
      }).catch(function () {
      });
    },
    detail(row) {
      this.$router.push(`/delivery/detail/index/${row.id}`)
    },
  }
}
</script>

<style scoped>

</style>
