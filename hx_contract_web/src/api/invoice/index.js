import request from '@/utils/request'
export function addInvoice(data) {
  return request({
    url: '/invoice/add',
    method: 'post',
    data: data
  })
}
export function addDelivery(data) {
  return request({
    url: '/order/consignment/add',
    method: 'post',
    data: data
  })
}
export function approveDelivery(data) {
  return request({
    url: '/order/consignment/approve',
    method: 'post',
    data: data
  })
}


export function listInvoice(query) {
  return request({
    url: '/invoice/list',
    method: 'get',
    params: query
  })
}

export function getAddressByCode(query) {
  return request({
    url: '/customer/getAddressByCode',
    method: 'get',
    params: query
  })
}

/**
 * 获取发票详情
 * @param query
 * @returns {*}
 */
export function getInvoiceDetail(query) {
  return request({
    url: '/invoice/detail',
    method: 'get',
    params: query
  })
}

/**
 * 附件上传
 * @param query
 * @returns {*}
 */
export function upload(query) {
  return request({
    url: '/system/oss/upload',
    method: 'post',
    params: query
  })
}
