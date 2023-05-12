import request from '@/utils/request'
// 获取SAP财务核算收入列表
export function listSAPFinancialApi(query) {
  return request({
    url: '/order/getOrderAndProduct',
    method: 'post',
    data: query
  })
}
// 新增计划
export function addPlanApi(data) {
  return request({
    url: '/order/shippingPlan/saveFcShippingPlan',
    method: 'post',
    data: data
  })
}
// 更新计划
export function updatePlanApi(data) {
  return request({
    url: '/order/shippingPlan/updateFcShippingPlan',
    method: 'post',
    data: data
  })
}
// 计划详细
export function detailPlanApi(query) {
  return request({
    url: '/order/shippingPlan/getShippingPlanDetail',
    method: 'get',
    params: query
  })
}
// 通知专证专员
export function notifyCommissionerApi(data) {
  return request({
    url: '/order/shippingPlan/noticeDocumentSpecialist',
    method: 'post',
    data: data
  })
}
// 报关完成
export function completeCustomsDeclarationApi(data) {
  return request({
    url: '/order/shippingPlan/reportCustomsCompleted',
    method: 'post',
    data: data
  })
}

// 查询计划列表
export function listPlanApi(query) {
  return request({
    url: '/order/shippingPlan/list',
    method: 'get',
    params: query
  })
}
// 同步sap
export function synchronizeSAPApi(data) {
  return request({
    url: '/order/shippingPlan/shippingPlanSyncSap',
    method: 'post',
    data: data
  })
}
