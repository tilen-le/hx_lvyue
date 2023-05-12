<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>回款编号：</span>
          {{ paymentDetail.paymentNumber }}
        </div>
        <i class="el-icon-close" style="cursor: pointer" @click="close"></i>
      </div>
    </div>
    <div class="claim-header">
      <div style="display: flex;align-items: center">
        <div class="line-item"></div>
        <span>回款信息</span>
      </div>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>客户回款编号: {{ paymentDetail.paymentNumber }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>凭证编号: {{ paymentDetail.documentNumber }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>公司名称: {{ paymentDetail.corporateName }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>客户名称: {{ paymentDetail.customerName }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>回款币种: {{ paymentDetail.paymentCurrency }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>到账金额: {{ paymentDetail.receivedAmount }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>回款已分配金额: {{ paymentDetail.allocatedAmount }}</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <span>回款未分配金额: {{ paymentDetail.undistributedAmount }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 15px">
        <el-col :span="6">
          <div>
            <span>负责人: {{ paymentDetail.diretor }}</span>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="claim-header">
      <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>认领明细</span>
        </div>
        <el-button type="primary" icon="el-icon-plus" @click="createClaim" style="margin-left: 15px">添加行</el-button>
      </div>
      <el-table
        :data="form"
        border
        style="margin-top: 15px"
        size="mini"
      >
        <el-table-column label="认领订单" align="center" min-width="120px">
          <template slot-scope="scope">
            <el-select v-model="scope.row.orderId" placeholder="请选择">
              <el-option
                v-for="dict in order"
                :key="dict.id"
                :label="dict.orderTitle"
                :value="dict.id"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="认领里程碑" align="center" min-width="120px">
          <template slot-scope="scope">
            <el-select v-model="scope.row.milestonesId" placeholder="请选择">
              <el-option
                v-for="dict in dict.type.claim_progress_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="剩余未回款" align="center" min-width="120px">
          <template slot-scope="scope">
            <span>{{ paymentDetail.paymentCurrency }} {{ scope.row.unAmount }}</span>
          </template>
        </el-table-column>
        <el-table-column label="认领币种" align="center" min-width="120px">
          <template slot-scope="scope">
            <el-select v-model="scope.row.currency" placeholder="请选择">
              <el-option
                v-for="dict in dict.type.sys_currency"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column
          label="认领金额"
          align="center"
        >
          <template slot-scope="scope">
            <el-input
              controls-position="right"
              :max="scope.row.unAmount"
              :precision="2"
              placeholder="标价"
              style="width: 70%"
              v-model="scope.row.amount"
            />
            <el-button
              type="text"
              style="margin-left: 5px"
              @click="addAllAmount(scope.row, scope.$index)"
              v-hasPermi="['payment:claim:all']"
            >全部
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          fixed="right"
        >
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="handleRemovePrice(scope.row, scope.$index)"
              v-hasPermi="['payment:create:create:delete']"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="text-align: end;margin-top: 30px">
      <el-button :loading="buttonLoading" type="primary" @click="submitForm" v-hasPermi="['payment:claim:add']">保存</el-button>
      <el-button @click="close">取 消</el-button>
    </div>
  </div>
</template>

<script>
import {addClaim, getPayment} from "@/api/payment";
import {listCusOrder} from "@/api/order";

export default {
  name: "createClaim",
  dicts: ['claim_progress_status', 'sys_currency'],
  data() {
    return {
      paymentDetail: {},
      form: [],
      order: [],
      buttonLoading: false
    }
  },
  created() {
    this.getPayment()
  },
  methods: {
    getPayment() {
      const params = {id: this.$route.params.pid}
      getPayment(params).then(res => {
        this.paymentDetail = res.data
        this.getCusOrder()
      })
    },
    handleRemovePrice(idx, index) {
      this.form.splice(index, 1);
    },
    addAllAmount(idx, index) {
      idx.amount = idx.unAmount
    },
    close() {
      this.form = []
      this.$router.go(-1)
    },
    createClaim() {
      const length = this.form.length;
      let unCalAmount = this.paymentDetail.undistributedAmount
      if (this.form.length != 0) {
        unCalAmount = unCalAmount - this.calAmount()
      }
      this.form.push({
        key:
          length === 0
            ? 1
            : parseInt(this.form[length - 1].key) + 1,
        orderId: undefined,
        milestonesId: undefined,
        unAmount: unCalAmount,
        currency: undefined,
        amount: undefined,
      });
    },
    calAmount() {
      console.log(this.form)
      let amounted = 0
      this.form.map(item => {
        if (item.amount != undefined) {
          amounted = amounted + parseFloat(item.amount)
        }
      })
      return amounted
    },
    getCusOrder() {
      const params = {
        cusCode: this.paymentDetail.cusCode
      }
      listCusOrder(params).then(res => {
        this.order = res.data
      })
    },
    submitForm() {
      if (this.form.length == 0) {
        this.$modal.msgWarning("请先添加认领明细");
        return;
      }
      this.buttonLoading = true
      const params = {
        paymentId: this.paymentDetail.id,
        details: this.form
      }
      addClaim(params).then(res => {
        this.$modal.msgSuccess("认领申请成功");
      }).finally(() => {
        this.buttonLoading = false
      })
    }
  }
}
</script>

<style scoped lang="scss">
.claim-header {
  background: white;
  border-radius: 4px;
  margin-top: 15px;
  padding-top: 15px;
  padding-bottom: 10px;
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
