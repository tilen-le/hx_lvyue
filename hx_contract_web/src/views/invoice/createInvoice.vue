<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>开票信息：</span>
          {{ customerDetail.name }}
          <span style="margin-left: 100px">客户编码：</span>
          {{ customerDetail.code }}
        </div>
      </div>
    </div>
    <div class="angel-card-top">
      <div style="display: flex;align-items: center;justify-content: space-between;margin-bottom: 15px">
        <div>客户收货信息</div>
        <el-button type="primary" @click="createAddress" style="margin-left: 15px">创建</el-button>
      </div>
      <el-table :data="address" border>
        <el-table-column label="收货人" align="center" key="name" prop="name"/>
        <el-table-column label="联系电话" align="center" key="phone" prop="phone"/>
        <el-table-column label="地址" align="center" key="address" prop="address">
          <template slot-scope="scope">
            {{ formatAddress(scope.row) }}
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope" v-if="scope.row.userId !== 1">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="updateAddress(scope.row)"
              v-hasPermi="['customer:address:add']"
            >编辑
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:user:edit']"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="angel-card-top">
      <div style="display: flex;align-items: center;justify-content: space-between;margin-bottom: 15px">
        <div>客户开票信息</div>
        <div>
          <el-button type="primary" @click="createInvoice">申请开票</el-button>
        </div>
      </div>
      <el-table :data="invoice" border>
        <el-table-column label="开户行" align="center" key="code" prop="code"/>
        <el-table-column label="账户" align="center" key="code" prop="code"/>
        <el-table-column label="地址" align="center" key="code" prop="code"/>
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope" v-if="scope.row.userId !== 1">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="updateAddress(scope.row)"
              v-hasPermi="['customer:address:add']"
            >编辑
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="detail(scope.row)"
              v-hasPermi="['system:user:edit']"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "createInvoice"
}
</script>

<style scoped>

</style>
