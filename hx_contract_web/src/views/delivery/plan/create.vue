<template>
  <div class="app-container">
    <el-form :model="planForm" ref="queryForm" size="small" label-width="130px"
             style="margin: 15px">
      <div class="plan-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>发货计划信息</span>
        </div>
        <el-row style="margin: 15px 15px 0px 15px">
          <el-col :span="6">
            <el-form-item label="原计划出库时间" prop="originalPlannedDeliveryDate">
              <el-date-picker
                v-model="planForm.originalPlannedDeliveryDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出库地点" prop="deliveryLocation">
              <el-input v-model="planForm.deliveryLocation" placeholder="请输入" maxlength="30"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="清关金额" prop="currentNode">
              <el-input v-model="planForm.currentNode" placeholder="请输入" maxlength="30"/>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="plan-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>详细信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="INVOICE NO" prop="invoiceNo">
              <el-input v-model="planForm.invoiceNo" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户" prop="customerId">
              <el-select
                v-model="planForm.customerId"
                placeholder="请选择"
                clearable
                style="width: 90%"
              >
                <el-option
                  v-for="dict in dict.type.sys_customer_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收货方" prop="consignee">
              <el-select
                v-model="planForm.consignee"
                placeholder="请选择"
                clearable
                style="width: 90%"
              >
                <el-option
                  v-for="dict in dict.type.sys_customer_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户联系人" prop="customerContact">
              <el-input v-model="planForm.customerContact" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="联系方式" prop="contactInformation">
              <el-input v-model="planForm.contactInformation" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="大洲" prop="continent">
              <el-select
                v-model="planForm.continent"
                placeholder="请选择"
                clearable
                style="width: 90%">
                >
                <el-option
                  v-for="dict in dict.type.sys_customer_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="国家" prop="nation">
              <el-select
                v-model="planForm.nation"
                placeholder="请选择"
                clearable
                style="width: 90%">
                >
                <el-option
                  v-for="dict in dict.type.sys_customer_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="通知方" prop="notifyId">
              <el-select
                v-model="planForm.notifyId"
                placeholder="请选择"
                clearable
                style="width: 90%"
              >
                <el-option
                  v-for="dict in dict.type.sys_customer_status"
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
            <el-form-item label="SIHIPPING MARK" prop="shippingMark">
              <el-input v-model="planForm.shippingMark" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="是否通知单证专员" prop="isNoticeDocumentSpecialist">
              <el-select
                v-model="planForm.isNoticeDocumentSpecialist"
                placeholder="请选择"
                clearable
                style="width: 90%"
              >
                <el-option
                  v-for="dict in dict.type.sys_customer_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="运费" prop="freight">
              <el-input v-model="planForm.freight" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="保费" prop="premium">
              <el-input v-model="planForm.premium" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="税费" prop="taxation">
              <el-input v-model="planForm.taxation" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="海运费" prop="oceanFreight">
              <el-input v-model="planForm.oceanFreight" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="空运费" prop="airFreight">
              <el-input v-model="planForm.airFreight" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="预计到货日" prop="expectedDeliveryDate">
              <el-date-picker
                v-model="planForm.expectedDeliveryDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="预计出运日" prop="expectedShipmentDate">
              <el-date-picker
                v-model="planForm.expectedShipmentDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="最迟装箱日期" prop="latestPackingDate">
              <el-date-picker
                v-model="planForm.latestPackingDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="信用证号码" prop="lcNo">
              <el-input v-model="planForm.lcNo" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="信用证交单快递号" prop="lcDeliveryNo">
              <el-input v-model="planForm.lcDeliveryNo" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="实际开船日" prop="actualDepartureDate">
              <el-date-picker
                v-model="planForm.actualDepartureDate"
                type="date"
                style="width: 90%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="货物完成时间及地点" prop="goodsCompleteDateAndLocate">
          <el-input v-model="planForm.goodsCompleteDateAndLocate" placeholder="请输入" type="textarea"
                    style="width: 95%"/>
        </el-form-item>
        <el-form-item label="保险条款" prop="insuranceClauses">
          <el-input v-model="planForm.insuranceClauses" placeholder="请输入" type="textarea"
                    style="width: 95%"/>
        </el-form-item>
        <el-form-item label="特殊要求" prop="specialRequirements">
          <el-input v-model="planForm.specialRequirements" placeholder="请输入" type="textarea"
                    style="width: 95%"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="planForm.remark" placeholder="请输入" type="textarea"
                    style="width: 95%"/>
        </el-form-item>
        <el-form-item label="报清关差异原因" prop="reportCustomsClearanceReason">
          <el-input v-model="planForm.reportCustomsClearanceReason" placeholder="请输入" type="textarea"
                    style="width: 95%"/>
        </el-form-item>
      </div>
      <div class="plan-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>报关信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="单证专员" prop="documentSpecialist">
              <el-input v-model="planForm.documentSpecialist" placeholder="请输入"
                        style="width: 95%"/>
              <!--              <Treeselect v-model="planForm.documentSpecialist" :options="deptOptions" :show-count="true"-->
              <!--                          style="width: 95%"-->
              <!--                          placeholder="请选择归属部门"/>-->
            </el-form-item>
          </el-col>
          <el-col :span="6">

            <el-form-item label="财务人员" prop="financialStaff">
              <el-input v-model="planForm.financialStaff" placeholder="请输入"
                        style="width: 95%"/>
              <!--              <Treeselect v-model="planForm.financialStaff" :options="deptOptions" :show-count="true"-->
              <!--                          style="width: 95%"-->
              <!--                          placeholder="请选择归属部门"/>-->
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="plan-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>汇总信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="报关单位" prop="reportCustomsInstitutions">
              <el-input v-model="planForm.reportCustomsInstitutions" placeholder="请输入"
                        style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="运输方式" prop="transType">
              <el-input v-model="planForm.transType" placeholder="请输入"
                        style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="柜型及数量" prop="abinetTypeAndQuantity">
              <el-input v-model="planForm.abinetTypeAndQuantity" placeholder="请输入"
                        style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="贸易方式" prop="tradeMode">
              <el-input v-model="planForm.tradeMode" placeholder="请输入"
                        style="width: 100%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="出运港" prop="shipmentPort">
              <el-input v-model="planForm.shipmentPort" placeholder="请输入"
                        style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="目的港" prop="destinationPort">
              <el-input v-model="planForm.destinationPort" placeholder="请输入"
                        style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收汇方式" prop="collectionMethod">
              <el-input v-model="planForm.collectionMethod" placeholder="请输入"
                        style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="预收汇日期" prop="preCollectDate">
              <el-date-picker
                v-model="planForm.preCollectDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="成交方式" prop="transactionMethod">
              <el-input v-model="planForm.transactionMethod" placeholder="请输入"
                        style="width: 100%"/>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="plan-header">
        <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
          <div style="display: flex;align-items: center">
            <div class="line-item"></div>
            <span>实际业务报关信息</span>
          </div>
          <el-button type="primary" icon="el-icon-plus" @click="createActInfo" style="margin-left: 15px">添加行
          </el-button>
        </div>
        <el-table
          :data="planForm.actInfoList"
          border
          style="margin-top: 15px"
          size="mini"
        >
          <el-table-column label="序号" align="center" min-width="60px">
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
          <el-table-column
            label="操作"
            align="center"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                @click="handleRemovePrice(scope.row, scope.$index)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="plan-header">
        <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
          <div style="display: flex;align-items: center">
            <div class="line-item"></div>
            <span>SAP财务核算收入</span>
          </div>
          <el-button type="primary" icon="el-icon-plus" @click="createActInfo" style="margin-left: 15px">添加行
          </el-button>
        </div>
        <el-table
          :data="planForm.finaical"
          border
          style="margin-top: 15px"
          size="mini"
        >
          <el-table-column label="序号" align="center" min-width="60px">
          </el-table-column>
          <el-table-column label="是否同步SAP" align="center" min-width="60px">
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
          <el-table-column
            label="操作"
            align="center"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                @click="handleRemovePrice(scope.row, scope.$index)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
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
import {deptTreeSelect} from "@/api/system/user";

export default {
  name: "create",
  components: {Treeselect},
  dicts: ['sys_customer_status'],
  data() {
    return {
      planForm: {},
      deptOptions: undefined,
    }
  },
  created() {
    this.getDeptTree()
  },
  methods: {
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    createActInfo() {

    }
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
