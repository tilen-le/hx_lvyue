<template>
  <div class="app-container">
<!--    查询-->
    <div class="angel-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-row>
          <el-col :span="10">
            <el-form-item label="发货计划编码" prop="name">
              <el-input
                v-model="queryParams.planCode"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="7">
<!--            收货人-->
            <el-form-item label="收货方" prop="consignee">
              <el-select
                v-model="queryParams.consignee"
                filterable
                remote
                reserve-keyword
                style="width: 90%"
                placeholder="请输入"
                :remote-method="remoteMethod2"
                >
                <el-option
                  v-for="item in customer2"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                  {{ item.name }}({{ item.code }})
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="7">
              <el-form-item style="width:100%;text-align: right">
                <el-button type="primary" size="mini" @click="addPlan">创建发货计划</el-button>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
              </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
<!--    列表-->
    <div class="angel-card-table">
      <el-table v-loading="loading" :data="planList" border
                row-key="id">
        <el-table-column label="发货计划编号" align="center" prop="planCode"/>
        <el-table-column label="INVOICE NO" align="center" prop="invoiceNo"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="客户联系人" align="center" prop="customerContact">
        </el-table-column>
        <el-table-column label="联系方式" align="center" prop="contactInformation"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="收货方" align="center" prop="consignee"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="通知方" align="center" prop="notifyId"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="SHIPPING MARK" align="center" prop="shippingMark"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="是否报关完成" align="center" prop="reportCustomsComplted"
                         :show-overflow-tooltip="true">
          <template scope="scope">
            <dict-tag :options="dict.type.ynn" :value="scope.row.reportCustomsComplted"/>
          </template>
        </el-table-column>
        <el-table-column label="是否同步SAP" align="center" prop="syncSapSuccess"
                         :show-overflow-tooltip="true">
          <template scope="scope">
            <dict-tag :options="dict.type.ynn" :value="scope.row.syncSapSuccess"/>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope" v-show="scope.row.reportCustomsComplted == 1">
            <el-button
              size="mini"
              type="text"
              @click="notifyCommissioner(scope.row)"
              v-hasPermi="['system:user:edit']"
            >通知单证专员
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="completeCustomsDeclaration(scope.row)"
              v-hasPermi="['system:user:edit']"
            >报关完成
            </el-button>
            <el-button
              v-show="null!=scope.row.reportCustomsComplted&&scope.row.syncSapSuccess==1"
              size="mini"
              type="text"
              @click="synchronizeSAP(scope.row)"
              v-hasPermi="['system:user:edit']"
            >同步SAP
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="toDetail(scope.row)"
              v-hasPermi="['system:user:edit']"
            >详情
            </el-button>
<!--            同步完成后不可编辑-->
<!--            v-show="scope.row.customsDeclarationCompleted==1"-->
            <el-button
              size="mini"
              type="text"
              @click="toEdit(scope.row)"
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
        @pagination="handleQuery"
      />
    </div>
  </div>
</template>

<script>
import { completeCustomsDeclarationApi, listPlanApi, notifyCommissionerApi, synchronizeSAPApi } from '@/api/plan'
import { listCustomer } from '@/api/customer'

export default {
  name: "index",
  dicts: ['sys_customer_status','ynn'],
  data() {
    return {
      queryParams: {
        pageSize: 10,
        pageNum: 1
      },
      loading: false,
      planList: [],
      total: 0,
      customer2: [],
    }
  },
  created() {
    // 查找计划列表
    this.handleQuery()
  },
  methods:{
    // 下拉框--远程获取人员信息
    remoteMethod2(query) {
      setTimeout(() => {
        const params = {
          code: query,
          name: query
        }
        listCustomer(params).then(res => {
          this.customer2 = []
          this.customer2 = res.rows
        })
      }, 1000)
    },
    // 跳转新增计划
    addPlan(){
      this.$router.push(`/plan/create/index`)
    },
    // 查询计划列表
    handleQuery(){
      listPlanApi(this.queryParams).then(res=>{
        this.planList=res.rows
      })
    },
    // 重置查询条件
    resetQuery(){
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 通知专证专员
    notifyCommissioner(row){
      let param={

      }
      notifyCommissionerApi(param).then(res=>{
        this.$message({
          message: '已为您通知',
          type: 'success'
        });
      })
    },
    // 报关完成
    completeCustomsDeclaration(row){
      let param={

      }
      completeCustomsDeclarationApi(param).then(res=>{
        this.$message({
          message: '报关已完成',
          type: 'success'
        });
        this.handleQuery();
      })
    },
    // 同步sap
    synchronizeSAP(row){
      let param={

      }
      synchronizeSAPApi(param).then(res=>{
        this.$message({
          message: 'SAP同步已完成',
          type: 'success'
        });
      })
    },
    // 跳转详情
    toDetail(row){
      this.$router.push(`/plan/create/detail/${row.id}`)
    },
    // 跳转编辑
    toEdit(row){
      this.$router.push(`/plan/create/edit/${row.id}`)
    },
  }
}
</script>

<style scoped>

</style>
