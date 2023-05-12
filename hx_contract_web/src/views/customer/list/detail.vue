<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>客户名称：</span>
          {{ customerDetail.name }}
          <span style="margin-left: 100px">客户编码：</span>
          {{ customerDetail.code }}
        </div>
      </div>
    </div>
    <div class="angel-card-top">
      <div style="display: flex;align-items: center;margin-bottom: 15px">
        <div>客户收货信息</div>
        <el-button type="primary" @click="createAddress" v-hasPermi="['customer:address:add']" style="margin-left: 15px">创建</el-button>
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
              v-hasPermi="['customer:msg:edit']"
            >编辑
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDelete(scope.row)"
              v-hasPermi="['customer:msg:delete']"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="angel-card-top">
      <div style="display: flex;align-items: center;margin-bottom: 15px">
        <div>客户开户行信息</div>
        <div>
          <el-button type="primary" @click="createInvoice" style="margin-left: 15px"
                     v-hasPermi="['customer:openbank:add']">创建
          </el-button>
        </div>
      </div>
      <el-table :data="invoice" border>
        <el-table-column label="开户行" align="center" key="openingBank" prop="openingBank"/>
        <el-table-column label="账户" align="center" key="account" prop="account"/>
        <el-table-column label="地址" align="center" key="address" prop="address"/>
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
              @click="updateInvoice(scope.row)"
              v-hasPermi="['customer:openbank:update']"
            >编辑
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="removeInvoice(scope.row)"
              v-hasPermi="['customer:openbank:remove']"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog :title="title" :visible.sync="openAddress" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="left">
        <el-form-item label="收货人" prop="name">
          <el-input v-model="form.name" placeholder="请输入" maxlength="30"/>
        </el-form-item>
        <el-form-item label="收货人电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入收货人电话"  />
        </el-form-item>
        <el-form-item label="收货地址" prop="location">
          <RegionSelect
            style="width: 100%"
            placeholder="请选择"
            v-model="form.location"
          ></RegionSelect>
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入" maxlength="50"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormAddress">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="invoiceTitle" :visible.sync="openInvoice" width="600px" append-to-body>
      <el-form ref="invoiceForm" :model="invoiceForm" :rules="invoiceRules" label-width="100px" label-position="left">
        <el-form-item label="开户行" prop="openingBank">
          <el-input v-model="invoiceForm.openingBank" placeholder="请输入" maxlength="30"/>
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="invoiceForm.account" placeholder="请输入"/>
        </el-form-item>
        <el-form-item label="开户行地址" prop="address">
          <el-input v-model="invoiceForm.address" placeholder="请输入" maxlength="50"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitInvoice" v-hasPermi="['customer:address:add']">确 定</el-button>
        <el-button @click="cancelInvoice">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    addAddress,
    addOpenBank,
    delAddress,
    delOpeningBank,
    getAddress,
    getCustomer,
    getOpenBank,
    updateAddress,
    updateOpenBank
  } from "@/api/customer";
  import RegionSelect from "@/components/Forms/RegionSelect.vue";

  export default {
  name: "detail",
  components: {RegionSelect},
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
      customerDetail: {},
      address: [],
      invoice: [],
      title: '新增收货地址',
      invoiceTitle: '新增开票信息',
      openAddress: false,
      openInvoice: false,
      form: {},
      invoiceForm: {},
      customerId: "",
      rules: {
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
      invoiceRules: {
        openingBank: [
          {required: true, message: "请输入开户行", trigger: "blur"}
        ],
        account: [
          {required: true, message: "请输入账号", trigger: "blur"}
        ],
        address: [
          {required: true, message: "请选择开户行地址", trigger: "blur"}
        ],
      }
    }
  },
  created() {
    const cid = this.$route.params.cid;
    this.customerId = cid;
    this.detail(cid)
    this.getAddress(cid)
    this.getInvoiceList(cid)
  },
  methods: {
    close() {
      this.$router.go(-1)
    },
    detail(cid) {
      getCustomer(cid).then(res => {
        this.customerDetail = res.data
      })
    },
    formatAddress(row) {
      return row.location.replaceAll(",", "") + row.address
    },
    getAddress() {
      const params = {
        customerId: this.customerId
      }
      getAddress(params).then((res => {
        this.address = res.data
      }))
    },
    createAddress() {
      this.form = {};
      this.form.customerId = this.customerId
      this.openAddress = true
    },
    updateAddress(row) {
      this.title = "编辑收货信息"
      this.reset()
      this.form = JSON.parse(JSON.stringify(row))
      this.openAddress = true
    },
    updateInvoice(row) {
      this.invoiceTitle = "编辑开户行"
      this.reset()
      this.invoiceForm = JSON.parse(JSON.stringify(row))
      this.openInvoice = true
    },
    createInvoice() {
      this.invoiceForm = {}
      this.invoiceForm.customerId = this.customerId
      this.invoiceTitle = "新增开户行"
      this.openInvoice = true
    },
    submitFormAddress() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id == undefined) {
            addAddress(this.form).then(res => {
              this.$modal.msgSuccess("创建成功");
              this.openAddress = false;
              this.getAddress();
            })
          } else {
            updateAddress(this.form).then(res => {
              this.$modal.msgSuccess("修改成功");
              this.openAddress = false;
              this.getAddress();
            })
          }
        }
      })
    },
    getInvoiceList() {
      const params = {
        customerId: this.customerId
      }
      getOpenBank(params).then(res => {
        this.invoice = res.data
      })
    },
    submitInvoice() {
      this.$refs["invoiceForm"].validate(valid => {
        if (valid) {
          if (this.invoiceForm.id == undefined) {
            addOpenBank(this.invoiceForm).then(res => {
              this.$modal.msgSuccess("创建成功");
              this.openInvoice = false;
              this.getInvoiceList();
            })
          } else {
            updateOpenBank(this.invoiceForm).then(res => {
              this.$modal.msgSuccess("修改成功");
              this.openInvoice = false;
              this.getInvoiceList();
            })
          }
        }
      })
    },
    handleDelete(row) {
      this.$modal.confirm('是否确认删除地址为"' + row.location + '"的数据项？').then(function () {
        return delAddress(row);
      }).then(() => {
        this.$modal.msgSuccess("删除成功");
        this.getAddress();
      }).catch(() => {
      });
    },
    removeInvoice(row) {
      this.$modal.confirm('是否确认删除开户行为"' + row.openingBank + '"的数据项？').then(function () {
        return delOpeningBank(row);
      }).then(() => {
        this.$modal.msgSuccess("删除成功");
        this.getInvoiceList();
      }).catch(() => {
      });
    },
    reset() {
      this.resetForm("form");
      this.resetForm("invoiceForm")
    },
    cancel() {
      this.openAddress = false
    },
    cancelInvoice() {
      this.openInvoice = false
    }
  }
}
</script>

<style scoped>

</style>
