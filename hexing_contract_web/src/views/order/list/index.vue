<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="订单编号" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入" clearable size="small" style="width: 240px"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="客户名称" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入" clearable size="small" style="width: 240px"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="发货状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择" clearable size="small" style="width: 240px">
              <el-option v-for="dict in statusDict" :key="parseInt(dict.value)" :label="dict.label"
                         :value="parseInt(dict.value)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="库存状态" prop="status" style="width: 100%;text-align: end">
            <el-select v-model="queryParams.status" placeholder="请选择" clearable size="small"
                       style="width: 240px"
            >
              <el-option v-for="dict in statusDict" :key="parseInt(dict.value)" :label="dict.label"
                         :value="parseInt(dict.value)"/>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="合同名称" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入" clearable size="small" style="width: 240px"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="订单时间" prop="createTime">
            <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss"
                            type="daterange"
                            range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                            :default-time="['00:00:00', '23:59:59']"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item style="width: 100%;text-align: end">
            <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
            <el-button icon="el-icon-refresh" @click="resetQuery">批量导出</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="orderList" border>
      <el-table-column
        type="selection"
        align="center"
        width="55">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {DICT_TYPE, getDictDatas} from "@/utils/dict";

export default {
  name: "AllOrder",
  data() {
    return {
      showSearch: true,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        name: undefined,
        code: undefined,
        status: undefined,
        createTime: []
      },
      orderList:[],
      total: 0,
      loading: false,
      statusDict: getDictDatas(DICT_TYPE.COMMON_STATUS),
      columns:[]
    }
  },
  methods: {
    handleQuery() {

    },
    resetQuery() {

    },
    getList(){

    }
  }
}
</script>

<style scoped>

</style>
