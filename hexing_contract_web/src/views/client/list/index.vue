<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-row>
        <el-col :span="6">
          <el-form-item label="客户名称" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入" clearable size="small" style="width: 240px"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="收货人" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入" clearable size="small" style="width: 240px"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择" clearable size="small" style="width: 240px">
              <el-option v-for="dict in statusDict" :key="parseInt(dict.value)" :label="dict.label"
                         :value="parseInt(dict.value)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6" style="text-align: right">
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd"
                   v-hasPermi="['system:user:create']">新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="clientList" border>
      <el-table-column label="客户编号" align="center" key="id" prop="id" v-if="columns[0].visible"/>
      <el-table-column label="状态" key="status" v-if="columns[1].visible" align="center">
        <template v-slot="scope">
          <el-switch v-model="scope.row.status" :active-value="0" :inactive-value="1"
                     @change="handleStatusChange(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" key="username" prop="username" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="收货人" align="center" key="nickname" prop="nickname" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="收货人电话" align="center" key="deptName" prop="dept.name" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="收货地址" align="center" key="mobile" prop="mobile" v-if="columns[5].visible"/>
      <el-table-column label="收货地址明细" align="center" key="mobile" prop="mobile" v-if="columns[6].visible"/>
      <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>
  </div>
</template>

<script>
import {DICT_TYPE, getDictDatas} from "@/utils/dict";
import {listCustomer} from "@/api/customer";

export default {
  name: "ClientList",
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
      total: 0,
      loading: false,
      clientList: [],
      statusDict: getDictDatas(DICT_TYPE.COMMON_STATUS),
      // 列信息
      columns: [
        {key: 0, label: `客户编号`, visible: true},
        {key: 1, label: `状态`, visible: true},
        {key: 2, label: `客户名称`, visible: true},
        {key: 3, label: `收货人`, visible: true},
        {key: 4, label: `收货人电话`, visible: true},
        {key: 5, label: `收货地址`, visible: true},
        {key: 6, label: `收货地址明细`, visible: true}
      ],
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    handleQuery() {

    },
    resetQuery() {

    },
    getList() {
      listCustomer(this.queryParams).then(res => {

      })
    },
    handleAdd() {

    }
  }
}
</script>

<style scoped>

</style>
