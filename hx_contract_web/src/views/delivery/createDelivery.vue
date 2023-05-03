<template>
  <div class="app-container">
    <el-form :model="deliveryForm" ref="queryForm" size="small" label-width="120px"
             style="margin: 15px">
      <div class="info-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>订单信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="合同名称" style="width: 100%">
              <el-input
                placeholder=""
                disabled
                v-model="contract.contractName"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="订单编号" style="width: 100%">
              <el-input
                placeholder=""
                disabled
                v-model="order.orderNumber"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="订单名称" style="width: 100%">
              <el-input
                placeholder=""
                disabled
                v-model="order.orderTitle"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户名称" style="width: 100%">
              <el-input
                placeholder=""
                disabled
                v-model="order.soldToPartyCd"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="事业部" style="width: 100%">
              <el-input
                placeholder=""
                disabled
                v-model="order.businessUnit"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="工厂" style="width: 100%">
              <el-input
                placeholder=""
                disabled
                v-model="order.factory"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="国内/国际营销部" style="width: 100%">
              <el-input
                placeholder=""
                disabled
                v-model="order.marketingDepartment"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="交货日期" style="width: 100%">
              <el-input
                placeholder=""
                disabled
                v-model="order.requireDeliveryDate"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="info-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>收货信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="收货方">
              <el-select v-model="deliveryForm.consigneeId" placeholder="请选择" style="width: 90%">
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
            <el-form-item label="收货联系人">
              <el-select v-model="deliveryForm.invoiceType" placeholder="请选择" style="width: 90%">
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
            <el-form-item label="收货联系人">
              <el-select v-model="deliveryForm.invoiceType" placeholder="请选择" style="width: 90%">
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
            <el-form-item label="发货地址" style="width: 100%">
              <RegionSelect
                style="width: 100%"
                placeholder="请选择"
                v-model="deliveryForm.location"
              ></RegionSelect>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="详细地址" style="width: 100%">
              <el-input
                placeholder="请输入"
                style="width:90%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="备注" style="width: 100%">
              <el-input
                placeholder="请输入"
                style="width:90%"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="info-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>发货信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="发货方" style="width: 100%">
              <el-select v-model="deliveryForm.invoiceType" placeholder="请选择" style="width: 90%">
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
            <el-form-item label="运输方式" style="width: 100%">
              <el-select v-model="deliveryForm.invoiceType" placeholder="请选择" style="width: 90%">
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
            <el-form-item label="是否备表直发" style="width: 100%">
              <el-select v-model="deliveryForm.invoiceType" placeholder="请选择" style="width: 90%">
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
            <el-form-item label="配件单独包装" style="width: 100%">
              <el-select v-model="deliveryForm.invoiceType" placeholder="请选择" style="width: 90%">
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
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="预计到货日期" style="width: 100%">
              <el-date-picker
                v-model="deliveryForm.preCollectDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="预计质保起算日期" style="width: 100%">
              <el-date-picker
                v-model="deliveryForm.preCollectDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="预计验收日期" style="width: 100%">
              <el-date-picker
                v-model="deliveryForm.preCollectDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出厂日期" style="width: 100%">
              <el-date-picker
                v-model="deliveryForm.preCollectDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="info-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>发货明细</span>
        </div>
        <el-table :data="deliveryForm.productList" border
                  style="margin-top: 15px"
                  row-key="id">
          <el-table-column label="SAP订单明细编码" align="center" key="orderNumber" prop="orderNumber"/>
          <el-table-column label="产品名称" align="center" key="orderNumber" prop="orderNumber"/>
          <el-table-column label="发货数量" align="center" key="orderNumber" prop="orderNumber"/>
          <el-table-column label="剩余发货" align="center" key="orderNumber" prop="orderNumber"/>
          <el-table-column label="库存" align="center" key="orderNumber" prop="orderNumber"/>
          <el-table-column label="SAP物料编码" align="center" key="orderNumber" prop="orderNumber">
          </el-table-column>
          <el-table-column label="单价" align="center" key="orderNumber" prop="orderNumber">
          </el-table-column>
          <el-table-column label="技术要求" align="center" key="orderNumber" prop="orderNumber">
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleDownload(scope.row)"
                v-hasPermi="['system:oss:download']"
              >重置
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="info-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>附件</span>
        </div>
        <el-form-item label="" style="margin: 15px">
          <fileUpload v-model="deliveryForm.file"/>
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
import RegionSelect from "@/components/Forms/RegionSelect.vue";
import {getOrderDetail} from "@/api/order";

export default {
  name: "createDelivery",
  components: {RegionSelect},
  dicts: ['invoice_type'],
  data() {
    return {
      deliveryForm: {},
      order: {},
      contract: {}
    }
  },
  created() {
    this.getOrderDetail()
  },
  methods: {
    getOrderDetail() {
      const oid = this.$route.params.oid;
      const params = {id: oid}
      getOrderDetail(params).then(res => {
        this.order = res.data.order
        this.contract = res.data.contract
      })
    }
  }
}
</script>

<style scoped>

</style>
