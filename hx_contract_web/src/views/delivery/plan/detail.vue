<template>
  <div class="app-container">
    <el-form :model="planForm" ref="queryForm" size="small" :rules="rules" label-width="130px"
             style="margin: 15px">
      <div class="angel-card">
        <div style="display: flex;justify-content: space-between;align-items: center">
          <div>
            <span>发货单编号：</span>
            {{ deliveryForm.consignment.consigmentNumber }}
          </div>
          <div>
            <el-button size="mini" type="text" @click="notifyCommissioner"
                       v-hasPermi="['system:user:edit']" >通知单证专员</el-button>
            <el-button size="mini" type="text" @click="completeCustomsDeclaration"
              v-hasPermi="['system:user:edit']">报关完成</el-button>
            <el-button v-show="scope.row.customsDeclarationCompleted==1"
              size="mini" type="text" @click="synchronizeSAP"
              v-hasPermi="['system:user:edit']" >同步SAP
            </el-button>
          </div>
        </div>
      </div>
<!--      发货计划信息-->
      <div class="plan-header">
        <el-descriptions title="发货计划信息" size="medium" border :column="3">
          <el-descriptions-item label="原计划出库时间">{{ planForm.originalPlannedDeliveryDate }}</el-descriptions-item>
          <el-descriptions-item label="出库地点">{{ planForm.deliveryLocation }}</el-descriptions-item>
          <el-descriptions-item label="清关金额">{{ planForm.currentNode }}</el-descriptions-item>
        </el-descriptions>
      </div>
<!--      详细信息-->
      <div class="plan-header">
        <el-descriptions title="详细信息" size="medium" border :column="3">
          <el-descriptions-item label="INVOICE NO">{{ planForm.invoiceNo }}</el-descriptions-item>
          <el-descriptions-item label="客户">{{ planForm.customerId }}</el-descriptions-item>
          <el-descriptions-item label="收货方">{{ planForm.consignee }}</el-descriptions-item>
          <el-descriptions-item label="客户联系人">{{ planForm.customerContact }}</el-descriptions-item>
          <el-descriptions-item label="联系方式">{{ planForm.contactInformation }}</el-descriptions-item>
          <el-descriptions-item label="大洲">{{ planForm.continent }}</el-descriptions-item>
          <el-descriptions-item label="国家">{{ planForm.nation }}</el-descriptions-item>
          <el-descriptions-item label="通知方">{{ planForm.notifyId }}</el-descriptions-item>
          <el-descriptions-item label="SIHIPPING MARK">{{ planForm.shippingMark }}</el-descriptions-item>
          <el-descriptions-item label="是否通知单证专员">{{ planForm.isNoticeDocumentSpecialist }}</el-descriptions-item>
          <el-descriptions-item label="运费">{{ planForm.freight }}</el-descriptions-item>
          <el-descriptions-item label="保费">{{ planForm.premium }}</el-descriptions-item>
          <el-descriptions-item label="税费">{{ planForm.taxation }}</el-descriptions-item>
          <el-descriptions-item label="海运费">{{ planForm.oceanFreight }}</el-descriptions-item>
          <el-descriptions-item label="空运费">{{ planForm.airFreight }}</el-descriptions-item>
          <el-descriptions-item label="预计到货日">{{ planForm.expectedDeliveryDate }}</el-descriptions-item>
          <el-descriptions-item label="预计出运日">{{ planForm.expectedShipmentDate }}</el-descriptions-item>
          <el-descriptions-item label="最迟装箱日期">{{ planForm.latestPackingDate }}</el-descriptions-item>
          <el-descriptions-item label="信用证号码">{{ planForm.lcNo }}</el-descriptions-item>
          <el-descriptions-item label="信用证交单快递号">{{ planForm.lcDeliveryNo }}</el-descriptions-item>
          <el-descriptions-item label="SAP开票凭证">{{ planForm.lcDeliveryNo }}</el-descriptions-item>
          <el-descriptions-item label="SAP交货项目">{{ planForm.lcDeliveryNo }}</el-descriptions-item>
        </el-descriptions>
      </div>
      <div>
        <div>
          <span>实际开船日: {{ planForm.actualDepartureDate }}</span>
        </div>
        <div>
          <span>货物完成时间及地点: {{ planForm.goodsCompleteDateAndLocate }}</span>
        </div>
        <div>
          <span>保险条款: {{ planForm.insuranceClauses }}</span>
        </div>
        <div>
          <span>特殊要求: {{ planForm.specialRequirements }}</span>
        </div>
        <div>
          <span>备注: {{ planForm.remark }}</span>
        </div>
        <div>
          <span>报清关差异原因: {{ planForm.reportCustomsClearanceReason }}</span>
        </div>
      </div>
      <div class="plan-header">
        <el-descriptions title="报关信息" size="medium" border :column="3">
          <el-descriptions-item label="单证专员">{{ planForm.documentSpecialist }}</el-descriptions-item>
          <el-descriptions-item label="财务人员">{{ planForm.financialStaff }}</el-descriptions-item>
        </el-descriptions>
      </div>
<!--      汇总信息-->
      <div class="plan-header">
        <el-descriptions title="汇总信息" size="medium" border :column="3">
          <el-descriptions-item label="报关单位">{{ planForm.reportCustomsInstitutions }}</el-descriptions-item>
          <el-descriptions-item label="运输方式">{{ planForm.transType }}</el-descriptions-item>
          <el-descriptions-item label="柜型及数量">{{ planForm.abinetTypeAndQuantity }}</el-descriptions-item>
          <el-descriptions-item label="贸易方式">{{ planForm.tradeMode }}</el-descriptions-item>
          <el-descriptions-item label="出运港">{{ planForm.shipmentPort }}</el-descriptions-item>
          <el-descriptions-item label="目的港">{{ planForm.destinationPort }}</el-descriptions-item>
          <el-descriptions-item label="收汇方式">{{ planForm.collectionMethod }}</el-descriptions-item>
          <el-descriptions-item label="预收汇日期">{{ planForm.preCollectDate }}</el-descriptions-item>
          <el-descriptions-item label="成交方式">{{ planForm.transactionMethod }}</el-descriptions-item>
        </el-descriptions>
      </div>
<!--      实际业务报关信息-->
      <div class="plan-header">
        <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
          <div style="display: flex;align-items: center">
            <div class="line-item"></div>
            <span>实际业务报关信息</span>
          </div>
        </div>
        <el-table
          :data="planForm.reportList"
          border
          style="margin-top: 15px"
          size="mini"
        >
          <el-table-column label="序号" align="center" min-width="60px" prop="sequence">
          </el-table-column>
          <el-table-column label="货物描述(中文)" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="报关数量" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="报关金额" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="品牌型号" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="包装件数" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="包装种类" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="毛重" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="净重" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="体积(CBM)" align="center" min-width="60px">
          </el-table-column>
        </el-table>
      </div>
<!--      SAP财务核算收入-->
      <div class="plan-header">
        <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
          <div style="display: flex;align-items: center">
            <div class="line-item"></div>
            <span>SAP财务核算收入</span>
          </div>
        </div>
        <el-table
          :data="planForm.financialList"
          border
          style="margin-top: 15px"
          size="mini"
        >
          <el-table-column label="序号" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="是否同步SAP" align="center" min-width="60px">
            <template scope="scope">
              <dict-tag :options="dict.type.ynn" :value="scope.row.unitPrice"/>
            </template>
          </el-table-column>
          <el-table-column label="订单名称" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="订单编号" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="订单明细编号" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="产品型号" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="产品型号名" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="报关数量" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="报关剩余数量" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="订单数量" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="产品总金额" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="剩余报关金额" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="本次报关金额" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="实际报关金额" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="单价" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="SAP物料编码" align="center" min-width="60px">
          </el-table-column>
        </el-table>
      </div>
<!--      附件-->
      <div class="plan-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>附件</span>
        </div>
        <el-form-item label="">
          <fileUpload v-model="planForm.file"/>
        </el-form-item>
      </div>
    </el-form>
    <div style="text-align: right">
      <el-button :loading="buttonLoading" type="primary" @click="submitForm">提交</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import { detailPlanApi, updatePlanApi } from '@/api/plan'

export default {
  name: "detail",
  components: {Treeselect},
  dicts: ['sys_customer_status', 'sys_currency', 'continent', 'sys_y_n', 'sys_receive_master', 'sys_trans_category', 'trade_type', 'pol_cate', 'exs_cate', 'sold_for','ynn'],
  data() {
    return {
      planForm: {
        reportList: [],
        financialList:[]
      },
      nation: [
        {
          label: '中国',
          value: '1'
        },
        {
          label: '美国',
          value: '2'
        }
      ],
    }
  },
  created() {
    // 获取计划id
    this.planForm.planId=this.$route.params.oid
    // 获取计划详细
    this.getPlanDetail()
  },
  methods: {
    // 获取计划详细
    getPlanDetail(){
      this.planForm.planId
      let param={

      }
      detailPlanApi(param).then(res=>{
        this.planForm=res.data
      })
    },
    // 通知专证专员
    notifyCommissioner(){
      this.planForm.planId
      let param={

      }
      notifyCommissionerApi(param).then(res=>{
        this.$message({
          message: '已为您通知',
          type: 'success'
        });
      })
    },
    // 报关完成
    completeCustomsDeclaration(){
      this.planForm.planId
      let param={

      }
      completeCustomsDeclarationApi(param).then(res=>{
        this.$message({
          message: '报关已完成',
          type: 'success'
        });
        this.getPlanDetail();
      })
    },
    // 同步sap
    synchronizeSAP(){
      this.planForm.planId
      let param={

      }
      synchronizeSAPApi(param).then(res=>{
        this.$message({
          message: 'SAP同步已完成',
          type: 'success'
        });
        this.getPlanDetail();
      })
    },
    // 提交表单
    submitForm(){
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          if (this.planForm.file != null && this.planForm.file != "") {
            if (!Array.isArray(this.planForm.file)) {
              this.planForm.file = JSON.parse(this.planForm.file)
            }
          } else {
            this.planForm.file = []
          }
          updatePlanApi(this.planForm).then(res => {
            this.$message({
              message: '更新完成',
              type: 'success'
            });
            // 关闭表单
            this.$store.dispatch('tagsView/delView', this.$route)
            this.$router.go(-1)
          })
        }
      })
    },
    // 取消
    cancel(){
      this.$store.dispatch('tagsView/delView', this.$route)
      this.$router.go(-1)
    },
  }
}
</script>


<style scoped lang="scss">
.plan-header {
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
