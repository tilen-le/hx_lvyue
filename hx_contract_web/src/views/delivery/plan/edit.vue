<template>
  <div class="app-container">
    <el-form :model="planForm" ref="queryForm" size="small" :rules="rules" label-width="130px"
             style="margin: 15px" v-loading="loading">
      <!--      发货计划信息-->
      <div class="plan-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>发货计划信息</span>
        </div>
        <el-row style="margin: 15px 15px 0px 15px">
          <el-col :span="8">
            <el-form-item label="原计划出库时间" prop="originalPlannedDeliveryDate" >
              <el-date-picker
                v-model="planForm.originalPlannedDeliveryDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出库地点" prop="deliveryLocation">
              <el-input v-model="planForm.deliveryLocation" placeholder="请输入" maxlength="50"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="清关金额" prop="currentNode">
              <el-input-number :min="0" :precision="2" v-model="planForm.currentNode"
                               placeholder="请输入" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <!--      详细信息-->
      <div class="plan-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>详细信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="INVOICE NO" prop="invoiceNo">
              <el-input v-model="planForm.invoiceNo" placeholder="请输入" maxlength="50" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户" prop="customerId">
              <el-select
                v-model="planForm.customerId"
                filterable
                remote
                reserve-keyword
                style="width: 90%"
                placeholder="请输入"
                :remote-method="remoteMethod"
                :loading="searchLoading">
                <el-option
                  v-for="item in customer"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                  {{ item.name }}({{ item.code }})
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收货方" prop="consignee">
              <el-select
                v-model="planForm.consignee"
                filterable
                remote
                reserve-keyword
                style="width: 90%"
                placeholder="请输入"
                :remote-method="remoteMethod2"
                :loading="searchLoading2">
                <el-option
                  v-for="item in customer2"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                  {{ item.name }}({{ item.code }})
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户联系人" prop="customerContact">
              <el-select
                @focus="changeConsignee"
                v-model="planForm.customerContactItem"
                style="width: 90%"
                placeholder="请输入"
                @change="changeCustomerContact"
                :loading="searchLoading3">
                <el-option
                  v-for="item in customerContactList"
                  :key="item.id"
                  :label="item.name"
                  :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="联系方式" prop="contactInformation">
              <el-input v-model="planForm.contactInformation" placeholder="请输入" maxlength="50" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="大洲" prop="continent">
              <el-select
                v-model="planForm.continent"
                placeholder="请选择"
                clearable
                @change="changeContinent"
                style="width: 90%">
                <el-option
                  v-for="dict in dict.type.continent"
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
                <el-option
                  v-for="dict in nation"
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
                filterable
                remote
                reserve-keyword
                style="width: 90%"
                placeholder="请输入"
                :remote-method="remoteMethod3"
                :loading="searchLoading4">
                <el-option
                  v-for="item in customer3"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                  {{ item.name }}({{ item.code }})
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="SIHIPPING MARK" prop="shippingMark">
              <el-input v-model="planForm.shippingMark" placeholder="请输入" maxlength="50" style="width: 90%"/>
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
                  v-for="dict in dict.type.sys_y_n"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="运费" prop="freight">
              <el-input-number :min="0" :precision="2"  v-model="planForm.freight" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="保费" prop="premium">
              <el-input-number :min="0" :precision="2"  v-model="planForm.premium" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="税费" prop="taxation">
              <el-input-number :min="0" :precision="2"  v-model="planForm.taxation" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="海运费" prop="oceanFreight">
              <el-input-number :min="0" :precision="2"  v-model="planForm.oceanFreight" placeholder="请输入" maxlength="30" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="空运费" prop="airFreight">
              <el-input-number :min="0" :precision="2"  v-model="planForm.airFreight" placeholder="请输入" maxlength="30" style="width: 90%"/>
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
              <el-input v-model="planForm.lcNo" placeholder="请输入" maxlength="50" style="width: 90%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="信用证交单快递号" prop="lcDeliveryNo">
              <el-input v-model="planForm.lcDeliveryNo" placeholder="请输入" maxlength="50" style="width: 90%"/>
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
          <el-input v-model="planForm.insuranceClauses" placeholder="请输入" type="textarea"  maxlength="300"
                    style="width: 95%"/>
        </el-form-item>
        <el-form-item label="特殊要求" prop="specialRequirements">
          <el-input v-model="planForm.specialRequirements" placeholder="请输入" type="textarea" maxlength="300"
                    style="width: 95%"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="planForm.remark" placeholder="请输入" type="textarea" maxlength="300"
                    style="width: 95%"/>
        </el-form-item>
        <el-form-item label="报清关差异原因" prop="reportCustomsClearanceReason">
          <el-input v-model="planForm.reportCustomsClearanceReason" placeholder="请输入" type="textarea" maxlength="300"
                    style="width: 95%"/>
        </el-form-item>
      </div>
      <!--      报关信息-->
      <div class="plan-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>报关信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="单证专员" prop="documentSpecialist">
              <el-select v-model="planForm.documentSpecialist" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="item in docKeeper"
                  :key="item.userName"
                  :label="item.nickName"
                  :value="item.userName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">

            <el-form-item label="财务人员" prop="financialStaff">
              <el-select v-model="planForm.financialStaff" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="item in bookKeeper"
                  :key="item.userName"
                  :label="item.nickName"
                  :value="item.userName"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <!--      汇总信息-->
      <div class="plan-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>汇总信息</span>
        </div>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="报关单位" prop="reportCustomsInstitutions">
              <el-select
                v-model="planForm.reportCustomsInstitutions"
                placeholder="请选择"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.sys_receive_master"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="运输方式" prop="transType">
              <el-select
                v-model="planForm.transType"
                placeholder="请选择"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.sys_trans_category"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="柜型及数量" prop="abinetTypeAndQuantity">
              <el-input v-model="planForm.abinetTypeAndQuantity" placeholder="请输入" maxlength="100"
                        style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="贸易方式" prop="tradeMode">
              <el-select
                v-model="planForm.tradeMode"
                placeholder="请选择"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.trade_type"
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
            <el-form-item label="出运港" prop="shipmentPort">
              <el-select
                v-model="planForm.shipmentPort"
                placeholder="请选择"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.pol_cate"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="目的港" prop="destinationPort">
              <el-select
                v-model="planForm.destinationPort"
                placeholder="请选择"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.exs_cate"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
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
                style="width:100%"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin: 15px 15px 0 15px">
          <el-col :span="6">
            <el-form-item label="成交方式" prop="transactionMethod">
              <el-select
                v-model="planForm.transactionMethod"
                placeholder="请选择"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.sold_for"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <!--      实际业务报关信息-->
      <div class="plan-header">
        <div style="display: flex;justify-content: space-between;align-items: center;padding-right: 15px">
          <div style="display: flex;align-items: center">
            <div class="line-item"></div>
            <span>实际业务报关信息</span>
          </div>
          <el-button type="primary" icon="el-icon-plus" @click="createReport" style="margin-left: 15px">添加行
          </el-button>
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
            <template slot-scope="scope">
              <el-input
                controls-position="right"
                placeholder="请输入"
                style="width: 70%"
                maxlength="100"
                v-model="scope.row.goodsDescCn"
              />
            </template>
          </el-table-column>
          <el-table-column label="报关数量" align="center" min-width="60px" prop="num">
            <template slot-scope="scope">
              <el-input-number :min="0"
                               controls-position="right"
                               placeholder="请输入"
                               style="width: 70%"
                               v-model="scope.row.num"
              />
            </template>
          </el-table-column>
          <el-table-column label="报关金额" align="center" min-width="60px" prop="amount">
            <template slot-scope="scope">
              <el-input-number :min="0" :precision="2"
                               controls-position="right"
                               placeholder="请输入"
                               style="width: 70%"
                               v-model="scope.row.amount"
              />
            </template>
          </el-table-column>
          <el-table-column label="品牌型号" align="center" min-width="60px" prop="brandModel">
            <template slot-scope="scope">
              <el-input
                controls-position="right"
                maxlength="100"
                placeholder="请输入"
                style="width: 70%"
                v-model="scope.row.brandModel"
              />
            </template>
          </el-table-column>
          <el-table-column label="包装件数" align="center" min-width="60px" prop="packNumber">
            <template slot-scope="scope">
              <<el-input-number :min="0"
                                controls-position="right"
                                placeholder="请输入"
                                style="width: 70%"
                                v-model="scope.row.packNumber"
            />
            </template>
          </el-table-column>
          <el-table-column label="包装种类" align="center" min-width="60px" prop="packagingType">
            <template slot-scope="scope">
              <el-input
                controls-position="right"
                :precision="2"
                placeholder="请输入"
                style="width: 70%"
                v-model="scope.row.packagingType"
              />
            </template>
          </el-table-column>
          <el-table-column label="毛重" align="center" min-width="60px" prop="grossWeight">
            <template slot-scope="scope">
              <el-input
                maxlength="100"
                controls-position="right"
                :precision="2"
                placeholder="请输入"
                style="width: 70%"
                v-model="scope.row.grossWeight"
              />
            </template>
          </el-table-column>
          <el-table-column label="净重" align="center" min-width="60px" prop="netWeight">
            <template slot-scope="scope">
              <el-input
                controls-position="right"
                maxlength="100"
                placeholder="请输入"
                style="width: 70%"
                v-model="scope.row.netWeight"
              />
            </template>
          </el-table-column>
          <el-table-column label="体积(CBM)" align="center" min-width="60px" prop="volume">
            <template slot-scope="scope">
              <el-input
                controls-position="right"
                maxlength="100"
                placeholder="请输入"
                style="width: 70%"
                v-model="scope.row.volume"
              />
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
                @click="removeReport(scope.row, scope.$index)"
              >删除
              </el-button>
            </template>
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
          <el-button type="primary" icon="el-icon-plus" @click="createActInfo" style="margin-left: 15px">添加行
          </el-button>
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
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.sapSyncFlag"
                placeholder="请输入">
                <el-option
                  v-for="dict in dict.type.ynn"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
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
            <template slot-scope="scope">
              <el-input
                controls-position="right"
                placeholder="请输入"
                style="width: 70%"
                v-model="scope.row.reportCustomsNum"
              />
            </template>
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
            <template slot-scope="scope">
              <el-input-number :min="0" :precision="2"
                               controls-position="right"
                               style="width: 70%"
                               v-model="scope.row.realityReportCustomsAmount"
              />
            </template>
          </el-table-column>
          <el-table-column label="单价" align="center" min-width="60px" prop="unitPrice">
          </el-table-column>
          <el-table-column label="SAP物料编码" align="center" min-width="60px" prop="sapMaterialCode">
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                @click="removeSap(scope.row, scope.$index)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!--      附件-->
      <div class="info-header">
        <div style="display: flex;align-items: center">
          <div class="line-item"></div>
          <span>附件</span>
        </div>
        <el-form-item label="" style="margin: 15px" label-width="0px">
          <fileUpload v-model="planForm.file" :valueJson="true"/>
        </el-form-item>
      </div>
    </el-form>
    <div style="text-align: right">
      <el-button type="primary" @click="submitForm">提 交</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
    <!--    订单列表选择弹窗-->
    <el-dialog title="SAP财务核算收入新增" :visible.sync="dialogVisible" >
      <!--      筛选条件-->
      <el-input
        placeholder="请输入内容"
        v-model="orderKeyword"
        clearable style="width: 40%;margin-right: 5px">
      </el-input>
      <el-button type="primary" @click="queryOrderList">查询</el-button>
      <el-table :data="orderList" ref="orderListTable" style="margin-top: 10px">
        <el-table-column type="selection" width="55"/>
        <el-table-column property="orderNumber" label="订单编号"></el-table-column>
        <el-table-column property="name" label="行项目"></el-table-column>
        <el-table-column property="productName" label="产品名"></el-table-column>
        <el-table-column property="orderName" label="订单名称"></el-table-column>
        <el-table-column property="customer" label="客户"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
      <el-button @click="undoSelectedOrders">取 消</el-button>
      <el-button type="primary" @click="doSelectedOrders">提 交</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import {deptTreeSelect} from "@/api/system/user";
import { getAddress, listCustomer, listCustomer2 } from '@/api/customer'
import {listBookKeeper, listDocKeeper} from "@/api/system/role";
import { listOrderByKeyWordApi } from '@/api/order'
import { detailPlanApi, listSAPFinancialApi, updatePlanApi } from '@/api/plan'

export default {
  name: "edit",
  components: {Treeselect},
  dicts: ['sys_customer_status', 'sys_currency', 'continent', 'sys_y_n', 'sys_receive_master',
    'sys_trans_category', 'trade_type', 'pol_cate', 'exs_cate', 'sold_for','sys_yes_no','ynn'],
  data() {
    return {
      planForm: {
        reportList: [],
        // sap财务核算列表
        financialList:[]
      },
      customer: [],
      customer2: [],
      customer3: [],
      bookKeeper: [],
      docKeeper: [],
      deptOptions: undefined,
      searchLoading: false,
      searchLoading2: false,
      searchLoading3: false,
      searchLoading4: false,
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
      rules: {
        originalPlannedDeliveryDate: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        deliveryLocation: [
          {required: true, message: "请输入", trigger: "blur"}
        ],
        invoiceNo: [
          {required: true, message: "请输入", trigger: "blur"}
        ],
        customerContact: [
          {required: true, message: "请输入", trigger: "blur"}
        ],
        customerId: [
          {required: true, message: "请输入", trigger: "blur"}
        ],
        consignee: [
          {required: true, message: "请输入", trigger: "blur"}
        ],
        continent: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        nation: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        shippingMark: [
          {required: true, message: "请输入", trigger: "blur"}
        ],
        goodsCompleteDateAndLocate: [
          {required: true, message: "请输入", trigger: "blur"}
        ],
        transType: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        tradeMode: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        destinationPort: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        collectionMethod: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        transactionMethod: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
        // goodsDescCn: [
        //   {required: true, message: "请输入", trigger: "blur"}
        // ],
        // num: [
        //   {required: true, message: "请输入", trigger: "blur"}
        // ],
        // amount: [
        //   {required: true, message: "请输入", trigger: "blur"}
        // ],
        // brandModel: [
        //   {required: true, message: "请输入", trigger: "blur"}
        // ],
        // sapSyncFlag: [
        //   {required: true, message: "请输入", trigger: "blur"}
        // ],
        // customsDeclarationQuantity: [
        //   {required: true, message: "请输入", trigger: "blur"}
        // ],
        // reportCustomsNum: [
        //   {required: true, message: "请输入", trigger: "blur"}
        // ],
        // realityReportCustomsAmount: [
        //   {required: true, message: "请输入", trigger: "blur"}
        // ],
      },
      // 客户联系人列表
      customerContactList: [],
      // 表格弹窗
      dialogVisible: false,
      // 订单列表
      orderList: [],
      // 订单查询关键词
      orderKeyword: null,
      loading: true,
    }
  },
  created() {
    // 获取计划id
    this.planForm.planId=this.$route.params.oid
    // 获取人员列表
    // 获取单证专员和财务人员列表
    this.initKeeper()
    // 获取部门树
    this.getDeptTree()
    // 获取计划详细
    this.getPlanDetail()
  },
  methods: {
    // 获取计划详细
    getPlanDetail(){
      this.loading=true
      this.planForm.planId
      let param={
        id: this.planForm.planId
      }
      detailPlanApi(param).then(res=>{
        this.planForm=res.data
      }).then(res=>{
        // 客户
        let param1={id: this.planForm.customerId}
        // 收货方
        let param2={id: this.planForm.consignee}
        // 通知方
        let param3={id: this.planForm.notifyId}
        listCustomer2(param1).then(res => {
          this.customer = res.data
        })
        listCustomer2(param2).then(res => {
          this.customer2 = res.data
        })
        listCustomer2(param3).then(res => {
          this.customer3 = res.data
        })
        // 客户联系人列表
        this.initConsignee()
        // 国家列表
        this.changeContinent(this.planForm.continent)
      }).then(res=>{
        this.loading=false
      })
    },
    // 下拉框--远程获取人员信息
    remoteMethod(query) {

      setTimeout(() => {
        if(null==query||""==query)
          return
        const params = {
          name: query
        }
        listCustomer2(params).then(res => {
          this.customer = []
          this.customer = res.data
        })
      }, 1000)
    },
    // 下拉框--远程获取人员信息
    remoteMethod2(query) {
      setTimeout(() => {
        if(null==query||""==query)
          return
        const params = {
          name: query
        }
        listCustomer2(params).then(res => {
          this.customer2 = []
          this.customer2 = res.data
        })
      }, 1000)
    },
    // 下拉框--远程获取人员信息
    remoteMethod3(query) {
      setTimeout(() => {
        if(null==query||""==query)
          return
        const params = {
          name: query
        }
        listCustomer2(params).then(res => {
          this.customer3 = []
          this.customer3 = res.data
        })
      }, 1000)
    },
    // 《大洲》下拉框改变
    changeContinent(value){
      // 依据大洲id获取国家列表
      this.nation
    },
    // 初始化
    initConsignee() {
      this.searchLoading3=true
      this.customerContactList=[]
      // 修改客户可选联系人列表
      let param={
        customerId: this.planForm.consignee
      }
      getAddress(param).then((res => {
        this.customerContactList = res.data
        for (let customerContact of this.customerContactList) {
          if(customerContact.id==this.planForm.customerContact){
            this.planForm.customerContactItem=customerContact
            break
          }
        }
        this.searchLoading3=false
      }))
    },
    // 《收货方》下拉框改变
    changeConsignee() {
      this.searchLoading3=true
      if(null==this.planForm.consignee){
        this.$message({
          message: '"您尚未选择收货方"',
          type: 'warning'
        });
        return
      }
      this.customerContactList=[]
      // 修改客户可选联系人列表
      let param={
        customerId: this.planForm.consignee
      }
      getAddress(param).then((res => {
        this.customerContactList = res.data
        this.searchLoading3=false
      }))
    },
    // 客户联系人改变
    changeCustomerContact(value){
      // 联系人赋值
      this.planForm.customerContact=value.id
      // 电话赋值
      this.planForm.contactInformation=value.phone
    },
    // 新增报表项
    createReport() {
      const length = this.planForm.reportList.length;
      this.planForm.reportList.push({
        sequence: length===0?1:this.planForm.reportList[length-1].sequence+1,
        goodsDescCn: undefined,
        num: undefined,
        amount: undefined,
        brandModel: undefined,
        packNumber: undefined,
        packagingType: undefined,
        grossWeight: undefined,
        netWeight: undefined,
        volume: undefined
      });
    },
    // 部门树
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 删除报表项
    removeReport(idx, index) {
      this.planForm.reportList.splice(index, 1);
    },
    // 删除SAP财务核算收入
    removeSap(row, index){
      this.planForm.financialList.splice(index,1);
    },
    // 单证人员、财务人员
    initKeeper() {
      const params = {
        roleName: "财务",
      }
      const params2 = {
        roleName: "单证专员",
      }
      listBookKeeper(params).then(res => {
        this.bookKeeper = res.data
      })
      listDocKeeper(params2).then(res => {
        this.docKeeper = res.data
      })
    },
    // 查询订单列表
    queryOrderList(){
      // 清空数组
      this.orderList=[]
      let param={
        orderNoOrName: this.orderKeyword
      }
      listOrderByKeyWordApi(param).then(res=>{
        this.orderList=res.data
      })
    },
    // SAP财务核算收入:选择订单
    createActInfo() {
      this.dialogVisible=true
    },
    // 确定选中订单
    doSelectedOrders(){
      let myProductIds=[]
      this.$refs.orderListTable.selection.forEach(function(e) {
        myProductIds.push(e.productId);
      })
      // 获取SAP财务核算收入列表
      listSAPFinancialApi(myProductIds).then(res=>{
        if(res.data.length>0){
          this.planForm.financialList=this.planForm.financialList.concat(res.data)
        }
      }).then(res=>{
        // 关闭弹窗
        this.dialogVisible=false
        // 数组清空
        this.orderList=[]
        // 查询条件清空
        this.orderKeyword=null
      })
    },
    // 取消选中订单
    undoSelectedOrders(){
      // 关闭弹窗
      this.dialogVisible=false
      // 数组清空
      this.orderList=[]
      // 查询条件清空
      this.orderKeyword=null
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
