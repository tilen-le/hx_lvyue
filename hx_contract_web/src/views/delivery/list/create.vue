<template>
  <div class="app-container">
    <el-form :model="deliveryForm" ref="queryForm" size="small" :rules="rules" label-width="120px"
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
                v-model="order.soldToParty"
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
          <span>收货信息
            <!--            新增收货联系人-->
              <el-button plain size="mini" type="primary" icon="el-icon-plus" circle @click="createAddress()"></el-button>
          </span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="收货方" prop="consigneeId">
              <el-select
                v-model="deliveryForm.consigneeId"
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
            <el-form-item label="收货联系人">
              <el-select v-model="deliveryForm.addressId" placeholder="请选择" style="width: 90%"
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
            <el-form-item label="收货人电话">
              <el-input
                placeholder=""
                disabled
                v-model="deliveryForm.mobile"
                style="width: 90%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收货地址" style="width: 100%">
              <el-input
                style="width: 90%"
                disabled
                v-model="deliveryForm.address"
                placeholder=""/>
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
            <el-form-item label="发货方" style="width: 100%" prop="consignorCode">
              <el-select v-model="deliveryForm.consignorCode" placeholder="请选择" style="width: 90%">
                <el-option
                  v-for="dict in dict.type.delivery_category"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="运输方式" style="width: 100%" prop="transType">
              <el-select v-model="deliveryForm.transType" placeholder="请选择" style="width: 90%">
                <el-option
                  v-for="dict in dict.type.sys_trans_category"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="是否备表直发" style="width: 100%">
              <el-select v-model="deliveryForm.isReserveSend" placeholder="请选择" style="width: 90%">
                <el-option
                  v-for="dict in dict.type.sys_y_n"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="配件单独包装" style="width: 100%" prop="isSeparatePackaging">
              <el-select v-model="deliveryForm.isSeparatePackaging" placeholder="请选择" style="width: 90%">
                <el-option
                  v-for="dict in dict.type.ynn"
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
            <el-form-item label="预计到货日期" style="width: 100%" prop="expectedArrivalDate">
              <el-date-picker
                v-model="deliveryForm.expectedArrivalDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="预计质保日期" style="width: 100%" prop="expectedWarrantyDate">
              <el-date-picker
                v-model="deliveryForm.expectedWarrantyDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="预计验收日期" style="width: 100%" prop="expectedCheckDate">
              <el-date-picker
                v-model="deliveryForm.expectedCheckDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出厂日期" style="width: 100%" prop="productDate">
              <el-date-picker
                v-model="deliveryForm.productDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="12">
            <el-form-item label="备注" style="width: 100%">
              <el-input
                placeholder="请输入备注"
                style="width:90%"
                v-model="deliveryForm.remark"
                type="textarea"
                :rows="2"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="info-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>发货明细</span>
        </div>
        <el-table :data="deliveryForm.products" border
                  style="margin-top: 15px"
                  row-key="id">
          <el-table-column label="SAP订单明细编码" align="center" key="sapDetailNumber" prop="sapDetailNumber"/>
          <el-table-column label="产品名称" align="center" key="productName" prop="productName"/>
          <el-table-column label="发货数量" align="center" key="productNum" prop="productNum">
            <template slot-scope="scope">
              <el-input
                controls-position="right"
                :precision="2"
                placeholder="请输入"
                style="width: 70%"
                type="number"
                v-model="scope.row.productNum"
              />
            </template>
          </el-table-column>
          <el-table-column label="剩余发货" align="center" key="notSentNum" prop="notSentNum"/>
          <el-table-column label="库存" align="center" key="inStorageNum" prop="inStorageNum"/>
          <el-table-column label="SAP物料编码" align="center" key="productNumber" prop="productNumber">
          </el-table-column>
          <el-table-column label="单价" align="center" key="unitPrice" prop="unitPrice">
              <template slot-scope="scope">
                {{ scope.row.currency }} {{ scope.row.unitPrice }}
              </template>
          </el-table-column>
          <el-table-column label="技术要求" align="center" key="technicalRequirement" prop="technicalRequirement">
            <template slot-scope="scope">
              <el-input
                controls-position="right"
                placeholder="请输入"
                style="width: 70%"
                v-model="scope.row.technicalRequirement"
              />
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="resetRow(scope.row)"
                v-hasPermi="['delivery:create:reset']"
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
        <el-form-item label="" style="margin: 15px" label-width="0px">
          <fileUpload v-model="deliveryForm.files" :valueJson="true"/>
        </el-form-item>
      </div>
    </el-form>
    <div style="text-align: center">
      <el-button :loading="buttonLoading" type="primary" v-hasPermi="['order:create:save']" @click="submitForm(3)">保存为草稿</el-button>
      <el-button :loading="buttonLoading" type="primary" v-hasPermi="['order:create:approve']" @click="submitForm(0)">提交审核</el-button>
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
  import RegionSelect from "@/components/Forms/RegionSelect.vue";
  import {getOrderDetail} from "@/api/order";
  import { addAddress, getAddressByCode, getOpenBankByBe, listCustomer, updateAddress } from '@/api/customer'
  import {addDelivery} from "@/api/invoice";

  export default {
  name: "createDelivery",
  components: {RegionSelect},
  dicts: ['sys_trans_category', 'sys_y_n','delivery_category', 'ynn'],
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
      deliveryForm: {},
      address: [],
      searchLoading: false,
      receiveInvoice: [],
      buttonLoading:false,
      rules: {
        transType: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        isSeparatePackaging: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        expectedArrivalDate: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        expectedWarrantyDate: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        expectedCheckDate: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        productDate:[
          {required: true, message: "请选择", trigger: "blur"}
        ],
        consigneeId:[
          {required: true, message: "请选择", trigger: "blur"}
        ],
        consignorCode:[
          {required: true, message: "请选择", trigger: "blur"}
        ],
      },
      order: {},
      contract: {},
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
            // 重新获取收货人列表
            this.getAddressByCodeApi(this.order.reciverCd);
            this.openAddress = false;
          })
        }
      })
    },
    // 新增收获人
    createAddress(){
      // 请你先选择收货方
      if(typeof this.deliveryForm.consigneeId === 'undefined' || this.deliveryForm.consigneeId == null || this.deliveryForm.consigneeId === ''){
        this.$message({
          message: '请您先选择收货方',
          type: 'warning'
        });
        return
      }
      this.consigneeForm = {};
      this.consigneeForm.customerId = this.deliveryForm.consigneeId
      // 显示新增收货人弹窗
      this.openAddress = true
    },

    addressChange(val) {
      this.address.map(item => {
        if (item.id == val) {
          this.deliveryForm.mobile = item.phone
          this.deliveryForm.address = item.location.replaceAll(",", "") + item.address
        }
      })
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
    getOrderDetail() {
      const oid = this.$route.params.oid;
      const params = {id: oid}
      getOrderDetail(params).then(res => {
        this.order = res.data.order
        this.deliveryForm.orderId = res.data.order.id
        this.remoteMethod(res.data.order.soldToPartyCd)
        this.deliveryForm.consigneeId=res.data.order.soldToPartyCd
        this.deliveryForm.products=res.data.products
        this.contract = res.data.contract
        //收货联系人列表
        this.getAddressByCodeApi(res.data.order.reciverCd);
      })
    },
    changeBe(val) {
      this.getOpenBank(val)
    },
    getOpenBank(code) {
/*      const params = {
        billee: code
      }
      getOpenBankByBe(params).then(res => {
        this.openBank = res.data
      })*/
      const codePa = {
        code: code
      }
      getAddressByCode(codePa).then(res => {
        this.address = res.data
      })
    },
    getAddressByCodeApi(code) {
      const codePa = {
        code: code
      }
      getAddressByCode(codePa).then(res => {
        this.address = res.data
      })
    },
    submitForm(val){
      this.$refs["queryForm"].validate(valid => {
        if (val === 3 || valid) {
          this.deliveryForm.approvalStatus=val
          if (this.deliveryForm.files != null && this.deliveryForm.files != "") {
            if(!Array.isArray(this.deliveryForm.files)){
              this.deliveryForm.files = JSON.parse(this.deliveryForm.files)
            }
          } else {
            this.deliveryForm.files = []
          }
          addDelivery(this.deliveryForm).then(res => {
            this.$modal.msgSuccess("提交成功");
            this.$store.dispatch('tagsView/delView', this.$route)
            this.$router.go(-1)
          })
        }
      })
    },
    cancel() {
      this.$modal.confirm('确认关闭页面？').then(function () {
        this.$store.dispatch('tagsView/delView', this.$route)
        this.$router.go(-1)
      });
    },
    resetRow(row) {
      row.productNum = null
      row.technicalRequirement = ''
    }
  }
}
</script>

<style scoped>

</style>
