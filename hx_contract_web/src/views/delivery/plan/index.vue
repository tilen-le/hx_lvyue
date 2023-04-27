<template>
  <div class="app-container">
    <div class="angel-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-row>
          <el-col :span="6">
            <el-form-item label="订单编号" prop="name">
              <el-input
                v-model="queryParams.name"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户名称" prop="name">
              <el-input
                v-model="queryParams.name"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
              <el-form-item style="width:100%;text-align: right">
                <el-button type="primary" size="mini" @click="addPlan">创建发货计划</el-button>
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
        <el-table-column label="发货计划编号" align="center" key="orderNumber" prop="orderNumber"/>
        <el-table-column label="客户" align="center" key="orderTitle" prop="orderTitle"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="INVOICE NO" align="center" key="soldToPartyCd" prop="soldToPartyCd"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="客户联系人" align="center" key="status">
        </el-table-column>
        <el-table-column label="联系方式" align="center" key="amount" prop="amount"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="收货方" align="center" key="" prop=""
                         :show-overflow-tooltip="true"/>
        <el-table-column label="通知方" align="center" key="" prop=""
                         :show-overflow-tooltip="true"/>
        <el-table-column label="SHIPPING MARK" align="center" key="" prop=""
                         :show-overflow-tooltip="true"/>
        <el-table-column label="是否报关完成" align="center" key="" prop=""
                         :show-overflow-tooltip="true"/>
        <el-table-column label="是否同步SAP" align="center" key="" prop=""
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
            >通知单证专员
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="detail(scope.row)"
              v-hasPermi="['system:user:edit']"
            >报关完成
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="detail(scope.row)"
              v-hasPermi="['system:user:edit']"
            >同步SAP
            </el-button>
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
              @click="detail(scope.row)"
              v-hasPermi="['system:user:edit']"
            >编辑
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
export default {
  name: "index",
  dicts: ['sys_customer_status'],
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
  methods:{
    addPlan(){
      this.$router.push(`/plan/create/index`)
    }
  }
}
</script>

<style scoped>

</style>
