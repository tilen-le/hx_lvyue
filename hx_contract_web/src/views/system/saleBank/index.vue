<template>
  <div class="app-container">
    <div class="angel-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-row>
          <el-col :span="6">
            <el-form-item label="受益人银行名称" prop="bankName">
              <el-input
                v-model="queryParams.bankName"
                placeholder="请输入"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item style="width:100%;text-align: right">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="angel-card-table">
      <el-button type="primary" icon="el-icon-add" size="mini" @click="addBank">新增</el-button>
      <el-table v-loading="loading" :data="bankList" border style="margin-top: 15px">
        <el-table-column label="受益人银行" align="center" key="bankName" prop="bankName"/>
        <el-table-column label="国内/国际" align="center" key="code" prop="code">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.bank_type" :value="scope.row.isInternational"/>
          </template>
        </el-table-column>
        <el-table-column label="银行账号" align="center" key="outBankCode" prop="outBankCode"/>
        <el-table-column label="银行国内账号" align="center" key="inBankCode" prop="inBankCode"/>
        <el-table-column label="受益人" align="center" key="beneficiary" prop="beneficiary"/>
        <el-table-column label="状态" align="center" key="status" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.available_type" :value="scope.row.status"/>
          </template>
        </el-table-column>
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
              @click="edit(scope.row)"
              v-hasPermi="['system:user:edit']"
            >编辑
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="detail(scope.row)"
              v-hasPermi="['system:user:edit']"
            >删除
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
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" label-position="left">
        <el-form-item label="受益人银行名称" prop="bankName">
          <el-input v-model="form.bankName" placeholder="请输入" maxlength="30"/>
        </el-form-item>
        <el-form-item label="国内/国际" prop="isInternational">
          <el-radio-group v-model="form.isInternational">
            <el-radio
              v-for="dict in dict.type.bank_type"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="银行账号" prop="outBankCode">
          <el-input v-model="form.outBankCode" placeholder="请输入" maxlength="50"/>
        </el-form-item>
        <el-form-item label="银行国内账号" prop="inBankCode">
          <el-input v-model="form.inBankCode" placeholder="请输入" maxlength="50"/>
        </el-form-item>
        <el-form-item label="受益人" prop="beneficiary">
          <el-input v-model="form.beneficiary" placeholder="请输入" maxlength="50"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.available_type"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import RegionSelect from "@/components/Forms/RegionSelect.vue";
import {addBank, listBank, updateBank} from "@/api/system/bank";

export default {
  name: "index",
  components: {RegionSelect},
  dicts: ['bank_type', 'available_type'],
  data() {
    return {
      queryParams: {
        pageSize: 10,
        pageNum: 1
      },
      loading: false,
      bankList: [],
      total: 0,
      title: "新增卖方银行",
      open: false,
      form: {},
      rules: {
        bankName: [
          {required: true, message: "请输入", trigger: "blur"}
        ],
        isInternational: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        outBankCode: [
          {required: true, message: "请输入", trigger: "blur"}
        ],
        beneficiary: [
          {required: true, message: "请输入", trigger: "blur"}
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleQuery() {
      this.getList()
    },
    resetQuery() {

    },
    cancel() {
      this.open = false
    },
    edit(row) {
      this.title = "编辑卖方银行"
      this.open = true
      this.form = row
    },
    addBank() {
      this.open = true
    },
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id == undefined) {
            addBank(this.form).then(res => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            })
          } else {
            updateBank(this.form).then(res => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            })
          }
        }
      })
    },
    getList() {
      listBank(this.queryParams).then(res => {
        this.bankList = res.rows
        this.total = res.total
      })
    }
  }
}
</script>

<style scoped>

</style>
