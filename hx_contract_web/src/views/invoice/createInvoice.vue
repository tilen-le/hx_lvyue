<template>
  <div class="app-container">
    <el-form :model="invoiceForm" ref="form" size="small" :rules="rules" label-width="120px"
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
                style="width: 90%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="订单名称" prop="orderTitle">
              <el-input
                style="width: 90%"
                v-model="invoiceForm.orderTitle"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户名称" prop="bilee">
              <el-input
                style="width: 90%"
                v-model="invoiceForm.soldToParty"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="发票类型" prop="invoiceType">
              <el-select v-model="invoiceForm.invoiceType" placeholder="请选择" style="width: 90%">
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
              <el-select v-model="invoiceForm.saleBank" placeholder="请选择" style="width: 90%">
                <el-option
                  v-for="dict in saleBank"
                  :key="dict.id"
                  :label="dict.bankName"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收票方" prop="consigneeId">
              <el-select
                v-model="invoiceForm.consigneeId"
                filterable
                remote
                reserve-keyword
                style="width: 90%"
                placeholder="请输入"
                :remote-method="remoteMethod"
                @change="changeBe"
                :loading="searchLoading">
                <el-option
                  v-for="item in receiveInvoice"
                  :key="item.code"
                  :label="item.name"
                  :value="item.code">
                  {{ item.name }}({{ item.code }})
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="开户行" prop="openingBank">
              <el-select v-model="invoiceForm.openingBank" placeholder="请选择" style="width: 90%">
                <el-option
                  v-for="item in openBank"
                  :key="item.id"
                  :label="item.openingBank"
                  :value="item.id"
                >
                  {{ item.openingBank }}({{ item.address }})
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="送货日期" prop="arrivalDate">
              <el-date-picker
                v-model="invoiceForm.arrivalDate"
                style="width: 90%"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="验收日期" prop="checkDate">
              <el-date-picker
                v-model="invoiceForm.checkDate"
                style="width: 90%"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="含税金额合计" prop="totalAmountWithTax">
              <el-input
                style="width: 90%"
                placeholder="请输入"
                v-model="invoiceForm.totalAmountWithTax"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="不含税金额合计" prop="totalAmountWithoutTax">
              <el-input
                placeholder="请输入"
                style="width: 90%"
                v-model="invoiceForm.totalAmountWithoutTax"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="税额合计" prop="configName">
              <el-input
                placeholder="请输入"
                style="width: 90%"
                v-model="invoiceForm.tax"
                disabled
              />
            </el-form-item>
          </el-col>

        </el-row>
      </div>
      <div class="invoice-header" style="margin-top: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>客户信息<!--            新增收货联系人-->
              <el-button plain size="mini" type="primary" icon="el-icon-plus" circle @click="createAddress()"></el-button></span>
        </div>
        <el-row>
          <el-col :span="6">
            <el-form-item label="收件人" prop="consignmentId">
              <el-select v-model="invoiceForm.consignmentId" placeholder="请选择" style="width: 90%"
                         @change="addressChange">
                <el-option
                  v-for="dict in address"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="联系电话" prop="mobile">
              <el-input
                placeholder="请输入"
                disabled
                v-model="invoiceForm.mobile"
                style="width: 90%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收货地址" prop="configName">
              <el-input
                style="width: 90%"
                disabled
                v-model="invoiceForm.address"
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
        <el-table :data="invoiceForm.productList" border
                  style="margin-top: 15px;width: 100%"
                  row-key="id">
          <el-table-column label="行项目号" align="center" key="sapDetailNumber" prop="sapDetailNumber"
                           width="160"/>
          <el-table-column label="产品型号" align="center" key="productModel" prop="productModel"
                           width="150"/>
          <el-table-column label="产品名称" align="center" key="productName" prop="productName"
                           width="200"/>
          <el-table-column label="SPA明细编码" align="center" key="productNumber" prop="productNumber"
                           width="150"/>
          <el-table-column label="已下单数量" align="center" key="num" prop="num"
                           width="150"/>
          <el-table-column align="center" key="unit" prop="unit"
                           width="150">
            <template slot="header" slot-scope="scope">
              单位<span style="color: red">*</span>
            </template>
            <template slot-scope="scope">
              <el-form-item :prop="'productList.' + scope.$index + '.unit'" :rules="rules.unit" style="text-align: center" label-width="0px">
                <el-input
                  controls-position="right"
                  :precision="2"
                  placeholder="单位"
                  style="width: 70%"
                  v-model="scope.row.unit"
                />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="财务软件编码" align="center" key="sapFinancialCode" prop="sapFinancialCode"
                           width="150">
            <template slot-scope="scope">
              <el-form-item style="text-align: center" label-width="0px">
                <el-select v-model="scope.row.sapFinancialCode" placeholder="请选择">
                  <el-option
                    v-for="dict in dict.type.finance_cate"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="工厂" align="center" key="factory" prop="factory"
                           width="150"/>
          <el-table-column label="在途库数量" align="center" key="inTransitNum" prop="inTransitNum"
                           width="150"/>
          <el-table-column label="申请开票数量" align="center" key="appliedQuantity" prop="appliedQuantity"
                           width="150">
            <template slot-scope="scope">
              <el-input
                controls-position="right"
                :precision="2"
                placeholder="请输入"
                style="width: 70%"
                type="number"
                min="1"
                @input="(val)=>invoiceNumChange(val,scope.row)"
                v-model="scope.row.appliedQuantity"
              />
            </template>
          </el-table-column>
          <el-table-column label="订单含税单价" align="center" key="unitPrice" prop="unitPrice"
                           width="150">
            <template slot-scope="scope">
              {{ scope.row.currency }} {{ scope.row.unitPrice }}
            </template>
          </el-table-column>
          <el-table-column label="开票含税单价" align="center" key="invoicingUnitPriceWithTax"
                           prop="invoicingUnitPriceWithTax"
                           width="150">
            <template slot-scope="scope">
              <el-input
                controls-position="right"
                :precision="2"
                placeholder="请输入"
                style="width: 70%"
                type="number"
                @input="(val)=>invoiceUnitPrice(val, scope.row)"
                v-model="scope.row.invoicingUnitPriceWithTax"
              />
            </template>
          </el-table-column>
          <el-table-column label="税率" align="center" key="taxRate" prop="taxRate" width="150">
          </el-table-column>
          <el-table-column align="center" key="customerMaterialName" prop="customerMaterialName"
                           width="150">
            <template slot="header" slot-scope="scope">
              客户物料名称<span style="color: red">*</span>
            </template>
            <template slot-scope="scope">
              <el-form-item :prop="'productList.' + scope.$index + '.customerMaterialName'" :rules="rules.customerMaterialName" style="text-align: center" label-width="0px">
              <el-input
                controls-position="right"
                max-length="50"
                placeholder="请输入"
                style="width: 70%"
                v-model="scope.row.customerMaterialName"
              />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="center" key="customerSpecName" prop="customerSpecName"
                           width="150">
            <template slot="header" slot-scope="scope">
              客户规格名称<span style="color: red">*</span>
            </template>
            <template slot-scope="scope">
              <el-form-item :prop="'productList.' + scope.$index + '.customerSpecName'" :rules="rules.customerSpecName" style="text-align: center" label-width="0px">
              <el-input
                controls-position="right"
                placeholder="请输入"
                max-length="50"
                style="width: 70%"
                v-model="scope.row.customerSpecName"
              />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="开票含税金额合计" align="center" key="invoicingAmountWithTax"
                           prop="invoicingAmountWithTax"
                           width="150">
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="clear(scope.row)"
                v-hasPermi="['invoice:create:reset']">重置
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="invoice-header" style="margin-top: 15px;padding: 15px 0">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>附件(送货单和验收单必需上传)</span>
        </div>
        <el-form-item label=" " label-width="10px" prop="files">
          <fileUpload v-model="invoiceForm.files" :valueJson="true" style="margin: 15px"/>
        </el-form-item>
      </div>
    </el-form>
    <div style="text-align: center">
      <el-button :loading="buttonLoading" type="primary" @click="submitForm(3)" v-hasPermi="['invoice:list:save']">保存为草稿</el-button>
      <el-button :loading="buttonLoading" type="primary" @click="submitForm(0)" v-hasPermi="['invoice:list:approve']">提交审核</el-button>
      <el-button @click="cancel" v-hasPermi="['invoice:create:cancel']">取 消</el-button>
    </div>

    <!--新增收货方弹窗-->
    <el-dialog title="新增收货方" :visible.sync="openAddress" width="600px" append-to-body>
      <el-form ref="addressForm" :model="consigneeForm" :rules="addressRules" label-width="100px" label-position="left">
        <el-form-item label="收货人" prop="name">
          <el-input v-model="consigneeForm.name" placeholder="请输入" maxlength="30"/>
        </el-form-item>
        <el-form-item label="收货人电话" prop="phone">
          <el-input v-model="consigneeForm.phone" placeholder="请输入收货人电话"  />
        </el-form-item>
        <el-form-item label="收货地址" prop="location">
          <RegionSelect
            style="width: 100%"
            placeholder="请选择"
            v-model="consigneeForm.location"
          ></RegionSelect>
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="consigneeForm.address" placeholder="请输入" maxlength="50"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormAddress">确 定</el-button>
        <el-button @click="cancelFormAddress">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getOrderDetail} from "@/api/order";
import {listAvailableBank} from "@/api/system/bank";
import {getAddressByCode, getOpenBankByBe, listCustomer} from "@/api/customer";
import {addInvoice} from "@/api/invoice";
import RegionSelect from '@/components/Forms/RegionSelect.vue'

export default {
  name: "createInvoice",
  components: { RegionSelect },
  dicts: ['invoice_type', 'finance_cate'],
  data() {
    const validatePhTelNumber = (rule, value, callback) => {
      const reg = /^((\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14})|([1][3,4,5,6,7,8,9][0-9]{9})$/
      if(reg.test(value)) {
        callback();
      } else {
        callback(new Error("请输入正确格式手机号"));
      }
    };
    return {
      invoiceForm: {},
      saleBank: [],
      openBank: [],
      address: [],
      buttonLoading: false,
      receiveInvoice: [],
      searchLoading: false,
      rules: {
        invoiceType: [
          {required: true, message: "请选择发票类型", trigger: "blur"}
        ],
        saleBank: [
          {required: true, message: "请选择卖方银行", trigger: "blur"}
        ],
        consigneeId: [
          {required: true, message: "请输入收票方", trigger: "blur"},
        ],
        openingBank: [
          {required: true, message: "请选择开户行", trigger: "blur"},
        ],
        arrivalDate: [
          {required: true, message: "请选择", trigger: "blur"},
        ],
        checkDate: [
          {required: true, message: "请选择", trigger: "blur"},
        ],
        consignmentId: [
          {required: true, message: "请选择收件人", trigger: "blur"},
        ],
        unit: [
          {required: true, message: "请填写单位", trigger: "blur"},
        ],
        customerMaterialName: [
          {required: true, message: "请填写客户物料名称", trigger: "blur"},
        ],
        customerSpecName: [
          {required: true, message: "请填写客户规格名称", trigger: "blur"},
        ],
        files: [
          {required: true, message: "请上传附件", trigger: "blur"},
        ],
      },
      // 收货人表单-----------
      consigneeForm: {},
      openAddress: false,
      addressRules: {
        name: [
          {required: true, message: "请输入收货人", trigger: "blur"}
        ],
        phone: [
          {required: true, trigger: "blur", message: "请输入收货电话"},
          {required: true, trigger: "blur", validator: validatePhTelNumber}
        ],
        location: [
          {required: true, message: "请选择收货地区", trigger: "blur"}
        ],
        address: [
          {required: true, message: "请选择收货地区", trigger: "blur"}
        ],
      },
      // --------------------
    }
  },
  created() {
    this.getOrderDetail()
    this.getSaleBank()
  },
  methods: {
    // 取消提交收获人
    cancelFormAddress(){
      this.openAddress = false
    },
    // 提交收获人
    submitFormAddress() {
      this.$refs["addressForm"].validate(valid => {
        if (valid) {
          addAddress(this.consigneeForm).then(res => {
            this.$modal.msgSuccess("创建成功");
            /** 获取客户信息列表**/
            getAddressByCode(this.invoiceForm.consigneeId).then(res => {
              this.address = res.data
            })
            this.openAddress = false;
          })
        }
      })
    },
    // 新增收获人
    createAddress(){
      // 客户不存在
      if(typeof this.invoiceForm.consigneeId === 'undefined' || this.invoiceForm.consigneeId == null || this.invoiceForm.consigneeId === ''){
        this.$message({
          message: '客户不存在',
          type: 'warning'
        });
        return
      }
      this.consigneeForm = {};
      this.consigneeForm.customerId = this.invoiceForm.consigneeId
      // 显示新增收货人弹窗
      this.openAddress = true
    },

    invoiceUnitPrice(val, row) {
      if (row.invoicingUnitPriceWithTax && row.appliedQuantity) {
        const totalAmount = row.invoicingUnitPriceWithTax * row.appliedQuantity
        row.invoicingAmountWithTax = totalAmount
      } else {
        row.invoicingAmountWithTax = 0;
      }
      this.computeTotal();
    },
    invoiceNumChange(val, row) {
      if (row.invoicingUnitPriceWithTax && row.appliedQuantity) {
        const totalAmount = row.invoicingUnitPriceWithTax * row.appliedQuantity
        row.invoicingAmountWithTax = totalAmount
      } else {
        row.invoicingAmountWithTax = 0;
      }
      this.computeTotal();
    },
    computeTotal() {
      //计算合计
      const productList = this.invoiceForm.productList
      if (productList) {
        let totalAmountWithTax = 0;
        let tax = 0;
        for (const product of productList) {
          if (typeof product.invoicingAmountWithTax !== 'undefined' &&
            product.invoicingAmountWithTax != null &&
            product.invoicingAmountWithTax !== '') {
            totalAmountWithTax += product.invoicingAmountWithTax
          }
          if (
            typeof product.taxRate !== 'undefined' &&
            product.taxRate != null &&
            product.taxRate !== '') {
            tax += (product.invoicingAmountWithTax * product.taxRate)
          }
        }
        this.invoiceForm.totalAmountWithTax = totalAmountWithTax
        this.invoiceForm.tax = tax
        this.invoiceForm.totalAmountWithoutTax = (totalAmountWithTax - tax)
      }
    },
    addressChange(val) {
      this.address.map(item => {
        if (item.id == val) {
          this.invoiceForm.mobile = item.phone
          this.invoiceForm.address = item.location.replaceAll(",", "") + item.address
        }
      })
    },
    getOrderDetail() {
      const oid = this.$route.params.oid;
      const params = {id: oid}
      getOrderDetail(params).then(res => {
        const order = res.data.order
        this.remoteMethod(order.bileeCd)
        this.invoiceForm = {
          orderId: order.id,
          orderTitle: order.orderTitle,
          soldToPartyCd: order.soldToPartyCd,
          soldToParty: order.soldToParty,
          consigneeId: order.bileeCd,
          totalAmountWithTax: undefined,
          totalAmountWithoutTax: undefined,
          tax: undefined
        }
        this.getOpenBank(order.bileeCd)
        this.handleProduct(res.data.products, order)
      })
    },
    clear(row) {
      row.unit = undefined
      row.sapFinancialCode = undefined
      row.appliedQuantity = undefined
      row.invoicingUnitPriceWithTax = undefined
      row.customerMaterialName = undefined
      row.customerSpecName = undefined
      row.invoicingAmountWithTax = undefined
      this.computeTotal();
    },
    cancel() {
      this.$store.dispatch('tagsView/delView', this.$route)
      this.$router.go(-1)
    },
    handleProduct(val, order) {
      const models = []
      val.map(item => {
        const pa = {
          orderProductId:item.id,
          taxRate: item.taxRate,
          productNumber: item.productNumber,
          productModel: item.productModel,
          sapDetailNumber: item.sapDetailNumber,
          productName: item.productName,
          num: item.num,
          unit: "",
          sapFinancialCode: '',
          factory: order.factory,
          inTransitNum: item.inTransitNum,
          appliedQuantity: '',
          unitPrice: item.unitPrice,
          currency: order.currency,
          invoicingUnitPriceWithTax: '',
          customerMaterialName: '',
          customerSpecName: ''
        }
        models.push(pa)
      })
      this.invoiceForm.productList = models
    },
    remoteMethod(query) {
      const params = {
        code: query,
        name: query
      }
      listCustomer(params).then(res => {
        this.receiveInvoice = res.rows
      })
    },
    getSaleBank() {
      listAvailableBank().then(res => {
        this.saleBank = res.data
      })
    },
    changeBe(val) {
      this.getOpenBank(val)
    },
    getOpenBank(code) {
      const params = {
        code: code
      }
      getOpenBankByBe(params).then(res => {
        this.openBank = res.data
      })
      const codePa = {
        code: code
      }
      /** 获取客户信息列表**/
      getAddressByCode(codePa).then(res => {
        this.address = res.data
      })
    },
    submitForm(val) {
      this.$refs["form"].validate(valid => {
        if (val === 3 || valid) {
          this.invoiceForm.approvalStatus=val
          if (this.invoiceForm.files) {
            if(!Array.isArray(this.invoiceForm.files)){
              this.invoiceForm.files = JSON.parse(this.invoiceForm.files)
            }
          } else {
            this.invoiceForm.files = []
          }
          addInvoice(this.invoiceForm).then(res => {
            // 跳转开票列表页面
            this.$router.push(`/invoice/list`)
            this.$modal.msgSuccess("提交成功");
          })
        }
      })
    }
  }
}
</script>

<style scoped>
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
