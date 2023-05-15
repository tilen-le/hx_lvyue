<template>
  <div class="app-container">
    <div class="contract-card">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="发货审批" name="1">
          <el-button
            type="primary"
            icon="el-icon-plus"
            @click="handleAdd"
            v-hasPermi="['approve:config:add']"
          >新建
          </el-button>
          <el-table v-loading="loading" :data="delivery" border style="margin-top: 15px">
            <el-table-column label="工厂" align="center" prop="factory"/>
            <el-table-column label="国内/国际营销部" align="center" prop="configId">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sale_dept" :value="scope.row.saleDept"/>
              </template>
            </el-table-column>
            <el-table-column label="库管" align="center" prop="storeKpName"/>
            <el-table-column label="仓储部经理" align="center" prop="wareName"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['approve:config:update']"
                >修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['approve:config:remove']"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="开票审批" name="2">
          <el-button
            type="primary"
            icon="el-icon-plus"
            @click="handleAddInvoice"
            v-hasPermi="['approve:invoice:add']"
          >新建
          </el-button>
          <el-table v-loading="loading" :data="invoice" border style="margin-top: 15px">
            <el-table-column label="工厂" align="center" prop="factory"/>
            <el-table-column label="国内/国际营销部" align="center" prop="businessUnit">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sale_dept" :value="scope.row.businessUnit"/>
              </template>
            </el-table-column>
            <el-table-column label="财务" align="center" prop="bookName"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleInvoiceUpdate(scope.row)"
                  v-hasPermi="['approve:invoice:update']"
                >修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['approve:invoice:remove']"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <el-dialog :title="title" :visible.sync="showDelivery" width="500px" append-to-body>
      <el-form ref="approveForm" :model="deliveryConfig" :rules="deliveryRules" label-width="140px">
        <el-form-item label="工厂" prop="factory">
          <el-select v-model="deliveryConfig.factory" placeholder="请选择" style="width: 100%">
            <el-option
              v-for="dict in dict.type.config_factory"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="国内/国际营销部" prop="saleDept">
          <el-select v-model="deliveryConfig.saleDept" placeholder="请选择" style="width: 100%">
            <el-option
              v-for="dict in dict.type.sale_dept"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库管" prop="storeKeeper">
          <el-select v-model="deliveryConfig.storeKeeper" placeholder="请选择" style="width: 100%">
            <el-option
              v-for="item in storeKeeper"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            >{{item.nickName}}({{item.userName}})</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓储部经理" prop="wareManager">
          <el-select v-model="deliveryConfig.wareManager" placeholder="请选择" style="width: 100%">
            <el-option
              v-for="item in wareKeeper"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            >{{item.nickName}}({{item.userName}})</el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleDeliveryAdd" v-hasPermi="['approve:config:add','approve:config:update']">确 定</el-button>
        <el-button @click="showDelivery = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="invoiceTitle" :visible.sync="showInvoice" width="500px" append-to-body>
      <el-form ref="invoiceForm" :model="invoiceConfig" :rules="invoiceRules" label-width="140px">
        <el-form-item label="工厂" prop="factory">
          <el-select v-model="invoiceConfig.factory" placeholder="请选择" style="width: 100%">
            <el-option
              v-for="dict in dict.type.config_factory"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="事业部" prop="businessUnit">
          <el-select v-model="invoiceConfig.businessUnit" placeholder="请选择" style="width: 100%">
            <el-option
              v-for="dict in dict.type.sale_dept"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="财务" prop="bookKeeper">
          <el-select v-model="invoiceConfig.bookKeeper" placeholder="请选择" style="width: 100%">
            <el-option
              v-for="item in bookKeeper"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            >{{item.nickName}}({{item.userName}})</el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleInvoiceAdd"  v-hasPermi="['approve:config:add','approve:config:update']">确 定</el-button>
        <el-button @click="showInvoice = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {listBookKeeper, listStoreKeeper, listWareKeeper} from "@/api/system/role";
import {addApproveConfig, delApproveConfig, listApproveConfig, updateApproveConfig} from "@/api/system/config";
export default {
  name: "index",
  dicts: ['config_factory', 'sale_dept'],
  data() {
    return {
      activeName: '1',
      delivery: [],
      loading: false,
      title: '新建发货审批配置',
      invoiceTitle: '新建开票审批配置',
      showDelivery: false,
      showInvoice: false,
      deliveryConfig: {},
      invoiceConfig:{},
      storeKeeper: [],
      wareKeeper: [],
      bookKeeper:[],
      invoice:[],
      deliveryRules: {
        factory: [
          {required: true, message: "请选择", trigger: "blur"},
        ],
        saleDept: [
          {required: true, message: "请选择", trigger: "blur"},
        ],
        storeKeeper: [
          {required: true, message: "请选择收件人", trigger: "blur"},
        ],
      },
      invoiceRules: {
        factory: [
          {required: true, message: "请选择", trigger: "blur"},
        ],
        businessUnit: [
          {required: true, message: "请选择", trigger: "blur"},
        ],
        bookKeeper: [
          {required: true, message: "请选择收件人", trigger: "blur"},
        ],
      }
    }
  },
  created() {
    this.getDeliveryConfig()

  },
  methods: {
    handleClick(tab, event) {
      if(tab.name=='1'){
        this.getDeliveryConfig()
      }else {
        this.getInvoiceConfig()
      }
    },
    handleDeliveryAdd() {
      this.$refs["approveForm"].validate(valid => {
        if (valid) {
          this.deliveryConfig.category = 1
          if (this.deliveryConfig.id == undefined) {
            addApproveConfig(this.deliveryConfig).then(res => {
              this.$modal.msgSuccess("新增成功");
              this.showDelivery = false;
              this.getDeliveryConfig();
            })
          } else {
            updateApproveConfig(this.deliveryConfig).then(res => {
              this.$modal.msgSuccess("修改成功");
              this.showDelivery = false;
              this.getDeliveryConfig();
            })
          }
        }
      })
    },
    handleInvoiceAdd() {
      this.$refs["invoiceForm"].validate(valid => {
        if (valid) {
          this.invoiceConfig.category = 2
          this.invoiceConfig.saleDept = this.invoiceConfig.businessUnit
          if (this.invoiceConfig.id == undefined) {
            addApproveConfig(this.invoiceConfig).then(res => {
              this.$modal.msgSuccess("新增成功");
              this.showInvoice = false;
              this.getInvoiceConfig();
            })
          } else {
            updateApproveConfig(this.invoiceConfig).then(res => {
              this.$modal.msgSuccess("修改成功");
              this.showInvoice = false;
              this.getInvoiceConfig();
            })
          }
        }
      })
    },
    getDeliveryConfig() {
      const param = {
        pageNum: 1,
        pageSize: 100,
        category: 1
      }
      listApproveConfig(param).then(res => {
        this.delivery = res.rows
      })
    },
    getInvoiceConfig() {
      const param = {
        pageNum: 1,
        pageSize: 100,
        category: 2
      }
      listApproveConfig(param).then(res => {
        this.invoice = res.rows
      })
    },
    handleAdd() {
      this.title = '新建发货审批配置'
      this.showDelivery = true
      this.getStoreKeeper()
    },
    handleAddInvoice() {
      this.invoiceTitle = '新建开票审批配置'
      this.showInvoice = true
      this.getStoreKeeper()
    },
    handleUpdate(row) {
      this.title = "编辑发货审批配置"
      this.showDelivery = true
      this.deliveryConfig = row
      this.getStoreKeeper()
    },
    handleInvoiceUpdate(row) {
      this.invoiceTitle = "编辑发货审批配置"
      this.showInvoice = true
      this.invoiceConfig = row
      this.getStoreKeeper()
    },
    getStoreKeeper() {
      const params = {
        pageSize: 100,
        pageNum: 1
      }
      const params3 = {
        roleId: "1653338831736295426",
      }
      listStoreKeeper(params).then(res => {
        this.storeKeeper = res.rows
      })
      listWareKeeper(params).then(res => {
        this.wareKeeper = res.rows
      })
      listBookKeeper(params3).then(res => {
        this.bookKeeper = res.data
      })
    },
    handleDelete(row) {
      this.$modal.confirm('确认要删除' + row.id + '配置吗？').then(function () {
        return delApproveConfig(row);
      }).then(() => {
        this.$modal.msgSuccess("删除成功");
        this.getDeliveryConfig()
        this.getInvoiceConfig()
      }).catch(function () {
      });
    }
  }
}

</script>

<style scoped>

</style>
