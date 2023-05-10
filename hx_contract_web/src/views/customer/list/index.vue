<template>
  <div class="app-container">
    <div class="angel-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-row>
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
          <el-col :span="6">
            <el-form-item label="收货人" prop="nickName">
              <el-input
                v-model="queryParams.nickName"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态" prop="status">
              <el-select
                v-model="queryParams.status"
                placeholder="请选择"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.sys_customer_status"
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
      <el-table v-loading="loading" :data="customerList" border>
        <el-table-column label="客户编码" align="center" key="code" prop="code" v-if="columns[0].visible"/>
        <el-table-column label="状态" align="center" key="status" v-if="columns[5].visible">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_customer_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="客户名称" align="center" key="name" prop="name" v-if="columns[1].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="收货人" align="center" key="nickName" prop="nickName" v-if="columns[2].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="收货人电话" align="center" key="deptName" prop="dept.deptName" v-if="columns[3].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="收货地址" align="center" key="phonenumber" prop="phonenumber" v-if="columns[4].visible"
                         width="120"/>
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
              icon="el-icon-edit"
              @click="detail(scope.row)"
              v-hasPermi="['system:user:edit']"
            >详情
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
import {listCustomer} from "@/api/customer";

export default {
  name: "index",
  dicts: ['sys_customer_status'],
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
        {key: 0, label: `客户编码`, visible: true},
        {key: 1, label: `客户名称`, visible: true},
        {key: 2, label: `收货人`, visible: true},
        {key: 3, label: `收货人电话`, visible: true},
        {key: 4, label: `收货地址`, visible: true},
        {key: 5, label: `状态`, visible: true}
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

    },
    detail(row) {
      this.$router.push(`/customer/detail/index/${row.id}`)
    },
    getList() {
      listCustomer(this.queryParams).then(res => {
        this.customerList = res.rows
        this.total = res.total
      })
    }
  }
}
</script>

<style scoped>

</style>
