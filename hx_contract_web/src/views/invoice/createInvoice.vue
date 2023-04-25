<template>
  <div class="app-container">
    <el-form :model="invoiceForm" ref="queryForm" size="small" :inline="true" label-width="120px"
             style="margin: 15px">
      <div class="invoice-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>开票信息</span>
        </div>
        <el-row>
          <el-col :span="6">
            <el-form-item label="开票类型" style="width: 100%">
              <el-input
                placeholder="标准开票"
                disabled
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="订单名称" prop="orderTitle">
              <el-input
                v-model="invoiceForm.orderTitle"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户名称" prop="soldToPartyCd">
              <el-input
                v-model="invoiceForm.soldToPartyCd"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="发票类型" prop="configName">
              <el-select v-model="invoiceForm.invoiceType" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.invoice_type"
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
            <el-form-item label="卖方银行" prop="saleBank">
              <el-select v-model="invoiceForm.saleBank" placeholder="请选择">
                <el-option
                  v-for="dict in saleBank"
                  :key="dict.value"
                  :label="dict.bankName"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收票方" prop="configName">
              <el-select v-model="invoiceForm.invoiceType" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.invoice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="开户行" prop="configName">
              <el-select v-model="invoiceForm.invoiceType" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.invoice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="送货日期" prop="configName">
              <el-date-picker
                v-model="invoiceForm.requireDeliveryDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="验收日期" prop="configName">
              <el-select v-model="invoiceForm.invoiceType" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.invoice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="含税金额合计" prop="configName">
              <el-input
                placeholder="请输入"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="不含税金额合计" prop="configName">
              <el-input
                placeholder="请输入"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="税额合计" prop="configName">
              <el-input
                placeholder="请输入"
                disabled
              />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="invoice-header" style="margin-top: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>客户信息</span>
        </div>
        <el-row>
          <el-col :span="6">
            <el-form-item label="收件人" prop="configName">
              <el-select v-model="invoiceForm.invoiceType" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.invoice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="联系电话" prop="configName">
              <el-input
                placeholder="请输入"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收货地址" prop="configName">
              <el-input
                placeholder="请输入"/>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="invoice-header" style="margin-top: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>开票明细</span>
        </div>
        <el-table :data="productList" border
                  style="margin-top: 15px"
                  row-key="id">
          <el-table-column label="产品编码" align="center" key="orderNumber" prop="orderNumber"/>
          <el-table-column label="产品型号" align="center" key="orderNumber" prop="orderNumber"/>
          <el-table-column label="SPA明细编码" align="center" key="orderNumber" prop="orderNumber"/>
          <el-table-column label="产品名称" align="center" key="orderNumber" prop="orderNumber"/>
          <el-table-column label="已下单数量" align="center" key="orderNumber" prop="orderNumber"/>
          <el-table-column label="单位" align="center" key="orderNumber" prop="orderNumber">

          </el-table-column>
          <el-table-column label="财务软件编吗" align="center" key="orderNumber" prop="orderNumber">

          </el-table-column>
          <el-table-column label="工厂" align="center" key="orderNumber" prop="orderNumber">

          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleDownload(scope.row)"
                v-hasPermi="['system:oss:download']"
              >清空
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="invoice-header" style="margin-top: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>附件(送货单和验收单必需上传)</span>
        </div>
        <el-form-item label="" style="margin: 15px">
          <fileUpload v-model="invoiceForm.file"/>
        </el-form-item>
      </div>
    </el-form>
    <div style="text-align: center">
      <el-button :loading="buttonLoading" type="primary" @click="submitForm">保存为草稿</el-button>
      <el-button :loading="buttonLoading" type="primary" @click="submitForm">提交审核</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import {getOrderDetail} from "@/api/order";
import {listAvailableBank} from "@/api/system/bank";

export default {
  name: "createInvoice",
  dicts: ['invoice_type'],
  data() {
    return {
      invoiceForm: {},
      productList: [],
      saleBank:[]
    }
  },
  created() {
    this.getOrderDetail()
    this.getSaleBank()
  },
  methods: {
    getOrderDetail() {
      const oid = this.$route.params.oid;
      const params = {id: oid}
      getOrderDetail(params).then(res => {
        this.invoiceForm = res.data
      })
    },
    getSaleBank(){
      listAvailableBank().then(res=>{
        this.saleBank=res.data
      })
    }
  }
}
</script>

<style scoped lang="scss">
.invoice-header {
  background: white;
  border-radius: 4px;
  padding-top: 15px;
  width: 100%;
}

.line-item {
  background: blue;
  height: 15px;
  margin-right: 5px;
  border-bottom-right-radius: 5px;
  border-top-right-radius: 5px;
  width: 4px
}
</style>
