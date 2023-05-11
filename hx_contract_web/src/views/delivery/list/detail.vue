<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>发货单编号：</span>
          {{ deliveryForm.consignment.consigmentNumber }}
        </div>
        <div>
          <el-button @click="submitForm" type="primary" v-show="deliveryForm.status==0">提交审批</el-button>
          <i class="el-icon-close" style="cursor: pointer;margin-left: 15px" @click="close"></i>
        </div>
      </div>
    </div>

    <el-descriptions title="订单信息" size="medium" border :column="3">
      <el-descriptions-item label="合同名称">{{ deliveryForm.contract.contractName }}</el-descriptions-item>
      <el-descriptions-item label="订单编号">{{ deliveryForm.order.orderNumber }}</el-descriptions-item>
      <el-descriptions-item label="订单名称">{{ deliveryForm.order.orderTitle }}</el-descriptions-item>
      <el-descriptions-item label="客户名称">{{ deliveryForm.order.soldToParty }}</el-descriptions-item>
      <el-descriptions-item label="事业部">{{ deliveryForm.order.businessUnit }}</el-descriptions-item>
      <el-descriptions-item label="工厂">{{ deliveryForm.order.factory }}</el-descriptions-item>
      <el-descriptions-item label="仓储部经理">{{ deliveryForm.order.warehouseManager }}</el-descriptions-item>
      <el-descriptions-item label="内销-库管员">暂未返回</el-descriptions-item>
      <el-descriptions-item label="国内/国际营销部">{{ deliveryForm.order.marketingDepartment }}</el-descriptions-item>
      <el-descriptions-item label="交货日期">{{ deliveryForm.order.requireDeliveryDate }}</el-descriptions-item>
    </el-descriptions>
    <el-descriptions title="收货信息" size="medium" border :column="3">
      <el-descriptions-item label="收货方">{{ deliveryForm.order.reciver }}</el-descriptions-item>
      <el-descriptions-item label="收货联系人">{{ deliveryForm.customerConsignment.name }}</el-descriptions-item>
      <el-descriptions-item label="收货人电话">{{ deliveryForm.customerConsignment.phone }}</el-descriptions-item>
      <el-descriptions-item label="收货地址">{{ deliveryForm.customerConsignment.location }}</el-descriptions-item>
      <el-descriptions-item label="详细地址">{{ deliveryForm.customerConsignment.address }}</el-descriptions-item>
    </el-descriptions>
    <el-descriptions title="发货信息" size="medium" border :column="3">
      <el-descriptions-item label="发货方">
        <dict-tag :options="dict.type.delivery_category" :value="deliveryForm.consignment.consignorCode"/>
      </el-descriptions-item>
      <el-descriptions-item label="运输方式">
        <dict-tag :options="dict.type.sys_trans_category" :value="deliveryForm.consignment.transType"/>
      </el-descriptions-item>
      <el-descriptions-item label="是否备表直发">
        <dict-tag :options="dict.type.sys_y_n" :value="deliveryForm.consignment.isReserveSend"/>
      </el-descriptions-item>
      <el-descriptions-item label="配件单独包装">
        <dict-tag :options="dict.type.ynn" :value="deliveryForm.consignment.isSeparatePackaging"/>
      </el-descriptions-item>
      <el-descriptions-item label="预计到货日期">{{ deliveryForm.consignment.expectedArrivalDate }}</el-descriptions-item>
      <el-descriptions-item label="预计质保起算日期">{{ deliveryForm.consignment.expectedWarrantyDate }}</el-descriptions-item>
      <el-descriptions-item label="预计验收日期">{{ deliveryForm.consignment.expectedCheckDate }}</el-descriptions-item>
      <el-descriptions-item label="出厂日期">{{ deliveryForm.consignment.productDate }}</el-descriptions-item>
      <el-descriptions-item label="备注">{{ deliveryForm.consignment.remark }}</el-descriptions-item>
    </el-descriptions>
    <div>
      <span>发货明细</span>
      <el-table :data="deliveryForm.products" border style="margin-top: 15px">
        <el-table-column label="SAP订单明细编码" align="center" prop="sapDetailNumber">
          <template slot-scope="scope">
            {{ scope.row.orderProduct.sapDetailNumber }}
          </template>
        </el-table-column>
        <el-table-column label="产品名称" align="center" key="productName" prop="productName">
          <template slot-scope="scope">
            {{ scope.row.orderProduct.productName }}
          </template>
        </el-table-column>
        <el-table-column label="发货数量" align="center" key="productNum" prop="productNum"/>
        <el-table-column label="剩余发货" align="center" key="notSentNum" prop="notSentNum">
          <template slot-scope="scope">
            {{ scope.row.orderProduct.notSentNum }}
          </template>
        </el-table-column>
        <el-table-column label="库存" align="center" key="inStorageNum" prop="inStorageNum">
          <template slot-scope="scope">
            {{ scope.row.orderProduct.inStorageNum }}
          </template>
        </el-table-column>
        <el-table-column label="SAP物料编码" align="center" key="productNumber" prop="productNumber">
          <template slot-scope="scope">
            {{ scope.row.orderProduct.productNumber }}
          </template>
        </el-table-column>
        <el-table-column label="单价" align="center" key="unitPrice" prop="unitPrice">
          <template slot-scope="scope">
            {{ deliveryForm.order.currency }} {{ scope.row.orderProduct.unitPrice }}
          </template>
        </el-table-column>
        <el-table-column label="技术要求" align="center" key="technicalRequirement" prop="technicalRequirement">
        </el-table-column>
      </el-table>
    </div>

    <div class="info-header">
      <div style="display: flex;align-items: center">
        <div class="line-item"></div>
        <span>附件</span>
      </div>
      <div style="padding: 15px">
        <el-link v-for="fileItem in deliveryForm.ossList"
                 :underline="false"  type="primary" target="_blank" :href="`${fileItem.url}`" class="fileShow">
          {{ fileItem.originalName }} </el-link>
      </div>
    </div>



<!--    提交审批弹窗-->
    <el-dialog title="提示" :visible.sync="submitForApprovalDialogVisible" width="30%" >
      <span> 非常抱歉，该发货单必要信息缺失，请完善后提交</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="submitForApprovalDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForApprovalDialogVisible = false">编辑发货单</el-button>
      </span>
    </el-dialog>
<!--    撤销审批弹窗-->
    <el-dialog title="提示" :visible.sync="revokeApprovalDialogVisible" width="30%">
      <span>您是否确认撤销该发货单的审批？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="revokeApprovalDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="revokeApprovalDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import RegionSelect from "@/components/Forms/RegionSelect.vue";
import {getOrderDetail} from "@/api/order";
import {getAddressByCode, getOpenBankByBe, listCustomer} from "@/api/customer";
import {addDelivery, addInvoice} from "@/api/invoice";
import { getDeliveryDetail } from '@/api/delivery'

export default {
  name: "detail",
  components: {RegionSelect},
  dicts: ['invoice_type', 'sys_trans_category', 'sys_y_n','delivery_category', 'ynn'],
  data() {
    return {
      // 发货单
      deliveryForm: {},
      // 接口日志列表
      logList: [],
      // 提交审批弹窗
      submitForApprovalDialogVisible: false,
      // 撤销审批弹窗
      revokeApprovalDialogVisible: false,
    }
  },
  created() {
    this.getDeliveryDetail()
  },
  methods: {
    // 获取发货单详细
    getDeliveryDetail() {
      const oid = this.$route.params.oid;
      const params = {id: oid}
      getDeliveryDetail(params).then(res => {
        this.deliveryForm = res.data
      })
    },
    // 提交发货单审批
    submitForm(val){
      // 收货方、发货地址、详细地址、发货方、运输方式、是否备表直发、配件单独包装、预计到货日期、预计质保起算日期、预计验收日期、出厂日期
      // 验证发货单字段
      if(null!=this.deliveryForm.Consignee&&null!=this.deliveryForm.shippingAddress&&
        null!=this.deliveryForm.detailedAddress&&null!=this.deliveryForm.shippers&&
        null!=this.deliveryForm.tranSport&&null!=this.deliveryForm.straightHair&&
        null!=this.deliveryForm.individuallyPackaged&&null!=this.deliveryForm.estimatedArrivalDate&&
        null!=this.deliveryForm.estimatedCommencementDate&&null!=this.deliveryForm.estimatedAcceptanceDate&&
        null!=this.deliveryForm.factoryDate
      ){
        // 表单校验通过&&发送发货单申请
        addDelivery(this.deliveryForm).then(res => {
          this.$modal.msgSuccess("提交成功");
        })
      }else{
        this.submitForApprovalDialogVisible=true
      }
    },
    // 接口重推
    cancel(){

    },
    // 取消提交审批
    cancelSubmissionForApproval(){
      this.submitForApprovalDialogVisible=false
    },
    // 跳转编辑发货单
    toDeliveryEdit() {
      this.submitForApprovalDialogVisible=false
      this.$router.push(`/delivery/create/index/${this.orderDetail.order.id}`)
    },
    // 确定撤销审批
    decideRevokeApproval(){

      this.revokeApprovalDialogVisible=false
      this.getDeliveryDetail()
    },
    // 取消撤销审批
    cancelRevokeApproval(){
      this.revokeApprovalDialogVisible=false
    },
    close() {

    }
  }
}
</script>

<style scoped>
  .fileShow{
    color: blue;
    margin-right: 15px;
  }
  .fileShow:hover{
    color: red;
  }
</style>
