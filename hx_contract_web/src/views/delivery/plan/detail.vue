<template>
  <div class="app-container">
    <el-form :model="planForm" ref="queryForm" size="small" label-width="130px"
             style="margin: 15px">
      <div class="angel-card">
        <div style="display: flex;justify-content: space-between;align-items: center">
          <div>
            <span>发货单编号：</span>
            {{ planForm.planCode }}
          </div>
          <div>
            <el-button size="mini" type="text" @click="notifyCommissioner"
                       v-show="null!=planForm.reportCustomsComplted&&planForm.reportCustomsComplted==0"
                       v-hasPermi="['delivery:plan:inform']" >通知单证专员</el-button>
            <el-button v-show="null!=planForm.reportCustomsComplted&&planForm.reportCustomsComplted==0"
              size="mini" type="text" @click="synchronizeSAP"
                       v-hasPermi="['delivery:plan:declare']" >报关完成</el-button>
            <el-button v-show="null!=planForm.reportCustomsComplted&&planForm.reportCustomsComplted==1"
                       size="mini" type="text" @click="synchronizeSAP"
                       v-hasPermi="['delivery:plan:sap']">同步SAP
            </el-button>
          </div>
        </div>
      </div>
<!--      发货计划信息-->
      <div class="plan-header"  style="padding-right: 10px;padding-left: 10px">
        <el-descriptions title="发货计划信息" size="medium" border >
          <el-descriptions-item label="原计划出库时间">{{ planForm.originalPlannedDeliveryDate }}</el-descriptions-item>
          <el-descriptions-item label="出库地点">{{ planForm.deliveryLocation }}</el-descriptions-item>
          <el-descriptions-item label="清关金额">{{ planForm.currentNode }}</el-descriptions-item>
        </el-descriptions>
      </div>
<!--      详细信息-->
      <div class="plan-header" style="padding-right: 10px;padding-left: 10px">
        <el-descriptions title="详细信息" size="medium" border :column="3">
          <el-descriptions-item label="INVOICE NO">{{ planForm.invoiceNo }}</el-descriptions-item>
          <el-descriptions-item label="客户">{{ planForm.customerName }}</el-descriptions-item>
          <el-descriptions-item label="收货方">{{ planForm.consigneeName }}</el-descriptions-item>
          <el-descriptions-item label="客户联系人">{{ planForm.customerContactName }}</el-descriptions-item>
          <el-descriptions-item label="联系方式">{{ planForm.contactInformation }}</el-descriptions-item>
          <el-descriptions-item label="大洲">
            <dict-tag :options="dict.type.continent" :value="planForm.continent"/>
          </el-descriptions-item>
          <el-descriptions-item label="国家">
            <dict-tag :options="dict.type.nation" :value="planForm.nation"/>
          </el-descriptions-item>
          <el-descriptions-item label="通知方">{{ planForm.notifyName }}</el-descriptions-item>
          <el-descriptions-item label="SIHIPPING MARK">{{ planForm.shippingMark }}</el-descriptions-item>
          <el-descriptions-item label="是否通知单证专员">
              <dict-tag :options="dict.type.ynn" :value="planForm.isNoticeDocumentSpecialist"/>
          </el-descriptions-item>
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
      <div class="plan-header" style="padding-right: 10px;padding-left: 10px">
        <el-descriptions title="报关信息" size="medium" border :column="3">
          <el-descriptions-item label="单证专员">{{ planForm.documentSpecialistName }}</el-descriptions-item>
          <el-descriptions-item label="财务人员">{{ planForm.financialStaffName }}</el-descriptions-item>
        </el-descriptions>
      </div>
<!--      汇总信息-->
      <div class="plan-header" style="padding-right: 10px;padding-left: 10px">
        <el-descriptions title="汇总信息" size="medium" border :column="3">
          <el-descriptions-item label="报关单位">{{ planForm.reportCustomsInstitutions }}</el-descriptions-item>
          <el-descriptions-item label="运输方式">
            <dict-tag :options="dict.type.sys_trans_category" :value="planForm.transType"/>
          </el-descriptions-item>
          <el-descriptions-item label="柜型及数量">{{ planForm.abinetTypeAndQuantity }}</el-descriptions-item>
          <el-descriptions-item label="贸易方式">
            <dict-tag :options="dict.type.trade_type" :value="planForm.tradeMode"/>
          </el-descriptions-item>
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
          <el-table-column   label="编号" align="center">
            <template slot-scope="scop">
              {{scop.$index+1}}
            </template>
          </el-table-column>
          <el-table-column label="货物描述(中文)" align="center" min-width="60px" prop="goodsDescCn">
          </el-table-column>
          <el-table-column label="报关数量" align="center" min-width="60px" prop="num">
          </el-table-column>
          <el-table-column label="报关金额" align="center" min-width="60px" prop="amount">
          </el-table-column>
          <el-table-column label="品牌型号" align="center" min-width="60px" prop="brandModel">
          </el-table-column>
          <el-table-column label="包装件数" align="center" min-width="60px" prop="packNumber">
          </el-table-column>
          <el-table-column label="包装种类" align="center" min-width="60px" prop="packagingType">
          </el-table-column>
          <el-table-column label="毛重" align="center" min-width="60px" prop="grossWeight">
          </el-table-column>
          <el-table-column label="净重" align="center" min-width="60px" prop="netWeight">
          </el-table-column>
          <el-table-column label="体积(CBM)" align="center" min-width="60px" prop="volume">
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
          <el-table-column   label="编号" align="center">
            <template slot-scope="scop">
              {{scop.$index+1}}
            </template>
          </el-table-column>
          <el-table-column label="是否同步SAP" align="center" min-width="60px" prop="sapSyncFlag">
            <template scope="scope">
              <dict-tag :options="dict.type.ynn" :value="scope.row.sapSyncFlag"/>
            </template>
          </el-table-column>
          <el-table-column label="订单名称" align="center" min-width="60px" prop="orderTitle">
          </el-table-column>
          <el-table-column label="订单编号" align="center" min-width="60px" prop="orderNumber">
          </el-table-column>
          <el-table-column label="订单明细编号" align="center" min-width="60px" prop="productNumber">
          </el-table-column>
          <el-table-column label="产品型号" align="center" min-width="60px" prop="productModel">
          </el-table-column>
          <el-table-column label="产品型号名" align="center" min-width="60px" prop="">
          </el-table-column>
          <el-table-column label="报关数量" align="center" min-width="60px" prop="reportCustomsNum">
          </el-table-column>
          <el-table-column label="报关剩余数量" align="center" min-width="60px" prop="reportCustomsResidueNum">
          </el-table-column>
          <el-table-column label="订单数量" align="center" min-width="60px" prop="num">
          </el-table-column>
          <el-table-column label="产品总金额" align="center" min-width="60px" prop="totalProductAmount">
          </el-table-column>
          <el-table-column label="剩余报关金额" align="center" min-width="60px" prop="remainingReportCustomsAmount">
          </el-table-column>
          <el-table-column label="本次报关金额" align="center" min-width="60px" prop="currentReportCustomsAmount">
          </el-table-column>
          <el-table-column label="实际报关金额" align="center" min-width="60px" prop="realityReportCustomsAmount">
          </el-table-column>
          <el-table-column label="单价" align="center" min-width="60px" prop="unitPrice">
          </el-table-column>
          <el-table-column label="SAP物料编码" align="center" min-width="60px" prop="sapMaterialCode">
          </el-table-column>
        </el-table>
      </div>
<!--      附件-->
      <div class="plan-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>附件</span>
        </div>
        <div style="padding: 15px">
          <el-link v-for="fileItem in planForm.file"
                   :underline="false"  type="primary" target="_blank" :href="`${fileItem.url}`" class="fileShow">
            {{ fileItem.originalName }} </el-link>
        </div>
      </div>
    </el-form>
    <div style="text-align: right">
<!--      <el-button type="primary" @click="submitForm">提交</el-button>-->
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import { detailPlanApi, updatePlanApi,synchronizeSAPApi } from '@/api/plan'

export default {
  name: "detail",
  components: {Treeselect},
  dicts: ['sys_customer_status', 'sys_currency', 'continent', 'sys_y_n', 'sys_receive_master',
    'sys_trans_category', 'trade_type', 'pol_cate', 'exs_cate', 'sold_for','ynn','nation'],
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
        id: this.planForm.planId
      }
      detailPlanApi(param).then(res=>{
        this.planForm=res.data
        this.planForm.file=this.planForm.sysOssList
      })
    },
    // 通知专证专员
    notifyCommissioner(){
      this.planForm.planId
      let param={
        id: this.planForm.planId
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
        id: this.planForm.planId
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

      this.$confirm('此订单将会同步sap, 请问您是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let param={
          id: this.planForm.planId
        }
        debugger
        synchronizeSAPApi(param).then(res=>{
          this.$message({
            message: 'SAP同步已完成',
            type: 'success'
          });
          this.getPlanDetail();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
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
