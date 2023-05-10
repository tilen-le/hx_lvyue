<template>
  <div class="app-container">
    <div class="angel-card">
      <div style="display: flex;justify-content: space-between;align-items: center">
        <div>
          <span>发货单编号：</span>
          {{ delivery.consigmentNumber }}
        </div>
        <div>
<!--          提交发货单审批-->
          <el-button @click="submitForm" type="primary" v-show="deliveryForm.status==0">提交审批</el-button>
          <i class="el-icon-close" style="cursor: pointer;margin-left: 15px" @click="close"></i>
        </div>
      </div>
    </div>
    <el-form :model="deliveryForm" size="small" :rules="rules" label-width="120px"
             style="margin: 15px">
      <div class="info-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>订单信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <div>
              <span>合同名称: {{ deliveryForm.contract.contractName }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span>订单编号: {{ deliveryForm.order.orderNumber }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span>订单名称: {{ deliveryForm.order.orderTitle }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span>客户名称: {{ deliveryForm.order.soldToPartyCd }}</span>
            </div>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <div>
              <span>事业部: {{ deliveryForm.order.businessUnit }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span>工厂: {{ deliveryForm.order.factory }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span>仓储部经理: {{ deliveryForm.order.factory }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span>内销-库管员: {{ deliveryForm.order.factory }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span>国内/国际营销部: {{ deliveryForm.order.marketingDepartment }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span>交货日期: {{ deliveryForm.order.requireDeliveryDate }}</span>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="info-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>收货信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <div>
              <span><span style="color: red">*</span>收货方: {{ deliveryForm.consigneeId }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span>收货联系人: {{ deliveryForm.addressId }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span>收货人电话: {{ deliveryForm.mobile }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span><span style="color: red">*</span>收货地址: {{ deliveryForm.address }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span><span style="color: red">*</span>详细地址: {{ deliveryForm.address }}</span>
            </div>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <div>
              <span>备注: {{ deliveryForm.remark }}</span>
            </div>
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
            <div>
              <span><span style="color: red">*</span>发货方: {{ deliveryForm.consignorCode }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span><span style="color: red">*</span>运输方式: {{ deliveryForm.transType }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span><span style="color: red">*</span>是否备表直发: {{ deliveryForm.isReserveSend }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span><span style="color: red">*</span>配件单独包装: {{ deliveryForm.isSeparatePackaging }}</span>
            </div>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <div>
              <span><span style="color: red">*</span>预计到货日期: {{ deliveryForm.expectedArrivalDate }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span><span style="color: red">*</span>预计质保起算日期: {{ deliveryForm.expectedWarrantyDate }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span><span style="color: red">*</span>预计验收日期: {{ deliveryForm.expectedCheckDate }}</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <span><span style="color: red">*</span>出厂日期: {{ deliveryForm.productDate }}</span>
            </div>
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
          <el-table-column label="发货数量" align="center" key="productNum" prop="productNum"/>
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
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleDownload(scope.row)"
                v-hasPermi="['system:oss:download']"
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
          <fileUpload v-model="deliveryForm.fileIds"/>
        </el-form-item>
      </div>
      <!--    接口日志-->
      <div class="claim-header">
        <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
          <div style="display: flex;align-items: center">
            <div class="line-item"></div>
            <span>接口日志</span>
          </div>
        </div>
        <el-table
          :data="logList"
          border
          style="margin-top: 15px"
          size="mini">
          <el-table-column label="执行时间" align="center" prop="createTime" min-width="120px">
          </el-table-column>
          <el-table-column label="所属模块" align="center" prop="syncSapInfoTime" min-width="120px">
          </el-table-column>
          <el-table-column label="接口ID" align="center" prop="claimNumber" min-width="120px">
          </el-table-column>
          <el-table-column label="接口名" align="center" prop="claimAmount" min-width="120px">
          </el-table-column>
          <el-table-column label="状态" align="center" prop="syncSapInfoTime" min-width="120px">
          </el-table-column>
          <el-table-column label="日志信息" align="center" prop="syncSapInfoTime" min-width="120px">
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" v-if="scope.row.status !='0'">
              <el-button
                size="mini"
                type="text"
                @click="cancel(scope.row)"
                v-hasPermi="['claim:list:cancel']"
              >接口重推
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-form>
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
import { getDeliveryApi } from '@/api/delivery'

export default {
  name: "detail",
  components: {RegionSelect},
  dicts: ['invoice_type', 'sys_trans_category', 'sys_y_n','delivery_category'],
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
      getDeliveryApi(params).then(res => {
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
  }
}
</script>

<style scoped>

</style>
