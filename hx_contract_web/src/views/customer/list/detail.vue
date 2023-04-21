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
    <el-dialog :title="title" :visible.sync="openAddress" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="left">
        <el-form-item label="收货人" prop="name">
          <el-input v-model="form.name" placeholder="请输入" maxlength="30"/>
        </el-form-item>
        <el-form-item label="收货人电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入" maxlength="11"/>
        </el-form-item>
        <el-form-item label="收获地址" prop="location">
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
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="left">
        <el-form-item label="开户行" prop="name">
          <el-input v-model="form.name" placeholder="请输入" maxlength="30"/>
        </el-form-item>
        <el-form-item label="账号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入" maxlength="11"/>
        </el-form-item>
        <el-form-item label="开户行地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入" maxlength="50"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitInvoice">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addAddress, delAddress, getAddress, getCustomer, updateAddress} from "@/api/customer";
import RegionSelect from "@/components/Forms/RegionSelect.vue";

export default {
  name: "detail",
  components: {RegionSelect},
  data() {
    return {
      customerDetail: {},
      address: [],
      invoice: [],
      title: '新增收货地址',
      invoiceTitle: '新增开票信息',
      openAddress: false,
      openInvoice: false,
      form: {},
      rules: {
        name: [
          {required: true, message: "请输入收货人", trigger: "blur"}
        ],
        phone: [
          {required: true, message: "请输入收货电话", trigger: "blur"}
        ],
        location: [
          {required: true, message: "请选择收货地区", trigger: "blur"}
        ],
        address: [
          {required: true, message: "请选择收货地区", trigger: "blur"}
        ],
      }
    }
  },
  created() {
    const cid = this.$route.params.cid;
    this.form.customerId = cid;
    this.detail(cid)
    this.getAddress()
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
      getAddress(this.form).then((res => {
        this.address = res.data
      }))
    },
    createAddress() {
      this.openAddress = true
    },
    updateAddress(row) {
      this.title = "编辑收货信息"
      this.reset()
      this.form = row
      this.openAddress = true
    },
    createInvoice() {
    },
    submitFormAddress() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id == undefined) {
            addAddress(this.form).then(res => {
              this.$modal.msgSuccess("创建成功");
              this.openAddress = false;
              this.getList();
            })
          } else {
            updateAddress(this.form).then(res => {
              this.$modal.msgSuccess("修改成功");
              this.openAddress = false;
              this.getList();
            })
          }
        }
      })
    },
    submitInvoice(){

    },
    handleDelete(row) {
      this.$modal.confirm('是否确认删除地址为"' + row.location + '"的数据项？').then(function () {
        return delAddress(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    reset() {
      this.resetForm("form");
    },
    cancel() {
      this.openAddress = false
    }
  }
}
</script>

<style scoped>

</style>
